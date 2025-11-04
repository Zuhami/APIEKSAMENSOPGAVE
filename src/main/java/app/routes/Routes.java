package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final CandidateRoutes candidateRoutes = new CandidateRoutes();


    public EndpointGroup getRoutes() {
        return () -> {
            path("/Candidate", candidateRoutes.getRoutes());

        };
    }
}
