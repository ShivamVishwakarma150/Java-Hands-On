package Java8;

import java.util.function.BiPredicate;

public class _08_BiPredicate {
    public static void main(String[] args) {
        BiPredicate<String,Integer> biPre = (s,i) -> s.length() == i;

        System.out.println(biPre.test("Amit",4));
        System.out.println(biPre.test("Shivam",4));
    }
}
