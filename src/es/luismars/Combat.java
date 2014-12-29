package es.luismars;

public class Combat {

    Squad a;
    Squad d;
    int distance = 60;
    int turn = 0;

    String summary = "";

    public Combat(Squad s, Squad e) {
        a = s;
        d = e;
    }

    public void assault() {
        addSummary("Initial state");
        while (turn <= 14 && a.canFight() && d.canFight()) {
            turn++;
            Squad att = a;
            Squad def = d;
            if (turn % 2 != 1) {
                att = d;
                def = a;
            }
            d.setAverage();
            a.setAverage();
            distance -= att.stats.get(Stats.M);

            if (!att.inCC && !def.inCC && att.canFight() && def.canFight()) {
                shooting(att, def, false);
                //check if should assault
                if (distance <= 6 && att.canFight() && def.canFight()) {
                    att.inCC = true;
                    att.canAssault = true;
                    def.canAssault = def.spr.counterAttack;
                    def.inCC = true;
                    shooting(def, att, true);
                    distance = 0;
                }
            }

            if (att.inCC && def.inCC && att.canFight() && def.canFight()) {
                combat(att, def);
            }
        }
    }

    public String toString() {
        return summary;
    }

    public void combat(Squad att, Squad def) {

        int attRemMin = att.remainingMinis();
        int defRemMin = def.remainingMinis();
        for (int init = 10; init >= 0; init--) {
            double woundsAtt = woundPool(att, def, init);
            double woundsDef = woundPool(def, att, init);

            att.wounds += woundsAtt;
            def.lost += woundsAtt;

            att.lost += woundsDef;
            def.wounds += woundsDef;
        }


        Rules.checkConcusive(att);
        Rules.checkConcusive(def);

        att.combatResult(attRemMin - att.remainingMinis(), defRemMin - def.remainingMinis(), def.squad[0]);
        def.combatResult(defRemMin - def.remainingMinis(), attRemMin - att.remainingMinis(), att.squad[0]);
        att.canAssault = false;
        addSummary("CC");
    }

    private double woundPool(Squad att, Squad def, int init) {
        double wounds = 0;
        if (att.canFight()) {
            for (Stats s : att.squad) {
                double w = s.combat(def, init, att.canAssault, att.spr.rage) * att.remainingPower();
                if (w > 0) {
                    //Instant death
                    if (s.get(Stats.S) >= 2 * def.squad[0].get(Stats.T) && def.squad[0].get(Stats.W) > 1) {
                        w *= def.squad[0].get(Stats.W);
                    }
                    if (def.squad[0].get(Stats.W) > 1 && s.ccw.get(0).concussive)
                        def.concW += w;
                    wounds += w;
                }
            }
        }

        return wounds;
    }

    public void shooting(Squad att, Squad def, boolean overwatch) {
        double w = 0;
        w += att.shoot(def, overwatch) * att.remainingPower();

        att.wounds += w;
        def.lost += w;

        if (w != 0) {
            if (overwatch)
                addSummary("Overwatch");
            else
                addSummary("Shooting");
        }
    }

    public void addSummary(String fase) {
        if (turn == 0)
            summary += "==================================\n\t\t" + fase;
        else
            summary += "Turn: " + (turn + 1) / 2 + "\t" + (turn % 2 == 1 ? a.TYPE : d.TYPE) + "\t(" + fase + ")";

        summary += "\n==================================" + ((distance > 0) ? ("\n\tDistance: \t" + distance) : "") +
                "\n\tWounds:\t\t" + ((int) (a.wounds * 100)) / 100.0 + " / " + d.woundSize +
                "\n\tLost:\t\t" + ((int) (a.lost * 100)) / 100.0 + " / " + a.woundSize +
                "\n\tRemaining:\t" + a.remainingMinis() + "(" + a.size + ") / " + d.remainingMinis() + "(" + d.size + ")" +
                "\n\tInit:\t" + a.squad[0].get(Stats.I) + "(" + a.squad[0].getBase(Stats.I) + ") / " + d.squad[0].get(Stats.I) + "(" + d.squad[0].getBase(Stats.I) + ")" +
                "\n\tConcW: \t" + a.concW + " / " + d.concW +
                "\n\tDef. retreats:\t" + d.defRetreats +
                "\n\tAtt. sweeps:\t" + a.canSweep +
                "\n\tAtt. retreats:\t" + a.defRetreats +
                "\n\tDef. sweeps:\t" + d.canSweep +
                (turn % 2 == 1 ? "\n==================================\n" : "\n==================================\n\n==================================\n");
    }
}
