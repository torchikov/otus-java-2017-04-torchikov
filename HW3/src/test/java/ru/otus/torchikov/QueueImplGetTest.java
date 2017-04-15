package ru.otus.torchikov;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by Torchikov Sergei on 15.04.2017.
 */
public class QueueImplGetTest {
    private Queue<String> queue;

    @Test
    public void element() {
        queue = new QueueImpl<>();
        queue.add("Bla");
        queue.offer("Bla 2");
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        String result = queue.element();
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        assertEquals("Bla", result);
    }

    @Test(expected = NoSuchElementException.class)
    public void elementWithNoElement() {
        queue = new QueueImpl<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        queue.element();
    }

    @Test
    public void peek() {
        queue = new QueueImpl<>();
        queue.add("Bla");
        queue.offer("Bla 2");
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        String result = queue.peek();
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        assertEquals("Bla", result);
    }

    @Test
    public void peekWithNoElement() {
        queue = new QueueImpl<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        String result = queue.peek();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(result);
    }

}
