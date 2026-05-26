import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class AnnotationTokenTest {

  private final Pattern pattern = Pattern.compile("@[A-Za-z-]+");

  @Test
  void matchesSimpleAnnotation() {
    assertTrue(pattern.matcher("@Override").find());
  }

  @Test
  void matchesAnnotationAtLineStart() {
    assertTrue(pattern.matcher("@Test abc").find());
  }

  @Test
  void matchesAnnotationWithDash() {
    assertTrue(pattern.matcher("@My-Annotation").find());
  }

  @Test
  void doesNotMatchInvalidAnnotation() {
    assertFalse(pattern.matcher("@123").find());
  }
}
