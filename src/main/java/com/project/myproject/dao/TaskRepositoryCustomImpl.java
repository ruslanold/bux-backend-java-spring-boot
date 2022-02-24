package com.project.myproject.dao;

import com.project.myproject.entity.Task;
import com.project.myproject.enums.ECountry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TaskRepositoryCustomImpl implements TaskRepositoryCustom{
//    @PersistenceContext
//    private EntityManager entityManager;


     @Override
     public List<Task> findAllByGeoFilterByCountriesAndNotExistsUsername(ECountry country, String username) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<User> query = cb.createQuery(User.class);
//        Root<User> user = query.from(User.class);
//
//        Path<String> emailPath = user.get("email");
//
//        List<Predicate> predicates = new ArrayList<>();
//        for (String email : emails) {
//            predicates.add(cb.like(emailPath, email));
//        }
//        query.select(user)
//                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        //return entityManager.createQuery(query).getResultList();
        return null;
    }
}
