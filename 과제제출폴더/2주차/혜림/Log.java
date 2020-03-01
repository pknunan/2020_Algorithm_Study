package pa_2;

import java.util.Comparator;

public class Log  {

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

	public static Comparator<Log> Comparator_time=new Comparator<Log>() {
		public int compare(Log l1, Log l2) {
			if(l1.ymd.year>l2.ymd.year)
				return 1;
			else if(l1.ymd.year==l2.ymd.year) {
				if(l1.ymd.monthRank(l1.ymd.month)>l2.ymd.monthRank(l2.ymd.month))
					return 1;
				else if(l1.ymd.monthRank(l1.ymd.month)==l2.ymd.monthRank(l2.ymd.month)) {
					if(l1.ymd.day>l2.ymd.day)
						return 1;
					else if(l1.ymd.day==l2.ymd.day) {
						if(l1.hms.hour>l2.hms.hour)
							return 1;
						else if(l1.hms.hour==l2.hms.hour) {
							if(l1.hms.minute>l2.hms.minute)
								return 1;
							else if(l1.hms.minute==l2.hms.minute) {
								if(l1.hms.second>l2.hms.second)
									return 1;
								else if(l1.hms.second==l2.hms.second)
									return 0;
							}
						}
					}
				}
			}
			return -1;
		}
	};

	public static Comparator<Log> Comparator_ip=new Comparator<Log>() {
		public int compare(Log l1, Log l2) {
			return l1.compareToIP(l1, l2);
		}
	};

	public int compareToIP(Log l1, Log l2) {
		if(l1.IP.compareTo(l2.IP)>0)
			return 1;
		else if(l1.IP.compareTo(l2.IP)==0)
			return 0;
		return -1;
	}

	public String toString() {
		return "IP: "+IP+" URL: "+URL+" Status: "+Status+" Date: "+ymd.toString()+hms.toString();
	}
}
