package com.example.granadafire.DAO;

import com.example.granadafire.entities.Game;
import com.example.granadafire.entities.Match;
import com.example.granadafire.entities.Player;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // Create
    public boolean createGame(Game game) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(game);
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

    // Read
    public Game getGameByName(String name) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            TypedQuery<Game> query = entityManager.createQuery(
                    "SELECT g FROM Game g WHERE g.gameName = :name", Game.class
            );
            query.setParameter("name", name);
            List<Game> result = query.getResultList();
            if (!result.isEmpty()) {
                return result.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Game> getAllGames() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Game g", Game.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Game> getGamesByName (String name) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Game g WHERE LOWER(g.gameName) Like :name OR LOWER(g.gameName) LIKE :name", Game.class);
            query.setParameter("name", "%" + name.toLowerCase() + "%");
        return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    // Update
    public boolean updateGame(Game game) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(game);
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

    // Delete
    public boolean deleteGame(Game game) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Game gameToDelete = entityManager.find(Game.class, game.getGameId());

            if (gameToDelete != null) {
                List<Player> players = entityManager.createQuery("FROM Player p Where p.game = :game", Player.class).setParameter("game", gameToDelete).getResultList();
                for (Player player : players) {
                    player.setGame(null);
                    entityManager.merge(player);
                }
//                for (Match match : matches) {
//                    match.setGame(Null);
//                    entityManager.merge(match);
//                }
                game = entityManager.merge(game);
                entityManager.remove(game);
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
        finally {
            entityManager.close();
        }
    }
}