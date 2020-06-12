# Detecting Code Smells
This project employs static analysis to detect "code smells" (warning signs) in Java software systems. The analyser is built using the JavaParser framework and makes extensive use of the Visitor design pattern.

Taxonomy for "Bad Code Smells" can be found at [http://mikamantyla.eu/BadCodeSmellsTaxonomy.html](http://mikamantyla.eu/BadCodeSmellsTaxonomy.html).

## The system detects the following code smells:
| Code smell | Result |
| - | - |
| Long Parameter List | Accurately detects all methods with more than 5 parameters. |
| Long Method (counting lines of code) | Accurately detects all methods with more than 20 lines of code (not including blank lines). |
| Large Class (counting lines of code) | Accurately detects classes with over 100 lines of code (not including blank lines). |
| Long Method (counting statements) | Accurately detects all methods with more than 20 statements. |
| Large Class (counting statements) | Accurately detects all classes with over 100 statements. |
| Data class | Detects all classes which have no methods or whose methods are only a combination of getters or setters, most Data Classes are detected. |
| Message Chains | Accurately detects all long sequences of method calls. |
| Temporary Field | Accurately detects all temporary fields. |
| Feature Envy | Accurately detects instances of Feature Envy. |
	
## To run the app:
Run ./src/app/Main.java
