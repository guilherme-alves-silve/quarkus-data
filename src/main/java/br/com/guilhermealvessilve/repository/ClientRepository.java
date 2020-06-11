package br.com.guilhermealvessilve.repository;

import br.com.guilhermealvessilve.data.Client;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class ClientRepository {

    private final EntityManager entityManager;

    @Inject
    public ClientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Client> getAll() {
        return entityManager.createQuery("SELECT c FROM Client c", Client.class)
                .getResultList();
    }

    public void save(final Client client) {
        entityManager.persist(client);
    }

    public Optional<Client> find(Integer id) {
        return entityManager.createQuery("SELECT c FROM Client c WHERE c.id = :id", Client.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    public void update(Client client) {
        entityManager.merge(client);
    }
}
