package sinthorion.aoc2022.day;

import sinthorion.aoc2022.system.Day;
import sinthorion.aoc2022.system.DaySolver;

@Day(day = 2)
public class Day2 implements DaySolver {

  enum Move {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    final int baseScore;
    Move(int score) {
      baseScore = score;
    }

    static Move parse(Character letter) {
      return switch (letter) {
        case 'A', 'X' -> ROCK;
        case 'B', 'Y' -> PAPER;
        case 'C', 'Z' -> SCISSORS;
        default -> throw new IllegalArgumentException(String.format("Expected ABC or XYZ, found '%c'", letter));
      };
    }

    Result result(Move second) {
      if (this == second) return Result.DRAW;
      return switch(second) {
        case ROCK -> this == SCISSORS ? Result.WIN : Result.LOSS;
        case PAPER -> this == ROCK ? Result.WIN : Result.LOSS;
        case SCISSORS -> this == PAPER ? Result.WIN : Result.LOSS;
      };
    }

    int score(Move second) {
      return second.baseScore + this.result(second).score;
    }

    Move needed(Result result) {
      if (result == Result.DRAW) return this;
      return switch (this) {
        case ROCK -> result == Result.WIN ? PAPER : SCISSORS;
        case PAPER -> result == Result.WIN ? SCISSORS : ROCK;
        case SCISSORS -> result == Result.WIN ? ROCK : PAPER;
      };
    }

  }

  enum Result {
    WIN(6),
    DRAW(3),
    LOSS(0);

    final int score;
    Result(int score) {
      this.score = score;
    }

    static Result parse(Character letter) {
      return switch (letter) {
        case 'X' -> LOSS;
        case 'Y' -> DRAW;
        case 'Z' -> WIN;
        default -> throw new IllegalArgumentException(String.format("Expected XYZ, found '%c'", letter));
      };
    }
  }

  @Override
  public Object solveA(String input) {
    int result = input.lines().mapToInt(line -> {
      Move first = Move.parse(line.charAt(0));
      Move second = Move.parse(line.charAt(2));
      return first.score(second);
    }).sum();
    return String.valueOf(result);
  }

  @Override
  public Object solveB(String input) {
    return input.lines().mapToInt(line -> {
      Move first = Move.parse(line.charAt(0));
      Result result = Result.parse(line.charAt(2));
      Move second = first.needed(result);
      return first.score(second);
    }).sum();
  }
}
