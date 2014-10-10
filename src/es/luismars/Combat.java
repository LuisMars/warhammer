package es.luismars;

/**
 * Created by Luis on 13/09/2014.
 */
public class Combat {

    Squad a;
    Squad d;
    int distance = 35;
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
                att.tempWounds = shooting(att, def, true) * att.remaining();
            }
            else
                att.tempWounds = shooting(att, def, false) * att.remaining();
        }
        // if is save to assault
        else if (distance - bestM - 6 <= 0) {
            distance -= bestM;
            att.tempWounds = shooting(att, def, true) * att.remaining();

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

                double tempWounds = shooting(att, def, mov != 0) * att.remaining();
                if (tempWounds > att.tempWounds) {
                    att.tempWounds = tempWounds;
                    bestM = mov;
                }

            }

            distance = tempD - bestM;
        }

        att.wounds = att.tempWounds;
        def.lost = att.wounds;

        addSummary("Shooting");
    }

    public void addSummary(String fase) {
        if (turn == 0)
            summary += "==================================\n\t\t" + fase;
        else
            summary += "Turn: " + (turn + 1) / 2 + "\t" + (turn % 2 == 1 ? a.TYPE : d.TYPE) + "\t(" + fase + ")";

        summary += "\n==================================" + ((distance > 0) ? ("\n\tDistance: \t" + distance) : "") +
                "\n\tWounds:\t\t" + a.wounds +
                "\n\tLost:\t\t" + a.lost +
                "\n\tRemaining:\t" + a.remainingMinis() + "(" + a.size + ") / " + d.remainingMinis() + "(" + d.size + ")" +
                "\n\tRetreat:\t" + d.defRetreats +
                "\n\tCatching:\t" + a.defRetreats +
                (turn % 2 == 1 ? "\n==================================\n" : "\n==================================\n\n==================================\n");
    }

    public String toString() {
        return summary;
    }

    public void combat(Squad att, Squad def) {
        double woundsAttTot = 0;
        double woundsDefTot = 0;
        for (int i = 10; i >= 0; i--) {
            double woundsAtt = 0;
            double woundsDef = 0;

            for (Stats s : att.squad) {
                woundsAtt += s.combat(def.squad[0], i) * att.remaining();
            }

            for (Stats s : def.squad) {
                woundsDef += s.combat(att.squad[0], i) * def.remaining();
            }

            woundsAttTot += woundsAtt;
            att.wounds += woundsAtt;
            def.lost += woundsAtt;

            woundsDefTot += woundsDef;
            att.lost += woundsDef;
            def.wounds += woundsDef;


        }

        att.combatResult(woundsAttTot, woundsDefTot, def.squad[0]);
        def.combatResult(woundsDefTot, woundsAttTot, att.squad[0]);
/*
        if (def.canCatch()) {
            att.lost += att.size * att.squad[0].get(Stats.W);
            def.wounds = att.lost;
        }
        if (att.canCatch()) {
            def.lost += def.size * def.squad[0].get(Stats.W);
            att.wounds = def.lost;
        }
        */
        addSummary("CC");
    }

    public double shooting(Squad att, Squad def, boolean moved) {
        double w = 0;
        for (Stats s : att.squad) {
            w += s.shooting(def.squad[0], distance, moved);
        }
        return w;
    }

    public void defensive(Squad att, Squad def) {
        double w = 0;
        for (Stats s : att.squad) {
            w += s.defensive(def.squad[0]);
        }

        att.wounds += w * att.remaining();
        def.lost = att.wounds;
        addSummary("Taking defensive");
    }
}
