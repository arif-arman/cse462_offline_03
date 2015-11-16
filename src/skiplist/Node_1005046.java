package skiplist;

public class Node_1005046 {
	int key;
	Node_1005046 above;
	Node_1005046 below;
	Node_1005046 before;
	Node_1005046 after;
	
	int linkCost;
	
	public Node_1005046(int key) {
		// TODO Auto-generated constructor stub
		this.key = key;
		this.linkCost = 0;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = "" + key + " (" + linkCost + ")" ;
		return s;
	}
	
	public void testPrint() {
		System.out.println("After " + after + " Below " + below + " Before " + before + " Above " + above);
	}
	
}
