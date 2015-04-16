package Graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayDeque;

import Graph.GraphUnweighted.Graph;

/**
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 */
public class Misc {

	/**********************************************/
	// Used by many algorithms
	static boolean[] visited;
	static int[] nodeTo;
	
	// Used by Connected Components and isBipartite
	static int compCount;
	static int[] belongsToCC;
	
	// Used by isBipartite
	static boolean[] evenColor;	
	/**********************************************/
	
	/**
	 * Determines the number of connected components in the graph and the marks the nodes with their corresponding component
	 * @param g Implicit graph
	 * @param finishCC if <code>true</code>, completes the computation of connected components even when it was determined that the graph was not bipartite 
	 * @return <code>true</code> if the input graph is bipartite
	 * @time O(|E| + |V|)
	 * @tested Live_5796
	 */
	static void connectedComp(Graph g) {
		compCount = 0;
		belongsToCC = new int[g.getNumVertices()];
		visited = new boolean[g.getNumVertices()];
		nodeTo = new int[g.getNumVertices()];	

		for (int u = 0; u < g.getNumVertices(); u++) {
			if(visited[u])
				continue;
			belongsToCC[u] = compCount;
			
			ArrayDeque<Integer> S = new ArrayDeque<Integer>();
			S.add(u);

			while (!S.isEmpty()) {
				int act = S.pop();
				visited[act] = true;
				belongsToCC[act] = compCount;
				for (int nei : g.getAdjacent(act)) {				
					if(!visited[nei]) {
						S.push(nei);
						nodeTo[nei] = act;
					}
				}
			}
			compCount++;
		}
	}
	
	/**
	 * Verifies if the graph is bipartite and checks connected components while doing so
	 * @param g Implicit graph
	 * @param finishCC if <code>true</code>, completes the computation of connected components even when it was determined that the graph was not bipartite 
	 * @return <code>true</code> if the input graph is bipartite
	 * @time O(|E| + |V|)
	 * @tested UVA_10004
	 */
	static boolean isBipartite(Graph g, boolean finishCC) {
		evenColor = new boolean[g.getNumVertices()];
		visited = new boolean[g.getNumVertices()];
		nodeTo = new int[g.getNumVertices()];
		compCount = 0;
		belongsToCC = new int[g.getNumVertices()];
		
		boolean isBip = true;
		
		for (int u = 0; u < g.getNumVertices(); u++) {
			
			if(visited[u])
				continue;
			
			belongsToCC[u] = compCount;
			
			ArrayDeque<Integer> S = new ArrayDeque<Integer>();
			S.add(u);
			
			while (!S.isEmpty()) {
				int act = S.pop();
				visited[act] = true;
				if(act == u) 
					evenColor[act] = false;
				else {
					evenColor[act] = !evenColor[nodeTo[act]]; // Opposite color than the previous node
				}
				belongsToCC[act] = compCount;
				for (int nei : g.getAdjacent(act)) {				
					if(!visited[nei]) {
						S.push(nei);
						nodeTo[nei] = act;
					} else {
						if(evenColor[nei] == evenColor[act]) {
							isBip = false;	// The graph is not bipartite
							if(!finishCC)
								return false;
						}
					}
				}
			}
			compCount++;
		}
		
		return isBip;
	}
	
	/**
	 * Verifies if the graph contains an Eulerian Path or an Eulerian Cycle. An Eulerian path 
	 * in an undirected graph is a path that uses each edge exactly once. An Eulerian cycle,
	 * or Eulerian tour in an undirected graph is a cycle that uses each edge exactly once.
	 * @param g Implicit graph
	 * @param guaranteedConnected if <code>true</code> the program does not check for connected components
	 * @return <code>0</code> if the graph does not contain Eulerian paths or cycles, <code>1</code> if the 
	 * graph contains an Eulerian path but not an Eulerian cycle, and <code>3</code> in the graph contains both
	 * an Eulerian path and an Eulerian cycle (the same).
	 * @time O(|V|) if connected and O(|E| + |V|) otherwise
	 */
	static int hasEulerianPathCycle(Graph g, boolean guaranteedConnected) {
		if (guaranteedConnected) {
			int odd = 0;
			
			for (int i = 0; i < g.getNumVertices(); i++) {
				if (g.getAdjacent(i).size() % 2 != 0)
					odd++;
			}
			
			if (odd == 0)
				return 2;
			else if (odd <= 2)
				return 1;
			else
				return 0;
		}
		
		connectedComp(g);
		
		int odd = 0;
		HashSet<Integer> compWithEdges = new HashSet<Integer>();
		
		for (int i = 0; i < g.getNumVertices(); i++) {
			if (g.getAdjacent(i).size() % 2 != 0)
				odd++;
			if (g.getAdjacent(i).size() > 0 )
				compWithEdges.add(belongsToCC[i]);
		}
		
		if (odd == 0 && compWithEdges.size() <= 1) // All the nodes with edges belong to the same component
			return 2;
		else if (odd <= 2 && compWithEdges.size() <= 1) // All the nodes with edges belong to the same component
			return 1;
		else
			return 0;
	}
	
	// Test with TC SRM Christmas Tree Decoration problem (and check MST with that problem too)
	static boolean hasCycle(Graph g, boolean directed) {		
		boolean[] visited = new boolean[g.getNumVertices()];
		int[] nodeTo = new int[g.getNumVertices()];
		
		for (int u = 0; u < g.getNumVertices(); u++) {
			if(visited[u])
				continue;
			
			nodeTo[u] = -1;
			
			ArrayDeque<Integer> S = new ArrayDeque<Integer>();
			S.add(u);

			while (!S.isEmpty()) {
				int act = S.pop();
				visited[act] = true;
				for (int nei : g.getAdjacent(act)) {
					if (nodeTo[act] == nei && !directed)
						continue;
					if (!visited[nei]) {
						S.push(nei);
						nodeTo[nei] = act;
					} else {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	/**
	 * Test with CF Goodbye 2014 B
	 * @time O(|V|^3)
	 */
	static void WarshallTransClosure(int n, boolean[][] edge, int[][] nodeTo) {		
		for (int i = 0; i < n; i++)
			Arrays.fill(nodeTo[i], i);
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					if (edge[i][k] && edge[k][j]) {
						edge[i][j] = true;
						nodeTo[i][j] = nodeTo[k][j];
					}
	}
	
	static ArrayDeque<Integer> pathWTC(int from, int to, boolean[][] edge, int[][] nodeTo) {
		if(!edge[from][to])
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
	
	/**
	 * Minimax (or Maximin). Find the path that connecting i and j with the
	 * whose maximum cost of any edge is minimal (Faster with MST).
	 * 
	 * @time O(|V|^3)
	 */
	static void minimax(int n, int[][] dist) {
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					dist[i][j] = Math.min(dist[i][j], Math.max(dist[i][k], dist[k][j]));
					//dist[i][j] = Math.max(dist[i][j], Math.min(dist[i][k], dist[k][j]));
	}
	
	public static void main(String[] args) {
		Graph g = new Graph(8);
				
		g.addUndirectedEdge(0,1);
		g.addUndirectedEdge(0,2);
		g.addUndirectedEdge(0,5);
		g.addUndirectedEdge(0,6);
		g.addUndirectedEdge(1,2);
		g.addUndirectedEdge(2,3);
		g.addUndirectedEdge(2,4);
		g.addUndirectedEdge(3,4);
		g.addUndirectedEdge(4,5);
		g.addUndirectedEdge(4,6);
		g.addUndirectedEdge(7,7);
		System.out.println(hasEulerianPathCycle(g, true));		
	}
}
