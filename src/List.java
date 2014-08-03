/**
 * Author : Samuel Bezerra Gomes
 * Student Number : 2869370
 **/

public interface List {

    public boolean isEmpty();

    public int size();

    public Object get(int index);

    public void insert(Object obj);

    public boolean insertAfter(Object Operation, int key);

    public Object delete(int index);
}
