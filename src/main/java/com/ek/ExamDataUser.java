/*
 * Author:  Ethan Kunce 
 * Date:    oct 31
 * Name:    ExamDataUser
 * Desc:    A main program with helper methods to input the exams.csv as an array of ExamData class.  
 */
package com.ek;

//Dependencies for File I/O/
import java.io.File;
import java.util.Scanner;

//Dependencies for drawing
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import com.ek.ExamData.ParentalEducation;
import com.ek.ExamData.Races;

public class ExamDataUser {
    // The tokenizeFile contains file I/O manipulation and may throw an Exception, if main calls it then main must also throw Exception
    public static void main(String[] args) throws Exception {
        String PATH = "C:\\Users\\Ethan\\Documents\\VsCode\\examsPlay\\ExamAnalysis\\resources\\exams.csv";
        ExamData arr[] = tokenizeFile(PATH);

        for (int i = 0; i < arr.length; i++) {
            System.out.println("i = " + i + ": " + arr[i].toString());
        }

        drawWindow(arr);

    }

    // Draw window is passed the Exam Data arr[] to perform analysis connections. 
    // tokenizeFile is not called in here becuase we want to isolate exceptions thrown.
    public static void drawWindow(ExamData arr[]) {

        // Create and configure the window
        JFrame window = new JFrame();
        window.setTitle("Graph1");
        window.setSize(600, 400);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // groupsMathScores is a method call that returns a ChartPanel object that can be added to our window. 
        // Note passing an entire array may be bad for memory.  The ExamData array is passed.

        // Let's create a dropdown box
        JComboBox<String> firstDropDown = new JComboBox<String>();
        JButton testButton = new JButton("Test");
        JPanel topPanel = new JPanel();
        topPanel.add(firstDropDown);
        topPanel.add(testButton);

        window.add(topPanel, BorderLayout.NORTH);

        // Populate the Drop down
        firstDropDown.addItem("Test1");
        firstDropDown.addItem("Test2");
        firstDropDown.addItem("Test3");

        // int scoreIndex = 0;
        // int enumIndex = 0;

        firstDropDown.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent arg0){
                int scoreIndex = firstDropDown.getSelectedIndex();
                
                // window.remove(0);
                window.add(barGraphScores(arr, scoreIndex, 0), BorderLayout.CENTER);
                window.transferFocus();
                window.repaint();
            } 
        });

        // window.add(barGraphScores(arr, 1, 0), BorderLayout.CENTER);


        // Show the Window (Keep this at the end)
        window.setVisible(true);
    }

    // returns a Chart Panel object containing a bar graph titled Ethnicity and Math Scores
    // It also calculates the the averages for each group
    public static ChartPanel groupsMathScores(ExamData arr[]) {
        DefaultCategoryDataset examdataRaces = new DefaultCategoryDataset();
        // for(ExamData student : arr)
        int numGroups = Races.values().length;
        // Calculate the avg of each group;
        int groupCounts[] = new int[numGroups];
        double groupAvg[] = new double[numGroups];

        for (ExamData student : arr) {
            // based on the enumeated value of the students group, add their Math score with their groups
            groupAvg[student.group.ordinal()] += student.mathScore;
            // based on the enumeated value of the students group, increment their number of students
            groupCounts[student.group.ordinal()]++;
        }

        for (int i = 0; i < numGroups; i++) {
            groupAvg[i] /= groupCounts[i];
            // This line prevents our default empty enum GroupNULL from being printed
            if(groupAvg[i] != 0 && groupCounts[i] != 0)
                examdataRaces.setValue(groupAvg[i], "Ethnic Groups", Races.values()[i]); // This sets the value for each bar
        }
        // dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createBarChart("Ethnicity and Math Scores", "Ethnic Groups", "Math Scores", examdataRaces);
        // window.add(new ChartPanel(chart), BorderLayout.CENTER);

        return new ChartPanel(chart);

    }


    public static ChartPanel barGraphScores(ExamData arr[], int scoreIndex, int enumIndex) {
        DefaultCategoryDataset examdataRaces = new DefaultCategoryDataset();
        // for(ExamData student : arr)
        int numGroups = enumIndex == 0 ? Races.values().length : ParentalEducation.values().length;
        String categoryAxis = enumIndex == 0 ? "Ethnic Groups" : "Parental Education";
        String valueAxis = "";
        // Calculate the avg of each group;
        int groupCounts[] = new int[numGroups];
        double groupAvg[] = new double[numGroups];

        for (ExamData student : arr) {
            switch (scoreIndex) {
                case 0:             //Math Case
                    // based on the enumeated value of the students group, add their Math score with their groups
                    groupAvg[student.group.ordinal()] += student.mathScore;
                    valueAxis = "Math Scores";
                    break;
                case 1:             //writing Case
                    // based on the enumeated value of the students group, add their Math score with their groups
                    groupAvg[student.group.ordinal()] += student.writingScore;
                    valueAxis = "Writing Scores";
                    break;
                case 2:             //reading Case
                    // based on the enumeated value of the students group, add their Math score with their groups
                    groupAvg[student.group.ordinal()] += student.readingScore;
                    valueAxis = "Reading Scores";
                    break;
                default:
                    break;
            }
            // based on the enumeated value of the students group, increment their number of students
            groupCounts[student.group.ordinal()]++;
        }

        for (int i = 0; i < numGroups; i++) {
            groupAvg[i] /= groupCounts[i];
            // This line prevents our default empty enum GroupNULL from being printed
            if(groupAvg[i] != 0 && groupCounts[i] != 0)
                examdataRaces.setValue(groupAvg[i], categoryAxis, enumIndex == 0 ? Races.values()[i] : ParentalEducation.values()[i]); // This sets the value for each bar
        }

        String chartTitle = categoryAxis + " and " + valueAxis;
        // dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createBarChart(chartTitle, categoryAxis, valueAxis, examdataRaces);
        // window.add(new ChartPanel(chart), BorderLayout.CENTER);

        return new ChartPanel(chart);

    }

    public static ExamData[] tokenizeFile(String Path) throws Exception {
        Scanner fileInput = new Scanner(new File(Path));
        int index = 0;
        // .useDelimiter("[\"]+");
        // StringBuilder wholeFile = new StringBuilder();
        // true if lunch is reduced/Free, false otherwise

        // ExamData does not have any overidden constructors so its easiest to just
        // create a copy of an ExamData Data and call the contructor with all parameters
        Boolean tlunchReduced;
        Boolean ttestPrepCourse;
        Boolean tgender;
        ExamData.Races tgroup;
        ExamData.ParentalEducation tpe;
        int tmathScore;
        int treadingScore;
        int twritingScore;

        ExamData store[] = new ExamData[100];

        while (fileInput.hasNext()) {
            // System.out.println(fileInput.nextLine());
            String[] temp = fileInput.nextLine().split("[\",]+");

            // for (int i = 0; i < temp.length; i++) {
            // System.out.println("i = " + i + ": " + temp[i]);
            // }

            tgender = temp[1].equals("male");

            switch (temp[2]) {
                case "group A":
                    tgroup = ExamData.Races.GroupA;
                    break;
                case "group B":
                    tgroup = ExamData.Races.GroupB;
                    break;
                case "group C":
                    tgroup = ExamData.Races.GroupC;
                    break;
                case "group D":
                    tgroup = ExamData.Races.GroupD;
                    break;
                case "group E":
                    tgroup = ExamData.Races.GroupE;
                    break;
                default:
                    tgroup = ExamData.Races.GroupNull;
                    break;
            }
            switch (temp[3]) {
                case "some high school":
                    tpe = ExamData.ParentalEducation.SHigh;
                    break;
                case "high school":
                    tpe = ExamData.ParentalEducation.High;
                    break;
                case "associate's degree":
                    tpe = ExamData.ParentalEducation.ADegree;
                    break;
                case "some college":
                    tpe = ExamData.ParentalEducation.SCollege;
                    break;
                case "bachelor's degree":
                    tpe = ExamData.ParentalEducation.BDegree;
                    break;
                case "master's degree":
                    tpe = ExamData.ParentalEducation.MDegree;
                    break;
                default:
                    tpe = ExamData.ParentalEducation.PENull;
                    break;
            }
            tlunchReduced = !temp[4].equals("standard");
            ttestPrepCourse = temp[5].equals("completed");

            tmathScore = Integer.parseInt(temp[6]);
            twritingScore = Integer.parseInt(temp[7]);
            treadingScore = Integer.parseInt(temp[8]);

            store[index] = new ExamData(tlunchReduced, ttestPrepCourse, tgender, tgroup, tpe, tmathScore, treadingScore, twritingScore);
            index++;
            // System.out.println(index);
        }

        fileInput.close();
        return store;
    }

}
