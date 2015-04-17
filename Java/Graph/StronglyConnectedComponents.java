package Graph;

import java.util.ArrayDeque;

/**
 * The resulting strongly connected components are a reverse topological sort of the DAG
 * 
 * @time O(|E| + |V|)
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 * @tested UVA_11838, UVA_247, UVA_11504, UVA_12442
 */
public class StronglyConnectedComponents {
	
	static Graph graph;
	static boolean[] visited;	// Visited flag	
	static int[] low;		// Lowest step node that can be visited from i	
	static int step;		// Number of nodes already visited
	static ArrayDeque<Integer> Stack;
	static int[] id;		// Id of the SCC containing i. Can also be changed to a list of lists
	static int countSCC;	// Number of SCC's in the graph
	
	static void tarjanSCC() {
		visited = new boolean[graph.getNumVertices()];		
		low = new int[graph.getNumVertices()];
		step = 0;
		Stack = new ArrayDeque<Integer>();
		id = new int[graph.getNumVertices()];
		countSCC = 0;		
		
		for (int i = 0; i < graph.getNumVertices(); i++) {
			if(!visited[i]) {
				DFStarjanSCC(i);
			}
		}
	}
	
	static void DFStarjanSCC(int cur) {
		visited[cur] = true;
		low[cur] = step++;
		int min = low[cur];
		Stack.addLast(cur);
		
		for (int next : graph.getAdjacent(cur)) {
			if(!visited[next]) {
				DFStarjanSCC(next);
			}
			min = Math.min(min,low[next]);
		}
		
		if(min < low[cur]) {
			low[cur] = min;
			return;
		}
		
		int nodeSCC;
		
		do {	// As the node act is the root of its SCC subtree, we pop all the subtree until act
			nodeSCC = Stack.removeLast();
			id[nodeSCC] = countSCC;
			low[nodeSCC] = graph.getNumVertices();
		} while (nodeSCC != cur);
		
		countSCC++;		
	}
	
	/**
	 * Returns the Directed Acyclic Graph formed with the contracted Strongly Connected Components.
	 * The returned graph doesn't have self-loops but might have parallel edges.
	 */
	static Graph getSccDag() {
		Graph dag = new Graph(countSCC);
		for (int cur = 0; cur < graph.getNumVertices(); cur++) {
			for (int next : graph.getAdjacent(cur)) {
				if(id[cur] != id[next]) {
					dag.addDirectedEdge(id[cur], id[next]);
				}
			}
		}		
		return dag;
	}
	
	/**
	 * Returns the Directed Acyclic Graph formed with the contracted Strongly Connected Components.
	 * The returned graph doesn't have parallel edges or self-loops.
	 */
	static GraphUniqueEdge getSimplifiedSccDag() {
	  GraphUniqueEdge dag = new GraphUniqueEdge(countSCC);
		for (int cur = 0; cur < graph.getNumVertices(); cur++) {
			for (int next : graph.getAdjacent(cur)) {
				if(id[cur] != id[next]) {
					dag.addDEdge(id[cur], id[next]);
				}
			}
		}		
		return dag;
	}

}
