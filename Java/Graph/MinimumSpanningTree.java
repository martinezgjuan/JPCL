package Graph;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Graph.GraphWeighted.Edge;
import Other.UnionFindCode.UnionFind;

/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 */
public class MinimumSpanningTree {

  static ArrayList<Edge> mst;
  static double mstWeight;

  /**
   * @param pQueue
   *          Priority Queue containing the edges of the weighted Graph
   * @time O(|E| log |E|)
   * @tested UVA_908, UVA_10048, UVA_11631, UVA_11747
   */
  static void mstKruskal(PriorityQueue<Edge> pQueue, int numVertices) {
    mst = new ArrayList<Edge>(numVertices - 1);
    UnionFind unionFind = new UnionFind(numVertices);
    mstWeight = 0;

    while (!pQueue.isEmpty() && mst.size() < numVertices - 1) {
      Edge edge = pQueue.poll();
      if (!unionFind.connected(edge.from, edge.to)) {
        unionFind.union(edge.from, edge.to);
        mst.add(edge);
        mstWeight += edge.weight;
      }
    }
  }

}
