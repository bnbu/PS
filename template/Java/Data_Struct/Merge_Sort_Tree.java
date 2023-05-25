import java.util.*;
import java.io.*;
public class Main {
	static int n;
	static int[] arr;
	static ArrayList<Integer>[] mergeTree;
	static int lowerBound(ArrayList<Integer> arr, int k) {
    	int s = 0,
    		e = arr.size();
        while (s < e) {
            int m = (s + e) / 2;
            if (arr.get(m) < k) s = m + 1;
            else e = m;
        }
        return s;
	}
	static int upperBound(ArrayList<Integer> arr, int k) {
		int s = 0,
			e = arr.size();
		
		while(s < e) {
			int m = (s + e) / 2;
			if(arr.get(m) <= k) s = m + 1;
			else e = m;
		}
		return s;
	}
	static ArrayList<Integer> makeMergeSortTree(int start, int end, int node) {
		if (start == end) {
			mergeTree[node].add(arr[start]);
			return mergeTree[node];
		}
		int mid = (start + end) / 2;
		ArrayList<Integer> left = makeMergeSortTree(start, mid, 2 * node),
				right = makeMergeSortTree(mid + 1, end, 2 * node + 1);

		int last = 0, n = 0;
		for (int l : left) {
			n = lowerBound(right, l);
			for (int i = last; i < n; i++) mergeTree[node].add(right.get(i));
			mergeTree[node].add(l);
			last = n;
		}
		for (int i = last; i < right.size(); i++) mergeTree[node].add(right.get(i));
		
		return mergeTree[node];
	}
	public static int get(int start, int end, int node, int left, int right, int k) {
		if (right < start || end < left) return 0;
		if (left <= start && end <= right) {
			int ret = upperBound(mergeTree[node], k);
			return mergeTree[node].size() - ret;
		}
		int mid = (start + end) / 2;
		return get(start, mid, 2*node, left, right, k) + get(mid + 1, end, 2*node + 1, left, right, k);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		
		mergeTree = new ArrayList[4*n];
		for (int i = 1; i < 4*n; i++) mergeTree[i] = new ArrayList<>();
		
		arr = new int[n + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) arr[i] = Integer.parseInt(st.nextToken());
		makeMergeSortTree(1, n, 1);
		
		int m = Integer.parseInt(br.readLine());
		while (m-- > 0) { 
			st = new StringTokenizer(br.readLine());
			sb.append(get(1, n, 1, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))).append("\n");
		}
		System.out.print(sb);
 	}
}
