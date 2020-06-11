package br.com.guilhermealvessilve.repository;

import br.com.guilhermealvessilve.data.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;

import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class AuthorRepository implements PanacheRepository<Author> {

    public List<Author> getAll() {
        return Author.findAll()
                .list();
    }

    public void save(final Author author) {
        author.persist();
    }

    @Override
    public Optional<Author> findByIdOptional(Long id) {
        return Author.find("id", id)
                .firstResultOptional();
    }

    public Optional<Author> update(Author author) {

        final var optAuthor = Author.<Author>find("id", author.id)
                .firstResultOptional();

        optAuthor.ifPresent(dbAuthor -> JpaOperations.getEntityManager().merge(author));

        return Optional.of(author);
    }

    public Optional<Author> delete(Long id) {
        final var optAuthor = Author.<Author>find("id", id)
                .firstResultOptional();

        optAuthor.ifPresent(author -> Author.deleteById(id));

        return optAuthor;
    }
}
