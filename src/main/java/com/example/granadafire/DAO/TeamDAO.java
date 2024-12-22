package com.example.granadafire.DAO;

import com.example.granadafire.entities.Player;
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

    public Team getTeamByName(String name) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Team> query = entityManager.createQuery(
                    "SELECT t FROM Team t WHERE t.teamName = :name", Team.class
            );
            query.setParameter("name", name);
            List<Team> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            entityManager.close();
        }
    }

    public List<Team> getAllTeams() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Team> teams = null;

        try {
            TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t", Team.class);
            teams = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return teams;
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
                for (Player player : team.getPlayers()) {
                    player.setTeam(null);
                    entityManager.merge(player);
                }
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

    public void deleteTeamsByGame(int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Team t WHERE t.game.id = :gameId")
                .setParameter("gameId", gameId)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
