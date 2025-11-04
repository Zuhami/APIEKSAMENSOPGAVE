package app.routes;
import app.controllers.impl.CandidateController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class CandidateRoutes {

    private final CandidateController candidateController = new CandidateController();

    protected EndpointGroup getRoutes() {
        return () -> {
            post("/", CandidateController::create);
            get("/", CandidateController::getAll);
            get("/{id}", CandidateController::getById);
            put("/{id}", CandidateController::update);
            delete("/{id}", CandidateController::delete);
            put("/{candidateId}/skills/{skillId}", CandidateController::AddSkillToCandidate);
            get("/category/{category}", CandidateController::getByCategory);

        };
    }
}