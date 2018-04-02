/*
Header
 */
package assignment5;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

public class Main {

		// launch(args);
		static Scanner kb;	// scanner connected to keyboard input, or input file
		private static String inputFile;	// input file, used instead of keyboard input if specified
		static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
		private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
		private static boolean DEBUG = false; // Use it or not, as you wish!
		static PrintStream old = System.out;	// if you want to restore output to console


		// Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
		static {
			myPackage = Critter.class.getPackage().toString().split(" ")[1];
		}

		/**
		 * Main method.
		 * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
		 * and the second is test (for test output, where all output to be directed to a String), or nothing.
		 */
		public static void main(String[] args) {
			if (args.length != 0) {
				try {
					inputFile = args[0];
					kb = new Scanner(new File(inputFile));
				} catch (FileNotFoundException e) {
					System.out.println("USAGE: java Main OR java Main <input file> <test output>");
					e.printStackTrace();
				} catch (NullPointerException e) {
					System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
				}
				if (args.length >= 2) {
					if (args[1].equals("test")) { // if the word "test" is the second argument to java
						// Create a stream to hold the output
						testOutputString = new ByteArrayOutputStream();
						PrintStream ps = new PrintStream(testOutputString);
						// Save the old System.out.
						old = System.out;
						// Tell Java to use the special stream; all console output will be redirected here from now
						System.setOut(ps);
					}
				}
			} else { // if no arguments to main
				kb = new Scanner(System.in); // use keyboard and console
			}

			boolean quit = false;




			while(!quit){
				// System.out.println("type 'quit', 'show', 'step', 'make' , 'seed', 'stats'");
				String input = kb.nextLine();
				String[] splitInput = input.split("\\s+");
				if(splitInput[0].compareTo("quit") == 0){
					if(splitInput.length !=1){
						System.out.println("error processing: "+input);
						continue;
					}
					quit = true;
					continue;
				}
				if(splitInput[0].compareTo("show")==0){
					if(splitInput.length !=1){
						System.out.println("error processing: "+input);
						continue;
					}
					Critter.displayWorld1();
				}

				else if(splitInput[0].compareTo("step")==0){
					if(splitInput.length !=1 && splitInput.length != 2){
						System.out.println("error processing: "+input);
						continue;
					}
					String count;
					if(splitInput.length ==2) {
						count = splitInput[1];
					}
					else{
						count = "1";
					}
					try {
						int numberCount = Integer.parseInt(count);
						for (int i = 0; i < numberCount; i++) {
							Critter.worldTimeStep();
						}
					} catch (NumberFormatException c) {
						System.out.println("error processing: "+input);
						continue;
					}
				}
				else if(splitInput[0].compareTo("seed")==0){
					if(splitInput.length !=2){
						System.out.println("error processing: "+input);
						continue;
					}
					input = splitInput[1];
					try {
						int numberSeed = Integer.parseInt(input);
						Critter.setSeed(numberSeed);
					}
					catch (NumberFormatException c){
						System.out.println("error processing: "+input);
						continue;
					}
				}
				else if(splitInput[0].compareTo("stats")==0){
					if(splitInput.length !=2){
						System.out.println("error processing: "+input);
						continue;
					}
					input = splitInput[1];
					try {
						List<Critter> instances= Critter.getInstances(splitInput[1]);
						String nameOfFunction = "runStats";
						String critter_class_name = "assignment4." + splitInput[1];
						Class myClass = Class.forName(critter_class_name);
						try {
							Method method = myClass.getMethod(nameOfFunction,List.class);
							try {
								method.invoke(null,instances);
							} catch (IllegalAccessException e) {
							} catch (InvocationTargetException e) {
							}
						} catch (NoSuchMethodException e) {
						}

					}
					catch (NumberFormatException c){
						System.out.println("error processing: "+input);
					}
					catch (InvalidCritterException e) {
						System.out.println("error processing: "+input);

					}
					catch (ClassNotFoundException e) {
						System.out.println("error processing: "+input);
					}
					catch (NoClassDefFoundError e) {
						System.out.println("error processing: "+input);
					}

				}
				else if(splitInput[0].compareTo("make")==0){
					if(splitInput.length !=2 && splitInput.length != 3){
						System.out.println("error processing: "+input);
						continue;
					}
					String count;
					if(splitInput.length ==3) {
						count = splitInput[2];
					}
					else{
						count = "1";
					}
					try {
						int numberCount = Integer.parseInt(count);
						for (int i = 0; i < numberCount; i++) {
							// String critterInputer = "assignment4." +splitInput[1];
							Critter.makeCritter(splitInput[1]);
						}
					} catch (NumberFormatException c) {
						System.out.println("error processing: "+input);
					}
					catch (InvalidCritterException c) {
						System.out.println("error processing: "+input);
						System.out.println("error processing: "+input);
					}
                /*catch (NoClassDefFoundError c) {
                    System.out.println("error processing: "+input);
                }
                */

				}
				else{
					System.out.println("error processing: "+input);
				}


			}
			/* Do not alter the code above for your submission. */
			/* Write your code below. */

			// System.out.println("GLHF");

			/* Write your code above */
			System.out.flush();

		}
	}


