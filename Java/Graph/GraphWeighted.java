package Graph;

import java.util.ArrayDeque;

/**
 * Graph class with weighted edges
 * 
 * @author Juan Mart�nez (https://www.linkedin.com/in/martinezgjuan)
 */
public class GraphWeighted {

  static class GraphWE {
    private final int numVertices;
    private int numEdges = 0;
    private ArrayDeque<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public GraphWE(int numVertices) {
      this.numVertices = numVertices;
      adj = (ArrayDeque<Edge>[]) new ArrayDeque[numVertices];
      for (int v = 0; v < numVertices; v++) {
        adj[v] = new ArrayDeque<Edge>();
      }
    }

    @SuppressWarnings("unchecked")
    public GraphWE(int numVertices, Iterable<Edge> edges) {
      this.numVertices = numVertices;
      adj = (ArrayDeque<Edge>[]) new ArrayDeque[numVertices];
      for (int v = 0; v < numVertices; v++) {
        adj[v] = new ArrayDeque<Edge>();
      }
      for (Edge edge : edges)
        addUEdge(edge);
    }

    public int getNumVertices() {
      return numVertices;
    }

    public int getNumEdges() {
      return numEdges;
    }

    public void addDEdge(Edge e) {
      adj[e.from].add(e);
      numEdges++;
    }

    public void addDEdge(int from, int to, int weight) {
      adj[from].add(new Edge(from, to, weight));
      numEdges++;
    }

    public void addUEdge(Edge edge) {
      adj[edge.from].add(edge);
      adj[edge.to].add(new Edge(edge.to, edge.from, edge.weight));
      numEdges++;
    }

    public void addUEdge(int from, int to, int l) {
      adj[from].add(new Edge(from, to, l));
      adj[to].add(new Edge(to, from, l));
      numEdges++;
    }

    public ArrayDeque<Edge> adj(int node) {
      return adj[node];
    }

    public String toString() {
      StringBuilder result = new StringBuilder(numVertices + numEdges);
      result.append("Nodes: " + numVertices + " Edges: " + numEdges + "\n");
      for (int act = 0; act < numVertices; act++) {
        result.append(act + " -> ");
        int count = 0;
        for (Edge e : adj(act)) {
          if (count != 0)
            result.append(", ");
          result.append("(" + e.to + " <" + e.weight + ">)");
          count++;
        }
        result.append('\n');
      }
      return result.toString();
    }
  }

  static class Edge implements Comparable<Edge> {
    public final int from;
    public final int to;
    public final int weight;

    Edge(int from, int to, int l) {
      this.from = from;
      this.to = to;
      this.weight = l;
    }

    Edge(int from, int to) {
      this.from = from;
      this.to = to;
      this.weight = 1;
    }

    @Override
    public boolean equals(Object other) {
      Edge that = (Edge) other;
      return this.from == that.from && this.to == that.to;
    }

    @Override
    public int hashCode() {
      return 31 * (527 + from) + to;
    }

    public int compareTo(Edge that) {
      if (this.weight != that.weight) {
        return Integer.compare(this.weight, that.weight);
      } else if (this.from != that.from) {
        return Integer.compare(this.from, that.from);
      } else {
        return Integer.compare(this.to, that.to);
      }
    }

    public String toString() {
      return "(" + from + ", " + to + ", <" + weight + ">)";
    }
  }

}
