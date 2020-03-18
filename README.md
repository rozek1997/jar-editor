# Jar Editor

Graphical application allowing make changes in .jar files: 
- adding/deleting methods
- adding /classes/constructors/fields
- overriding constructors/methods

Application allow you to take any .jar file and using java reflection make changes within application = change .class files. Then you can generate new .jar file with added code within jar editor app
During generating new .jar file, app might have stayed unresponsive. To avoid this problem, generation of new .jar file works on another thread


## Technology used 
- JavaAssist by JBoss 
- Javafx for GUI
## Preview

### Main view

![jar_editor_after editing](https://user-images.githubusercontent.com/38226876/76954476-b11bbe00-6910-11ea-992b-9e9617148ec3.png)

### Error message

![error-message](https://user-images.githubusercontent.com/38226876/76954499-ba0c8f80-6910-11ea-9f68-41dfb6bb436a.png)


## Running application

In project folder containing pom.xml<br>
`mvn clean install`<br>
Maven will generate target folder will .jar file. In target folder run command:<br>
`java -jar jar-editor-1.0-shaded.jar`
