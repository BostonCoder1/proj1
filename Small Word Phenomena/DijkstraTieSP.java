package pa2;

import edu.princeton.cs.algs4.*;

public class DijkstraTieSP {
    // Instance variable
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;
    private int[] edgesCount;

    // constructor
    public DijkstraTieSP(EdgeWeightedDigraph G, int s) {
        // checking the negative edges
        for (DirectedEdge edg : G.edges()) {
            if (edg.weight() < 0)
                throw new IllegalArgumentException("edge " + edg + " has negative weight");
        }
        // update the variable, distance, edge and number of edge.
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        edgesCount = new int[G.V()];

        validateVertex(s);
        // initialize distTO[s], and numbOfEdges[] to 0 for other than source.
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
            edgesCount[v] = Integer.MAX_VALUE;
        }
        // initialize distTO[s], and numbOfEdges to 0 for source.
        distTo[s] = 0.0;
        edgesCount[s] = 0;

        // relax the vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge edg : G.adj(v))
                relax(edg);
        }

    }

    // relax edge edg and update pq if changed
    private void relax(DirectedEdge edg) {
        int v = edg.from(), w = edg.to();
        if (distTo[w] > distTo[v] + edg.weight()) {
            distTo[w] = distTo[v] + edg.weight();
            edgeTo[w] = edg;
            edgesCount[w] = edgesCount[v] + 1;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else pq.insert(w, distTo[w]);
        }
        if (distTo[w] == distTo[v] + edg.weight()) {
            if (edgesCount[w] > edgesCount[v] + 1) edgesCount[w] = edgesCount[v] + 1;
            edgeTo[w] = edg;
        }
    }

    // find the distance to v.
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    // check whether there is path or not.
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    // Iterable
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();

        for (DirectedEdge edg = edgeTo[v]; edg != null; edg = edgeTo[edg.from()]) {
            path.push(edg);
        }
        // return the path
        return path;
    }

    // validating the vertex.
    private void validateVertex(int v) {
        int V = distTo.length;
        // v cant be less than 0 and more than V.
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " it is not in bound " + (V - 1));
    }

    // Public void static main.
    public static void main(String[] args) {
        // read the input File.
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        // read int from command line.
        int s = Integer.parseInt(args[1]);

        // compute shortest paths
        DijkstraSP sourcepath = new DijkstraSP(G, s);
        DijkstraTieSP sourcePathTie = new DijkstraTieSP(G, s);


// path from s to t, Where s is source.
        for (int i = 0; i < G.V(); i++) {
            if (sourcePathTie.hasPathTo(i)) {
                StdOut.printf("%d to %d (%.2f)  ", s, i, sourcePathTie.distTo(i));
                for (DirectedEdge edg : sourcePathTie.pathTo(i)) {
                    StdOut.print(edg + "   ");
                }
                StdOut.println();
            }
            // There is no path.
            else {
                StdOut.printf("%d to %d         no path", s, i);
                System.out.println();
            }
        }
    }

}
