/*
 * Author:  Ethan Kunce 
 * Date:    oct 31
 * Name:    ExamData
 * Desc:    A data structure to hold a record from exams.csv 
 */
package com.ek;
// enum Races { GroupA, GroupB, GroupC, GroupD };
// enum ParentalEducation {SHigh, High, SCollege, ADegree, BDegree, MDegree};

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

    public String toString(){
        String sGroup;
        switch(this.group){
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

        String sDegree;
        switch(this.pe){            
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

        return ""  + (this.gender ? "male, " : "female, ") + sGroup + ", " + sDegree + ", " + (this.lunchReduced ? "free/reduced, " : "standard, ") + (this.testPrepCourse ? "completed, " : "none, ") + this.mathScore + ", " + this.writingScore + ", " + this.readingScore;

    }

}
