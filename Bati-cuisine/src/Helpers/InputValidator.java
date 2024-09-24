package Helpers;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Predicate;

public class InputValidator implements ValidatorInterface {
    final private Scanner scanner;
    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }
    String stringRegex = "^[A-Za-z\\s]{3,}$";
    String phoneRegex = "^(?:\\+212|0)([5-7]\\d{8})$";
    String addressRegex = "^[a-zA-Z0-9\\s,'-./]+$";
    public String validateString(String prompt) {
        try {
            System.out.println(prompt);
            String string = scanner.nextLine().trim().toLowerCase();
            boolean isValid = isStringValid().test(string);
            if (!isValid) {
                System.out.println(string + " is not a valid input. Please enter again:");
                return validateString(prompt);
            }
            return string;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            return validateString(prompt);
        }
    }
    public String validateAddress(String prompt) {
        try {
            System.out.println(prompt);
            String string = scanner.nextLine();
            boolean isValid = isAddressValid().test(string);
            if (!isValid) {
                System.out.println(string + " is not a valid input. Please enter again:");
                return validateString(prompt);
            }
            return string;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            return validateString(prompt);
        }
    }
    public double ValidateDouble(String prompt) {
        try {
         System.out.println(prompt);
         double number = scanner.nextDouble();
         boolean isValid = isDoubleValid().test(number);
         if (!isValid) {
             System.out.println(number + " is not a valid input. Please enter again:");
             return ValidateDouble(prompt);
         }
         return number;
        }catch (InputMismatchException e) {
            System.out.println(prompt);
            return ValidateDouble(prompt);
        }
    }
    public String ValidatePrompt(String prompt) {
        System.out.println(prompt);
        String string = scanner.nextLine().trim().toLowerCase();
        boolean isValid = isPromptValid().test(string);
        if (!isValid) {
            System.out.println(string + " is not a valid input. Please enter again:");
            return ValidatePrompt(prompt);
        }
        return string;
    }
    public int ValidateInt(String prompt, int inputInt) {
        try {

        }catch (InputMismatchException e) {
            System.out.println(prompt);
        }
        return inputInt;
    }
    public String phoneValidator(String prompt) {
        try {
            System.out.println(prompt);
            String string = scanner.nextLine();
            boolean isValid = isPhoneValid().test(string);
            if (!isValid) {
                System.out.println(string + " is not a valid input. Please enter again:");
                return phoneValidator(prompt);
            }
            return string;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            return phoneValidator(prompt);
        }
    }
    @Override
    public Predicate<String> isStringValid() {
        return input -> !input.isEmpty() && input.matches(stringRegex);
    }
    @Override
    public Predicate<String> isAddressValid() {
        return input -> input != null && input.matches(addressRegex);
    }
    @Override
    public Predicate<Integer> isIntegerValid() {
        return integer -> integer > 0;
    }
    @Override
    public Predicate<Double> isDoubleValid() {
        return input -> input > 0;
    }
    public Predicate<String> isPhoneValid() {
        return phone -> phone.matches(phoneRegex);
    }
    public Predicate<String> isValid() {
        return input -> input != null && input.equalsIgnoreCase("y");
    }
    public Predicate<String> isNotValid() {
        return input -> input != null && input.equalsIgnoreCase("n");
    }
    public Predicate<String> isPromptValid() {
        return isValid().or(isNotValid());
    }
}
