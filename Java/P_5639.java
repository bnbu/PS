import java.util.*;

class Node {
	int v;
	Node left;
	Node right;
	Node parent;
	public Node(int v) {
		this.v = v;
		left = right = parent = null;
	}
}
class BinarySearchTree {
	Node root; 
	
	BinarySearchTree() {
		root = null;
	}
	void insert(int v) {
		Node n = new Node(v);
		if (root == null) {
			root = n;
		}
		else {
			Node curr = root;
			Node save = null;
			while (curr != null) {
				save = curr;
				if (curr.v < v)
					curr = curr.right;
				else
					curr = curr.left;
			}
			if (save.v < v) {
				save.right = n;
				n.parent = save;
			}
			else {
				save.left = n;
				n.parent = save;
			}
		}
	}
	void postOrder(Node n) {
		if (n.left != null)
			postOrder(n.left);
		if (n.right != null)
			postOrder(n.right);
		System.out.println(n.v);
	}
}
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		BinarySearchTree bst = new BinarySearchTree();
		while (scan.hasNextInt()) {
			int v = scan.nextInt();
			bst.insert(v);
		}
		bst.postOrder(bst.root);
	}
}
// 2020-12-23 03:54 해결
// 자료구조에서 배운 이진탐색트리..
