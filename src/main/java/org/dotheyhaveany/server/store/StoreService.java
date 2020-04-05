package org.dotheyhaveany.server.store;

import java.util.Optional;

public interface StoreService {

    Iterable<Store> findAll();

    Optional<Store> findById(int id);
}
