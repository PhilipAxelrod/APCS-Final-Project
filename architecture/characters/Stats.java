package architecture.characters;

public class Stats
{
    public void setHP( int HP )
    {
        this.stats[0] = HP;
    }


    public void setMP( int MP )
    {
        this.stats[1] = MP;
    }


    public void setATK( int ATK )
    {
        this.stats[2] = ATK;
    }


    public void setDEF( int DEF )
    {
        this.stats[3] = DEF;
    }


    public void setACC( int ACC )
    {
        this.stats[4] = ACC;
    }


    public void setAVO( int AVO )
    {
        this.stats[5] = AVO;
    }


    public void setCRIT( int CRIT )
    {
        this.stats[6] = CRIT;
    }


    public void setCRITAVO( int CRITAVO )
    {
        this.stats[7] = CRITAVO;
    }

    private int[] stats = new int[8];


    public int getHP()
    {
        return stats[0];
    }


    public int getMP()
    {
        return stats[1];
    }


    public int getATK()
    {
        return stats[2];
    }


    public int getDEF()
    {
        return stats[3];
    }


    
    public int getACC()
    {
        return stats[4];
    }
    


    public int getAVO()
    {
        return stats[5];
    }


    public int getCRIT()
    {
        return stats[6];
    }


    public int getCRITAVO()
    {
        return stats[7];
    }


    public int[] getStats()
    {
        return stats;
    }


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
        this.stats[0] = 0;
        this.stats[1] = 0;
        this.stats[2] = 0;
        this.stats[3] = 0;
        this.stats[4] = 0;
        this.stats[5] = 0;
        this.stats[6] = 0;
        this.stats[7] = 0;
    }


    public void incrementAll()
    {
        this.stats[0] += 1;
        this.stats[1] += 1;
        this.stats[2] += 1;
        this.stats[3] += 1;
        this.stats[4] += 1;
        this.stats[5] += 1;
        this.stats[6] += 1;
        this.stats[7] += 1;
    }


    public void setAll( int toSet )
    {
        this.stats[0] = toSet;
        this.stats[1] = toSet;
        this.stats[2] = toSet;
        this.stats[2] = toSet;
        this.stats[3] = toSet;
        this.stats[4] = toSet;
        this.stats[5] = toSet;
        this.stats[6] = toSet;
    }

}
