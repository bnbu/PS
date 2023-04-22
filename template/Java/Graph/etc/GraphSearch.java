package test;
import java.util.*;

public class GraphSearch {
	static ArrayList<Integer>[] arr;
	static boolean[] visit;
	
	public static void main(String args[]) {
		 
        Scanner sc = new Scanner(System.in);
 
        int n = sc.nextInt();   //������ ��
        int m = sc.nextInt();   //������ ��
        int start = sc.nextInt();//������ ����
 
        arr = new ArrayList[n + 1];  //�ε��� ���ǻ� n+1�� �ϰ�, 0��° ��Ҵ� ������� ����
        visit = new boolean[n + 1];   //�ε��� ���ǻ� n+1�� �ϰ�, 0��° ��Ҵ� ������� ����
         
        for (int i = 1; i <= n; i++) {
            arr[i] = new ArrayList<Integer>();
        }
 
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();   //�������� �̾��� ����1
            int v = sc.nextInt();   //����1�� �������� �̾��� ����2
            //�瓇���� ��� ���ʴ� �������ش�.
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
    } // ��͹�� -> ��ǻ�ʹ� ���������� ���ñ����� ����.
    
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
    } // ���������� ������ ����ϴ� ����
    // => ���⼭ stack -> queue�� �ٲٸ� bfs �ϼ���
    
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
    } // stack�� ����ϴ� dfs�� �޸� queue�� ����ϴ� bfs.
}
