import edu.duke.Point;
/**
 * Write a description of KiveMoveTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KivaMoveTest {
    // Define the FloorMap we'll use for all the tests
    String defaultLayout = ""
                           + "-------------\n"
                           + "        P   *\n"
                           + "   **       *\n"
                           + "   **       *\n"
                           + "  K       D *\n"
                           + " * * * * * **\n"
                           + "-------------\n";

    FloorMap defaultMap = new FloorMap(defaultLayout);

    public void testForwardFromUp() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        Kiva kiva = new Kiva(defaultMap);

        // WHEN
        // We move one space forward
        kiva.move(KivaCommand.FORWARD);
        
        // THEN
        // The Kiva has moved one space up
        verifyKivaState("testForwardFromUp", 
            kiva, new Point(2, 3), FacingDirection.UP, false, false);
    }
    
    public void testForwardWhileFacing() {
        // While facing UP
        Kiva kiva = new Kiva(defaultMap);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        // new location from (2, 4) will be (1, 4)
        // and direction will be LEFT
        verifyKivaState("testForwardWhileFacingLeft", 
            kiva, new Point(1, 4), FacingDirection.LEFT, false, false);
            
        Kiva kiva2 = new Kiva(defaultMap);
        kiva2.move(KivaCommand.TURN_LEFT);
        kiva2.move(KivaCommand.TURN_LEFT);
        kiva2.move(KivaCommand.FORWARD);
        // new location from (2, 4) will be (2, 5)
        // and direction will be DOWN
        System.out.println();
        System.out.println("2x TURN_LEFT");
        verifyKivaState("testForwardWhileFacingLeft", 
            kiva2, new Point(2, 5), FacingDirection.DOWN, false, false);
            
        Kiva kiva3 = new Kiva(defaultMap);
        kiva3.move(KivaCommand.TURN_LEFT);
        kiva3.move(KivaCommand.TURN_LEFT);
        kiva3.move(KivaCommand.TURN_LEFT);
        kiva3.move(KivaCommand.FORWARD);
        // new location from (2, 4) will be (3, 4)
        // and direction will be RIGHT
        System.out.println();
        System.out.println("3x TURN_LEFT");
        verifyKivaState("testForwardWhileFacingLeft", 
            kiva3, new Point(3, 4), FacingDirection.RIGHT, false, false);
    }
    
    // For you: create all the other tests and call verifyKivaState() for each

    public void testTurnLeftFromUp() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        Kiva kiva = new Kiva(defaultMap);

        // WHEN
        // We turn to the left
        kiva.move(KivaCommand.TURN_LEFT);
        
        // THEN
        // The Kiva has turned to face the left direction
        verifyKivaState("testTurnLeftFromUp", 
            kiva, new Point(2, 4), FacingDirection.LEFT, false, false);
            
        kiva.move(KivaCommand.TURN_LEFT);
        verifyKivaState("testTurnLeftFromLeft", 
            kiva, new Point(2, 4), FacingDirection.DOWN, false, false);
            
        kiva.move(KivaCommand.TURN_LEFT);
        verifyKivaState("testTurnLeftFromDown", 
            kiva, new Point(2, 4), FacingDirection.RIGHT, false, false);
            
        kiva.move(KivaCommand.TURN_LEFT);
        verifyKivaState("testTurnLeftFromRight", 
            kiva, new Point(2, 4), FacingDirection.UP, false, false);
    }
    
    public void testTakeOnPod() {
        Kiva kiva = new Kiva(defaultMap);
        
        kiva.move(KivaCommand.FORWARD); // (2, 3)
        kiva.move(KivaCommand.FORWARD); // (2, 2)
        kiva.move(KivaCommand.FORWARD); // (2, 1)
        
        kiva.move(KivaCommand.TURN_RIGHT); // directionFacing = RIGHT
        
        kiva.move(KivaCommand.FORWARD); // (3,1)
        kiva.move(KivaCommand.FORWARD); // (4,1)
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); // (8,1)
        
        kiva.move(KivaCommand.TAKE);
        
        // expected location: (8, 1)
        // expected direction: (RIGHT)
        // expected take: TRUE
        verifyKivaState("testTakeOnPod", 
            kiva, new Point(8, 1), FacingDirection.RIGHT, true, false);
    }
    
    public void testDropOnDropZone() {
        Kiva kiva = new Kiva(defaultMap);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        
        kiva.move(KivaCommand.TURN_RIGHT);
        
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        
        kiva.move(KivaCommand.TAKE); // (8, 1)
        
        // Move to DropZone
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); // (10, 1)
        
        kiva.move(KivaCommand.TURN_RIGHT); // Facing Down
        
        kiva.move(KivaCommand.FORWARD); 
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD); // (10, 4)
        kiva.move(KivaCommand.DROP);
        
        verifyKivaState("testTakeOnPod", 
            kiva, new Point(10, 4), FacingDirection.DOWN, false, true);
        
    }
    
    public void testMoveOutOfBounds() {
        Kiva kiva = new Kiva(defaultMap);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        System.out.println("testMoveOutOfBounds: (expect an IllegalMoveException)");
        kiva.move(KivaCommand.FORWARD);
        
        // This only runs if no exception was thrown
        System.out.println("testMoveOutOfBounds FAIL!");
        System.out.println("Moved outside the FloorMap!");
    }
    
    public void testObjectAtLocation() {
        Kiva kiva = new Kiva(defaultMap);
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
    }
    
    public void testDropZoneObject() {
        Kiva kiva = new Kiva(defaultMap);
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TAKE);
        
        // Test out of bounds UP
        // kiva.move(KivaCommand.FORWARD);
        // kiva.move(KivaCommand.FORWARD);
        // kiva.move(KivaCommand.FORWARD);
        // kiva.move(KivaCommand.FORWARD);
        // kiva.move(KivaCommand.FORWARD);
        // kiva.move(KivaCommand.FORWARD);
        
        // Test that Kiva cannot take POD if already has POD
        //kiva.move(KivaCommand.TAKE);
        
        kiva.move(KivaCommand.TURN_RIGHT);
        
        // Test if the Kiva has a POD, and goes to location: POD
        //kiva.move(KivaCommand.FORWARD);
        //kiva.move(KivaCommand.TURN_LEFT);
        //kiva.move(KivaCommand.TURN_LEFT);
        
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.TURN_RIGHT);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.FORWARD);
        kiva.move(KivaCommand.DROP);
    }
    
    public void testTurnRightFromUp() {
        // GIVEN
        // A Kiva built with the default map we defined earlier
        Kiva kiva = new Kiva(defaultMap);

        // WHEN
        // We turn to the left
        kiva.move(KivaCommand.TURN_RIGHT);
        
        // THEN
        // The Kiva has turned to face the right direction
        verifyKivaState("testTurnRightFromUp", 
            kiva, new Point(2, 4), FacingDirection.RIGHT, false, false);
            
        // expected UP
        kiva.move(KivaCommand.TURN_LEFT); // reset facingDirection to UP.
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        System.out.println("LEFT RIGHT");
        verifyKivaState("testTurnLeftFromLeft", 
            kiva, new Point(2, 4), FacingDirection.UP, false, false);
            
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        System.out.println("LEFT LEFT RIGHT");
        verifyKivaState("testTurnLeftFromDown", 
            kiva, new Point(2, 4), FacingDirection.LEFT, false, false);
            
        kiva.move(KivaCommand.TURN_RIGHT); // reset facingDirection to UP.
        
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_LEFT);
        kiva.move(KivaCommand.TURN_RIGHT);
        System.out.println("LEFT LEFT LEFT RIGHT");
        verifyKivaState("testTurnLeftFromRight", 
            kiva, new Point(2, 4), FacingDirection.DOWN, false, false);
    }
    
    private boolean sameLocation(Point a, Point b) {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }

    private void verifyKivaState(
            String testName,
            Kiva actual,
            Point expectLocation,
            FacingDirection expectDirection,
            boolean expectCarry,
            boolean expectDropped) {

        Point actualLocation = actual.getCurrentLocation();
        if (sameLocation(actualLocation, expectLocation)) {
            System.out.println(
                    String.format("%s: current location SUCCESS", testName));
        }
        else {
            System.out.println(
                    String.format("%s: current location FAIL!", testName));
            System.out.println(
                    String.format("Expected %s, got %s",
                            expectLocation, actualLocation));
        }

        FacingDirection actualDirection = actual.getDirectionFacing();
        if (actualDirection == expectDirection) {
            System.out.println(
                    String.format("%s: facing direction SUCCESS", testName));
        }
        else {
            System.out.println(
                    String.format("%s: facing direction FAIL!", testName));
            System.out.println(
                    String.format("Expected %s, got %s",
                            expectDirection, actualDirection));
        }

        boolean actualCarry = actual.isCarryingPod();
        if (actualCarry == expectCarry) {
            System.out.println(
                    String.format("%s: carrying pod SUCCESS", testName));
        }
        else {
            System.out.println(
                    String.format("%s: carrying pod FAIL!", testName));
            System.out.println(
                    String.format("Expected %s, got %s",
                            expectCarry, actualCarry));
        }

        boolean actualDropped = actual.isSuccessfullyDropped();
        if (actualDropped == expectDropped) {
            System.out.println(
                    String.format("%s: successfully dropped SUCCESS", testName));
        }
        else {
            System.out.println(
                    String.format("%s: successfully dropped FAIL!", testName));
            System.out.println(
                    String.format("Expected %s, got %s",
                            expectDropped, actualDropped));
        }
    }
}
