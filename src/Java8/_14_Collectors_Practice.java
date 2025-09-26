package Java8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class _14_Collectors_Practice {
    public static void main(String[] args) {

        // A — Basic: toList / toSet / toCollection
        List<String> list = Stream.of("a","b","c")
                .collect(Collectors.toList());

        Set<String> set = Stream.of("a","b","a")
                .collect(Collectors.toSet());

        Deque<String> dq = Stream.of("x","y")
                .collect(Collectors.toCollection(ArrayDeque::new));

        List<String> words = Arrays.asList("apple", "banana", "apple");

        //B — toMap with duplicate-key handling (important pitfall)
        Map<String,Integer> counts = words.stream()
                .collect(Collectors.toMap(w->w,w->1,Integer::sum));

        Map<String,Integer> ordered = words.stream()
                .collect(Collectors.toMap(w->w,w->1,Integer::sum,LinkedHashMap::new));

//        System.out.println(counts);
//        System.out.println(ordered);

        // C — groupingBy + downstream collectors (powerful)
        List<String> names = Arrays.asList("Amy","Bob","Alex","Brian","Cory");

        Map<Character,List<String>> mp = names.stream()
                .collect(Collectors.groupingBy(s->s.charAt(0)));

//        System.out.println(mp);

        Map<Character,Long> mp2=names.stream()
                .collect(Collectors.groupingBy(s->s.charAt(0),Collectors.counting()));
//        System.out.println(mp2);

        Map<Character,Map<Integer,List<String>>> multi = names.stream()
                .collect(Collectors.groupingBy(s->s.charAt(0),Collectors.groupingBy(String::length)));

//        System.out.println(multi);

        Map<Character,Set<String>> grpUpper = names.stream()
                .collect(Collectors.groupingBy(s->s.charAt(0),
                        Collectors.mapping(String::toUpperCase,Collectors.toSet())));


//      System.out.println(grpUpper);


        // D — partitioningBy (special case of grouping: boolean key)
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6);

        Map<Boolean,List<Integer>> part = nums.stream()
                .collect(Collectors.partitioningBy(s->s%2==0));

//        System.out.println(part.get(true));
//        System.out.println(part.get(false));

        // E — summarizing / summing / averaging / counting / maxBy

        IntSummaryStatistics stats = Stream.of(2,3,5,7)
                .collect(Collectors.summarizingInt(Integer::intValue));

//        System.out.println("Max: "+stats.getMax()+" Sum: "+stats.getSum());


        double avg = Stream.of(2,3,5,7).collect(Collectors.averagingInt(Integer::intValue));
        System.out.println(avg);

        Optional<Integer> max = Stream.of(2,3,5,7)
                .collect(Collectors.maxBy(Integer::compareTo));
        Optional<Integer> max2 = Stream.of(2,3,5,7)
                .max(Integer::compareTo);

//        System.out.println(max.get());
//        System.out.println(max2.get());

//        System.out.println(Stream.of(2,3,5,7).collect(Collectors.maxBy(Integer::compareTo)).get());

        // F — joining (strings)
        String csv = Stream.of("a","b","c")
                .collect(Collectors.joining(","));

//        System.out.println(csv);
//
//        System.out.println(Stream.of("a","b","c").collect(Collectors.joining("-","[","]")));



    }

}
