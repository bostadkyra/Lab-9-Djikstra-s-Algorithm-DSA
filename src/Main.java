import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;

public class Main {

    //formats the distance to 6 decimal places and removes trailing zeros
    private static String format(double d){
        return BigDecimal.valueOf(d).setScale(6, RoundingMode.HALF_UP).
                stripTrailingZeros().toPlainString();
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        AdjacencyList graph = null;

        while (graph == null){
            System.out.println("Graph file to read from (ex: graph1.txt): ");
            String path = in.nextLine().trim();
            try{
                graph = new ReadFile(path).getAdjacencyList();
            } catch (IOException e){
                System.out.println("Could not read the graph file: " + e.getMessage());
            }
        }

        List<Node> nodes = graph.getNodes();
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++){
            if (i > 0){
                names.append(", "); //starts adding commas after first node
            }
            names.append(nodes.get(i));
        }
        System.out.println("\nList of nodes: " + names + "\n");

        Dijkstra dijkstra = new Dijkstra(graph);

        while (true){
            System.out.println("\nNode to start at (blank to quit): ");
            String label = in.nextLine().trim();
            if (label.isEmpty()){
                break; //quits program if blank entered
            }

            Node start = graph.getNode(label);
            if (start == null){
                System.out.println("No node named " + label + " found in the graph.");
                continue;
            }

            dijkstra.run(start);
            System.out.println("\n");

            for (Node target : nodes){
                if (!dijkstra.isReachable(target)){
                    System.out.println(start + " -> " + target + " unreachable"); //if graph is disconnected
                    continue;
                }
                StringBuilder line = new StringBuilder();
                List<Node> path = dijkstra.getPath(target);
                for (int i = 0; i < path.size(); i++){
                    if (i > 0){
                        line.append(" -> ");
                    }
                    line.append(path.get(i));
                }
                line.append("  dist: ").append(format(dijkstra.getDistance(target)));
                System.out.println(line);
            }
        }
        System.out.println("Program Exited.");
    }
}
