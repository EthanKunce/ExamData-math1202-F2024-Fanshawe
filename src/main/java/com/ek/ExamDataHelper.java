package com.ek;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import com.ek.ExamData.ParentalEducation;
import com.ek.ExamData.Races;

public class ExamDataHelper {

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
        JComboBox<String> scoreSelectBox = new JComboBox<String>();
        JComboBox<String> enumSelectBox = new JComboBox<String>();

        scoreSelectBox.addItem("Math Scores");
        scoreSelectBox.addItem("Writing Scores");
        scoreSelectBox.addItem("Reading Scores");

        enumSelectBox.addItem("Ethnic Groups");
        enumSelectBox.addItem("Parental Education");
        // JButton testButton = new JButton("Test");
        JPanel topPanel = new JPanel();
        topPanel.add(scoreSelectBox);
        topPanel.add(enumSelectBox);
        
        // topPanel.add(testButton);
        
        ActionListener actionListener = new ActionListener() {
            @Override public void actionPerformed(ActionEvent arg0){
                int scoreIndex = scoreSelectBox.getSelectedIndex();
                int enumIndex = enumSelectBox.getSelectedIndex();
                window.getContentPane().removeAll();
                // window.getContentPane().add(chartPanels[scoreIndex][enumIndex], BorderLayout.CENTER);
                
                window.getContentPane().add(topPanel, BorderLayout.NORTH);
                window.getContentPane().add(barGraphScores(arr, scoreIndex, enumIndex), BorderLayout.CENTER);
                window.validate();
                window.repaint();
            } 
        };

        scoreSelectBox.addActionListener(actionListener);
        enumSelectBox.addActionListener(actionListener);

        window.getContentPane().add(topPanel, BorderLayout.NORTH);
        window.getContentPane().add(barGraphScores(arr, 0, 0), BorderLayout.CENTER);

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
            int enumOrdinance = enumIndex == 0 ? student.group.ordinal() : student.pe.ordinal();
            switch (scoreIndex) {
                case 0:             //Math Case
                    // based on the enumeated value of the students group, add their Math score with their groups
                    groupAvg[enumOrdinance] += student.mathScore;
                    valueAxis = "Math Scores";
                    break;
                case 1:             //writing Case
                    // based on the enumeated value of the students group, add their Math score with their groups
                    groupAvg[enumOrdinance] += student.writingScore;
                    valueAxis = "Writing Scores";
                    break;
                case 2:             //reading Case
                    // based on the enumeated value of the students group, add their Math score with their groups
                    groupAvg[enumOrdinance] += student.readingScore;
                    valueAxis = "Reading Scores";
                    break;
                default:
                    break;
            }
            // based on the enumeated value of the students group, increment their number of students
            groupCounts[enumOrdinance]++;
        }

        for (int i = 0; i < numGroups; i++) {
            String valueAxisString = enumIndex == 0 ? ExamData.toString(Races.values()[i]) : ExamData.toString(ParentalEducation.values()[i]);
            groupAvg[i] /= groupCounts[i];
            // This line prevents our default empty enum GroupNULL from being printed
            if(groupAvg[i] != 0 && groupCounts[i] != 0)
                examdataRaces.setValue(groupAvg[i], categoryAxis, valueAxisString); // This sets the value for each bar
        }

        

        String chartTitle = categoryAxis + " and " + valueAxis;
        // dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createBarChart(chartTitle, categoryAxis, valueAxis, examdataRaces);
        // window.add(new ChartPanel(chart), BorderLayout.CENTER);

        return new ChartPanel(chart);

    }

}
