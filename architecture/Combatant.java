package architecture;

import java.util.HashMap;
import java.util.TimerTask;

public abstract class Combatant extends TimerTask
{

    int health, mana, level;
    HashMap<String, Integer> stats;


    public Combatant( int health, int mana, int level, HashMap<String, Integer> stats )
    {
        this.health = health;
        this.mana = mana;
        this.level = level;
        this.stats = stats;
    }
}
