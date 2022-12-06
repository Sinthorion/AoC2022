package sinthorion.aoc2022.system;

import static org.reflections.scanners.Scanners.TypesAnnotated;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Scanner;
import org.reflections.Reflections;

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

      String resultA = String.valueOf(solver.solveA(input));
      if (resultA != null) { // null signals not implemented
        log("Day %d Part A", day);
        log("Result: %s", resultA);
      } else {
        log("Part A not implemented");
      }
      String resultB = String.valueOf(solver.solveB(input));
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

  static DaySolver getSolver(int forDay) {
    Reflections reflections = new Reflections("sinthorion.aoc2022");

    for (Class<?> klazz : reflections.get(TypesAnnotated.with(Day.class).asClass())) {
      Day annotation = klazz.getAnnotation(Day.class);
      int annotatedDay = annotation.day();
      if (annotatedDay < 1 || annotatedDay > 25) {
        throw new IllegalArgumentException("'day' must be 1-25 in @Day at " + klazz.getCanonicalName());
      }
      if (!DaySolver.class.isAssignableFrom(klazz)) {
        throw new RuntimeException("Annotation @Day must only be used for DaySolvers");
      }

      if (annotatedDay == forDay) {
        try {
          return (DaySolver)klazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
          throw new RuntimeException(e);
        }
      }
    }

    return null;
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
      throw new IllegalArgumentException("Input must be 1-25");
    }

    DaySolver solver = getSolver(day);
    if (solver == null) {
      throw new RuntimeException("Day " + day + " has not been implemented yet or could not be found");
    }
    run(day, solver, cookie);
  }
}
