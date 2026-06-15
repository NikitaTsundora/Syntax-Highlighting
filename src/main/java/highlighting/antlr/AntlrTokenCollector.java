package highlighting.antlr;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaColours;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.*;

public class AntlrTokenCollector extends SyntaxHighlighter {

  @Override
  public List<HighlightRegion> collectMatches(String text) {

    List<HighlightRegion> regions = new ArrayList<>();

    MiniJavaLexer lexer = new MiniJavaLexer(CharStreams.fromString(text));
    CommonTokenStream stream = new CommonTokenStream(lexer);
    stream.fill();
    List<Token> tokens = stream.getTokens();

    for (int i = 0; i < tokens.size(); i++) {
      Token t = tokens.get(i);

      if (t.getType() == Token.EOF) {
        continue;
      }

      if (t.getType() == MiniJavaLexer.AT) {
        regions.add(
            new HighlightRegion(
                t.getStartIndex(), t.getStopIndex() + 1, MiniJavaColours.ANNOTATION_COLOUR));
        if (i + 1 < tokens.size()) {
          Token next = tokens.get(i + 1);
          if (next.getType() == MiniJavaLexer.IDENTIFIER) {
            regions.add(
                new HighlightRegion(
                    next.getStartIndex(),
                    next.getStopIndex() + 1,
                    MiniJavaColours.ANNOTATION_COLOUR));
          }
        }

        continue;
      }

      Color c = colourFor(t);
      if (c != null) {
        regions.add(new HighlightRegion(t.getStartIndex(), t.getStopIndex() + 1, c));
      }
    }

    return regions;
  }

  private Color colourFor(Token t) {
    int type = t.getType();

    // Keywords
    switch (type) {
      case MiniJavaLexer.PACKAGE:
      case MiniJavaLexer.IMPORT:
      case MiniJavaLexer.CLASS:
      case MiniJavaLexer.PUBLIC:
      case MiniJavaLexer.PRIVATE:
      case MiniJavaLexer.FINAL:
      case MiniJavaLexer.RETURN:
      case MiniJavaLexer.NULL:
      case MiniJavaLexer.NEW:
      case MiniJavaLexer.IF:
      case MiniJavaLexer.ELSE:
      case MiniJavaLexer.WHILE:
      case MiniJavaLexer.EXTENDS:
      case MiniJavaLexer.IMPLEMENTS:
        return MiniJavaColours.KEYWORD_COLOUR;
    }

    // Literale
    if (type == MiniJavaLexer.STRING_LITERAL) return MiniJavaColours.STRING_LITERAL_COLOUR;
    if (type == MiniJavaLexer.CHAR_LITERAL) return MiniJavaColours.CHAR_LITERAL_COLOUR;

    // Kommentare
    if (type == MiniJavaLexer.LINE_COMMENT) return MiniJavaColours.LINE_COMMENT_COLOUR;
    if (type == MiniJavaLexer.BLOCK_COMMENT) return MiniJavaColours.BLOCK_COMMENT_COLOUR;
    if (type == MiniJavaLexer.JAVADOC_COMMENT) return MiniJavaColours.JAVADOC_COMMENT_COLOUR;
    return null;
  }
}
