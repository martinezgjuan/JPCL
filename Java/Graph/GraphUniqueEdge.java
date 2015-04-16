package Graph;

import java.util.HashSet;

public class GraphUniqueEdge {

	static class GraphUE {
		final int V;
		int E = 0;
		private HashSet<Integer>[] adj;

		@SuppressWarnings("unchecked")
		public GraphUE(int V) {
			this.V = V;
			adj = (HashSet<Integer>[]) new HashSet[V];
			for (int v = 0; v < V; v++) {
				adj[v] = new HashSet<Integer>();
			}
		}

		public void addDEdge(int v, int w) {
			adj[v].add(w);
			E++;
		}

		public void addUEdge(int v, int w) {
			adj[v].add(w);
			if (v != w)
				adj[w].add(v);
			E++;
		}

		public HashSet<Integer> adj(int node) {
			return adj[node];
		}

		public String toString() {
			StringBuilder sb = new StringBuilder(V + E);
			sb.append("Nodes: " + V + " Edges: " + E + "\n");
			for (int i = 0; i < V; i++) {
				sb.append(i + " -> " + adj(i).toString() + "\n");
			}
			return sb.toString();
		}
	}
}