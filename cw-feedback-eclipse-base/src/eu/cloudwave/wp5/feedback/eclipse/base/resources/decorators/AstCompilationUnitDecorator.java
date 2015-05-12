package eu.cloudwave.wp5.feedback.eclipse.base.resources.decorators;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.base.AbstractCompilationUnitDecorator;

/**
 * A decorator for {@link ICompilationUnit}'s that adds functionality to fetch its Abstract Syntax Tree.
 */
public class AstCompilationUnitDecorator extends AbstractCompilationUnitDecorator implements ICompilationUnit {

  private CompilationUnit astRoot;

  private AstCompilationUnitDecorator(final ICompilationUnit compilationUnit) {
    super(compilationUnit);
  }

  /**
   * Reads current {@link ICompilationUnit} (i.e. a Java source file) and creates the AST DOM. Returns the root node of
   * the AST which is of type {@link CompilationUnit} (not to be confused with {@link ICompilationUnit}.
   * 
   * @return the root node of the AST
   */
  public CompilationUnit getAst() {
    if (astRoot == null) {
      astRoot = createAst();
    }
    return astRoot;
  }

  private CompilationUnit createAst() {
    // advise the parser to parse the code following to the Java Language Specification, Fourth Edition
    @SuppressWarnings("deprecation")
    // TODO: remove warning 
	final ASTParser parser = ASTParser.newParser(AST.JLS4);

    // tells the parser, that it has to expect an ICompilationUnit as input
    parser.setKind(ASTParser.K_COMPILATION_UNIT);

    parser.setSource(compilationUnit);

    // binding service has to be explicitly requested at parse times
    parser.setResolveBindings(true);

    return (CompilationUnit) parser.createAST(new NullProgressMonitor());
  }

  public static AstCompilationUnitDecorator of(final ICompilationUnit compilationUnit) {
    return new AstCompilationUnitDecorator(compilationUnit);
  }

}
