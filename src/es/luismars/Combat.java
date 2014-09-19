package es.luismars;
/**
 * Created by Luis on 13/09/2014.
 */
public class Combat {

    TermSquad t;
    Long ID = 0101l;
    TermSquad b = new TermSquad(0);
    boolean hammerhand;
    static double lost;
    static double wounds;

    public Combat (TermSquad a) {
        t = a;
    }

    public double assault() {
        return assault(0);
    }
    public double normal() {
        return assault(1);
    }

    public double assault(int turn) {
        wounds = 0;
        lost = 0;
        //false - false
        wounds += t.shoot(b.termis[0].stats);

        combat(turn, true, false);

        //false - true

        combat(turn+1, true, false);

        //true - true

        return wounds;
    }

    public void combat(int turn, boolean att, boolean def) {
        double a; //wounds taken in past round
        double p; //wounds dealt in past round
        double w = Math.min(b.size, wounds); // total dealt
        double l = Math.min(t.size, lost); // total received
        for (int i = 10; i >= 0; i--) {
            p = (((1.0 * t.size) - l) / (t.size * 1.0)) * t.combat(turn, b.termis[0].stats, i, att);
            a = (((1.0 * b.size) - w) / (b.size * 1.0)) * b.combat(1, t.termis[0].stats, i, def);

            w += p;
            l += a;

            w = Math.min(b.size, w);
            l = Math.min(t.size, l);
        }
        lost += l;
        wounds += w;
    }
}
