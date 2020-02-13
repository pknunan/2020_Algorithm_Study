package pa_2;

public class Times {
	
	public int hour;
	public int minute;
	public int second;
	
	
	public Times(int h, int m, int s) {
		hour=h;
		minute=m;
		second=s;
	}
	
	public Times(String x, String y, String z) {
		
		hour=Integer.parseInt(x);
		minute=Integer.parseInt(y);
		second=Integer.parseInt(z);
		
	}
	
	public int compareTo(Times other) {
		
		if(hour>other.hour)
			return 1;
		else if(hour==other.hour) {
			if(minute>other.minute)
				return 1;
			else if(minute==other.minute) {
				if(second>other.second)
					return 1;
				else if(second<other.second)
					return -1;
				return 0;
			}
			return -1;
		}
		return -1;

	}
	
	@Override
	public String toString() {
		return hour+"½Ã"+minute+"ºÐ"+second+"ÃÊ";
	}
}
