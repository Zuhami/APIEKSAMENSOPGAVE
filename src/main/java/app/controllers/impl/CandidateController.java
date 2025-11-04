package app.controllers.impl;

import app.config.HibernateConfig;
import app.daos.impl.CandidateDAO;
import app.dtos.CandidateDTO;
import app.entities.Candidate;
import app.entities.Skill;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class CandidateController {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final CandidateDAO dao = CandidateDAO.getInstance(emf);


    public static void create(Context ctx) {
        CandidateDTO dto = ctx.bodyAsClass(CandidateDTO.class);
        CandidateDTO created = dao.create(dto);
        ctx.status(201).json(created);
    }


    public static void getAll(Context ctx) {
        List<CandidateDTO> candidates = dao.readAll();
        ctx.json(candidates);
    }


    public static void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        CandidateDTO candidate = dao.read(id);
        if (candidate == null) {
            ctx.status(404).result("Candidate not found");
        } else {
            ctx.json(candidate);
        }
    }


    public static void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        CandidateDTO dto = ctx.bodyAsClass(CandidateDTO.class);
        CandidateDTO updated = dao.update(id, dto);
        if (updated == null) {
            ctx.status(404).result("Candidate not found");
        } else {
            ctx.json(updated);
        }
    }


    public static void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        if (dao.validatePrimaryKey(id)) {
            dao.delete(id);

            ctx.status(204);
        } else {
            ctx.status(404).result("Candidate not found");
        }
    }
    public static void AddSkillToCandidate(Context ctx) {
        int candidateId = Integer.parseInt(ctx.pathParam("candidateId"));
        int skillId = Integer.parseInt(ctx.pathParam("skillId"));
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Candidate candidate = em.find(Candidate.class, candidateId);
            Skill skill = em.find(Skill.class,skillId);
            if (candidate == null || skill == null) {
                ctx.status(404).result("Skill not found");
                em.getTransaction().rollback();
                return;
            }
            candidate.getSkills().add(skill);
            em.merge(candidate);
            em.getTransaction().commit();
            System.out.println("Skill added");
        }catch (Exception e) {
            ctx.status(500).result("could not add skill" + e.getMessage());
        }
    }

    public static void getByCategory(Context ctx) {
        String categoryParam = ctx.pathParam("category").toUpperCase();

        var emf = HibernateConfig.getEntityManagerFactory();
        try (var em = emf.createEntityManager()) {
            List<Candidate> candidates = em.createQuery(
                    "SELECT DISTINCT c FROM Candidate c LEFT JOIN FETCH c.skills",
                    Candidate.class
            ).getResultList();

            List<CandidateDTO> filtered = candidates.stream()
                    .filter(c -> c.getSkills().stream()
                            .anyMatch(s -> s.getCategory().name().equalsIgnoreCase(categoryParam)))
                    .map(c -> new CandidateDTO(c.getId(), c.getName(), c.getEducation(), c.getPhone()))
                    .toList();

            if (filtered.isEmpty()) {
                ctx.status(404).result("No candidates found for category: " + categoryParam);
            } else {
                ctx.json(filtered);
            }
        } catch (Exception e) {
            ctx.status(500).result("Error filtering candidates: " + e.getMessage());
        }
    }


}

