package org.lavertis.tasktrackerapi.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String jwtToken;
}