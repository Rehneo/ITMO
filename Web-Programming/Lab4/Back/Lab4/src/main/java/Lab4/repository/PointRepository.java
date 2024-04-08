package Lab4.repository;


import Lab4.entity.Point;
import Lab4.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
@Transactional
public class PointRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public void save(Point point){
        entityManager.persist(point);
        entityManager.flush();
    }

    public void clearByOwner(User user){
        entityManager.createQuery("delete from Point point where point.owner = :owner")
                .setParameter("owner", user).executeUpdate();
    }

    public List<Point> getByOwner(User user){
        String query = "select point from Point point where point.owner = :owner";
        return entityManager.createQuery(query, Point.class)
                .setParameter("owner", user).getResultList();
    }

}
