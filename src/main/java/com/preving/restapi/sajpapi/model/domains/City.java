package com.preving.restapi.sajpapi.model.domains;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "VIG_SALUD", name = "LOCALIDADES")
public class City implements Serializable {

    private int id;
    private String name;
    private Region prvCod;

    //create the get and set methods with the definition gonna have in the data base
    @Id
    @Column(name = "LOC_ID", nullable = false, precision = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "LOC_NOMBRE", length = 100)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //create the relationship between PROVINCIAS and LOCALIDADES tables join the columns of each tables
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOC_PRV_COD", referencedColumnName = "PRV_COD", nullable = false)
    public Region getPrvCod() {
        return prvCod;
    }
    public void setPrvCod(Region prvCod) {
        this.prvCod = prvCod;
    }

    //create a class constructor for dependency injection
    public City() {
    }

    public City(int id, String name, Region prvCod) {
        this.id = id;
        this.name = name;
        this.prvCod = prvCod;
    }
}
