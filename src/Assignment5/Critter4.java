/* CRITTERS Bobo.java
 * EE422C Assignment4 submission by:
 * Names: Keeshan Patel & Shawn Victor
 * EIDs : kpp484 & sfv225
 * Date : 3/20/2018
 */

package Assignment5;

import java.util.*;

/**
 * This Critter class: "Bobo" is a unique extension of the TestCritter
 * Unique Cases:
 *      -This Critter will move randomly in one direction
 *      -When prompted to fight if it has lower than the amount of energy to reproduce then try to fight, if not it will try to evade
 */

public class Critter4 extends Critter.TestCritter {

	/**
	 * This method returns the unique string ID of this critter
	 * @return the unique identifier string for Bobo
	 * Unique Case: The String Identifier for Bobo is "B"
	 */
	public String toString() {
		return "4";
	}

	/**
	 * This method will perform a time step for Bobo
	 * Unique Case:
	 *      -Will walk in a random direction
	 */
	@Override
	public void doTimeStep()
	{
		int direction = getRandomInt(8);
		walk(direction);
	}

	public CritterShape viewShape()
	{
		return CritterShape.SQUARE;
	}

	/**
	 * This method will indicate whether the critter decides to fight another critter based on the toString of another Critter
	 * @param opponent is a string indicating what type of opponent the critter is going against
	 * @return boolean, if this critter wishes to fight the other critter it will return false, if not it will evade returning false
	 * Unique Case:
	 *      -If the energy of the Critter is less than the minimum needed to reproduce then only will it fight
	 *      -Only if the opponent is another Bobo and has enough energy will it reproduce to make a new Bobo child, and will evade
	 */
	@Override
	public boolean fight(String opponent)
	{
		if(getEnergy() < Params.min_reproduce_energy)
		{
			return true;
		}
		else
		{
			if(opponent.equals("B"))
			{
				Critter4 child = new Critter4();
				reproduce(child, 8);
			}
			walk(getRandomInt(8));
			return false;
		}
	}

	/**
	 * This is just used for testing working ability of this Critter for debugging
	 * @param l is a list of all the critters
	 */
	public void test (List<Critter> l)
	{

	}
}
