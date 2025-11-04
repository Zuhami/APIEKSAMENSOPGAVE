package app.dtos;

import app.entities.Candidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor   // Needed by Jackson
@AllArgsConstructor // Full constructor
public class CandidateDTO {
    private int id;
    private String name;
    private String education;
    private String phone;

    public CandidateDTO(Candidate candidate) {
        this.id = candidate.getId();
        this.name = candidate.getName();
        this.education = candidate.getEducation();
        this.phone = candidate.getPhone();
    }

    // Convert from DTO to Entity
    public Candidate toEntity() {
        Candidate candidate = new Candidate();
        candidate.setId(this.id);
        candidate.setName(this.name);
        candidate.setEducation(this.education);
        candidate.setPhone(this.phone);
        return candidate;
    }
}