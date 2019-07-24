package haha;

import java.util.Scanner;

public class Main {
	/*
	 * 深度优先搜索遍历的应用
	 */
	 //定义全局共享变量
	static int sum = 0;//存放和
	static int pos = -1;//指针
	static int n = 0;//初始化n为0
	static int count = 0;//得到解的次数
	static int[] reslut;//试探数组

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		reslut = new int[n];
		DFS(1);

	}

	private static void DFS(int x) {
		if (sum == n) {// 得到一组解
			count++;
			System.out.print(n + "=");
			for (int i = 0; i < pos; i++) {// 输出前pos-1个元素
				System.out.print(reslut[i] + "+");
			}
			if (count % 4 == 0 || reslut[pos] == n) {// 此时换行，注意换行时不再打印分号。
			                                        //而且最后一位即 n = n时也不需要打印费分号。
				System.out.println(reslut[pos]);
			}else {
				System.out.println(reslut[pos]);//单独处理第pos个元素
			}
			return;// 返回上一层
		}

		if (sum > n) {// 此时是超解的
			return;// 啥都不干，直接退回上一层
		}

		for (int i = x; i < n + 1; i++) {//把i的初值赋为x，目的就是保持序列自增，防止出现（x+1）+x情况
			reslut[++pos] = i;// 要先自增再赋值，也会减少中间变量，节省空间
			sum += i;
			DFS(i);// 进入递归,其中一个就是检查作用
			pos--;// 回到初始位置
			sum -= i;// 清空该位
			
		}
	}
}
