package com.preving.restapi.sajpapi.security;


import com.preving.restapi.sajpapi.model.domains.security.Authority;
import com.preving.restapi.sajpapi.model.domains.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getAuthorities()),
                user.isEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority ->
                        new SimpleGrantedAuthority((authority.getAplicacion().getCodigoApp() != null ?
                                authority.getAplicacion().getCodigoApp() : authority.getAplicacion().getId()) +
                                "-" + authority.getId()))
                .collect(Collectors.toList());
    }
}
