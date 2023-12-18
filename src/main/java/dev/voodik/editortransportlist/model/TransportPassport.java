package dev.voodik.editortransportlist.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "PTS")
@Data
public class TransportPassport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UIN")
    private Integer uniqueNumber;

    @Column(name = "UNTS", nullable = false)
    private Integer referenceNumber;

    @Column(name = "TID", nullable = false)
    private Integer typeId;

    @Column(name = "FIRMID", nullable = false)
    private Integer firmId;

    @Column(name = "GRP", nullable = false)
    private Integer loadCapacity;

    @Column(name = "NORMT", nullable = false)
    private Integer normFuelConsumption;

    @Column(name = "DATASP")
    private String dateOfDebit;

    @ManyToOne
    @JoinColumn(name = "TID", insertable = false, updatable = false)
    private TransportType transportType;

    @ManyToOne
    @JoinColumn(name = "FIRMID", insertable = false, updatable = false)
    private Firm v_firm;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportPassport that = (TransportPassport) o;
        return uniqueNumber.equals(that.uniqueNumber) && referenceNumber.equals(that.referenceNumber) && typeId.equals(that.typeId) && firmId.equals(that.firmId) && loadCapacity.equals(that.loadCapacity) && normFuelConsumption.equals(that.normFuelConsumption) && Objects.equals(dateOfDebit, that.dateOfDebit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueNumber, referenceNumber, typeId, firmId, loadCapacity, normFuelConsumption, dateOfDebit);
    }
}
