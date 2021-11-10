import edu.duke.Point;

/**
 * This is the <b>Kiva Robot!</b> Until this Robot becomes automated, humans are still in control.
 * 
 * This Kiva Robot contains a bunch of knowledge such as:
 * <p>#currentLocation -- Gives the current location of the Kiva Robot as a point on a FloorMap (x, y).</p>
 * <p>#directionFacing -- Gives the direction the Kiva Robot is facing (Up, Down, Left, Right).</p>
 * <p>#map -- Is a FloorMap representation of the Kiva Robot's world. Includes OBSTACLES, POD, DROP_ZONE,
 *         Kiva location, and boundaries of the FloorMap.</p>
 * <p>#carryingPod -- True if the Kiva Robot is carrying a pod. Otherwise, false.</p>
 * <p>#successfullyDropped -- True if the Kiva Robot successfully dropped a pod at a DROP_ZONE location.</p>
 * <p>#motorLifetime -- How long until this Robot dies :). Life expectancy is at 20,000 hours, but configured
 *                   as milliseconds. (Obviously for information purposes and not to make me convert
 *                   milliseconds to hours).</p>
 *                    
 * @author Nicholas Schmidt
 * @version ATA Coding Project due 10/22/2021
 */
public class Kiva {
    private Point currentLocation;
    private FacingDirection directionFacing;
    private FloorMap map;
    private boolean carryingPod;
    private boolean successfullyDropped;
    private long motorLifetime;
    
    /**
     * Creates a new Kiva Robot
     * 
     * @param map Floormap. Creates a map for the Kiva Robot with 
     *            the current location of the Kiva Robot at Point "K" on the map.
     */
    public Kiva (FloorMap map) {
        this.map = map;
        this.currentLocation = map.getInitialKivaLocation();
        //System.out.println("Starting location: " + this.currentLocation);
        this.directionFacing = FacingDirection.UP;
        this.carryingPod = false;
        this.successfullyDropped = false;
        this.motorLifetime = 0;
    }
    
    /**
     * Creates a Kiva Robot with user-defined location
     * 
     * @param map FloorMap. Creates a home for the Kiva Robot with
     *            the location of the Kiva Robot at God's (You... You're God. Not the robot)
     *            location.
     * @param currentLocation Point. User-defined location for the Kiva Robot
     */
    
    public Kiva (FloorMap map, Point currentLocation) {
        this.map = map;
        this.currentLocation = currentLocation;
        this.directionFacing = FacingDirection.UP;
        this.carryingPod = false;
        this.successfullyDropped = false;
        this.motorLifetime = 0;
    }
    
    /**
     * Checks if the robot has a pod - initialized to false
     * @return True if carrying pod. False if not carrying pod
     */
    public boolean isCarryingPod() {
        return this.carryingPod;
    }
    
    /**
     * Checks if the robot can drop a pod - initialized to false
     * @return True if drop is successful. False if drop is not successful
     */
    public boolean isSuccessfullyDropped() {
        return this.successfullyDropped;
    }
    
    /**
     * Gets the lifetime of the Robot's motor in milliseconds
     * @return the lifetime of the Robot's motor in milliseconds
     */
    public long getMotorLifetime() {
        return this.motorLifetime;
    }
    
    /**
     * Increments the amount of Robot's motor life by 1000ms
     */
    public void incrementMotorLifetime() {
        this.motorLifetime = this.motorLifetime + 1000;
    }
    
    /**
     * Gets the current location as (x, y)
     * @return the current location of the robot
     */
    public Point getCurrentLocation() {
        return this.currentLocation;
    }
    
    /**
     * Gets the direction the Robot is facing
     * @return the direction the Robot is facing
     */
    public FacingDirection getDirectionFacing() {
        return this.directionFacing;
    }
    
