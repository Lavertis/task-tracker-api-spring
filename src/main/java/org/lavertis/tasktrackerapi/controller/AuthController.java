package org.lavertis.tasktrackerapi.controller;

import org.lavertis.tasktrackerapi.dto.auth.JwtResponse;
import org.lavertis.tasktrackerapi.dto.auth.SignInRequest;
import org.lavertis.tasktrackerapi.entity.AppUser;
import org.lavertis.tasktrackerapi.exceptions.NotFoundException;
import org.lavertis.tasktrackerapi.service.user_service.IUserService;
import org.lavertis.tasktrackerapi.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private IUserService userService;

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> signIn(@RequestBody SignInRequest signInRequest) throws Exception {
        final AppUser user = userService.getUserByUsername(signInRequest.getEmail());
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        authenticate(signInRequest.getEmail(), signInRequest.getPassword());
        final String token = jwtTokenUtil.generateToken(user);
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