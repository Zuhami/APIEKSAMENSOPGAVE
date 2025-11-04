package app.routes;

import app.controllers.impl.SkillController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class SkillRoutes {

    protected EndpointGroup getRoutes() {
        return () -> {
            post("/", SkillController::create);      // Create skill
            get("/", SkillController::getAll);       // Read all skills
            get("/{id}", SkillController::getById);  // Read by ID
            put("/{id}", SkillController::update);   // Update skill
            delete("/{id}", SkillController::delete);// Delete skill
        };
    }
}