package highlighting;

import highlighting.antlr.*;
import java.nio.file.*;
import org.antlr.v4.runtime.*;

public class PrettyPrintDemo {

  public static void main(String[] args) throws Exception {

    // 1. Eingabe laden
    String input = Files.readString(Path.of("example.minijava"));

    // 2. Lexer + Parser
    MiniJavaLexer lexer = new MiniJavaLexer(CharStreams.fromString(input));
    MiniJavaParser parser = new MiniJavaParser(new CommonTokenStream(lexer));

    var tree = parser.compilationUnit();

    // 3. Fragen Sie auf der Konsole nach der gewünschten Anzahl von Leerzeichen pro Einrückstufe
    System.out.print("Indent width: ");
    int indent = new java.util.Scanner(System.in).nextInt();

    // 4. Pretty Printer ausführen
    PrettyPrinterVisitor pp = new PrettyPrinterVisitor(indent);
    pp.visit(tree);

    // 5. Ausgabe
    System.out.println("\nPretty Printed Code:\n");
    System.out.println(pp.result());
  }
}
