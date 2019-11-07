package com.preving.restapi.sajpapi.model.domains.security;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PcUsuariosGruposPrimaryKey implements Serializable {

    @Column(name = "USUARIO_ID")
    private int usuarioId;

    @Column(name = "GRUPO_ID")
    private int grupoId;

    @Id
    @Column(name = "USUARIO_ID", nullable = false, precision = 0)
    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Id
    @Column(name = "GRUPO_ID", nullable = false, precision = 0)
    public int getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(int grupoId) {
        this.grupoId = grupoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PcUsuariosGruposPrimaryKey that = (PcUsuariosGruposPrimaryKey) o;
        return usuarioId == that.usuarioId &&
                grupoId == that.grupoId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(usuarioId, grupoId);
    }
}
