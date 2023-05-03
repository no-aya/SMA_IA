package ma.enset.sma;

public class Client {
    public static int count=0;
    String name;
    public Client() {
        System.out.println("***Creating client*** ");
        System.out.println("Client count : "+count);
        count++;
        name="client"+count;
    }
}
