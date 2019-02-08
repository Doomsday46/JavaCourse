package ru.homework.javacourse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Game")
public class GameEntity {

    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("Score")
    private Integer score;

    @JsonProperty("State")
    private Boolean state;

    @JsonProperty("DateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Game_has_Player",
            joinColumns = { @JoinColumn(name = "idGame") },
            inverseJoinColumns = { @JoinColumn(name = "idPlayer") }
    )
    @JsonIgnore
    private Set<PlayerEntity> players = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idLocation", nullable=false)
    @JsonIgnore
    private LocationEntity location;

    public GameEntity() {

    }

    public GameEntity(Integer score, Boolean state, Date datetime, Set<PlayerEntity> players, LocationEntity location) {
        this.score = score;
        this.state = state;
        this.datetime = datetime;
        this.players = players;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Set<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(Set<PlayerEntity> players) {
        this.players = players;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEntity that = (GameEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(score, that.score) &&
                Objects.equals(state, that.state) &&
                Objects.equals(datetime, that.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, state, datetime);
    }

    @Override
    public String toString() {
        return "GameEntity{" +
                "id=" + id +
                ", score=" + score +
                ", state=" + state +
                ", datetime=" + datetime +
                '}';
    }
}
