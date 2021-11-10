
/**
 * Write a description of KivaCommandTester here.
 * 
 * @author Nicholas Schmidt
 * @version ATA Coding Project due 10/22/2021
 */
public class KivaCommandTester {
    public void testForward() {
        KivaCommand command = KivaCommand.FORWARD;
        System.out.println(command + " followed by " + command.getDirectionKey() + " is printed out to the console");
    }
    
    public void testTurnLeft() {
        KivaCommand command = KivaCommand.TURN_LEFT;
        System.out.println(command + " followed by " + command.getDirectionKey() + " is printed out to the console");
    }
    
    public void testTurnRight() {
        KivaCommand command = KivaCommand.TURN_RIGHT;
        //System.out.println(command);
        //System.out.println(command.getDirectionKey());
        System.out.println(command + " followed by " + command.getDirectionKey() + " is printed out to the console");
    }
    
    public void testTake() {
        KivaCommand command = KivaCommand.TAKE;
        //System.out.println(command);
        //System.out.println(command.getDirectionKey());
        System.out.println(command + " followed by " + command.getDirectionKey() + " is printed out to the console");
    }
    
    public void testDrop() {
        KivaCommand command = KivaCommand.DROP;
        //System.out.println(command);
        //System.out.println(command.getDirectionKey());
        System.out.println(command + " followed by " + command.getDirectionKey() + " is printed out to the console");
    }
}
