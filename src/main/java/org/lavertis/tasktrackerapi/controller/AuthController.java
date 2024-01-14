package org.lavertis.tasktrackerapi.controller;

import org.lavertis.tasktrackerapi.utils.JwtTokenUtil;
import org.lavertis.tasktrackerapi.dto.request.SignInRequest;
import org.lavertis.tasktrackerapi.dto.response.JwtResponse;
import org.lavertis.tasktrackerapi.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody SignInRequest signInRequest) throws Exception {
        authenticate(signInRequest.getEmail(), signInRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}