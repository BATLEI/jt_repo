package haha;

public class TestFind {
	public static void main(String[] args) {
		find(5);
	}
	static String s = new String();
	static int[] a = new int[2];
	public static void find(int n) {
		for (int i = 1; i <= n/2; i++) {
			
			System.out.println(i+"+"+(n-i));
			a[0] = i;
			a[1] = n-i;
			if (a[1]>=2*a[0]) {
				s += (String.valueOf(a[0])+"+");
				System.out.print(s);
				find(a[1]);
			}else {
				s = "";
			}
			
		}
	}
	
	public static void print() {
		
	}
}
