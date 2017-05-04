package architecture;

public class MonsterDistributionTester
{
    private static final int count = 20;

    private static final int level = 5;

    private static Monster monster;


    public static void main( String[] args )
    {
        //
        // for ( int i = 0; i < count; i++ )
        // {
        // // Attributes
        // monster = new Skeleton( level );
        // for ( int j = 0; j < 7; j++ )
        // {
        // System.out.print( Combatant.attributeNames[j] + " "
        // + monster.getAttributes()[j] + " " );
        // }
        // System.out.println();
        //
        // // Stats
        // for ( int j = 0; j < Combatant.statNames.length; j++ )
        // {
        // System.out.print( Combatant.statNames[j] + " "
        // + monster.getStats()[j] + " " );
        // }
        // System.out.println();
        //
        // }
        // System.out.println();
        // for ( int i = 0; i < monster.distributionRatios().length; i++ )
        // {
        // System.out.print( Combatant.attributeNames[i] + " "
        // + Math.round( monster.distributionRatios()[i] * 100 ) + "% " );
        // }
        for ( int i = 0; i < count; i++ )
            new Skeleton( 5 ).printStatus();
        new Skeleton( 5 ).printDistributionRatios();
    }

}
