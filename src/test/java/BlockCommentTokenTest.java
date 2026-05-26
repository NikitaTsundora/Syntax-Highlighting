import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class BlockCommentTokenTest {

  private final Pattern pattern = Pattern.compile("/\\*[^*][\\s\\S]*?\\*/");

  @Test
  void matchesSimpleBlockComment() {
    assertTrue(pattern.matcher("/* test */").find());
  }

  @Test
  void matchesBlockCommentInMiddle() {
    assertTrue(pattern.matcher("abc /* test */ def").find());
  }

  @Test
  void doesNotMatchJavadoc() {
    assertFalse(pattern.matcher("/** javadoc */").find());
  }
}
