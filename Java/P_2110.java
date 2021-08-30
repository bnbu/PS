import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] number = new int[n];
		for (int i = 0; i < n; i++)
			number[i] = scan.nextInt();
		
		double avg;
		int mid, mode, range;
		
		Arrays.sort(number);
		avg = getAvg(number);
		mid = getMid(number);
		mode = getMode(number);
		range = getRange(number);
		System.out.printf("%.0f\n", avg);
		System.out.println(mid);
		System.out.println(mode);
		System.out.println(range);
	}
	public static double getAvg(int[] arr) {
		double sum = 0; 
		for (int i = 0; i < arr.length; i++)
			sum += arr[i];
		
		return sum / arr.length;
 	}
	
	public static int getMid(int[] arr) {
		if (arr.length % 2 == 1) 
			return arr[arr.length / 2];
		else {
			return (arr[arr.length / 2] - arr[arr.length / 2 - 1]) / 2;
		}
	}
	public static int getMode(int[] arr) {
		Map<Integer, Integer> map = new TreeMap<>();
		for (int i : arr) {
			if (map.containsKey(i))
				map.put(i, map.get(i) + 1);
			else 
				map.put(i, 1);
		}
		ArrayList<Integer> keys = new ArrayList<>(map.keySet());
		ArrayList<Integer> values = new ArrayList<>(map.values());
		ArrayList<Integer> index = new ArrayList<>();
		ArrayList<Integer> filteredArr = new ArrayList<>();
		int max = 0;
		for (int i : values)
			max = max < i ? i : max;
		for (int i = 0; i < values.size(); i++)
			if (max == values.get(i))
				index.add(i);
		for (int i : index) 
			filteredArr.add(keys.get(i));

		if (filteredArr.size() == 1)
			return filteredArr.get(0);
		else
			return filteredArr.get(1);
		 
	}
	public static int getRange(int[] arr) {
		return Math.abs(arr[arr.length - 1] - arr[0]);
	}
}
// 2020-08-31 해결
// mode 구할때 좀 덕지덕지 해서 안될거 같았는데 이게 되네 ㅋㅋ
