package org.dotheyhaveany.server.store;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(final StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Iterable<Store> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public Optional<Store> findById(final int id) {
        return storeRepository.findById(id);
    }
}
