package algorithm_2019.Pa_8_1;

public class Area implements Cloneable{
    static int size = 0;

    int number;
    String name;
    double longitude;
    double latitude;
    double weigh;
    Area next;
    boolean isMSTNode;
    Area superNode;

    public Area(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        number = size++;
    }

    @Override
    public String toString() {
        return "\t{" +
                "number="+number+
                ", area='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
