package app.routes;
import app.controllers.impl.CandidateController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class CandidateRoutes {

    private final CandidateController candidateController = new CandidateController();

    protected EndpointGroup getRoutes() {
        return () -> {
            post("/", CandidateController::create);    // create new shop (ADMIN role required)
            get("/", CandidateController::getAll);                // get all shops
            get("/{id}", CandidateController::getById);               // get shop by id
            put("/{id}", CandidateController::update);             // update shop by id (ADMIN role required)
            delete("/{id}", CandidateController::delete);          // delete shop by id (ADMIN role required)
        };
    }
}