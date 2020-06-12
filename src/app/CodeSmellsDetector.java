package app;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import data_class.DataClassDetector;
import feature_envy.FeatureEnvyDetector;
import long_class_lines.LongClassLinesDetector;
import long_class_statements.LongClassStatementsDetector;
import long_method_lines.LongMethodLinesDetector;
import long_methods_statements.LongMethodStatementsDetector;
import long_parameter_list.LongParameterListDetector;
import message_chains.MessageChainsDetector;
import temporary_fields.TemporaryFieldsDetector;

public class CodeSmellsDetector {
	
	private static final int METHOD_LINES_LIMIT = 20;
	private static final int CLASS_LINES_LIMIT = 100;
	private static final int METHOD_PARAMS_LIMIT = 5;
	private static final int METHOD_STATEMENTS_LIMIT = 20;
	private static final int CLASS_STATEMENTS_LIMIT = 100;
	
	List<File> javaFiles;
	
	public CodeSmellsDetector(final String filePath) {
		JavaFileAccessor jfa = new JavaFileAccessor(new File(filePath));
		javaFiles = jfa.getJavaFiles();
	}
	
	public void run() {
		
		Map<String, CompilationUnit> compilationUnits = new HashMap<String, CompilationUnit>();
		for (File file : javaFiles) {
			try {
				CompilationUnit cu = StaticJavaParser.parse(file);
				compilationUnits.put(file.getPath(), cu);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		List<Analyser> analysers = new ArrayList<Analyser>();
		analysers.add(new LongMethodLinesDetector(compilationUnits, METHOD_LINES_LIMIT));
		analysers.add(new LongClassLinesDetector(compilationUnits, CLASS_LINES_LIMIT));
		analysers.add(new LongParameterListDetector(compilationUnits, METHOD_PARAMS_LIMIT));
		analysers.add(new LongMethodStatementsDetector(compilationUnits, METHOD_STATEMENTS_LIMIT));
		analysers.add(new LongClassStatementsDetector(compilationUnits, CLASS_STATEMENTS_LIMIT));
		analysers.add(new TemporaryFieldsDetector(compilationUnits));
		analysers.add(new MessageChainsDetector(compilationUnits));
		analysers.add(new DataClassDetector(compilationUnits));
		analysers.add(new FeatureEnvyDetector(compilationUnits));
		analysers.forEach(analyser -> {
			analyser.findAllSmells();
			analyser.printAllSmells();
			System.out.println();
		});
		
	}
	
}
