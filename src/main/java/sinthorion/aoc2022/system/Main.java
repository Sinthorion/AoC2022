package sinthorion.aoc2022.system;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import sinthorion.aoc2022.days.Day1;

public class Main {

  static void run(int day, DaySolver solver) {
    try(
      InputStream input = new URL(
          String.format("https://adventofcode.com/2022/day/%d/input", day)
      ).openStream()
    ) {
      String resultA = solver.solveA(input);
      if (resultA != null) { // null signals not implemented
        log("Day %d Part A", day);
        log("Result: %s", resultA);
      }
      String resultB = solver.solveB(input);
      if (resultB != null) { // null signals not implemented
        log("Day %d Part B", day);
        log("Result: %s", resultB);
      }
    } catch (IOException e) {
      throw new RuntimeException("Unexpected fatal error", e);
    }
  }

  static void log(String s, Object ... args) {
    System.out.printf(s, args);
    System.out.println();
  }

  public static void main(String[] args) {
    run(1, new Day1());
  }
}
