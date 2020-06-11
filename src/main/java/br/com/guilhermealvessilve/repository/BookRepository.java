package br.com.guilhermealvessilve.repository;

import br.com.guilhermealvessilve.data.Book;
import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BookRepository {

    private static final Logger LOGGER = Logger.getLogger(BookRepository.class.getSimpleName());

    private final AgroalDataSource dataSourceMySQL;

    @Inject
    public BookRepository(@DataSource("mysql") AgroalDataSource dataSourceMySQL) {
        this.dataSourceMySQL = dataSourceMySQL;
    }

    public List<Book> getAll() {

        try (final var connection = dataSourceMySQL.getConnection();
             final var statement = connection.prepareStatement("SELECT * FROM book;");
             final var resultSet = statement.executeQuery()) {
            final var books = new ArrayList<Book>();
            while (resultSet.next()) {
                final var book = Book.builder()
                        .id(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .author(resultSet.getString("author"))
                        .pages(resultSet.getInt("pages"))
                        .build();
                books.add(book);
            }

            return books;
        } catch (SQLException ex) {
            LOGGER.error(ex);
            return List.of();
        }
    }
}
