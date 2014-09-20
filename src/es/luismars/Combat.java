package es.luismars;

/**
 * Created by Luis on 13/09/2014.
 */
public class Combat {

    Squad a;
    Squad d;
    int distance = 24;
    int turn = 0;

    public Combat(Squad s) {
        a = s;
        d = new TerminatorSquad(0);
    }

    public void assault() {
        //TODO: add LD tests
        while (distance > 0) {
            turn++;
            d.lost = a.wounds += shooting(a, d) * a.remaining();
            distance -= a.squad[0].get(Stats.M);
            //TODO: Add break when distance == 0 and shoot deffensive
            a.lost = d.wounds += shooting(d, a) * d.remaining();
            distance -= d.squad[0].get(Stats.M);
        }

        while (turn <= 7 && a.lost < a.size && d.lost < d.size) {
            combat(a, d);
        }


    }

    public void combat(Squad att, Squad def) {

        for (int i = 10; i >= 0; i--) {
            double woundsAtt = 0;
            double woundsDef = 0;

            for (Stats s : att.squad) {
                woundsAtt = s.combat(def.squad[0], i) * att.remaining();
            }

            for (Stats s : def.squad) {
                woundsDef = s.combat(att.squad[0], i) * att.remaining();
            }

            att.lost = def.wounds += woundsDef;
            def.lost = att.wounds += woundsAtt;

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
