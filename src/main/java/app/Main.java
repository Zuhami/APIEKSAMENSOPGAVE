package app;
import app.controllers.impl.DogController;
import io.javalin.Javalin;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        }).start(7007);


        app.get("/api/dog", DogController::getAllDogs);
        app.get("/api/dog/{id}", DogController::getDogById);
        app.post("/api/dog", DogController::createDog);
        app.put("/api/dog/{id}", DogController::updateDog);
        app.delete("/api/dog/{id}", DogController::deleteDog);

    }


}
