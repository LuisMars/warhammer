package es.luismars;


public class EldarCCW extends CCWeapon {

    public EldarCCW(int WP, boolean MC, boolean DW) {
        super(WP, MC, DW);
    }

    public EldarCCW(int WP) {
        super(WP);
    }

    public void updateStats(Stats stats) {
        switch (ID) {
            //Sword
            case 0: {
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
                res += "";
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
