package ru.otus.torchikov;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by Torchikov Sergei on 15.04.2017.
 */
public class QueueImplRemoveTest {
    private Queue<String> queue;

    @Test
    public void successRemove() {
        queue = new QueueImpl<>();
        queue.add("Bla");
        assertEquals(1, queue.size());
        queue.remove();
        assertTrue(queue.isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromEmptyQueue() {
        queue = new QueueImpl<>();
        queue.remove();
    }

    @Test
    public void poll() {
        queue = new QueueImpl<>();
        queue.add("Bla");
        queue.offer("Bla 2");
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        String result = queue.poll();
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals("Bla", result);
    }

    @Test
    public void pollWithOneElement() {
        queue = new QueueImpl<>();
        queue.offer("Bla");
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        String result = queue.poll();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertEquals("Bla", result);
    }

    @Test
    public void pollWithNoElements() {
        queue = new QueueImpl<>();
        assertTrue(queue.isEmpty());
        String result = queue.poll();
        assertNull(result);
    }


}
