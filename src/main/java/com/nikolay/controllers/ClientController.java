package com.nikolay.controllers;

import com.nikolay.dto.UserLoginResponseDTO;
import com.nikolay.dto.CreateClientDTO;
import com.nikolay.dto.LoginClientDTO;
import com.nikolay.services.ClientService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public void createClient(@RequestBody CreateClientDTO clientDTO) { clientService.createClient(clientDTO); }

    @PostMapping("/login")
    public UserLoginResponseDTO loginClient(@RequestBody LoginClientDTO loginClientDTO, HttpServletResponse response) { return clientService.loginClient(loginClientDTO, response); }

    @PutMapping("/verify")
    public void verifyClient(@RequestParam String token) {
        clientService.verifyClient(token);
    }

}
