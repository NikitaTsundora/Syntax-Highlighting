package highlighting.regex;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaTokens;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class RegexHighlighter extends SyntaxHighlighter {

  @Override
  public List<HighlightRegion> collectMatches(String text) {
    List<HighlightRegion> result = new ArrayList<>();
    for (Token token : MiniJavaTokens.defaultTokens()) {
      Matcher m = token.pattern().matcher(text);
      while (m.find()) {
        int start = m.start();
        int end = m.end();
        result.add(new HighlightRegion(start, end, token.colour()));
      }
    }
    return result;
  }

  @Override
  public List<HighlightRegion> resolveConflicts(List<HighlightRegion> normalized) {
    List<HighlightRegion> result = new ArrayList<>();
    for (HighlightRegion r : normalized) {
      boolean overlaps = false;
      for (HighlightRegion s : result) {
        if (r.start() < s.end() && s.start() < r.end()) {
          overlaps = true;
          break;
        }
      }
      if (!overlaps) {
        result.add(r);
      }
    }
    return result;
  }
}
