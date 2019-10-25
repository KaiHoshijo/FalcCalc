package fracCalc;

import java.util.Arrays;
import java.util.Scanner;

public class FracCalc {

    public static Scanner console = new Scanner(System.in);
    public static String operation = "";
    public static String output = "";

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
        String output = "";
        // doesn't do PEMDAS
        for (int i = 1; i <= sections.length - 2;) {
            String number1 = sections[i-1];
            String operator = sections[i];
            String number2 = sections[i+1];

            // splits the numbers into whole numbers, numerator, and denominator
            String[] fractionParts0 = findFraction(number1);
            String[] fractionParts1 = findFraction(number2);

            // creates "pure" fractions
            String[] pureFraction0 = convertToFraction(fractionParts0);
            String[] pureFraction1 = convertToFraction(fractionParts1);

            if (pureFraction0[1].equals("0") || pureFraction1[1].equals("0") || number2.equals("0")) {
                return "Error: Cannot divide by 0";
            }

            if (operator.length() != 1) {
                return "Error: Invalid input";
            }

            // System.out.println(Arrays.toString(pureFraction0));
            // System.out.println(Arrays.toString(pureFraction1));
            if (output.length() == 0) {
                output = evaulation(pureFraction0, operator, pureFraction1);
            } else {
                String[] pureFractionOutput = convertToFraction(findFraction(output));
                output = evaulation(pureFractionOutput, operator, pureFraction1);
            }
            i += 2;
        }
//        String number1 = sections[0];
//        String operator = sections[1];
//        String number2 = sections[2];
//
//        // splits the numbers into whole numbers, numerator, and denominator
//        String[] fractionParts0 = findFraction(number1);
//        String[] fractionParts1 = findFraction(number2);
//
//        // creates "pure" fractions
//        String[] pureFraction0 = convertToFraction(fractionParts0);
//        String[] pureFraction1 = convertToFraction(fractionParts1);
//
//        // System.out.println(Arrays.toString(pureFraction0));
//        // System.out.println(Arrays.toString(pureFraction1));
//
//        return evaulation(pureFraction0, operator, pureFraction1);
        return output;
        // return "whole:" + fractionParts1[0] + " numerator:" + fractionParts1[1] + " denominator:" + fractionParts1[2];
    }

    // TODO: Fill in the space below with any helper methods that you think you will need
    public static String[] findFraction(String fraction) {
        // returns the whole number, numerator, and denominator
        String wholeNumber; String numerator; String denominator;
        // whole number section
        int wholeNumberIndex = fraction.indexOf("_");
        int numeratorEndIndex = fraction.indexOf("/");
        if (wholeNumberIndex != -1) {
            wholeNumber = fraction.substring(0, wholeNumberIndex);
        } else if (numeratorEndIndex == -1) {
            wholeNumber = fraction;
        } else {
            wholeNumber = "0";
        }
        // numerator and denominator section
        if (wholeNumberIndex != -1) {
            numerator = fraction.substring(wholeNumberIndex+1, numeratorEndIndex);
            denominator = fraction.substring(numeratorEndIndex+1);
        } else if (numeratorEndIndex != -1) {
            numerator = fraction.substring(0, numeratorEndIndex);
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

    public static String evaulation(String[] num1, String operator, String[] num2) {
        output = "";
        // figures out operator
        if (operator.equals("+")) {
            // adds the numbers
            // fractions
            output += addFractions(num1, num2);
        } else if (operator.equals("-")) {
            // subtracts the numbers
            // fractions
            output += subtractFractions(num1, num2);
        } else if (operator.equals("*")) {
            // multiples the numbers
            output += multiplyFractions(num1, num2);
        } else if (operator.equals("/")) {
            // divides the numbers
            output += divideFractions(num1, num2);
        }
        String[] fractionOutput = output.split("/");
        return reduce(fractionOutput);
    }

    // operations
    public static String addFractions(String[] num1, String[] num2) {
        if (!num1[0].equals("0") && !num2[0].equals("0")) {
            String[] newFractions = greatestCommonMultiple(num1, num2);
            // updating the first numerator and denominator
            num1[0] = newFractions[0];
            num1[1] = newFractions[1];
            // updating the second numerator and denominator
            num2[0] = newFractions[2];
            num2[1] = newFractions[3];
            int combined = Integer.parseInt(num1[0]) + Integer.parseInt(num2[0]);
            return Integer.toString(combined) + "/" + num2[1];
        } else if (num1[0].equals("0")) {
            // adding the first fraction
            return num2[0] + "/" + num2[1];
        } else if (num2[0].equals("0")) {
            // adding the second fraction
            return num1[0] + "/" + num1[1];
        }
        return "";
    }

    public static String subtractFractions(String[] num1, String[] num2) {
        if (!num1[0].equals("0") && !num2[0].equals("0")) {
            String[] newFractions = greatestCommonMultiple(num1, num2);
            // updating the first numerator and denominator
            num1[0] = newFractions[0];
            num1[1] = newFractions[1];
            // updating the second numerator and denominator
            num2[0] = newFractions[2];
            num2[1] = newFractions[3];
            int difference = Integer.parseInt(num1[0]) - Integer.parseInt(num2[0]);
            return Integer.toString(difference) + "/" + num1[1];
        } else if (num2[0].equals("0")) {
            // subtracting the first fraction
            return num1[0] + "/" + num1[1];
        } else if (num1[0].equals("0")) {
            // subtracting the second fraction
            return "-" + num2[0] + "/" + num2[1];
        }
        return "";
    }

    public static String multiplyFractions(String[] num1, String[] num2) {
        // multiplies the numerators of the two fractions
        int numerator = Integer.parseInt(num1[0]) * Integer.parseInt(num2[0]);
        // multiplies the denominators of the two fractions
        int denominator = Integer.parseInt(num1[1]) * Integer.parseInt(num2[1]);
        // returns the new fraction
        return numerator + "/" + denominator;
    }

    public static String divideFractions(String[] num1, String[] num2) {
        // gets the numerator and denomintaor of the second fraction
        int numerator = Integer.parseInt(num2[0]);
        int denominator = Integer.parseInt(num2[1]);
        // copy dot flip or gets the opposite of the second fraction
        // and multiples it to the first fraction
        if (numerator < 0) {
            num2[0] = "-" + Integer.toString(denominator);
            num2[1] = Integer.toString(Math.abs(numerator));
        } else {
            num2[0] = Integer.toString(denominator);
            num2[1] = Integer.toString(numerator);
        }
        // creates the new fraction
        String[] newNum2 = {num2[0], num2[1]};
        // returns the produce of the first fraction and the new second fraction
        return multiplyFractions(num1, newNum2);
    }

    // methods used to simplify the use of fractions
    public static String[] greatestCommonMultiple(String[] num1, String[] num2) {
        // check the mod of each number
        int numerator1 = Integer.parseInt(num1[0]);
        int numerator2 = Integer.parseInt(num2[0]);
        int denominator1 = Integer.parseInt(num1[1]);
        int denominator2 = Integer.parseInt(num2[1]);

        int mod1 = denominator1 % denominator2;
        int mod2 = denominator2 % denominator1;
        // if not zero, cry... and multiply each denom and numer by opposing denom
        if (mod1 != 0 && mod2 != 0) {
            numerator1 *= denominator2;
            numerator2 *= denominator1;
            denominator1 *= denominator2;
            denominator2 = denominator1;
        } else if (denominator1 == denominator2) {
            // generating a new fraction
            String[] newFractions = {Integer.toString(numerator1), Integer.toString(denominator1),
                    Integer.toString(numerator2), Integer.toString(denominator1)};
            return newFractions;
        }
        // else celebrate and multiply the zero mod with the division of the two numbers
        else if (mod1 == 0) {
            numerator2 *= denominator1 / denominator2;
            denominator2 *= denominator1 / denominator2;
        } else if (mod2 == 0) {
            numerator1 *= denominator2 / denominator1;
            denominator1 *= denominator2 / denominator1;
        }
        // generating a new fraction
        String[] newFractions = {Integer.toString(numerator1), Integer.toString(denominator1),
                Integer.toString(numerator2), Integer.toString(denominator1)};
        return newFractions;
    }

    // used to convert the numbers with whole numbers to improper fractions
    public static String[] convertToFraction(String[] num) {
        // gets the improper version of each fraction
        int product = Integer.parseInt(num[0]) * Integer.parseInt(num[2]);
        // adds the whole number in the form of the fraction to the current numerator
        String newNumerator = Integer.toString(Math.abs(product) + Integer.parseInt(num[1]));
        // ensures that the number stays negative
        if (product < 0) {
            newNumerator = "-" + newNumerator;
        }
        // returns the newly improper fraction
        String[] pureFraction = {newNumerator, num[2]};
        return pureFraction;
    }

    // reduces the improper fractions back to whole numebers
    public static String reduceToWhole(String[] num) {
        // does the opposite of convertToFraction
        // finds the whole number given by the division of numerator and denominator
        int wholeNumber = Integer.parseInt(num[0]) / Integer.parseInt(num[1]);
        // finds what would be left in the numerator
        int numerator = Integer.parseInt(num[0]) % Integer.parseInt(num[1]);
        // returns zero if numerator and whole number are zero
        if (wholeNumber == 0 && numerator == 0) {
            return "0";
        } else if (wholeNumber == 0) {
            // returns only a fraction
            return num[0] + "/" + num[1];
        } else if (numerator == 0) {
            // returns only a whole number
            return Integer.toString(wholeNumber);
        }
        // returns a whole number PLUS the fraction
        return Integer.toString(wholeNumber) + "_" + Integer.toString(Math.abs(numerator)) + "/" + num[1];
    }

    // reduces the fraction and then returns the whole number and the left over fraction
    public static String reduce(String[] num) {
        // gets the current numerator and denominator
        int numerator = Integer.parseInt(num[0]);
        int denominator = Integer.parseInt(num[1]);
        if (numerator == 0) {
            // returns 0 into whole numbers
            String[] output = {"0", num[1]};
            return reduceToWhole(output);
        } else {
            // reduces the number WHILE it can continue to reduce
            int i = 2;
            while(i < (denominator / 2) + 1) {
                if (numerator % i == 0 && denominator % i == 0) {
                    numerator /= i;
                    denominator /= i;
                    i = 2;
                } else {
                    i++;
                }
            }
        }
        String[] output = {Integer.toString(numerator), Integer.toString(denominator)};
        // returns the newly reduced fraction to reduceToWhole
        return reduceToWhole(output);
    }

}
