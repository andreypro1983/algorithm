package HW4;

public class App {
    public static void main(String[] args) {
        BlackRedTree<Integer> tree = new BlackRedTree<>();
        tree.add(5);
        tree.add(3);
        tree.add(8);
        tree.add(6);
        tree.add(4);
        tree.add(2);
        tree.print();
    }
    
}
