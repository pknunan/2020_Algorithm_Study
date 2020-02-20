package algorithm_2019.Pa_7_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class AreaFile {

    private HashMap<String, Area> AreaList = new HashMap<>();

    public AreaFile() {
        init();
    }

    private void init() {
        String path = "algorithm/src/algorithm_2019/resource/" + "alabama.txt";
        File file = new File(path);

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fileScanner != null) {
            while (fileScanner.hasNextLine()) {
                String[] line = fileScanner.nextLine().split("\t");
                AreaList.put(line[0], new Area(line[0], Double.parseDouble(line[1]), Double.parseDouble(line[2])));
            }

            try {
                connectEdges();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            System.out.println("Successfully connect all edges.");
        }
    }

    private void connectEdges() throws CloneNotSupportedException {
        String path = "algorithm/src/algorithm_2019/resource/" + "roadList2.txt";
        File file = new File(path);

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fileScanner != null) {
            while (fileScanner.hasNextLine()) {
                String[] line = fileScanner.nextLine().split("\t");
                connect(AreaList.get(line[0]), (Area) AreaList.get(line[1]).clone());
                connect(AreaList.get(line[1]), (Area) AreaList.get(line[0]).clone());
            }
        }
    }

    private void connect(Area header, Area target) {
        target.next = header.next;
        header.next = target;
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

    public void closeAreaOf(String areaName) {
        if (!AreaList.containsKey(areaName)) {
            System.out.println("Wrong Area");
            return;
        }

        HashMap<String, Boolean> hashChk = new HashMap<>();
        Queue<Area> q = new LinkedList<>();
        q.offer(AreaList.get(areaName));
        hashChk.put(areaName, true);

        int t = 0;
        while (!q.isEmpty() && t <= 10) {
            int size = q.size();
            System.out.println("Hop "+t+":");
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
            if(!hashChk.containsKey(target.name)) {
                q.offer(AreaList.get(target.name));
                hashChk.put(target.name, true);
            }
            target = target.next;
        }
    }

    public void traversalOf(String areaName) {
        if (!AreaList.containsKey(areaName)) {
            System.out.println("Wrong Area");
            return;
        }
        HashMap<String, Boolean> hashChk = new HashMap<>();
        System.out.println("Graph traversal:");
        DFS(AreaList.get(areaName), hashChk);
    }

    private void DFS(Area start, HashMap<String, Boolean> hashChk) {
        System.out.println(start);
        hashChk.put(start.name, true);
        while(start != null) {
            if(!hashChk.containsKey(start.name)) {
                DFS(AreaList.get(start.name), hashChk);
            }
            start = start.next;
        }
    }

}
