package long_method_lines;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LongMethodLinesVisitor extends VoidVisitorAdapter<List<LongMethodLines>> {
	
	private int linesLimit;
	private String pathName;
	
	public LongMethodLinesVisitor(String pathName, int linesLimit) {
		this.pathName = pathName;
		this.linesLimit = linesLimit;
	}
	
	@Override
	public void visit(MethodDeclaration md, List<LongMethodLines> methodNumLines) {
		super.visit(md, methodNumLines);
		
		String[] lines = md.toString().split("\r\n|\r|\n");
		if (lines.length > linesLimit) {
			methodNumLines.add(new LongMethodLines(pathName, md, lines.length));
		}
	}

}
