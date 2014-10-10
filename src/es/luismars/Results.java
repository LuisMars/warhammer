package es.luismars;
/**
 * Created by Luis on 13/09/2014.
 */
public class Results implements Comparable<Results> {

    String TYPE;
    int ID;
    int COST;
    double WOUNDS;
    double LOST;
    int TURN;
    double DEFRETREATS;
    double ATTRETREATS;

    double ATTSWEEPS;
    double DEFSWEEPS;

    double SIZE;
    double ENEMYSIZE;

    double EF;

    //TODO: add cost of the other unit
    public Results(String type, int id, int size, int cost, double wounds, double lost, int turn,
                   double defRetreats, double attRetreats, double attSweeps, double defSweeps, int enemySize) {
        TYPE = type;
        ID = id;
        COST = cost;
        WOUNDS = Math.min(enemySize, wounds);
        LOST = Math.min(size, lost);
        TURN = turn;
        DEFRETREATS = defRetreats;
        ATTRETREATS = attRetreats;
        ATTSWEEPS = attSweeps;
        DEFSWEEPS = defSweeps;

        SIZE = size;
        ENEMYSIZE = enemySize;
        EF = (WOUNDS / ENEMYSIZE) * (SIZE / LOST) * (1 + DEFRETREATS) * (1 - ATTRETREATS) * (1 + ATTSWEEPS) * (1 - DEFSWEEPS);

    }

    public int compareTo(Results R) {
        return (EF > R.EF) ? 1 : ((EF == R.EF) ? (LOST < R.LOST ? 1 : (LOST == R.LOST ? (COST < R.COST ? 1 : (COST == R.COST ? 0 : -1)) : -1)) : -1);
    }

    @Override
    public String toString() {
        GreyKnightSquad t = new GreyKnightSquad(TYPE, ID);
        return "\n\n===============================================\n" +
                "-----------------------------------------------\n" +
                TYPE + " Squad: " + ID +
                "\n\tLast turn:\t\t" + (TURN) / 2 +
                "\n\tDef. retreats:\t" + DEFRETREATS +
                "\n\tAtt. sweeps:\t" + ATTSWEEPS +
                "\n\tAtt. retreats:\t" + ATTRETREATS +
                "\n\tDef. sweeps:\t" + DEFSWEEPS +
                "\n\tEfficiency:\t\t" + EF +
                "\n\tWounds:\t\t\t" + WOUNDS / ENEMYSIZE +
                "\n\tWounds lost:\t" + LOST / SIZE + "\n\n" +
                t + "===============================================\n";
    }
}
