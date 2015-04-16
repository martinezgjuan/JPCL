package Graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;

import Graph.GraphUnweighted.Graph;

/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 */
public class MiscGraph {

  /**********************************************/
  // Used by many algorithms
  static boolean[] visited;
  static int[] nodeTo;

  // Used by Connected Components and isBipartite
  static int compCount;
  static int[] belongsToCC;

  /**********************************************/

  /**
   * Determines the number of connected components in the graph and the marks the nodes with their
   * corresponding component
   * 
   * @param graph
   *          Implicit graph
   * @param finishCC
   *          If <code>true</code>, completes the computation of connected components even when it
   *          was determined that the graph was not bipartite
   * @return <code>true</code> if the input graph is bipartite
   * @time O(|E| + |V|)
   * @tested Live_5796
   */
  static void connectedComp(Graph graph) {
    compCount = 0;
    belongsToCC = new int[graph.getNumVertices()];
    visited = new boolean[graph.getNumVertices()];
    nodeTo = new int[graph.getNumVertices()];

    for (int initialNode = 0; initialNode < graph.getNumVertices(); initialNode++) {
      if (visited[initialNode]) {
        continue;
      }
      belongsToCC[initialNode] = compCount;
      ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
      stack.add(initialNode);

      while (!stack.isEmpty()) {
        int node = stack.pop();
        visited[node] = true;
        belongsToCC[node] = compCount;
        for (int next : graph.getAdjacent(node)) {
          if (!visited[next]) {
            stack.push(next);
            nodeTo[next] = node;
          }
        }
      }
      compCount++;
    }
  }

  /**
   * Verifies if the graph is bipartite and checks connected components while doing so
   * 
   * @param graph
   *          Implicit graph
   * @param finishCC
   *          If <code>true</code>, completes the computation of connected components even when it
   *          was determined that the graph was not bipartite
   * @return <code>true</code> if the input graph is bipartite
   * @time O(|E| + |V|)
   * @tested UVA_10004
   */
  static boolean isBipartite(Graph graph, boolean finishCC) {
    boolean[] evenColor = new boolean[graph.getNumVertices()];
    visited = new boolean[graph.getNumVertices()];
    nodeTo = new int[graph.getNumVertices()];
    compCount = 0;
    belongsToCC = new int[graph.getNumVertices()];
    boolean isBipartite = true;

    for (int initialNode = 0; initialNode < graph.getNumVertices(); initialNode++) {
      if (visited[initialNode]) {
        continue;
      }
      belongsToCC[initialNode] = compCount;
      ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
      stack.add(initialNode);

      while (!stack.isEmpty()) {
        int curNode = stack.pop();
        visited[curNode] = true;
        if (curNode == initialNode) {
          evenColor[curNode] = false;
        } else {
          evenColor[curNode] = !evenColor[nodeTo[curNode]]; // Opposite color than the previous node
        }
        belongsToCC[curNode] = compCount;
        for (int next : graph.getAdjacent(curNode)) {
          if (!visited[next]) {
            stack.push(next);
            nodeTo[next] = curNode;
          } else {
            if (evenColor[next] == evenColor[curNode]) {
              isBipartite = false; // The graph is not bipartite
              if (!finishCC)
                return false;
            }
          }
        }
      }
      compCount++;
    }

    return isBipartite;
  }

