package app.daos.impl;

import app.daos.IDAO;
import app.dtos.SkillDTO;
import app.entities.Skill;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class SkillDAO implements IDAO<SkillDTO, Integer> {

    private static SkillDAO instance;
    private static EntityManagerFactory emf;

    // ✅ Singleton
    public static SkillDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SkillDAO();
        }
        return instance;
    }


    @Override
    public SkillDTO read(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Skill skill = em.find(Skill.class, id);
            return skill != null ? new SkillDTO(
                    skill.getId(),
                    skill.getName(),
                    skill.getCategory(),
                    skill.getDescription()
            ) : null;
        }
    }


    @Override
    public List<SkillDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<SkillDTO> query = em.createQuery(
                    "SELECT new app.dtos.SkillDTO(s.id, s.name, s.category, s.description) FROM Skill s",
                    SkillDTO.class
            );
            return query.getResultList();
        }
    }


    @Override
    public SkillDTO create(SkillDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            Skill skill = new Skill(
                    dto.getName(),
                    dto.getCategory(),
                    dto.getDescription()
            );
            em.getTransaction().begin();
            em.persist(skill);
            em.getTransaction().commit();

            return new SkillDTO(
                    skill.getId(),
                    skill.getName(),
                    skill.getCategory(),
                    skill.getDescription()
            );
        }
    }


    @Override
    public SkillDTO update(Integer id, SkillDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Skill skill = em.find(Skill.class, id);
            if (skill == null) {
                em.getTransaction().rollback();
                return null;
            }

            skill.setName(dto.getName());
            skill.setCategory(dto.getCategory());
            skill.setDescription(dto.getDescription());
            em.getTransaction().commit();

            return new SkillDTO(
                    skill.getId(),
                    skill.getName(),
                    skill.getCategory(),
                    skill.getDescription()
            );
        }
    }


    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Skill skill = em.find(Skill.class, id);
            if (skill != null) {
                // 🔹 Unlink this skill from all candidates first
                skill.getCandidates().forEach(candidate -> candidate.getSkills().remove(skill));
                em.flush(); // Apply unlink before deletion

                em.remove(skill);
            }

            em.getTransaction().commit();
        }
    }


    @Override
    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Skill.class, id) != null;
        }
    }
}
