package ru.homework.javacourse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Player")
public class PlayerEntity {

    @Id
    @GeneratedValue
    private Long idPlayer;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("SecondName")
    private String secondName;

    @JsonProperty("Age")
    @Temporal(TemporalType.TIMESTAMP)
    private Date age;

    @OneToMany(mappedBy="player", targetEntity=PrizePlaceEntity.class)
    private Set<PrizePlaceEntity> prizePlaceEntities = new HashSet<>();

    @ManyToMany(mappedBy = "players", targetEntity=GameEntity.class)
    private Set<GameEntity> games = new HashSet<>();

    public PlayerEntity() {

    }

    public PlayerEntity(String firstName, String secondName, Date age, Set<PrizePlaceEntity> prizePlaceEntities, Set<GameEntity> games) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.prizePlaceEntities = prizePlaceEntities;
        this.games = games;
    }

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

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public Set<PrizePlaceEntity> getPrizePlaceEntities() {
        return prizePlaceEntities;
    }

    public void setPrizePlaceEntities(Set<PrizePlaceEntity> prizePlaceEntities) {
        this.prizePlaceEntities = prizePlaceEntities;
    }

    public Set<GameEntity> getGames() {
        return games;
    }

    public void setGames(Set<GameEntity> games) {
        this.games = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerEntity that = (PlayerEntity) o;
        return Objects.equals(idPlayer, that.idPlayer) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(secondName, that.secondName) &&
                Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlayer, firstName, secondName, age);
    }

    @Override
    public String toString() {
        return "PlayerEntity{" +
                "idPlayer=" + idPlayer +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                '}';
    }
}
