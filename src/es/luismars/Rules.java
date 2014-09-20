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


    public static double armorSave(int AS) {
        return (AS - 1) / 6.0;
    }

    public static double specialSave(int SS) {
        return (SS - 1) / 6.0;
    }

    public static double reroll1ofN(double p, int n) {
        return p + Math.pow(p, n) * (1 - p);
    }
}
