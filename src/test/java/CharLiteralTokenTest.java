import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class CharLiteralTokenTest {

  private final Pattern pattern = Pattern.compile("'([^'\\\\]|\\\\.)'");

  @Test
  void matchesSimpleChar() {
    assertTrue(pattern.matcher("'a'").find());
  }

  @Test
  void matchesEscapedChar() {
    assertTrue(pattern.matcher("'\\n'").find());
  }

  @Test
  void doesNotMatchTooManyChars() {
    assertFalse(pattern.matcher("'ab'").find());
  }
}
