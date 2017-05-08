package com.amgji.graphbase;

import java.io.File;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SymbolGraph;

public class MySymbolGraph {

	private ST<String, Integer> st; // 节点字符串映射到索引
	private String[] keys; // 索引——》字符串
	private MyEdgeWeightedDigraph graph; // the underlying graph

	public MySymbolGraph(File filename, String delimiter) {
        st = new ST<String, Integer>();

        In in = new In(filename);
        //将数据读入符号表
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(delimiter);
            for (int i = 0; i < a.length-1; i++) {
                if (!st.contains(a[i]))
                    st.put(a[i], st.size());
            }
        }
        StdOut.println("Done reading " + filename);

        // 将数据写入反向索引表
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;
        }

        // 将数据读入图的邻接表
        graph = new MyEdgeWeightedDigraph(st.size());
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            int v = st.get(a[0]);
            int w = st.get(a[1]);
            int weight=Integer.parseInt(a[2]);    
            MyDirectedEdge e = new MyDirectedEdge(v, w, weight);
			graph.addEdge(e);
        }
    }
	//是否包含某节点
	public boolean contains(String s) {
		return st.contains(s);
	}
	//通过符号名获取索引值
	public int indexOf(String s) {
		return st.get(s);
	}
	//通过索引值获取符号名
	public String nameOf(int v) {
		validateVertex(v);
		return keys[v];
	}
	//返回无向图
	public MyEdgeWeightedDigraph graph() {
		return graph;
	}

	private void validateVertex(int v) {
		int V = graph.V();
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(st.size() + " " + graph.E() + MyEdgeWeightedDigraph.NEWLINE);
		for (int v = 0; v < st.size(); v++) {
			s.append(keys[v] + ": ");
			for (MyDirectedEdge e : graph.adj(v)) {
				s.append(keys[e.from()] + " --"+e.weight()+"-- "+keys[e.to()]+"  ");
			}
			s.append(MyEdgeWeightedDigraph.NEWLINE);
		}
		return s.toString();
	}
	
	public static void main(String[] args) {
		File file = new File("src/resource/SymbolData.txt");
		MySymbolGraph sg = new MySymbolGraph(file, " ");
		System.out.println(sg);
		
		
	}

}
