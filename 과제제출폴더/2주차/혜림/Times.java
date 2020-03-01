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
	
	@Override
	public String toString() {
		return hour+"½Ã"+minute+"ºĞ"+second+"ÃÊ";
	}
}
