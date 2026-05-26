package highlighting.presets;

import highlighting.regex.Token;
import java.util.List;
import java.util.regex.Pattern;

public final class MiniJavaTokens {
  public static List<Token> defaultTokens() {
    return List.of(
        // Javadoc‑Kommentare: /** ... */ (ggf. anders einfärben als normale Blockkommentare)
        Token.of(Pattern.compile("/\\*\\*[\\s\\S]*?\\*/"), MiniJavaColours.JAVADOC_COMMENT_COLOUR),

        // Mehrzeilige Kommentare: /* ... */
        Token.of(Pattern.compile("/\\*[^*][\\s\\S]*?\\*/"), MiniJavaColours.BLOCK_COMMENT_COLOUR),

        // Einzeilige Kommentare: // bis zum Zeilenende
        Token.of(Pattern.compile("//.*"), MiniJavaColours.LINE_COMMENT_COLOUR),

        // Strings: alles zwischen " und dem nächsten "
        Token.of(Pattern.compile("\"([^\"\\\\]|\\\\.)*\""), MiniJavaColours.STRING_LITERAL_COLOUR),

        // Characters: genau ein Zeichen zwischen ' und '
        Token.of(Pattern.compile("'([^'\\\\]|\\\\.)'"), MiniJavaColours.CHAR_LITERAL_COLOUR),

        // Annotationen: beginnen mit @, gefolgt von Buchstaben oder Minuszeichen, z.B. @Override
        Token.of(Pattern.compile("@[A-Za-z-]+"), MiniJavaColours.ANNOTATION_COLOUR),

        /* Keywords (als ganze Wörter, nicht als Teil anderer Bezeichner oder Kommentare o.ä.):
        package, import, class, public, private, final, return, null, new*/
        Token.of(
            Pattern.compile("\\b(package|import|class|public|private|final|return|null|new)\\b"),
            MiniJavaColours.KEYWORD_COLOUR));
  }
}
