package com.me.rocks.queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RocksStoreShould extends RocksShould {

    private StoreOptions options;
    private RocksStore rocksStore;
    private RocksQueue queue;

    @Before public void
    setUp() {
        options = StoreOptions.builder().database(generateDBName()).build();
        rocksStore = new RocksStore(options);
        queue = rocksStore.createQueue(generateQueueName());
    }

    @Test(expected = RuntimeException.class) public void
    when_create_store_without_database_should_throws_exception() {
        StoreOptions options = new StoreOptions.Builder().build();
        new RocksStore(options);
    }

    @Test public void
    when_create_a_new_queue_its_size_should_approximate_to_zero() {
        assertNotNull(queue);
        assertEquals(queue.getHeadIndex(), 0);
        assertEquals(queue.getTailIndex(),0);
        assertEquals(queue.approximateSize(), 0);
    }

    @Test public void
    when_create_queue_by_the_same_name_should_always_return_the_same_queue() {
        String name = generateQueueName();
        RocksQueue q1 = rocksStore.createQueue(name);
        RocksQueue q2 = rocksStore.createQueue(name);

        assertEquals(q1, q2);
    }

    @After public void
    destroy() {
        rocksStore.close();
    }
}
