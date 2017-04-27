package architecture;

import java.util.HashMap;

public abstract class Combatant
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
