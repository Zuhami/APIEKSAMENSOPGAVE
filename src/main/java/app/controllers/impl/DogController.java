package app.controllers.impl;

import app.dtos.DogDTO;
import io.javalin.http.Context;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Data
@NoArgsConstructor
public class DogController {

    private static Map<Integer, DogDTO> dogs = new HashMap<>();
    private static int currentId = 1;

    // Get all dogs
    public static void getAllDogs(Context ctx) {
        Collection<DogDTO> allDogs = dogs.values();
        ctx.json(allDogs);
    }

    // Get dog by ID
    public static void getDogById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        DogDTO dog = dogs.get(id);
        if (dog == null) {
            ctx.status(404).result("Dog not found");
        } else {
            ctx.json(dog);
        }
    }

    // Create new dog
    public static void createDog(Context ctx) {
        DogDTO newDog = ctx.bodyAsClass(DogDTO.class);
        newDog.setId(currentId++);
        dogs.put(newDog.getId(), newDog);
        ctx.status(201).json(newDog);
    }

    // Update dog
    public static void updateDog(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        DogDTO existingDog = dogs.get(id);
        if (existingDog == null) {
            ctx.status(404).result("Dog not found");
            return;
        }

        DogDTO updatedDog = ctx.bodyAsClass(DogDTO.class);
        updatedDog.setId(id);
        dogs.put(id, updatedDog);
        ctx.json(updatedDog);
    }

    // Delete dog
    public static void deleteDog(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        DogDTO removedDog = dogs.remove(id);
        if (removedDog == null) {
            ctx.status(404).result("Dog not found");
        } else {
            ctx.json(removedDog);
        }
    }
}
