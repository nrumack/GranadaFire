package com.example.granadafire.entities;

import com.example.granadafire.DAO.PlayerDAO;
import com.example.granadafire.DAO.TeamDAO;
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

    @Column(name = "match_result")
    private String result;

    @ManyToOne
    @JoinColumn(name = "player_id1")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player_id2")
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "team_id1")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team_id2")
    private Team team2;

    @Column(name = "score1", nullable = true)
    private Integer score1;

    @Column(name = "score2", nullable = true)
    private Integer score2;


    public int getMatchId() {
        return matchId;
    }

    public LocalDateTime getMatchDateRaw() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
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

    public String getParticipant1Name() {
        if (team1 != null) {
            return team1.getTeamName();
        } else if (player1 != null) {
            return player1.getFirstName() + " " + player1.getLastName();
        }
        return "N/A";
    }

    public String getParticipant2Name() {
        if (team2 != null) {
            return team2.getTeamName();
        } else if (player2 != null) {
            return player2.getFirstName() + " " + player2.getLastName();
        }
        return "N/A";
    }

    public String getWinnerName() {
        if (score1 == null || score2 == null) {
            return "N/A";
        }
        if (score1 > score2) {
            return team1 != null ? team1.getTeamName() : player1 != null ? player1.getFirstName() + " " + player1.getLastName() : "N/A";
        } else if (score2 > score1) {
            return team2 != null ? team2.getTeamName() : player2 != null ? player2.getFirstName() + " " + player2.getLastName() : "N/A";
        }
        return "Draw";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s vs %s | Result: %s",
                matchDate, getParticipant1Name(), getParticipant2Name(), getResult());
    }
}






