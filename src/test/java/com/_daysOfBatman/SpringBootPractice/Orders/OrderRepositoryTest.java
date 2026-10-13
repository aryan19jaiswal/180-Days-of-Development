package com._daysOfBatman.SpringBootPractice.Orders;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderRepositoryTest {

    private final OrderRepository repo = new OrderRepository();

    @Test
    void seededWithThreeOrdersInIdOrder() {
        assertEquals(3, repo.findAll().size());
        assertEquals(1L, repo.findAll().get(0).id());
    }

    @Test
    void replaceOnMissingIdDoesNotCreate() {
        assertTrue(repo.replace(999L, "x", OrderStatus.CREATED).isEmpty());
        assertTrue(repo.findById(999L).isEmpty());
    }

    @Test
    void deleteReportsWhetherSomethingWasRemoved() {
        assertTrue(repo.deleteById(1L));
        assertFalse(repo.deleteById(1L));
    }

    @Test
    void concurrentInsertsGetUniqueIds() throws Exception {
        int threads = 16, perThread = 200;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);
        Set<Long> ids = java.util.concurrent.ConcurrentHashMap.newKeySet();
        for (int t = 0; t < threads; t++) {
            pool.submit(() -> {
                start.await();
                for (int i = 0; i < perThread; i++) {
                    ids.add(repo.insert("x", OrderStatus.CREATED).id());
                }
                return null;
            });
        }
        start.countDown();
        pool.shutdown();
        assertTrue(pool.awaitTermination(30, TimeUnit.SECONDS));
        assertEquals(threads * perThread, ids.size());
        assertEquals(3 + threads * perThread, repo.findAll().size());
    }
}
