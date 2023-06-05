package D0404;

import java.util.*;
import java.io.*;
public class Main {
	public static final int NEXT_MAX = 26;
	public static class Trie {
		Trie[] next;
		boolean output;
		
		public Trie() {
			this.output = false;
			next = new Trie[26];
		}
		
		void insert(String key, int idx) {
			if (key.length() == idx) this.output = true;
			else {
				int curr = key.charAt(idx) - 'a';
				if (next[curr] == null) next[curr] = new Trie();
				next[curr].insert(key, idx + 1);
			}
		}
		
		boolean find(String key, int idx) {
			if (key.length() == idx) return this.output;
			
			int curr = key.charAt(idx) - 'a';
			if (next[curr] == null) return false;
			return next[curr].find(key, idx + 1);
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()),
			m = Integer.parseInt(st.nextToken());
		
		Trie root = new Trie();
		
		while (n-- > 0) {
			st = new StringTokenizer(br.readLine());
			String str = st.nextToken();
			root.insert(str, 0);
		}
		
		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			String str = st.nextToken();
			System.out.println(root.find(str, 0));
		}
	}
}