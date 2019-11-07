package com.preving.restapi.sajpapi.model.domains.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.preving.restapi.sajpapi.model.domains.customers.Customer;
import com.preving.restapi.sajpapi.model.domains.security.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

import java.util.Date;

@VisibleCondition(required = "description")
@Entity
@Table(schema = "SAJP", name = "DOCUMENTOS")
public class Document implements Serializable {

    private Integer id;
    private String name;
    private DocumentType documentType = new DocumentType();
    private Customer customer = new Customer();
    private String attachedUrl;
    private String attachedName;
    private String attachedContentType;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date = new Date();
    private boolean visibleExtranet;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date visibleUntil = new Date();
    private Boolean completeTerritory;
    private Boolean completeSectors;
    private String description;
    private String internalObservations;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date created = new Date();
    private User createdBy = new User();
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date modified = new Date();
    private User modifiedBy = new User();
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date deleted = new Date();
    private User deletedBy = new User();

    public Document() {
    }

    public Document(Integer id, String name, DocumentType documentType, Customer customer, String attachedUrl,
                    String attachedName, String attachedContentType, Date date, boolean visibleExtranet,
                    Date visibleUntil, Boolean completeTerritory, Boolean completeSectors, String description,
                    String internalObservations, Date created, User createdBy, Date modified, User modifiedBy,
                    Date deleted, User deletedBy) {
        this.id = id;
        this.name = name;
        this.documentType = documentType;
        this.customer = customer;
        this.attachedUrl = attachedUrl;
        this.attachedName = attachedName;
        this.attachedContentType = attachedContentType;
        this.date = date;
        this.visibleExtranet = visibleExtranet;
        this.visibleUntil = visibleUntil;
        this.completeTerritory = completeTerritory;
        this.completeSectors = completeSectors;
        this.description = description;
        this.internalObservations = internalObservations;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.deleted = deleted;
        this.deletedBy = deletedBy;
    }
    //create the get and set methods with the definition gonna have in the data base
    // Filter/Header getter and setter
    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(name = "DOCUMENTOS_SEQ", sequenceName = "DOCUMENTOS_SEQ", schema = "SAJP", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUMENTOS_SEQ")
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @Basic
    @Column(name = "NOMBRE", length = 128, nullable = false)
    @NotBlank
    @Required
    @Length(min=1, max=128)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Required
    @JoinColumn(name = "TIPO_ID", referencedColumnName = "ID")
    public DocumentType getDocumentType() { return documentType; }
    public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID")
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    @Basic
    @Column(name = "URL_ADJUNTO")
    public String getAttachedUrl() { return attachedUrl; }
    public void setAttachedUrl(String attachedUrl) { this.attachedUrl = attachedUrl; }

    @Basic
    @Column(name = "NOMBRE_ADJUNTO")
    public String getAttachedName() { return attachedName; }
    public void setAttachedName(String attachedName) { this.attachedName = attachedName; }

    @Basic
    @Column(name = "CONTENT_TYPE_ADJUNTO", nullable = false)
    @Pattern(regexp="^(application/msword|application/vnd.openxmlformats-officedocument.wordprocessingml.document|application/vnd.oasis.opendocument.text|application/vnd.ms-excel|application/vnd.openxmlformats-officedocument.spreadsheetml.sheet|application/vnd.oasis.opendocument.spreadsheet|image/jpeg|image/png|application/x-zip-compressed|application/pdf)$")
    public String getAttachedContentType() { return attachedContentType; }
    public void setAttachedContentType(String attachedContentType) { this.attachedContentType = attachedContentType; }

    @Basic
    @Column(name = "FECHA", nullable = false)
    @Required
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @Basic
    @Column(name = "VISIBLE_EXTRANET", nullable = false)
    public boolean isVisibleExtranet() { return visibleExtranet; }
    public void setVisibleExtranet(boolean visibleExtranet) { this.visibleExtranet = visibleExtranet; }

    @Basic
    @Column(name = "VISIBLE_HASTA")
    @FutureOrPresent
    public Date getVisibleUntil() { return visibleUntil; }
    public void setVisibleUntil(Date visibleUntil) { this.visibleUntil = visibleUntil; }

    @Basic
    @Column(name = "TERRITORIO_COMPLETO")
    public Boolean isCompleteTerritory() { return completeTerritory; }
    public void setCompleteTerritory(Boolean completeTerritory) { this.completeTerritory = completeTerritory; }

    @Basic
    @Column(name = "SECTORES_COMPLETO")
    public Boolean isCompleteSectors() { return completeSectors; }
    public void setCompleteSectors(Boolean completeSectors) { this.completeSectors = completeSectors; }


    @Basic
    @Column(name = "DESCRIPCION")
    @Length(max=512)
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Basic
    @Column(name = "OBSERVACIONES_INTERNAS")
    public String getInternalObservations() { return internalObservations; }
    public void setInternalObservations(String internalObservations) { this.internalObservations = internalObservations; }

    @Basic
    @Column(name = "CREADO", nullable = false)
    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CREADO_POR", referencedColumnName = "ID")
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    @Basic
    @Column(name = "MODIFICADO")
    public Date getModified() { return modified; }
    public void setModified(Date modified) { this.modified = modified; }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MODIFICADO_POR", referencedColumnName = "ID")
    public User getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(User modifiedBy) { this.modifiedBy = modifiedBy; }

    @Basic
    @Column(name = "BORRADO")
    public Date getDeleted() { return deleted; }
    public void setDeleted(Date deleted) { this.deleted = deleted; }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BORRADO_POR", referencedColumnName = "ID")
    public User getDeletedBy() { return deletedBy; }
    public void setDeletedBy(User deletedBy) { this.deletedBy = deletedBy; }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", documentType=" + documentType +
                ", customer=" + customer +
                ", attachedUrl='" + attachedUrl + '\'' +
                ", attachedName='" + attachedName + '\'' +
                ", attachedContentType='" + attachedContentType + '\'' +
                ", date=" + date +
                ", visibleExtranet=" + visibleExtranet +
                ", visibleUntil=" + visibleUntil +
                ", completeTerritory=" + completeTerritory +
                ", completeSectors=" + completeSectors +
                ", description='" + description + '\'' +
                ", internalObservations='" + internalObservations + '\'' +
                ", created=" + created +
                ", createdBy=" + createdBy +
                ", modified=" + modified +
                ", modifiedBy=" + modifiedBy +
                ", deleted=" + deleted +
                ", deletedBy=" + deletedBy +
                '}';
    }

}
