package com.amgji.dijkstrasp;

import java.io.File;
import java.util.List;

import com.amgji.graphbase.HotpotQueue;
import com.amgji.graphbase.MyDirectedEdge;
import com.amgji.graphbase.MyEdgeWeightedDigraph;
import com.amgji.utils.FullPermutation;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class MyDijkstraSP {
	private double[] distTo; // 从起点到达各顶点的最短距离
	public MyDirectedEdge[] edgeTo; // edgeTo[v] = last edge on shortest s->v

	private IndexMinPQ<Double> pq; // 索引优先队列
	private static MyDijkstraSP sp2;
	private static MyDijkstraSP sp4;
	private static MyDijkstraSP sp7;
	private static MyDijkstraSP sp12;
	private static MyDijkstraSP sp13;
	private static MyDijkstraSP sp14;

	/**
	 * 生成一个由起点s出发到有向图中其他节点的最短距离树
	 */
	public MyDijkstraSP(MyEdgeWeightedDigraph G, int s) {
		for (MyDirectedEdge e : G.edges()) {
			if (e.weight() < 0)
				throw new IllegalArgumentException("edge " + e + " has negative weight");
		}

		distTo = new double[G.V()];
		edgeTo = new MyDirectedEdge[G.V()];

		validateVertex(s);

		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[s] = 0.0;

		// relax vertices in order of distance from s
		pq = new IndexMinPQ<Double>(G.V());
		pq.insert(s, distTo[s]);
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			for (MyDirectedEdge e : G.adj(v))
				relax(e);
		}

		// check optimality conditions
		assert check(G, s);
	}

	// 放松一条边 ，如果需要则更新索引优先队列
	private void relax(MyDirectedEdge e) {
		int v = e.from(), w = e.to();
		if (distTo[w] > distTo[v] + e.weight()) {
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
			if (pq.contains(w))
				pq.decreaseKey(w, distTo[w]);
			else
				pq.insert(w, distTo[w]);
		}
	}

	/**
	 * 
	 * @param v
	 * @return 返回从顶点s到v的最短距离
	 */
	public double distTo(int v) {
		validateVertex(v);
		return distTo[v];
	}

	/**
	 * 判断是否从s能到达v
	 * 
	 * @param v
	 * @return
	 */
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	/**
	 * Returns a shortest path from the source vertex {@code s} to vertex
	 * {@code v}.
	 *
	 * @param v
	 *            the destination vertex
	 * @return a shortest path from the source vertex {@code s} to vertex
	 *         {@code v} as an iterable of edges, and {@code null} if no such
	 *         path
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public Iterable<MyDirectedEdge> pathTo(int v) {
		validateVertex(v);
		if (!hasPathTo(v))
			return null;
		Stack<MyDirectedEdge> path = new Stack<MyDirectedEdge>();
		for (MyDirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
	}

	// check optimality conditions:
	// (i) for all edges e: distTo[e.to()] <= distTo[e.from()] + e.weight()
	// (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] +
	// e.weight()
	private boolean check(MyEdgeWeightedDigraph G, int s) {

		// check that edge weights are nonnegative
		for (MyDirectedEdge e : G.edges()) {
			if (e.weight() < 0) {
				System.err.println("negative edge weight detected");
				return false;
			}
		}

		// check that distTo[v] and edgeTo[v] are consistent
		if (distTo[s] != 0.0 || edgeTo[s] != null) {
			System.err.println("distTo[s] and edgeTo[s] inconsistent");
			return false;
		}
		for (int v = 0; v < G.V(); v++) {
			if (v == s)
				continue;
			if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
				System.err.println("distTo[] and edgeTo[] inconsistent");
				return false;
			}
		}

		// check that all edges e = v->w satisfy distTo[w] <= distTo[v] +
		// e.weight()
		for (int v = 0; v < G.V(); v++) {
			for (MyDirectedEdge e : G.adj(v)) {
				int w = e.to();
				if (distTo[v] + e.weight() < distTo[w]) {
					System.err.println("edge " + e + " not relaxed");
					return false;
				}
			}
		}

		// check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] +
		// e.weight()
		for (int w = 0; w < G.V(); w++) {
			if (edgeTo[w] == null)
				continue;
			MyDirectedEdge e = edgeTo[w];
			int v = e.from();
			if (w != e.to())
				return false;
			if (distTo[v] + e.weight() != distTo[w]) {
				System.err.println("edge " + e + " on shortest path not tight");
				return false;
			}
		}
		return true;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = distTo.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	public static void findPath(MyDijkstraSP sp, int[] a, int i) {
		StdOut.printf("%d to %d (%.2f)  ", a[i], a[i + 1], sp2.distTo(a[i + 1]));
		for (MyDirectedEdge e : sp.pathTo(a[i + 1])) {
			StdOut.print(e + "   ");
		}
		StdOut.println();
	}

	/**
	 * Unit tests the {@code DijkstraSP} data type.
	 *
	 * @param args
	 *            the command-line arguments
	 */
	/*
	public static void main(String[] args) {
		List<int[]> hotpotList = FullPermutation.getFullPermutation();

		File file = new File("src/resource/data.txt");
		In in = new In(file);
		MyEdgeWeightedDigraph G = new MyEdgeWeightedDigraph(in);

		// compute shortest paths
		MyDijkstraSP sp = new MyDijkstraSP(G, 0);

		// print shortest path
		// for (int t = 0; t < G.V(); t++) {
		// if (sp.hasPathTo(t)) {
		// StdOut.printf("%d to %d (%.2f) ", 0, t, sp.distTo(t));
		// for (MyDirectedEdge e : sp.pathTo(t)) {
		// StdOut.print(e + " ");
		// }
		// StdOut.println();
		// } else {
		// StdOut.printf("%d to %d no path\n", 0, t);
		// }
		// }
		// 起点为绿点的图 2, 4, 7, 12, 13, 14
		sp2 = new MyDijkstraSP(G, 2);
		sp4 = new MyDijkstraSP(G, 4);
		sp7 = new MyDijkstraSP(G, 7);
		sp12 = new MyDijkstraSP(G, 12);
		sp13 = new MyDijkstraSP(G, 13);
		sp14 = new MyDijkstraSP(G, 14);

		for (int[] a : hotpotList) {
			for (int i = 0; i < a.length - 1; i++) {
				switch (a[i]) {
				case 2:
					//findPath(sp2);
					break;
				case 4:

					break;
				case 7:

					break;
				case 12:

					break;
				case 13:

					break;
				case 14:

					break;
				}
			}
		}

		if (sp.hasPathTo(17)) {
			StdOut.printf("%d to %d (%.2f)  ", 0, 17, sp.distTo(17));
			for (MyDirectedEdge e : sp.pathTo(17)) {
				StdOut.print(e + "   ");
			}
			StdOut.println();
		} else {
			StdOut.printf("%d to %d         no path\n", 0, 17);
		}
	}
	*/
}
