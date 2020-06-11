package br.com.guilhermealvessilve.resource;

import br.com.guilhermealvessilve.data.Book;
import br.com.guilhermealvessilve.repository.BookRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/book")
public class BookResource {

    private final BookRepository repository;

    @Inject
    public BookResource(BookRepository repository) {
        this.repository = repository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        return repository.getAll();
    }
}