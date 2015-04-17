package Graph;

import java.util.ArrayDeque;

/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan) (based on Coursera's course
 *         Algorithms 2 by Princeton University)
 */
public class MaximumFlow {

  static int INF = Integer.MAX_VALUE / 2;
  static FlowEdge[] edgeTo;
  static boolean[] visited;

  /**
   * EdmondsKarp version of FordFulkerson's maximum flow algorithm
   * 
   * @return Maximum flow of the network given
   * @time O(|V| * |E|^2)
   * @tested UVA_820, UVA_10480, UVA_11380
   */
  static int fordFulkerson(GraphMaxFlow graph, int source, int sink) {
    int totalFlow = 0;
    edgeTo = new FlowEdge[graph.numVertices];

    while (hasAugPath(graph, source, sink)) {
      // Find minimum capacity in path from source to dest
      int min = INF;
      for (int node = sink; node != source; node = edgeTo[node].other(node)) {
        min = Math.min(min, edgeTo[node].residualCapacityTo(node));
      }
      // Increase flow in path from source to dest
      for (int node = sink; node != source; node = edgeTo[node].other(node)) {
        edgeTo[node].addResidualFlowTo(node, min);
      }
      totalFlow += min;
    }
    return totalFlow;
  }

  static boolean hasAugPath(GraphMaxFlow graph, int source, int dest) {
    visited = new boolean[graph.numVertices];

    ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
    queue.add(source);

    while (!queue.isEmpty()) {
      int node = queue.removeFirst();
      if (node == dest) {
        return true;
      }
      if (visited[node]) {
        continue;
      }
      visited[node] = true;

      for (FlowEdge edge : graph.getAdjacent(node)) {
        int next = edge.other(node);
        if (!visited[next] && edge.residualCapacityTo(next) > 0) {
          edgeTo[next] = edge;
          queue.addLast(next);
        }
      }
    }
    return false;
  }

  public boolean inSourceCut(int node) {
    return visited[node];
  }

  static class GraphMaxFlow {
    final int numVertices;
    int numEdges = 0;
    private ArrayDeque<FlowEdge>[] adjacent;

    @SuppressWarnings("unchecked")
    public GraphMaxFlow(int numVertices) {
      this.numVertices = numVertices;
      adjacent = (ArrayDeque<FlowEdge>[]) new ArrayDeque[numVertices];
      for (int node = 0; node < numVertices; node++) {
        adjacent[node] = new ArrayDeque<FlowEdge>();
      }
    }

    public void addEdge(FlowEdge edge) {
      adjacent[edge.from].add(edge);
      adjacent[edge.to].add(edge);
      numEdges++;
    }

    public void addEdge(int from, int to, int capacity) {
      addEdge(new FlowEdge(from, to, capacity));
    }

    public void addUndirectedEdge(int from, int to, int capacity) {
      addEdge(new FlowEdge(from, to, capacity));
      addEdge(new FlowEdge(to, from, capacity));
    }

    public ArrayDeque<FlowEdge> getAdjacent(int node) {
      return adjacent[node];
    }

    public String toString() {
      StringBuilder sb = new StringBuilder(numVertices + numEdges);
      sb.append("Nodes: " + numVertices + " Edges: " + numEdges + "\n");
      for (int node = 0; node < numVertices; node++) {
        sb.append(node + " -> ");
        int count = 0;
        for (FlowEdge edge : getAdjacent(node)) {
          if (count != 0) {
            sb.append(", ");
          }
          sb.append(edge.toString());
          count++;
        }
        sb.append('\n');
      }
      return sb.toString();
    }
  }

  static class FlowEdge {
    final int from, to, cap;
    int flow = 0;

    public FlowEdge(int from, int to, int cap) {
      this.from = from;
      this.to = to;
      this.cap = cap;
    }

    public int other(int node) {
      if (node == from) {
        return to;
      } else {
        return from;
      }
    }

    public int residualCapacityTo(int node) {
      if (node == to) {
        return cap - flow;
      } else {
        return flow;
      }
    }

    public void addResidualFlowTo(int node, int newFlow) {
      if (node == to) {
        flow += newFlow;
      } else {
        flow -= newFlow;
      }
    }

    public String toString() {
      return "(" + from + ", " + to + ", <" + flow + "/" + cap + ">)";
    }
  }

}
