import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class StringLiteralTokenTest {

  private final Pattern pattern = Pattern.compile("\"([^\"\\\\]|\\\\.)*\"");

  @Test
  void matchesSimpleString() {
    assertTrue(pattern.matcher("\"abc\"").find());
  }

  @Test
  void matchesStringWithEscapes() {
    assertTrue(pattern.matcher("\"a \\\" b\"").find());
  }

  @Test
  void matchesStringContainingCommentSymbols() {
    assertTrue(pattern.matcher("\"test // not comment\"").find());
    assertTrue(pattern.matcher("\"test /* not comment */\"").find());
  }

  @Test
  void doesNotMatchUnclosedString() {
    assertFalse(pattern.matcher("\"abc").find());
  }
}
