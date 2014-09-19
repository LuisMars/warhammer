package es.luismars;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luis on 16/09/2014.
 */
public abstract class Infantry {

    Stats baseStats;
    Stats stats;
    CCWeapon ccw;
    RangedWeapon rw;
    List<Specialtems> spi = new ArrayList<>();


    public Infantry (Stats s, CCWeapon ccw, RangedWeapon rw, Specialtems spi) {
        stats = s;
        setCcw(ccw);
        setRw(rw);
        setSpi(spi);
        
        
    }

    public void updateStats() {
    	stats = baseStats;
    	stats.addStats(ccw.stats);
    }
    
    public CCWeapon getCcw() {
        return ccw;
    }

    public void setCcw(CCWeapon ccw) {
        this.ccw = ccw;
    }

    public RangedWeapon getRw() {
        return rw;
    }

    public void setRw(RangedWeapon rw) {
        this.rw = rw;
    }

    public Specialtems getSpi() {
        return spi.get(0);
    }

    public void setSpi(Specialtems spi) {
        this.spi.add(spi);
}

    public double combat(Stats s) {
    	double wounds = 0;
    	for(int i = 0; i < stats.get(Stats.A); i++) {
    		wounds += hit(s,i+1)*wound(s,i+1);
    		//TODO: saves!!!
    	}
    	
    	
    	return wounds;
    }
    
    private double wound(Stats s, int i) {
    	
    	double wounds = Rules.wound(stats.get(Stats.S), s.get(Stats.T));
    	
    	for(Specialtems sp : spi)	{
    		if(sp.rerollWounds) {
    			wounds *= Rules.reroll(wounds);
    			break;
    		}
    		else if(sp.rerollOneWound) {
    			wounds *= Rules.reroll1ofN(wounds, i);
    			break;
    		}
    			
    	}
    	return wounds;

	}

	public double hit(Stats s, int i) {
    	
    	double hits = Rules.hit(stats.get(Stats.WS), s.get(Stats.WS));
    	
    	for(Specialtems sp : spi)	{
    		if(sp.rerollHit) {
    			hits *= Rules.reroll(hits);
    			break;
    		}
    		else if(sp.rerollOneHit) {
    			hits *= Rules.reroll1ofN(hits, i);
    			break;
    		}
    			
    	}
    	return hits;
    }
	
	public double save(Stats s, int i) {
    	
    	double hits = Rules.armorSave(s.get(Stats.AS));
    	
    	for(Specialtems sp : spi)	{
    		if(sp.rerollHit) {
    			hits *= Rules.reroll(hits);
    			break;
    		}
    		else if(sp.rerollOneHit) {
    			hits *= Rules.reroll1ofN(hits, i);
    			break;
    		}
    			
    	}
    	return hits;
    }
	
}