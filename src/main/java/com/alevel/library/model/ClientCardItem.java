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

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "book_id")
    private List<Book> books;
}
