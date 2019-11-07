package com.preving.restapi.sajpapi.model.domains;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "VIG_SALUD", name = "PROVINCIAS")
public class Region implements Serializable {

    private int id;
    private String name;

    //create the get and set methods with the definition gonna have in the data base
    @Id
    @Column(name = "PRV_COD", nullable = false, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PRV_NOMBRE", length = 100, nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //create a class constructor for dependency injection
    public Region() {
    }

    public Region(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
