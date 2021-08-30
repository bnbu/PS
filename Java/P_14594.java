import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt(), m = scan.nextInt();
		int[] room = new int[n + 1];
		int roomNum = 1;
		while (m-- > 0) {
			int x = scan.nextInt(), y = scan.nextInt();
			
			int temp = 0;
			boolean chk = false;
			for (int i = x; i <= y; i++) {
				if (room[i] == 0)
					room[i] = roomNum;
				else if (room[i] != 0) {
					temp = room[i];
					chk = true;
					break;
				}
			}
			
			if (chk) {
				roomNum--;
				for (int i = x; i <= y; i++) {
					room[i] = roomNum;
				}
				for (int i = 1; i <= n; i++) {
					if (room[i] == temp)
						room[i] = roomNum;
					else if (room[i] != temp)
						continue;
				}
			}
			
			roomNum++;
		}
		if (m == 0) {
			System.out.println(n);
		}
		else {
			HashMap<Integer, Integer> map = new HashMap<>();
			for (int i = 1; i <= n; i++) {
				if (room[i] != 0)
					map.put(room[i], 1);
				else if (room[i] == 0)
					if (map.containsKey(0))
						map.put(0, map.get(0) + 1);
					else
						map.put(0, 1);
			}
			
			if (map.containsKey(0))
				System.out.println(map.size() - 1 + map.get(0)); 
			else
				System.out.println(map.size());
		}		
	}
}
// 2020-09-18 02:17 해결
//ㅋㅋㅋㅋㅋㅋ 이게 왜됨ㅋㅋㅋㅋㅋ
