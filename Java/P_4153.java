import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			int[] n = new int[3];
			for (int i = 0; i < n.length; i++) {
				n[i] = scan.nextInt();
				n[i] *= n[i];
			}
			
			if (n[0] == 0 && n[1]== 0 && n[2] == 0)
				break;
			
			Arrays.sort(n);
			if (n[0] + n[1] == n[2])
				System.out.println("right");
			else 
				System.out.println("wrong");
		}
	}
}
// 2020-08-30 해결
// 삼각형의 변의 순서의 보장은 없었음을 생각해야함
