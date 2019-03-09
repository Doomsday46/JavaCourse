package ru.homework.javacourse.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player {

    private Long idPlayer;
    private String firstName;
    private String secondName;
    private Date age;
    private Tournament tournament;
    private Set<Game> games = new HashSet<>();

    public Player() {

    }

    public Player(String firstName, String secondName, Date age, Tournament tournament) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.tournament = tournament;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @ManyToMany(mappedBy = "players", targetEntity=Game.class)
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player that = (Player) o;
        return Objects.equals(idPlayer, that.idPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlayer);
    }

    @Override
    public String toString() {
        return "Player{" +
                "idPlayer=" + idPlayer +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                '}';
    }
}
