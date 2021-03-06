package es.luismars;

/**
 * Created by Luis on 12/09/2014.
 */
public class Rules {

    public static double hitRoll(int att, int def) {
        double hits;

        if (att > def)
            hits = 4 / 6.0;
        else if (def > 2 * att)
            hits = 2 / 6.0;
        else
            hits = 3 / 6.0;

        return hits;
    }

    public static double shootRoll(int hp) {
        double shots = hp / 6.0;

        if (hp > 5)
            shots = 5 / 6.0 + (1 / 6.0) * (hp - 5) / 6.0;

        return shots;
    }

    public static double reroll(double p) {
        return (p) + ((1 - p) * p);
    }

    public static double woundRoll(int att, int def) {
        double wounds;

        if (att == def)
            wounds = 3 / 6.0;
        else if (att == def + 1)
            wounds = 4 / 6.0;
        else if (att == def - 1)
            wounds = 2 / 6.0;
        else if (att > def + 1)
            wounds = 5 / 6.0;
        else if (att + 3 >= def)
            wounds = 1 / 6.0;
        else
            wounds = 0;

        return wounds;
    }

    public static String woundRoll() {
        String res = "";
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                int p = 7 - (int) (woundRoll(i, j) * 6);
                res += (p != 7 ? p + "+" : "-") + "\t";
            }
            res += "\n";
        }
        return res;
    }

    public static String hitRoll() {
        String res = "";
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                int p = 7 - (int) (hitRoll(i, j) * 6);
                res += (p != 7 ? p + "+" : "-") + "\t";
            }
            res += "\n";
        }
        return res;
    }

    public static double passTest(int n) {
        double p = 1;
        switch (n) {
            case 10:
                p += 3;
            case 9:
                p += 4;
            case 8:
                p += 5;
            case 7:
                p += 6;
            case 6:
                p += 5;
            case 5:
                p += 4;
            case 4:
                p += 3;
            case 3:
                p += 2;
        }
        return p / 36.0;
    }

    public static double save(int AP, int AS, int SS) {
        if (AP <= AS)
            return armorSave(AP, AS);
        else
            return specialSave(SS);
    }

    public static double armorSave(int AP, int AS) {
        if (AS == 7 || AP <= AS)
            return 1;
        else
            return (AS - 1) / 6.0;
    }

    public static double specialSave(int SS) {
        return (SS - 1) / 6.0;
    }

    public static double reroll1ofN(double p, int n) {
        return p + Math.pow(p, n) * (1 - p);
    }

    public static double rending(double wounds, double save) {
        return force(wounds, save, 1);
    }

    public static double force(double wounds, double save, int W) {
        if (wounds != 0)
            return ((wounds - (1 / 6.0)) * save) + ((1 - save) * (W / 6.0));
        else
            return wounds * save;
    }

    public static double retreatCC(double wDif, int L) {
        if (wDif > -0.5)
            return (1 - passTest(L - (int) (Math.ceil(wDif))));
        else
            return 0;
    }

    public static double sweepingAdvance(int iA, int iD) {
        return Math.min(Math.max(1 - ((6 - ((iA + 3) - iD)) / 6.0), 0), 1);
    }

    public static void checkConcusive(Squad att) {

        if (att.concActive) {
            att.concActive = false;
            for (Stats s : att.squad)
                s.updateStats();
        }

        if (att.concW > 0) {
            att.concW = (int) Math.ceil(att.concW);
            att.concActive = true;
            for (int j = 0; j < att.squad.length && j < att.concW; j++) {
                att.squad[j].set(Stats.I, 1);
            }
        }

        att.concW = 0;
    }

}
