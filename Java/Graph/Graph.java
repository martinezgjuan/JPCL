package Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class GraphUnweighted {

	static class Graph {
		private final int numVertices;
		private int numEdges = 0;
		private ArrayList<ArrayDeque<Integer>> adjacent;

		public Graph(int numVertices) {
			this.numVertices = numVertices;
			adjacent = new ArrayList<ArrayDeque<Integer>>();
			for (int v = 0; v < numVertices; v++)
				adjacent.add(new ArrayDeque<Integer>());
		}

		public int getNumVertices() {
			return numVertices;
		}
		
		public int getNumEdges() {
			return numEdges;
		}
		
		public void addDirectedEdge(int from, int to) {
			adjacent.get(from).add(to);
			numEdges++;
		}

		public void addUndirectedEdge(int from, int to) {
			adjacent.get(from).add(to);
			if (from != to)
				adjacent.get(to).add(from);
			numEdges++;
		}

		public ArrayDeque<Integer> getAdjacent(int node) {
			return adjacent.get(node);
		}

		public String toString() {
			StringBuilder sb = new StringBuilder(numVertices + numEdges);
			sb.append("Nodes: " + numVertices + " Edges: " + numEdges + "\n");
			for (int i = 0; i < numVertices; i++) {
				sb.append(i + " -> " + getAdjacent(i).toString() + "\n");
			}
			return sb.toString();
		}
		
	}

}
