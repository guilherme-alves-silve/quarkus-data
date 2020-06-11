package br.com.guilhermealvessilve.data;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Data
@Builder
@Indexed
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "author_book")
public class AuthorBook extends PanacheEntity {

    @FullTextField(analyzer = "english")
    private String title;

    @ManyToOne
    @JsonbTransient
    private Author author;

    private int pages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorBook that = (AuthorBook) o;
        return pages == that.pages &&
                title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, pages);
    }

    @Override
    public String toString() {
        return "AuthorBook{" +
                "title='" + title + '\'' +
                ", pages=" + pages +
                ", id=" + id +
                '}';
    }
}
