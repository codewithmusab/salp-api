package com.preving.restapi.sajpapi.model.dao.seguridad;


import com.preving.restapi.sajpapi.model.domains.security.PcUsuariosGruposPrimaryKey;
import com.preving.restapi.sajpapi.model.domains.security.PcUsuariosgrupos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pcusuariosgrupos", collectionResourceRel = "pcusuariosgrupos")
public interface PcUsuariosgruposRepository extends JpaRepository<PcUsuariosgrupos, PcUsuariosGruposPrimaryKey> {

}
