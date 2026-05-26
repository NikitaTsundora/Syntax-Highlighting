import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class LineCommentTokenTest {

  private final Pattern pattern = Pattern.compile("//.*");

  @Test
  void matchesLineComment() {
    assertTrue(pattern.matcher("// test").find());
  }

  @Test
  void matchesLineCommentAtEnd() {
    assertTrue(pattern.matcher("abc // test").find());
  }

  @Test
  void commentTokenMatchesWholeLine() {
    assertTrue(pattern.matcher("// return").find());
  }
}
