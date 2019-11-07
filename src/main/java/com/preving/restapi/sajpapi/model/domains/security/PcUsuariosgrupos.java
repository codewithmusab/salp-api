package com.preving.restapi.sajpapi.model.domains.security;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PC_USUARIOSGRUPOS", schema = "GC2006_RELEASE", catalog = "")
public class PcUsuariosgrupos {
    @EmbeddedId
    private PcUsuariosGruposPrimaryKey pcUsuariosGruposPrimaryKey;
}
