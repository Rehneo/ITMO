package web.database;
import web.model.Points;
import web.utils.AreaChecker;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Named("dbManager")
@SessionScoped
public class DatabaseManager implements Serializable {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    @Inject
    private AreaChecker areaChecker;


    public Points addPoint(Points point){
        point.setResult(areaChecker.isInArea(point));
        entityManager.getTransaction().begin();
        entityManager.merge(point);
        entityManager.getTransaction().commit();
        return point;
    }

    public int getPointsCount(){
        entityManager.getTransaction().begin();
        int k = entityManager.createQuery("select count(*) from web.model.Points", Number.class).getSingleResult().intValue();
        entityManager.getTransaction().commit();
        return k;
    }

    public List<Points> getPoints(){
        int firstResult = Math.max(getPointsCount() - 20, 0);
        entityManager.getTransaction().begin();
        List<Points> list = entityManager.createQuery("select point from web.model.Points point", Points.class).
                setFirstResult(firstResult).setMaxResults(20).getResultList();
        entityManager.getTransaction().commit();
        Collections.reverse(list);
        return list;
    }

    public String getAllCoordinates(){
        List<Points> list = getPoints();
        StringBuilder s = new StringBuilder();
        for(Points p:list){
            s.append(p.toString());
            s.append("/");
        }
        return s.toString();
    }

    public void addPointFromJs(){
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        double x = Double.parseDouble(map.get("x"));
        double y = Double.parseDouble(map.get("y"));
        double r = Double.parseDouble(map.get("r"));
        Points point = new Points(x,y,r);
        addPoint(point);
    }
}
