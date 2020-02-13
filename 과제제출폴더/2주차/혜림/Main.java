package pa_2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class Main {

	static int n=0;
	public static int capacity = 100000;
	static Log[] log=new Log[capacity];

	Scanner kb=new Scanner(System.in);

	public void processCommand() {

		while(true) {

			System.out.print("$ ");
			String command=kb.next();

			if(command.equals("read"))
				readCSV();
			else if(command.equals("sort"))
				sort();
			else if(command.equals("print"))
				print();
			else if(command.equals("exit"))
				break;
		}

	}
	public void readCSV() {

		String read_command=kb.next();
		if(read_command.equals("webLog.csv")) {

			BufferedReader br = null;
			String line;
			String path = "C:/Users/lacuc/Desktop/webLog.csv";
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

				String test =br.readLine();
				while((line = br.readLine()) != null) {

					String[] temp = line.split(",");
					log[n]=new Log(temp[0], temp[1], temp[2], temp[3]);

					n++;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		else
			System.out.println("The file isn't exist.");

	}
	private void print() {

		for(int i=0;i<n;i++)
			System.out.println(log[i].toString());

	}
	public void sort() {

		String sort_command=kb.next();
		if(sort_command.equals("-t")) {

			for(int i=n-1;i>0;i--) {
				for(int j=0;j<i;j++) {
					if((log[j].compareToTime(log[j+1]))==-1) {
						Log tmp=log[j];
						log[j]=log[j+1];
						log[j+1]=tmp;
					}
				}
			}
			
			for(int i=0;i<n;i++)
				System.out.println(log[i].toString());

		}
		else if(sort_command.equals("-ip")) {
		
			for(int i=n-1;i>0;i--) {
				for(int j=0;j<i;j++) {
					
					if(log[j].compareToIP()<log[j+1].compareToIP()){
						Log tmp=log[j];
						log[j]=log[j+1];
						log[j+1]=tmp;
					}
				}
			}
		}
		for(int i=0;i<n;i++)
			System.out.println(log[i].toString());

	}

	public static void main(String[] args) {

		Main app=new Main();
		app.processCommand();
	}

}
