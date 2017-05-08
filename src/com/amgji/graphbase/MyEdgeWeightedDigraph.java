package com.amgji.graphbase;

import java.io.File;

import com.amgji.graph.MyEdgeWeightedGraph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class MyEdgeWeightedDigraph {
	public static final String NEWLINE = System.getProperty("line.separator");

	private final int V; // number of vertices in this digraph
	private int E; // number of edges in this digraph
	private Bag<MyDirectedEdge>[] adj; // adj[v] = adjacency list for vertex v
	private int[] indegree; // indegree[v] = indegree of vertex v

	/**
	 * 初始化一个顶点数为V边数为0的加权有向图
	 */
	public MyEdgeWeightedDigraph(int V) {
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
		this.V = V;
		this.E = 0;
		this.indegree = new int[V];
		adj = (Bag<MyDirectedEdge>[]) new Bag[V];
		for (int v = 0; v < V; v++)
			adj[v] = new Bag<MyDirectedEdge>();
	}

	/**
	 * 从流中读取一个加权有向图 如：src/resource/data.txt
	 */
	public MyEdgeWeightedDigraph(In in) {
		this(in.readInt());
		int E = in.readInt();
		if (E < 0)
			throw new IllegalArgumentException("Number of edges must be nonnegative");
		for (int i = 0; i < E; i++) {
			int v = in.readInt();
			int w = in.readInt();
			validateVertex(v);
			validateVertex(w);
			int weight = in.readInt();
			addEdge(new MyDirectedEdge(v, w, weight));
			addEdge(new MyDirectedEdge(w, v, weight));
		}
	}

	/**
	 * 
	 * @return 返回这个无向图顶点的总数
	 */
	public int V() {
		return V;
	}

	/**
	 * 
	 * @return 返回这个无向图边的总数
	 */
	public int E() {
		return E;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	/**
	 * 为这个无向图添加一条边
	 */
	public void addEdge(MyDirectedEdge e) {
		int v = e.from();
		int w = e.to();
		validateVertex(v);
		validateVertex(w);
		adj[v].add(e);
		indegree[w]++;
		E++;
	}

	/**
	 * 
	 * @param v
	 *            顶点
	 * @return 和v相关联的所有边
	 */
	public Iterable<MyDirectedEdge> adj(int v) {
		validateVertex(v);
		return adj[v];
	}

	/**
	 * 
	 * @param v
	 * @return 返回从这个顶点出发的边的个数
	 */
	public int outdegree(int v) {
		validateVertex(v);
		return adj[v].size();
	}

	/**
	 * 
	 * @param v
	 * @return 返回已v为终点的边的个数
	 */
	public int indegree(int v) {
		validateVertex(v);
		return indegree[v];
	}

	/**
	 * @return all edges in this edge-weighted digraph, as an iterable
	 */
	public Iterable<MyDirectedEdge> edges() {
		Bag<MyDirectedEdge> list = new Bag<MyDirectedEdge>();
		for (int v = 0; v < V; v++) {
			for (MyDirectedEdge e : adj(v)) {
				list.add(e);
			}
		}
		return list;
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " " + E + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (MyDirectedEdge e : adj[v]) {
				s.append(e + "  ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}

	/**
	 * 单元测试
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("src/resource/data.txt");
		In in = new In(file);
		MyEdgeWeightedDigraph G = new MyEdgeWeightedDigraph(in);
		StdOut.println(G);
	}
}
