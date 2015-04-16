package Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Graph.GraphUnweighted.Graph;
import Graph.GraphWeighted.Edge;

/**
 * Beware of single node components which are not stored as the algorithm stores
 * edges.
 * 
 * @time O(|V| + |E|). About 20% faster when only finding articulation points.
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 * @tested LiveArchive_5796, UVA_315, UVA_610, UVA_796, UVA_10765
 */
public class BiconnectedComponents {

  static Graph graph;
  static int[] low;
  static int[] depth;
  static boolean[] isArticulation;
  static int step;

  static boolean findBridgesAndBCs;
  static List<Edge> bridges;
  static List<List<Edge>> bicComps;
  static ArrayDeque<Edge> edgeStack;

  static boolean isBridge(int u, int v) {
    return (low[u] > depth[v] || low[v] > depth[u]);
  }

  /**
   * @param needBridgesAndBCs
   */
  static void biconnectedComp(boolean needBridgesAndBCs) {
    low = new int[graph.getNumVertices()];
    depth = new int[graph.getNumVertices()];
    isArticulation = new boolean[graph.getNumVertices()];
    findBridgesAndBCs = needBridgesAndBCs;
    bridges = new ArrayList<Edge>();
    bicComps = new ArrayList<List<Edge>>();
    edgeStack = new ArrayDeque<Edge>();
    Arrays.fill(depth, -1);
    step = 0;

    for (int i = 0; i < graph.getNumVertices(); i++)
      if (depth[i] == -1)
        dfsBicComp(i, i);
  }

  static void dfsBicComp(int parent, int node) {
    int children = 0;
    depth[node] = step++;
    low[node] = depth[node];
    for (int next : graph.getAdjacent(node)) {
      if (next == parent || depth[next] >= depth[node]) {
        continue;
      }
      if (findBridgesAndBCs) {
        edgeStack.addLast(new Edge(node, next));
      }
      if (depth[next] == -1) {
        children++;
        dfsBicComp(node, next);
        low[node] = Math.min(low[node], low[next]);
        if (low[next] >= depth[node]) {
          if (findBridgesAndBCs) {
            newBicComp(Math.min(node, next), Math.max(node, next));
          }
          if (node != parent) {
            isArticulation[node] = true;
          }
        }
        if (findBridgesAndBCs && low[next] == depth[next]) { // node-next is a
                                                             // bridge
          bridges.add(new Edge(node, next));
        }

      } else {
        low[node] = Math.min(low[node], depth[next]);
      }

    }

    if (node == parent && children > 1)
      isArticulation[node] = true;
  }

  static void newBicComp(int u, int v) {
    int ind = bicComps.size();
    bicComps.add(new ArrayList<Edge>());
    boolean stop = false;

    while (!stop) {
      stop = (edgeStack.getLast().from == u && edgeStack.getLast().to == v)
              || (edgeStack.getLast().from == v && edgeStack.getLast().to == u);
      bicComps.get(ind).add(edgeStack.removeLast());
    }
  }

  public static void main(String[] args) {
    graph = new Graph(6);
    graph.addUndirectedEdge(0, 1);
    graph.addUndirectedEdge(1, 2);
    graph.addUndirectedEdge(1, 3);
    graph.addUndirectedEdge(1, 4);
    graph.addUndirectedEdge(1, 5);
    graph.addUndirectedEdge(4, 5);

    /*
     * g = new Graph(9); g.addUEdge(0, 1); g.addUEdge(1, 2); g.addUEdge(3, 4);
     * g.addUEdge(4, 5); g.addUEdge(5, 6); g.addUEdge(6, 7); g.addUEdge(2, 8);
     * g.addUEdge(8, 1);
     */

    biconnectedComp(true);
    System.out.println(bicComps);
    for (List<Edge> comp : bicComps) {
      for (Edge e : comp)
        System.out.println(isBridge(e.from, e.to));
    }

    int a = 2;
    a++;
  }
}
