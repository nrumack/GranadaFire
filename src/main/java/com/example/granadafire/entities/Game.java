package com.example.granadafire.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int game_id;

    @Column(name = "game_name", length = 50, nullable = true)
    private String game_name;

    public Game(){
    }

    public Game(String game_name, Player player) {
        this.game_name = game_name;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

}