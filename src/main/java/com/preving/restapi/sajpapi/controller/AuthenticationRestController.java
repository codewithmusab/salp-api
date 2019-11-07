package com.preving.restapi.sajpapi.controller;


import com.preving.restapi.sajpapi.model.exceptions.CustomRestApiException;
import com.preving.restapi.sajpapi.model.exceptions.errors.RestApiErrorCode;
import com.preving.restapi.sajpapi.model.exceptions.errors.RestApiErrorDetail;
import com.preving.restapi.sajpapi.security.JwtAuthenticationRequest;
import com.preving.restapi.sajpapi.security.JwtTokenUtil;
import com.preving.restapi.sajpapi.security.service.JwtAuthenticationResponse;
import com.preving.restapi.sajpapi.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/auth")
public class AuthenticationRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) throws CustomRestApiException {

        // Perform the security
        try{
            final Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload password post-security so we can generate token
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            final String token = this.jwtTokenUtil.generateToken(userDetails);

            // Return the token
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (ProviderNotFoundException e){
            List<RestApiErrorDetail> errores = new ArrayList<>();
            errores.add(new RestApiErrorDetail("user.incorrect", RestApiErrorCode.USER_INCORRECT.getMessage()));
            throw new CustomRestApiException(HttpStatus.UNAUTHORIZED, RestApiErrorCode.USER_INCORRECT, errores);
        }
    }

    /*
    @RequestMapping(value = "/login/{usuarioId}/{hash}", method = RequestMethod.GET)
    public ResponseEntity<?> loginParams(
        @PathVariable("usuarioId") String usuarioId,
        @PathVariable("hash") String hash
    ) throws AuthenticationException {

        String hashCalculado = this.usuariosService.getHashByUsuarioId(usuarioId);

        if(!hash.isEmpty() && hashCalculado.equals(hash)){
            // Reload password post-security so we can generate token
            final UserDetails userDetails = this.userDetailsService.loadUserById(Long.parseLong(usuarioId));

            Device device = new Device() {
                @Override
                public boolean isNormal() {
                    return false;
                }

                @Override
                public boolean isMobile() {
                    return false;
                }

                @Override
                public boolean isTablet() {
                    return false;
                }

                @Override
                public DevicePlatform getDevicePlatform() {
                    return null;
                }
            };

            final String token = this.jwtTokenUtil.generateToken(userDetails, device);

            // Return the token
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));

        } else {
            List<RestApiErrorDetail> errores = new ArrayList<>();
            errores.add(new RestApiErrorDetail("user.incorrect", RestApiErrorCode.USER_INCORRECT.getMessage()));
            throw new NotValidRestApiException(RestApiErrorCode.USER_INCORRECT, errores);
        }
    }*/

}
