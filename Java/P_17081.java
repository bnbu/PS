package D0103;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Tile {
	int type;
	/*
	'#' (-1)
	'.' (0) : 빈칸
	'B' (1) : 아이템 상자
	'^' (2) : 가시함정
	'&' (3) : 몬스터
	'M' (4) : 보스몬스터
	*/
	boolean isHero;
	Monster monster;
	Item item;
	
	public Tile(int type, boolean isHero) {
		this.type = type;
		this.isHero = isHero;
	}

	public String toString() {
		String ret = null;
		switch (this.type) {
			case -1:
				ret = "#";
				break;
			case 0:
				ret = ".";
				break;
			case 1:
				ret = "B";
				break;
			case 2:
				ret = "^";
				break;
			case 3:
				ret = "&";
				break;
			case 4:
				ret = "M";
				break;
		}
		return isHero ? "@" : ret;
	}
}
class Item {
	int type;
	/*
	 0 : 무기
	 1 : 방어구
	 2 : 장신구
	 */
	public Item(int type) {
		this.type = type;
	}	
}
class Weapon extends Item {
	int atk;
	public Weapon(int type, int atk) {
		super(type);
		this.atk = atk;
	}
}
class Armor extends Item {
	int def;
	public Armor(int type, int def) {
		super(type);
		this.def = def;
	}
}
class Accessory extends Item {
	String name;
	public Accessory(int type, String name) {
		super(type);
		this.name = name;
	}
}
class Monster {
	String name;
	int att, def, hp, exp;
	public Monster(String name, int att, int def, int hp, int exp) {
		this.name = name;
		this.att = att;
		this.def = def;
		this.hp = hp;
		this.exp = exp;
	}
	@Override
	public String toString() {
		return "Monster [name=" + name + ", att=" + att + ", def=" + def + ", hp=" + hp + ", exp=" + exp + "]";
	}
}
class Hero {
	int lv, hpCurr, hpMax, attOrg, defOrg, attWep, defAmr, expCurr;
	int posX, posY;
	int accFlag, accCnt;
	// 0000000
	// 앞에서부터 HR RE CO EX DX HU CU 장신구 유무 여부
	public Hero(int x, int y) {
		this.posX = x;
		this.posY = y;
		this.lv = 1;
		this.hpCurr = this.hpMax = 20;
		this.attOrg = this.defOrg = 2;
	}
	public int attSum() {
		return attOrg + attWep;
	}
	public int defSum() {
		return defOrg + defAmr;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("LV : ").append(this.lv).append("\n");
		sb.append("HP : ").append(this.hpCurr).append("/").append(this.hpMax).append("\n");
		sb.append("ATT : ").append(this.attOrg).append("+").append(this.attWep).append("\n");
		sb.append("DEF : ").append(this.defOrg).append("+").append(this.defAmr).append("\n");
		sb.append("EXP : ").append(this.expCurr).append("/").append(this.lv * 5).append("\n");
		
		// debug
//		 sb.append("ACC : ").append(Integer.toBinaryString(accFlag)).append("\n");
		return sb.toString();
	}
}
public class Main {
	static final int[] dx = {1, -1, 0, 0},
					   dy = {0, 0, 1, -1}; // 순서대로 R L D U
	static int sx, sy;
	static int n, m;
	static int monsters, items;
	static boolean isWin, isEnd;
	static String command, killedBy;
	static Hero hero; 
	static Tile[][] map;
	static boolean accCheck(int acc) {
		return (hero.accFlag & 1 << acc) > 0;
	}
	static void die(Tile tile) {
		tile.isHero = false;
		if (accCheck(1)) {
			// 처음위치로 부활 및 악세서리 회수 
			hero.hpCurr = hero.hpMax;
			hero.posX = sx;
			hero.posY = sy;
			map[hero.posY][hero.posX].isHero = true;
			hero.accCnt--;
			hero.accFlag ^= 1 << 1;
		}
		else {
			hero.hpCurr = 0;
			isWin = false;
			isEnd = true;
			if (tile.type == 2) killedBy = "SPIKE TRAP";
			else killedBy = tile.monster.name;
		}
	}
	static void trapped(Tile tile) {
		hero.hpCurr -= accCheck(4) ? 1 : 5;
		if (hero.hpCurr <= 0) die(tile);
	}
	static void pickUp(Tile tile) {
		Item item = tile.item;
		// 장신구는 비어있으면 반드시 착용 (4개까지)
		// 무기,방어구는 반드시 교체
		switch (item.type) {
			case 0:
				hero.attWep = ((Weapon)item).atk;
				break;
			case 1:
				hero.defAmr = ((Armor)item).def;
				break;
			case 2:
				if (hero.accCnt >= 4) break;
				int i = 0;
				switch (((Accessory)item).name) {
					case "HR":
						break;
					case "RE":
						i = 1;
						break;
					case "CO":
						i = 2;
						break;
					case "EX":
						i = 3;
						break;
					case "DX":
						i = 4;
						break;
					case "HU":
						i = 5;
						break;
					case "CU":
						i = 6;
						break;
				}
				if ((hero.accFlag & 1 << i) == 0) {
					hero.accFlag |= 1 << i;
					hero.accCnt++;
				}
				break;
		}
		
		// 아이템 줍고 빈칸으로 변경
		tile.type = 0;
		tile.item = null;
	}
	static void battleVictory(Tile tile) {
		// 경험치 정산
		int exp = tile.monster.exp;
		if (accCheck(3)) exp = (int)Math.floor(1.2 * exp);
		
		hero.expCurr += exp;
		if (hero.expCurr >= 5*hero.lv) {
			// 레벨업
			hero.lv++;
			hero.expCurr = 0;
			hero.hpCurr = hero.hpMax += 5;
			hero.attOrg += 2;
			hero.defOrg += 2;
		}
		
		if (tile.type == 4) {
			isEnd = true;
			isWin = true;
		}
		
		if (accCheck(0)) hero.hpCurr = (hero.hpCurr + 3) > hero.hpMax ? hero.hpMax : hero.hpCurr + 3;
		tile.type = 0;
	}
	static void battle(Tile tile) {
		Monster monster = tile.monster;
//		System.out.println(monster);
		// 장신구 고려해서 전투 생각하기
		
		int firstDmg = accCheck(2) ? (accCheck(4) ? 3*hero.attSum() : 2*hero.attSum()) : hero.attSum();
		firstDmg = Math.max(1, firstDmg - monster.def);
		monster.hp -= firstDmg;
		
//		System.out.println(firstDmg + " / " + monster.hp);
		
		if (monster.hp <= 0) {
			battleVictory(tile);
		}
		else {
			int heroDmg = Math.max(1, hero.attSum() - monster.def);
			int monsterDmg = Math.max(1, monster.att - hero.defSum());
			
			if (tile.type == 4 && accCheck(5)) hero.hpCurr = hero.hpMax;
			
			int h = monster.hp / heroDmg + (monster.hp % heroDmg > 0 ? 1 : 0),
				m = hero.hpCurr / monsterDmg + (hero.hpCurr % monsterDmg > 0 ? 1 : 0);
//			System.out.println(hero.attSum() + "-" + monster.def);
//			System.out.println(monster.hp + "/" + heroDmg + "=" + monster.hp / heroDmg);
//			System.out.println("h="+h + " m=" + m);
			
			if (tile.type == 4 && accCheck(5)) h--;
						
			hero.hpCurr -= h * monsterDmg;
			if (hero.hpCurr <= 0) die(tile);
			else battleVictory(tile);
		}
	}
	static void arrive(int x, int y) {
		// 이동 못하는 상황인 경우 (즉 다시 같은자리로 이동)
		if (hero.posX == x && hero.posY == y) {
			if (map[y][x].type == 2) {	
				trapped(map[y][x]);
			}
		}
		else {
			map[hero.posY][hero.posX].isHero = false;
			map[y][x].isHero = true;
			hero.posX = x;
			hero.posY = y;
			
			switch (map[y][x].type) {
				/*
				 'B' (1) : 아이템 상자
				'^' (2) : 가시함정
				'&' (3) : 몬스터
				'M' (4) : 보스몬스터
				 */
				case 1:
					pickUp(map[y][x]);
					break;
				case 2:
					trapped(map[y][x]);
					break;
				case 3:
				case 4:
					battle(map[y][x]);
					break;
					 
			}
		}
	}
	static void moveHero(int dir) {
		int nextX = hero.posX + dx[dir],
			nextY = hero.posY + dy[dir];
		
		// 이동 못하는 경우 해당 위치에 대해 다시 진행
		if (nextX <= 0 || nextY <= 0 || nextX > m || nextY > n || map[nextY][nextX].type == -1) {
			arrive(hero.posX, hero.posY);
			return;
		}
		
		// 이동이 가능한 경우는 이동
		arrive(nextX, nextY);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());	
		map = new Tile[n + 1][m + 1];
		
