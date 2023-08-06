package HW3;

public class App {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(3);
        linkedList.add(2);
        linkedList.add(10);
        linkedList.add(6);
        linkedList.add(23);

        linkedList.print();
        boolean isRevert = linkedList.revert();
        linkedList.print();
        System.out.println(isRevert);

    }
}
