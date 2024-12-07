package com.example.granadafire.DAO;

import com.example.granadafire.entities.Player;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    //CRUD-operations
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfiq");
    private EntityManager entityManager;

    //Create
    public boolean savePerson(Player player) {
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
        List<Player> listToReturn = new ArrayList<>();
        TypedQuery<Player> result = entityManager.createQuery("FROM Player", Player.class);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    //Update
    public void updatePlayer(Player playerToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            if (entityManager.contains(playerToUpdate)) {
                System.out.println("Player exists ");
                entityManager.persist(playerToUpdate);
            } else {
                System.out.println("Player does not exist");
                Player recievedPlayer = entityManager.merge(playerToUpdate);
                System.out.println(recievedPlayer.getId() + " is active");
            }
            entityManager.merge(playerToUpdate);
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
    public void deletePLayer(Player player) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(player)) {
                player = entityManager.merge(player);
                System.out.println(player.getId());
            }
            entityManager.remove(player);
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
