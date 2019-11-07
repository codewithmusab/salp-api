package com.preving.restapi.sajpapi.model.domains.customers;

import com.preving.restapi.sajpapi.model.domains.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "GC2006_RELEASE", name = "PC_CLIENTES")
@NamedNativeQueries({
        @NamedNativeQuery(name = "findCustomersByFilter", query =
                "SELECT CLI.ID, CLI.NOMBRE AS NAME, CLI.APELLIDOS AS SURNAME, CLI.RAZONSOCIAL AS COMPANYNAME, CLI.GRAN_CUENTA AS BIGACCOUNT, CLI.CNAE_ID AS CNAE, " +
                "   CASE " +
                "       WHEN TIPO_CLIENTE = 'PERSONA FISICA' THEN CLI.NIF " +
                "       ELSE CLI.CIF " +
                "   END CIFNIF " +
                "FROM GC2006_RELEASE.PC_CLIENTES CLI, " +
                "   GC2006_RELEASE.NMC_OFERTAS OFE, " +
                "   GC2006_RELEASE.NMC_OA OA, " +
                "   (SELECT * " +
                "   FROM GC2006_RELEASE.NMC_OA_X_SERVICIOS " +
                "   WHERE SERVICIO_ID IN (342, 343, 344, 345, 346, 347, 348, 349)) OA_SER, " +
                "   (SELECT MAX(FECHA_BAJA) FECHA_BAJA, MIN(FECHA_ALTA) FECHA_ALTA, SERVICIO_ID, OFERTA_ID " +
                "   FROM GC2006_RELEASE.NMC_OAH_X_SERVICIOS " +
                "   WHERE SERVICIO_ID IN (342, 343, 344, 345, 346, 347, 348, 349) " +
                "       AND BAJA_EVENTO_TIPO = 5 " +
                "   GROUP BY SERVICIO_ID, OFERTA_ID) OAH_SER, " +
                "   GC2006_RELEASE.NMC_SERVICIOS SER_A, " +
                "   GC2006_RELEASE.NMC_SERVICIOS SER_B, " +
                "   GC2006_RELEASE.TM_ENTIDADES ENT " +
                "WHERE CLI.ID = OFE.CLIENTE_ID " +
                "   AND OFE.ID = OA.ID " +
                "   AND OA.ID = OA_SER.OFERTA_ID(+) " +
                "   AND OA_SER.SERVICIO_ID = SER_A.ID(+) " +
                "   AND OA.ID = OAH_SER.OFERTA_ID(+) " +
                "   AND OAH_SER.SERVICIO_ID = SER_B.ID(+) " +
                "   AND CLI.EMP_ID = ENT.ID " +
//                "   AND CLI.ID = :clienteId " +
                "   AND (UPPER(CLI.CIFNIF) LIKE UPPER('%' || :criterion || '%') " +
                        "OR UPPER(CLI.NOMBRE) LIKE UPPER('%' || :criterion || '%') " +
                        "OR UPPER(CLI.APELLIDOS) LIKE UPPER('%' || :criterion || '%') " +
                        "OR UPPER(CLI.RAZONSOCIAL) LIKE UPPER('%' || :criterion || '%')) " +
                "   AND OA.FECHA_BAJA IS NULL"
                , resultSetMapping = "CustomerMapping"
        )
})
@SqlResultSetMappings(
        @SqlResultSetMapping(
                name="CustomerMapping",
                classes={
                        @ConstructorResult(
                                targetClass = Customer.class,
                                columns = {
                                        @ColumnResult(name="id", type = Integer.class),
                                        @ColumnResult(name="name", type = String.class),
                                        @ColumnResult(name="surname", type = String.class),
                                        @ColumnResult(name="companyName", type = String.class),
                                        @ColumnResult(name="cifnif", type = String.class),
                                        @ColumnResult(name="bigAccount", type = Integer.class),
                                        @ColumnResult(name="cnae", type = Integer.class)
                                })})
)

