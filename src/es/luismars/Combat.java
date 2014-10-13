package es.luismars;

public class Combat {

    Squad a;
    Squad d;
    int distance = 12;
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

            if (!att.inCC && !def.inCC && att.canFight() && def.canFight()) {
                shoot(att, def);

                //check if should assault
                if (distance <= 6 && att.canFight() && def.canFight()) {
                    att.inCC = true;
                    def.inCC = true;
                    defensive(def, att);
                    distance = 0;
                }
            }

            if (att.inCC && def.inCC && att.canFight() && def.canFight()) {
                combat(att, def);
            }
        }
    }

    public void shoot(Squad att, Squad def) {

        int tempD = distance;
        int bestM = att.squad[0].get(Stats.M);
        int mov;

        //if distance is more than enough to assault
        if (distance <= bestM) {
            if (distance != 1) {
                distance = 1;
                att.tempWounds = shooting(att, def, true) * att.remainingPower();
            } else
                att.tempWounds = shooting(att, def, false) * att.remainingPower();
        }
        // if is save to assault
        else if (distance - bestM - 6 <= 0) {
            distance -= bestM;
            att.tempWounds = shooting(att, def, true) * att.remainingPower();

        } //not getting assaulted
/*
        //TODO: check if its better to assault than going back
        else if(def.squad[0].get(Stats.M) + 6 >= distance - bestM) {
            //System.out.println(att.TYPE + " in danger in turn " + (turn+1)/2);

            mov = def.squad[0].get(Stats.M) - 7;
            distance += mov;
            att.tempWounds = shooting(att, def, mov != 0) * att.remaining();
        }*/
        else {
            for (int i = bestM; i >= 0; i--) {
                mov = i;
                distance = tempD - i;

                double tempWounds = shooting(att, def, mov != 0) * att.remainingPower();

                if (tempWounds > att.tempWounds) {
                    att.tempWounds = tempWounds;
                    bestM = mov;
                }

            }

            distance = tempD - bestM;
        }

        att.wounds += att.tempWounds;
        def.lost = att.wounds;

        addSummary("Shooting");
    }

    public String toString() {
        return summary;
    }

    public void combat(Squad att, Squad def) {
        double woundsAttTot = 0;
        double woundsDefTot = 0;
        int attRemMin = att.remainingMinis();
        int defRemMin = def.remainingMinis();
        for (int i = 10; i >= 0; i--) {
            double woundsAtt = woundPool(att, def, i);
            double woundsDef = woundPool(def, att, i);

            woundsAttTot += woundsAtt;
            att.wounds += woundsAtt;
            def.lost += woundsAtt;

            woundsDefTot += woundsDef;
            att.lost += woundsDef;
            def.wounds += woundsDef;
        }


        Rules.checkConcusive(att);
        Rules.checkConcusive(def);

        att.combatResult(attRemMin - att.remainingMinis(), defRemMin - def.remainingMinis(), def.squad[0]);
        def.combatResult(defRemMin - def.remainingMinis(), attRemMin - att.remainingMinis(), att.squad[0]);

        addSummary("CC");
    }

    private double woundPool(Squad att, Squad def, int i) {
        double wounds = 0;
        if (att.canFight()) {
            for (Stats s : att.squad) {
                double w = s.combat(def.squad[0], i) * att.remainingPower();
                if (w > 0) {
                    //Instant death
                    if (s.get(Stats.S) >= 2 * def.squad[0].get(Stats.T) && def.squad[0].get(Stats.W) > 1) {
                        w *= def.squad[0].get(Stats.W);
                    }
                    if (def.squad[0].get(Stats.W) > 1 && s.ccw.spr.concussive)
                        def.concW += w;
                    wounds += w;
                }
            }
        }

        return wounds;
    }

    public double shooting(Squad att, Squad def, boolean moved) {
        double w = 0;
        for (Stats s : att.squad) {
            w += s.shooting(def.squad[0], def.size, distance, moved);
        }
        return w;
    }

    public void defensive(Squad att, Squad def) {
        double w = 0;
        for (Stats s : att.squad) {
            w += s.defensive(def.squad[0]);
        }

        att.wounds += w * att.remainingPower();
        def.lost = att.wounds;
        addSummary("Taking defensive");
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
