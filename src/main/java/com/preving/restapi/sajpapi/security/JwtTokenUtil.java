package com.preving.restapi.sajpapi.security;



import com.preving.restapi.sajpapi.model.domains.UsuarioMin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    static final String HEADER_PARAM_TYPE_KEY = "typ";
    static final String HEADER_PARAM_TYPE_VALUE_JWT = "JWT";

    static final String CLAIM_KEY_AUTHORITIES = "roles";
    static final String CLAIM_KEY_USER_ID = "u_id";
    static final String CLAIM_KEY_USER_FNAME = "u_nombre";
    static final String CLAIM_KEY_USER_LNAME = "u_apellidos";
    static final String CLAIM_KEY_USER_EMAIL = "u_email";

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.prefijo}")
    private String tokenHeaderPrefijo;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private UsuarioMin getUserFromToken(HttpServletRequest request) {
        String token = this.getTokenFromHeader(request);

        UsuarioMin u;
        try {
            final Claims claims = getClaimsFromToken(token);

            u = new UsuarioMin();
            u.setId(((Integer)claims.get(CLAIM_KEY_USER_ID)).intValue());
            u.setEmail((String)claims.get(CLAIM_KEY_USER_EMAIL));
            u.setNombre((String)claims.get(CLAIM_KEY_USER_FNAME));
            u.setApellidos((String)claims.get(CLAIM_KEY_USER_LNAME));
            u.setUsuario(claims.getSubject());

        } catch (Exception e) {
            u = null;
        }
        return u;
    }

    public UsuarioMin getUserWithRolesFromToken(HttpServletRequest request) {
        UsuarioMin u = this.getUserFromToken(request);
        if (u != null) {
            u.setRoles(this.getRolesFromToken(request));
        }
        return u;
    }

    public String[] getRolesFromToken(HttpServletRequest request) {
        String token = this.getTokenFromHeader(request);
        try {
            ArrayList listaRoles = (ArrayList)getClaimsFromToken(token).get(CLAIM_KEY_AUTHORITIES);
            if(listaRoles != null){
                return (String[])listaRoles.toArray(new String[0]);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
         final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /*private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }*/

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserDetails userDetails/*, Device device*/) {
        Map<String, Object> claims = new HashMap<>();

        if(userDetails instanceof JwtUser){
            JwtUser jwtUser = (JwtUser)userDetails;
            claims.put(CLAIM_KEY_USER_ID, jwtUser.getId());
            claims.put(CLAIM_KEY_USER_FNAME, jwtUser.getFirstname());
            claims.put(CLAIM_KEY_USER_LNAME, jwtUser.getLastname());
            claims.put(CLAIM_KEY_USERNAME, jwtUser.getUsername());
            claims.put(CLAIM_KEY_USER_EMAIL, jwtUser.getEmail());
        }

        //claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));

        String roles[] = null;
        if(userDetails != null) {
            roles = new String[userDetails.getAuthorities().size()];
            int i = 0;
            for(GrantedAuthority role : userDetails.getAuthorities()){
                roles[i++] = role.getAuthority();

            }
        }
        claims.put(CLAIM_KEY_AUTHORITIES, roles);
        claims.put(CLAIM_KEY_CREATED, new Date(System.currentTimeMillis()));
        return generateToken(claims);
    }

    public String generateToken(Map<String, Object> claims) {

        return Jwts.builder()
                .setHeaderParam(HEADER_PARAM_TYPE_KEY, HEADER_PARAM_TYPE_VALUE_JWT)
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date(System.currentTimeMillis()));
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);

        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }

    public String getTokenFromHeader(HttpServletRequest request){
        String authToken = request.getHeader(this.tokenHeader);
        if(authToken != null && authToken.startsWith(this.tokenHeaderPrefijo)) {
            authToken = authToken.substring(this.tokenHeaderPrefijo.length());
        }
        return authToken;
    }

}