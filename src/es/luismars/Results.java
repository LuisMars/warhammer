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
    double CATCHING;
    double RETREAT;

    double EF;

    //TODO: add cost of the other unit
    public Results(String type, int id, int cost, double wounds, double lost, int turn, double catching, double retreat) {
        TYPE = type;
        ID = id;
        COST = cost;
        WOUNDS = wounds;
        LOST = lost;
        TURN = turn;
        CATCHING = catching;
        RETREAT = retreat;
        //TODO: wounds and lost wounds [0, 1]?
        //if (WOUNDS - LOST > 0.0)
        EF = ((7 - TURN / 2) * WOUNDS * ((1 + CATCHING) * (1 - RETREAT))) / (LOST);//(WOUNDS) / (LOST * COST);
        //else
        //    EF = (TURN)/((1+RETREAT) * COST * (LOST-WOUNDS));
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
                "\n\tLast turn: " + (TURN) / 2 +
                "\n\tCatching: " + CATCHING +
                "\n\tRetreating: " + RETREAT +
                "\n\tEfficiency: " + EF +
                "\n\tWounds: " + WOUNDS +
                "\n\tWounds lost: " + LOST + "\n\n" +
                t + "===============================================\n";
    }
}
