package com.preving.restapi.sajpapi.model.dao.seguridad;


import com.preving.restapi.sajpapi.model.domains.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeguridadDao extends JpaRepository<User, Long> {

    @Query("SELECT s.salto, s.password " +
            "FROM User s WHERE (s.username = ?1) " +
        "AND (s.enabled = true) " +
        // Se comprueba que el usuario no tengo el rol TECNICO EXTERNO
        "and not exists (select t.pcUsuariosGruposPrimaryKey.usuarioId from PcUsuariosgrupos t " +
        "where t.pcUsuariosGruposPrimaryKey.usuarioId = s.id and t.pcUsuariosGruposPrimaryKey.grupoId = ?2)")
    public List<Object[]> findByUsername(String userName, int rolTecnicoExterno);

}
