package com.preving.restapi.sajpapi.model.domains.security;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PC_GRUPOS", schema = "GC2006_RELEASE")
public class Authority implements Serializable {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "nombre", length = 50)
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "app_id")
    private Aplicacion aplicacion;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<User> users;

    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}