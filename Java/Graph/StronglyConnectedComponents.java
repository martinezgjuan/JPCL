package Graph;

import java.util.ArrayDeque;

import Graph.GraphUniqueEdge.GraphUE;
import Graph.GraphUnweighted.Graph;

/**
 * The resulting strongly connected components are a reverse topological sort of the DAG
 * 
 * @time O(|E| + |V|)
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 * @tested UVA_11838, UVA_247, UVA_11504, UVA_12442
 */
public class StronglyConnectedComponents {
	
	static Graph g;
	static boolean[] vis;	// Visited flag	
	static int[] low;		// Lowest step node that can be visited from i	
	static int step;		// Number of nodes already visited
	static ArrayDeque<Integer> Stack;
	static int[] id;		// Id of the SCC containing i. Can also be changed to a list of lists
	static int countSCC;	// Number of SCC's in the graph
	
	static void tarjanSCC() {
		vis = new boolean[g.getNumVertices()];		
		low = new int[g.getNumVertices()];
		step = 0;
		Stack = new ArrayDeque<Integer>();
		id = new int[g.getNumVertices()];
		countSCC = 0;		
		
		for (int i = 0; i < g.getNumVertices(); i++)
			if(!vis[i])
				DFStarjanSCC(i);				
	}
	
	static void DFStarjanSCC(int act) {
		vis[act] = true;
		low[act] = step++;
		int min = low[act];
		Stack.addLast(act);
		
		for (int nei : g.getAdjacent(act)) {
			if(!vis[nei])
				DFStarjanSCC(nei);
			min = Math.min(min,low[nei]);
		}
		
		if(min < low[act]) {
			low[act] = min;
			return;
		}
		
		int nodeSCC;
		
		do {	// As the node act is the root of its SCC subtree, we pop all the subtree until act
			nodeSCC = Stack.removeLast();
			id[nodeSCC] = countSCC;
			low[nodeSCC] = g.getNumVertices();
		} while (nodeSCC != act);
		
		countSCC++;		
	}
	

	/**
	 * Returns the Directed Acyclic Graph formed with the contracted Strongly Connected Components.
	 * The returned graph doesn't have self-loops but might have parallel edges.
	 */
	static Graph getSCCDAG() {
		Graph dag = new Graph(countSCC);
		for (int i = 0; i < g.getNumVertices(); i++) {
			for (int nei : g.getAdjacent(i)) {
				if(id[i] != id[nei]) {
					dag.addDirectedEdge(id[i], id[nei]);
				}
			}
		}		
		return dag;
	}
	
	/**
	 * Returns the Directed Acyclic Graph formed with the contracted Strongly Connected Components.
	 * The returned graph doesn't have parallel edges or self-loops.
	 */
	static GraphUE getSimplifiedSCCDAG() {
		GraphUE dag = new GraphUE(countSCC);
		for (int i = 0; i < g.getNumVertices(); i++) {
			for (int nei : g.getAdjacent(i)) {
				if(id[i] != id[nei]) {
					dag.addDEdge(id[i], id[nei]);
				}
			}
		}		
		return dag;
	}

}
