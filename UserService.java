package com.csueb.util;

import com.csueb.bean.User;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public List<User> getUsers(){
    	EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();
    	em.getTransaction().begin();
        List<User> users = em.createQuery("Select u From User u",User.class).getResultList();
        em.getTransaction().commit();
        return users;
    }
    
    public void create(User user){
    	EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }
    
    public void delete(Integer custId){
    	EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();

        em.getTransaction().begin();
        User localUser = em.find(User.class, custId);
        em.remove(localUser);
        em.getTransaction().commit();
    }
    
    public void update(User user){
    	EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();

        em.getTransaction().begin();
        User localUser = em.find(User.class, user.getCustID());
        localUser.setPassword(user.getPassword());
        //em.persist(user);
        em.getTransaction().commit();
    }
    
    public User validate(String email, String password){
    	EntityManager em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();
    	em.getTransaction().begin();
    	String queryString = "Select u from User u where u.email = :email";
    	List<User> userList = em.createQuery(queryString).setParameter("email", email).getResultList();
    	//.getSingleResult();
        //System.out.println(arg0);
        em.getTransaction().commit();
        if (userList.isEmpty()) {
        	return null;
        } else {
        	User localUser = userList.get(0);
        	System.out.println("FName: " + localUser.getFirstname() + "LName" + localUser.getLastname());
        	if (localUser.getPassword().equals(password)) {
        		return localUser;
        	} else {
        		return null;
        	}
        }
    }
}
