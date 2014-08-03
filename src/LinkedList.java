/**
 * Author : Samuel Bezerra Gomes
 * Student Number : 2869370
**/

import java.util.*;
import java.util.List;

public class LinkedList<DT> extends AbstractSequentialList<DT> implements
        List<DT> {

    transient int size = 0;

    transient Node<DT> voidNode;

    private static class Node<DT> {
        DT data;

        Node<DT> previous, next;

        Node(DT _data, Node<DT> _previous, Node<DT> _next) {
            data = _data;
            previous = _previous;
            next = _next;
        }
    }

    private static class NodeIterator<DT> implements ListIterator<DT> {
        int pos, expectedModCount;

        LinkedList<DT> list;

        Node<DT> link, lastNode;

        NodeIterator(LinkedList<DT> object, int location) {
            list = object;
            expectedModCount = list.modCount;
            if (location >= 0 && location <= list.size) {
                link = list.voidNode;
                if (location < list.size / 2) {
                    for (pos = -1; pos + 1 < location; pos++) {
                        link = link.next;
                    }
                } else {
                    for (pos = list.size; pos >= location; pos--) {
                        link = link.previous;
                    }
                }
            }
        }
        // Adding a new node
        public void add(DT object) {
            if (expectedModCount == list.modCount) {
                Node<DT> next = link.next;
                Node<DT> newNode = new Node<DT>(object, link, next);
                link.next = newNode;
                next.previous = newNode;
                link = newNode;
                lastNode = null;
                pos++;
                expectedModCount++;
                list.size++;
                list.modCount++;
            } else {
                throw new ConcurrentModificationException();
            }
        }

        public boolean hasNext() {
            return link.next != list.voidNode;
        }

        public boolean hasPrevious() {
            return link != list.voidNode;
        }

        public DT next() {
            if (expectedModCount == list.modCount) {
                LinkedList.Node<DT> next = link.next;
                if (next != list.voidNode) {
                    lastNode = link = next;
                    pos++;
                    return link.data;
                }
                throw new NoSuchElementException();
            }
            throw new ConcurrentModificationException();
        }

        public int nextIndex() {
            return pos + 1;
        }

        public DT previous() {
            if (expectedModCount == list.modCount) {
                if (link != list.voidNode) {
                    lastNode = link;
                    link = link.previous;
                    pos--;
                    return lastNode.data;
                }
                throw new NoSuchElementException();
            }
            throw new ConcurrentModificationException();
        }

        public int previousIndex() {
            return pos;
        }
        // removing Node
        public void remove() {
            if (lastNode != null) {
                Node<DT> next = lastNode.next;
                Node<DT> previous = lastNode.previous;
                previous.next = next;
                next.previous = previous;
                if (lastNode == link) {
                    pos--;
                }
                link = previous;
                lastNode = null;
                list.size--;
            }
        }

        public void set(DT object) {
            if (lastNode != null) {
                lastNode.data = object;
            }
        }
    }


    //Creating a new instance
    public LinkedList() {
        voidNode = new Node<DT>(null, null, null);
        voidNode.previous = voidNode;
        voidNode.next = voidNode;
    }

    @Override
    public int size() {
        return size;
    }

    //Adding a object in the List

    @Override
    public void add(int location, DT object) {
        if (location >= 0 && location <= size) {
            Node<DT> link = voidNode;
            if (location < (size / 2)) {
                for (int i = 0; i <= location; i++) {
                    link = link.next;
                }
            } else {
                for (int i = size; i > location; i--) {
                    link = link.previous;
                }
            }
            Node<DT> previous = link.previous;
            Node<DT> newNode = new Node<DT>(object, previous, link);
            previous.next = newNode;
            link.previous = newNode;
            size++;
            modCount++;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public ListIterator<DT> listIterator(int location) {
        return new NodeIterator<DT>(this, location);
    }

    // To delete the fist element in the list
    public DT removeFirst() {
        Node<DT> first = voidNode.next;
        Node<DT> next = first.next;
        voidNode.next = next;
        next.previous = voidNode;
        size--;
        modCount++;
        return first.data;
    }

    // Deletes an object of type Node
    @Override
    public DT remove(int location) {
        Node<DT> link = voidNode;
        if (location < (size / 2)) {
            for (int i = 0; i <= location; i++) {
                link = link.next;
            }
        } else {
            for (int i = size; i > location; i--) {
                link = link.previous;
            }
        }
        Node<DT> previous = link.previous;
        Node<DT> next = link.next;
        previous.next = next;
        next.previous = previous;
        size--;
        modCount++;
        return link.data;
    }

}
