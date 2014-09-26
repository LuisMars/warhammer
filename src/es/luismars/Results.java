package es.luismars;
/**
 * Created by Luis on 13/09/2014.
 */
public class Results implements Comparable<Results> {

    int ID;
    int COST;
    double WOUNDS;
    double LOST;
    int TURN;
    double CATCHING;
    double RETREAT;

    double EF;

    //TODO: add cost of the other unit
    public Results(int id, int cost, double wounds, double lost, int turn, double catching, double retreat) {
        ID = id;
        COST = cost;
        WOUNDS = wounds;
        LOST = lost;
        TURN = turn;
        CATCHING = catching;
        RETREAT = retreat;
        if (WOUNDS - LOST > 0.0)
            EF = (WOUNDS * 440) / (LOST * COST * TURN);
        else
            EF = 0;
    }

    public int compareTo(Results R) {
        return (EF > R.EF) ? 1 : ((EF == R.EF) ? 0 : -1);
    }

    @Override
    public String toString() {
        TerminatorSquad t = new TerminatorSquad(ID);
        return "ID: " + ID +
                "\nLast turn: " + TURN +
                "\nCatching: " + CATCHING +
                "\nRetreating: " + RETREAT +
                "\nEfficiency: " + EF +
                "\nWounds: " + WOUNDS +
                "\nWounds lost: " + LOST + "\n\n" +
                t + "\n-----------------------------------------------\n";
    }
}