//@SqlResultSetMapping(
//        name="CustomerMapping",
//        classes={
//                @ConstructorResult(
//                        targetClass = Customer.class,
//                        columns = {
//                                @ColumnResult(name="id", type = Integer.class),
//                                @ColumnResult(name="name", type = String.class),
//                                @ColumnResult(name="surname", type = String.class),
//                                @ColumnResult(name="companyName", type = String.class),
//                                @ColumnResult(name="cifnif", type = String.class),
//                                @ColumnResult(name="bigAccount", type = Integer.class),
//                                @ColumnResult(name="cnae", type = Cnae.class),
//                        })},
//        entities = {
//                @EntityResult(entityClass= Cnae.class, fields= {
//                        @FieldResult(name = "cnae", column = "id"),
//                })})

//@SqlResultSetMapping(
//        name = "CustomerMapping",
//        entities = @EntityResult(
//                        entityClass = Customer.class,
//                        fields = {
//                                @FieldResult(name = "id", column = "ID"),
//                                @FieldResult(name = "name", column = "NAME"),
//                                @FieldResult(name= "surname", column = "SURMAME"),
//                                @FieldResult(name= "companyName", column = "COMPANYNAME"),
//                                @FieldResult(name = "cifnif", column = "CIFNIF"),
//                                @FieldResult(name = "bigAccount", column = "BIGACCOUNT")})
//                @EntityResult(
//                        entityClass = Cnae.class,
//                        fields = {
//                                @FieldResult(name = "id", column = "cnae_id")})
//        )

public class Customer implements Serializable {

    // Filter/Header
    private int id;
    private String name;
    private String surname;
    private String companyName;
    private String cifnif;
    private int bigAccount;
    private Entities entity;
    private Contract contract;

    // Client data
    private String activity;
    private Cnae cnae;
    private String agent;
    private String nifAgent;
    private Colaborator colaborator;
    private Commercial commercial;
    private int privateCompany;
    private CompanyGroup clientGroup;

    // Client contact data
    private String contact;
    private String mobile;
    private String phone1;
    private String phone2;
    private String fax;
    private String email;

    // Client address
    private City city;
    private String address;
    private String postalCode;

    // Client postal address
    private City paCity;
    private String paAddress;
    private String paPostalCode;


