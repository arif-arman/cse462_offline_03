package skiplist;
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
	
	Node insertAfterAbove(Node p, Node q, int key, int cost) {
		Node temp = new Node(key);
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
		//System.out.println(k);
		if (height == 0) extendTopLayer();
		
		Node p = skipSearch(k);
		Node q = insertAfterAbove(p,null,k,1);
		int i = 0;
		
		while (rand.nextBoolean()) {
			int count = q.linkCost;
			while (p.above == null) {
				count += p.linkCost;
				p = p.before;			
			}
			p = p.above;
			q = insertAfterAbove(p, q, k, count);
			Node r = q.after;
			if (r.key != INF) {
				Node t = r.below;
				while (t.key != k) {
					t = t.before;	
				}
				r.linkCost = r.linkCost - q.linkCost + 1;
			}
			if (++i == height) extendTopLayer();
		}
		
		Node r = q.after;
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
		Node p = start;
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
		SkipList skp = new SkipList();
		
		File file = new File("input.txt");
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			skp.skipInsert(input.nextInt());
			//skp.skipPrint();
			//System.out.println();
		}
		input.close();
		
		skp.skipPrint();
		System.out.println(skp.indexOf(54));
	}
}
