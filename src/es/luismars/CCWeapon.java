package es.luismars;
/**
 * Created by Luis on 11/09/2014.
 */
public class CCWeapon {

    int ID;
    int cost = 0;
    SpecialRules spr;


    public CCWeapon(int WP, boolean MC, boolean DW) {
        ID = Math.min(4, WP);

    }

    public void updateStats(Stats stats) {
        stats.set(stats.AP,3);

        switch (ID) {
            case 0: {
                break;
            }
            case 1: {
                stats.add(stats.S, 1);
                cost += 2;
                break;
            }
            case 2: {
                stats.add(stats.A, 1);
                cost +=4;
                break;
            }
            case 3: {
                stats.add(stats.S, 2);
                stats.set(stats.AP, 4);
                cost += 5;
                break;
            }

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
            case 0:
                res += "Sword ";
            case 1:
                res += "Halberd ";
            case 2:
                res += "Falchions ";
            case 3:
                res += "Stave ";
            case 4:
                res += "Hammer ";
            default: return "Invalid weapon";
        }


    }

    public String getID() {
        return Utils.toBinStr(ID, 3);
    }
}