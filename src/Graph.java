/**
 * Author : Samuel Bezerra Gomes
 * Student Number : 2869370
 **/

import java.util.*;

public class Graph<DT> {

    private HashMap<DT,Set<DT>> nextNodesMap;

    private HashMap<DT,LinkedHashSet<DT>> nextNodes;

    private ArrayList<DT> nodes;


    //Instantiating a new graph
    public Graph() {
        nextNodes = new HashMap<DT,LinkedHashSet<DT>>();
        nextNodesMap = new HashMap<DT,Set<DT>>();
        nodes = new ArrayList<DT>();
    }
    
    //Returns a node
    public boolean containsNode( DT f_id ) {
        return nodes.contains(f_id);
    }
    
    //Adjacent set of nodes.
    public Set<DT> getNextNodes(DT s_id) {
        Set<DT> s_nextNodes = nextNodesMap.get(s_id);
        if(s_nextNodes == null) throw new NoSuchElementException("no such node");
        return s_nextNodes;
    }

    // Creates an edge between two nodes (f_id=first id and s_id = second id)
    public void addEdge(DT f_id, DT s_id) {
        LinkedHashSet<DT> f_nextNodes = nextNodes.get(f_id);
        LinkedHashSet<DT> s_nextNodes = nextNodes.get(s_id);
        f_nextNodes.add(s_id);
        s_nextNodes.add(f_id);
    }

    //Adding a new node
    public void addNode(DT s_id) {
        nodes.add(s_id);
        LinkedHashSet<DT> s_nextNodes = new LinkedHashSet<DT>();
        nextNodes.put(s_id, s_nextNodes);
        nextNodesMap.put(s_id, Collections.unmodifiableSet(s_nextNodes));
    }

    //Returns true if the two specified nodes have an edge between them.
    public boolean containsEdge(DT f_id, DT s_id) {
        LinkedHashSet<DT> f_nextNodes = nextNodes.get(f_id);
        if(f_nextNodes == null) throw new NoSuchElementException("First actor not found");
        if(!nextNodes.containsKey(s_id)) throw new NoSuchElementException("Second  actor not found");
        return f_nextNodes.contains(s_id);
    }
    
    public Collection<DT> getNodes() {
        return Collections.unmodifiableCollection(nodes);
    }

}



