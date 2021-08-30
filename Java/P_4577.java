import java.util.*;
public class Main {
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {-1, 1, 0, 0}; // U D L R;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int r = scan.nextInt(), c = scan.nextInt();
		scan.nextLine();
		int gameCount = 1;
		
		while (r != 0 && c != 0) {
			boolean clear = true;
			int x = 0, y = 0;
			char[][] map = new char[r][c];
			for (int i = 0; i < r; i++) {
				String input = scan.nextLine();
				for (int j = 0; j < c; j++) {
					map[i][j] = input.charAt(j);
					
					if (map[i][j] == 'w' || map[i][j] == 'W') {
						x = j;
						y = i;
					}
				}
			}
			
			String control = scan.nextLine();
			for (int i = 0; i < control.length(); i++) {
				int idx = 0;
				switch (control.charAt(i)) {
				case 'U':
					idx = 0;
					break;
				case 'D':
					idx = 1;
					break;
				case 'L':
					idx = 2;
					break;
				case 'R':
					idx = 3;
					break;
				}
				
				if (map[y + dy[idx]][x + dx[idx]] == '#') continue;
				else if (map[y + dy[idx]][x + dx[idx]] == '.') {
					if (map[y][x] == 'W') {
						map[y][x] = '+';
						map[y + dy[idx]][x + dx[idx]] = 'w';
					}
					else {
						map[y][x] = '.';
						map[y + dy[idx]][x + dx[idx]] = 'w';
					}
				}
				else if (map[y + dy[idx]][x + dx[idx]] == '+') {
					if (map[y][x] == 'W') {
						map[y][x] = '+';
						map[y + dy[idx]][x + dx[idx]] = 'W';					
					}
					else {
						map[y][x] = '.';
						map[y + dy[idx]][x + dx[idx]] = 'w';
					}
				}
				else if (map[y + dy[idx]][x + dx[idx]] == 'b' || map[y + dy[idx]][x + dx[idx]] == 'B') {
					if (map[y + 2 * dy[idx]][x + 2 * dx[idx]] == '#' || map[y + 2 * dy[idx]][x + 2 * dx[idx]] == 'b'|| map[y + 2 * dy[idx]][x + 2 * dx[idx]] == 'B') continue;
					else if (map[y + 2 * dy[idx]][x + 2 * dx[idx]] == '.') {
						if (map[y][x] == 'W') {
							if (map[y + dy[idx]][x + dx[idx]] == 'b') {
								map[y + 2 * dy[idx]][x + 2 * dx[idx]] = 'b';
								map[y + dy[idx]][x + dx[idx]] = 'w';
								map[y][x] = '+';
							}
							else {
								map[y + 2 * dy[idx]][x + 2 * dx[idx]] = 'b';
								map[y + dy[idx]][x + dx[idx]] = 'W';
								map[y][x] = '+';
							}
						}
						else {
							if (map[y + dy[idx]][x + dx[idx]] == 'b') {
								map[y + 2 * dy[idx]][x + 2 * dx[idx]] = 'b';
								map[y + dy[idx]][x + dx[idx]] = 'w';
								map[y][x] = '.';
							}
							else {
								map[y + 2 * dy[idx]][x + 2 * dx[idx]] = 'b';
								map[y + dy[idx]][x + dx[idx]] = 'W';
								map[y][x] = '.';
							}
						}
					}
					else if (map[y + 2 * dy[idx]][x + 2 * dx[idx]] == '+') {
						if (map[y][x] == 'W') {
							if (map[y + dy[idx]][x + dx[idx]] == 'b') {
								map[y + 2 * dy[idx]][x + 2 * dx[idx]] = 'B';
								map[y + dy[idx]][x + dx[idx]] = 'w';
								map[y][x] = '+';
							}
							else {
								map[y + 2 * dy[idx]][x + 2 * dx[idx]] = 'B';
								map[y + dy[idx]][x + dx[idx]] = 'W';
								map[y][x] = '+';
							}
						}
						else {
							if (map[y + dy[idx]][x + dx[idx]] == 'b')  {
								map[y + 2 * dy[idx]][x + 2 * dx[idx]] = 'B';
								map[y + dy[idx]][x + dx[idx]] = 'w';
								map[y][x] = '.';
							}
							else {
								map[y + 2 * dy[idx]][x + 2 * dx[idx]] = 'B';
								map[y + dy[idx]][x + dx[idx]] = 'W';
								map[y][x] = '.';
							}
						}
					}
				}
				x += dx[idx];
				y += dy[idx];
				
				clear = true;
				for (char[] arr : map) {
					for (char ch : arr)
						if (ch == 'b') {
							clear = false;
							break;
						}
				}
				
				if (clear) break;
			}
			
			if (clear) sb.append("Game " + gameCount + ": complete\n" );
			else sb.append("Game " + gameCount + ": incomplete\n");
			
			for (char[] arr : map) {
				for (char ch : arr)
					sb.append(ch);
				sb.append("\n");
			}
			
			gameCount++;
			r = scan.nextInt();
			c = scan.nextInt();
			scan.nextLine();
		}
		System.out.println(sb.toString());
 	}
}
// 2020-09-08 12:27 해결
