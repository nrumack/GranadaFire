package com.example.granadafire.entities;

import jakarta.persistence.*;

//Created by Nicholas Rumack

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="player_id")
    private int id;

    @Column(name = "first_name", length = 40, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "nickname", length = 40, nullable = false)
    private String nickname;

    @Column(name = "street", length = (75), nullable = true)
    private String street;

    @Column(name = "post_number", length = 6, nullable = true)
    private int postNumber;

    @Column(name = "city", length = 60, nullable = true)
    private String city;

    @Column(name = "country", length = 50, nullable = true)
    private String country;

    @Column(name = "email", length = 50, nullable = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "team_id", nullable = true)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id", nullable = true)
    private Game game;


    public Player() {

    }

    public Player(String firstName, String lastName, String nickname, String street, int postNumber, String city, String country, String email, Team team, Game game) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.street = street;
        this.postNumber = postNumber;
        this.city = city;
        this.country = country;
        this.email = email;
        this.team = team;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Team getTeam() {return team;}

    public void setTeam(Team team) {this.team = team;}

    public Game getGame() {return game;}

    public void setGame(Game game) {this.game = game;}
}
