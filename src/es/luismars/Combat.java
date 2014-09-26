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
        d = new TerminatorSquad(59281552);
    }

    public void assault() {
        //TODO: better sweeping advances
/*
        while (distance > 0) {

            turn++;
            a.wounds += shooting(a, d) * a.remaining();
            d.lost = a.wounds;
            distance -= a.squad[0].get(Stats.M);
            if (distance < 0) break;
            //shoot deffensive
            turn++
            d.wounds += shooting(d, a) * d.remaining();
            a.lost = d.wounds;
            distance -= d.squad[0].get(Stats.M);
            addSummary("Shooting");
        }
*/
        while (turn <= 14 && a.canFight() && d.canFight()) {
            if (turn % 2 == 1)
                combat(a, d);
            else
                combat(d, a);
            addSummary("CC");
            turn++;
        }


    }

    public void addSummary(String fase) {
        summary += ("Turn: " + turn + " (" + fase + ")\nWounds: " + a.wounds + "\nLost: " + a.lost);
        summary += ("\nRemaining: " + a.remainingMinis() + "/" + d.remainingMinis());
        summary += ("\nRetreat: " + d.catching);
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

        if (def.canCatch()) {
            att.lost = att.size * att.squad[0].get(Stats.W);
            def.wounds = att.lost;
        }
        if (att.canCatch()) {
            def.lost = def.size * def.squad[0].get(Stats.W);
            att.wounds = def.lost;
        }

    }

    public double shooting(Squad att, Squad def) {
        double w = 0;
        for (Stats s : att.squad) {
            w += s.shooting(def.squad[0], distance);
        }
        return w;
    }

}
