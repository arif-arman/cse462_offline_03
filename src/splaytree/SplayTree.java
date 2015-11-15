package splaytree;

import lib.BTreePrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SplayTree {
	
	public static PrintWriter out;

	public SplayTree() throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		File file = new File("input.txt");
		Scanner input = new Scanner(file);
		out = new PrintWriter(new File("output.txt"));
		// construct a tree
		Node root = null;
		while (input.hasNext()) {
			root = splayInsert(root, new Node(input.nextInt()));
			//BTreePrinter.printNode(root);
		}
		input.close();
		out.println("--- Tree walk ---");
		walk(root);
		BTreePrinter.printNode(root);
		
		//root = splayInsert(root, new Node(77));
		//BTreePrinter.printNode(root);
		Node temp;
		temp = minimum(root);
		out.println("Minimum: " + temp.data);
		temp = maximum(root);
		out.println("Maximum: " + temp.data);


		// root = delete(root, temp);
		// BTreePrinter.printNode(root);
		// walk(root);

		// root = leftRotate(root, temp);
		// BTreePrinter.printNode(root);
		
		// splay
		int searchKey = 53;
		root = splaySearch(root, searchKey);
		BTreePrinter.printNode(root);
		
		//delete
		searchKey = 99;
		root = splayDelete(root, searchKey);
		BTreePrinter.printNode(root);
		
		out.close();
	}

	void walk(Node root) {
		if (root == null)
			return;
		walk(root.left);
		out.println(root.data);
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
		//System.out.println(p + " " + node);
		if(p != null) {
			root = splay(root, p);
			//BTreePrinter.printNode(root);
			if (root.data <= node.data) {
				node.left = root;
				root.parent = node;
				if (root.right != null) {
					node.right = root.right;
					root.right.parent = node;
					root.right = null;
				}
			}
			else {
				node.right = root;
				root.parent = node;
				if (root.left != null) {
					node.left = root.left;
					root.left.parent = node;
					root.left = null;
				}
			}
		}
		return node;
	}

	Node search(Node root, int data) {
		if (root == null || root.data == data)
			return root;
		if (data < root.data)
			return search(root.left, data);
		else
			return search(root.right, data);
	}

	Node splaySearch(Node root, int data) {
		out.println("Searching ... " + data);
		Node node = search(root, data);
		if (node != null) {
			out.println(data + " found");
			return splay(root,node);
		}
		else {
			out.println(data + " not found");
			return root;
		}
	}
	
	Node minimum(Node root) {
		if (root == null)
			return null;
		while (root.left != null) {
			root = root.left;
		}
		return root;
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

	Node splayDelete(Node root, int searchKey) {
		out.println("Deleting ... " + searchKey);
		Node temp = splaySearch(root,searchKey);
		//BTreePrinter.printNode(temp);
		if (temp.data != searchKey) {
			out.println(searchKey + " not found");
			return root;
		}
		else {
			Node l = temp.left;
			Node r = temp.right;
			temp = null;
			Node max = maximum(l);
			l = splay(l, max);
			l.right = r;
			r.parent = l;
			out.println(searchKey + " deleted");
			return l;
		}
		
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
