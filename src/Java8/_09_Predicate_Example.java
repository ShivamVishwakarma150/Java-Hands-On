package Java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class _09_Predicate_Example {
    public static void main(String[] args) {
        List<String> lst = Arrays.asList("Ravi","Ramesh","Suhas");

        Predicate<String> pre = s -> s.length() > 4;
        System.out.println(pre.test("Ravi"));
        System.out.println(pre.test("Ravindra"));
        System.out.println(pre.negate().test("Ravindra"));

        lst.stream().filter(pre).forEach(System.out::println);
    }
}
