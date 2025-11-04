package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dog")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    private String breed;
    private String gender;
    private int age;

    // ✅ Convenience constructor to create Dog from a DogDTO
    public Dog(app.dtos.DogDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.breed = dto.getBreed();
        this.gender = dto.getGender();
        this.age = dto.getAge();
    }
}
