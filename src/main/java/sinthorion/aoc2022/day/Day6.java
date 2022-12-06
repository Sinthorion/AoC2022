package sinthorion.aoc2022.day;

import java.util.HashSet;
import java.util.stream.Collectors;
import sinthorion.aoc2022.system.Day;
import sinthorion.aoc2022.system.DaySolver;

@Day(day = 6)
public class Day6 implements DaySolver {

  boolean allDistinct(String marker) {
    return marker.chars().boxed().collect(Collectors.toCollection(HashSet::new)).size()
        == marker.length();
  }

  @Override
  public Object solveA(String input) {
    final int MARKERLENGTH = 4;
    for (int start = 0; start < input.length() - MARKERLENGTH - 1; start++) {
      if (allDistinct(input.substring(start, start + MARKERLENGTH))) {
        return start + MARKERLENGTH;
      }
    }
    return 0;
  }

  @Override
  public Object solveB(String input) {
    final int MARKERLENGTH = 14;
    for (int start = 0; start < input.length() - MARKERLENGTH - 1; start++) {
      if (allDistinct(input.substring(start, start + MARKERLENGTH))) {
        return start + MARKERLENGTH;
      }
    }
    return 0;
  }
}
