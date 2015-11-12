package skiplist;

public class Node {
	int key;
	Node above;
	Node below;
	Node before;
	Node after;
	
	int linkCost;
	
	public Node(int key) {
		// TODO Auto-generated constructor stub
		this.key = key;
		this.linkCost = 0;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = "" + key ;
		return s;
	}
	
	public void testPrint() {
		System.out.println("After " + after + " Below " + below + " Before " + before + " Above " + above);
	}
	
}