    /** 
     * Check if the next move is legal
     * 
     * <p>This method identifies the next move for the Kiva Robot and checks if the move is LEGAL.</p>
     * 
     * @param nextLocation nextLocation is created based on the move() method.
     *                     For example: if move() FORWARD facing direction UP.
     *                     The nextLocation is (currentLocation.x, currentLocation.y - 1).
     * @return Nothing. It's just a check...                    
     * @exception java.lang.IllegalMoveException on illegal moves
     */
    public void moveExceptionHelper(Point nextLocation) {
        FloorMapObject terrain = map.getObjectAtLocation(nextLocation);
        //System.out.println(nextLocation + " " + terrain);
        if (terrain == FloorMapObject.POD && isCarryingPod()) {
            throw new IllegalMoveException(String.format(
                                            "Tried to move to location: %s%s while carrying pod = %s",
                                            this.currentLocation, terrain, isCarryingPod()));
        }
        
        if (terrain == FloorMapObject.OBSTACLE) {
            System.out.println(this.currentLocation);
            throw new IllegalMoveException(String.format("%s is in the way at next location %s", 
                                            terrain, nextLocation));
        }
    }
    
    /**
     * Checks if you're trying to dispose of the Kiva Robot by plummetting it outside of it's home world
     * 
     * @param nextLocation Point
     * @return Nothing. Just checking how devious you are.
     */
    public void checkOutOfBounds(Point nextLocation) {
        if (nextLocation.getX() > map.getMaxColNum()) {
            throw new IllegalMoveException(String.format("Next move %s results in out of bounds moving: %s",
                                            nextLocation, this.directionFacing));
        }
        else if (nextLocation.getY() > map.getMaxRowNum()) {
            throw new IllegalMoveException(String.format("Next move %s results in out of bounds moving: %s",
                                            nextLocation, this.directionFacing));
        }
        else if (nextLocation.getX() == 0 && this.directionFacing == FacingDirection.LEFT) {
            throw new IllegalMoveException(String.format("Next move %s results in out of bounds moving: %s",
                                            nextLocation, this.directionFacing));
        }
        else if (nextLocation.getY() == 0 && this.directionFacing == FacingDirection.UP) {
            throw new IllegalMoveException(String.format("Next move %s results in out of bounds moving: %s",
                                            nextLocation, this.directionFacing));
        }
    }
    
    /**
     * Moves the Kiva Robot 1 coordinate plane direction at a time (x or y)
     * 
     * <p>This method first checks if the Kiva Robot can legally move forward.
     * Then, if legal, sets the "currentLocation" to the "nextLocation."
     * And finally, updates "directionFacing." Although, after looking at this... 
     * I don't think that's necessary.</p>
     * 
     * @see move()
     * @return Nothing.
     */
    public void moveForward() {
        int x = this.currentLocation.getX();
        int y = this.currentLocation.getY();
        if (this.directionFacing == FacingDirection.UP) {
            Point nextLocation = new Point(x, y - 1); // next move location
            checkOutOfBounds(nextLocation); // check out of bounds
            moveExceptionHelper(nextLocation); // check for obstacle at next location
            this.currentLocation = new Point(x, y - 1); // Successful move
            this.directionFacing = FacingDirection.UP; // Update directionFacing
        }
        else if (this.directionFacing == FacingDirection.LEFT) {
            Point nextLocation = new Point(x - 1, y);
            checkOutOfBounds(nextLocation);
            moveExceptionHelper(nextLocation);
            this.currentLocation = new Point(x - 1, y);
            this.directionFacing = FacingDirection.LEFT;
        }
        else if (this.directionFacing == FacingDirection.DOWN) {
            Point nextLocation = new Point(x, y + 1);
            checkOutOfBounds(nextLocation);
            moveExceptionHelper(nextLocation);
            this.currentLocation = new Point(x, y + 1);
            this.directionFacing = FacingDirection.DOWN;
        }
        else if (this.directionFacing == FacingDirection.RIGHT) {
            Point nextLocation = new Point(x + 1, y);
            checkOutOfBounds(nextLocation);
            moveExceptionHelper(nextLocation);
            this.currentLocation = new Point(x + 1, y);
            this.directionFacing = FacingDirection.RIGHT;
        }
        isCarryingPod();
        isSuccessfullyDropped();
    }
    
