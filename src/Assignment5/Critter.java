package Assignment5;

import java.util.Iterator;
import java.util.List;

import javafx.scene.image.*;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.layout.*;

import javafx.scene.text.*;

import javafx.scene.control.*;

public abstract class Critter {
    /* NEW FOR PROJECT 5 */

    /* the default color is white, which I hope makes critters invisible by default
     * If you change the background color of your View component, then update the default
     * color to be the same as you background
     *
     * critters must override at least one of the following three methods, it is not
     * proper for critters to remain invisible in the view
     *
     * If a critter only overrides the outline color, then it will look like a non-filled
     * shape, at least, that's the intent. You can edit these default methods however you
     * need to, but please preserve that intent as you implement them.
     */
    public javafx.scene.paint.Color viewColor() {
        return javafx.scene.paint.Color.WHITE;
    }

    public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
    public javafx.scene.paint.Color viewFillColor() { return viewColor(); }

    public abstract CritterShape viewShape();

    private static String myPackage;
    private	static List<Critter> population = new java.util.ArrayList<Critter>();
    private static List<Critter> babies = new java.util.ArrayList<Critter>();

    // Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    protected final String look(int direction, boolean steps) {return "";}

	/* rest is unchanged from Project 4 */


    private static java.util.Random rand = new java.util.Random();
    public static int getRandomInt(int max) {
        return rand.nextInt(max);
    }

    public static void setSeed(long new_seed) {
        rand = new java.util.Random(new_seed);
    }

    /* a one-character long string that visually depicts your critter in the ASCII interface */
    public String toString() { return ""; }

    private int energy = 0;
    protected int getEnergy() { return energy; }

    private int x_coord;
    private int y_coord;

    protected final void walk(int direction) {
        this.energy -= Params.walk_energy_cost;
        switch (direction) {
            case 0:
                this.x_coord = (x_coord+1) % Params.world_width;
                break;
            case 1:
                this.x_coord = (x_coord+1) % Params.world_width;
                this.y_coord = (y_coord-1) % Params.world_height;
                if(y_coord<0) {
                    y_coord += Params.world_height;
                }
                break;
            case 2:
                this.y_coord = (y_coord-1) % Params.world_height;
                if(y_coord<0) {
                    y_coord += Params.world_height;
                }
                break;
            case 3:
                this.y_coord = (y_coord-1) % Params.world_height;
                if(y_coord<0) {
                    y_coord += Params.world_height;
                }
                this.x_coord = (x_coord-1) % Params.world_width;
                if(x_coord<0) {
                    x_coord += Params.world_width;
                }
                break;
            case 4:
                this.x_coord = (x_coord-1) % Params.world_width;
                if(x_coord<0) {
                    x_coord += Params.world_width;
                }
                break;
            case 5:
                this.x_coord = (x_coord-1) % Params.world_width;
                if(x_coord<0) {
                    x_coord += Params.world_width;
                }
                this.y_coord = (y_coord+1) % Params.world_height;
                break;
            case 6:
                this.y_coord = (y_coord+1) % Params.world_height;
                this.y_coord = (y_coord+1) % Params.world_height;
                break;
            case 7:
                this.x_coord = (x_coord+1) % Params.world_width;
                this.y_coord = (y_coord+1) % Params.world_height;
                break;
        }
    }

    protected final void run(int direction) {
        this.energy += (2*Params.walk_energy_cost);
        walk(direction);
        walk(direction);
        this.energy -= Params.run_energy_cost;
    }

    protected final void reproduce(Critter offspring, int direction) {
        if(getEnergy() < Params.min_reproduce_energy)
        {
            return;
        }

        offspring.energy = getEnergy() / 2;
        energy = energy/2;

        switch(direction)
        {
            case 0:
                offspring.x_coord = (x_coord+1) % Params.world_width;
                break;
            case 1:
                offspring.x_coord = (x_coord+1) % Params.world_width;
                offspring.y_coord = (y_coord-1) % Params.world_height;
                if(y_coord < 0) {
                    offspring.y_coord += Params.world_height;
                }
                break;
            case 2:
                offspring.y_coord = (y_coord-1) % Params.world_height;
                if(y_coord<0) {
                    offspring.y_coord += Params.world_height;
                }
                break;
            case 3:
                offspring.y_coord = (y_coord-1) % Params.world_height;
                offspring.x_coord = (x_coord-1) % Params.world_width;
                if(y_coord<0){
                    offspring.y_coord += Params.world_height;
                }
                if(x_coord<0) {
                    offspring.x_coord += Params.world_width;
                }
                break;
            case 4:
                offspring.x_coord = (x_coord-1) % Params.world_width;
                if(x_coord<0) {
                    offspring.x_coord += Params.world_width;
                }
                break;
            case 5:
                offspring.x_coord = (x_coord-1) % Params.world_width;
                offspring.y_coord = (y_coord+1) % Params.world_height;
                if(x_coord<0) {
                    offspring.x_coord += Params.world_width;
                }
                break;
            case 6:
                offspring.y_coord = (y_coord+1) % Params.world_height;
                break;
            case 7:
                offspring.x_coord = (x_coord+1) % Params.world_width;
                offspring.y_coord = (y_coord+1) % Params.world_height;
                break;
        }
        babies.add(offspring);
    }

