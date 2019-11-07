package com.preving.restapi.sajpapi.model.domains.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PC_USUARIOS", schema = "GC2006_RELEASE")
public class User {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "USUARIO", length = 50, unique = true)
    @NotNull
    @JsonIgnore
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "SALTO", length = 100)
    @NotNull
    @JsonIgnore
    @Size(min = 4, max = 100)
    private String salto;

    @Column(name = "HASHEDPWD", length = 100)
    @NotNull
    @JsonIgnore
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "NOMBRE", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String firstname;

    @Column(name = "APELLIDOS", length = 200)
    @NotNull
    @Size(min = 4, max = 50)
    private String lastname;

    @Column(name = "EMAIL", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "ACTIVO")
    @NotNull
    private boolean enabled;

    @Column(name = "FCH_UPDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "PC_USUARIOSGRUPOS", schema = "GC2006_RELEASE",
            joinColumns = {@JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "GRUPO_ID", referencedColumnName = "ID")})
    private List<Authority> authorities;

    public User(){
    }

    public User(Long id, @NotNull @Size(min = 4, max = 50) String username, @NotNull @Size(min = 4, max = 100) String salto, @NotNull @Size(min = 4, max = 100) String password, @NotNull @Size(min = 4, max = 100) String firstname, @NotNull @Size(min = 4, max = 50) String lastname, @NotNull @Size(min = 4, max = 50) String email, @NotNull boolean enabled, @NotNull Date lastPasswordResetDate) {
        this.id = id;
        this.username = username;
        this.salto = salto;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalto() {
        return salto;
    }

    public void setSalto(String salto) {
        this.salto = salto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public User (String salto, String password){
        this.salto = salto;
        this.password = password;
    }
}
