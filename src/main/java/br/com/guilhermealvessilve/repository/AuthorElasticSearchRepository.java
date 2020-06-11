package br.com.guilhermealvessilve.repository;

import br.com.guilhermealvessilve.data.Author;
import br.com.guilhermealvessilve.data.AuthorBook;
import org.hibernate.search.mapper.orm.Search;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class AuthorElasticSearchRepository {

    private static final Logger LOGGER = Logger.getLogger(AuthorElasticSearchRepository.class.getSimpleName());

    private final EntityManager entityManager;

    @Inject
    public AuthorElasticSearchRepository(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void reindex() throws InterruptedException {
        if (AuthorBook.count() > 0) {
            LOGGER.info("Starting index of Elasticsearch");
            Search.session(entityManager)
                    .massIndexer()
                    .startAndWait();
        }
    }

    public List<Author> searchForAuthor(String pattern, Integer size) {
        return Search.session(entityManager)
                .search(Author.class)
                .where(where -> (pattern == null || pattern.trim().isEmpty())
                        ? where.matchAll()
                        : where.simpleQueryString()
                        .fields(
                                "firstName",
                                "lastName",
                                "books.title")
                        .matching(pattern))
                .sort(sort -> sort.field("firstName_sort")
                        .then()
                        .field("lastName_sort"))

                .fetchHits(size);
    }
}
