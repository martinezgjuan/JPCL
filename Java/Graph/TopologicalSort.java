package Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeSet;

import Graph.GraphWeighted.Edge;
import Graph.GraphWeighted.GraphWE;

/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 */
public class TopologicalSort {
		
	/**
	 * Returns a valid topological sort of the Directed Acyclic Graph (DAG) given as input. There
	 * exists a topological sort iff the graph is directed and acyclic. The algorithm finds the 
	 * reverse DFS postorder of the graph, which is proven to be the topological order. This is
	 * an iterative version of Tarjan's algorithm
	 * @param graph Directed acyclic graph 
	 * @time O(|V| + |E|)
	 * @tested UVa_10305
	 */
	static ArrayDeque<Integer> topSortTarjan(Graph graph) {		
		boolean[] visited = new boolean[graph.getNumVertices()];
		int[] nodeTo = new int[graph.getNumVertices()];
		int[] count = new int[graph.getNumVertices()];

		ArrayDeque<Integer> res = new ArrayDeque<Integer>();

		for (int u = 0; u < graph.getNumVertices(); u++) {
			if(visited[u]) {
				continue;
			}
			nodeTo[u] = -1;

			ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
			stack.add(u);

			while (!stack.isEmpty()) {
				int node = stack.removeLast();
				visited[node] = true;
				boolean last = true;
				for (int next : graph.getAdjacent(node)) {				
					if (!visited[next]) {
						stack.addLast(next);
						nodeTo[next] = node;
						count[node]++; // Increase the count of open nodes for the node node
						last = false;
					}
				}
				if(last) { // Add in the stack if it is the last node in the dfs branch
					res.addFirst(node);
					
					int aux = node; // Close the node for the parent and close the parent recursively if it has no more children open					
					while(nodeTo[aux] != -1 && --count[nodeTo[aux]] == 0) { 
						aux = nodeTo[aux];
						res.addFirst(aux);						
					}
				}
			}
		}

		return res;
	}
	
	/**
	 * Kahn's algorithm to find a valid topological order of the Directed Acyclic
	 * Graph (DAG) given as input. There exists a topological sort iff the graph is 
	 * directed and acyclic.
	 * @param graph Directed acyclic graph
	 * @time O(|V| + |E|)
	 * @tested UVa_10305
	 */
	public static int[] topSortKahn(Graph graph) {
		ArrayDeque<Integer> S = new ArrayDeque<Integer>();
		int[] res = new int[graph.getNumVertices()];
		int[] countIn = new int[graph.getNumVertices()];
		
		for (int i = 0; i < graph.getNumVertices(); i++) {
			for (int node : graph.getAdjacent(i)) {
				countIn[node]++;
			}			
		}
		
		for (int i = 0; i < graph.getNumVertices(); i++) {
			if(countIn[i] == 0)
				S.addLast(i);
		}
		
		int index = 0;
		while (!S.isEmpty()) {
			int node = S.removeLast();
			res[index++] = node;
			
			for (int next : graph.getAdjacent(node)) {
				if(--countIn[next] == 0) {
					S.addLast(next);
				}
			}			
		}
		
		return res;
	}
	
	/**
	 * Kahn's algorithm to find a valid topological order of the Directed Acyclic
	 * Graph (DAG) given as input. There exists a topological sort iff the graph is 
	 * directed and acyclic.
	 * @param graph Directed acyclic graph
	 * @time O(|V| + |E|)
	 * @tested UVa_10305
	 */
	public static int[] topSortKahn(GraphWE graph) {
		ArrayDeque<Integer> S = new ArrayDeque<Integer>();
		int[] res = new int[graph.getNumVertices()];
		int[] countIn = new int[graph.getNumVertices()];
		
		for (int i = 0; i < graph.getNumVertices(); i++) {
			for (Edge e : graph.adj(i)) {
				countIn[e.to]++;
			}			
		}
		
		for (int i = 0; i < graph.getNumVertices(); i++) {
			if(countIn[i] == 0)
				S.addLast(i);
		}
		
		int index = 0;
		while (!S.isEmpty()) {
			int node = S.removeLast();
			res[index++] = node;
			
			for (Edge next : graph.adj(node)) {
				if(--countIn[next.to] == 0) {
					S.addLast(next.to);
				}
			}			
		}
		
		return res;
	}
	
