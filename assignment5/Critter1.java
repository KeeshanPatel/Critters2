/* CRITTERS Ashwin.java
 * EE422C Assignment4 submission by:
 * Names: Keeshan Patel & Shawn Victor
 * EIDs : kpp484 & sfv225
 * Date : 3/20/2018
 */

package assignment5;

import assignment5.Critter.TestCritter;

/**
 * This Critter class: "Critter1" is a unique extension of the TestCritter
 * Unique Cases:
 *      -This Critter has a 1/4th chance of moving and will try to sleep on every timeStep to which it gains energy when it is not in a fight
 *      -When prompted to actually fight it will always evade to which it wont be able to sleep and will lose energy drastically
 */

public class Critter1 extends TestCritter {

    /**
     * This method returns the unique string ID of this critter
     * @return the unique identifier string for Ashwin
     * Unique Case: The String Identifier for Ashwin is "A"
     */
    @Override
    public String toString () {
        return "1";
    }

    /**
     * This method will perform a time step for Ashwin
     * Unique Case:
     *      -Has a 1/4 chance of walking in a random direction
     *      -During each time step it will sleep a little to gain energy
     */
    @Override
    public void doTimeStep() {
        int direction = getRandomInt(8);
        if(direction % 4 == 0)
        {
            walk(getRandomInt(8));
        }
        setEnergy(getEnergy() + getRandomInt(10));
    }

    /**
     * This method will indicate whether the critter decides to fight another critter based on the toString of another Critter
     * @param opponent is a string indicating what type of opponent the critter is going against
     * @return boolean, if this critter wishes to fight the other critter it will return false, if not it will evade returning false
     * Unique Case:
     *      -The Ashwin Critter is unable to sleep and will lose 45 energy
     *      -Will always try to evade
     */
    @Override
    public boolean fight(String opponent) {
        setEnergy(getEnergy() - getRandomInt(45));
        return false;
    }


}
