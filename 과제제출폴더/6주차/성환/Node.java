package algorithm_2019.Pa_8_1;

public class Node {
    int number;
    int size;

    public Node(int number) {
        this.number = number;
        size = 1;
    }

    public boolean equals(Node obj) {
        return number==obj.number;
    }
}
