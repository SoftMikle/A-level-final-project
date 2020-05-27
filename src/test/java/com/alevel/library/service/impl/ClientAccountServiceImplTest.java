package com.alevel.library.service.impl;

import com.alevel.library.model.ClientAccountInfo;
import com.alevel.library.repository.ClientAccountRepository;
import com.alevel.library.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static com.alevel.library.model.additional.enums.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class ClientAccountServiceImplTest {

  @Mock
  private ClientAccountRepository clientAccountRepository;
  @Mock
  private ClientRepository clientRepository;

  private ClientAccountServiceImpl clientAccountService;

  @BeforeEach
  public void setUp(){
    MockitoAnnotations.initMocks(this);
    clientAccountService = new ClientAccountServiceImpl(clientAccountRepository, clientRepository);
  }

  @Test
  void save() {
    ClientAccountInfo clientAccountInfo = new ClientAccountInfo();
    clientAccountInfo.setDonationsAndCharity("Alex is God");
    doReturn(clientAccountInfo).when(clientAccountRepository).save(ArgumentMatchers.eq(clientAccountInfo));
    ArgumentCaptor<ClientAccountInfo> captor = ArgumentCaptor.forClass(ClientAccountInfo.class);
    clientAccountService.save(clientAccountInfo);
    verify(clientAccountRepository).save(captor.capture());
    ClientAccountInfo value = captor.getValue();
    assertEquals("Alex is God", value.getDonationsAndCharity());
    assertEquals(ACTIVE, value.getStatus());
  }
}