	/**
	 * Kahn's algorithm to find the lowest lexicographic topological order of the Directed Acyclic
	 * Graph (DAG) given as input. There exists a topological sort iff the graph is directed and 
	 * acyclic.
	 * @param graph Directed acyclic graph
	 * @time O(|V| log |V| + |E|)
	 * @tested SPOJ_PFDEP UVa_10305
	 */
	static int[] lexTopSortKahn(Graph graph) {
		PriorityQueue<Integer> PQ = new PriorityQueue<Integer>();
		int[] res = new int[graph.getNumVertices()];
		int[] countIn = new int[graph.getNumVertices()];
		
		for (int i = 0; i < graph.getNumVertices(); i++) {
			for (int node : graph.getAdjacent(i)) {
				countIn[node]++;
			}			
		}
		
		for (int i = 0; i < graph.getNumVertices(); i++) {
			if(countIn[i] == 0)
				PQ.add(i);
		}
		
		int index = 0;
		while (!PQ.isEmpty()) {
			int node = PQ.remove();
			res[index++] = node;
			
			for (int next : graph.getAdjacent(node)) {
				if(--countIn[next] == 0) {
					PQ.add(next);
				}
			}			
		}
		
		return res;
	}
	
	/**
	 * Prints all the topological orders of the Directed Acyclic Graph (DAG) given as input
	 * in lexicographical order.
	 * @param graph Directed acyclic graph
	 * @time O( |possible orders| * (|V| log |V| + |E|) ) which could be up to O(|V|!) when there are no edges.
	 * @tested UVa_124
	 */
	static void printLexTopSorts(Graph graph) {
		int[] countIn = new int[graph.getNumVertices()];

		for (int i = 0; i < graph.getNumVertices(); i++) {
			for (int node : graph.getAdjacent(i)) {
				countIn[node]++;
			}
		}
		
		TreeSet<Integer> sortedInts = new TreeSet<Integer>();
		
		for (int i = 0; i < graph.getNumVertices(); i++) {
			if(countIn[i] == 0) {
				sortedInts.add(i);
			}
		}
		
		printTopSorts(graph, sortedInts, new ArrayList<Integer>(), countIn);
	}

	static void printTopSorts(Graph graph, TreeSet<Integer> sortedInts, ArrayList<Integer> res, int[] countIn) {
		if(res.size() == graph.getNumVertices()) {
			// Print elements in res
			return;
		}

		for (int node : sortedInts) {
			TreeSet<Integer> sortedInts2 = new TreeSet<Integer>(sortedInts);
			sortedInts2.remove(node);
			ArrayList<Integer> list = new ArrayList<Integer>(res);
			list.add(node);
			int[] countIn2 = countIn.clone();
			for (int next : graph.getAdjacent(node)) {
				if(--countIn2[next] == 0) {
					sortedInts2.add(next);
				}
			}	

			printTopSorts(graph, sortedInts2, list, countIn2);	
		}
	}
	
	public static void main(String[] args) {
		Graph g = new Graph(7);
		
		g.addDirectedEdge(0, 5);
		g.addDirectedEdge(0, 1);
		g.addDirectedEdge(3, 5);
		g.addDirectedEdge(5, 4);
		g.addDirectedEdge(6, 0);
		g.addDirectedEdge(1, 4);
		g.addDirectedEdge(0, 2);
		g.addDirectedEdge(3, 6);
		g.addDirectedEdge(3, 4);
		g.addDirectedEdge(6, 4);
		g.addDirectedEdge(3, 2);
		
		System.out.println(topSortTarjan(g));
	}

}
