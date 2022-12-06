package sinthorion.aoc2022.day;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day6Test {

  @Test
  void solveA() {
    Day6 day6 = new Day6();
    assertEquals(day6.solveA("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 7);
    assertEquals(day6.solveA("bvwbjplbgvbhsrlpgdmjqwftvncz"), 5);
    assertEquals(day6.solveA("nppdvjthqldpwncqszvftbrmjlhg"), 6);
    assertEquals(day6.solveA("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 10);
    assertEquals(day6.solveA("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 11);
  }

  @Test
  void solveB() {
    Day6 day6 = new Day6();
    assertEquals(day6.solveB("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 19);
    assertEquals(day6.solveB("bvwbjplbgvbhsrlpgdmjqwftvncz"), 23);
    assertEquals(day6.solveB("nppdvjthqldpwncqszvftbrmjlhg"), 23);
    assertEquals(day6.solveB("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 29);
    assertEquals(day6.solveB("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 26);
  }
}