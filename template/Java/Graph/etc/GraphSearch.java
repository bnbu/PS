package test;
import java.util.*;

public class GraphSearch {
	static ArrayList<Integer>[] arr;
	static boolean[] visit;
	
	public static void main(String args[]) {
		 
        Scanner sc = new Scanner(System.in);
 
        int n = sc.nextInt();   //정점의 수
        int m = sc.nextInt();   //간선의 수
        int start = sc.nextInt();//시작할 정점
 
        arr = new ArrayList[n + 1];  //인덱스 편의상 n+1를 하고, 0번째 요소는 사용하지 않음
        visit = new boolean[n + 1];   //인덱스 편의상 n+1를 하고, 0번째 요소는 사용하지 않음
         
        for (int i = 1; i <= n; i++) {
            arr[i] = new ArrayList<Integer>();
        }
 
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();   //간선으로 이어진 정점1
            int v = sc.nextInt();   //정점1과 간선으로 이어진 정점2
            //양뱡향일 경우 양쪽다 저장해준다.
            arr[u].add(v);
            arr[v].add(u);
        }

        for (int i = 1; i < arr.length; i++)
    		Collections.sort(arr[i]);
        
        System.out.print("dfs1: ");
        dfs1(start);
        System.out.println();
        
        Arrays.fill(visit, false);
        
//        System.out.print("dfs2: ");
//        dfs2(start);
//        System.out.println();

        Arrays.fill(visit, false);
        System.out.print("bfs: ");
        bfs(start);
        System.out.println();
//        System.out.println("");
//        System.out.println("a : "+Arrays.toString(arr));
//        System.out.println("visit : "+Arrays.toString(visit));
    }
     
     
    public static void dfs1(int x) {
    	if (visit[x])
    		return;
    	else {
			visit[x] = true;
			System.out.print(x + " ");

			for (int y : arr[x]) {
				if (visit[y] == false) {
					dfs1(y);
				}
			}
    	}
    } // 재귀방법 -> 컴퓨터는 내부적으로 스택구조라 가능.
    
    public static void dfs2(int x) {
    	Stack<Integer> stack = new Stack<>();

    	stack.push(x);
    	
    	while (!stack.isEmpty()) {
    		int curr = stack.peek();
    		if (!visit[curr]) {
				System.out.print(curr + " ");
				visit[curr] = true;
    		}
    		stack.pop();
    		
    		for (int i = 0; i < arr[curr].size(); i++) {
    			int next = arr[curr].get(i);
    			if (!visit[next])
    				stack.push(next);
    		}
    	}
    } // 직접적으로 스택을 사용하는 구조
    // => 여기서 stack -> queue로 바꾸면 bfs 완성임
    
    public static void bfs(int x) {
    	Queue<Integer> q = new LinkedList<>();
    	
    	q.add(x);
    	
    	while (!q.isEmpty()) {
    		int curr = q.peek();
    		if (!visit[curr]) {
    			System.out.print(curr + " ");
    			visit[curr] = true;
    		}
    		q.poll();
    		
    		for (int i = 0; i < arr[curr].size(); i++) {
    			int next = arr[curr].get(i);
    			if (!visit[next])
    				q.add(next);
    		}
    	}
    } // stack을 사용하는 dfs와 달리 queue를 사용하는 bfs.
}
