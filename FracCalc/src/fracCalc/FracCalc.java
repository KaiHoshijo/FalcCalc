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
            if (!operation.equals("quit")) {
                System.out.println(produceAnswer(operation));
            }
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

        // splits the numbers into whole numbers, numerator, and denominator
        String[] fractionParts0 = findFraction(number1);
        String[] fractionParts1 = findFraction(number2);

        
        return "whole:" + fractionParts1[0] + " numerator:" + fractionParts1[1] + " denominator:" + fractionParts1[2];
    }

    // TODO: Fill in the space below with any helper methods that you think you will need
    public static String[] findFraction(String fraction) {
        // returns the whole number, numerator, and denominator
        String wholeNumber; String numerator; String denominator;
        // whole number section
        int wholeNumberIndex = fraction.indexOf("_");
        if (wholeNumberIndex != -1) {
          wholeNumber = fraction.substring(0, wholeNumberIndex);
        } else if (fraction.indexOf("/") == -1) {
          wholeNumber = fraction;
        } else {
          wholeNumber = "0";
        }
        // numerator and denominator section
        int numeratorStartIndex = fraction.indexOf("_");
        int numeratorEndIndex = fraction.indexOf("/");
        if (numeratorStartIndex != -1) {
            numerator = fraction.substring(numeratorStartIndex+1, numeratorEndIndex);
            denominator = fraction.substring(numeratorEndIndex+1);
        } else if (numeratorEndIndex != -1) {
            numerator = fraction.substring(0, numeratorEndIndex);
            denominator = fraction.substring(numeratorEndIndex+1);
        } else {
            numerator = "0";
            denominator = "1";
        }
        String[] output = {wholeNumber, numerator, denominator};
        return output;
    }
}
