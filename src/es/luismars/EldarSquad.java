package es.luismars;

public class EldarSquad extends Squad {

    public EldarSquad(String type) {
        TYPE = type;
        if (TYPE.charAt(0) == 'W') {
            size = 1;
            squad = new Stats[size];
            squad[0] = new Stats(new int[]{4, 4, 10, 8, 6, 5, 4, 10, 3, 7, 7, 0, 0, 0, 0, 12}, new EldarCCW(0), new EldarRP(0));
            squad[0].setCost(230);
            cost = squad[0].getCost();
            setWoundSize();
        }
        if (TYPE.charAt(0) == 'B') {

        }
    }
}
