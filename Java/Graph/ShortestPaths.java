package Graph;

import static Graph.TopologicalSort.topSortKahn;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.PriorityQueue;

import Graph.GraphWeighted.Edge;
import Graph.GraphWeighted.GraphWE;
import Juan.PairCode.Pair;

public class ShortestPaths {

	static int[] distanceTo;
	static Edge[] edgeTo;
	static final int INF = Integer.MAX_VALUE / 2;

	/**
	 * Non-negative cycles! Performance guaranteed only with non-negative edges.
	 * 
	 * @param graph
	 * @param source
	 * @param dest
	 * @time O(|E| log |V|)
	 * @tested UVA_10986, UVA_11367
	 */
	static void dijkstra(GraphWE graph, int source /* , int dest */) {
		edgeTo = new Edge[graph.getNumVertices()];
		distanceTo = new int[graph.getNumVertices()];
		Arrays.fill(distanceTo, INF);
		PriorityQueue<Pair<Integer, Integer>> queue = new PriorityQueue<Pair<Integer, Integer>>();
		distanceTo[source] = 0;

		queue.add(new Pair<Integer, Integer>(0, source));

		while (!queue.isEmpty()) {
			Pair<Integer, Integer> elem = queue.poll();
			int act = elem.second;
			// if(act == dest)
			// break;
			if (elem.first > distanceTo[act])
				continue;

			for (Edge edge : graph.adj(act)) {
				if (distanceTo[edge.to] > distanceTo[edge.from] + edge.weight) {
					distanceTo[edge.to] = distanceTo[edge.from] + edge.weight;
					edgeTo[edge.to] = edge;
					queue.add(new Pair<Integer, Integer>(distanceTo[edge.to], edge.to));
				}
			}
		}
	}

	/**
	 * Can have negative edges. Can find longest path by negating weights. Can
	 * be used to schedule tasks by obtaining the longest path.
	 * 
	 * @time O(|V| + |E|)
	 */
	static void AcyclicSP(GraphWE graph, int source) {
		edgeTo = new Edge[graph.getNumVertices()];
		distanceTo = new int[graph.getNumVertices()];

		int[] topSort = topSortKahn(graph);

		Arrays.fill(distanceTo, INF);
		distanceTo[source] = 0;

		for (int i = 0; i < graph.getNumVertices(); i++)
			for (Edge e : graph.adj(topSort[i]))
				if (distanceTo[e.to] > distanceTo[e.from] + e.weight) {
					distanceTo[e.to] = distanceTo[e.from] + e.weight;
					edgeTo[e.to] = e;
				}
	}

	/**
	 * Variation of Bellman's algorithm known as Shortest Path Faster Algorithm.
	 * Can have negative edges. Can find longest path by negating weights.
	 * Doesn't work with negative cycles but can be used to detect them by
	 * running allowing |V| + 1 iterations and checking if it reaches it. Can be
	 * used to schedule tasks by obtaining the longest path. Beware of overflows
	 * with the initial maximum value!
	 * 
	 * @time O(|V| * |E|) but typical case is O(|V| + |E|)
	 * @tested UVA_558, UVA_10986
	 */
	static void BellmanFord(GraphWE graph, int source) {
		edgeTo = new Edge[graph.getNumVertices()];
		distanceTo = new int[graph.getNumVertices()];
		Arrays.fill(distanceTo, INF);
		distanceTo[source] = 0;

		boolean[] inQueue = new boolean[graph.getNumVertices()];
		ArrayDeque<Integer> nodes = new ArrayDeque<Integer>();
		ArrayDeque<Integer> nodes2 = new ArrayDeque<Integer>();
		nodes.add(source);

		int times = 0;
		while (!nodes.isEmpty() && times++ < graph.getNumVertices() - 1) {

			while (!nodes.isEmpty()) {
				int act = nodes.removeFirst();
				inQueue[act] = false;

				for (Edge edge : graph.adj(act)) {
					if (distanceTo[edge.to] > distanceTo[edge.from] + edge.weight) {
						distanceTo[edge.to] = distanceTo[edge.from] + edge.weight;
						edgeTo[edge.to] = edge;

						if (!inQueue[edge.to]) {
							nodes2.add(edge.to);
							inQueue[edge.to] = true;
						}
					}
				}
			}
			nodes = nodes2;
			nodes2 = new ArrayDeque<Integer>();
		}
	}

	/**
	 * Returns the path from the source to dest or null if it doesn't exist
	 */
	static ArrayDeque<Edge> pathTo(int dest) {
		ArrayDeque<Edge> path = new ArrayDeque<Edge>();
		if (distanceTo[dest] == INF)
			return null;

		for (Edge edge = edgeTo[dest]; edge != null; edge = edgeTo[edge.from])
			path.addFirst(edge);
		return path;
	}
	

	/**
	 * 
	 * @param n
	 * @param dist
	 * @param nodeTo
	 * @time O(|V|^3)
	 * @tested UVA_11463
	 */
	static void FloydWarshall(int n, int[][] dist, int[][] nodeTo) {
		for (int i = 0; i < n; i++)
			Arrays.fill(nodeTo[i], i);
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					if (dist[i][k] + dist[k][j] < dist[i][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						nodeTo[i][j] = nodeTo[k][j];
					}
	}

	static ArrayDeque<Integer> pathFW(int from, int to, int[][] dist, int[][] nodeTo) {
		if(dist[from][to] == INF)
			return null;

		ArrayDeque<Integer> res = new ArrayDeque<Integer>();
		int act = to;

		while (act != from) {
			res.addFirst(act);
			act = nodeTo[from][act];
		}
		res.addFirst(act);

		return res;
	}

}
