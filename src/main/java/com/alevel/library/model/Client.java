package com.alevel.library.model;

import com.alevel.library.model.additional.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clients", schema = "library")
@Data
public class Client extends BaseEntity implements Serializable {

    @Size(min = 2, max = 100)
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 1, max = 100)
    @Column(name = "last_name")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_day")
    private Date birthDay;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private ClientCard clientCard;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private ClientAccountInfo accountInfo;

    @Column(name = "is_debtor")
    private boolean isDebtor;

}
