package es.luismars;
/**
 * Created by Luis on 11/09/2014.
 */
public class Stats {


	public static final int WS = 0;
	public static final int BS = 1;
	public static final int S = 2;
	public static final int T = 3;
	public static final int W = 4;
	public static final int I = 5;
	public static final int A = 6;
	public static final int L = 7;
	public static final int AS = 8;
	public static final int SS = 9;
	public static final int AP = 10;
	public static final int WD = 11;

    int[] stats = new int[12];
    
    public Stats (int[] s) {
    	stats = s;
    }

    
    public void addStats(Stats s) {
    	for (int i = 0; i <= stats.length; i++) {
    		if(stats[i] >= 0) 
    			s.stats[i] = stats[i];
    		else
    			stats[i] *= 2;
    		s.stats[i] = Math.min(s.stats[i], 10);
    	}
    }
    
    public void set(int s, int c) {
    	stats[s] = c;
    }
    
    public int get(int s) {
    	return stats[s];
    }
}
