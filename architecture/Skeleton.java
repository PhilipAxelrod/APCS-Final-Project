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
        return 3.0;
    }


    @Override
    public double[] attributeDistribution()
    {
        double[] distribution = { 20, 2, 15, 15, 10, 3, 5 };
        return distribution.clone();
    }


    @Override
    public String type()
    {
        return "Skeleton";
    }


    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        
    }

}
