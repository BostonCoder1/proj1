package pa2;

import edu.princeton.cs.algs4.*;

public class DegreesOfSeparationBFS {
    // Instance variable.
    SymbolGraph symbolGraph;
    BreadthFirstPaths breadthFirstPaths;

// constructor.
    public DegreesOfSeparationBFS(String file, String delimiter, String source) {
        // creating the object symbolGraph.
        this.symbolGraph = new SymbolGraph(file, delimiter);
        this.breadthFirstPaths = new BreadthFirstPaths(symbolGraph.graph(), symbolGraph.indexOf(source));
    }
 // getSymbolGraph.
    public SymbolGraph getSymbolGraph(){
        return this.symbolGraph;
    }

    public Stack<Integer> graphPath(String sink) {
        Stack<Integer> path = new Stack<Integer>();
        if (symbolGraph.contains(sink)) {
            int t = symbolGraph.indexOf(sink);
            for (int v : breadthFirstPaths.pathTo(t)) {
             //   StdOut.println("   " + symbolGraph.nameOf(v));
                path.push(v);
            }
        }
        // return path
        return path;
    }
// find out the baconNumber
    public int baconNumber(String sink){
        if (symbolGraph.contains(sink)){
            return breadthFirstPaths.distTo(symbolGraph.indexOf(sink)) / 2;
        }
        return -1;
    }
// get the path.
    public BreadthFirstPaths getBreadthFirstPaths(){
        return this.breadthFirstPaths;
    }


    public static void main(String[] args) {
        String src  = args[0];
        String filename ="/Users/sumangautam/Desktop/pa2_solution/src/pa2/"+src;
        String delimiter = args[1];
        String source    = args[2];
        Stack<String> st = new Stack<String>();
        st.push(source);

         StdOut.println("Source: " + source);

        SymbolGraph sg = new SymbolGraph(filename, delimiter);
        Graph G = sg.graph();

        if (!sg.contains(source)) {
            StdOut.println(source + " not in database.");
            return;
        }

        int s = sg.indexOf(source);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        while (!StdIn.isEmpty()) {
            String sink = StdIn.readLine();
            if (sg.contains(sink)) {
                int t = sg.indexOf(sink);
                if (bfs.hasPathTo(t)) {
                    for (int v : bfs.pathTo(t)) {
                        st.push(sg.nameOf(v));
                        StdOut.println("   " + sg.nameOf(v));
                    }
                }
                else {
                    StdOut.println("Not connected");
                }
            }
            else {
                StdOut.println("   Not in database.");
            }
        }

    }
}

