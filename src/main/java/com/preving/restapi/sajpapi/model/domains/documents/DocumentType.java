package com.preving.restapi.sajpapi.model.domains.documents;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "SAJP", name = "TM_DOCUMENTOS_TIPOS")
public class DocumentType implements Serializable {

    private int id;
    private String denomination;
    private boolean active;
    private String description;

    public DocumentType() {
    }

    public DocumentType(int id, String denomination, boolean active, String description) {
        this.id = id;
        this.denomination = denomination;
        this.active = active;
        this.description = description;
    }

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Basic
    @Column(name = "DENOMINACION", length = 150, nullable = false)
    public String getDenomination() { return denomination; }
    public void setDenomination(String denomination) { this.denomination = denomination; }

    @Basic
    @Column(name = "ACTIVO")
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Basic
    @Column(name = "DESCRIPCION", length = 128, nullable = false)
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "DocumentType{" +
                "id=" + id +
                ", denomination='" + denomination + '\'' +
                ", active=" + active +
                ", description='" + description + '\'' +
                '}';
    }

}
