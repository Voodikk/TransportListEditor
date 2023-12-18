package dev.voodik.editortransportlist.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "V_FIRM")
@Data
public class Firm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FIRMID")
    private Integer id;

    @Column(name = "TLGR")
    private String name;

    @Column(name = "DISLOC")
    private String location;

}
