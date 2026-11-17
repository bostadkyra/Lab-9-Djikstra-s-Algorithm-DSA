import java.util.*;

public class Dijkstra {
    private final AdjacencyList graph;
    private double[] dist;
    private Node[] prev;

    //priority-queue entry (a node and the distance it was queued with)
    private static class Entry{
        final Node node;
        final double dist;
        Entry(Node node, double dist){
            this.node = node;
            this.dist = dist;
        }
    }

    public Dijkstra(AdjacencyList graph){
        this.graph = graph;
    }

    public void run(Node source){
        int n = graph.getNodes().size();
        dist = new double[n];
        prev = new Node[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY); //sets each distance to start at infinity
        dist[source.getIndex()] = 0; //sets the distance of the starting node to 0
        PriorityQueue<Entry> pq = new PriorityQueue<>(Comparator.
                comparingDouble((Entry e) -> e.dist)); //tells the comparator to compare the distance of each entry
        pq.add(new Entry(source, 0));

        while (!pq.isEmpty()){
            Entry curr = pq.poll();

            //skips entries where a shorter route to this node was already found
            if (curr.dist > dist[curr.node.getIndex()]){
                continue;
            }

            for (Edge e : graph.getNeighbors(curr.node)){
                double newDist = curr.dist + e.weight;
                if (newDist < dist[e.to.getIndex()]){
                    dist[e.to.getIndex()] = newDist;
                    prev[e.to.getIndex()] = curr.node;
                    pq.add(new Entry(e.to, newDist));
                }
            }
        }
    }

    public double getDistance(Node target){
        return dist[target.getIndex()];
    }

    //would return false if graph is disconnected
    public boolean isReachable(Node target){
        return dist[target.getIndex()] != Double.POSITIVE_INFINITY;
    }

    public List<Node> getPath(Node target){
        LinkedList<Node> path = new LinkedList<>();
        if (!isReachable(target)){
            return path; //returns an empty list if unreachable
        }
        for (Node n = target; n != null; n = prev[n.getIndex()]){ //works backwards from target to source
            path.addFirst(n); //adds each node to the front of the path which reverses the order
        }
        return path;
    }
}
