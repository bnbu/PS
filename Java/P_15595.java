import java.util.*;
import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		HashMap<String, Integer> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			String[] s = br.readLine().split(" ");
			if (s[1].equals("megalusion"))
				continue;
			
			if (map.containsKey(s[1]) && map.get(s[1]) >= 0)
				continue;
			
			if (s[2].equals("4") && !map.containsKey(s[1]))
				map.put(s[1], 0);
			else if (s[2].equals("4") && map.containsKey(s[1])) 
				map.put(s[1], map.get(s[1]) * -1);
			else {
				if (map.containsKey(s[1]))
					map.put(s[1], map.get(s[1]) - 1);
				else
					map.put(s[1], -1);
			}
		}
		int correct = 0, out = 0;
		ArrayList<Integer> values = new ArrayList<>(map.values());
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) >= 0) {
				correct++;
				out += values.get(i);
			}
			else continue;
		}
		double result = (double)correct / (correct + out);
		if (correct == 0)
			System.out.println(0);
		else 
			System.out.println(result * 100);
	}
}
// 2020-09-14 해결
// 리스트랑 맵을 같이 쓰자니, 리스트의 탐색에서 시간이 오지게 걸림
// map 임의 참조 : O(logN) 라서 맵의 탐색은 그렇게 느리진 않음
// 따라서 맵 하나로만 해야했음
// 따라서 틀리면, 값은 1씩 감소하되, 정답이 되는 순간 -1을 곱함 따라서 정답자들의 틀린값만 얻으려면 양수 (0포함)만 얻음 됨.
// 참고로 탐색속도는 트리맵 < 해쉬맵이다.
