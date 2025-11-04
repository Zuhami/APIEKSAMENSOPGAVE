package app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor   // Needed by Jackson
@AllArgsConstructor // Full constructor
public class DogDTO {
    private int id;
    private String name;
    private String breed;
    private String gender;
    private int age;
}