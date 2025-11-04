package app.controllers.impl;

import app.config.HibernateConfig;
import app.daos.impl.CandidateDAO;
import app.dtos.CandidateDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class CandidateController {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final CandidateDAO dao = CandidateDAO.getInstance(emf);

    // 🔹 CREATE
    public static void create(Context ctx) {
        CandidateDTO dto = ctx.bodyAsClass(CandidateDTO.class);
        CandidateDTO created = dao.create(dto);
        ctx.status(201).json(created);
    }

    // 🔹 READ ALL
    public static void getAll(Context ctx) {
        List<CandidateDTO> candidates = dao.readAll();
        ctx.json(candidates);
    }

    // 🔹 READ BY ID
    public static void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        CandidateDTO candidate = dao.read(id);
        if (candidate == null) {
            ctx.status(404).result("Candidate not found");
        } else {
            ctx.json(candidate);
        }
    }

    // 🔹 UPDATE
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

    // 🔹 DELETE
    public static void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        if (dao.validatePrimaryKey(id)) {
            dao.delete(id);
            ctx.status(204);
        } else {
            ctx.status(404).result("Candidate not found");
        }
    }
}
