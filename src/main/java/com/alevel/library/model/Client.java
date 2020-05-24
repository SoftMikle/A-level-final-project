package com.alevel.library.model;

import com.alevel.library.model.additional.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.HashCodeExclude;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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

    @HashCodeExclude
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private ClientCard clientCard;

    @HashCodeExclude
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private ClientAccountInfo accountInfo;

    @Column(name = "is_debtor")
    private boolean isDebtor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client that = (Client) o;
        return isDebtor() == that.isDebtor() &&
                getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getBirthDay().equals(that.getBirthDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getBirthDay(), isDebtor());
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", isDebtor=" + isDebtor +
                '}';
    }
}
