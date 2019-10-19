package fracCalc;

import java.util.Scanner;

public class FracCalc {

    public static Scanner console = new Scanner(System.in);
    public static String operation = "";

    public static void main(String[] args) 
    {
        // TODO: Read the input from the user and call produceAnswer with an equation
        while (!operation.equals("quit")) {
            System.out.println("Input your operation: ");
            operation = console.nextLine().toLowerCase();
        }

    }
    
    // ** IMPORTANT ** DO NOT DELETE THIS FUNCTION.  This function will be used to test your code
    // This function takes a String 'input' and produces the result
    //
    // input is a fraction string that needs to be evaluated.  For your program, this will be the user input.
    //      e.g. input ==> "1/2 + 3/4"
    //        
    // The function should return the result of the fraction after it has been calculated
    //      e.g. return ==> "1_1/4"
    public static String produceAnswer(String input)
    { 
        // TODO: Implement this function to produce the solution to the input
        String[] sections = input.split(" ");
        String number1 = sections[0];
        String operator = sections[1];
        String number2 = sections[2];

        String[] fractionParts = findFraction(number2);

        return "whole:" + fractionParts[0] + " numerator:" + fractionParts[1] + " denominator:" + fractionParts[2];
    }

    // TODO: Fill in the space below with any helper methods that you think you will need
    public static String[] findFraction(String fraction) {
        // returns the whole number, numerator, and denominator
        String wholeNumber; String numerator; String denominator;
        // whole number section
        // error: doesn't work when it's only a whole numer
        // FIXME: fix it!
        int wholeNumberIndex = fraction.indexOf("_");
        wholeNumber = wholeNumberIndex != -1 ? fraction.substring(0, wholeNumberIndex) : "0";
        // numerator and denominator section
        int numeratorStartIndex = fraction.indexOf("_");
        if (numeratorStartIndex != -1) {
            int numeratorEndIndex = fraction.indexOf("/");
            numerator = fraction.substring(numeratorStartIndex+1, numeratorEndIndex);
            denominator = fraction.substring(numeratorEndIndex+1);
        } else {
            numerator = "0";
            denominator = "1";
        }
        String[] output = {wholeNumber, numerator, denominator};
        return output;
    }

    public static String evaulation(String[] num1, String operator, String[] num2) {
      // the output of the program 
      String output = "";
      // figures out operator 
      if (operator.equals("+")) {
          // adds the numbers
          if (num2[2].equal(num1[2])) {
              
          }
      } else if (operator.equals("-")) {

      } else if (operator.equals("*")) {

      } else {

      }
    }

    public static String greatestCommonMultiple(String[] num1, String[] num2) {
        // check the mod of each number
        // if not zero, cry... and multiply each denom and numer by opposing denom
        // else celebrate and multiply the zero mod with the division of the two numbers
        return "";
      
    }

    public static String addWhole(String[] num1, String[] num2) {
      if (!num1[0].equals("0") && !num2[0].equals("0")) {
          return Integer.toString(Integer.parseInt(num1[0]) + Integer.parseInt(num2[0])) + "_";
      } else if (!num1[0].equals("0")) {
        return num1[0] + "_";
      } else if (!num2[0].equals("0")) {
        return num2[0] + "_";
      }
      return "";
    }
}
