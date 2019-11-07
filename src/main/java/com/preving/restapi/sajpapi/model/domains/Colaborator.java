package com.preving.restapi.sajpapi.model.domains;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "GC2006_RELEASE", name = "PC_COLABORADOR")
public class Colaborator implements Serializable {

    private int id;
    private String name;
    private String cif;

    //create the get and set methods with the definition gonna have in the data base
    @Id
    @Column(name = "ID", nullable = false, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NOMBRE", length = 100, nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "CIF", length = 20)
    public String getCif() {
        return cif;
    }
    public void setCif(String cif) {
        this.cif = cif;
    }

    //create a class constructor for dependency injection
    public Colaborator() {
    }

    public Colaborator(int id, String name, String cif) {
        this.id = id;
        this.name = name;
        this.cif = cif;
    }
}
