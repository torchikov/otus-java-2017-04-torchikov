package ru.otus.torchikov;

import java.util.*;

/**
 * Created by Torchikov Sergei on 15.04.2017.
 *
 * See tests
 */
public class QueueImpl<E> implements Queue<E> {
    private Element<E>[] array;
    private int head;
    private int tail;
    private int size;
    private int elementCount;

    @SuppressWarnings("unchecked")
    public QueueImpl(int size) {
        this.size = size;
        this.array = new Element[size];
        this.elementCount = 0;
        this.head = 0;
        this.tail = -1;
    }

    @SuppressWarnings("unchecked")
    public QueueImpl() {
        this(16);
    }


    public int size() {
        return elementCount;
    }

    public boolean isEmpty() {
        return elementCount == 0;
    }

    public boolean contains(Object o) {
        if (Objects.isNull(o)) {
            return false;
        }
        if (elementCount == 0) {
            return false;
        }

        boolean isExist = false;
        for (int i = 0; i < size; i++) {
            if (Objects.isNull(array[i]) || !array[i].isActual()) {
                continue;
            }
            if (array[i].getValue().equals(o)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = -1;

            @Override
            public boolean hasNext() {
                index++;
                if (index == size || Objects.isNull(array[index])) {
                    return false;
                } else {
                    if (!array[index].isActual()) {
                        index++;
                        return hasNext();
                    } else {
                        return true;
                    }
                }
            }

            @Override
            public E next() {
                return array[index].getValue();
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[elementCount];
        int index = 0;
        for (E e : this) {
            newArray[index] = e;
            index++;
        }
        return newArray;
    }

    @Override
    public <T> T[] toArray(T[] a) { //Честно говоря не знаю как это делать
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (elementCount == 0) {
            return false;
        }
        boolean isNeedToChange = false;

        for (Object obj : c) {
            for (int i = 0; i < size; i++) {
                if (Objects.isNull(array[i]) || !array[i].isActual()) {
                    continue;
                }
                if (array[i].getValue().equals(obj)) {
                    isNeedToChange = true;
                    array[i].setActual(false);
                }
            }
        }
        if (!isNeedToChange) {
            return false;
        } else {
            doDefragmentation();
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    private void doDefragmentation() {
        Element[] newArray = new Element[size];
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (Objects.nonNull(array[i]) && array[i].isActual()) {
                newArray[index] = array[i];
                index++;
            }
        }
        tail = index - 1;
        head = 0;
        elementCount = index;
        array = newArray;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (elementCount == 0) {
            return false;
        }
        boolean isNeedToChange = false;

        for (int i = 0; i < size; i++) {
            if (Objects.isNull(array[i]) || !array[i].isActual()) {
                continue;
            }
            if (!c.contains(array[i].getValue())) {
                array[i].setActual(false);
                isNeedToChange = true;
            }
        }

        if (!isNeedToChange) {
            return false;
        } else {
            doDefragmentation();
            return true;
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        array = new Element[size];
        tail = -1;
        head = 0;
        elementCount = 0;
    }

    @Override
    public boolean remove(Object o) {
        if (Objects.isNull(o) || elementCount == 0) {
            return false;
        }

        boolean isNeedToChange = false;

        for (int i = 0; i < size; i++) {
            if (Objects.isNull(array[i]) || !array[i].isActual()) {
                continue;
            }
            if (array[i].getValue().equals(o)) {
                isNeedToChange = true;
                array[i].setActual(false);
                break;
            }
        }
        if (!isNeedToChange) {
            return false;
        } else {
            doDefragmentation();
            return true;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return false;
        }
        int freeCells = size - elementCount;
        if (c.size() > freeCells) {
            return false;
        }
        doDefragmentation();
        for (E ob : c) {
            addElement(ob);
        }
        return true;
    }

    @Override
    public boolean add(E e) {
        if (isQueueFull()) {
            throw new IllegalStateException("The queue is full!");
        } else {
            addElement(e);
            return true;
        }
    }

    @Override
    public boolean offer(E e) {
        if (isQueueFull()) {
            return false;
        } else {
            addElement(e);
            return true;
        }
    }

    @Override
    public E remove() {
        if (isQueueEmpty()) {
            throw new NoSuchElementException("The queue is empty!");
        } else {
            return getAndDeleteHeadElement();
        }
    }

    @Override
    public E poll() {
        if (isQueueEmpty()) {
            return null;
        } else {
            return getAndDeleteHeadElement();
        }
    }

    @Override
    public E element() {
        if (isQueueEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        } else {
            return getHeadElement();
        }
    }

    @Override
    public E peek() {
        if (isQueueEmpty()) {
            return null;
        } else {
            return getHeadElement();
        }
    }

    @SuppressWarnings("unchecked")
    private E getAndDeleteHeadElement() {
        Element element = array[head];
        element.setActual(false);
        E value = (E) element.getValue();
        head++;
        if (head == size - 1) {
            head = 0;
        }
        elementCount--;
        return value;
    }

    private E getHeadElement() {
        return array[head].getValue();
    }

    private void addElement(E e) {
        tail++;
        if (tail == size) {
            tail = 0;
        }
        array[tail] = new Element(e);
        elementCount++;
    }

    private boolean isQueueFull() {
        return elementCount == size;
    }

    private boolean isQueueEmpty() {
        return elementCount == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("QueueImpl{");
        for (int i = 0; i < elementCount; i++) {
            if (Objects.nonNull(array[i]) && array[i].isActual()) {
                sb.append(array[i].getValue()).append("; ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private class Element<E> {
        private E value;
        private boolean isActual;

        Element(E value) {
            this.value = value;
            this.isActual = true;
        }

        E getValue() {
            return value;
        }

        boolean isActual() {
            return isActual;
        }

        void setActual(boolean actual) {
            isActual = actual;
        }
    }

}
