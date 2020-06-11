package br.com.guilhermealvessilve.service;

import br.com.guilhermealvessilve.data.Author;
import br.com.guilhermealvessilve.data.AuthorBook;
import br.com.guilhermealvessilve.repository.AuthorBookRepository;
import br.com.guilhermealvessilve.repository.AuthorElasticSearchRepository;
import br.com.guilhermealvessilve.repository.AuthorRepository;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AuthorService {

    private final AuthorRepository repository;

    private final AuthorBookRepository bookRepository;

    private final AuthorElasticSearchRepository elasticSearchRepository;

    @Inject
    public AuthorService(
            final AuthorRepository repository,
            final AuthorBookRepository bookRepository,
            final AuthorElasticSearchRepository elasticSearchRepository
    ) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.elasticSearchRepository = elasticSearchRepository;
    }

    @Transactional
    public void onStart(@Observes StartupEvent startupEvent) throws InterruptedException {
        elasticSearchRepository.reindex();
    }

    public List<Author> getAll() {
        return repository.getAll();
    }

    public Optional<Author> get(Long id) {
        return repository.findByIdOptional(id);
    }

    @Transactional
    public void save(Author author) {
        author.getBooks()
                .forEach(book -> book.setAuthor(author));
        bookRepository.persist(author.getBooks());
        repository.save(author);
    }

    @Transactional
    public Optional<Author> update(Long id, Author author) {
        author.id = id;
        return repository.update(author)
                .map(updatedAuthor -> {
                    author.getBooks()
                            .forEach(book -> book.setAuthor(author));
                    bookRepository.update(author.getBooks());
                    return updatedAuthor;
                });
    }

    @Transactional
    public Optional<Author> delete(Long id) {
        return repository.delete(id);
    }

    @Transactional
    public List<Author> searchForAuthor(String pattern, Integer size) {
        return elasticSearchRepository.searchForAuthor(pattern, size);
    }
}
