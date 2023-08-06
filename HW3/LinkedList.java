package HW3;
public class LinkedList<T extends Comparable<T>> {
    private Node root;
    private int size;

    public void add(T value) {
        if (root == null) {
            root = new Node(value);
            size = 1;
            return;
        }
        Node currentNode = root;
        while (currentNode.next != null)
            currentNode = currentNode.next;
        currentNode.next = new Node(value);
        size++;
    }
    
    public boolean revert() {
        if(root == null || size == 1)
            return false;
        int beginIndex = 0;
        int endIndex = size - 1;
        while (beginIndex < endIndex) {
            swap(beginIndex, endIndex);
            beginIndex++;
            endIndex--;    
        }
        return true;  
    }

    public void addAt(int index, T value) {
        if (index == 0) {
            Node newNode = new Node(value);
            newNode.next = root;
            root = newNode;
            size++;
            return;
        }
        Node prev = getNode(index - 1);
        Node newNode = new Node(value);
        newNode.next = prev.next;
        prev.next = newNode;
        size++;
    }

    public void addSorted(T value) {
        if (root == null) {
            root = new Node(value);
            size++;
            return;
        }
        if (root.value.compareTo(value) >= 0) {
            Node newNode = new Node(value);
            newNode.next = root;
            root = newNode;
            size++;
            return;
        }
        Node currentNode = root;
        while (currentNode.next != null) {
            if (currentNode.next.value.compareTo(value) >= 0) {
                Node newNode = new Node(value);
                newNode.next = currentNode.next;
                currentNode.next = newNode;
                size++;
                return;
            }
            currentNode = currentNode.next;
        }
        currentNode.next = new Node(value);
        size++;
    }

    public boolean remove(T value) {
        if (root == null) return false;
        if (root.value.compareTo(value) == 0) {
            root = root.next;
            size--;
            return true;
        }
        Node currentNode = root;
        while (currentNode.next != null) {
            if (currentNode.next.value.compareTo(value) == 0) {
                currentNode.next = currentNode.next.next;
                size--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    public void removeAt(int index) {
        if (index == 0) {
            root.next = root.next.next;
            size--;
            return;
        }
        Node prevNode = this.getNode(index - 1);
        prevNode.next = prevNode.next.next;
        size--;
    }

    public int removeAll(T value) {
        int oldSize = size;
        if (root == null) return 0;
        while (root != null && root.value.compareTo(value) == 0) {
            root = root.next;
            size--;
        }
        Node currentNode = root;
        while (currentNode.next != null) {
            if (currentNode.next.value.compareTo(value) == 0) {
                currentNode.next = currentNode.next.next;
                size--;
            } else currentNode = currentNode.next;
        }
        return oldSize - size;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node currentNode = root;
        for (int i = 0; i < index; i++)
            currentNode = currentNode.next;
        return currentNode;
    }

    public T getValue(int index) {
        return this.getNode(index).value;
    }

    public void setValue(int index, T value) {
        getNode(index).value = value;
    }

    public void swap(int index1, int index2) {
        if (index1 == index2) return;
        if (index1 < 0 || index1 >= size || index2 < 0 || index2 >= size) return;
        Node currentNode = root;
        Node node1 = null;
        Node node2 = null;
        for (int i = 0; currentNode != null; i++) {
            if (i == index1) node1 = currentNode;
            else if (i == index2) node2 = currentNode;
            if (node2 != null && node1 != null) break;
            currentNode = currentNode.next;
        }
        T temp = node1.value;
        node1.value = node2.value;
        node2.value = temp;
    }

    public void quickSort() {
        quickSort(0, size - 1);
    }

    private void quickSort(int leftBorder, int rightBorder) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        T pivot = getValue((leftMarker + rightMarker) / 2);
        while (leftMarker <= rightMarker) {
            while (getValue(leftMarker).compareTo(pivot) < 0) leftMarker++;
            while (getValue(rightMarker).compareTo(pivot) > 0) rightMarker--;
            if (leftMarker <= rightMarker) {
                swap(leftMarker, rightMarker);
                leftMarker++;
                rightMarker--;
            }
        }
        if (leftMarker < rightBorder) quickSort(leftMarker, rightBorder);
        if (leftBorder < rightMarker) quickSort(leftBorder, rightMarker);
    }

    public void print() {
        Node currentNode = root;
        System.out.print("[ ");
        while (currentNode != null) {
            System.out.print(currentNode.value + " ");
            currentNode = currentNode.next;
        }
        System.out.println("]  size: " + size);
    }

    private class Node {
        T value;
        Node next;

        Node() {
        }

        Node(T value) {
            this.value = value;
        }
    }
}

