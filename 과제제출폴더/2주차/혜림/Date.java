package pa_2;

public class Date {

	public int day;
	public String month;
	public int year;
	public Times time;

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

	public int compareTo(Date other) {

		if(year>other.year)
			return 1;
		else if(year==other.year) {
			if(monthRank(month)>monthRank(other.month))
				return 1;
			else if(monthRank(month)==monthRank(other.month)) {
				if(day>other.day)
					return 1;
				else if(day==other.day) {
					if(time.hour>other.time.hour)
						return 1;
					else if(time.hour==other.time.hour) {
						if(time.minute>other.time.minute)
							return 1;
						else if(time.minute==other.time.minute) {
							if(time.second>other.time.second)
								return 1;
							else if(time.second==other.time.second)
								return 0;
						}
					}
	
				}
			}
		}
		return -1;
	}

	public String toString() {
		return year+"/"+month+"/"+day+"/";
	}
}
