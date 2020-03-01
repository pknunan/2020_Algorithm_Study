package pa_2;

public class Date {

	public int day;
	public String month;
	public int year;


	public Date(String token) {

		String []t;
		if(!token.equals("ime")) {

			t=token.split("/");
			day=Integer.parseInt(t[0]);
			month=t[1];
			year=Integer.parseInt(t[2]);
		}

	}

	public int monthRank(String month) {
		String [] months= {"Jan", "Feb", "Mar", "Apr", "May","Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

		for(int i=0;i<months.length;i++) {
			if(month.equals(months[i]))
				return i;
			else
				continue;
		}
		return -1;
	}


	public String toString() {
		return year+"/"+month+"/"+day+"/";
	}
}
