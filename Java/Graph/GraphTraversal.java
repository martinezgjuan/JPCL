package Graph;

import java.util.ArrayDeque;

import Graph.GraphUnweighted.Graph;

public class GraphTraversal {
	
	/***********************************/
	static Graph graph;

	// Used by many algorithms
	static boolean[] visited;
	static int[] nodeTo;

	// Optionally used by BFS
	static int[] distTo;
	/***********************************/
	
	/***********************************/
	/*** Grid Exploration              */
	static int[] deltaRow = { 1, 0, -1, 0 }; // S,E,N,W nextghbors
	static int[] deltaCol = { 0, 1, 0, -1 };
	
	//static int[] deltaRow = { 1, 1, 0, -1, -1, -1, 0, 1 }; // S,SE,E,NE,N,NW,W,SW nextghbors
	//static int[] deltaCol = { 0, 1, 1, 1, 0, -1, -1, -1 };
	
	static int maxRow;
	static int maxCol;

	public static boolean isValidCell(int row, int column) {
		return 0 <= row && row < maxRow && 0 <= column && column < maxCol;
	}
	/***********************************/
	
	
	// Call initRecDFS before calling this function
	/**
	 * @tested CodeChef CHEFPRES
	 */
	static void initRecDFS() {
		visited = new boolean[graph.getNumVertices()];
		nodeTo = new int[graph.getNumVertices()];
	}

	static void dfs(int node) {
		visited[node] = true;
		for (int next : graph.getAdjacent(node)) {
			if (!visited[next]) {
				nodeTo[next] = node;
				dfs(next);
			}
		}
	}

	/**
	 * @tested UVA_10048
	 */
	static void iterativeDfs(int source) {
		visited = new boolean[graph.getNumVertices()];
		nodeTo = new int[graph.getNumVertices()];
		ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
		stack.add(source);

		while (!stack.isEmpty()) {
			int node = stack.pop();
			if(visited[node])
				continue;
			visited[node] = true;
			for (int next : graph.getAdjacent(node)) {
				if (!visited[next]) {
					stack.push(next);
					nodeTo[next] = node;
				}
			}
		}
	}

	/**
	 * @tested UVA_429
	 */
	static void bfs(int source) {
		visited = new boolean[graph.getNumVertices()];
		nodeTo = new int[graph.getNumVertices()];
		distTo = new int[graph.getNumVertices()];
		ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
		queue.add(source);
		distTo[source] = 0;

		while (!queue.isEmpty()) {
			int node = queue.removeFirst();
			if(visited[node])
				continue;
			visited[node] = true;

			for (int next : graph.getAdjacent(node)) {
				if (!visited[next]) {
					distTo[next] = distTo[node] + 1;
					nodeTo[next] = node;
					queue.addLast(next);
				}
			}
		}
	}

	// Use after running DFS or BFS from node u
	static ArrayDeque<Integer> pathTo(int from, int to) {
		ArrayDeque<Integer> path = new ArrayDeque<Integer>();
		if (!visited[to])
			return null;
		path.addFirst(to);
		while (nodeTo[to] != from) {
			path.addFirst(nodeTo[to]);
			to = nodeTo[to];
		}
		path.addFirst(from);
		return path;
	}

}
