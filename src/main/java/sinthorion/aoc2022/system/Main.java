package sinthorion.aoc2022.system;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Scanner;

public class Main {

  static InputStream openConnection(int day, String cookie) throws IOException {
    URL url = new URL("https://adventofcode.com/2022/day/" + day + "/input");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.addRequestProperty("Cookie","session=" + URLEncoder.encode(cookie, StandardCharsets.UTF_8));
    return conn.getInputStream();
  }

  static void run(int day, DaySolver solver, String cookie) {
    try(InputStream inputStream = openConnection(day, cookie)) {
      String input = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

      String resultA = solver.solveA(input);
      if (resultA != null) { // null signals not implemented
        log("Day %d Part A", day);
        log("Result: %s", resultA);
      } else {
        log("Part A not implemented");
      }
      String resultB = solver.solveB(input);
      if (resultB != null) { // null signals not implemented
        log("Day %d Part B", day);
        log("Result: %s", resultB);
      } else {
        log("Part B not implemented");
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
    String cookie = System.getenv("COOKIE");
    if (cookie == null) {
      throw new RuntimeException("Missing AoC session cookie. Set the COOKIE environment variable "
          + "to the 'session' cookie from adventofcode.com after logging in.");
    }

    Scanner sysInput = new Scanner(System.in);

    Calendar today = Calendar.getInstance();
    int dayDefault = 0;
    if (today.get(Calendar.MONTH) == Calendar.DECEMBER && today.get(Calendar.DAY_OF_MONTH) <= 25) {
      dayDefault = today.get(Calendar.DAY_OF_MONTH);
      System.out.printf("Run which day (default %d)? ", dayDefault);
    } else {
      System.out.print("Run which day? ");
    }
    String userInput = sysInput.nextLine();
    int day = userInput.isBlank() ? dayDefault : Integer.parseInt(userInput);
    if (day <= 0 || day > 25) {
      new IllegalArgumentException("Input must be 1-25").printStackTrace();
      return;
    }

    try {
      Class<?> klass = Class.forName("sinthorion.aoc2022.day.Day" + day);
      if (!DaySolver.class.isAssignableFrom(klass)) {
        System.err.println("Day" + day + " does not implement DaySolver");
        return;
      }
      DaySolver solver = (DaySolver)klass.getConstructor().newInstance();
      run(day, solver, cookie);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Day " + day + " has not been implemented yet or could not be found", e);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
             NoSuchMethodException e) {
      System.err.println("Failed to instantiate Day" + day + " solver");
      throw new RuntimeException(e);
    }
  }
}
