package sinthorion.aoc2022.day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import sinthorion.aoc2022.system.Day;
import sinthorion.aoc2022.system.DaySolver;

@Day(day = 3)
public class Day3 implements DaySolver {

  static int priority(char item) {
    if (Character.isUpperCase(item)) {
      return item - 'A' + 27;
    } else {
      return item - 'a' + 1;
    }
  }

  static Set<Character> stringToMap(String input) {
    return input.chars().mapToObj(c -> (char) c)
        .collect(HashSet::new, HashSet::add, HashSet::addAll);
  }

  static class Backpack {

    Set<Character> left;
    Set<Character> right;

    public Backpack(String left, String right) {
      this.left = stringToMap(left);
      this.right = stringToMap(right);
    }

    Set<Character> intersection() {
      Set<Character> intersection = new HashSet<>(this.left);
      intersection.retainAll(this.right);
      return intersection;
    }
  }

  static class Group {

    Set<Character> a, b, c;

    public Group(String first, String second, String third) {
      this.a = stringToMap(first);
      this.b = stringToMap(second);
      this.c = stringToMap(third);
    }

    Character badge() {
      Set<Character> intersection = new HashSet<>(a);
      intersection.retainAll(b);
      intersection.retainAll(c);
      if (intersection.size() != 1) {
        throw new RuntimeException("Group badge not found");
      }
      return intersection.stream().findFirst().get();
    }
  }

  @Override
  public Object solveA(String input) {
    return input.lines()
        .map(line -> new Backpack(line.substring(0, line.length() / 2),
            line.substring(line.length() / 2)))
        .map(Backpack::intersection).flatMap(Set::stream).mapToInt(Day3::priority).sum();
  }

  @Override
  public Object solveB(String input) {
    return input.lines().collect(TakeThree.collector(Group::new)).stream().map(Group::badge)
        .mapToInt(Day3::priority).sum();
  }

  @FunctionalInterface
  public interface TriFunction<T, R> {

    R apply(T t1, T t2, T t3);
  }

  static class TakeThree<T, R> {

    private final TriFunction<T, R> function;

    private final List<T> buffer;

    private final List<R> results;

    TakeThree(TriFunction<T, R> function) {
      this.function = function;
      buffer = new ArrayList<>(3);
      results = new ArrayList<>();
    }

    TakeThree<T, R> supply() {
      return this;
    }

    public void accept(T val) {
      buffer.add(val);
      if (buffer.size() == 3) {
        results.add(function.apply(buffer.get(0), buffer.get(1), buffer.get(2)));
        buffer.clear();
      }
    }

    TakeThree<T, R> combine(TakeThree<T, R> right) {
      throw new UnsupportedOperationException("Parallel Stream not supported");
    }

    List<R> finish() {
      return results;
    }

    public static <T, R> Collector<T, ?, List<R>> collector(TriFunction<T, R> function) {
      TakeThree<T, R> collector = new TakeThree<>(function);
      return Collector.of(collector::supply, TakeThree::accept, TakeThree::combine,
          TakeThree::finish);
    }
  }
}