    /**
     * Turns the direction of the Kiva Robot left
     */
    public void turnLeft() {
        this.currentLocation = getCurrentLocation();
        if (this.directionFacing == FacingDirection.UP) {
            this.directionFacing = FacingDirection.LEFT;
        }
        else if (this.directionFacing == FacingDirection.LEFT) {
            this.directionFacing = FacingDirection.DOWN;
        }
        else if (this.directionFacing == FacingDirection.DOWN) {
            this.directionFacing = FacingDirection.RIGHT;
        }
        else if (this.directionFacing == FacingDirection.RIGHT) {
            this.directionFacing = FacingDirection.UP;
        }
        isCarryingPod();
        isSuccessfullyDropped();
    }
    
    /** 
     * Turns the direction of the Kiva Robot right
     */
    public void turnRight() {
        this.currentLocation = getCurrentLocation();
        if (this.directionFacing == FacingDirection.UP) {
            this.directionFacing = FacingDirection.RIGHT;
        }
        else if (this.directionFacing == FacingDirection.RIGHT) {
            this.directionFacing = FacingDirection.DOWN;
        }
        else if (this.directionFacing == FacingDirection.DOWN) {
            this.directionFacing = FacingDirection.LEFT;
        }
        else if (this.directionFacing == FacingDirection.LEFT) {
            this.directionFacing = FacingDirection.UP;
        }
        isCarryingPod();
        isSuccessfullyDropped();
    }
    
    /**
     *  Takes pod if current location is POD
     * 
     * <p>If the FloorMapObject is a POD at the Kiva Robot's currentLocation, the Kiva Robot can TAKE.
     * Otherwise, noPodException is thrown.</p>
     * 
     * <p>Also checks if the FloorMapObject is a POD and if the Kiva Robot is already carrying a pod. Kiva Robot
     * cannot carry more than 1 POD</p>
     * 
     * @exception NoPodException on not a POD location.
     * @exception IllegalMoveException on already carrying a POD
     * 
     * @return Nothing.
     */
    public void takePod() {
        FloorMapObject terrain = map.getObjectAtLocation(this.currentLocation);
        if (terrain != FloorMapObject.POD) {
            throw new NoPodException(String.format("Cannot take nonexistent pod from this location %s", 
                                                    this.currentLocation));
        }
        else if (terrain == FloorMapObject.POD && isCarryingPod()) {
            throw new IllegalMoveException(String.format("Already carrying pod = %s. Cannot take another",
                                                            isCarryingPod()));
        }
        System.out.println("Successfully picked up POD at " + this.currentLocation);
        this.carryingPod = true;
        this.successfullyDropped = false;
    }
    
    /**
     * Drops pod if current location is DROP_ZONE
     * 
     * <p>Also checks if the Kiva Robot is carrying a POD. Cannot drop a POD if no POD exists.</p>
     * 
     * @exception IllegalDropZoneException on not a DROP_ZONE
     * 
     * 
     * @exception IllegalMoveException on move(drop) if Kiva Robot has no POD
     * @return Nothing.
     */
    public void dropPod() {
        FloorMapObject terrain = map.getObjectAtLocation(this.currentLocation);
        if (terrain != FloorMapObject.DROP_ZONE) {
            throw new IllegalDropZoneException("This is not a DROP_ZONE -- location " + this.currentLocation);
        }
        else if (terrain == FloorMapObject.DROP_ZONE && !isCarryingPod()) {
            throw new IllegalMoveException(String.format("isCarryingPod = %s. Cannot drop nothing", 
                                                            isCarryingPod()));
        }
        System.out.println("Successfully dropped POD at " + this.currentLocation);
        this.carryingPod = false;
        this.successfullyDropped = true;
    }
    
    /**
     * Controls the movement of the Kiva Robot based on the user input.
     * 
     * @param command KivaCommand. Is the user's intention for the Kiva Robot. FORWARD, TURN_LEFT, TURN_RIGHT, TAKE, DROP
     * 
     * @return Nothing.
     */
    public void move(KivaCommand command) {
        if (command == KivaCommand.FORWARD) {
            moveForward();
            incrementMotorLifetime();
        }
        else if (command == KivaCommand.TURN_LEFT) {
            turnLeft();
            incrementMotorLifetime();
        }
        else if (command == KivaCommand.TURN_RIGHT) {
            turnRight();
            incrementMotorLifetime();
        }
        else if (command == KivaCommand.TAKE) {
            takePod();
        }
        else if (command == KivaCommand.DROP) {
            dropPod();
        }
    }
}
