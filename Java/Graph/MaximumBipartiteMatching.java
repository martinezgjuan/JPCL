package Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @time O(|E| * sqrt |V|)
 * @author Juan Martínez (https://www.linkedin.com/in/martinezgjuan)
 * @tested Spotify Cat vs. Dog, UVA_10080
 */
public class MaximumBipartiteMatching {

	// n: number of nodes on left side, nodes are numbered 1 to n
	// m: number of nodes on right side, nodes are numbered n+1 to n+m
	// G = NIL[0] U G1[G[1---n]] U G2[G[n+1---n+m]]

	static int NIL = 0;
	static int MAX;
	static int INF = Integer.MAX_VALUE;
	static ArrayList<Integer>[] G;
	static int n;
	static int m;
	static int[] match;
	static int[] distance;

	static boolean bfs() {
		int i, u;
		ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
		for (i = 1; i <= n; i++) {
			if (match[i] == NIL) {
				distance[i] = 0;
				queue.addLast(i);
			} else
				distance[i] = INF;
		}
		distance[NIL] = INF;
		while (!queue.isEmpty()) {
			u = queue.removeLast();
			if (distance[u] < distance[NIL]) {
				for (int v : G[u]) {
					if (distance[match[v]] == INF) {
						distance[match[v]] = distance[u] + 1;
						queue.addLast(match[v]);
					}
				}
			}
		}
		return (distance[NIL] != INF);
	}

	static boolean dfs(int u) {
		if (u != NIL) {
			for (int v : G[u]) {
				if (distance[match[v]] == distance[u] + 1) {
					if (dfs(match[v])) {
						match[v] = u;
						match[u] = v;
						return true;
					}
				}
			}
			distance[u] = INF;
			return false;
		}
		return true;
	}

	static int hopcroftKarp() {
		int matching = 0, i;
		// match[] is assumed NIL for all vertex in G
		while (bfs())
			for (i = 1; i <= n; i++)
				if (match[i] == NIL && dfs(i))
					matching++;
		return matching;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int u, v;

		n = sc.nextInt();
		m = sc.nextInt();
		int p = sc.nextInt();
		MAX = m + n + 1;
		match = new int[MAX];
		distance = new int[MAX];
		G = (ArrayList<Integer>[]) new ArrayList[n + 1];

		for (int i = 0; i < G.length; i++) {
			G[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < p; i++) {
			// u in G1, v in G2

			u = sc.nextInt();
			v = sc.nextInt();

			v += n;
			G[u].add(v);
		}

		System.out.printf("%d\n", hopcroftKarp());
		sc.close();
	}

}