		for (int i = 1; i <= n; i++) {
			int j = 1;
			for (char c : br.readLine().toCharArray()) {
				int type = 0;
				switch (c) {
					case '#':
						type = -1;
						break;
					case 'B':
						type = 1;
						items++;
						break;
					case '^':
						type = 2;
						break;
					case '&':
						type = 3;
						monsters++;
						break;
					case 'M':
						type = 4;
						monsters++;
						break;
					case '@':
						type = 0;
						sx = j;
						sy = i;	
						hero = new Hero(j, i);
						break;
				}
				map[i][j++] = new Tile(type, c == '@');
			}
		}
		command = br.readLine();
		
		while (monsters-- > 0) {
			st = new StringTokenizer(br.readLine());
			int y = Integer.parseInt(st.nextToken()),
				x = Integer.parseInt(st.nextToken());
			map[y][x].monster = new Monster(st.nextToken(), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		while (items-- > 0) {
			st = new StringTokenizer(br.readLine());
			int y = Integer.parseInt(st.nextToken()),
				x = Integer.parseInt(st.nextToken());
			switch (st.nextToken()) {
				case "W" :
					map[y][x].item = new Weapon(0, Integer.parseInt(st.nextToken()));
					break;
				case "A" :
					map[y][x].item = new Armor(1, Integer.parseInt(st.nextToken()));
					break;
				case "O" :
					map[y][x].item = new Accessory(2, st.nextToken());
					break;
			}
		}
		
		int ans = 0;
		for (char c : command.toCharArray()) {
			switch (c) {
				case 'R':
					moveHero(0);
					break;
				case 'L':
					moveHero(1);
					break;
				case 'D':
					moveHero(2);
					break;
				case 'U':
					moveHero(3);
					break;
			}
			ans++;
			
//			for (int i = 1; i <= n; i++) {
//				for (int j = 1; j <= m; j++) sb.append(map[i][j]);
//				sb.append("\n");
//			}
//			sb.append("Command : ").append(c).append("\n");
//			sb.append("Passed Turns : ").append(ans).append("\n");
//			sb.append(hero).append("\n");
			
			if (isEnd) {
				break;
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) sb.append(map[i][j]);
			sb.append("\n");
		}
		sb.append("Passed Turns : ").append(ans).append("\n");
		sb.append(hero);
		
		if (isEnd) {
			if (isWin) sb.append("YOU WIN!");
			else sb.append("YOU HAVE BEEN KILLED BY ").append(killedBy).append("..");
		}
		else sb.append("Press any key to continue.");
		System.out.println(sb);
	}
}
