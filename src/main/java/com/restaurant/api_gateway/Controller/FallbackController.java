package com.restaurant.api_gateway.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/users")
    public String userServiceFallback() {
        return "User Service is taking longer than expected. Please try again later.";
    }

    @GetMapping("/commandes")
    public String commandeServiceFallback() {
        return "Commande Service is taking longer than expected. Please try again later.";
    }

    @GetMapping("/reclamations")
    public String reclamationServiceFallback() {
        return "Reclamation Service is taking longer than expected. Please try again later.";
    }
    
}