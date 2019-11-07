package com.preving.restapi.sajpapi.model.domains;

import javax.persistence.*;

@Entity
@Table(schema = "GC2006_RELEASE", name = "TM_ENTIDADES")
public class Entities {

    private int id;
    private String name;

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
    @Column(name = "NOMBRE_COMERCIAL", length = 150)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //create a class constructor for dependency injection
    public Entities() {
    }

    public Entities(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
