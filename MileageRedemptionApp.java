/************************************************************************
CSCI 470 section 1
TA:
Partner 1     Ben Lane
zID:		      z1806979
Partner 2:    Jinhong Yao
zID:		      z178500
Assignment:   1
Date Due:	    2/14/2018

Purpose:      Shows which destinations you can fly to using your air miles; if possible,
              using supersaver miles. Using the remaining miles, it will upgrade farthest
              flights to first class whenever possible.
*************************************************************************/

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.ArrayList;

public class MileageRedemptionApp {
  public static void main(String[] args) {
    boolean skip = false;

    // Check to see if correct number of arguments were passed in.
    if (args.length != 1) {
      System.out.println("Incorrect number of arguments.");
      System.out.println("Please input only a path to a text file.");

      System.exit(1);
    }

    String choice = "Y";        // (y/n) Continue variable

    // While use wants to continue with the program...
    while (choice.equals("Y")) {
      skip = false;

      MilesRedeemer m = new MilesRedeemer();  // MilesRedeemer object

      File file = new File(args[0]);          // File object

      Scanner sc = new Scanner(System.in);    // Scanner object

      int accMiles = 0;     // accumualted miles
      int departMonth = 1;  // month user wants to depart

      // Read file of destinations.
      try {
        Scanner input = new Scanner(file);
        m.readDestinations(input);
      } catch(IOException e) {
          System.out.println("Cannot find the file specified. Exiting.");

          System.exit(1);
        }

      // Get list of city names.
      System.out.println("---------------------------------------------");
      System.out.println("List of destination cities you can travel to:\n");
      String[] cities = m.getCityNames();

      // Print out list of cities.
      for (String s : cities)
        System.out.println(s);

      // Get user's total miles.
      System.out.println("---------------------------------------------\n");
      System.out.print("Please input your total accumulated miles: ");
      try {
        accMiles = sc.nextInt();
      }
      catch (InputMismatchException e) {
        System.out.println("Error: number was not an integer.");

        skip = true;
      }

      if (skip == false) {
        // Get user's requested month of departure.
        System.out.print("\nPlease input your month of departure: ");
        try {
          departMonth = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: number was not an integer.");

            skip = true;
        }
      }

      // Make sure month is within a range of 1 to 12.
      while (departMonth < 1 || departMonth > 12) {
        try {
          System.out.print("Please enter a valid month number (1-12): ");

          departMonth = sc.nextInt();

        } catch (InputMismatchException e) {
            System.out.println("Error: number was not an integer.");

            skip = true;

            departMonth = 1;
        }
      }

      if (skip == false) {
        // Create a list of destinations along with their class-type and remaining miles.
        ArrayList<String> finalOutput = new ArrayList<String>(m.redeemMiles(accMiles, departMonth));

        // Display final list.
        for (String s : finalOutput) {
          System.out.println(s);
        }

        // Display remaining miles.
        System.out.println("\nYour remaining miles: " + m.getRemainingMiles());
      }

      System.out.print("\nDo you want to continue (y/n)? ");

      choice = sc.nextLine();

      choice = sc.nextLine();

      choice = choice.toUpperCase();
    }
  }
}
