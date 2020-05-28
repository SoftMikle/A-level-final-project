package com.alevel.library.service.impl;

import com.alevel.library.exceptions.ClientAccountNotFoundException;
import com.alevel.library.exceptions.ClientNotFoundException;
import com.alevel.library.model.Client;
import com.alevel.library.model.ClientAccountInfo;
import com.alevel.library.repository.ClientAccountRepository;
import com.alevel.library.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static com.alevel.library.model.additional.enums.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class ClientAccountServiceImplTest {

  @Mock
  private ClientAccountRepository clientAccountRepository;
  @Mock
  private ClientRepository clientRepository;

  ClientAccountInfo accountInfo;
  Client client;
  @InjectMocks
  private ClientAccountServiceImpl service;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    accountInfo = new ClientAccountInfo();
    accountInfo.setId(112);
    accountInfo.setDonationsAndCharity("Top donator");
    accountInfo.setDiscount(33.33);
    client = new Client();
    client.setAccountInfo(accountInfo);
    client.setFirstName("Tester");
    accountInfo.setClient(client);
  }

  @Test
  void save() {
    ClientAccountInfo clientAccountInfo = new ClientAccountInfo();
    clientAccountInfo.setDonationsAndCharity("Alex is God");
    doReturn(clientAccountInfo).when(clientAccountRepository).save(ArgumentMatchers.eq(clientAccountInfo));
    ArgumentCaptor<ClientAccountInfo> captor = ArgumentCaptor.forClass(ClientAccountInfo.class);
    service.save(clientAccountInfo);
    verify(clientAccountRepository).save(captor.capture());
    ClientAccountInfo value = captor.getValue();
    assertEquals("Alex is God", value.getDonationsAndCharity());
    assertEquals(ACTIVE, value.getStatus());
  }

  @Test
  void findById() {
    doReturn(Optional.of(accountInfo)).when(clientAccountRepository).findById(nullable(Integer.class));
    doReturn(true).when(clientAccountRepository).existsById(nullable(Integer.class));
    ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
    service.findById(accountInfo.getId());
    verify(clientAccountRepository).findById(captor.capture());
    assertEquals(accountInfo.getId(), captor.getValue());

    doReturn(false).when(clientAccountRepository).existsById(nullable(Integer.class));
    assertThrows(ClientAccountNotFoundException.class, () -> service.findById(accountInfo.getId()));
  }

  @Test
  void delete() {
    doReturn(true).when(clientAccountRepository).existsById(nullable(Integer.class));
    ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
    service.delete(accountInfo.getId());
    verify(clientAccountRepository).deleteById(captor.capture());
    assertEquals(accountInfo.getId(), captor.getValue());

    doReturn(false).when(clientAccountRepository).existsById(nullable(Integer.class));
    assertThrows(ClientAccountNotFoundException.class, () -> service.delete(accountInfo.getId()));
  }

  @Test
  void findByClientId() {
    doReturn(accountInfo).when(clientAccountRepository).findByClientId(nullable(Integer.class));
    doReturn(true).when(clientRepository).existsById(nullable(Integer.class));
    ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
    service.findByClientId(accountInfo.getClient().getId());
    verify(clientAccountRepository).findByClientId(captor.capture());
    assertEquals(client.getId(), captor.getValue());

    doReturn(null).when(clientAccountRepository).findByClientId(nullable(Integer.class));
    assertThrows(ClientAccountNotFoundException.class, () -> service.findByClientId(accountInfo.getId()));
    doReturn(false).when(clientRepository).existsById(nullable(Integer.class));
    assertThrows(ClientNotFoundException.class, () -> service.findByClientId(accountInfo.getId()));
  }
}