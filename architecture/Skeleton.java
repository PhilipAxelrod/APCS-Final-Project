package architecture;

public class Skeleton extends Monster
{

    public Skeleton( int level )
    {
        super( level );
    }


    @Override
    public boolean isMagicDamage()
    {
        return false;
    }


    @Override
    public double defenseFactor()
    {
        return 2;
    }


    @Override
    public double[] attributeDistribution()
    {
        double[] distribution = { 20, 3, 15, 15, 10, 3, 5 };
        return distribution.clone();
    }


    @Override
    public String type()
    {
        return "Skeleton";
    }

}
