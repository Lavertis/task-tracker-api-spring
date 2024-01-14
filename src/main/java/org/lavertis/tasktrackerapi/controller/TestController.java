package org.lavertis.tasktrackerapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class TestController {
    @GetMapping("hello")
    public String welcomePage() {
        return "Welcome!";
    }
}
