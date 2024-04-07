# How to run the project & short description
The project was made using Maven tool in IntellijIdea. To run the project, you need to 
execute the command from the main directory:
```java
java -cp .\target\OcadoBasketSplitter-1.0-SNAPSHOT-jar-with-dependencies.jar com.ocado.basket.Main
```
The complete project JAR file is located in the target directory. All *.java files are located 
in the src\main\java\com\ocado\basket directory, and all dependencies, which are JSON 
files, are located in the src\main\resources directory. If new files are added, they should 
be placed in the resources directory (this applies to configuration or test files). Then, 
update the main function in the Main class according to your requirements. Then uild 
the project again using the following command:
```java
mvn clean install
```
After successfully building the project, execute it again using the command mentioned 
earlier. In the main function, the basket splitting process is performed for the provided 
basket-1 and basket-2 files. The project uses the json-simple library to handle the 
content of JSON files.
For testing, from the main directory execute the command:
```java
mvn test
```
