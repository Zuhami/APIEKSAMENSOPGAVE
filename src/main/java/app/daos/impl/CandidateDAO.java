package app.daos.impl;

import app.daos.IDAO;
import app.dtos.CandidateDTO;
import app.entities.Candidate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CandidateDAO implements IDAO<CandidateDTO, Integer> {

    private static CandidateDAO instance;
    private static EntityManagerFactory emf;


    public static CandidateDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CandidateDAO();
        }
        return instance;
    }


    @Override
    public CandidateDTO read(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Candidate candidate = em.find(Candidate.class, id);
            return candidate != null ? new CandidateDTO(
                    candidate.getId(),
                    candidate.getName(),
                    candidate.getEducation(),
                    candidate.getPhone()
            ) : null;
        }
    }


    @Override
    public List<CandidateDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<CandidateDTO> query = em.createQuery(
                    "SELECT new app.dtos.CandidateDTO(c.id, c.name, c.education, c.phone) FROM Candidate c",
                    CandidateDTO.class
            );
            return query.getResultList();
        }
    }


    @Override
    public CandidateDTO create(CandidateDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            Candidate candidate = new Candidate(
                    dto.getName(),
                    dto.getPhone(),
                    dto.getEducation()
            );
            em.getTransaction().begin();
            em.persist(candidate);
            em.getTransaction().commit();

            // Return DTO with new ID
            return new CandidateDTO(
                    candidate.getId(),
                    candidate.getName(),
                    candidate.getEducation(),
                    candidate.getPhone()
            );
        }
    }


    @Override
    public CandidateDTO update(Integer id, CandidateDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Candidate candidate = em.find(Candidate.class, id);
            if (candidate == null) {
                em.getTransaction().rollback();
                return null;
            }
            candidate.setName(dto.getName());
            candidate.setPhone(dto.getPhone());
            candidate.setEducation(dto.getEducation());
            em.getTransaction().commit();

            return new CandidateDTO(
                    candidate.getId(),
                    candidate.getName(),
                    candidate.getEducation(),
                    candidate.getPhone()
            );
        }
    }


    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Candidate candidate = em.find(Candidate.class, id);
            if (candidate != null) {
                em.remove(candidate);
            }
            em.getTransaction().commit();
        }
    }


    @Override
    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Candidate.class, id) != null;
        }
    }
}
