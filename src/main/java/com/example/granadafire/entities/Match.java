package com.example.granadafire.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Created by Martin

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;

    @Column(name = "winner", length = 20)
    private String winner;

    @ManyToOne
    @JoinColumn(name = "player1_id", referencedColumnName = "id")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", referencedColumnName = "id")
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "team1_id", referencedColumnName = "id")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id", referencedColumnName = "id")
    private Team team2;

    @Column(nullable = false)
    private boolean isCompleted;

    public Match() {
    }

    public Match(LocalDateTime matchDate, Player player1, Player player2, Team team1, Team team2, boolean isCompleted) {
        this.matchDate = matchDate;
        this.player1 = player1;
        this.player2 = player2;
        this.team1 = team1;
        this.team2 = team2;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return matchDate;
    }

    public void setDate(LocalDateTime date) {
        this.matchDate = date;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

}
