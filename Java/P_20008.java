import java.util.*;
import java.io.*;
public class Main {
	public static Skill[] skillSet;
	public static int n;
	public static int hp;
	public static int t = Integer.MAX_VALUE;
	public static Stack<Integer> stack;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		hp = Integer.parseInt(st.nextToken());
		skillSet = new Skill[n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int cd = Integer.parseInt(st.nextToken()),
				dmg = Integer.parseInt(st.nextToken());
			skillSet[i] =  new Skill(i, cd, dmg);
		}
		
		stack = new Stack<>();
		backtrack(hp);
		
		System.out.println(t);
	}
	public static void backtrack(int hp) {
		if (hp <= 0) {
			Queue<Integer> used = new LinkedList<>();
			used.addAll(stack);
			int time = 0;
			for (int i = 0; i < n; i++)
				skillSet[i].current = 0;
			while (!used.isEmpty()) {
				int curr = used.peek();
				
				if (skillSet[curr].current == 0) {
					skillSet[curr].current = skillSet[curr].coolDown;
					used.poll();
				}
				for (int i = 0; i < n; i++) {
					if (skillSet[i].current > 0)
						skillSet[i].current--;
				}
				time++;
				if (time >= t)
					return;
			}
			t = time;
			return;
		}
		
		for (int i = 0; i < n; i++) {
			stack.push(i);
			backtrack(hp - skillSet[i].dmg);
			stack.pop();
		}
	}
}
class Skill {
	int num;
	int coolDown;
	int dmg;
	int current = 0;
	public Skill(int n, int c, int d) {
		num = n;
		coolDown = c;
		dmg = d;
	}
}
// 2020-12-23 16:39 해결
// 그냥 완전탐색 돌림.
// 백트래킹으로 살짝 바꿨더니 시간 복잡도에 개선은 있긴 했음.
