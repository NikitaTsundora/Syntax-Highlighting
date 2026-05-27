import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class JavadocTokenTest {

  private final Pattern pattern = Pattern.compile("/\\*\\*[\\s\\S]*?\\*/");

  @Test
  void matchesSimpleJavadoc() {
    var m = pattern.matcher("/** Hallo */");
    assertTrue(m.find());
  }

  @Test
  void matchesJavadocInMiddle() {
    var m = pattern.matcher("abc /** Test */ def");
    assertTrue(m.find());
  }

  @Test
  void matchesMultilineJavadoc() {
    var text =
        """
        /**
         * Mehrzeilig
         */
        """;
    assertTrue(pattern.matcher(text).find());
  }

  @Test
  void doesNotMatchNormalBlockComment() {
    assertFalse(pattern.matcher("/* kein Javadoc */").find());
  }
}
