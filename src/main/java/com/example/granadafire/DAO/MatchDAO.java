package com.example.granadafire.DAO;

import com.example.granadafire.entities.Match;
import jakarta.persistence.*;

import java.util.List;
// Created by Martin
public class MatchDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    public void addMatch(Match match) {

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(match);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error adding match: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Match> getAllMatches() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            TypedQuery<Match> query = em.createQuery("SELECT m FROM Match m", Match.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error retrieving matches: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public void updateMatch(Match match) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(match);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error updating match: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void deleteMatchById(int matchId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Match match = entityManager.find(Match.class, matchId);
            if (match != null) {
                entityManager.remove(match);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void deleteMatchesByGame(int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Match m WHERE m.team1.game.id = :gameId OR m.team2.game.id = :gameId")
                .setParameter("gameId", gameId)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

