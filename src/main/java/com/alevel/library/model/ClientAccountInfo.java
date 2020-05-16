package com.alevel.library.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "client_accounts_info", schema = "library")
@Data
public class ClientAccountInfo extends BaseEntity {

    @Column(name = "discount")
    private double discount;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "accounts_donated_books",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")})
    private Set<Book> donatedBooks;

    @Column(name = "donations_and_charity")
    private String donationsAndCharity;
}
