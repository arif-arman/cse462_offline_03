import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class SkipList {
	
	final int INF = 10000;
	Node start;
	Node end;
	int height;
	int size;
	Random rand;
	
	public SkipList() {
		// TODO Auto-generated constructor stub
		start = new Node(-INF);
		end = new Node(INF);
		start.after = end;
		end.before = start;
		height = 0;
		size = 0;
		rand = new Random();
	}
	
	public void skipPrint() {
		Node downward = start;
		while(downward != null) {
			Node forward = downward;
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
	
	public Node skipSearch(int k) {
		Node p = start;
		while(p.below != null) {
			p = p.below;
			while(p.after.key <= k) p = p.after;
		}
		return p;
	}
	
	Node insertAfterAbove(Node p, Node q, int key) {
		Node temp = new Node(key);
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
		Node n1 = new Node(-INF);
		Node n2 = new Node(INF);
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
		if (height == 0) extendTopLayer();
		
		Node p = skipSearch(k);
		Node q = insertAfterAbove(p,null,k);
		int i = 0;
		while (rand.nextBoolean()) {
			while (p.above == null) {
				p = p.before;
			}
			p = p.above;
			q = insertAfterAbove(p, q, k);
			if (++i == height) extendTopLayer();
		}
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		SkipList skp = new SkipList();
		/*
		File file = new File("input.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			skp.skipInsert(input.nextInt());
			//skp.skipPrint();
			//System.out.println();
		}
		input.close();
		*/
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			skp.skipInsert(rand.nextInt(100));
		}
		skp.skipPrint();
	}
}
