import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class KeywordTokenTest {

  private final Pattern pattern =
      Pattern.compile("\\b(package|import|class|public|private|final|return|null|new)\\b");

  @Test
  void matchesKeywordAtStart() {
    assertTrue(pattern.matcher("class A").find());
  }

  @Test
  void matchesKeywordInMiddle() {
    assertTrue(pattern.matcher("public class Test").find());
  }

  @Test
  void matchesKeywordAtEnd() {
    assertTrue(pattern.matcher("return").find());
  }

  @Test
  void doesNotMatchInsideIdentifier() {
    assertFalse(pattern.matcher("className").find());
    assertFalse(pattern.matcher("returnValue").find());
  }

  @Test
  void keywordMatchesOnlyWholeWords() {
    assertTrue(pattern.matcher("return").find());
    assertFalse(pattern.matcher("returnValue").find());
  }
}
