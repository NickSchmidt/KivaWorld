
/**
 * Write a description of KivaMotorLifetimeTester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KivaMotorLifetimeTester {
    String layout = "-----\n" +
                    "|K D|\n" +
                    "| P |\n" +
                    "|* *|\n" +
                    "-----\n";
                    
    FloorMap defaultMap = new FloorMap(layout);
    
    public void testMotorLifetime() {
        Kiva kiva = new Kiva(defaultMap);
        System.out.println("MotorLifetime: " + kiva.getMotorLifetime());
        kiva.move(KivaCommand.TURN_RIGHT);
        System.out.println("MotorLifetime: " + kiva.getMotorLifetime());
        kiva.move(KivaCommand.FORWARD);
        System.out.println("MotorLifetime: " + kiva.getMotorLifetime());
        kiva.move(KivaCommand.TURN_RIGHT);
        System.out.println("MotorLifetime: " + kiva.getMotorLifetime());
        kiva.move(KivaCommand.FORWARD);
        System.out.println("MotorLifetime: " + kiva.getMotorLifetime());
        kiva.move(KivaCommand.TAKE);
        System.out.println("MotorLifetime: " + kiva.getMotorLifetime());
    }
    
}
