import static org.junit.jupiter.api.Assertions.*;

import highlighting.regex.RegexHighlighter;
import org.junit.jupiter.api.Test;

public class RegexHighlighterTest {
  // Testet einfachen Fall ohne Überlappungen
  @Test
  void noConflicts() {
    var h = new RegexHighlighter();
    String text = "class Test";

    var r = h.computeRegions(text);

    assertFalse(r.isEmpty());
    assertEquals("class", text.substring(r.get(0).start(), r.get(0).end()));
  }

  // Keyword innerhalb eines Kommentars,
  @Test
  void keywordInsideCommentIsIgnored() {
    var h = new RegexHighlighter();
    String text = "/* class */";

    var r = h.computeRegions(text);

    assertEquals(1, r.size());
    assertEquals("/* class */", text.substring(r.get(0).start(), r.get(0).end()));
  }

  // Javadoc‑Kommentar, der auch vom normalen Blockkommentar‑Token matchbar wäre
  @Test
  void javadocBeatsBlockComment() {
    var h = new RegexHighlighter();
    String text = "/** abc */";

    var r = h.computeRegions(text);

    assertEquals(1, r.size());
    assertEquals("/** abc */", text.substring(r.get(0).start(), r.get(0).end()));
  }

  // Fall mit aufeinanderfolgenden Regionen
  @Test
  void adjacentRegionsAreAllowed() {
    var h = new RegexHighlighter();
    String text = "class class";

    var r = h.computeRegions(text);

    assertEquals(2, r.size());
    assertEquals("class", text.substring(r.get(0).start(), r.get(0).end()));
    assertEquals("class", text.substring(r.get(1).start(), r.get(1).end()));
  }

  // Leerstring bzw. Text ohne Matches
  @Test
  void emptyStringProducesNoRegions() {
    var h = new RegexHighlighter();

    var r = h.computeRegions("");

    assertTrue(r.isEmpty());
  }
}
