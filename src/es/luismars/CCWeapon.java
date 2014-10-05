package es.luismars;
/**
 * Created by Luis on 11/09/2014.
 */
public class CCWeapon {

    int ID;
    int cost = 0;
    SpecialRules spr = new SpecialRules();


    public CCWeapon(int WP, boolean MC, boolean DW) {
        ID = Math.min(4, WP);
        spr.rerollOneHit = MC;
        spr.rerollOneWound = DW;
    }

    public void updateStats(Stats stats) {
        stats.set(stats.AP,3);

        switch (ID) {
            //Sword
            case 0: {
                break;
            }
            //Halberd
            case 1: {
                stats.add(stats.S, 1);
                cost += 2;
                break;
            }
            //Falchions
            case 2: {
                stats.add(stats.A, 1);
                cost +=4;
                break;
            }
            //Stave
            case 3: {
                stats.add(stats.S, 2);
                stats.set(stats.AP, 4);
                cost += 5;
                break;
            }
            //Hammer
            case 4: {
                stats.mult(stats.S, 2);
                stats.set(stats.I, 1);
                stats.set(stats.AP, 2);
                cost += 10;
                break;
            }
        }
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        String res = "";
        if (spr.rerollOneHit)
            res += "Master-crafted ";

        switch (ID) {
            case 0: {
                res += "Sword, ";
                break;
            }
            case 1: {
                res += "Halberd, ";
                break;
            }
            case 2: {
                res += "Falchions, ";
                break;
            }
            case 3: {
                res += "Stave, ";
                break;
            }
            case 4: {
                res += "Hammer, ";
                break;
            }
            default:
                res += "Invalid weapon ";
        }

        return res;
    }

    public String getID() {
        return Utils.toBinStr(ID, 3);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CCWeapon)
            return ID == ((CCWeapon) obj).ID;
        return false;
    }
}
