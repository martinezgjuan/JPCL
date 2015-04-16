package Graph;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Graph.GraphWeighted.Edge;
import Juan.UnionFindCode.UnionFind;

public class MinimumSpanningTree {

	static ArrayList<Edge> MST;
	static double MSTweight;

	// Test with TC SRM Christmas Tree Decoration problem (and check MST with
	// that problem too)
	/**
	 * @param PQ
	 *            Priority Queue containing the edges of the weighted Graph
	 * @time O(|E| log |E|)
	 * @tested UVA_908, UVA_10048, UVA_11631, UVA_11747
	 */
	static void MSTKruskal(PriorityQueue<Edge> PQ, int n) {
		MST = new ArrayList<Edge>(n - 1);
		UnionFind UF = new UnionFind(n);
		MSTweight = 0;

		while (!PQ.isEmpty() && MST.size() < n - 1) {
			Edge e = PQ.poll();
			if (!UF.connected(e.from, e.to)) {
				UF.union(e.from, e.to);
				MST.add(e);
				MSTweight += e.weight;
			}
		}
	}

}
