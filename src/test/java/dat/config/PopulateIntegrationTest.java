package dat.config;

import app.config.Populate;
import app.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PopulateIntegrationTest {

    @Test
    void testPopulateDatabaseWithJsonData() {
        // 1️⃣ Create an EntityManagerFactory using your Testcontainers setup
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();

        // 2️⃣ Populate the database using your existing logic
        Populate.populateDatabase(emf);

        // 3️⃣ Verify the data was inserted correctly
        EntityManager em = emf.createEntityManager();

        Long DogCount = em.createQuery("SELECT COUNT(d) FROM Dog d", Long.class).getSingleResult();

        //Long actionCount = em.createQuery("SELECT COUNT(a) FROM Action a", Long.class).getSingleResult();

        System.out.printf("dogs: %d",
                DogCount);

        // 4️⃣ Assert that data exists in all tables
        assertTrue(DogCount > 0, "No towns found in DB!");

        //assertTrue(actionCount > 0, "No actions found in DB!");

        em.close();
    }
}

