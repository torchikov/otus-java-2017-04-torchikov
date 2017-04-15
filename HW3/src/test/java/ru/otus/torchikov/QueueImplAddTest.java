package ru.otus.torchikov;

import org.junit.Test;

import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by Torchikov Sergei on 15.04.2017.
 */
public class QueueImplAddTest {
    private Queue<String> queue;

    @Test
    public void successAdd() {
        queue = new QueueImpl<>();
        queue.add("Bla");
        assertEquals(1, queue.size());
    }

    @Test(expected = IllegalStateException.class)
    public void addToFullQueue() {
        queue = new QueueImpl<>(1);
        queue.add("Bla");
        queue.add("Bla 2");
    }

    @Test
    public void fillAllQueueUsingAdd() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 16; i++) {
            queue.add("Bla " + i);
        }
        assertEquals(16, queue.size());
    }

    @Test
    public void successOffer() {
        queue = new QueueImpl<>();
        boolean result = queue.offer("Bla");
        assertTrue(result);
    }

    @Test
    public void offerToFullQueueUsingOffer() {
        queue = new QueueImpl<>(1);
        queue.offer("Bla");
        boolean result = queue.offer("Blas 2");
        assertFalse(result);
    }

    @Test
    public void fillAllQueue() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 16; i++) {
            queue.offer("Bla " + i);
        }
        assertEquals(16, queue.size());

    }
}
