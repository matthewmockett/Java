import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * To begin with designing the program, I found reading the assignment multiple times, taking notes and fully understanding what I had to achieve was tremendous help in completing this assignment.
 * Furthermore, I began with writing a rough algorithm of what each class, PathFinder and DLStack needed to do and what methods I needed to implement. The overall goal of the PathFinder class was to navigate through the chambers
 * from the entrance to the treasure whilst maintaining certain conditions based on the type of chamber. The DLStack class uses a double linked list and allows for access and manipulation of the elements in the stack. One challenge whilst coding I came
 * across was implementing the pop() method in the DLStack class. I repeatedly failed the test case and realized it was my syntax in the if else and for loop statements. Therefore, the logic of the wasn't something I struggled to hard to understand, it was
 * the syntax which gave me difficulty. As I had already a concrete plan before coding, the logic wasn't something I was trying to figure out while coding as I could look at my notes that I hard wrote algorithmically. To test my code, I used the provided
 * test cases and made my own, but using print statements to see where my code went wrong or where it made it to was the most useful tool for debugging. Lastly, the PathFinder class allowed me to navigate properly through the chambers appropriately and
 * the DLStack class provided me with methods to keep track of the path whilst moving towards the treasure and chambers. 
 *  
 */



/**
 * PathFinder class is used for finding a way through the path of chambers
 * Uses a DLStack to keep track of the pathway
 */
public class PathFinder {
    private Map pyramidMap; // Reference to the object of the Map class that represents the chambers of the park

    /**
     * initializes the pyramidMap by reading the map data from the specified file
     * @param fileName Name of the file containing the map data.
     */
    public PathFinder(String fileName) {
        try {
            this.pyramidMap = new Map(fileName);
        } catch (InvalidMapCharacterException e) {
            System.err.println("Invalid character found in the map file: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error while reading the map file: " + e.getMessage());
        }
    }

    /**Finds and returns the path through the chambers of the park using a DLStack
     *
     * @return A DLStack containing the path through the chambers
     */
    public DLStack<Chamber> path() {
        DLStack<Chamber> path = new DLStack<>();
        int numTreasures = pyramidMap.getNumTreasures();
        Chamber entranceChamber = pyramidMap.getEntrance();
        path.push(entranceChamber);
        entranceChamber.markPushed();
        Chamber chamber, neighbourChamber;
        int treasureChamberFoundSoFar = 0;
        while (!path.isEmpty()) {
            chamber = path.peek();
            if (chamber.isTreasure())
                treasureChamberFoundSoFar++;
            if (chamber.isTreasure() && treasureChamberFoundSoFar == numTreasures)
                break;
            neighbourChamber = this.bestChamber(chamber);
            if (neighbourChamber != null) {
                path.push(neighbourChamber);
                neighbourChamber.markPushed();
            } else
                path.pop().markPopped();
        }
        return path;
    }

    /**
     * Returns the map representing the chambers of the park
     * @return The map object
     */
    public Map getMap() {
        return this.pyramidMap;
    }

    /**
     * Checks if the given chamber is dim, not sealed, not lighted and has at least one lighted neighbor.
     *
     * @param currentChamber The chamber to check
     * @return True if the chamber is dim, false otherwise
     */
    public boolean isDim(Chamber currentChamber) {
        if (currentChamber != null) {
            boolean oneNeighbourLighted = false;
            for (int i = 0; i <= 5; i++) {
                Chamber neighbor = currentChamber.getNeighbour(i);
                if (neighbor != null && neighbor.isLighted()) {
                    oneNeighbourLighted = true;
                    break;
                }
            }
            return !currentChamber.isSealed() && !currentChamber.isLighted() && oneNeighbourLighted;
        }
        return false;
    }

    /**
     * Finds the most appropriate neighboring chamber to move to from the given chamber based on the conditions
     * @param currentChamber The current chamber
     * @return The best neighboring chamber, null if none are found
     */
    public Chamber bestChamber(Chamber currentChamber) {
        int index;
        Chamber neighbourChamber;
        for (index = 0; index <= 5; index++) {
            neighbourChamber = currentChamber.getNeighbour(index);
            if (neighbourChamber != null && !neighbourChamber.isMarked() && neighbourChamber.isTreasure())
                return neighbourChamber;
        }
        for (index = 0; index <= 5; index++) {
            neighbourChamber = currentChamber.getNeighbour(index);
            if (neighbourChamber != null && !neighbourChamber.isMarked() && neighbourChamber.isLighted())
                return neighbourChamber;
        }
        for (index = 0; index <= 5; index++) {
            neighbourChamber = currentChamber.getNeighbour(index);
            if (neighbourChamber != null && !neighbourChamber.isMarked() && isDim(neighbourChamber))
                return neighbourChamber;
        }
        return null;
    }
}
