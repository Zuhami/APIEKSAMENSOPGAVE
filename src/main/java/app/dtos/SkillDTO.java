package app.dtos;

import app.entities.Skill;
import app.enums.SkillCategory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillDTO {

    private int id;
    private String name;
    private SkillCategory category;
    private String description;


    public SkillDTO(Skill skill) {
        this.id = skill.getId();
        this.name = skill.getName();
        this.category = skill.getCategory();
        this.description = skill.getDescription();
    }
}
