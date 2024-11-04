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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.ek.ExamData.Races;

public class ExamDataUser {
    public static void main(String[] args) throws Exception{
        String PATH = "C:\\Users\\Ethan\\Documents\\VsCode\\examsPlay\\ExamAnalysis\\resources\\exams.csv";
        ExamData arr[] = tokenizeFile(PATH);

        for(int i = 0; i < arr.length; i++){
            System.out.println("i = " + i + ": " + arr[i].toString());
        }        

        drawGraph(arr);
        
    }

    public static void drawGraph(ExamData arr[]){
        

        


        //Create and configure the window
        JFrame window = new JFrame();
        window.setTitle("Graph1");
        window.setSize(600, 400);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        // Create a line graph
        XYSeries series = new XYSeries("Math Scores");
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        DefaultCategoryDataset examdataRaces = new DefaultCategoryDataset();
        // for(ExamData student : arr)

        // Calculate the avg of each group;
        int groupCounts[] = {0, 0, 0, 0, 0};
        double groupAvg[] = new double[5];

        // for(ExamData student : arr){
        //     switch(student.group){
        //         case GroupA:
        //         groupAvg[0] += student.mathScore;
        //         groupCounts[0]++;
        //         break;
        //     case GroupB:
        //     groupAvg[1] += student.mathScore;
        //     groupCounts[1]++;
        //         break;
        //     case GroupC:
        //     groupAvg[2] += student.mathScore;
        //     groupCounts[2]++;
        //         break;
        //     case GroupD:
        //     groupAvg[3] += student.mathScore;
        //     groupCounts[3]++;
        //         break;                    
        //     case GroupE:
        //     groupAvg[4] += student.mathScore;
        //     groupCounts[4]++;
        //         break;
        //     default:
        //         break;
        //     }
        // }

        for(ExamData student : arr){
            groupAvg[student.group.ordinal()] += student.mathScore;
            groupCounts[student.group.ordinal()]++;
        }

        for(int i = 0; i < groupAvg.length; i++){
            groupAvg[i] /= groupCounts[i];
            examdataRaces.setValue(groupAvg[i], "Ethnic Groups", Races.values()[i]);
        }
        // dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createBarChart("Ethnicity and Math Scores", "Ethnic Groups", "Math Scores", examdataRaces);
        window.add(new ChartPanel(chart), BorderLayout.CENTER);


        // Show the Window (Keep this at the end)
        window.setVisible(true);
    }

    public static ExamData [] tokenizeFile(String Path) throws Exception {
        Scanner fileInput = new Scanner(new File(Path));
        int index = 0;
        // .useDelimiter("[\"]+");
        // StringBuilder wholeFile = new StringBuilder();
        // true if lunch is reduced/Free, false otherwise
        Boolean tlunchReduced;
        Boolean ttestPrepCourse;
        Boolean tgender;
        ExamData.Races tgroup;
        ExamData.ParentalEducation tpe;
        int tmathScore;
        int treadingScore;
        int twritingScore;

        ExamData store [] = new ExamData[100];

        while(fileInput.hasNext())
        {
            // System.out.println(fileInput.nextLine());
            String []temp = fileInput.nextLine().split("[\",]+");

            // for (int i = 0; i < temp.length; i++) {
            //     System.out.println("i = " + i + ": " + temp[i]);
            // }

            tgender = temp[1].equals("male");
        
            switch(temp[2]){
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
            switch(temp[3]){
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
