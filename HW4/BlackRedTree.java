package HW4;

import java.util.ArrayList;
import java.util.List;

public class BlackRedTree<T extends Comparable<T>> {
    Node root;

    public boolean add(T value) {
        if (root == null) {
            root = new Node(value);
            root.color = Color.black;
            return true;
        }
        return addNode(root, value) != null;
    }

    private Node addNode(Node node, T value) {

        if (node == null) {
            return null;
        }
        if (node.value.compareTo(value) == 0) {
            return null;
        }
        if (node.value.compareTo(value) > 0) {
            if (node.leftChild == null) {
                node.leftChild = new Node(value);
                return node.leftChild;
            }
            Node newNode = addNode(node.leftChild, value);
            node.leftChild = rebalance(node.leftChild);
            return newNode;
        }
        if (node.value.compareTo(value) < 0) {
            if (node.rightChild == null) {
                node.rightChild = new Node(value);
                return node.rightChild;
            }
        }
        Node newNode = addNode(node.rightChild, value);
        node.rightChild = rebalance(node.rightChild);
        return newNode;

    }

    private Node leftTurn(Node node) {
        Node left = node.leftChild;
        Node between = left.rightChild;
        left.color = node.color;
        left.rightChild = node;
        node.color = Color.red;
        node.leftChild = between;
        return left;
    }

    private Node rightTurn(Node node) {
        Node right = node.rightChild;
        Node between = right.leftChild;
        right.color = node.color;
        right.leftChild = node;
        node.color = Color.red;
        node.rightChild = between;
        return right;
    }

    private void colorTurn(Node node) {
        node.rightChild.color = Color.black;
        node.leftChild.color = Color.black;
        node.color = Color.red;

    }

    private Node rebalance(Node node) {
        Node head = node;
        boolean needRebalance = true;
        while (needRebalance) {
            needRebalance = false;
            if (head.rightChild != null && head.rightChild.color == Color.red
                    && (head.leftChild == null || head.leftChild.color == Color.black)) {
                head = rightTurn(node);
                needRebalance = true;
            }
            if (head.leftChild != null && head.leftChild.leftChild != null && head.leftChild.color == Color.red
                    && head.leftChild.leftChild.color == Color.red) {
                head = leftTurn(node);
                needRebalance = true;
            }
            if (head.leftChild != null && head.rightChild != null && head.leftChild.color == Color.red
                    && head.rightChild.color == Color.red) {
                colorTurn(head);
                needRebalance = true;
            }
        }
        return head;
    }

    private class Node {
        T value;
        Node leftChild;
        Node rightChild;
        Color color;

        Node(T value) {
            this.value = value;
            this.color = Color.red;
        }

    }

    enum Color {
        black, red
    }

    private class PrintNode {
        Node node;
        String str;
        int depth;

        public PrintNode() {
            node = null;
            str = " ";
            depth = 0;
        }

        public PrintNode(Node node) {
            depth = 0;
            this.node = node;
            this.str = node.value.toString();
        }
    }

    private void printLines(List<List<PrintNode>> list, int i, int j, int i2, int j2) {
        if (i2 > i) // РРґС‘Рј РІРЅРёР·
        {
            while (i < i2) {
                i++;
                list.get(i).get(j).str = "|";
            }
            list.get(i).get(j).str = "\\";
            while (j < j2) {
                j++;
                list.get(i).get(j).str = "-";
            }
        } else {
            while (i > i2) {
                i--;
                list.get(i).get(j).str = "|";
            }
            list.get(i).get(j).str = "/";
            while (j < j2) {
                j++;
                list.get(i).get(j).str = "-";
            }
        }
    }

    public int maxDepth() {
        return maxDepth2(0, root);
    }

    private int maxDepth2(int depth, Node node) {
        depth++;
        int left = depth;
        int right = depth;
        if (node.leftChild != null)
            left = maxDepth2(depth, node.leftChild);
        if (node.rightChild != null)
            right = maxDepth2(depth, node.rightChild);
        return left > right ? left : right;
    }

    private int nodeCount(Node node, int count) {
        if (node != null) {
            count++;
            return count + nodeCount(node.leftChild, 0) + nodeCount(node.rightChild, 0);
        }
        return count;
    }

    public void print() {

        int maxDepth = maxDepth() + 3;
        int nodeCount = nodeCount(root, 0);
        int width = 50;//maxDepth * 4 + 2;
        int height = nodeCount * 5;
        List<List<PrintNode>> list = new ArrayList<List<PrintNode>>();
        for (int i = 0; i < height; i++) /*РЎРѕР·РґР°РЅРёРµ СЏС‡РµРµРє РјР°СЃСЃРёРІР°*/ {
            ArrayList<PrintNode> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new PrintNode());
            }
            list.add(row);
        }

        list.get(height / 2).set(0, new PrintNode(root));
        list.get(height / 2).get(0).depth = 0;

        for (int j = 0; j < width; j++)  /*РџСЂРёРЅС†РёРї Р·Р°РїРѕР»РЅРµРЅРёСЏ*/ {
            for (int i = 0; i < height; i++) {
                PrintNode currentNode = list.get(i).get(j);
                if (currentNode.node != null) {
                    currentNode.str = currentNode.node.value.toString();
                    if (currentNode.node.leftChild != null) {
                        int in = i + (maxDepth / (int) Math.pow(2, currentNode.depth));
                        int jn = j + 3;
                        printLines(list, i, j, in, jn);
                        list.get(in).get(jn).node = currentNode.node.leftChild;
                        list.get(in).get(jn).depth = list.get(i).get(j).depth + 1;

                    }
                    if (currentNode.node.rightChild != null) {
                        int in = i - (maxDepth / (int) Math.pow(2, currentNode.depth));
                        int jn = j + 3;
                        printLines(list, i, j, in, jn);
                        list.get(in).get(jn).node = currentNode.node.rightChild;
                        list.get(in).get(jn).depth = list.get(i).get(j).depth + 1;
                    }

                }
            }
        }
        for (int i = 0; i < height; i++) /*Р§РёСЃС‚РєР° РїСѓСЃС‚С‹С… СЃС‚СЂРѕРє*/ {
            boolean flag = true;
            for (int j = 0; j < width; j++) {
                if (list.get(i).get(j).str != " ") {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                list.remove(i);
                i--;
                height--;
            }
        }

        for (var row : list) {
            for (var item : row) {
                if (item.node != null) {
                    if (item.node.color == Color.red)
                        System.out.print("\u001B[31m" + item.str + " " + "\u001B[0m");
                    else
                        System.out.print("\u001B[30m" + item.str + " " + "\u001B[0m");
                } else
                    System.out.print(item.str + " ");
            }
            System.out.println();
        }
    }    
}
