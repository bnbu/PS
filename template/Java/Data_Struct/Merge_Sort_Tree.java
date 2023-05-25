import java.util.*;
import java.io.*;
public class Main {
	static long cnt;
	static ArrayList<Integer>[] tree;
	static int lowerBoundSearch(ArrayList<Integer> arr, int k) {
    	int s = 0,
    		e = arr.size();
        while (s < e) {
            int m = (s + e) / 2;
            if (arr.get(m) < k) s = m + 1;
            else e = m;
        }
        return s;
	}
	static ArrayList<Integer> makeMergeSortTree(int start, int end, int node) {
		if (start == end) return tree[node];
		
		int mid = (start + end) / 2;
		ArrayList<Integer> left = makeMergeSortTree(start, mid, 2 * node),
							right = makeMergeSortTree(mid + 1, end, 2 * node + 1);
		
		int last = 0, n = 0;
		for (int l : left) {
			n = lowerBoundSearch(right, l);
			cnt += n;
			
			for (int i = last; i < n; i++) tree[node].add(right.get(i));
			tree[node].add(l);
			last = n;
		}
		for (int i = last; i < right.size(); i++) tree[node].add(right.get(i));
		
		return tree[node];
	}
	static int findNode(int start, int end, int node, int idx) {
		if (start == end) return node;
		
		int mid = (start + end) / 2;
		if (idx <= mid) return findNode(start, mid, 2 * node, idx);
		else return findNode(mid + 1, end, 2 * node + 1, idx);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int n = Integer.parseInt(br.readLine());
		
		tree = new ArrayList[4*n];
		for (int i = 1; i < tree.length; i++) tree[i] = new ArrayList<>();
		
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) tree[findNode(1, n, 1, i)].add(Integer.parseInt(st.nextToken()));
		makeMergeSortTree(1, n, 1);
		System.out.println(cnt);
	}
}