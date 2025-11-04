package app.controllers.impl;

import app.config.HibernateConfig;
import app.daos.impl.SkillDAO;
import app.dtos.SkillDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class SkillController {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final SkillDAO dao = SkillDAO.getInstance(emf);


    public static void create(Context ctx) {
        SkillDTO dto = ctx.bodyAsClass(SkillDTO.class);
       SkillDTO created = dao.create(dto);
        ctx.status(201).json(created);
    }


    public static void getAll(Context ctx) {
        List<SkillDTO> skills = dao.readAll();
        ctx.json(skills);
    }


    public static void getById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        SkillDTO skill = dao.read(id);
        if (skill == null) {
            ctx.status(404).result("skill not found");
        } else {
            ctx.json(skill);
        }
    }


    public static void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        SkillDTO dto = ctx.bodyAsClass(SkillDTO.class);
       SkillDTO updated = dao.update(id, dto);
        if (updated == null) {
            ctx.status(404).result("skill not found");
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
            ctx.status(404).result("skill not found");
        }
    }

}
