package br.com.guilhermealvessilve.repository;

import br.com.guilhermealvessilve.data.AuthorBook;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AuthorBookRepository implements PanacheRepository<AuthorBook> {

    public void update(List<AuthorBook> books) {
        books.forEach(book -> JpaOperations.getEntityManager()
                .merge(book));
    }
}
