package com.example.granadafire.DAO;

import com.example.granadafire.entities.Player;
import com.example.granadafire.entities.Staff;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//Created by Nicholas Rumack

public class StaffDAO {
    //CRUD-operations
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");
    private EntityManager entityManager;

    //Create
    public boolean saveStaff(Staff staff) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(staff);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    //Read
    public Staff getStaffById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Staff staffToReturn = entityManager.find(Staff.class, id);
        entityManager.close();
        return staffToReturn;
    }

    public List<Staff> getAllStaff() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Staff> listToReturn = new ArrayList<>();
        TypedQuery<Staff> result = entityManager.createQuery("FROM Staff", Staff.class);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    public List<Staff> getStaffByName (String name) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Staff> query = entityManager.createQuery("SELECT s FROM Staff s WHERE LOWER(s.firstName) Like :name OR LOWER(s.firstName) LIKE :name", Staff.class);
            query.setParameter("name", "%" + name.toLowerCase() + "%");
        return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    //Update
    public void updateStaff(Staff staffToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Staff existingStaff = entityManager.find(Staff.class, staffToUpdate.getId());
            if (existingStaff != null) {
                existingStaff.setFirstName(staffToUpdate.getFirstName());
                existingStaff.setLastName(staffToUpdate.getLastName());
                existingStaff.setNickname(staffToUpdate.getNickname());
                existingStaff.setStreet(staffToUpdate.getStreet());
                existingStaff.setPostNumber(staffToUpdate.getPostNumber());
                existingStaff.setCity(staffToUpdate.getCity());
                existingStaff.setCountry(staffToUpdate.getCountry());
                existingStaff.setEmail(staffToUpdate.getEmail());

                entityManager.merge(existingStaff);
            }

            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    //Delete
    public void deleteStaff(Staff staff) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(staff)) {
                staff = entityManager.merge(staff);
                System.out.println(staff.getId());
            }
            entityManager.remove(staff);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }
}
