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

}
