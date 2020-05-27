package com.alevel.library.rest;

import com.alevel.library.dto.request.ClientRequestDto;
import com.alevel.library.dto.response.BookResponseDto;
import com.alevel.library.dto.response.ClientAccountInfoDto;
import com.alevel.library.dto.response.ClientCardItemResponseDto;
import com.alevel.library.dto.response.ClientResponseDto;
import com.alevel.library.model.Book;
import com.alevel.library.model.Client;
import com.alevel.library.model.ClientAccountInfo;
import com.alevel.library.model.ClientCardItem;
import com.alevel.library.service.BookService;
import com.alevel.library.service.ClientAccountService;
import com.alevel.library.service.ClientCardItemService;
import com.alevel.library.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/clients")

public class ClientsController {

    private final ClientService clientService;
    private final BookService bookService;
    private final ClientAccountService clientAccountService;
    private final ClientCardItemService clientCardItemService;

    @Autowired
    public ClientsController(ClientService clientService,
                             BookService bookService,
                             ClientAccountService clientAccountService,
                             ClientCardItemService clientCardItemService) {
        this.clientService = clientService;
        this.bookService = bookService;
        this.clientAccountService = clientAccountService;
        this.clientCardItemService = clientCardItemService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    Page<ClientResponseDto> getAll(@PageableDefault(page = 0, size = 20)
                                   @SortDefault.SortDefaults({
                                           @SortDefault(sort = "lastName", direction = Sort.Direction.DESC),
                                           @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                   })
                                           Pageable pageable) {
        Page<Client> clients = clientService.findAll(pageable);
        Page<ClientResponseDto> result = clients.map(ClientResponseDto::toDto);
        return result;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    HttpStatus postClient(@RequestBody ClientRequestDto clientRequestDto) {
        Client client = clientRequestDto.toClient();
        clientService.save(client);

        return HttpStatus.CREATED;
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("{clientId}")
    HttpStatus putClient(@RequestBody ClientRequestDto clientRequestDto, @PathVariable int clientId) {
        Client client = clientRequestDto.toClient();
        client.setId(clientId);
        clientService.update(client);
        return HttpStatus.OK;
    }

    @GetMapping("{clientId}")
    ResponseEntity<ClientResponseDto> getById(@PathVariable int clientId) {
        Client client = clientService.findById(clientId);
        ClientResponseDto result = ClientResponseDto.toDto(client);
        return ResponseEntity.ok(result);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{clientId}")
    HttpStatus deleteById(@PathVariable int clientId) {
        clientService.delete(clientId);
        return HttpStatus.OK;

    }

    @GetMapping("{clientId}/extra")
    ResponseEntity<ClientAccountInfoDto> getAccountInfoById(@PathVariable int clientId) {
        ClientAccountInfo clientAccountInfo = clientAccountService.findByClientId(clientId);
        ClientAccountInfoDto result = ClientAccountInfoDto.toDto(clientAccountInfo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("{clientId}/books")
    ResponseEntity<Page<BookResponseDto>> getAllClientsBooks(@PathVariable int clientId, Pageable pageable) {

        Page<Book> books = clientService.findAllBooksByClientId(clientId, pageable);
        Page<BookResponseDto> result = books.map(BookResponseDto::toDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("{clientId}/history")
    ResponseEntity<Page<ClientCardItemResponseDto>> getClientsHistory(@PathVariable int clientId, Pageable pageable) {
        Page<ClientCardItem> clientCardItems = clientCardItemService.findByClientId(clientId, pageable);
        Page<ClientCardItemResponseDto> result = clientCardItems
                .map(ClientCardItemResponseDto::toDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/books/{bookId}")
    ResponseEntity<ClientResponseDto> getClientByBookId(@PathVariable int bookId) {

        Client client = bookService.findClientByBookId(bookId);
        ClientResponseDto result = ClientResponseDto.toDto(client);
        return ResponseEntity.ok(result);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/debtors")
    ResponseEntity<Page<ClientResponseDto>> getAllDebtors(Pageable pageable) {

        Page<Client> clients = clientService.findAllDebtors(pageable);
        Page<ClientResponseDto> result = clients.map(ClientResponseDto::toDto);
        return ResponseEntity.ok(result);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("{clientId}/books/{bookId}")
    HttpStatus setBook(@PathVariable int clientId, @PathVariable int bookId) {
        clientService.setBook(clientId, bookId);
        return HttpStatus.OK;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/search")
    ResponseEntity<Page<ClientResponseDto>> getAllByExample(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthDate,
            Pageable pageable) {

        Client client = new Client();
        client.setFirstName(name);
        client.setLastName(surname);
        client.setBirthDay(birthDate);
        Page<Client> clients = clientService.search(pageable, client);
        Page<ClientResponseDto> result = clients.map(ClientResponseDto::toDto);
        return ResponseEntity.ok(result);
    }
}
