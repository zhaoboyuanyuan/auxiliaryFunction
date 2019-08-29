adb pull /sdcard/robotium/TEST-all.xml D:\code\auxiliaryFunction\src\testOutput\runner\until
d:
cd \code\auxiliaryFunction\src\testOutput\runner\until
java -classpath serializer.jar -jar xalan.jar -IN TEST-all.xml -XSL rejunit-noframes.xsl -OUT junit-report2.html
