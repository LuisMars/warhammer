package es.luismars;

import java.util.List;

/**
 * Created by Luis on 11/09/2014.
 */
public class Stats implements Comparable {

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

    public CCWeapon ccw;
    public RangedWeapon rw;
    public RangedWeapon rw2;
    public SpecialRules spr = new SpecialRules();
    public String ID = "";

    private int[] baseStats = new int[16];
    private int[] stats = new int[16];
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
        ccw = cc;
        rw = r;
        rw2 = r2;
        updateStats();
    }

    public double shooting(Stats s, int size, int distance, boolean moved) {
        double wounds = 0;

        checkMovement(moved);
        wounds += shootWeapon(s, size, rw, distance);
        wounds += shootWeapon(s, size, rw2, distance);

        return wounds;
    }

    private double shootWeapon(Stats s, int size, RangedWeapon r, int distance) {
        double wounds = 0;
        if (r != null) {
            r.updateStats(this);
            if (distance <= get(RANGE)) {
                for (int i = 1; i <= get(Stats.SHOTS); i++) {
                    if (r.spr.template)
                        wounds += Math.min(size / 2, Math.max(size / 4, (get(RANGE) - distance))) * woundShooting(s, i) * saveShooting(s, i);
                    else if (r.spr.rending)
                        wounds += shoot(s, i) * Rules.rending(get(STR), s.get(T), s.get(AS));
                    else if (r.spr.distort || r.spr.force)
                        wounds += shoot(s, i) * Rules.distort(get(STR), s.get(T), s.get(AS), s.get(W));
                    else
                        wounds += shoot(s, i) * woundShooting(s, i) * saveShooting(s, i);
                }//TODO: check penetration on distort/force
            }
        }
        return wounds;
    }

    private void checkMovement(boolean moved) {
        rw.updateStats(this);
        if (!spr.shootHeavy && moved) {
            if (rw.spr.salvo) {
                mult(SHOTS, 0.5);
                mult(RANGE, 0.5);
            } else if (rw.spr.heavy) {
                mult(SHOTS, 0);
                mult(RANGE, 0);
            }
        }
    }

    public double defensive(Stats s) {
        double wounds = 0;

        for (int i = 1; i <= get(Stats.SHOTS); i++) {
            if (rw.spr.rending)
                wounds += shootDefensive(s, i) * Rules.rending(get(STR), s.get(T), s.get(AS));
            else if (rw.spr.distort)
                wounds += shoot(s, i) * Rules.distort(get(STR), s.get(T), s.get(AS), s.get(W));
            else
                wounds += shootDefensive(s, i) * woundShooting(s, i) * saveShooting(s, i);
        }
        return wounds;
    }

    //TODO: extra attacks
    public double combat(Stats s, int init) {
        double wounds = 0;
        if (init == get(I)) {
            for (int i = 1; i <= get(A); i++) {
                if (s.ccw.spr.force) {
                    wounds += hit(s, i) * Rules.distort(get(STR), s.get(T), s.get(AS), s.get(W));
                } else
                    wounds += hit(s, i) * wound(s, i) * save(s, i);
            }
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

    private double shootDefensive(Stats s, int i) {
        double hits = 1 / 6.0;
        if (rw.spr.template)
            hits = 1;
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

    public void updateStats() {
        stats = baseStats.clone();
        ccw.updateStats(this);
        rw.updateStats(this);
        if (rw2 != null)
            rw2.updateStats(this);

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
        return Integer.parseInt(rw.getID() + ccw.getID());
    }


    @Override
    public int compareTo(Object o) {
        int c = ((Stats) o).cost;
        return (cost > c ? -1 : (cost == c ? 0 : 1));
    }

    public String toString() {
        String res = "";
        res += " with:\t" + (ID.length() == 8 ? "" : "\t\t") + "(" + getCost() + ")" +
                (ccw.toString().isEmpty() ? "" : "\n\tccw") +
                (rw.exists() ? ("\n\t" + rw) : "") +
                ((rw2 != null && rw2.exists()) ? ("\n\t" + rw2) : "");

        return res;
    }
}
