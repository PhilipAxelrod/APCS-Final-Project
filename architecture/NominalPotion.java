package architecture;

public class NominalPotion extends Potion
{
    private int value;

    private static final double maxFactor = 0.6;


    public NominalPotion( int level )
    {
        super();

        value = (int)Math.round( maxFactor * vitalFactors[type] * level * var );

    }


    @Override
    public boolean use()
    {
        // TODO Auto-generated method stub
        return false;
    }

}
