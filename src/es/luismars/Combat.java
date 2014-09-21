package es.luismars;

/**
 * Created by Luis on 13/09/2014.
 */
public class Combat {

    Squad a;
    Squad d;
    int distance = 24;
    int turn = 0;

    String summary = "";

    public Combat(Squad s) {
        a = s;
        d = new TerminatorSquad(0);
    }

    public void assault() {
        //TODO: better sweeping advances
        while (distance > 0) {

            turn++;
            a.wounds += shooting(a, d) * a.remaining();
            d.lost = a.wounds;
            distance -= a.squad[0].get(Stats.M);
            if (distance < 0) break;
            //shoot deffensive
            d.wounds += shooting(d, a) * d.remaining();
            a.lost = d.wounds;
            distance -= d.squad[0].get(Stats.M);
            addSummary("Shooting");
        }

        while (turn <= 7 && a.canFight() && d.canFight()) {
            combat(a, d);
            combat(d, a);
            addSummary("CC");
            turn++;
        }


    }

    public void addSummary(String fase) {
        summary += ("Turn: " + turn + " (" + fase + ")\nWounds: " + a.wounds + "\nLost: " + a.lost);
        summary += ("\nRemaining: " + a.remainingMinis() + "/" + d.remainingMinis());
        summary += ("\nRetreat: " + a.retreat);
        summary += ("\nCatching: " + a.catching);
        summary += ("\n-----------------------------\n");
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
                woundsAtt = s.combat(def.squad[0], i) * att.remaining();
            }

            for (Stats s : def.squad) {
                woundsDef = s.combat(att.squad[0], i) * def.remaining();
            }

            woundsAttTot += woundsAtt;
            woundsDefTot += woundsDef;

        }

        double catching = Rules.retreatCC((woundsAttTot - woundsDefTot), def.squad[0].get(Stats.L));
        double retreat = Rules.retreatCC((woundsDefTot - woundsAttTot), att.squad[0].get(Stats.L));

        att.catching = catching * Rules.sweepingAdvance(def.squad[0].getBase(Stats.I), att.squad[0].getBase(Stats.I));
        att.retreat = retreat * Rules.sweepingAdvance(att.squad[0].getBase(Stats.I), def.squad[0].getBase(Stats.I));
        ;

        def.catching = att.retreat;
        def.retreat = att.catching;

        att.lost = def.wounds += woundsDefTot;
        def.lost = att.wounds += woundsAttTot;
    }

    public double shooting(Squad att, Squad def) {
        double w = 0;
        for (Stats s : att.squad) {
            w += s.shooting(def.squad[0], distance);
        }
        return w;
    }

}
