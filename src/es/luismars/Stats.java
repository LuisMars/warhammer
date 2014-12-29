package es.luismars;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Luis on 11/09/2014.
 */
public class Stats extends SpecialRules implements Comparable {

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

    public static final int M = 15;

    public List<CCWeapon> ccw = new ArrayList<>();
    public List<RangedWeapon> rw = new ArrayList<>();
    public String ID = "";

    public int[] baseStats = new int[16];
    public int[] stats = new int[16];
    private int cost;

    public Stats(int[] s) {
        baseStats = s;
        stats = baseStats.clone();
    }

    public Stats(int[] s, CCWeapon cc, RangedWeapon r) {
        this(s, cc, r, null);
    }

    public Stats(int[] s, CCWeapon cc, RangedWeapon r, RangedWeapon r2) {
        baseStats = s;
        ccw.add(cc);
        rw.add(r);
        rw.add(r2);
        updateStats();
    }

    public double shoot(Squad def, int distance, boolean moved, boolean overwatch) {
        double wounds = 0;
        for (RangedWeapon r : rw) {
            if (r != null)
                wounds += r.shoot(this, def.stats, moved, distance, def.remainingMinis(), overwatch);
        }
        return wounds;
    }

    //TODO: extra weapons
    public double combat(Squad def, int init, boolean assault, boolean rage) {
        double wounds = 0;
        for (CCWeapon cc : ccw) {
            if (init == get(I)) {
                wounds += cc.attack(this, def.stats, assault, rage);
            }
        }
        return wounds;
    }


    private double hit(Stats s, int i) {

        double h = 0;
        for (CCWeapon cc : ccw) {
            double hits = Rules.hitRoll(get(WS), s.get(WS));

            if (cc.rerollHit)
                h += Rules.reroll(hits);
            else if (cc.rerollOneHit)
                h += Rules.reroll1ofN(hits, i);
            else
                h += hits;
        }
        return h;
    }

    private double wound(Stats s, int i) {
        double w = 0;
        for (CCWeapon cc : ccw) {
            double wounds = Rules.woundRoll(get(S), s.get(T));

            if (cc.rerollWounds)
                w = Rules.reroll(wounds);

            else if (cc.rerollOneWound)
                w = Rules.reroll1ofN(wounds, i);
            else
                w = wounds;
        }

        return w;

    }

    private double save(Stats s) {
        return Rules.save(get(AP), s.get(AS), s.get(SS));
    }

    public void updateStats() {
        for (CCWeapon cc : ccw) {
            stats = baseStats.clone();
            cc.updateStats(this);
        }
    }

    public void setAll(Stats s) {
        stats = s.stats.clone();
    }

    public void set(int s, int c) {
        stats[s] = c;
    }

    public void add(int s, int c) {
        stats[s] += c;
    }

    public void mult(int s, double c) {
        stats[s] *= c;
        stats[s] = Math.min(stats[s], 10);
    }

    public int get(int s) {
        return stats[s];
    }

    public int getBase(int s) {
        return baseStats[s];
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int c) {
        cost = c;
    }

    public void addCost(int c) {
        cost += c;
    }


    public String getID() {
        return ID;
    }


    //TODO: Better hash and compareto
    public boolean equals(Object o) {
        return o instanceof Stats && (cost == (((Stats) o).cost)) && rw.equals(((Stats) o).rw) && rw.equals(((Stats) o).rw);
    }

    public int hashCode() {
        return Arrays.hashCode(stats);
    }


    @Override
    public int compareTo(Object o) {
        int c = ((Stats) o).cost;
        return (cost > c ? -1 : (cost == c ? 0 : 1));
    }

    public String toString() {
        String res = "";
        res += " with:\t" + (ID.length() == 8 ? "" : "\t\t") + "(" + getCost() + ")" +
                (ccw.toString().isEmpty() ? "" : "\n\t" + ccw);
        for (RangedWeapon r : rw)
            if (r != null)
                res += "\n\t" + r;
        return res;
    }
}
