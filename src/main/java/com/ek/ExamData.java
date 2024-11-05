/*
 * Author:  Ethan Kunce 
 * Date:    oct 31
 * Name:    ExamData
 * Desc:    A data structure to hold a record from exams.csv 
 */
package com.ek;
// enum Races { GroupA, GroupB, GroupC, GroupD };
// enum ParentalEducation {SHigh, High, SCollege, ADegree, BDegree, MDegree};

import java.io.File;
import java.util.Scanner;

public class ExamData{
    public enum Races { GroupA, GroupB, GroupC, GroupD, GroupE, GroupNull};
    public enum ParentalEducation {SHigh, High, SCollege, ADegree, BDegree, MDegree, PENull};
    
    // true if lunch is reduced/Free, false otherwise
    public Boolean lunchReduced;
    // True if completed prep course. False if not completed
    public Boolean testPrepCourse;
    // True if male. False if female
    public Boolean gender;

    public Races group;
    public ParentalEducation pe;

    public int mathScore;
    public int readingScore;
    public int writingScore;

    public ExamData(Boolean lunchReduced, Boolean testPrepCourse, Boolean gender, Races group, ParentalEducation pe, int mathScore, int readingScore, int writingScore){
        
        this.lunchReduced = lunchReduced;
        this.testPrepCourse = testPrepCourse;
        this.gender = gender;
        this.group = group;
        this.pe = pe;
        this.mathScore = mathScore;
        this.readingScore = readingScore;
        this.writingScore = writingScore;

    }

    public static String toString(ExamData.Races sRace){
        String sGroup;
        switch(sRace){
            case GroupA:
                sGroup = "Group A";
                break;
            case GroupB:
                sGroup = "Group B";
                break;
            case GroupC:
                sGroup = "Group C";
                break;
            case GroupD:
                sGroup = "Group D";
                break;                    
            case GroupE:
                sGroup = "Group E";
                break;
            default:
                sGroup = "Group Null";
                break;
        }
        return sGroup;
    }

    public static String toString(ExamData.ParentalEducation sPE){
        String sDegree;
        switch(sPE){            
            case SHigh:
                sDegree = "some high school";
                break;
            case High:
                sDegree = "high school";
                break;
            case ADegree:
                sDegree = "associate's degree";
                break;
            case SCollege:
                sDegree = "some college";
                break;
            case BDegree:
                sDegree = "bachelor's degree";
                break;
            case MDegree:
                sDegree = "master's degree";
                break;
            default:
                sDegree = "PENULL";
                break;             
        }     
        return sDegree;
    }


    public String toString(){
        String sGroup = toString(this.group);
        String sDegree = toString(this.pe);

        return ""  + (this.gender ? "male, " : "female, ") + sGroup + ", " + sDegree + ", " + (this.lunchReduced ? "free/reduced, " : "standard, ") + (this.testPrepCourse ? "completed, " : "none, ") + this.mathScore + ", " + this.writingScore + ", " + this.readingScore;

    }

    // Previously in ExamDataUser.java, makes more sense in the ExamData.java file as its the same class type. 
    // perhaps add support for dynamic arrays.
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