    //create the get and set methods with the definition gonna have in the data base
    // Filter/Header getter and setter
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
    @Column(name = "NOMBRE", length = 100)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "APELLIDOS", length = 150)
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "RAZONSOCIAL", length = 150)
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "CIFNIF")
    public String getCifnif() {
        return cifnif;
    }
    public void setCifnif(String cifnif) {
        this.cifnif = cifnif;
    }

    @Basic
    @Column(name = "GRAN_CUENTA", nullable = false)
    public int getBigAccount() {
        return bigAccount;
    }
    public void setBigAccount(int bigAccount) {
        this.bigAccount = bigAccount;
    }

    @Basic
    @Column(name = "PRIVADO", nullable = false)
    public int getPrivateCompany() {
        return privateCompany;
    }
    public void setPrivateCompany(int privateCompany) {
        this.privateCompany = privateCompany;
    }

    //create the relationship between TM_ENTIDADES and PC_CLIENTES tables join the columns of each tables
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMP_ID", referencedColumnName = "ID")
    public Entities getEntity() {
        return entity;
    }
    public void setEntity(Entities entity) {
        this.entity = entity;
    }

    //create the relationship between PC_GRUPOS_CLIENTES and PC_CLIENTES tables join the columns of each tables
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GRUPO_CLIENTE_ID", referencedColumnName = "ID")
    public CompanyGroup getClientGroup() {
        return clientGroup;
    }
    public void setClientGroup(CompanyGroup clientGroup) {
        this.clientGroup = clientGroup;
    }


    // CHANGE FOR REAL SERVICES TABLE CONNETION
    @Transient
    public Contract getContract() {
        return contract = new Contract();
    }
    public void setContract(Contract contract) {
        this.contract = contract;
    }


    // Client data getter and setter
    @Basic
    @Column(name = "ACTIVIDAD", length = 500)
    public String getActivity() {
        return activity;
    }
    public void setActivity(String activity) {
        this.activity = activity;
    }

    //create the relationship between PC_CNAES and PC_CLIENTES tables join the columns of each tables
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CNAE_ID", referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Cnae getCnae() {
        return cnae;
    }
    public void setCnae(Cnae cnae) {
        this.cnae = cnae;
    }

    @Basic
    @Column(name = "REPRESENTANTE", length = 250)
    public String getAgent() {
        return agent;
    }
    public void setAgent(String agent) {
        this.agent = agent;
    }

    @Basic
    @Column(name = "NIF_REPRESENTANTE", length = 16)
    public String getNifAgent() {
        return nifAgent;
    }
    public void setNifAgent(String nifAgent) {
        this.nifAgent = nifAgent;
    }

    //create the relationship between PC_COLABORADOR and PC_CLIENTES tables join the columns of each tables
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COLABORADOR_ID", referencedColumnName = "ID")
    public Colaborator getColaborator() {
        return colaborator;
    }
    public void setColaborator(Colaborator colaborator) {
        this.colaborator = colaborator;
    }

    //create the relationship between PC_USUARIOS and PC_CLIENTES tables join the columns of each tables
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMERCIAL_ID", referencedColumnName = "ID")
    public Commercial getCommercial() {
        return commercial;
    }
    public void setCommercial(Commercial commercial) {
        this.commercial = commercial;
    }

    // Client contact data
    @Basic
    @Column(name = "CONTACTO", length = 150)
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "MOVIL", length = 15)
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "TELEFONO1", length = 15)
    public String getPhone1() {
        return phone1;
    }
    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    @Basic
    @Column(name = "TELEFONO2", length = 15)
    public String getPhone2() {
        return phone2;
    }
    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    @Basic
    @Column(name = "FAX", length = 15)
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "EMAIL", length = 150)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Client address
    //create the relationship between VIG_SALUD.LOCALIDADES and PC_CLIENTES tables join the columns of each tables
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCALIDAD_ID", referencedColumnName = "LOC_ID")
    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }

    @Basic
    @Column(name = "DIRECCION", length = 150)
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "COD_POSTAL", length = 6)
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    // Client postal address
    //create the relationship between VIG_SALUD.LOCALIDADES and PC_CLIENTES tables join the columns of each tables
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCALIDAD_ID_DIRECCION", referencedColumnName = "LOC_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public City getPaCity() {
        return paCity;
    }
    public void setPaCity(City paCity) {
        this.paCity = paCity;
    }

    @Basic
    @Column(name = "DIRECCION_DIRECCION", length = 150)
    public String getPaAddress() {
        return paAddress;
    }
    public void setPaAddress(String paAddress) {
        this.paAddress = paAddress;
    }

    @Basic
    @Column(name = "CP_DIRECCION", length = 6)
    public String getPaPostalCode() {
        return paPostalCode;
    }
    public void setPaPostalCode(String paPostalCode) {
        this.paPostalCode = paPostalCode;
    }


    //create a class constructor for dependency injection
    public Customer() {
    }

//    public Customer(int id, String name, String surname, String companyName, String cifnif, int bigAccount, Cnae cnae) {
//        this.id = id;
//        this.name = name;
//        this.surname = surname;
//        this.companyName = companyName;
//        this.cifnif = cifnif;
//        this.bigAccount = bigAccount;
//        this.cnae = cnae;
//    }

    public Customer(int id, String name, String surname, String companyName, String cifnif, int bigAccount,
    Entities entity, Contract contract, String activity, Cnae cnae, String agent, String nifAgent,
    Colaborator colaborator, Commercial commercial, int privateCompany, CompanyGroup clientGroup,
    String contact, String mobile, String phone1, String phone2, String fax, String email, City city,
    String address, String postalCode, City paCity, String paAddress, String paPostalCode) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.companyName = companyName;
        this.cifnif = cifnif;
        this.bigAccount = bigAccount;
        this.entity = entity;
        this.contract = contract;
        this.activity = activity;
        this.cnae = cnae;
        this.agent = agent;
        this.nifAgent = nifAgent;
        this.colaborator = colaborator;
        this.commercial = commercial;
        this.privateCompany = privateCompany;
        this.clientGroup = clientGroup;
        this.contact = contact;
        this.mobile = mobile;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.fax = fax;
        this.email = email;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
        this.paCity = paCity;
        this.paAddress = paAddress;
        this.paPostalCode = paPostalCode;
    }
}
