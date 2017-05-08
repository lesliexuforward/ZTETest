package com.amgji.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyGraph {
	public static final String CRCL = "\r\n";

	private final int V;// 顶点数目
	private int E;// 边的数目
	private List<Integer>[] adj;// 邻接表

	public MyGraph(int V) {
		this.V = V;
		this.E = 0;
		adj = new ArrayList[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new ArrayList<Integer>();
		}
	}

	public MyGraph(Scanner in) {
		this(in.nextInt());
		int E = in.nextInt();
		for (int i = 0; i < E; i++) { // 添加一条边
			int v = in.nextInt();
			int w = in.nextInt();
			addEdge(v, w);
		}
	}

	public void addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v);
		E++;
	}

	public int getV() {
		return V;
	}

	public int getE() {
		return E;
	}

	public Iterable<Integer> adj(int v) {
		return adj[v];
	}

	public static void main(String[] args) {

		StringBuilder sb = new StringBuilder();
		sb.append(18);
		sb.append(CRCL);
		sb.append(41);
		sb.append("0 1");

	}

}
