package ru.otus.torchikov;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by Torchikov Sergei on 15.04.2017.
 */
public class QueueImplCommonTest {
    private Queue<String> queue;

    @Test
    public void contains() {
        queue = new QueueImpl<>();
        queue.add("Bla");
        queue.offer("Bla 2");
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        String match = "Bla 2";
        boolean result = queue.contains(match);
        assertTrue(result);
    }

    @Test
    public void containsAdvanced() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 7; i++) {
            queue.add("Bla " + i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(7, queue.size());

        queue.remove();
        assertEquals(6, queue.size());

        for (int i = 10; i < 20; i++) {
            queue.add("Bla " + i);
        }
        assertEquals(16, queue.size());

        queue.remove();
        queue.remove();
        assertEquals(14, queue.size());
        boolean resulTrue = queue.contains("Bla 4");
        boolean resultFalse = queue.contains("Bla 0");
        assertTrue(resulTrue);
        assertFalse(resultFalse);
    }

    @Test
    public void containsWithNullArgument() {
        queue = new QueueImpl<>();
        queue.add("Bla");
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        boolean result = queue.contains(null);
        assertFalse(result);
    }

    @Test
    public void containsWithAnotherType() {
        queue = new QueueImpl<>();
        queue.add("Bla");
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        boolean result = queue.contains(new Integer(1));
        assertFalse(result);
    }

    @Test
    public void clear() {
        queue = new QueueImpl<>();
        queue.add("Bla");
        queue.offer("Bla 2");
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        queue.clear();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void clearAndAdd() {
        queue = new QueueImpl<>();
        queue = new QueueImpl<>();
        queue.add("Bla");
        queue.offer("Bla 2");
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        queue.clear();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        queue.add("Bla");
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
    }

    @Test
    public void iterator() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 9; i++) {
            queue.add("Bla " + i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(9, queue.size());
        queue.remove();
        queue.remove();
        queue.offer("Bla 15");
        assertEquals(8, queue.size());

        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            assertNotNull(iterator.next());
        }
    }

    @Test
    public void toArray() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 10; i++) {
            queue.add("Bla " + i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(10, queue.size());
        queue.remove();
        queue.remove();
        queue.offer("Bla 15");
        assertEquals(9, queue.size());

        Object[] array = queue.toArray();
        assertEquals(9, array.length);
    }

    @Test
    public void removeAll() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 16; i++) {
            queue.add("Bla " + i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(16, queue.size());
        List<String> list = Arrays.asList("Bla 4", "Bla 12", "Bla 0", "Asdjas");
        queue.removeAll(list);
        assertEquals(13, queue.size());
        String head = queue.element();
        assertEquals("Bla 1", head);
    }

    @Test
    public void removeAllAndAdd() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 16; i++) {
            queue.add("Bla " + i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(16, queue.size());
        List<String> list = Arrays.asList("Bla 4", "Bla 12", "Bla 0", "Asdjas");
        queue.removeAll(list);
        assertEquals(13, queue.size());
        String head = queue.element();
        assertEquals("Bla 1", head);
        queue.add("Bla 100");
        assertEquals(14, queue.size());
    }

    @Test
    public void retainAll() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 16; i++) {
            queue.add("Bla " + i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(16, queue.size());
        List<String> list = Arrays.asList("Bla 4", "Bla 12", "Bla 0", "Asdjas");
        queue.retainAll(list);
        assertEquals(3, queue.size());
    }

    @Test
    public void retainAllAndAdd() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 16; i++) {
            queue.add("Bla " + i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(16, queue.size());
        List<String> list = Arrays.asList("Bla 4", "Bla 12", "Bla 0", "Asdjas");
        queue.retainAll(list);
        assertEquals(3, queue.size());
        queue.add("Bla 100");
        queue.offer("Bla 101");
        assertEquals(5, queue.size());
    }

    @Test
    public void remove() {
        queue = new QueueImpl<>();
        queue.add("Bla");
        queue.offer("Bla 2");
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        boolean result = queue.remove("Bla");
        assertEquals(1, queue.size());
        assertTrue(result);
    }

    @Test
    public void removeNotContained() {
        queue = new QueueImpl<>();
        queue.add("Bla");
        queue.offer("Bla 2");
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        boolean result = queue.remove("");
        assertFalse(result);
        assertEquals(2, queue.size());
    }

    @Test
    public void containsAll() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 16; i++) {
            queue.add("Bla " + i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(16, queue.size());
        List<String> list = Arrays.asList("Bla 1", "Bla 10", "Bla 12", "Bla 5");
        boolean result = queue.containsAll(list);
        assertTrue(result);
    }

    @Test
    public void containsAllFailed() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 16; i++) {
            queue.add("Bla " + i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(16, queue.size());
        List<String> list = Arrays.asList("Bla 1", "Bla 10", "Bla 12", "Bla 500");
        boolean result = queue.containsAll(list);
        assertFalse(result);
    }

    @Test
    public void addAll() {
        queue = new QueueImpl<>();
        queue.add("Bla 0");
        queue.offer("Bla 1");
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        List<String> list = Arrays.asList("Bla 100", "Bla 101", "Bla 102");
        boolean result = queue.addAll(list);
        assertTrue(result);
        assertEquals(5, queue.size());
    }

    @Test
    public void addAllNotSpace() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 14; i++) {
            queue.add("Bla " + i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(14, queue.size());
        List<String> list = Arrays.asList("Bla 100", "Bla 101", "Bla 102");
        boolean result = queue.addAll(list);
        assertFalse(result);
        assertEquals(14, queue.size());
    }

    @Test
    public void addAllAdvanced() {
        queue = new QueueImpl<>();
        for (int i = 0; i < 14; i++) {
            queue.add("Bla " + i);
        }
        assertFalse(queue.isEmpty());
        assertEquals(14, queue.size());
        queue.remove();
        queue.remove();
        queue.offer("Bla 300");
        assertEquals(13, queue.size());
        List<String> list = Arrays.asList("Bla 100", "Bla 101", "Bla 102", "Bla 103");
        boolean resultFalse = queue.addAll(list);
        assertFalse(resultFalse);
        assertEquals(13, queue.size());
        list = Arrays.asList("Bla 200", "Bla 201", "Bla 202");
        boolean resultTrue = queue.addAll(list);
        assertTrue(resultTrue);
        assertEquals(16, queue.size());
    }

    @Test(expected = NotImplementedException.class)
    public void toArrayNotImplemented() {
        queue = new QueueImpl<>();
        String[] array = new String[0];
        queue.toArray(array);
    }


}

