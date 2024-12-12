package com.example.granadafire.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private int matchId;

    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;

    @Column(name = "match_result", length = 10)
    private String matchResult;

    @ManyToOne
    @JoinColumn(name = "player_id1", referencedColumnName = "player_id")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player_id2", referencedColumnName = "player_id")
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "team_id1", referencedColumnName = "team_id")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team_id2", referencedColumnName = "team_id")
    private Team team2;

    public Match() {}

    public Match(LocalDateTime matchDate, String matchResult, Player player1, Player player2, Team team1, Team team2) {
        this.matchDate = matchDate;
        this.matchResult = matchResult;
        this.player1 = player1;
        this.player2 = player2;
        this.team1 = team1;
        this.team2 = team2;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
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
}

