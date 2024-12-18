package com.example.granadafire.DAO;

import com.example.granadafire.entities.Game;
import com.example.granadafire.entities.Player;
import com.example.granadafire.entities.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//Created by Nicholas Rumack

public class PlayerDAO {
    //CRUD-operations
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");
    private EntityManager entityManager;

    //Create
    public boolean savePlayer(Player player) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(player);
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
    public Player getPlayerById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Player playerToReturn = entityManager.find(Player.class, id);
        entityManager.close();
        return playerToReturn;
    }

    public List<Player> getAllPlayers() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Player> query = entityManager.createQuery("SELECT p FROM Player p", Player.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Player> getPlayersByGames(List<Game> games) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Player> query = entityManager.createQuery(
                    "SELECT p FROM Player p WHERE p.game IN :games", Player.class
            );
            query.setParameter("games", games);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Player> getPlayersByName(String name) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            TypedQuery<Player> query = entityManager.createQuery(
                    "SELECT p FROM Player p WHERE LOWER(p.firstName) LIKE :name OR LOWER(p.lastName) LIKE :name", Player.class
            );
            query.setParameter("name", "%" + name.toLowerCase() + "%");
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    //Update
    public void updatePlayer(Player playerToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Player existingPlayer = entityManager.find(Player.class, playerToUpdate.getId());
            if (existingPlayer != null) {
                existingPlayer.setFirstName(playerToUpdate.getFirstName());
                existingPlayer.setLastName(playerToUpdate.getLastName());
                existingPlayer.setNickname(playerToUpdate.getNickname());
                existingPlayer.setStreet(playerToUpdate.getStreet());
                existingPlayer.setPostNumber(playerToUpdate.getPostNumber());
                existingPlayer.setCity(playerToUpdate.getCity());
                existingPlayer.setCountry(playerToUpdate.getCountry());
                existingPlayer.setEmail(playerToUpdate.getEmail());
                existingPlayer.setTeam(playerToUpdate.getTeam());
                existingPlayer.setGame(playerToUpdate.getGame());

                entityManager.merge(existingPlayer);
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

    //Delete
    public boolean deletePlayer(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Player player = entityManager.find(Player.class, id);
            if (player != null) {
                entityManager.remove(player);
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

    public void deletePlayersByGame(int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Player p WHERE p.team.game.id = :gameId")
                .setParameter("gameId", gameId)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
