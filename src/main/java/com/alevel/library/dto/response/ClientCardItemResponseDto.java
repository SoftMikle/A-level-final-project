package com.alevel.library.dto.response;

import com.alevel.library.model.ClientCardItem;
import com.alevel.library.model.additional.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class ClientCardItemResponseDto {

    int id;
    Date reserved;
    Status status;
    Date returned;
    BookResponseDto book;

    public ClientCardItemResponseDto() {
    }

    public static ClientCardItemResponseDto toDto(ClientCardItem clientCardItem) {
        ClientCardItemResponseDto clientResponseDto = new ClientCardItemResponseDto();
        clientResponseDto.setId(clientCardItem.getId());
        clientResponseDto.setReserved(clientCardItem.getReserved());
        clientResponseDto.setStatus(clientCardItem.getStatus());
        clientResponseDto.setReturned(clientCardItem.getReturned());
        BookResponseDto bookResponseDto = BookResponseDto.toDto(clientCardItem.getBook());
        clientResponseDto.setBook(bookResponseDto);

        return clientResponseDto;
    }
}
