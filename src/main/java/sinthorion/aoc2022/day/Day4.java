package sinthorion.aoc2022.day;

import sinthorion.aoc2022.system.Day;
import sinthorion.aoc2022.system.DaySolver;

@Day(day = 4)
public class Day4 implements DaySolver {


  static class Range {
    int start, end;

    public Range(int start, int end) {
      if (start > end) throw new IllegalArgumentException("start is greater than end");
      this.start = start;
      this.end = end;
    }

    static Range parse(String input) {
      String[] split = input.split("-", 2);
      if (split.length != 2) {
        throw new RuntimeException("Parsing '" + input + "' as Range failed");
      }
      return new Range(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    boolean isSubset(Range other) {
      return this.start >= other.start && this.end <= other.end;
    }

    boolean isSubsetOrSuperset(Range other) {
      return this.isSubset(other) || other.isSubset(this);
    }

    boolean overlaps(Range other) {
      return (this.end >= other.start && this.end <= other.end)
          || (other.end >= this.start && other.end <= this.end);
    }
  }

  static class Pair {
    Range first, second;

    static Pair parse(String input) {
      String[] split = input.split(",", 2);
      if (split.length != 2) {
        throw new RuntimeException("Parsing '" + input + "' as Pair failed");
      }
      Pair pair = new Pair();
      pair.first = Range.parse(split[0]);
      pair.second = Range.parse(split[1]);
      return pair;
    }
  }

  @Override
  public Object solveA(String input) {
    return input.lines().map(Pair::parse).filter(pair -> pair.first.isSubsetOrSuperset(pair.second)).count();
  }

  @Override
  public Object solveB(String input) {
    return input.lines().map(Pair::parse).filter(pair -> pair.first.overlaps(pair.second)).count();
  }
}
