package algorithm_2019.Pa_7_1;

import java.util.Scanner;

public class Pa_7_1 {
    public static void main(String[] args) {
        AreaFile alabama = new AreaFile();
        Scanner sc = new Scanner(System.in);
        String command;

        label:
        while(true) {
            System.out.print("$ ");
            command = sc.next();

            switch (command) {
                case "closeAreasOf":
                    alabama.closeAreaOf(sc.next());
                    break;
                case "traversalOf":
                    alabama.traversalOf(sc.next());
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
