package algorithm_2019.Pa_8_1;

import java.io.*;
import java.util.*;

public class AreaFile {

    private HashMap<String, Area> AreaMap = new HashMap<>();

    public AreaFile() {
        init();
    }

    private void init() {
        try {
            addToMap();
            connectEdges();
        } catch (FileNotFoundException | CloneNotSupportedException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully connect all edges.");
    }

    private void addToMap() throws FileNotFoundException {
        String path = "algorithm/src/algorithm_2019/resource/" + "alabama.txt";
        File file = new File(path);

        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            String[] line = fileScanner.nextLine().split("\t");
            AreaMap.put(line[0], new Area(line[0], Double.parseDouble(line[1]), Double.parseDouble(line[2])));
        }
    }

    private void connectEdges() throws CloneNotSupportedException, FileNotFoundException {
        String path = "algorithm/src/algorithm_2019/resource/" + "roadList2.txt";
        File file = new File(path);

        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            String[] line = fileScanner.nextLine().split("\t");
            connect(AreaMap.get(line[0]), (Area) AreaMap.get(line[1]).clone());
            connect(AreaMap.get(line[1]), (Area) AreaMap.get(line[0]).clone());
        }
    }

    private void connect(Area header, Area target) {
        target.next = header.next;
        header.next = target;
        target.superNode = header;
        target.weigh = calDistance(header.latitude, header.longitude, target.latitude, target.longitude);
    }

    private double calDistance(double lat1, double lon1, double lat2, double lon2) {
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

    private double deg2rad(double deg) {
        return deg * Math.PI / (double) 180;
    }

    private double rad2deg(double rad) {
        return rad * (double) 180 / Math.PI;
    }

    public void closeOf(String areaName) {
        if (!AreaMap.containsKey(areaName)) {
            System.out.println("Type as \"$ [command] [area name]\"");
            return;
        }

        HashMap<String, Boolean> hashChk = new HashMap<>();
        Queue<Area> q = new LinkedList<>();
        q.offer(AreaMap.get(areaName));
        hashChk.put(areaName, true);

        int t = 0;
        while (!q.isEmpty() && t <= 10) {
            int size = q.size();
            System.out.println("Hop " + t + ":");
            for (int i = 0; i < size; i++) {
                Area target = q.poll();
                System.out.println(target);
                offerAllAdjacentLocationOf(q, target, hashChk);
            }
            t++;
        }
    }

    private void offerAllAdjacentLocationOf(Queue<Area> q, Area target, HashMap<String, Boolean> hashChk) {
        while (target != null) {
            if (!hashChk.containsKey(target.name)) {
                q.offer(AreaMap.get(target.name));
                hashChk.put(target.name, true);
            }
            target = target.next;
        }
    }

    public void traversalOf(String areaName) {
        if (!AreaMap.containsKey(areaName)) {
            System.out.println("Type as \"$ [command] [area name]\"");
            return;
        }
        HashMap<String, Boolean> hashChk = new HashMap<>();
        System.out.println("Graph traversal:");
        DFS(AreaMap.get(areaName), hashChk);
    }

    private void DFS(Area start, HashMap<String, Boolean> hashChk) {
        System.out.println(start);
        hashChk.put(start.name, true);
        while (start != null) {
            if (!hashChk.containsKey(start.name)) {
                DFS(AreaMap.get(start.name), hashChk);
            }
            start = start.next;
        }
    }

    private void print() throws IOException {
        File file = new File("algorithm/src/algorithm_2019/resource/mst.txt");
        if(!file.exists())
            file.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        ArrayList<Area> list = new ArrayList<>(AreaMap.values());
        list.sort((o1, o2) -> o1.number - o2.number);

        for (int i = 0; i < list.size(); i++) {
            Area area = list.get(i);
            bw.write(area.number + " " + area.longitude + " " + area.latitude + " ");
            Area curr = area.next;
            String temp = "";
            int degree = 0;
            while (curr != null) {
                if (curr.isMSTNode) {
                    temp = temp + " " + curr.number;
                    degree++;
                }
                curr = curr.next;
            }
            bw.write(degree + temp);
            bw.newLine();
            bw.flush();
        }
    }

    public void mst() {
        ArrayList<Area> list = new ArrayList<>();
        // add all edges to list
        addAllEdgesToList(list);
        // sort by weight
        list.sort((o1, o2) -> Double.compare(o1.weigh, o2.weigh));
        // make set
        Node[] set = new Node[Area.size];
        for (int i = 0; i < set.length; i++)
            set[i] = new Node(i);
        // wupc
        for (Area area : list) {
            if (!findSetWithPc(area.number, set).equals(findSetWithPc(area.superNode.number, set))) {
                weightedUnion(area.number, area.superNode.number, set);
                area.isMSTNode = true;
                Area otherNode = AreaMap.get(area.name);
                while (!otherNode.name.equals(area.superNode.name))
                    otherNode = otherNode.next;
                otherNode.isMSTNode = true;
            }
        }
        // print to text file

        try {
            print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void weightedUnion(int n1, int n2, Node[] set) {
        Node x = findSetWithPc(n1, set);
        Node y = findSetWithPc(n2, set);
        if (x.size > y.size) {
            set[y.number].number = x.number;
            x.size += y.size;
        } else {
            set[x.number].number = y.number;
            y.size += x.size;
        }
    }

    private Node findSetWithPc(int number, Node[] set) {
        while (number != set[number].number) {
            set[number].number = set[set[number].number].number;
            number = set[number].number;
        }
        return set[number];
    }

    private void addAllEdgesToList(ArrayList<Area> list) {
        for (Area area : AreaMap.values()) {
            Area curr = area.next;
            while (curr != null) {
                list.add(curr);
                curr = curr.next;
            }
        }
    }
}
