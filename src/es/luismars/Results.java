package es.luismars;
/**
 * Created by Luis on 13/09/2014.
 */
public class Results implements Comparable<Results> {

    int ID;
    int COST;
    double ASSAULT;
    double NORMAL;
    double LOST;
    double EF;
    public Results(int id, int cost, double assault, double normal, double lost) {
        ID = id;
        COST = cost;
        ASSAULT = assault;
        NORMAL = normal;
        LOST = lost;
        EF = (NORMAL*280)/(LOST * COST);
    }

    public int compareTo(Results R) {
        return (EF > R.EF) ? 1 : ((EF == R.EF) ? 0 : -1);
    }

    @Override
    public String toString() {
        TermSquad t = new TermSquad(ID);
        return "ID: " + ID +
                "\nEfficiency: " + EF +
                "\nWounds: " + NORMAL +
                "\nAssaulting: " + ASSAULT +
                "\nWounds lost: " + LOST + "\n\n" +
                t + "\n-----------------------------------------------\n";
    }
}
