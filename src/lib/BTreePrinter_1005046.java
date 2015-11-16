package lib;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import splaytree.Node_1005046;
import splaytree.SplayTree_1005046;
public class BTreePrinter_1005046 {

    public static void printNode(Node_1005046 root) {
        int maxLevel = BTreePrinter_1005046.maxLevel(root);
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<Node_1005046> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter_1005046.isAllElementsNull(nodes))
            return;
        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter_1005046.printWhitespaces(firstSpaces);

        List<Node_1005046> newNodes = new ArrayList<Node_1005046>();
        for (Node_1005046 node : nodes) {
            if (node != null) {
                System.out.print(node.data);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter_1005046.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter_1005046.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter_1005046.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BTreePrinter_1005046.printWhitespaces(1);

                BTreePrinter_1005046.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    BTreePrinter_1005046.printWhitespaces(1);

                BTreePrinter_1005046.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static int maxLevel(Node_1005046 node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter_1005046.maxLevel(node.left), BTreePrinter_1005046.maxLevel(node.right)) + 1;
    }

    private static boolean isAllElementsNull(List list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}