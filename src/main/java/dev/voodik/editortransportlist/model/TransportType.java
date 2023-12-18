package dev.voodik.editortransportlist.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TIPTR")
@Data
public class TransportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TID")
    private Integer id;

    @Column(name = "TNAME", nullable = false)
    private String name;

    @Column(name = "VIDT", nullable = false)
    private Integer vidId;

    @Column(name = "PRIZNGR")
    private String attribute;

    @ManyToOne
    @JoinColumn(name = "VIDT", insertable = false, updatable = false)
    private TransportVid transportVid;
}
