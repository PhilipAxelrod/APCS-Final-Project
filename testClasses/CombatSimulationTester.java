package testClasses;

import architecture.characters.CombatResult;
import architecture.characters.Monster;
import architecture.characters.Player;
import architecture.characters.Skeleton;


public class CombatSimulationTester
{
    // Defender information
    private static final Monster target = new Skeleton( 20, new Player() );

    // Attacker information
    private static final int atk = 70, acc = 300, crit = 75, attacks = 10;


    public static void main( String[] args )
    {
        target.printStatus();
        for ( int attempts = 0; attempts < attacks; attempts++ )
        {
            // TODO: make compile
//            CombatResult result = target.receiveAttack( atk, acc, crit, null );
//
//            if ( result.isHit() )
//                System.out.println( result.getDamage() + "!" );
//            else
//                System.out.println( "Miss!" );
//
//            if ( result.isCritical() )
//                System.out.println( "Critical Hit!" );
//
//            System.out.println();
//
//            target.printVitals();
            System.out.println();
        }

    }

}
