import java.util.*;
import java.io.*;
public class J {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String[] s = br.readLine().split(" ");
		int v0 = Integer.parseInt(s[0]),m = Integer.parseInt(s[1]),
			t = Integer.parseInt(s[2]);
		int v = v0;
		int x = 0, y = 0;
		int direction = 0;
		boolean zero = false;
		if (t < 8) {
			while (t-- > 0) {
				switch (direction) {
				case 0:
					y += v;
					break;
				case 1:
					x += v;
					break;
				case 2: 
					y -= v;
					break;
				case 3:
					x -= v;
					break;
				}
				direction = (direction + 1) % 4;
				v = (v * m) % 10;
			}
		}
		else {
			for (int i = 0; i < 4; i++) {
				switch (direction) {
				case 0:
					y += v;
					break;
				case 1:
					x += v;
					break;
				case 2: 
					y -= v;
					break;
				case 3:
					x -= v;
					break;
				}
				direction = (direction + 1) % 4;
				v = (v * m) % 10;
				if (v == 0)
					zero = true;
			}
			if (!zero) {
				int fx = x, fy = y;
				for (int i = 0; i < 4; i++) {
					switch (direction) {
					case 0:
						y += v;
						break;
					case 1:
						x += v;
						break;
					case 2: 
						y -= v;
						break;
					case 3:
						x -= v;
						break;
					}
					direction = (direction + 1) % 4;
					v = (v * m) % 10;
					if (v == 0)
						zero = true;
				}
				t -= 4;
				int repeat = t / 4;
				t %= 4;
				x -= fx;
				x *= repeat;
				x += fx;
				y -= fy;
				y *= repeat;
				y += fy;
				
				while (t-- > 0) {
					switch (direction) {
					case 0:
						y += v;
						break;
					case 1:
						x += v;
						break;
					case 2: 
						y -= v;
						break;
					case 3:
						x -= v;
						break;
					}
					direction = (direction + 1) % 4;
					v = (v * m) % 10;
				}
			}
		}
		
		 
		System.out.println(x + " " + y);
	}
}