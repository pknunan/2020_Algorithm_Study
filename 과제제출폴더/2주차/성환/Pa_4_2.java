package algorithm_2019;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Pa_4_2 {

    static class WebLog {
        String IP;
        String Time;
        String URL;
        String Status;

        static String sortedBy = "default";

        WebLog(String[] log) {
            IP = log[0];
            Time = log[1].substring(1);
            URL = log[2];
            Status = log[3];
        }

        @Override
        public String toString() {
            if(sortedBy.equals("IP"))
                return IP+"\n\tTime: "+Time+"\n\tURL: "+URL+"\n\tStatus: "+Status+"\n";
            else if(sortedBy.equals("Time"))
                return Time + "\n\tIP: " + IP + "\n\tURL: " + URL + "\n\tStatus: " + Status + "\n";
            return "invalid argument";
        }
    }

    static WebLog[] logs = new WebLog[1000];
    static int logSize = 0;

    static Comparator<WebLog> ipComparator =(o1, o2) -> {
        if(o1.IP.equals(o2.IP))
            return o1.Time.compareTo(o2.Time);
        return o1.IP.compareTo(o2.IP);
    };
    static Comparator<WebLog> timeComparator =(o1, o2) -> o1.Time.compareTo(o2.Time);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String command;

        while(true) {

            System.out.print("$ ");
            command = sc.next();
            if(command.equals("exit"))
                break;
            else if (command.equals("read")) {
                try {
                    readFile(sc.next());
                } catch (FileNotFoundException e) {
                    System.out.println("File is not Exist.");
                } catch (NoSuchElementException e) { }
            }
            else if (command.equals("print"))
                try {
                    printFile();
                } catch (NoSuchElementException e) {
                    System.out.println("There's no logs.");
                }
            else if (command.equals("sort"))
                try {
                    sortFile(sc.next());
                } catch (NoSuchElementException e) {
                    System.out.println("There's no logs.");
                }
        }
    }

    private static void sortFile(String next) throws NoSuchElementException {
        if(logs[0] == null)
            throw new NoSuchElementException();

        if(next.equals("-ip")) {
            Arrays.sort(logs, 0, logSize, ipComparator);
            WebLog.sortedBy = "IP";
        } else if (next.equals("-t")) {
            Arrays.sort(logs, 0, logSize, timeComparator);
            WebLog.sortedBy = "Time";
        }
        printFile();
    }

    private static void printFile() throws NoSuchElementException{
        if(logs[0] == null)
            throw new NoSuchElementException();
        System.out.println(toString(logs));
    }

    private static void readFile(String next) throws FileNotFoundException, NoSuchElementException {
        String path = "D:\\java\\practice\\algorithm\\src\\algorithm_2019\\resource\\" + next;
        File file = new File(path);
        Scanner fileScanner = new Scanner(file);
        fileScanner.nextLine();

        while(true) {
            String line = fileScanner.nextLine();
            String[] tmp = line.split(",");
            addLog(tmp);
        }
    }

    private static void addLog(String[] log) {
        if(logSize >= logs.length)
            reallocate();
        logs[logSize++] = new WebLog(log);
    }

    private static void reallocate() {
        WebLog[] tmp = new WebLog[logs.length*2];
        System.arraycopy(logs, 0, tmp, 0, logs.length);
        logs = tmp;
    }

    private static String toString(WebLog[] o) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < logSize; i++)
            sb.append(o[i].toString());
        return sb.toString();
    }
}
