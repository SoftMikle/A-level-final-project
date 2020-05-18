package com.alevel.library.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client_cards", schema = "library")
@Data
public class ClientCardItem extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany(mappedBy = "clientCardItem", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Book> books;
}
