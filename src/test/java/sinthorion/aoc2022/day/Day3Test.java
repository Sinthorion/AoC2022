package sinthorion.aoc2022.day;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day3Test {

  @org.junit.jupiter.api.Test
  void priority() {
    assertEquals(5, Day3.priority('e'));
    assertEquals(30, Day3.priority('D'));
  }

  @Test
  void solveB() {
    String input = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw""";
    assertEquals("70", new Day3().solveB(input));
  }
}