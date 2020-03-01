package Graph.PA07;
import java.io.*;
import java.nio.Buffer;
import java.util.*;

class Spot {
    int index;
    String name;
    double lat;
    double lon;
    LinkedList<Integer> list;
    boolean visited = false;
    int hop = 0;
    public Spot(int index, String name, double lat, double lon) {

        this.index = index;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        list = new LinkedList<Integer>();
    }

    @Override
    public String toString() {
        return "Spot{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ",hop="+hop+
                "}\n";
    }

    public Spot() {

    }

}

public class Main {

    static int n = 0;
    static ArrayList<Spot> Spots = new ArrayList<Spot>();

    public static void main(String[] args) {

        Main app = new Main();
        try {
            app.process_command();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void process_command() throws IOException {
        readFile();
        process_Input();


    }

    private void process_Input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a or b");
        String in = br.readLine();
        if(in.equals("a")){
            System.out.println("Input name of spot");
            BFS(br.readLine());
        }
        else if(in.equals("b")){
            System.out.println("Input name of spot");
            int t = findIndex(br.readLine());
            DFS(t);
        }
    }


    private void readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("alabama.txt"));
        while (true) {
            String input = br.readLine();
            if (input == null) break;
            String[] strings = input.split("\t");
            Spots.add(new Spot(n++, strings[0].trim(), Double.parseDouble(strings[1]), Double.parseDouble(strings[2]))); //장소정보저장
        }
        br = new BufferedReader(new FileReader("roadList2.txt"));

        while (true) {
            String input = br.readLine();
            if (input == null) break;

            String[] strings = input.split("\t");
            Spot s1 = new Spot();
            Spot s2 = new Spot();
            for (Spot s : Spots) {
                if (s.name.equals(strings[0].trim())) {
                    s1 = s;
                }
                if (s.name.equals(strings[1].trim())) {
                    s2 = s;
                }
            }

            if (s1.index >= 0 && s2.index >= 0) {
                s1.list.add(s2.index);
                s2.list.add(s1.index);

            }
        }

    }
    public int findIndex(String target){
        for(Spot s:Spots){
            if(target.equals(s.name)){
               return s.index;
            }
        }
        return -1;
    }
    public void BFS(String target) {
        Queue<Integer> q = new LinkedList<Integer>();
        q.add( findIndex(target) );
        while(!q.isEmpty())
        {
            int u = q.poll();
            for(int i:Spots.get(u).list){
                if(!Spots.get(i).visited){
                    Spots.get(i).visited = true;
                    Spots.get(i).hop = Spots.get(u).hop+1;
                    if(Spots.get(i).hop>10){
                        return;
                    }
                    System.out.println(Spots.get(i));
                    q.add(i);
                }
            }
        }
        System.out.println();

    }

    public void DFS(int target) {
        if(target == -1) {
            System.out.println("-1");
            return;
        }
        Spots.get(target).visited = true;
        System.out.println(Spots.get(target));
        for(int i:Spots.get(target).list){
            if(!Spots.get(i).visited)
            {
                DFS(i);
            }
        }
    }
    public void DFS_ALL(){
        for(Spot s:Spots){
            if(!s.visited)
            {
                DFS(s.index);
            }
        }
    }
    private double deg2rad(double deg) {
        return (double) (deg * Math.PI / (double) 180);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private double rad2deg(double rad) {
        return (double) (rad * (double) 180 / Math.PI);
    }

    public double calDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344; // 단위 mile 에서 km 변환.
        dist = dist * 1000.0; // 단위 km 에서 m 로 변환
        return dist;
    }
}
