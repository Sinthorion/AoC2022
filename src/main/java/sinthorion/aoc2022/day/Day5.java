package sinthorion.aoc2022.day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import sinthorion.aoc2022.system.Day;
import sinthorion.aoc2022.system.DaySolver;

@Day(day = 5)
public class Day5 implements DaySolver {

  static <T> T getLast(T[] array) {
    if (array.length == 0) {
      throw new NullPointerException("Empty array");
    }
    return array[array.length - 1];
  }

  List<Stack<Object>> stacks;

  List<Move> moves;

  @SuppressWarnings("unchecked")
  void parse(String input) {
    List<String> header = input.lines().takeWhile(line -> !line.isBlank())
        .collect(Collectors.toCollection(ArrayList::new));

    String stackNums = header.remove(header.size() - 1);
    int stackCount = Integer.parseInt(getLast(stackNums.split(" "))); // unecessary complicated; won't be more than 9
    stacks = Arrays.asList(new Stack[stackCount]);

    Collections.reverse(header);

    for (int i = 0; i < stackCount; i++) {
      Stack<Object> stack = new Stack<>();
      stacks.set(i, stack);
      final int index = i; // needed for lambda capture
      header.stream().map(s -> s.charAt(1 + 4*index)).filter(c -> c != ' ').forEach(stack::push);
    }

    moves = input.lines().dropWhile(line -> !line.isBlank()).dropWhile(String::isBlank).map(Move::parse).toList();
  }

  static void doTimes(int n, Runnable function) {
    while (n-- > 0) {
      function.run();
    }
  }

  static class Move {
    int from, to, amount;

    static Move parse(String input) {
      String[] split = input.split(" ");
      Move move = new Move();
      move.amount = Integer.parseInt(split[1]);
      move.from = Integer.parseInt(split[3]);
      move.to = Integer.parseInt(split[5]);
      return move;
    }

    <T> void applyA(List<Stack<T>> stacks) {
      Stack<T> fromStack = stacks.get(from - 1);
      Stack<T> toStack = stacks.get(to - 1);
      if (fromStack.size() < amount) {
        throw new RuntimeException(String.format("Stack %d is smaller than amount %d", from, amount));
      }
      doTimes(amount, () -> toStack.push(fromStack.pop()));
    }

    <T> void applyB(List<Stack<T>> stacks) {
      Stack<T> fromStack = stacks.get(from - 1);
      Stack<T> toStack = stacks.get(to - 1);
      if (fromStack.size() < amount) {
        throw new RuntimeException(String.format("Stack %d is smaller than amount %d", from, amount));
      }
      Stack<T> buffer = new Stack<>();
      doTimes(amount, () -> buffer.push(fromStack.pop()));
      doTimes(amount, () -> toStack.push(buffer.pop()));
    }
  }

  @Override
  public String solveA(String input) {
    parse(input);
    moves.forEach(move -> move.applyA(stacks));
    return stacks.stream().map(Stack::peek).collect(Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString));
  }

  @Override
  public String solveB(String input) {
    parse(input);
    moves.forEach(move -> move.applyB(stacks));
    return stacks.stream().map(Stack::peek).collect(Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString));
  }
}
