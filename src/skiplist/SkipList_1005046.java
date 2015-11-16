package skiplist;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class SkipList_1005046 {
	
	final int INF = 10000;
	Node_1005046 start;
	Node_1005046 end;
	int height;
	int size;
	Random rand;
	
	public SkipList_1005046() {
		// TODO Auto-generated constructor stub
		start = new Node_1005046(-INF);
		end = new Node_1005046(INF);
		start.after = end;
		end.before = start;
		height = 0;
		size = 0;
		rand = new Random();
	}
	
	public void skipPrint() {
		Node_1005046 downward = start;
		while(downward != null) {
			Node_1005046 forward = downward;
			while(true) {
				System.out.print(forward);
				forward = forward.after;
				if (forward == null) break;
				System.out.print(" --- ");
			}
			System.out.println();
			downward = downward.below;
		}
		
	}
	
	
	public Node_1005046 skipSearch(int k) {
		Node_1005046 p = start;
		while(p.below != null) {
			p = p.below;
			while(p.after.key <= k) p = p.after;
		}
		return p;
	}
	
	Node_1005046 insertAfterAbove(Node_1005046 p, Node_1005046 q, int key, int cost) {
		Node_1005046 temp = new Node_1005046(key);
		temp.linkCost = cost;
		temp.before = p;
		temp.after = p.after;
		p.after.before = temp;
		p.after = temp;
		temp.below = q;
		if (q != null) q.above = temp;
		//System.out.println(p + " " + q + " " + temp );
		//if (q!=null) q.testPrint();
		//temp.testPrint();
		return temp;
	}
	
	void extendTopLayer() {
		Node_1005046 n1 = new Node_1005046(-INF);
		Node_1005046 n2 = new Node_1005046(INF);
		n1.below = start;
		n1.after = n2;
		n2.before = n1;
		n2.below = end;
		start.above = n1;
		end.above = n2;
		start = n1;
		end = n2;
		height++;
	}
	
	public void skipInsert(int k) {
		//System.out.println(k);
		if (height == 0) extendTopLayer();
		
		Node_1005046 p = skipSearch(k);
		Node_1005046 q = insertAfterAbove(p,null,k,1);
		int i = 0;
		
		while (rand.nextBoolean()) {
			int count = q.linkCost;
			while (p.above == null) {
				count += p.linkCost;
				p = p.before;			
			}
			p = p.above;
			q = insertAfterAbove(p, q, k, count);
			Node_1005046 r = q.after;
			if (r.key != INF) {
				Node_1005046 t = r.below;
				while (t.key != k) {
					t = t.before;	
				}
				r.linkCost = r.linkCost - q.linkCost + 1;
			}
			if (++i == height) extendTopLayer();
		}
		
		Node_1005046 r = q.after;
		while (true) {
			if (r.above == null) r = r.after;
			if (r == null) break;
			while (r.above!= null) {
				r = r.above;
				r.linkCost++;
			}
			
		}
		//skipPrint();
	}
	
	public int indexOf(int key) {
		int index = -1;
		Node_1005046 p = start;
		while(p.below != null) {
			p = p.below;
			while(p.after.key <= key) {
				p = p.after;
				index += p.linkCost;
			}
		}
		return index;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		SkipList_1005046 skp = new SkipList_1005046();
		File file = new File("input1.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			int choice = input.nextInt();
			if (choice == 1) skp.skipInsert(input.nextInt());
			else if (choice == 2) {
				int searchKey = input.nextInt();
				System.out.println("Index of " + searchKey + ": " + skp.indexOf(searchKey));
				//skp.skipPrint();
			}
			else skp.skipPrint();
			//System.out.println();
		}
		input.close();
		/*
		int insertKey = 97;
		skp.skipInsert(insertKey);
		*/
		


		//System.out.println("Index of " + searchKey + ": " + skp.indexOf(searchKey));
	}
}
