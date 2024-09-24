package Helpers;

import java.util.function.Predicate;

public interface ValidatorInterface {
    Predicate<String> isStringValid();
    Predicate<String> isAddressValid();
    Predicate<Integer> isIntegerValid();
    Predicate<Double> isDoubleValid();
}
