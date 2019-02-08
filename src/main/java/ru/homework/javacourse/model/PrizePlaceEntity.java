package ru.homework.javacourse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PrizePlace")
public class PrizePlaceEntity {

    @Id
    @GeneratedValue
    private Long idPrizePlace;

    @JsonProperty("PrizePlace")
    private int prizePlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idTournament", nullable=false)
    @JsonIgnore
    private TournamentEntity tournament;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idPlayer", nullable=false)
    @JsonIgnore
    private PlayerEntity player;

    public PrizePlaceEntity() {

    }

    public PrizePlaceEntity(int prizePlace, TournamentEntity tournament, PlayerEntity player) {
        this.prizePlace = prizePlace;
        this.tournament = tournament;
        this.player = player;
    }

    public Long getIdPrizePlace() {
        return idPrizePlace;
    }

    public void setIdPrizePlace(Long idPrizePlace) {
        this.idPrizePlace = idPrizePlace;
    }

    public int getPrizePlace() {
        return prizePlace;
    }

    public void setPrizePlace(int prizePlace) {
        this.prizePlace = prizePlace;
    }

    public TournamentEntity getTournament() {
        return tournament;
    }

    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrizePlaceEntity that = (PrizePlaceEntity) o;
        return prizePlace == that.prizePlace &&
                Objects.equals(idPrizePlace, that.idPrizePlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrizePlace, prizePlace);
    }

    @Override
    public String toString() {
        return "PrizePlaceEntity{" +
                "idPrizePlace=" + idPrizePlace +
                ", prizePlace=" + prizePlace +
                '}';
    }
}
