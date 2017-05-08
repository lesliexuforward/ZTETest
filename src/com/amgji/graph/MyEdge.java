package com.amgji.graph;

import edu.princeton.cs.algs4.StdOut;

public class MyEdge implements Comparable<MyEdge> {
	private final int v; // 顶点之一
	private final int w; // 另一个顶点
	private final int weight; // 边的权重

	/**
	 * 
	 * @param v 顶点之一
	 * @param w 另一个顶点
	 * @param weight 边的权重
	 */
	public MyEdge(int v, int w, int weight) {
		if (v < 0)
			throw new IllegalArgumentException("vertex index must be a nonnegative integer");
		if (w < 0)
			throw new IllegalArgumentException("vertex index must be a nonnegative integer");
		if (Double.isNaN(weight))
			throw new IllegalArgumentException("Weight is NaN");
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	/**
	 * 
	 * @return 返回边的权重
	 */
	public int weight() {
		return weight;
	}

	/**
	 * 
	 * @return 返回其中一个顶点
	 */
	public int either() {
		return v;
	}

	/**
	 * 
	 * @param vertex 当前顶点
	 * @return 返回当前边的另一个顶点
	 */
	public int other(int vertex) {
		if (vertex == v)
			return w;
		else if (vertex == w)
			return v;
		else
			throw new IllegalArgumentException("Illegal endpoint");
	}

	/**
	 * 比较两条边的权重大小
	 */
	@Override
	public int compareTo(MyEdge that) {
		return Integer.compare(this.weight, that.weight);
	}

	public String toString() {
		return String.format("%d-%d %d", v, w, weight);
	}

	
	public int getWeight() {
		return weight;
	}
	public int getV() {
		return v;
	}
	public int getW() {
		return w;
	}
	/**
	 *  单元测试
	 * @param args
	 */
	public static void main(String[] args) {
		MyEdge e = new MyEdge(12, 34, 8);
		StdOut.println(e);
	}
}
