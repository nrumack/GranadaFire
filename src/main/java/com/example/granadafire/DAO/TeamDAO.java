package com.example.granadafire.DAO;

import com.example.granadafire.entities.Team;
import jakarta.persistence.*;

import java.util.List;
// Created by Martin
public class TeamDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    public boolean saveTeam(Team team) {

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(team);
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

    public Team getTeamById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            return entityManager.find(Team.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<Team> getAllTeams() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            TypedQuery<Team> result = entityManager.createQuery("FROM Team", Team.class);
            return result.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    public boolean updateTeam(Team team) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(team);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    public boolean deleteTeam(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Team team = entityManager.find(Team.class, id);
            if (team != null) {
                entityManager.remove(team);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

}
