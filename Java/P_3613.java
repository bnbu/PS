import java.util.Scanner;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		
		int lastUnderBar = -1;
		boolean upper = false, underBar = false;
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) == '_') {
				underBar = true;
				if (lastUnderBar == i - 1)
					upper = true;
				if (i == 0 || i == name.length() - 1)
					upper = true;
				lastUnderBar = i;
			}
				
			else if (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') {
				upper = true;
				if (i == 0)
					underBar = true;
			}
		}
		
		if (underBar && upper)
			System.out.println("Error!");
		else if (underBar) {
			String[] split = name.split("_");
			String result = "";
			for (int i = 0; i < split.length; i++) {
				for (int j = 0; j < split[i].length(); j++) {
					if (i == 0)
						result += split[i].charAt(j);
					else
						result += (j == 0) ? (char)(split[i].charAt(j) - 32) : split[i].charAt(j);
				}
			}
			System.out.println(result);
		}
		else if (upper) {
			String result = "";
			for (int i = 0; i < name.length(); i++) {
				if (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z' && i > 0)
					result += "_" + (char)(name.charAt(i) + 32);
				else 
					result += name.charAt(i);
			}
			System.out.println(result);
		}
		else {
			System.out.println(name);
		}
	}
}
// 2020-09-14 해결
