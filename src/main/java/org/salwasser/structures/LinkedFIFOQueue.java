package org.salwasser.structures;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Zac on 6/11/2016.
 */
public class LinkedFIFOQueue<T> implements Queue<T>{
    private class LinkedQueueElement<T> {
        public T val = null;
        public LinkedQueueElement<T> next = null;
    }

    private LinkedQueueElement<T> head = null;
    private LinkedQueueElement<T> tail = null;
    private Class elementValClass = null;
    private int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean containsAll(Collection<?> colToCheck) {
        Collection<T> toCheck = (Collection<T>)colToCheck;
        HashSet<T> checkList = new HashSet<T>();
        checkList.addAll(asList());
        for (T val : toCheck) {
            if (!checkList.contains(val)) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(Object objToCheck) {
        T toCheck = (T)objToCheck;
        LinkedQueueElement<T> inspector = head;
        while (inspector != null) {
            if (inspector.val.equals(toCheck)) {
                return true;
            }
            inspector = inspector.next;
        }
        return false;
    }

    public T element() {
        if (size == 0) {
            throw new NoSuchElementException("Empty!");
        }
        return peek();
    }

    public T peek() {
        if (head == null) {
            return null;
        }
        return head.val;
    }

    public Iterator<T> iterator() {
        return asList().iterator();
    }

    public boolean retainAll(Collection<?> toRetain) {
        Collection<T> checker = (Collection<T>)toRetain;
        if (head == null) {
            return true;
        }
        LinkedQueueElement<T> inspector = head;
        tail = null;
        head = null;

        while (inspector != null) {
            if (checker.contains(inspector.val)) {
                if (tail != null) {
                    tail.next = inspector;
                }
                tail = inspector;
                if (head == null) {
                    head = inspector;
                }
            }
            inspector = inspector.next;
        }

        size = asList().size();

        return true;
    }

    public boolean removeAll(Collection<?> toRemove) {
        for (T val : (Collection<T>)toRemove) {
            this.remove(val);
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> toInsert) {
        for (T val : toInsert) {
            this.add(val);
        }
        return true;
    }

    public boolean offer(T toInsert) {
        return add(toInsert);
    }

    public boolean add(T addAt, T toInsert) {
        return add(addAt, toInsert, 0);
    }

    public boolean add(T addAt, T toInsert, int instance) {
        LinkedQueueElement<T> elemToInsert = new LinkedQueueElement<T>();
        elemToInsert.val = toInsert;
        LinkedQueueElement<T> inspector = head;
        while (inspector != null) {
            if (inspector.val.equals(addAt)) {
                if (instance == 0) {
                    elemToInsert.next = inspector.next;
                    inspector.next = elemToInsert;
                    size++;
                    return true;
                }
                instance--;
            }
            inspector = inspector.next;
        }
        return false;
    }

    public boolean add(T toInsert) {
        LinkedQueueElement<T> elemToInsert = new LinkedQueueElement<T>();
        elemToInsert.val = toInsert;
        if (tail == null) {
            tail = elemToInsert;
        } else {
            tail.next = elemToInsert;
            tail = elemToInsert;
        }

        if (head == null) {
            head = tail;
        }

        size++;
        return true;
    }

    private ArrayList<T> asList() {
        ArrayList<T> arrayToConvert = new ArrayList<T>();
        LinkedQueueElement<T> inspector = head;
        while (inspector != null) {
            arrayToConvert.add(inspector.val);
            inspector = inspector.next;
        }

        return arrayToConvert;
    }

    public T[] toArray() {
        if (elementValClass == null) {
            throw new RuntimeException("Generic class must be set at runtime if you wish to use toArray().");
        }
        ArrayList<T> arrayToConvert = asList();
        T[] retval = (T[])Array.newInstance(elementValClass, arrayToConvert.size());
        return arrayToConvert.toArray(retval);
    }

    public <T>T[] toArray(T[] container) {
        return asList().toArray(container);
    }

    public void clear() {
        head = null;
        tail = null;
    }

    public boolean remove(Object toRemove) {
        return remove((T)toRemove, false);
    }

    private boolean remove(T toRemove, boolean isPoll) {
        if (head == null) {
            return false;
        }
        LinkedQueueElement<T> inspector = head;
        tail = null;
        boolean retval = false;
        while (inspector != null) {
            if (inspector.val.equals(toRemove)) {
                retval = true;
                size--;
                if (tail != null) {
                    tail.next = inspector.next;
                } else {
                    head = inspector.next;
                }
                if (isPoll) {
                    head = inspector.next;
                    return true;
                }
            } else {
                tail = inspector;
            }
            inspector = inspector.next;
        }
        return retval;
    }

    public T poll(){
        if (head == null) {
            return null;
        }
        T retval = head.val;
        remove(retval, true);
        return retval;
    }

    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Empty!");
        }
        return poll();
    }

    @Override
    public String toString() {
        return asList().toString();
    }

}
