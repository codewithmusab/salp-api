package com.preving.restapi.sajpapi.model.domains;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "CRM", name = "CONTACTOS")
public class Contact implements Serializable {

    private int id;
    private int customerId;
    private String name;
    private String mail1;
    private String mail2;

    //create the get and set methods with the definition gonna have in the data base
    @Id
    @Column(name = "ID", nullable = false, precision = 0)
    @SequenceGenerator(name = "CONTACTOS_SEQ", sequenceName = "CONTACTOS_SEQ", schema="CRM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACTOS_SEQ")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CLIENTE_ID", nullable = false)
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "CONTACTO", length = 150)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "EMAIL", length = 80)
    public String getMail1() {
        return mail1;
    }
    public void setMail1(String mail1) {
        this.mail1 = mail1;
    }

    @Basic
    @Column(name = "EMAIL2", length = 80)
    public String getMail2() {
        return mail2;
    }
    public void setMail2(String mail2) {
        this.mail2 = mail2;
    }


    //create a class constructor for dependency injection
    public Contact() {
    }

    public Contact(int id, int customerId, String name, String mail1, String mail2) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
        this.mail1 = mail1;
        this.mail2 = mail2;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", name='" + name + '\'' +
                ", mail1='" + mail1 + '\'' +
                ", mail2='" + mail2 + '\'' +
                '}';
    }
}
