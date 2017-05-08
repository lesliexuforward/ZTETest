package com.amgji.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FullPermutation {

	public static List<int[]> result=new ArrayList<int[]>();
	static int[] array = { 2, 4, 7, 12, 13, 14 };

	public static void main(String[] args) {
		getAllOrder(0, array.length - 1);
		for(int[] a:result){
			System.out.println(Arrays.toString(a));
		}
	}
	
	public static List<int[]> getFullPermutation(){
		getAllOrder(0, array.length - 1);
		return result;
	}

	public static void getAllOrder(int begin, int end) {
		if (begin == end) {
			// 此处就是正常全排列
			// check如果不满足绿线条件，排除
			check();
		} else {
			for (int i = begin; i <= end; i++) {
				// 交换数据
				swap(begin, i);
				getAllOrder(begin + 1, end);
				// 恢复原数组内容
				swap(i, begin);
			}
		}
	}

	public static void swap(int from, int to) {
		// 这里应该加上各种防止无效交换的情况
		// 比如位置相同，或者2个位置的数据相同
		if (from == to) {
			return;
		}
		int tmp = array[from];
		array[from] = array[to];
		array[to] = tmp;
	}

	public static void check() {
		// 排列拿到了，可以进行你的判断了。
		if (connectgreenLine(array)) {
			//System.out.println(Arrays.toString(array));
			//HotpotQueue queue = new HotpotQueue(array[0],array[1],array[2],array[3],array[4],array[5]);
			int[] temp = Arrays.copyOf(array, 6);
			int[] newtemp=new int[]{0,temp[0],temp[1],temp[2],
					temp[3],temp[4],temp[5],17};
			
			result.add(newtemp);
		}
	}

	private static boolean connectgreenLine(int[] array2) {
		// TODO Auto-generated method stub
		// 判断绿线是否连接上
		int len = array2.length;
		int flag1 = 0, flag2 = 0;
		for (int i = 0; i < len; i++) {
			if (array[i] == 2) {
				// 判断2,3是否连接
				if (i == 0) {
					if (array[i + 1] == 4)
						flag1 = 1;
				} else if (i == len - 1) {
					if (array[i - 1] == 4)
						flag1 = 1;
				} else {
					if (array[i - 1] == 4 || array[i + 1] == 4) {
						flag1 = 1;
					}
				}
			}

			//
			// 判断13,14
			if (array[i] == 13) {

				if (i == 0) {
					if (array[i + 1] == 14)
						flag2 = 1;
				} else if (i == len - 1) {
					if (array[i - 1] == 14)
						flag2 = 1;
				} else {
					if (array[i - 1] == 14 || array[i + 1] == 14) {
						flag2 = 1;
					}
				}
			}

		}
		int sum = flag1 + flag2;
		if (sum == 2)
			return true;
		else
			return false;
	}

}
