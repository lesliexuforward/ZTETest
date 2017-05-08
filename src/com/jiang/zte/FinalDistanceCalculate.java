package com.jiang.zte;

import java.io.File;
import java.util.List;

import com.amgji.dijkstrasp.MyDijkstraSP;
import com.amgji.graphbase.MyEdgeWeightedDigraph;
import com.amgji.utils.FullPermutation;

import edu.princeton.cs.algs4.In;

//在此类中利用之前创建的各种类进行最终的计算
public class FinalDistanceCalculate {
	//得到中间节点的多种排列
	public static List<int[]> hotpotList;
	public static MyEdgeWeightedDigraph G;
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		
		hotpotList = FullPermutation.getFullPermutation();
		File file = new File("src/resource/data.txt");
		In in = new In(file);
		 G = new MyEdgeWeightedDigraph(in);
		
		 /*
		//先测试第一种方式hotpotList[0]
		int[] first = hotpotList.get(0);
		//最终的距离
		int endDistance=0;
		endDistance=findDistance(first);
		System.out.println("长度是："+endDistance);
		*/
		 //需要对链表的数组都计算，得到路径最短的一条线路
		 MinArray myMinArray=new MinArray();
		 for(int i=0;i<hotpotList.size();i++){
			 int[] tempArray=hotpotList.get(i);
			 int temp=findDistance(tempArray);
			 if(temp<myMinArray.distance)
			 {
				 myMinArray.distance=temp;
				 myMinArray.index=i;
			 }			 
			 
		 }
		 
		 System.out.println("最短路径的长度是："
				 +myMinArray.distance+"，在list的第 "+myMinArray.index+"个数组中");
		
	}
	
	//对一个排列找出它消耗的距离
	private static int findDistance(int[] first) {
		// TODO 自动生成的方法存根
		int redPointIndex=findIndex(first);
		//当值为true时说明有错误的路径，false时说明路径正确
		boolean isHaveWrongWay=wrongWay(redPointIndex,first);
		if(isHaveWrongWay){
			//进入说明有错误的路线
			return Integer.MAX_VALUE;
		}
		//开始执行，没有错误路线
		int sum=0;
		int arrayLength=first.length;
		for(int i=0;i<arrayLength-1;i++){
			MyDijkstraSP sp=new MyDijkstraSP(G, first[i]);
			//将double强制转换int
			int temp=(int)sp.distTo(first[i+1]);
			sum+=temp;
		}
		
		//此条线中间消耗的距离
		return sum;
	}
	//判断此排列中是否含有红线
	private static boolean wrongWay(int redPointIndex, int[] first) {
		// TODO 自动生成的方法存根
		
		//当首元素时
		if(redPointIndex==0){
			MyDijkstraSP sp=new MyDijkstraSP(G, first[1]);
			if(sp.edgeTo[12].from()==11) return true;
		}
		//尾元素时
		else if(redPointIndex==first.length-1){
			MyDijkstraSP sp=new MyDijkstraSP(G, first[first.length-2]);
			if(sp.edgeTo[12].from()==11) return true;
		}
		//中间时
		else{
			MyDijkstraSP sp1=new MyDijkstraSP(G, first[redPointIndex-1]);
			MyDijkstraSP sp2=new MyDijkstraSP(G, first[redPointIndex+1]);
			if(sp1.edgeTo[12].from()==11||
					sp2.edgeTo[12].from()==11) return true;
		}
		//默认正确路线
		return false;
		
	}

	//找排列中值为12的索引
	private static int findIndex(int[] first) {
		// TODO 自动生成的方法存根
		for(int i=0;i<first.length;i++){
			if(first[i]==12)
				return i;
		}
		return 0;
	}

}
