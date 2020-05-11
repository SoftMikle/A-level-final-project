package com.alevel.library.rest;

import com.alevel.library.dto.request.ClientRequest;
import com.alevel.library.dto.response.ClientResponse;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/clients/")
public class ClientsController {

    @GetMapping
    ResponseEntity<String> getAll(SpringDataWebProperties.Pageable pageable) {
        return ResponseEntity.ok("This will be a page of clients");
    }

    @PostMapping
    ResponseEntity<ClientRequest> postClient(@RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok(clientRequest);
    }

    @PutMapping("{clientId}")
    ResponseEntity<ClientResponse> putClient(@RequestBody ClientRequest clientRequest, @PathVariable int clientId) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(clientId);
        clientResponse.setBirthDate(clientRequest.getBirthDate());
        clientResponse.setName(clientRequest.getName());
        clientResponse.setSurname(clientRequest.getSurname());
        return ResponseEntity.ok(clientResponse);
    }

    @GetMapping("{clientId}")
    ResponseEntity<ClientResponse> getById(@PathVariable int clientId) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(clientId);
        clientResponse.setBirthDate(new Date());
        clientResponse.setName("Mikle");
        clientResponse.setSurname("Savchenko");
        return ResponseEntity.ok(clientResponse);
    }

    @DeleteMapping("{clientId}")
    ResponseEntity<String> deleteById(@PathVariable int clientId) {

        return ResponseEntity.ok("User with id = " + clientId + " deleted");
    }


}
