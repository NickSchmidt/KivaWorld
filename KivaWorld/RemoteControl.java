import edu.duke.FileResource;
import java.util.Arrays;

/**
 * This is the class that controls Kiva's actions. Implement the <code>run()</code>
 * method to deliver the pod and avoid the obstacles.
 * 
 * @author Nicholas Schmidt
 * @version ATA Coding Project due 10/22/2021
 */
public class RemoteControl {
    KeyboardResource keyboardResource;

    /**
     * Build a new RemoteControl
     */
    public RemoteControl() {
        keyboardResource = new KeyboardResource();
    }
    
    /**
     * Converts keyboard input into Kiva Robot Language.
     * 
     * <p> Stores the inputs into an array. It's helpful to loop through the array and execute
     * the commands for the Kiva Robot.</p>
     * 
     * @exception IllegalArgumentException on illegal input commands
     * @return a KivaCommand array of actions
     */
    public KivaCommand[] convertToKivaCommands(String commands) {
        KivaCommand[] arrayOfCommands = new KivaCommand[commands.length()];
        for (int i = 0; i < commands.length(); i++) {
            char command = commands.charAt(i);
            for (KivaCommand c : KivaCommand.values()) {
                char convert = c.getDirectionKey();
                if (command != 'F' && command != 'L' && command != 'R' && command != 'T' && command != 'D') {
                    throw new IllegalArgumentException(String.format("Command %s does not exist. Please choose F, R, L, T, or D", command));
                }
                if (command != convert) {
                    continue;
                }
                arrayOfCommands[i] = c;
            }
        }
        return arrayOfCommands;
    }

    /**
     * The controller that directs Kiva's activity. Prompts the user for the floor map
     * to load, displays the map, and asks the user for the commands for Kiva to execute.
     */
    public void run() {
        System.out.println("Please select a map file.");
        FileResource fileResource = new FileResource();
        String inputMap = fileResource.asString();
        FloorMap floorMap = new FloorMap(inputMap);
        System.out.println(floorMap);

        System.out.println("Please enter the directions for the Kiva Robot to take.");
        String directions = keyboardResource.getLine();
        System.out.println("Directions that you typed in: " + directions);
        
        Kiva kiva = new Kiva(floorMap);
        System.out.println(String.format("Starting location is %s and facing %s", kiva.getCurrentLocation(), kiva.getDirectionFacing()));
        KivaCommand[] commands = convertToKivaCommands(directions);
        for (int i = 0; i < commands.length; i++) {
            kiva.move(commands[i]);
        }
        if (commands[commands.length - 1] != KivaCommand.DROP) {
            System.out.println("I'm sorry. The Kiva Robot did not pick up the pod and then drop it off in the right place.");
        }
        if (kiva.isSuccessfullyDropped() == true && commands[commands.length - 1] == KivaCommand.DROP) {
            System.out.println("Successfully picked up the pod and dropped it off. Thank you!");
        }
        System.out.printf("Lifetime of Kiva Robot is: %s milliseconds ", kiva.getMotorLifetime());
    }
}
