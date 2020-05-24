package com.alevel.library.rest;

import com.alevel.library.dto.request.ClientRequestDto;
import com.alevel.library.dto.response.BookResponseDto;
import com.alevel.library.dto.response.ClientAccountInfoDto;
import com.alevel.library.dto.response.ClientResponseDto;
import com.alevel.library.model.Book;
import com.alevel.library.model.Client;
import com.alevel.library.model.ClientAccountInfo;
import com.alevel.library.service.BookService;
import com.alevel.library.service.ClientAccountService;
import com.alevel.library.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients/")

public class ClientsController {

    private final ClientService clientService;
    private final BookService bookService;
    private final ClientAccountService clientAccountService;

    @Autowired
    public ClientsController(ClientService clientService, BookService bookService, ClientAccountService clientAccountService) {
        this.clientService = clientService;
        this.bookService = bookService;
        this.clientAccountService = clientAccountService;
    }

    @GetMapping
    Page<Client> getAll(@PageableDefault(page = 0, size = 20)
                        @SortDefault.SortDefaults({
                                @SortDefault(sort = "last_name", direction = Sort.Direction.DESC),
                                @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                        })
                                Pageable pageable) {
        Page<Client> result = clientService.findAll(pageable);

        return result;
    }

    @PostMapping
    HttpStatus postClient(@RequestBody ClientRequestDto clientRequestDto) {
        Client client = clientRequestDto.toClient();
        client = clientService.save(client);
        if (client.getId() == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.CREATED;
    }

    @PatchMapping("{clientId}")
    HttpStatus putClient(@RequestBody ClientRequestDto clientRequestDto, @PathVariable int clientId) {
        Client client = clientRequestDto.toClient();
        client.setId(clientId);
        clientService.update(client);
        return HttpStatus.OK;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("{clientId}")
    ResponseEntity<ClientResponseDto> getById(@PathVariable int clientId) {
        Client client = clientService.findById(clientId);
        if (client != null) {
            ClientResponseDto result = ClientResponseDto.toClientResponseDto(client);
            return ResponseEntity.ok(result);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{clientId}")
    HttpStatus deleteById(@PathVariable int clientId) {
        if (clientService.existsById(clientId)) {
            clientService.delete(clientId);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @GetMapping("{clientId}/extra")
    ResponseEntity<ClientAccountInfoDto> getAccountInfoById(@PathVariable int clientId) {
        ClientAccountInfo clientAccountInfo = clientAccountService.findByClientId(clientId);
        ClientAccountInfoDto result = ClientAccountInfoDto.toClientAccountInfoDto(clientAccountInfo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("{clientId}/books")
    ResponseEntity<Page<BookResponseDto>> getAllClientsBooks(@PathVariable int clientId, Pageable pageable) {

        Page<Book> books = bookService.findAllBooksByClientId(clientId, pageable);
        Page<BookResponseDto> result = books.map(BookResponseDto::toBookResponseDto);
        return ResponseEntity.ok(result);
    }


    @PatchMapping("{clientId}/books/{bookId}")
    HttpStatus setBook(@PathVariable int clientId, @PathVariable int bookId) {
        clientService.setBook(clientId, bookId);
        return HttpStatus.OK;
    }
}
