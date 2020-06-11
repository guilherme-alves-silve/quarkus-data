package br.com.guilhermealvessilve.repository;

import br.com.guilhermealvessilve.data.Library;
import io.quarkus.infinispan.client.Remote;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class LibraryRepository {

    private final RemoteCache<String, Library> cache;

    private final RemoteCacheManager cacheManager;

    @Inject
    public LibraryRepository(
            @Remote("library") RemoteCache<String, Library> cache,
            RemoteCacheManager cacheManager) {
        this.cache = cache;
        this.cacheManager = cacheManager;
    }

    public Set<String> getCacheNames() {
        return cacheManager.getCacheNames();
    }

    public void save(final Library library) {
        cache.put(library.getId(), library);
    }

    public Library get(final String id) {
        return cache.get(id);
    }
}
