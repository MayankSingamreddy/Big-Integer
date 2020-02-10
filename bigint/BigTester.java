package bigint;

import java.math.BigInteger;
import java.util.Scanner;

public class BigTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.print("What would you like to test: (p)arse, (a)dd, (m)ultiply, or (q)uit?: ");
            char choice = scanner.nextLine().toLowerCase().charAt(0);
            int numberOfPasses = 0;
            int numberOfTests = 1000;
            switch (choice) {
                case 'p':
                    for (int i = 0; i < numberOfTests; i++) {
                        System.out.printf("Parse Test #%d: ", i);
                        StringBuilder parseString = new StringBuilder();
                        if (Math.random() > 0.5)
                            parseString.append('-');
                        for (int j = 0; j < (int) (Math.random() * 30 + 1); j++)
                            parseString.append((int) (Math.random() * 10 + 1));
                        bigint.BigInteger parsedBigInteger = bigint.BigInteger.parse(parseString.toString());
                        String parsed = parsedBigInteger.toString();
                        BigInteger bigInteger = new BigInteger(parsed);
                        boolean passed = true;
                        if (!parsed.equals(bigInteger.toString()))
                            passed = false;
                        else if (parsed.equals("0")) {
                            if (parsedBigInteger.numDigits != 0 || parsedBigInteger.front != null)
                                passed = false;
                        }
                        else if (parsed.length() != parsedBigInteger.numDigits) {
                            if (parsedBigInteger.negative) {
                                if (parsed.length() - 1 != parsedBigInteger.numDigits)
                                    passed = false;
                            }
                            else
                                passed = false;
                        }
                        if (passed)
                            numberOfPasses++;
                        System.out.print(passed ? "passed" : "FAILED");
                        System.out.printf(" | your parse function: %-30s %s \n", parsed, "the actual value: " + bigInteger.toString());
                    }
                    System.out.println("Percentage Passed: " + (100.0 * numberOfPasses / numberOfTests) + "%");
                    break;
                case 'a':
                    for (int i = 0; i < numberOfTests; i++) {
                        System.out.printf("Addition Test #%d: ", i);
                        StringBuilder additionString1 = new StringBuilder(), additionString2 = new StringBuilder();
                        if (Math.random() > 0.5)
                            additionString1.append('-');
                        for (int j = 0; j < (int) (Math.random() * 30 + 1); j++)
                            additionString1.append((int) (Math.random() * 10 + 1));
                        if (Math.random() > 0.5)
                            additionString2.append('-');
                        for (int j = 0; j < (int) (Math.random() * 30 + 1); j++)
                            additionString2.append((int) (Math.random() * 10 + 1));
                        bigint.BigInteger additionBigInteger = bigint.BigInteger.add(bigint.BigInteger.parse(additionString1.toString()), bigint.BigInteger.parse(additionString2.toString()));
                        String addition = additionBigInteger.toString();
                        BigInteger bigInteger = new BigInteger(additionString1.toString());
                        bigInteger = bigInteger.add(new BigInteger(additionString2.toString()));
                        boolean passed = true;
                        if (!addition.equals(bigInteger.toString()))
                            passed = false;
                        else if (addition.equals("0")) {
                            if (additionBigInteger.numDigits != 0 || additionBigInteger.front != null)
                                passed = false;
                        }
                        else if (addition.length() != additionBigInteger.numDigits) {
                            if (additionBigInteger.negative) {
                                if (addition.length() - 1 != additionBigInteger.numDigits)
                                    passed = false;
                            }
                            else
                                passed = false;
                        }
                        if (passed)
                            numberOfPasses++;
                        System.out.print(passed ? "passed" : "FAILED");
                        System.out.printf(" | your add function: %-25s %-45s ", addition, "the actual value: " + bigInteger.toString());
                        System.out.println("arguments: " + additionString1.toString() + " " + additionString2.toString());
                    }
                    System.out.println("Percentage Passed: " + (100.0 * numberOfPasses / numberOfTests) + "%");
                    break;
                case 'm':
                    for (int i = 0; i < numberOfTests; i++) {
                        System.out.printf("Multiplication Test #%d: ", i);
                        StringBuilder multiplicationString1 = new StringBuilder(), multiplicationString2 = new StringBuilder();
                        if (Math.random() > 0.5)
                            multiplicationString1.append('-');
                        for (int j = 0; j < (int) (Math.random() * 30 + 1); j++)
                            multiplicationString1.append((int) (Math.random() * 10 + 1));
                        if (Math.random() > 0.5)
                            multiplicationString2.append('-');
                        for (int j = 0; j < (int) (Math.random() * 30 + 1); j++)
                            multiplicationString2.append((int) (Math.random() * 10 + 1));
                        bigint.BigInteger multiplicationBigInteger =  bigint.BigInteger.multiply(bigint.BigInteger.parse(multiplicationString1.toString()), bigint.BigInteger.parse(multiplicationString2.toString()));
                        String multiplication = multiplicationBigInteger.toString();
                        BigInteger bigInteger = new BigInteger(multiplicationString1.toString());
                        bigInteger = bigInteger.multiply(new BigInteger(multiplicationString2.toString()));
                        boolean passed = true;
                        if (!multiplication.equals(bigInteger.toString()))
                            passed = false;
                        else if (multiplication.equals("0")) {
                            if (multiplicationBigInteger.numDigits != 0 || multiplicationBigInteger.front != null)
                                passed = false;
                        }
                        else if (multiplication.length() != multiplicationBigInteger.numDigits) {
                            if (multiplicationBigInteger.negative) {
                                if (multiplication.length() - 1 != multiplicationBigInteger.numDigits)
                                    passed = false;
                            }
                            else
                                passed = false;
                        }
                        if (passed)
                            numberOfPasses++;
                        System.out.print(passed ? "passed" : "FAILED");
                        System.out.printf(" | your multiply function: %-25s %-45s ", multiplication, "the actual value: " + bigInteger.toString());
                        System.out.println("arguments: " + multiplicationString1.toString() + " " + multiplicationString2.toString());
                    }
                    System.out.println("Percentage Passed: " + (100.0 * numberOfPasses / numberOfTests) + "%");
                    break;
                case 'q':
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

}
