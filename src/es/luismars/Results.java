package es.luismars;
/**
 * Created by Luis on 13/09/2014.
 */
public class Results implements Comparable<Results> {

    int ID;
    int COST;
    double ASSAULT;
    double LOST;
    double EF;

    public Results(int id, int cost, double assault, double lost) {
        ID = id;
        COST = cost;
        ASSAULT = assault;
        LOST = lost;
        EF = (ASSAULT) / (LOST * COST);
    }

    public int compareTo(Results R) {
        return (EF > R.EF) ? 1 : ((EF == R.EF) ? 0 : -1);
    }

    @Override
    public String toString() {
        TerminatorSquad t = new TerminatorSquad(ID);
        return "ID: " + ID +
                "\nEfficiency: " + EF +
                "\nWounds: " + ASSAULT +
                "\nWounds lost: " + LOST + "\n\n" +
                t + "\n-----------------------------------------------\n";
    }
}
