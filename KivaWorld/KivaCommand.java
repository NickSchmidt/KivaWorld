
/**
 * Sets the Keyboard characters for Remote Control movement. For example: If user entered "F", the command
 * is FORWARD. If the user entered D, the command is DROP
 * 
 * @author Nicholas Schmidt
 * @version ATA Coding Project due 10/22/2021
 */
public enum KivaCommand{
    FORWARD('F'), 
    TURN_LEFT('L'), 
    TURN_RIGHT('R'), 
    TAKE('T'), 
    DROP('D');
    
    private char abbrev;
    
    private KivaCommand(char abbrev) {
        this.abbrev = abbrev;
    }
    
    /**
     * Converts keyboard input into Kiva Robot language.
     * @return The Keyboard character for intended action.
     */
    public char getDirectionKey() {
        return this.abbrev;
    }
}