    public abstract void doTimeStep();
    public abstract boolean fight(String oponent);


    public static void worldTimeStep() {
        for (Critter c : population) {
            c.doTimeStep();
        }
        checkDead();
        for (int i = 0; i < population.size(); i++) {
            for (int j = 0; j < population.size(); j++) {
                if (i == j) {
                    continue;
                }
                Critter firstCritter = population.get(i);
                Critter secondCritter = population.get(j);
                if ((firstCritter.x_coord == secondCritter.x_coord) && (firstCritter.y_coord == secondCritter.y_coord)) {

                    int oldXCoordFirst = firstCritter.x_coord;
                    int oldYCoordFirst = firstCritter.y_coord;
                    boolean firstWantFight = firstCritter.fight(secondCritter.toString());
                    if (!firstWantFight) {
                        if (firstCritter.getEnergy() < 0) {
                            population.remove(firstCritter);
                            //System.out.println("Critter rans away exhausted");

                            continue;
                        }
                        for (Critter c : population) {
                            if (c == firstCritter) {
                                continue;
                            }
                            if ((firstCritter.x_coord == c.x_coord) && (firstCritter.y_coord == c.y_coord)) {
                                firstCritter.x_coord = oldXCoordFirst;
                                firstCritter.y_coord = oldYCoordFirst;
                                break;
                            }
                        }
                    }

                    int oldXCoordSecond = secondCritter.x_coord;
                    int oldYCoordSecond = secondCritter.y_coord;
                    boolean secondWantFight = secondCritter.fight(firstCritter.toString());
                    if (!secondWantFight) {
                        if (secondCritter.getEnergy() < 0) {
                            population.remove(secondCritter);
                            //System.out.println("Critter rans away exhausted");

                            continue;
                        }
                        for (Critter c : population) {
                            if (c == secondCritter) {
                                continue;
                            }
                            if ((c.x_coord == secondCritter.x_coord) && (c.y_coord == secondCritter.y_coord)) {
                                secondCritter.x_coord = oldXCoordSecond;
                                secondCritter.y_coord = oldYCoordSecond;
                                break;
                            }
                        }
                    }
                    checkDead();
                    if(population.contains(firstCritter) && population.contains(secondCritter)&&((firstCritter.x_coord == secondCritter.x_coord) && (firstCritter.y_coord == secondCritter.y_coord))){
                        Critter winner;
                        Critter loser;
                        int firstRoll =0;
                        int secondRoll =0;

                        if(firstWantFight){
                            firstRoll = getRandomInt(firstCritter.energy);
                        }
                        if(secondWantFight){
                            secondRoll = getRandomInt(secondCritter.energy);
                        }

                        if(firstRoll == secondRoll){
                            winner = firstCritter;
                            loser = secondCritter;
                        }
                        else if(firstRoll>secondRoll){
                            winner = firstCritter;
                            loser = secondCritter;
                        }
                        else{
                            loser = firstCritter;
                            winner = secondCritter;
                        }
                        winner.energy += (loser.energy/2);
                        //System.out.println("Critter killed");

                        population.remove(loser);
                        if(loser == firstCritter){
                            i--;
                            break;
                        }
                    }

                }
            }
        }
        for (Critter c : population) {
            c.energy -= Params.rest_energy_cost;
        }
        for(int i =0; i < Params.refresh_algae_count;i++){
            try{
                makeCritter("Algae");
            }
            catch (InvalidCritterException e){
                System.out.println("error processing: "+ e.offending_class);

            }
        }
        checkDead();
        population.addAll(babies);
        babies.clear();
    }

