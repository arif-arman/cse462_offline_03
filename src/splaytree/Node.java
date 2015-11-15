package splaytree;

public class Node {
	
	public int data;
	public Node parent;
	public Node left;
	public Node right;
	
	public Node(int data) {
		// TODO Auto-generated constructor stub
		this.data = data;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = "" + data;
		return s;
	}
	

}
