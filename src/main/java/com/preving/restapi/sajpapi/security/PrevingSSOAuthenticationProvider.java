package com.preving.restapi.sajpapi.security;


import com.preving.restapi.sajpapi.model.dao.seguridad.SeguridadDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrevingSSOAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SeguridadDao seguridadDao;

    @Value(value = "${roles.tecnico-externo}")
    private int OPTEC_ROL_TECNICO_EXTERNO;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // use the credentials
        // and authenticate against the third-party system

        boolean authenticated = false;

        List<Object[]> usuario = this.seguridadDao.findByUsername(username, OPTEC_ROL_TECNICO_EXTERNO);

        String salto = (String)usuario.get(0)[0];
        String passBBDD = (String)usuario.get(0)[1];

        // Concatenar salto y pwd ofrecido
        String pwdAndSalt = password + salto;
        // Encriptar cadena resultante de operacion anterior
        String pwdHashed = DigestUtils.sha1Hex(pwdAndSalt).toUpperCase();

        // hash de pwd ofrecido coincide con el que existe en la base de datos
        if(passBBDD.equals(pwdHashed)){
            authenticated = true;
        }

        if (authenticated) {
            return new UsernamePasswordAuthenticationToken(username, password);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}


