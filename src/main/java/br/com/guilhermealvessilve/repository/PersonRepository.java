package br.com.guilhermealvessilve.repository;

import br.com.guilhermealvessilve.data.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;

import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class PersonRepository implements PanacheRepository<Person> {

    public List<Person> getAll() {
        return Person.findAll()
                .list();
    }

    public void save(final Person person) {
        person.persist();
    }

    public Optional<Person> find(Long id) {
        return Person.find("id", id)
                .firstResultOptional();
    }

    public void update(Person person) {
        JpaOperations.getEntityManager().merge(person);
    }
}
