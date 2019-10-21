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
        String number1 = sections[0];
        String operator = sections[1];
        String number2 = sections[2];

        // splits the numbers into whole numbers, numerator, and denominator
        String[] fractionParts0 = findFraction(number1);
        String[] fractionParts1 = findFraction(number2);

        // creates "pure" fractions
        String[] pureFraction0 = convertToFraction(fractionParts0);
        String[] pureFraction1 = convertToFraction(fractionParts1);

        // System.out.println(Arrays.toString(pureFraction0));
        // System.out.println(Arrays.toString(pureFraction1));

        return evaulation(pureFraction0, operator, pureFraction1);
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
        return reduceToWhole(reduce(fractionOutput));
    }

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
        int numerator = Integer.parseInt(num1[0]) * Integer.parseInt(num2[0]);
        int denominator = Integer.parseInt(num1[1]) * Integer.parseInt(num2[1]);
        return numerator + "/" + denominator;
    }

    public static String divideFractions(String[] num1, String[] num2) {
        int numerator = Integer.parseInt(num2[0]);
        int denominator = Integer.parseInt(num2[1]);
        if (numerator < 0) {
            num2[0] = "-" + Integer.toString(denominator);
            num2[1] = Integer.toString(Math.abs(numerator));
        } else {
            num2[0] = Integer.toString(denominator);
            num2[1] = Integer.toString(numerator);
        }
        String[] newNum2 = {num2[0], num2[1]};
        return multiplyFractions(num1, newNum2);
    }

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

    public static String[] convertToFraction(String[] num) {
        int product = Integer.parseInt(num[0]) * Integer.parseInt(num[2]);
        String newNumerator = Integer.toString(Math.abs(product) + Integer.parseInt(num[1]));
        if (product < 0) {
            newNumerator = "-" + newNumerator;
        }
        String[] pureFraction = {newNumerator, num[2]};
        return pureFraction;
    }

    public static String reduceToWhole(String[] num) {
        int wholeNumber = Integer.parseInt(num[0]) / Integer.parseInt(num[1]);
        int numerator = Integer.parseInt(num[0]) % Integer.parseInt(num[1]);
        if (wholeNumber == 0 && numerator == 0) {
            return "0";
        } else if (wholeNumber == 0) {
            return num[0] + "/" + num[1];
        } else if (numerator == 0) {
            return Integer.toString(wholeNumber);
        }
        return Integer.toString(wholeNumber) + "_" + Integer.toString(Math.abs(numerator)) + "/" + num[1];
    }
    // currently not reducing all the way
    public static String[] reduce(String[] num) {
        int numerator = Integer.parseInt(num[0]);
        int denomintaor = Integer.parseInt(num[1]);
        int original = (denomintaor / 2) + 1;
        if (numerator == 0) {
            String[] output = {"0", num[1]};
            return output;
        } else {
            for (int i = 2; i <= (denomintaor / 2) + 1; i++) {
                if (numerator / i == numerator / (i + 0.0) && denomintaor / i == denomintaor / (i + 0.0)) {
                    numerator /= i;
                    denomintaor /= i;
                    i = 2;
                }
            }
        }
        String[] output = {Integer.toString(numerator), Integer.toString(denomintaor)};
        return output;
    }

}
