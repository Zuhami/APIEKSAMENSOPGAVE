package app.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.entities.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Populate {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public static void populateDatabase(EntityManagerFactory emf){

        List<Candidate> candidates = loadJsonFile("/Candidate.json", new TypeReference<List<Candidate>>() {});


        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            //persist all actions
            Set<Skill> uniqueSkills = candidates.stream()
                    .flatMap(c -> c.getSkills().stream())
                    .collect(Collectors.toSet());

            uniqueSkills.forEach(em::persist);


            candidates.forEach(em::persist);




            em.getTransaction().commit();
        }

        System.out.println("--- Database populated successfully! ---");



    }

    /**
     * Generic method to load any JSON list into a list of objects
     */
    public static <T> List<T> loadJsonFile(String path, TypeReference<List<T>> typeRef) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = Populate.class.getResourceAsStream(path)) {
            if (input == null) {
                throw new IOException("Could not find " + path + " in resources folder.");
            }
            return mapper.readValue(input, typeRef);
        } catch (IOException e) {
            throw new RuntimeException("Error reading " + path, e);
        }
    }

    // Optional main for testing
    public static void main(String[] args) {

        populateDatabase(HibernateConfig.getEntityManagerFactory());
    }
}
