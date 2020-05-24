package com.alevel.library.model;

import com.alevel.library.model.additional.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "client_cards", schema = "library")
@Data
public class ClientCard extends BaseEntity implements Serializable {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "clientCard", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<ClientCardItem> clientCardItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientCard)) return false;
        ClientCard that = (ClientCard) o;
        return Objects.equals(getClient(), that.getClient()) &&
                Objects.equals(getClientCardItems(), that.getClientCardItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClient(), getClientCardItems());
    }
}
