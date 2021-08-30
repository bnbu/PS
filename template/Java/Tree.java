import java.util.*;
import java.io.*;
class Node implements Comparable<Node>{
	Node parent;
	int data;
	ArrayList<Node> children;
	public Node(int n) {
		parent = null;
		data = n;
		children = new ArrayList<>();
	}
	public int compareTo(Node n) {
		return this.data - n.data;
	}
}
class Tree {
	Node root;
	ArrayList<Node> nodeList;
	public Tree() {
		root = new Node(1);
		nodeList = new ArrayList<>();
		nodeList.add(root);
	}
	public boolean insertNode(int p, int d) {
		for (int i = 0; i < nodeList.size(); i++) {
			Node u = nodeList.get(i);
			if (u.data == p) {
				Node v = new Node(d);
				v.parent = u;
				u.children.add(v);
				nodeList.add(v);
				
				return true;
			}
		}
		return false;
	}
}
public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int t = Integer.parseInt(br.readLine());
		Tree tree = new Tree();
		while (t-- > 1) {
			st = new StringTokenizer(br.readLine());
			int p = Integer.parseInt(st.nextToken()),
				d = Integer.parseInt(st.nextToken());
			if (tree.insertNode(p, d))
				continue;
			else if (tree.insertNode(d, p))
				continue;
		}
		Collections.sort(tree.nodeList);
		for (int i = 1; i < tree.nodeList.size(); i++)
			System.out.println(tree.nodeList.get(i).parent.data);
	}
}
