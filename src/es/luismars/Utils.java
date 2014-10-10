package es.luismars;

/**
 * Created by Luis on 09/09/2014.
 */
public class Utils {
    public static String toBinStr(int n, int s) {
        String N = Integer.toBinaryString(n);
        while (N.length() < s)
            N = "0" + N;
        return N;
    }

    public static int read(String ID, int ini, int end) {
        return Integer.parseInt(ID.substring(ini, end), 2);
    }

    public static String time(long millis) {

        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;

        return String.format("%02dh %02dm %02ds", hour, minute, second);
    }
}
