package algorithm_2019.Pa_8_1;

import java.util.Scanner;

public class Pa_8_1 {
    public static void main(String[] args) {
        AreaFile alabama = new AreaFile();
        Scanner sc = new Scanner(System.in);
        String command;

        label:
        while(true) {
            System.out.print("$ ");
            command = sc.next();

            switch (command) {
                case "closeOf":
                    alabama.closeOf(sc.nextLine().trim());
                    break;
                case "traversalOf":
                    alabama.traversalOf(sc.nextLine().trim());
                    break;
                case "print":
                    alabama.mst();
                    break;
                case "exit":
                    break label;
                default:
                    System.out.println("Wrong Command.");
                    break;
            }
        }
    }
}
