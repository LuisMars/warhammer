package es.luismars;

import java.util.List;

/**
 * Created by Luis on 11/09/2014.
 */
public abstract class Stats {

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

    public static final int SHOTS = 11;
    public static final int RANGE = 12;
    public static final int STR = 13;
    public static final int APW = 14;

    public CCWeapon ccw;
    public RangedWeapon rw;

    private int[] baseStats = new int[15];
    private int[] stats = new int[15];
    private int cost;

    public Stats() {
    }

    public Stats(int[] s, CCWeapon cc, RangedWeapon r) {
        stats = baseStats = s;
        ccw = cc;
        rw = r;
        ccw.updateStats(this);
        rw.updateStats(this);

    }

    public double shooting(Stats s) {
        double wounds = 0;
        for (int i = 1; i < get(Stats.SHOTS); i++) {
            wounds += shoot(s, i) * woundShooting(s, i) * saveShooting(s, i);
        }
        return wounds;
    }

    public double combat(Stats s) {
        double wounds = 0;
        for (int i = 1; i <= get(A); i++) {
            wounds += hit(s, i) * wound(s, i) * save(s, i);
            //if (get(S) >= s.get(T) && s.get(W) > 1) //TODO: instant death
        }
        return wounds;
    }

    private double shoot(Stats s, int i) {
        double hits = Rules.shootRoll(get(Stats.BS));

        if (rw.spr.rerollHit)
            hits = Rules.reroll(hits);
        else if (rw.spr.rerollOneHit)
            hits = Rules.reroll1ofN(hits, i);

        return hits;
    }

    private double woundShooting(Stats s, int i) {
        double wounds = Rules.woundRoll(get(STR), s.get(T));

        if (rw.spr.rerollWounds)
            wounds = Rules.reroll(wounds);

        else if (rw.spr.rerollOneWound)
            wounds = Rules.reroll1ofN(wounds, i);


        return wounds;

    }

    private double saveShooting(Stats s, int i) {
        double wounds;
        if (get(APW) > s.get(AS))
            wounds = Rules.armorSave(s.get(AS));
        else
            wounds = Rules.specialSave(s.get(SS));
        return wounds;
    }

    private double hit(Stats s, int i) {

        double hits = Rules.hitRoll(get(WS), s.get(WS));

        if (ccw.spr.rerollHit)
            hits = Rules.reroll(hits);

        else if (ccw.spr.rerollOneHit)
            hits = Rules.reroll1ofN(hits, i);


        return hits;
    }

    private double wound(Stats s, int i) {

        double wounds = Rules.woundRoll(get(S), s.get(T));

        if (ccw.spr.rerollWounds)
            wounds = Rules.reroll(wounds);

        else if (ccw.spr.rerollOneWound)
            wounds = Rules.reroll1ofN(wounds, i);


        return wounds;

    }

    private double save(Stats s, int i) {
        double wounds;
        if (get(AP) > s.get(AS))
            wounds = Rules.armorSave(s.get(AS));
        else
            wounds = Rules.specialSave(s.get(SS));
        return wounds;
    }

    public void setAll(Stats s) {
        stats = s.stats;
    }
    
    public void set(int s, int c) {
    	stats[s] = c;
    }

    public void add(int s, int c) {
        stats[s] += c;
    }

    public void mult(int s, int c) {
        stats[s] *= c;
        stats[s] = Math.min(stats[s], 10);
    }
    
    public int get(int s) {
    	return stats[s];
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int c) {
        cost = c;
    }

}
