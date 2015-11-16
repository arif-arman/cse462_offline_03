package splaytree;

import lib.BTreePrinter_1005046;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class SplayTree_1005046 {
	
	//public static PrintWriter out;

	public SplayTree_1005046() throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		File file = new File("input.txt");
		Scanner input = new Scanner(file);
		Scanner scanner = new Scanner(System.in);
		//out = new PrintWriter(new File("output.txt"));
		// construct a tree
		Node_1005046 root = null;
		
		while (input.hasNext()) {
			int choice = input.nextInt();
			if (choice == 1) {
				root = splayInsert(root, new Node_1005046(input.nextInt()));
			}
			else if (choice == 2) {
				int searchKey = input.nextInt();
				root = splaySearch(root, searchKey);
				BTreePrinter_1005046.printNode(root);
			}
			else if (choice == 3) {
				int searchKey = input.nextInt();
				root = splayDelete(root, searchKey);
				BTreePrinter_1005046.printNode(root);
			}
			else if (choice == 4) BTreePrinter_1005046.printNode(root);
			else {
				System.out.println("--- Tree walk ---");
				walk(root);
			}
			//BTreePrinter.printNode(root);
		}
		
		input.close();
		
		/*
		BTreePrinter_1005046.printNode(root);
		
		int insertKey = 77;
		//root = splayInsert(root, new Node_1005046(insertKey));
		//BTreePrinter_1005046.printNode(root);
		Node_1005046 temp;
		temp = minimum(root);
		System.out.println("Minimum: " + temp.data);
		temp = maximum(root);
		System.out.println("Maximum: " + temp.data);

		// root = delete(root, temp);
		// BTreePrinter.printNode(root);
		// walk(root);

		// root = leftRotate(root, temp);
		// BTreePrinter.printNode(root);
		
		// splay
		int searchKey = 54;
		root = splaySearch(root, searchKey);
		BTreePrinter_1005046.printNode(root);
		
		//delete
		searchKey = 54;
		root = splayDelete(root, searchKey);
		BTreePrinter_1005046.printNode(root);
		
		*/
	//	out.close();
	}

	void walk(Node_1005046 root) {
		if (root == null)
			return;
		walk(root.left);
		System.out.println(root.data);
		walk(root.right);
	}

	// returns root
	Node_1005046 insert(Node_1005046 root, Node_1005046 node) {
		if (root == null) {
			root = node;
			node.parent = null;
		} else {
			Node_1005046 parent = null;
			Node_1005046 search = root;
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
	
	Node_1005046 splayInsert(Node_1005046 root, Node_1005046 node) {
		System.out.println("Inserting ... " + node);
		Node_1005046 t = root;
		Node_1005046 p = null;
		while(t != null) {
	      p = t;
	      if(t.data <= node.data) t = t.right;
	      else t = t.left;
	    }
		//System.System.out.println(p + " " + node);
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

	Node_1005046 search(Node_1005046 root, int data) {
		if (root == null || root.data == data)
			return root;
		if (data < root.data)
			return search(root.left, data);
		else
			return search(root.right, data);
	}

	Node_1005046 splaySearch(Node_1005046 root, int data) {
		System.out.println("Searching ... " + data);
		Node_1005046 node = search(root, data);
		if (node != null) {
			System.out.println(data + " found");
			return splay(root,node);
		}
		else {
			System.out.println(data + " not found");
			return root;
		}
	}
	
	Node_1005046 minimum(Node_1005046 root) {
		if (root == null)
			return null;
		while (root.left != null) {
			root = root.left;
		}
		return root;
	}
	
	Node_1005046 maximum(Node_1005046 root) {
		if (root == null)
			return null;
		while (root.right != null) {
			root = root.right;
		}
		return root;
	}

	Node_1005046 successor(Node_1005046 node) {
		if (node == null)
			return null;
		if (node.right != null)
			return minimum(node.right);
		else {
			// We need to traverse upwards in the tree to find a node where
			// the node is the left child of a parent
			// parent is the successor
			Node_1005046 parent = node.parent;
			while (parent != null && node != parent.left) {
				node = parent;
				parent = node.parent;
			}
			return parent;
		}
	}

	Node_1005046 transplant(Node_1005046 root, Node_1005046 u, Node_1005046 v) {
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
	Node_1005046 delete(Node_1005046 root, Node_1005046 node) {
		if (node.left == null) {
			root = transplant(root, node, node.right);
		} else if (node.right == null) {
			root = transplant(root, node, node.left);
		} else {
			// Has two children -- successor must be on the right
			Node_1005046 successor = minimum(node.right);
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

	Node_1005046 splayDelete(Node_1005046 root, int searchKey) {
		System.out.println("Deleting ... " + searchKey);
		Node_1005046 temp = splaySearch(root,searchKey);
		//BTreePrinter_1005046.printNode(temp);
		if (temp.data != searchKey) {
			System.out.println(searchKey + " not found");
			return root;
		}
		else {
			Node_1005046 l = temp.left;
			Node_1005046 r = temp.right;
			Node_1005046 max;
			if (l == null && r != null) {
				r.parent = null;
				l = r;
			}				
			else if (r == null && l != null) {
				l.parent = null;
			}
			else if (r == null && l == null) {
				l = null;
				temp = null;
			}
			else{
				max = maximum(l);
				l = splay(l, max);
				l.right = r;
				r.parent = l;
			}
				
			temp = null;
			System.out.println(searchKey + " deleted");
			return l;
		}
		
	}
	Node_1005046 leftRotate(Node_1005046 root, Node_1005046 node) {
		Node_1005046 t = node.right;
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

	Node_1005046 rightRotate(Node_1005046 root, Node_1005046 node) {
		Node_1005046 t = node.left;
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

	Node_1005046 splay(Node_1005046 root, Node_1005046 node) {
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
		SplayTree_1005046 spt = new SplayTree_1005046();

	}

}
