package app;
public class Main {
	
	private static final String FILE_PATH = "CS409TestSystem2019/src";

	public static void main(String[] args) {
		
		CodeSmellsDetector detector = new CodeSmellsDetector(FILE_PATH);
		detector.run();
		
	}

}
