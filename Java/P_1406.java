import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String s = br.readLine();

		LinkedList<Character> list = new LinkedList<>();
		for (int i = 0; i < s.length(); i++)
			list.add(s.charAt(i));
		
		ListIterator<Character> iterator = list.listIterator(list.size());
		

		int num = Integer.parseInt(br.readLine());

		
		for (int i = 0; i < num; i++) {
			String[] inputs = br.readLine().split(" ");
			
			switch (inputs[0]) {
			case "L":
				if (iterator.hasPrevious())
					iterator.previous();
				break;
			case "D":
				if (iterator.hasNext())
					iterator.next();
				break;
			case "B":
				if (iterator.hasPrevious()) {
					iterator.previous();
					iterator.remove();
				}
				break;
			case "P":
				iterator.add(inputs[1].charAt(0));
				break;
			}
		}
		
		for (char c : list)
			sb.append(c);
		
		System.out.print(sb);
	}
}
