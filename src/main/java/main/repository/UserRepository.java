package main.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import main.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private EntityManager entityManager;

    UserRepository(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    public List<User> getUsers() {
        TypedQuery<User> query = entityManager.createQuery("FROM User ORDER BY firstName ASC", User.class);
        return query.getResultList();
    }

    public User getUserById(int userId) {
        return entityManager.find(User.class, userId);
    }
}
