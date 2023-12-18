package dev.voodik.editortransportlist.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "VIDTC")
@Data
public class TransportVid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIDT")
    private Integer id;

    @Column(name = "SHNAME", nullable = false)
    private String shortName;

    @Column(name = "FULLNAME")
    private String fullName;
}

