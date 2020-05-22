package com.alevel.library.model.additional;

import com.alevel.library.model.Book;
import com.alevel.library.model.ClientCard;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class ClientCardItemId implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    private ClientCard clientCard;

    @ManyToOne(fetch = FetchType.EAGER)
    private Book book;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientCardItemId that = (ClientCardItemId) o;

        if (!Objects.equals(clientCard, that.clientCard)) return false;
        if (!Objects.equals(book, that.book))
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (clientCard != null ? clientCard.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }
}