    public static void displayWorld1() {
        char[][] worldVeiw = new char[Params.world_width + 2][Params.world_height + 2];

        worldVeiw[0][0] = '+';
        worldVeiw[Params.world_width + 1][Params.world_height + 1] = '+';
        worldVeiw[0][Params.world_height + 1] = '+';
        worldVeiw[Params.world_width + 1][0] = '+';

        for (int i = 1; i < (Params.world_height + 1); i++) {
            worldVeiw[0][i] = '|';
            worldVeiw[Params.world_width + 1][i] = '|';
        }
        for (int i = 1; i < (Params.world_width + 1); i++) {
            worldVeiw[i][0] = '-';
            worldVeiw[i][Params.world_height+1] = '-';
        }
        for(int i = 1; i < (Params.world_width + 1); i++){
            for(int j = 1; j < (Params.world_height+1);j++){
                worldVeiw[i][j]= ' ';
            }
        }

        for (Critter c : population) {
            worldVeiw[c.x_coord + 1][c.y_coord + 1] = c.toString().charAt(0);
        }

        for (int i = 0; i < (Params.world_height + 2); i++) {
            for (int j = 0; j < (Params.world_width + 2); j++) {
                System.out.print(worldVeiw[j][i]);
            }
            System.out.println();
        }

    }
    /* Alternate displayWorld, where you use Main.<pane> to reach into your
       display component.
    */
    public static Group displayWorld() {

        Group critterGroup = new Group();

        for(Critter c : population)
        {
            Piece p = new Piece(c.viewShape(), c.x_coord, c.y_coord);

            critterGroup.getChildren().add(p);
        }

        return critterGroup;

    }


    /* create and initialize a Critter subclass
     * critter_class_name must be the name of a concrete subclass of Critter, if not
     * an InvalidCritterException must be thrown
     */
    public static void makeCritter(String critter_class_name) throws InvalidCritterException {
        try
        {
            critter_class_name = "Assignment5." +critter_class_name;

            Class c = Class.forName(critter_class_name);
            Critter newCritter = (Critter) c.newInstance();
            newCritter.energy = Params.start_energy;
            newCritter.x_coord = getRandomInt(Params.world_width);
            newCritter.y_coord = getRandomInt(Params.world_height);
            population.add(newCritter);
        }
        catch(Exception c){
            throw new InvalidCritterException(c.toString());
        }
        catch(NoClassDefFoundError c){
            throw new InvalidCritterException(c.toString());
        }
    }

    public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
        List<Critter> result = new java.util.ArrayList<Critter>();
        try
        {
            critter_class_name = "Assignment5." +critter_class_name;
            Class c = Class.forName(critter_class_name);

            for(Critter source: population)
            {
                if(c.isInstance(source))
                {
                    result.add(source);
                }
            }
        }
        catch(Exception ex)
        {
            throw new InvalidCritterException(ex.toString());
        }
        return result;
    }

    public static void runStats(List<Critter> critters) {
        System.out.print("" + critters.size() + " critters as follows -- ");
        java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
        for (Critter crit : critters) {
            String crit_string = crit.toString();
            Integer old_count = critter_count.get(crit_string);
            if (old_count == null) {
                critter_count.put(crit_string,  1);
            } else {
                critter_count.put(crit_string, old_count.intValue() + 1);
            }
        }
        String prefix = "";
        for (String s : critter_count.keySet()) {
            System.out.print(prefix + s + ":" + critter_count.get(s));
            prefix = ", ";
        }
        System.out.println();
    }

    /* the TestCritter class allows some critters to "cheat". If you want to
     * create tests of your Critter model, you can create subclasses of this class
     * and then use the setter functions contained here.
     *
     * NOTE: you must make sure thath the setter functions work with your implementation
     * of Critter. That means, if you're recording the positions of your critters
     * using some sort of external grid or some other data structure in addition
     * to the x_coord and y_coord functions, then you MUST update these setter functions
     * so that they correctup update your grid/data structure.
     */
    static abstract class TestCritter extends Critter {

        protected void setEnergy(int new_energy_value) {
            super.energy = new_energy_value;
        }

        protected void setX_coord(int new_x_coord) {
            super.x_coord = new_x_coord;
        }

        protected void setY_coord(int new_y_coord) {
            super.y_coord = new_y_coord;
        }

        protected int getX_coord() {
            return super.x_coord;
        }

        protected int getY_coord() {
            return super.y_coord;
        }


        /*
         * This method getPopulation has to be modified by you if you are not using the population
         * ArrayList that has been provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
         */
        protected static List<Critter> getPopulation() {
            return population;
        }

        /*
         * This method getBabies has to be modified by you if you are not using the babies
         * ArrayList that has been provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.  Babies should be added to the general population
         * at either the beginning OR the end of every timestep.
         */
        protected static List<Critter> getBabies() {
            return babies;
        }
    }

    /**
     * Clear the world of all critters, dead and alive
     */
    public static void clearWorld() {
        population.clear();
        babies.clear();
    }
    /**
     * This method will go though the population list and will remove the Citters with less than or equal to zero energy
     */
    public static void checkDead(){
        for (Iterator<Critter> iterator = population.iterator(); iterator.hasNext();) {
            Critter c = iterator.next();
            if(c.getEnergy() <= 0){
                iterator.remove();
                //System.out.println("Critter exhausted");
            }
        }
    }
}
