package pa_2;

public class Log {

	public String IP;
	public Date ymd;
	public Times hms;
	public String URL;
	public String Status;

	public Log(String IP, String Time, String URL, String Status) {

		String []tokens=Time.split(":");
		this.IP=IP;
		ymd=new Date(tokens[0].substring(1));
		hms=new Times(tokens[1], tokens[2], tokens[3]);
		this.URL=URL;
		this.Status=Status;
	}

	
	/*public static Comparator<Log> Comparator_1=new Comparator<Log>() {
		public int compare(Log l1, Log l2) {
			return l1.IP.compareTo(l2.IP);
		}
	};
	
	public static Comparator<Log> Comparator_2=new Comparator<Log>() {
		public int compare(Log l1, Log l2) {
			return l1.ymd.compareTo(l2.ymd);
		}
	};
	*/
	public int compareToIP() {

		char s='.';
		String tmp=IP;
		for(int i=0;i<IP.length();i++) {
			
			if(IP.charAt(i)==s) {
				IP=squuze(IP, s);
			}
			
		}
		int ip=Integer.parseInt(IP);
		IP=tmp;
		return ip;
	}

	static String squuze(String from, char ch) {
		return from.replaceFirst(String.format("[%c]", ch),"");
	}

	public int compareToTime(Log l) {

		if(ymd.compareTo(l.ymd)==1)
			return 1;
		else if(ymd.compareTo(l.ymd)==0)
			return 0;
		return -1;
	}

	public String toString() {
		return "IP: "+IP+" URL: "+URL+" Status: "+Status+" Date: "+ymd.toString()+hms.toString();
	}
}
