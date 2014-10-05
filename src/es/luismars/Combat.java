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
        d = new GreyKnightSquad("Strike", "10");
    }

    public void assault() {
        //TODO: maximize wounds, optimize movement
        while (distance > 0) {

            turn++;
            distance -= a.squad[0].get(Stats.M);
            a.wounds += shooting(a, d) * a.remaining();
            d.lost = a.wounds;

            if (distance < 0) {
                addSummary("Shooting");
                break;
            }
            //shoot defensive
            turn++;
            distance -= d.squad[0].get(Stats.M);
            d.wounds += shooting(d, a) * d.remaining();
            a.lost = d.wounds;
            addSummary("Shooting");
        }

        if (turn % 2 == 1) {
            d.wounds += defensive(d, a) * d.remaining();
            a.lost = d.wounds;
        } else {
            a.wounds += defensive(a, d) * a.remaining();
            d.lost = a.wounds;
        }
        addSummary("Defensive");

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
        summary += ("Turn: " + (turn + 1) / 2 + " (" + fase + ")" +
                "\n\tWounds: " + a.wounds +
                "\n\tLost: " + a.lost) +
                "\n\tRemaining: " + a.remainingMinis() + "/" + d.remainingMinis() +
                "\n\tRetreat: " + d.catching +
                "\n\tCatching: " + a.catching +
                ((distance > 0) ? ("\n\tDistance: " + distance) : "") +
                "\n----------------------------------\n";
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
            att.lost += att.size * att.squad[0].get(Stats.W);
            def.wounds = att.lost;
        }
        if (att.canCatch()) {
            def.lost += def.size * def.squad[0].get(Stats.W);
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

    public double defensive(Squad att, Squad def) {
        double w = 0;
        for (Stats s : att.squad) {
            w += s.defensive(def.squad[0]);
        }
        return w;
    }
}
