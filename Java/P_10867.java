import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int num = scan.nextInt();
		ArrayList<Integer> arr = new ArrayList<>();
		
		for (int i = 0; i < num; i++) {
			int input = scan.nextInt();
			arr.add(input);
		}
		
		HashSet<Integer> hs = new HashSet<>(arr);
		Integer[] sorted = new Integer[hs.size()];
		hs.toArray(sorted);
		Arrays.sort(sorted);
		
		for (int i : sorted) {
			System.out.print(i + " ");
		}
	}
}

