/* CRITTERS Bertha.java
 * EE422C Assignment4 submission by:
 * Names: Keeshan Patel & Shawn Victor
 * EIDs : kpp484 & sfv225
 * Date : 3/20/2018
 */

package Assignment5;

import Assignment5.Critter.TestCritter;

/**
 * This Critter class: "Critter2" is a unique extension of the TestCritter
 * Unique Cases:
 *      -If its energy is below 40 during a fight it will enter berserker mode and will gain 100 energy,
 *      -Bertha will randomly runs in a random direction during a timeStep
 */

public class Critter2 extends TestCritter {

	boolean berserkerMode = false;
	int cooldownTimer = 0;

	/**
	 * This method returns the unique string ID of this critter
	 * @return the unique identifier string for Bertha
	 * Unique Case: The String Identifier for Bertha is "R"
	 */
	@Override
	public String toString () {
		return "2";
	}

	/**
	 * This method will perform a time step for Bertha
	 * Unique Case:
	 *      -Will run in a random direction
	 */
	@Override
	public void doTimeStep() {
		int direction = getRandomInt(8);
		run(direction);
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
	 *      -After every 5 fights if the Critter's energy is less than 40 Bertha will go into Berserker Mode and gain 100 Energy and accept the fight
	 *      -Immediately after the berserk mode has been activate in the next fight Bertha energy goes drastically lowered to 10 gaining 10 energy each subsequent fight evaded
	 */
	@Override
	public boolean fight(String opponent) {
		if(berserkerMode && cooldownTimer == 0)
		{
			setEnergy(10);
			cooldownTimer = 5;
			if(getEnergy() > Params.run_energy_cost)
			{
				run(getRandomInt(8));

			}
			else
			{
				walk(getRandomInt(8));
			}
			setEnergy(getEnergy() + 10);
			return false;
		}

		if(getEnergy() < 40 && cooldownTimer == 0)
		{
			berserkerMode = true;
			setEnergy(getEnergy() + 100);
			return true;
		}
		cooldownTimer--;
		return true;
	}


}