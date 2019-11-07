package com.preving.restapi.sajpapi.model.domains;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "GC2006_RELEASE", name = "PC_USUARIOS")
public class Commercial implements Serializable {

    private int id;
    private String name;
    private String surname;

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
    @Column(name = "APELLIDOS", length = 100)
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }



    //create a class constructor for dependency injection
    public Commercial() {
    }

    public Commercial(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}
