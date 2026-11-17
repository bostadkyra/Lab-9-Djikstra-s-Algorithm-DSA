import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    private final AdjacencyList list = new AdjacencyList();

    public ReadFile(String path) throws IOException {
        //builds adjacency list and discovers all nodes
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = br.readLine()) != null){
                line = line.trim();
                if (line.isEmpty()){ //distinguishes between empty line vs null
                    continue;
                }
                String[] p = line.split("\\s+"); //splits on one or more consecutive whitespace characters
                if (p.length != 3){
                    throw new IOException("Unaccepted line format. Expected 'from to weight' but received " + line);
                }
                try{
                    list.addEdge(p[0], p[1], Double.parseDouble(p[2]));
                } catch (NumberFormatException e){
                    throw new IOException("Bad weight in line: " + line);
                }
            }
        }
    }

    public AdjacencyList getAdjacencyList(){
        return list;
    }
}
