package com.alevel.library.model;

import com.alevel.library.model.additional.ClientCardItemId;
import com.alevel.library.model.additional.enums.Status;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "client_card_items", schema = "library")
@AssociationOverrides({
        @AssociationOverride(name = "pk.clientCard",
                joinColumns = @JoinColumn(name = "client_card_id")),
        @AssociationOverride(name = "pk.book",
                joinColumns = @JoinColumn(name = "book_id"))})
@Data
public class ClientCardItem implements Serializable {

    @EmbeddedId
    private ClientCardItemId pk = new ClientCardItemId();

    @CreatedDate
    @Column(name = "reserving_time")
    private Date reserved;

    @LastModifiedDate
    @Column(name = "returning_time")
    private Date returned;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Transient
    public ClientCard getClientCard() {
        return getPk().getClientCard();
    }

    public void setClientCard(ClientCard clientCard) {
        getPk().setClientCard(clientCard);
    }

    @Transient
    public Book getBook() {
        return getPk().getBook();
    }

    public void setBook(Book book) {
        getPk().setBook(book);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ClientCardItem that = (ClientCardItem) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;

        return true;
    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }

}
