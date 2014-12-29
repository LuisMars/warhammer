package es.luismars;
/**
 * Created by Luis on 13/09/2014.
 */
public class Results implements Comparable<Results> {

    String TYPE;
    int ID;
    int COST;
    int ENEMYCOST;
    double WOUNDS;
    double LOST;

    double OVERKILL;

    int TURN;
    double DEFRETREATS;
    double ATTRETREATS;

    double ATTSWEEPS;
    double DEFSWEEPS;

    double SIZE;
    double ENEMYSIZE;

    double EF;

    //TODO: add cost of the other unit
    public Results(String type, int id, int size, int cost, int enemyCost, double wounds, double lost, int turn,
                   double defRetreats, double attRetreats, double attSweeps, double defSweeps, int enemySize) {
        TYPE = type;
        ID = id;
        COST = cost;
        ENEMYCOST = enemyCost;
        WOUNDS = Math.min(enemySize, wounds);
        LOST = Math.min(size, lost);

        OVERKILL = wounds - WOUNDS;
        TURN = turn;
        DEFRETREATS = defRetreats;
        ATTRETREATS = attRetreats;
        ATTSWEEPS = attSweeps;
        DEFSWEEPS = defSweeps;

        SIZE = size;
        ENEMYSIZE = enemySize;
        EF = (WOUNDS / ENEMYSIZE) * (OVERKILL / ENEMYSIZE) * ((SIZE - LOST) / SIZE) * (1 + DEFRETREATS) * (1 - ATTRETREATS) * (1 + ATTSWEEPS) * (1 - DEFSWEEPS);// * (ENEMYCOST*1.0/COST);
    }

    public int compareTo(Results R) {
        //return (EF > R.EF) ? 1 : ((EF < R.EF) ? -1 : 0);
        return (EF > R.EF) ? 1 : ((EF < R.EF) ? -1 : (COST > R.COST) ? -1 : ((COST < R.COST) ? 1 : 0));
    }

    @Override
    public String toString() {
        GreyKnightSquad t = new GreyKnightSquad(TYPE, ID);
        return "\n\n===============================================\n" +
                "-----------------------------------------------\n" +
                TYPE + " Squad: " + ID +
                "\n\tLast turn:\t\t" + (TURN + 1) / 2 +
                "\n\tDef. retreats:\t" + DEFRETREATS +
                "\n\tAtt. sweeps:\t" + ATTSWEEPS +
                "\n\tAtt. retreats:\t" + ATTRETREATS +
                "\n\tDef. sweeps:\t" + DEFSWEEPS +
                "\n\tEfficiency:\t\t" + EF +
                "\n\tWounds:\t\t\t" + WOUNDS / ENEMYSIZE +
                "\n\tOverkill:\t\t" + OVERKILL / ENEMYSIZE +
                "\n\tWounds lost:\t" + LOST / SIZE + "\n\n" +
                t + "===============================================\n";
    }
}
