package com.example.simplecrudapp;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseRVModal implements Parcelable{

    // create variables for fields
    private String courseId;
    private String courseName;
    private String courseDescription;
    private String coursePrice;
    private String bestSuitedFor;
    private String courseImg;

    public CourseRVModal(String courseID, String courseName, String courseDesc, String coursePrice, String courseSuited, String courseImg){

        this.courseId = courseID;
        this.courseName = courseName;
        this.courseDescription = courseDesc;
        this.coursePrice = coursePrice;
        this.bestSuitedFor = courseSuited;
        this.courseImg = courseImg;

    }

    // setter getter
    public String getCourseId(){
        return courseId;
    }

    public void setCourseId(String courseId){
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getBestSuitedFor() {
        return bestSuitedFor;
    }

    public void setBestSuitedFor(String bestSuitedFor) {
        this.bestSuitedFor = bestSuitedFor;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    // empty constructor
    public CourseRVModal(){


    }

    protected CourseRVModal(Parcel parcel){

        courseId = parcel.readString();
        courseName = parcel.readString();
        courseDescription = parcel.readString();
        coursePrice = parcel.readString();
        bestSuitedFor = parcel.readString();
        courseImg = parcel.readString();

    }

    public static final Creator<CourseRVModal> CREATOR = new Creator<CourseRVModal>(){
        @Override
        public CourseRVModal createFromParcel(Parcel parcel){
            return new CourseRVModal(parcel);
        }

        @Override
        public CourseRVModal[] newArray(int i){
            return new CourseRVModal[i];
        }
    };

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){

        dest.writeString(courseId);
        dest.writeString(courseName);
        dest.writeString(courseDescription);
        dest.writeString(coursePrice);
        dest.writeString(bestSuitedFor);
        dest.writeString(courseImg);

    }

}
