package com.xworkz.userapp.repository;

import com.xworkz.userapp.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository{
    // database logic
    @Autowired
    EntityManagerFactory emf; //Persistence.createEntityManagerFactory("springMvc")

    @Override
    @Transactional
    public Boolean save(UserEntity entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            log.warn(e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
        finally {
            em.close();
        }
    }

    @Override
    public UserEntity fetchPasswordByEmail(String email) {
        EntityManager entityManager = emf.createEntityManager();
        return (UserEntity) entityManager.createNamedQuery("getPasswordByEmail")
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    @Transactional
    public UserEntity getUserByEmail(String email) {
        log.info("Repo getUserByEmail startedEmail: " + email);
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity userEntity = em.createNamedQuery("getUsersByEmail", UserEntity.class)
                    .setParameter("emailId",email)
                    .getSingleResult();
            log.info("Repo is sending: {}", userEntity);
            log.info("repo getUserByEmail ended");
            return userEntity;
        } catch (NoResultException e) {
            log.info("No user found for email:{}", email);
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Boolean existByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("Checking email in repo: [" + email + "]"); // Debugging

            Long count = em.createNamedQuery("emailExist", Long.class)
                    .setParameter("email", email.trim()) // Ensure no spaces
                    .getSingleResult();

            System.out.println("Email count from repo: " + count);
            return count > 0;
        } catch (Exception e) {
            log.warn("Error checking email existence: {}", e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public Boolean existByPhNumber(String phNumber) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createNamedQuery("phExist", Long.class)
                    .setParameter("phNumber", phNumber)
                    .getSingleResult();
            return count > 0; // Return true if phone number exists, false otherwise
        } catch (Exception e) {
            log.warn("Error checking phone number existence:{}", e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updateUser(UserEntity userEntity) {
        EntityManager em = emf.createEntityManager();
        boolean isUpdated = false;
        try {
            em.getTransaction().begin();
            // Check if the entity exists
            UserEntity existingEntity = em.find(UserEntity.class, userEntity.getId());
            if (existingEntity != null) { // Only update if it exists
                em.merge(userEntity);
                isUpdated = true;
            }
            em.getTransaction().commit();
            return isUpdated;
        } catch (Exception e) {
            log.warn(e.getMessage());
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    @Transactional
    public boolean updateProfile(UserEntity updatedEntity) {
        EntityManager em = emf.createEntityManager();
        boolean isUpdated = false;
        try {
            em.getTransaction().begin();
            // Fetch the existing entity
            UserEntity existingEntity = em.find(UserEntity.class, updatedEntity.getId());
            if (existingEntity != null) {
                // Update all fields
                existingEntity.setName(updatedEntity.getName());
                existingEntity.setPhNumber(updatedEntity.getPhNumber());
                existingEntity.setAge(updatedEntity.getAge());
                existingEntity.setGender(updatedEntity.getGender());
                existingEntity.setLocation(updatedEntity.getLocation());
                existingEntity.setPwd(updatedEntity.getPwd());
                existingEntity.setProfileData(updatedEntity.getProfileData());

                existingEntity.setInvalidLogInCount(updatedEntity.getInvalidLogInCount());
                existingEntity.setAccountLocked(updatedEntity.isAccountLocked());
                existingEntity.setLastLogIn(updatedEntity.getLastLogIn());

                em.merge(existingEntity);
                //Set updatedBy and updatedTime AFTER the merge is successful
                existingEntity.setUpdateBy(updatedEntity.getName());
                existingEntity.setUpdatedTime(LocalDateTime.now());

                // Merge again to save updatedBy and updatedTime
                em.merge(existingEntity);

                isUpdated = true;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            log.warn("Error updating profile: {}", e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
        return isUpdated;
    }

    @Override
    public void deleteByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNamedQuery("deleteUserByEmail").setParameter("email",email).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public UserEntity getUserById(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        UserEntity userEntity = (UserEntity) em.createNamedQuery("getUserById").setParameter("id", id).getSingleResult();
        log.info("User entity retrieved successfully: {}", userEntity);
        em.getTransaction().commit();
        em.close();
        return userEntity;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createNamedQuery("getAllUsersQuery").getResultList();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                log.warn(e.getMessage());
            }
            return null;
        }finally {
            em.close();
        }
    }

    @Override
    public void deleteById(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNamedQuery("deleteUser").setParameter("id",id).executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public long getNameCount(String name) {
        System.out.println("invoking getNameCount in the repo"+name);
        EntityManager entityManager = emf.createEntityManager();
//        EntityTransaction entityTransaction = entityManager.getTransaction();
        long count;
        try{
//            System.out.println(" invoking in the try...............");
            Query query = entityManager.createNamedQuery("getNameCount").setParameter("setName", name);
            count = (long)query.getSingleResult();
            System.out.println("count: " + count);
            return count;
        }catch(Exception e){
            System.out.println("getting exception in getNameCount ...."+e.getMessage());
            return 0;
        }finally {
            entityManager.close();
        }
    }

}
