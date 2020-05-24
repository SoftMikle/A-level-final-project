package com.alevel.library.model;

import com.alevel.library.model.additional.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "client_card_items", schema = "library")
@Data
@EqualsAndHashCode(callSuper = false)
public class ClientCardItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "reserving_time")
    private Date reserved;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "returning_time")
    private Date returned;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @HashCodeExclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_card_id", referencedColumnName = "id")
    private ClientCard clientCard;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientCardItem)) return false;
        ClientCardItem that = (ClientCardItem) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getReserved(), that.getReserved()) &&
                Objects.equals(getReturned(), that.getReturned()) &&
                getStatus() == that.getStatus() &&
                Objects.equals(getBook(), that.getBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getReserved(), getReturned(), getStatus(), getBook());
    }
}
