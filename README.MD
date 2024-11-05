# Exam Analysis
Exam Analysis project is made up of three java files and it parses the exams.csv file for charting. ExamData.java holds a class that stores records from exams.csv as objects. ExamDataHelper.java takes a look at the java UI and also imports JFreeChart to quickly and simply draw the charts as panels. 

ExamDataUser is a user file that tests utilizing the classes.

JFreeChart is the only dependency that exists outside of the Java Common Libraries. 

# Maven and JFreeChart
I downloaded Maven 3.9.9 as a build tool and dependency manager. To download Maven see https://maven.apache.org/download.cgi.Then copy Maven's bin path to your system $PATH.

Once Maven is installed, open project directory and run `mvn clean install`

Maven will read the pom.xml file, see that JFreeChart is a depdency and install it. 

You may have vscode now run the program.

### Eclipse should have native Maven and depedency support
Under file, go to the bottom of the drop down and find the Import button. Open Maven from the list programs and select Existing Maven Projects and select the ExamData-math1202-F2024-Fanshawe directory.

You may try to run it from Eclipse. 


