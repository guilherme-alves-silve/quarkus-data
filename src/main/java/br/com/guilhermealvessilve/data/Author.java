package br.com.guilhermealvessilve.data;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.util.List;

@Data
@Indexed
@Entity(name = "author")
@EqualsAndHashCode(callSuper = true)
public class Author extends PanacheEntity {

    @FullTextField(analyzer = "name")
    @KeywordField(
            name = "firstName_sort",
            sortable = Sortable.YES,
            normalizer = "sort"
    )
    @Column(name = "first_name")
    @JsonbProperty("first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonbProperty("last_name")
    @FullTextField(analyzer = "name")
    @KeywordField(
            name = "lastName_sort",
            sortable = Sortable.YES,
            normalizer = "sort"
    )
    private String lastName;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "author"
    )
    @IndexedEmbedded
    private List<AuthorBook> books;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", books=" + books +
                '}';
    }
}
