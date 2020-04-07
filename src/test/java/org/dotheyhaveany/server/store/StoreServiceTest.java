package org.dotheyhaveany.server.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreServiceTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreService storeService;

    @Test
    void findAll() {
        assertFalse(storeService.findAll().iterator().hasNext());

        storeRepository.save(new Store("Test Store", "Test address"));

        assertTrue(storeService.findAll().iterator().hasNext());
    }

    @Test
    void findById() {
        final Store testStore = new Store("Test Store", "Test address");
        storeRepository.save(testStore);

        assertNotNull(testStore.getId());

        final Optional<Store> maybeStore = storeService.findById(testStore.getId());

        assertTrue(maybeStore.isPresent());
        assertEquals(testStore, maybeStore.get());
    }
}