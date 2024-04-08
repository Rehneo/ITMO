package Lab4.repository;
import Lab4.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
@Transactional
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    public void update(User user){
        entityManager.merge(user);
        entityManager.flush();
    }

    public List<User> getByUsername(String username){
        String query ="select user from User user where user.username = :username";
        return entityManager.createQuery(query, User.class)
                .setParameter("username", username).getResultList();
    }


    public List<User> getByToken(String token){
        String query ="select user from User user where user.token = :token";
        return entityManager.createQuery(query, User.class)
                .setParameter("token", token).getResultList();
    }
}
