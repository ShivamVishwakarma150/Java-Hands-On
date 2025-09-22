package Java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class _03_FI_FunctionalCombo {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Raj", "Amit", "Suresh", "John");

        Predicate<String> lengthCheck = name -> name.length() > 4;
        Function<String, Integer> lengthFinder = name -> name.length();
        Consumer<Integer> printer = len -> System.out.println("Length: " + len);

        names.stream()
                .filter(lengthCheck)
                .map(lengthFinder)
                .forEach(printer);
    }
}
