package app.entities;

import app.enums.SkillCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    private SkillCategory category;

    private String description;

    @ManyToMany(mappedBy = "skills")
    private Set<Candidate> candidates = new HashSet<>();


    public Skill(String name, SkillCategory category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }
}