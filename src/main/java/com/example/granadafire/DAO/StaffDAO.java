package com.example.granadafire.DAO;

import com.example.granadafire.entities.Staff;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//Created by Nicholas Rumack

public class StaffDAO {
    //CRUD-operations
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfiq");
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

    //Update
    public void updateStaff(Staff staffToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            if (entityManager.contains(staffToUpdate)) {
                System.out.println("Staff exists ");
                entityManager.persist(staffToUpdate);
            } else {
                System.out.println("Staff does not exist");
                Staff recievedStaff = entityManager.merge(staffToUpdate);
                System.out.println(recievedStaff.getId() + " is active");
            }
            entityManager.merge(staffToUpdate);
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
