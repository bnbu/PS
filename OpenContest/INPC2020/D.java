import java.io.*;
public class D {
	public static int[] memoization;
	public static int h;
	public static int y;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		h = Integer.parseInt(input[0]);
		y = Integer.parseInt(input[1]);
		memoization = new int[y + 1];
		System.out.println(get(y));
	}
	public static int get(int y) {
		if (y == 0) {
			return h;
		}
		else if (y == 1) {
			return (int) (h * 1.05);
		}
		else {
			if (memoization[y] != 0)
				return memoization[y];
			
			if (y >= 5) 
				memoization[y] = (int) Math.max(memoization[y], get(y - 5) * 1.35);
			if (y >= 3) 
				memoization[y] = (int) Math.max(memoization[y], get(y - 3) * 1.20);
			if (y > 1)
				memoization[y] = (int) Math.max(memoization[y], get(y - 1) * 1.05);
			
			return memoization[y];
		}
	}
}