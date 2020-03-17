# Jar Editor

## Overview 
Graphical application allowing make changes in .jar files: 
- adding/deleting methods
- adding /classes/constructors/fields
- overriding constructors/methods

Application allow you to take any .jar file and using java reflection .class tree.
Then from within application you can change .class files. Then you can generate new .jar file with added code within jar editor app
During generating new .jar file, app might have stayed unresponsive. To avoid this problem generating new .jar file works on another thread


## Technology used 
- JavaAssist by JBoss 
- Javafx for GUI
## Preview

![main-view](https://drive.google.com/uc?export=view&id=1bJje9_Db0qj_66Iu8h3WMx_zlRL_EnFe)

![error-view](https://drive.google.com/uc?export=view&id=1nwQHUaTItJTf3yqprk_o7zdr651nawPo)

## Running application

In project folder containing pom.xml<br>
`mvn clean install`<br>
Maven will generate target folder will .jar file. In target folder run command:<br>
`java -jar jar-editor-1.0-shaded.jar`
