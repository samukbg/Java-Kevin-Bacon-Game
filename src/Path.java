
/**
 * Author : Samuel Bezerra Gomes
 * Student Number : 2869370
 **/

public class Path<DT>
{
    //Previous node in BFS
    public DT prev_node;

    //Number of nodes from the source
    public int num_jumps;

    public Path next;
    public Path previous;
    public DT data;

    //Applying the constructor
    public Path(DT prev, int distance) {
        this.prev_node = prev;
        this.num_jumps = distance;
    }

    //Get Previews method
    public DT getPrev() {

        return prev_node;
    }

    //Get number of Edges method
    public int getNumEdges() {

        return num_jumps;
    }

    public Path<DT> getNext()
    {
        return next;
    }

    //Returns the element stored in this node.
    public DT getElement()
    {
        return data;
    }

    //Sets the element stored in this node.
    public void setElement (DT elem)
    {
        data = elem;
    }
}
