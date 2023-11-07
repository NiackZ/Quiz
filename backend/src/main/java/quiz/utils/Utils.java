package quiz.utils;

import java.util.HashSet;
import java.util.List;

public class Utils {

    public static boolean listContainsValuesIgnoreCase(List<String> srcList, List<String> secondList) {
        return listContainsValues(srcList, secondList, true);
    }

    public static boolean listContainsValues(List<String> srcList, List<String> secondList, boolean ignoreCase) {
        return ignoreCase
               ? secondList.stream().allMatch(item -> srcList.stream().anyMatch(e -> e.equalsIgnoreCase(item)))
               : new HashSet<>(srcList).containsAll(secondList);
    }
}
