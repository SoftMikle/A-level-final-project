package com.alevel.library.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "clients", schema = "library")
@Data
public class Client extends BaseEntity {

    @Size(min = 2, max = 100)
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 1, max = 100)
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_day")
    private Date birthDay;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_card_id")
    private ClientCardItem clientCardItem;

    @Column(name = "is_debtor")
    private boolean isDebtor;

}
