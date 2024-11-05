/*
 * Author:  Ethan Kunce 
 * Date:    oct 31
 * Name:    ExamDataUser
 * Desc:    A main program with helper methods to input the exams.csv as an array of ExamData class.  
 */
package com.ek;

public class ExamDataUser {
    // The tokenizeFile contains file I/O manipulation and may throw an Exception, if main calls it then main must also throw Exception
    public static void main(String[] args) throws Exception {
        String PATH = "resources\\exams.csv";
        ExamData arr[] = ExamData.tokenizeFile(PATH);

        for (int i = 0; i < arr.length; i++) {
            System.out.println("i = " + i + ": " + arr[i].toString());
        }
        
        ExamDataHelper.drawWindow(arr);
    }

}
