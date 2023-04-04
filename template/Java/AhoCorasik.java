package D0404;

import java.util.*;
import java.io.*;
public class AhoCorasik {
	public static final int NEXT_MAX = 26;
	public static class Trie {
		Trie[] next = new Trie[26];
		boolean output;
		
		Trie fail;
		
		public Trie() {
			this.output = false;
			this.fail = null;
			for (int i = 0; i < 26; i++) next[i] = null;
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
			if (key.length() - 1 == idx) return this.output;
			
			int curr = key.charAt(idx) - 'a';
			if (next[curr] == null) return false;
			return next[curr].find(key, idx + 1);
		}
	}
	public static void makeFailure(Trie root) {
		Queue<Trie> q = new LinkedList<>();
		root.fail = root;
		q.add(root);
		while (!q.isEmpty()) {
			Trie curr = q.poll();
			
			for (int i = 0; i < 26; i++) {
				Trie next = curr.next[i];
				if (next == null) continue;
				
				if (curr == root)
					next.fail = root;
				else {
					Trie dest = curr.fail;
					while (dest != root && dest.next[i] == null) dest = dest.fail;
				
					if (dest.next[i] != null) dest = dest.next[i];
					next.fail = dest;
				}
				
				if (next.fail.output) next.output = true;
				q.add(next);
			}
		}
	}
	public static boolean ahoCorasik(String s, Trie root) {
		Trie curr = root;
		boolean ret = false;
		for (int i = 0; i < s.length(); i++) {
			int n = s.charAt(i) - 'a';
			
			while (curr != root && curr.next[n] == null) curr = curr.fail;
			
			if (curr.next[n] != null)
				curr = curr.next[n];
			
			if (curr.output) {
				ret = true;
				break;
			}
		}
		return ret;
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