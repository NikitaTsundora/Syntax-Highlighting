package highlighting.antlr;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public final class PrettyPrinterVisitor extends MiniJavaBaseVisitor<Void> {

  private final StringBuilder out = new StringBuilder();
  private final int indentWidth;
  private int currentIndent = 0;
  private boolean atLineStart = true;

  // For simple spacing between tokens:
  private Token lastToken = null;

  public PrettyPrinterVisitor(int indentWidth) {
    this.indentWidth = Math.max(0, indentWidth);
  }

  public String result() {
    return out.toString();
  }

  @Override
  public Void visitCompilationUnit(MiniJavaParser.CompilationUnitContext ctx) {

    // packageDecl (falls vorhanden)
    if (ctx.packageDecl() != null) {
      visit(ctx.packageDecl());
      nl();
      nl();
    }

    // importDecls (jeweils eine Zeile)
    for (var imp : ctx.importDecl()) {
      visit(imp);
      nl();
    }
    if (!ctx.importDecl().isEmpty()) nl();

    // typeDecls (z.B. Klassen)
    for (var t : ctx.typeDecl()) {
      visit(t);
      nl();
      nl();
    }

    return null;
  }

  @Override
  public Void visitClassBody(MiniJavaParser.ClassBodyContext ctx) {

    writeln("{");
    currentIndent++;

    // classBodyDeclaration*
    for (var decl : ctx.classBodyDeclaration()) {
      indent();
      visit(decl);
      nl();
    }

    currentIndent--;
    write("}");
    return null;
  }

  @Override
  public Void visitBlock(MiniJavaParser.BlockContext ctx) {
    writeln("{");
    currentIndent++;

    // blockStatement*
    for (var stmt : ctx.blockStatement()) {
      indent();
      visit(stmt);
      nl();
    }

    currentIndent--;
    indent();
    write("}");

    return null;
  }

  @Override
  public Void visitStatement(MiniJavaParser.StatementContext ctx) {

    // 1. Block
    if (ctx.block() != null) {
      visit(ctx.block());
      return null;
    }

    // 2. return ... ;
    if (ctx.RETURN() != null) {
      write("return");
      if (ctx.expression() != null) {
        write(" ");
        visit(ctx.expression());
      }
      write(";");
      return null;
    }

    // 3. if (...) statement (else statement)?
    if (ctx.IF() != null) {
      write("if (");
      visit(ctx.expression());
      write(") ");

      // then-branch
      visit(ctx.statement(0));

      // else-branch
      if (ctx.ELSE() != null) {
        write(" else ");
        visit(ctx.statement(1));
      }
      return null;
    }

    // 4. while (...) statement
    if (ctx.WHILE() != null) {
      write("while (");
      visit(ctx.expression());
      write(") ");
      visit(ctx.statement(0));
      return null;
    }

    // 5. expression ;
    if (ctx.expression() != null) {
      visit(ctx.expression());
      write(";");
      return null;
    }

    return null;
  }

  // ---------------- helper methods ----------------

  private void indent() {
    if (atLineStart) {
      out.repeat(" ", Math.max(0, indentWidth * currentIndent));
      atLineStart = false;
    }
  }

  private void write(String s) {
    if (s == null || s.isEmpty()) return;
    indent();
    out.append(s);
  }

  private void nl() {
    out.append('\n');
    atLineStart = true;
    lastToken = null; // Reset spacing context at the beginning of a line
  }

  private void writeln(String s) {
    write(s);
    nl();
  }

  // --------------- token output + basic spacing ---------------

  @Override
  public Void visitTerminal(TerminalNode node) {
    Token t = node.getSymbol();
    String text = t.getText();

    if (lastToken != null) {
      int prevType = lastToken.getType();
      int curType = t.getType();

      // Simple heuristic: insert a space between "word-like" tokens
      if (needsSpaceBetween(prevType, curType)) write(" ");
    }

    write(text);
    lastToken = t;
    return null;
  }

  private boolean needsSpaceBetween(int prevType, int curType) {
    return isWordLike(prevType) && isWordLike(curType);
  }

  private boolean isWordLike(int type) {
    return type == MiniJavaLexer.IDENTIFIER
        || type == MiniJavaLexer.STRING_LITERAL
        || type == MiniJavaLexer.CHAR_LITERAL
        || type == MiniJavaLexer.NULL
        || type == MiniJavaLexer.PACKAGE
        || type == MiniJavaLexer.IMPORT
        || type == MiniJavaLexer.CLASS
        || type == MiniJavaLexer.PUBLIC
        || type == MiniJavaLexer.PRIVATE
        || type == MiniJavaLexer.FINAL
        || type == MiniJavaLexer.RETURN
        || type == MiniJavaLexer.NEW
        || type == MiniJavaLexer.IF
        || type == MiniJavaLexer.ELSE
        || type == MiniJavaLexer.WHILE
        || type == MiniJavaLexer.EXTENDS
        || type == MiniJavaLexer.IMPLEMENTS;
  }
}
