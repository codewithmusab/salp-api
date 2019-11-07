package com.preving.restapi.sajpapi.model.domains;

import java.io.Serializable;
import java.util.Date;

public class Contract implements Serializable {

    private int id;
    private String serviceName;
    private String registerDate;
    private String finishDate;
    private String especiality;
    private boolean active;
    private String[] name = {"AJE PT Program - AJP (N 1: 1-10T)", "AJE PT Program - AJP (N 2: 11-25T)", "AJE PT Program - AJP (N 3: 36-50T)", "SPA +", "SPA Flexible"};
    private String[] regDe = {"01-01-2010", "07-10-2013", "12-07-2011", "11-11-2014", "20-02-2012"};
    private String[] endD = {"-", "-", "21-08-2021", "10-10-2020", "14-02-2018"};


    public int getId() {
        return id = ((int) (Math.random()*(5 - 1))) + 1;
//        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isActive() {
        return active = false;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEspeciality() {
        return especiality;
    }

    public void setEspeciality(String especiality) {
        this.especiality = especiality;
    }

    public Contract() {
        id = getId();
        serviceName = name[id];
        registerDate = regDe[id];
        finishDate = endD[id];
        especiality = "H,S,V";
        active = false;
    }
}
