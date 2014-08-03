/**
 * Author : Samuel Bezerra Gomes
 * Student Number : 2869370
 **/

import java.util.Collection;
import java.util.HashMap;

public class DijkstraAlgorithm {

    private String firstActor = "";

    private String secondActor = "";

    private Graph<String> graph;

    private LinkedList<String> nodeList;

    private HashMap<String, Path<String>>range;

    public static int distance = 0;
    
    private int _distance = 0;

    //Constructor
    public DijkstraAlgorithm(Graph<String> _graph, String _firstActor, String _secondActor){
        graph = _graph;
        firstActor = _firstActor;
        secondActor = _secondActor;

        
        Collection<String> nodes = _graph.getNodes(); //Instantiating nodes
        range = new HashMap<String, Path<String>>(nodes.size()); //Instantiating distance
        nodeList = new LinkedList<String>(); //Instantiating list of nodes
 
        range.put(firstActor, new Path<String>("",0)); //adding the first void node
        nodeList.add(firstActor);

        while(!nodeList.isEmpty()){
            String deletedNode = nodeList.removeFirst();
            Collection<String> nextNode = graph.getNextNodes(deletedNode);
            _distance++;

            for(String key : nextNode){
                if(!range.containsKey(key)){
                    range.put(key, new Path<String>(deletedNode, _distance));
                    nodeList.add(key);
                }
            }
        }
        distance = 0;
        while(secondActor.length() > 0) {
            System.out.print(secondActor);
            if(range.get(secondActor).getNumEdges() > 0)
                System.out.print(" => ");
            distance++;
            secondActor =range.get(secondActor).getPrev();

        }
        System.out.println("\n\nThe Actor"+_secondActor+ " is in the level/layer "  + distance/2+ " from " +_firstActor+".");

    }

}
