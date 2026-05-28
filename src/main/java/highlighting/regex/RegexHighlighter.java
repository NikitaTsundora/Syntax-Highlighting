package highlighting.regex;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaTokens;
import java.util.ArrayList;
import java.util.List;

public class RegexHighlighter extends SyntaxHighlighter {

  @Override
  public List<HighlightRegion> collectMatches(String text) {
    List<HighlightRegion> result = new ArrayList<>();
    for (Token token : MiniJavaTokens.defaultTokens()) {
      result.addAll(token.test(text));
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
