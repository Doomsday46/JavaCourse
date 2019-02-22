package ru.homework.javacourse.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tournaments")
public class Tournament {
    private Long idTournament;
    private String name;
    private Date beginDate;
    private User assignedTo;
    private Set<Game> games;
    private Set<Player> players;
    private Set<Location> locations;

    public Tournament() {

    }

    public Tournament(String name, Date beginDate) {
        this.name = name;
        this.beginDate = beginDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(Long idTournament) {
        this.idTournament = idTournament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return Objects.equals(idTournament, that.idTournament);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTournament);
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "idTournament=" + idTournament +
                ", name='" + name + '\'' +
                ", beginDate=" + beginDate +
                '}';
    }
}
