/* CRITTERS Bevo.java
 * EE422C Assignment4 submission by:
 * Names: Keeshan Patel & Shawn Victor
 * EIDs : kpp484 & sfv225
 * Date : 3/20/2018
 */

package Assignment5;

import Assignment5.Critter.TestCritter;

/**
 * This Critter class: "Critter3" is a unique extension of the TestCritter
 * Unique Cases:
 *      -During a time step it will randomly run in a diagonal direction
 *      -During a fight if it has more than 30 energy it will try to fight, if not it will try to evade
 */

public class Critter3 extends TestCritter {

	/**
	 * This method returns the unique string ID of this critter
	 * @return the unique identifier string for Bevo
	 * Unique Case: The String Identifier for Bevo is "U"
	 */
	@Override
	public String toString () {
		return "3";
	}

	/**
	 * This method will perform a time step for Bevo
	 * Unique Case:
	 *      -Will randomly run in a diagonal direction
	 */
	@Override
	public void doTimeStep() {
		int[] moves = {1,3,6,8};
		run(moves[getRandomInt(3)]);
	}

	public CritterShape viewShape()
	{
		return CritterShape.TRIANGLE;
	}

	/**
	 * This method will indicate whether the critter decides to fight another critter based on the toString of another Critter
	 * @param opponent is a string indicating what type of opponent the critter is going against
	 * @return boolean, if this critter wishes to fight the other critter it will return false, if not it will evade returning false
	 * Unique Case:
	 *      -If its energy is greater than 30 it will fight, else it will try to evade
	 */
	@Override
	public boolean fight(String opponent) {
		if(getEnergy() > 30)
			return true;
		else
			return false;
	}


}
