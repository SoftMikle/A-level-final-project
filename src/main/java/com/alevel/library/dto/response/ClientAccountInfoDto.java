package com.alevel.library.dto.response;

import com.alevel.library.model.ClientAccountInfo;
import lombok.Data;

@Data
public class ClientAccountInfoDto {

    int id;
    double discount;
    String donationsAndCharity;

    public ClientAccountInfoDto() {
    }

    public static ClientAccountInfoDto toDto(ClientAccountInfo clientAccountInfo) {
        ClientAccountInfoDto clientAccountInfoDto = new ClientAccountInfoDto();
        clientAccountInfoDto.setId(clientAccountInfo.getId());
        clientAccountInfoDto.setDiscount(clientAccountInfo.getDiscount());
        clientAccountInfoDto.setDonationsAndCharity(clientAccountInfo.getDonationsAndCharity());

        return clientAccountInfoDto;
    }
}
