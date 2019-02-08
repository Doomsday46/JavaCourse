package ru.homework.javacourse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Tournament")
public class TournamentEntity {

    @Id
    @GeneratedValue
    private Long idTournament;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("BeginDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDate;

    @OneToMany(mappedBy="tournament", targetEntity=PrizePlaceEntity.class)
    private Set<PrizePlaceEntity> prizePlaceEntities = new HashSet<>();

    public TournamentEntity() {

    }

    public TournamentEntity(String name, Date beginDate, Set<PrizePlaceEntity> prizePlaceEntities) {
        this.name = name;
        this.beginDate = beginDate;
        this.prizePlaceEntities = prizePlaceEntities;
    }

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

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Set<PrizePlaceEntity> getPrizePlaceEntities() {
        return prizePlaceEntities;
    }

    public void setPrizePlaceEntities(Set<PrizePlaceEntity> prizePlaceEntities) {
        this.prizePlaceEntities = prizePlaceEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentEntity that = (TournamentEntity) o;
        return Objects.equals(idTournament, that.idTournament) &&
                Objects.equals(name, that.name) &&
                Objects.equals(beginDate, that.beginDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTournament, name, beginDate);
    }

    @Override
    public String toString() {
        return "TournamentEntity{" +
                "idTournament=" + idTournament +
                ", name='" + name + '\'' +
                ", beginDate=" + beginDate +
                '}';
    }
}
