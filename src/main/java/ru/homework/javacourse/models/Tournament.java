package ru.homework.javacourse.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tournament")
public class Tournament {
    private Long idTournament;
    private String name;
    private Date startTournament;
    private Date endTournament;
    private User assignedTo;

    private Set<Player> players;
    private Set<Location> locations;

    public Tournament() {

    }

    public Tournament(String name) {
        this.name = name;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
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

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    public Date getStartTournament() {
        return startTournament;
    }

    public void setStartTournament(Date startTournament) {
        this.startTournament = startTournament;
    }

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    public Date getEndTournament() {
        return endTournament;
    }

    public void setEndTournament(Date endTournament) {
        this.endTournament = endTournament;
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
                ", name='" + name + '\''+'}';
    }
}
