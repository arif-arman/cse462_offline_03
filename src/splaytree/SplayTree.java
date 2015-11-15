package splaytree;

import lib.BTreePrinter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SplayTree {

	public SplayTree() throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		File file = new File("input.txt");
		Scanner input = new Scanner(file);

		// construct a tree
		Node root = null;
		while (input.hasNext()) {
			root = splayInsert(root, new Node(input.nextInt()));
			BTreePrinter.printNode(root);
		}
		input.close();
		// walk(root);
		BTreePrinter.printNode(root);
		Node temp = search(root, 44);
		if (temp != null)
			System.out.println("Found: " + temp.data);
		else
			System.out.println("Not found");

		temp = minimum(root);
		System.out.println("Minimum: " + temp.data);
		temp = maximum(root);
		System.out.println("Maximum: " + temp.data);

		int k = 99;
		temp = successor(search(root, k));
		if (temp != null)
			System.out.println("Successor of " + k + ": " + temp.data);
		else
			System.out.println("Not found");

		// root = delete(root, temp);
		// BTreePrinter.printNode(root);
		// walk(root);

		// root = leftRotate(root, temp);
		// BTreePrinter.printNode(root);
		
		// splay
		root = splaySearch(root, 17);
		BTreePrinter.printNode(root);
	}

	void walk(Node root) {
		if (root == null)
			return;
		walk(root.left);
		System.out.println(root.data);
		walk(root.right);
	}

	// returns root
	Node insert(Node root, Node node) {
		if (root == null) {
			root = node;
			node.parent = null;
		} else {
			Node parent = null;
			Node search = root;
			boolean left = false;
			while (search != null) {
				parent = search;
				if (node.data <= search.data) {
					search = search.left;
					left = true;
				} else {
					search = search.right;
					left = false;
				}
			}
			node.parent = parent;
			if (left)
				parent.left = node;
			else
				parent.right = node;
		}
		return root;
	}
	
	Node splayInsert(Node root, Node node) {
		Node t = root;
		Node p = null;
		while(t != null) {
	      p = t;
	      if(t.data <= node.data) t = t.right;
	      else t = t.left;
	    }
		node.parent = p;
	    if(p == null ) root = node;
	    else if(p.data <= node.data) p.right = node;
	    else p.left = node;
		return splay(root, node);
	}

	Node search(Node root, int data) {
		if (root == null || root.data == data)
			return root;
		if (data < root.data)
			return search(root.left, data);
		else
			return search(root.right, data);
	}

	Node minimum(Node root) {
		if (root == null)
			return null;
		while (root.left != null) {
			root = root.left;
		}
		return root;
	}
	
	Node splaySearch(Node root, int data) {
		Node node = search(root, data);
		if (node != null) return splay(root,node);
		else return root;
	}

	Node maximum(Node root) {
		if (root == null)
			return null;
		while (root.right != null) {
			root = root.right;
		}
		return root;
	}

	Node successor(Node node) {
		if (node == null)
			return null;
		if (node.right != null)
			return minimum(node.right);
		else {
			// We need to traverse upwards in the tree to find a node where
			// the node is the left child of a parent
			// parent is the successor
			Node parent = node.parent;
			while (parent != null && node != parent.left) {
				node = parent;
				parent = node.parent;
			}
			return parent;
		}
	}

	Node transplant(Node root, Node u, Node v) {
		if (u.parent == null)
			root = v;
		else if (u.parent.left == u) {
			u.parent.left = v;
		} else if (u.parent.right == u) {
			u.parent.right = v;
		}
		if (v != null)
			v.parent = u.parent;
		return root;
	}

	// returns root
	Node delete(Node root, Node node) {
		if (node.left == null) {
			root = transplant(root, node, node.right);
		} else if (node.right == null) {
			root = transplant(root, node, node.left);
		} else {
			// Has two children -- successor must be on the right
			Node successor = minimum(node.right);
			// assert(successor.left == null);
			if (successor != node.right) {
				root = transplant(root, successor, successor.right);
				successor.right = node.right;
				successor.right.parent = successor;
			}
			root = transplant(root, node, successor);
			successor.left = node.left;
			successor.left.parent = successor;
		}
		return root;
	}

	Node leftRotate(Node root, Node node) {
		Node t = node.right;
		if (t == null)
			return root;
		else {
			node.right = t.left;
			if (t.left != null)
				t.left.parent = node;
			t.parent = node.parent;
		}
		if (node.parent == null)
			root = t;
		else if (node == node.parent.left)
			node.parent.left = t;
		else
			node.parent.right = t;
		if (t != null)
			t.left = node;
		node.parent = t;
		return root;
	}

	Node rightRotate(Node root, Node node) {
		Node t = node.left;
		if (t == null)
			return root;
		else {
			node.left = t.right;
			if (t.right != null)
				t.right.parent = node;
			t.parent = node.parent;
		}
		if (node.parent == null)
			root = t;
		else if (node == node.parent.left)
			node.parent.left = t;
		else
			node.parent.right = t;
		if (t != null)
			t.right = node;
		node.parent = t;
		return root;
	}

	Node splay(Node root, Node node) {
		while (node.parent != null) {
			if (node.parent.parent == null) {
				if (node.parent.left == node)
					root = rightRotate(root, node.parent);
				else
					root = leftRotate(root, node.parent);
			} 
			else if(node.parent.left == node && node.parent.parent.left == node.parent) {
				root = rightRotate(root, node.parent.parent);
				root = rightRotate(root, node.parent);
			} 
			else if(node.parent.right == node && node.parent.parent.right == node.parent) {
				root = leftRotate(root, node.parent.parent);
				root = leftRotate(root, node.parent);
			} 
			else if(node.parent.left == node && node.parent.parent.right == node.parent) {
				root = rightRotate(root, node.parent);
				root = leftRotate(root, node.parent);
			} 
			else {
				root = leftRotate(root, node.parent);
				root = rightRotate(root, node.parent);
			}
		}
		return root;
	}

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		SplayTree spt = new SplayTree();

	}

}
