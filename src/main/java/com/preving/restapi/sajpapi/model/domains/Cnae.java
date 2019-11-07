package com.preving.restapi.sajpapi.model.domains;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "GC2006_RELEASE", name = "PC_CNAES")
public class Cnae implements Serializable {

    private int id;
    private String cnae;
    private String description;

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
    @Column(name = "CNAE", length = 10)
    public String getCnae() {
        return cnae;
    }
    public void setCnae(String cnae) {
        this.cnae = cnae;
    }

    @Basic
    @Column(name = "DESCRIPCION", length = 500)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    //create a class constructor for dependency injection
    public Cnae() {
    }

    public Cnae(int id, String cnae, String description) {
        this.id = id;
        this.cnae = cnae;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Cnae{" +
                "id=" + id +
                ", cnae='" + cnae + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
