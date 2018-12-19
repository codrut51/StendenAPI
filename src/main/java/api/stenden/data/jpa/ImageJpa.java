package api.stenden.data.jpa;

import api.stenden.data.Image;
import api.stenden.data.ImageRepository;
import api.stenden.data.model.ImageAnnotated;
import api.stenden.data.model.ImagePOJO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ImageJpa implements ImageRepository {

//    private SessionFactory sessionFactory;
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

//    @Autowired
//    public ImageJpa(SessionFactory sessionFactory)
//    {
//        this.sessionFactory = sessionFactory;
//    }
    @Override
    @Transactional
    public Image getById(Long id) {
//        Session sess = sessionFactory.getCurrentSession();
//        ImageAnnotated imageAnnotated = sess.load(ImageAnnotated.class, id);
        System.out.println("Here I am!!");
        System.out.println(em.isOpen());
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<ImageAnnotated> query = criteriaBuilder.createQuery(ImageAnnotated.class);
        Root<ImageAnnotated> from = query.from(ImageAnnotated.class);
        ParameterExpression<Long> parameter = criteriaBuilder.parameter(Long.class);
        query.select(from).where(criteriaBuilder.equal(from.get("id"), parameter));

        return em.createQuery(query).setParameter(parameter, id).getSingleResult();
//        return imageAnnotated;
    }

    @Override
    @Transactional
    public List<Image> getImages() {
//        System.out.println(sessionFactory);
//        Session sess = sessionFactory.getCurrentSession();
//        List<Image> images = sess.createQuery("from images").list();

        return null;
    }
}