  /**
   * Verifies if the graph contains an Eulerian Path or an Eulerian Cycle. An Eulerian path in an
   * undirected graph is a path that uses each edge exactly once. An Eulerian cycle, or Eulerian
   * tour in an undirected graph is a cycle that uses each edge exactly once.
   * 
   * @param graph
   *          Implicit graph
   * @param guaranteedConnected
   *          If <code>true</code> the program does not check for connected components
   * @return <code>0</code> if the graph does not contain Eulerian paths or cycles, <code>1</code>
   *         If the graph contains an Eulerian path but not an Eulerian cycle, and <code>3</code> in
   *         the graph contains both an Eulerian path and an Eulerian cycle (the same).
   * @time O(|V|) if connected and O(|E| + |V|) otherwise
   */
  static int hasEulerianPathCycle(Graph graph, boolean guaranteedConnected) {
    if (guaranteedConnected) {
      int odd = 0;

      for (int node = 0; node < graph.getNumVertices(); node++) {
        if (graph.getAdjacent(node).size() % 2 != 0) {
          odd++;
        }
      }

      if (odd == 0) {
        return 2;
      } else if (odd <= 2) {
        return 1;
      } else {
        return 0;
      }
    }

    connectedComp(graph);
    int odd = 0;
    HashSet<Integer> compWithEdges = new HashSet<Integer>();

    for (int node = 0; node < graph.getNumVertices(); node++) {
      if (graph.getAdjacent(node).size() % 2 != 0) {
        odd++;
      }
      if (graph.getAdjacent(node).size() > 0) {
        compWithEdges.add(belongsToCC[node]);
      }
    }

    if (odd == 0 && compWithEdges.size() <= 1) {
      // All the nodes with edges belong to the same component
      return 2;
    } else if (odd <= 2 && compWithEdges.size() <= 1) {
      // All the nodes with edges belong to the same component
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * Whether there is a cycle in the given graph
   */
  static boolean hasCycle(Graph graph, boolean directed) {
    boolean[] visited = new boolean[graph.getNumVertices()];
    int[] nodeTo = new int[graph.getNumVertices()];

    for (int initialNode = 0; initialNode < graph.getNumVertices(); initialNode++) {
      if (visited[initialNode]) {
        continue;
      }
      nodeTo[initialNode] = -1;

      ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
      stack.add(initialNode);

      while (!stack.isEmpty()) {
        int node = stack.pop();
        visited[node] = true;
        for (int next : graph.getAdjacent(node)) {
          if (nodeTo[node] == next && !directed) {
            continue;
          }
          if (!visited[next]) {
            stack.push(next);
            nodeTo[next] = node;
          } else {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * @time O(|V|^3)
   */
  static void WarshallTransClosure(int numVertices, boolean[][] edge, int[][] nodeTo) {
    for (int i = 0; i < numVertices; i++) {
      Arrays.fill(nodeTo[i], i);
    }
    for (int k = 0; k < numVertices; k++) {
      for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
          if (edge[i][k] && edge[k][j]) {
            edge[i][j] = true;
            nodeTo[i][j] = nodeTo[k][j];
          }
        }
      }
    }
  }

  /**
   * Returns a path after running the Warshal Transitive Closure algorithm
   */
  static ArrayDeque<Integer> pathWTC(int from, int to, boolean[][] edge, int[][] nodeTo) {
    if (!edge[from][to]) {
      return null;
    }

    ArrayDeque<Integer> res = new ArrayDeque<Integer>();
    int act = to;

    while (act != from) {
      res.addFirst(act);
      act = nodeTo[from][act];
    }
    res.addFirst(act);
    return res;
  }

  /**
   * Minimax (or Maximin). Find the path that connecting i and j with the whose maximum cost of any
   * edge is minimal (Faster with MST).
   * 
   * @time O(|V|^3)
   */
  static void minimax(int n, int[][] dist) {
    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          dist[i][j] = Math.min(dist[i][j], Math.max(dist[i][k], dist[k][j]));
          // dist[i][j] = Math.max(dist[i][j], Math.min(dist[i][k], dist[k][j]));
        }
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(8);

    g.addUndirectedEdge(0, 1);
    g.addUndirectedEdge(0, 2);
    g.addUndirectedEdge(0, 5);
    g.addUndirectedEdge(0, 6);
    g.addUndirectedEdge(1, 2);
    g.addUndirectedEdge(2, 3);
    g.addUndirectedEdge(2, 4);
    g.addUndirectedEdge(3, 4);
    g.addUndirectedEdge(4, 5);
    g.addUndirectedEdge(4, 6);
    g.addUndirectedEdge(7, 7);
    System.out.println(hasEulerianPathCycle(g, true));
  }
}
