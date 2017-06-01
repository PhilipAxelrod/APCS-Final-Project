package architecture.characters;

/**
 * Stores combat stats for each Combatant
 *
 * @author Kevin Liu
 * @version May 31, 2017
 * @author Period: 5
 * @author Assignment: APCS Final
 *
 * @author Sources: none
 */
public class Stats
{
    private int[] stats = new int[8];


    public Stats(
        int HP,
        int MP,
        int ATK,
        int DEF,
        int ACC,
        int AVO,
        int CRIT,
        int CRITAVO )
    {
        this.stats[0] = HP;
        this.stats[1] = MP;
        this.stats[2] = ATK;
        this.stats[3] = DEF;
        this.stats[4] = ACC;
        this.stats[5] = AVO;
        this.stats[6] = CRIT;
        this.stats[7] = CRITAVO;
    }


    public Stats()
    {
        for ( int i = 0; i < stats.length; i++ )
        {
            stats[i] = 0;
        }
    }


    /**
     * Sets HP
     * 
     * @param HP
     *            new HP
     */
    public void setHP( int HP )
    {
        this.stats[0] = HP;
    }


    /**
     * Sets MP
     * 
     * @param MP
     *            new MP
     */
    public void setMP( int MP )
    {
        this.stats[1] = MP;
    }


    /**
     * Sets ATK
     * 
     * @param ATK
     *            new ATK
     */
    public void setATK( int ATK )
    {
        this.stats[2] = ATK;
    }


    /**
     * Sets DEF
     * 
     * @param DEF
     *            new Def
     */
    public void setDEF( int DEF )
    {
        this.stats[3] = DEF;
    }


    /**
     * Sets ACC
     * 
     * @param ACC
     *            new ACC
     */
    public void setACC( int ACC )
    {
        this.stats[4] = ACC;
    }


    /**
     * Sets AVO
     * 
     * @param AVO
     *            new AVO
     */
    public void setAVO( int AVO )
    {
        this.stats[5] = AVO;
    }


    /**
     * Sets CRIT
     * 
     * @param CRIT
     *            new CRIT
     */
    public void setCRIT( int CRIT )
    {
        this.stats[6] = CRIT;
    }


    /**
     * Sets CRITAVO
     * 
     * @param CRITAVO
     *            new CRITAVO
     */
    public void setCRITAVO( int CRITAVO )
    {
        this.stats[7] = CRITAVO;
    }


    /**
     * @return the HP
     */
    public int getHP()
    {
        return stats[0];
    }


    /**
     * @return the MP
     */
    public int getMP()
    {
        return stats[1];
    }


    /**
     * @return the ATK
     */
    public int getATK()
    {
        return stats[2];
    }


    /**
     * @return the DEF
     */
    public int getDEF()
    {
        return stats[3];
    }


    /**
     * @return the ACC
     */
    public int getACC()
    {
        return stats[4];
    }


    /**
     * @return the AVO
     */
    public int getAVO()
    {
        return stats[5];
    }


    /**
     * @return the CRIT
     */
    public int getCRIT()
    {
        return stats[6];
    }


    /**
     * @return the CRITAVO
     */
    public int getCRITAVO()
    {
        return stats[7];
    }


    /**
     * @return all stats
     */
    public int[] getStats()
    {
        return stats;
    }


    /**
     * Increments all stats by 1.
     */
    public void incrementAll()
    {
        for ( int i = 0; i < stats.length; i++ )
        {
            stats[i]++;
        }
    }


    /**
     * Sets all stats
     * 
     * @param toSet
     *            new stat value
     */
    public void setAll( int toSet )
    {
        for ( int i = 0; i < stats.length; i++ )
        {
            stats[i] = toSet;
        }
    }

}
