package com.amgji.graphbase;

public class MyDirectedEdge {
	private final int v; // 边的一个顶点
	private final int w; // 边的另一个顶点
	private final int weight; // 权重

	public MyDirectedEdge(int v, int w, int weight) {
		if (v < 0)
			throw new IllegalArgumentException("Vertex names must be nonnegative integers");
		if (w < 0)
			throw new IllegalArgumentException("Vertex names must be nonnegative integers");
		if (Double.isNaN(weight))
			throw new IllegalArgumentException("Weight is NaN");
		this.v = v;
		this.w = w;
		this.weight = weight;
	}

	public int from() {
		return v;
	}

	public int to() {
		return w;
	}

	public int weight() {
		return weight;
	}
	@Override
	public String toString() {
		return v + "--"+weight+"-->" + w + " " ;
	}

//	public static void main(String[] args) {
//		DirectedEdge e = new DirectedEdge(12, 34, 5.67);
//		StdOut.println(e);
//	}
}
