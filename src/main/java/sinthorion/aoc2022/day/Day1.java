package sinthorion.aoc2022.day;

import java.util.Arrays;
import java.util.Comparator;
import sinthorion.aoc2022.system.Day;
import sinthorion.aoc2022.system.DaySolver;

@Day(day = 1)
public class Day1 implements DaySolver {

  @Override
  public Object solveA(String input) {
    return Arrays.stream(input.split("\\n\\n")).mapToInt(section -> section.lines()
        .mapToInt(Integer::parseInt).sum()).max().orElseThrow();
  }

  @Override
  public Object solveB(String input) {
    return Arrays.stream(input.split("\\n\\n")).mapToInt(section -> section.lines()
        .mapToInt(Integer::parseInt).sum()).boxed().sorted(Comparator.reverseOrder()).mapToInt(i->i)
        .limit(3).sum();
  }
}
