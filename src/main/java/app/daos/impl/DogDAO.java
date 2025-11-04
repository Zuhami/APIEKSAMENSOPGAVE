package app.daos.impl;

import app.daos.IDAO;
import app.dtos.DogDTO;
import app.entities.Dog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class DogDAO implements IDAO<DogDTO, Integer> {

    private static DogDAO instance;
    private static EntityManagerFactory emf;

    // ✅ Singleton pattern
    public static DogDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogDAO();
        }
        return instance;
    }

    // 🔹 Read by ID
    @Override
    public DogDTO read(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Dog dog = em.find(Dog.class, id);
            return dog != null ? new DogDTO(
                    dog.getId(),
                    dog.getName(),
                    dog.getBreed(),
                    dog.getGender(),
                    dog.getAge()
            ) : null;
        }
    }

    // 🔹 Read all
    @Override
    public List<DogDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<DogDTO> query = em.createQuery(
                    "SELECT new app.dtos.DogDTO(d.id, d.name, d.breed, d.gender, d.age) FROM Dog d",
                    DogDTO.class
            );
            return query.getResultList();
        }
    }

    // 🔹 Create new dog
    @Override
    public DogDTO create(DogDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            Dog dog = new Dog(dto);
            em.getTransaction().begin();
            em.persist(dog);
            em.getTransaction().commit();
            return new DogDTO(dog.getId(), dog.getName(), dog.getBreed(), dog.getGender(), dog.getAge());
        }
    }

    // 🔹 Update existing dog
    @Override
    public DogDTO update(Integer id, DogDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Dog dog = em.find(Dog.class, id);
            if (dog != null) {
                dog.setName(dto.getName());
                dog.setBreed(dto.getBreed());
                dog.setGender(dto.getGender());
                dog.setAge(dto.getAge());
            }
            em.getTransaction().commit();
            return new DogDTO(dog.getId(), dog.getName(), dog.getBreed(), dog.getGender(), dog.getAge());
        }
    }

    // 🔹 Delete dog
    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Dog dog = em.find(Dog.class, id);
            if (dog != null) {
                em.remove(dog);
            }
            em.getTransaction().commit();
        }
    }

    // 🔹 Validate if ID exists
    @Override
    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Dog.class, id) != null;
        }
    }
}
