package splaytree;

public class SplayTree {
	
	public SplayTree() {
		// TODO Auto-generated constructor stub
		int list[] = {
                45, 50, 1, 9, 44, 56, 98, 43, 32
        };
 
        // construct a tree
        Node root = null;
 
        // We will just use the first tree as the root
        for (int i = 0; i < 9; ++i) {
                root = insert(root, new Node(list[i]));
        }
        
        walk(root);
 
        Node temp = search(root, 44);
        System.out.println("Found: " + temp.data);
        
        temp = minimum(root);
        System.out.println("Minimum: " + temp.data);
        temp = maximum(root);
        System.out.println("Maximum: " + temp.data);

        int k = 50;
        temp = successor(search(root, k));
        System.out.println("Successor of " + k + ": " + temp.data);
        
        root = delete(root, temp);
        walk(root);
	}
	
	void walk(Node root) {
		if (root == null) return;
		walk(root.left);
		System.out.println(root.data);
		walk(root.right);
	}
	
	// returns root
	Node insert(Node root, Node node) {
		if (root == null) {
			root = node;
			node.parent = null;
		}
		else {
			Node parent = null;
			Node search = root;
			boolean left = false;
			while (search != null) {
				parent = search;
				if (node.data <= search.data) {
					search = search.left;
					left = true;
				}
				else {
					search = search.right;
					left = false;
				}
			}
			node.parent = parent;
			if (left) parent.left = node;
			else parent.right = node;
		}
		return root;
	}
	
	Node search(Node root, int data) {
		if (root == null || root.data == data) return root;
        if (data < root.data) return search(root.left, data);
        else return search(root.right, data);
	}
	
	Node minimum(Node root) {
        if (root == null ) return null;
        while (root.left != null) {
               root = root.left;
        }
        return root;
	}
	
	Node maximum(Node root) {
        if (root == null ) return null;
        while (root.right != null) {
               root = root.right;
        }
        return root;
	}
 
	Node successor(Node node) {
		if (node == null) return null; 
        if (node.right != null) return minimum(node.right);
        else {
		// We need to traverse upwards in the tree to find a node where
		// the node is the left child of a parent
		// parent is the successor
        	Node parent = node.parent;
            while(parent != null && node != parent.left) {
            	node = parent;
                parent = node.parent;
            }
            return parent;
        }
	}
	
	Node transplant(Node root, Node u, Node v) {
		if (u.parent == null) root = v;
	    else if (u.parent.left == u) {
	    	u.parent.left = v;
	    } 
	    else if (u.parent.right == u) {
	            u.parent.right = v;
	    }
	    if (v != null) v.parent = u.parent;
	    return root;
	}
	
	// returns root
	Node delete(Node root, Node node) {
		if (node.left == null) {
            root = transplant(root, node, node.right);
		} 
		else if (node.right == null) {
            root = transplant(root, node, node.left);
		} 
		else {
            // Has two children -- successor must be on the right
            Node successor = minimum(node.right);
            //assert(successor.left == null);
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SplayTree spt = new SplayTree();
	}

}
