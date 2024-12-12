package com.example.granadafire.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

// Created by Martin
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Player> players;

    @ManyToOne
    @JoinTable(name = "game_id")
    private Game game;

    public Team() {}

    public Team(String name, Game game, List<Player> players) {
        this.name = name;
        this.game = game;
        this.players = players;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
