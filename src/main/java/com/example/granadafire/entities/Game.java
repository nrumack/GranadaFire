package com.example.granadafire.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spel_id")
    private int spel_id;

    @Column(name = "spel_namn", length = 50, nullable = false)
    private String spel_namn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spelare_id")
    private Player spelare;

    public Game(){
    }

    public Game(int spel_id, String spel_namn, Player spelare) {
        this.spel_id = spel_id;
        this.spel_namn = spel_namn;
        this.spelare = spelare;
    }
    public int getSpel_id() {
        return spel_id;
    }

    public void setSpel_id(int spel_id) {
        this.spel_id = spel_id;
    }

    public String getSpel_namn() {
        return spel_namn;
    }

    public void setSpel_namn(String spel_namn) {
        this.spel_namn = spel_namn;
    }

    public Player getSpelare() {
        return spelare;
    }

    public void setSpelare(Player spelare) {
        this.spelare = spelare;
    }

}