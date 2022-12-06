package sinthorion.aoc2022.system;

public interface DaySolver {

  /**
   * @param input the puzzle input, in plaintext
   * @return an object to be stringified as the result, or null if the part has not been implemented
   */
  Object solveA(String input);

  /**
   * @param input the puzzle input, in plaintext
   * @return an object to be stringified as the result, or null if the part has not been implemented
   */
  Object solveB(String input);
}
