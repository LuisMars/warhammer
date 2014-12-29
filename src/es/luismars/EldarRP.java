package es.luismars;

public class EldarRP extends RangedWeapon {

    public EldarRP(int id) {
        super(id);
    }

    public EldarRP(int id, boolean MC, boolean DW) {
        super(id);
        rerollOneHit = MC;
        rerollOneWound = DW;
    }

    public void updateStats(Stats stats) {
        switch (ID) {
            //Heavy Wraithcannon
            case 0: {
                stats.set(Stats.SHOTS, 2);
                stats.set(Stats.RANGE, 36);
                stats.set(Stats.STR, 10);
                stats.set(Stats.APW, 2);
                distort = true;
                cost = 0;
                break;
            }
        }
    }

    public int getCost() {
        return cost;
    }

    public String toString() {
        String res = "";
        if (rerollOneHit)
            res += "Master-crafted ";
        switch (ID) {
            case 0: {
                res += "Heavy Wraithcannon ";
                break;
            }
        }

        return res;
    }

    public String getID() {
        return Utils.toBinStr(ID, 2);
    }

    public boolean exists() {
        return ID != 7;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RangedWeapon)
            return ID == ((RangedWeapon) obj).ID;
        return false;
    }
}
