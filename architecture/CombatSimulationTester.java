package architecture;

public class CombatSimulationTester
{
    // Defender information
    private static final Monster target = new Skeleton( 20 );

    // Attacker information
    private static final int atk = 70, acc = 240, crit = 20, attacks = 10;


    public static void main( String[] args )
    {
        target.printStatus();
        for ( int attempts = 0; attempts < attacks; attempts++ )
        {
            int[] result = target.receiveAttack( atk, acc, crit );

            System.out.println( result[0] + "!" );

            if ( result[1] == 1 )
                System.out.println( "Miss!" );

            else if ( result[1] == 2 )
                System.out.println( "Critical Hit!" );

            else if ( result[1] == 0 )
                System.out.println( "Hit" );

            System.out.println();

            target.printVitals();
            System.out.println();
        }

    }

}
