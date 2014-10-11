package es.luismars;


public class CCWeapon {

    int ID;
    int cost = 0;
    SpecialRules spr = new SpecialRules();

    public CCWeapon(int WP, boolean MC, boolean DW) {
        ID = Math.min(4, WP);
        spr.rerollOneHit = MC;
        spr.rerollOneWound = DW;
    }

    public CCWeapon(int WP) {
        ID = WP;
    }
    public void updateStats(Stats stats) {
        stats.set(Stats.AP, 3);

        switch (ID) {
            //Sword
            case 0: {
                break;
            }
            //Halberd
            case 1: {
                stats.add(Stats.S, 1);
                cost += 2;
                break;
            }
            //Falchions
            case 2: {
                stats.add(Stats.A, 1);
                cost +=4;
                break;
            }
            //Stave
            case 3: {
                stats.add(Stats.S, 2);
                stats.set(Stats.AP, 4);
                cost += 5;
                break;
            }
            //Hammer
            case 4: {
                stats.mult(Stats.S, 2);
                stats.set(Stats.I, 1);
                stats.set(Stats.AP, 2);
                cost += 10;
                spr.concussive = true;
                break;
            }
            //Great Sword
            case 5: {
                stats.mult(Stats.S, 2);
                stats.set(Stats.AP, 2);
                spr.rerollOneHit = true;
                cost += 10;
                break;
            }
            //Power fists
            case 6: {
                stats.mult(Stats.S, 2);
                stats.set(Stats.AP, 2);
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
                res += "Sword";
                break;
            }
            case 1: {
                res += "Halberd";
                break;
            }
            case 2: {
                res += "Falchions";
                break;
            }
            case 3: {
                res += "Stave";
                break;
            }
            case 4: {
                res += "Daemon Hammer";
                break;
            }
            case 5: {
                res += "Great Sword";
                break;
            }
            case 6: {
                res += "Power fists";
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
        return obj instanceof CCWeapon && ID == ((CCWeapon) obj).ID;
    }
}
