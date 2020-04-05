package edu.upenn.cis350.projectcopy;

import java.util.ArrayList;

public class User {
    private String name;
    private String profileURI;
    private ArrayList<Course> courses;
    private String bio;

    public User(String name, String profileURI, ArrayList<Course> courses, String bio) {
        this.name = name;
        this.profileURI = profileURI;
        this.courses = courses;
        this.bio = bio;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Course> getCourses() {
        return courses;
    }
    public String getBio(){
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileURI() {
        return profileURI;
    }
    public void setProfileURI(String profileURI) {
        this.profileURI = profileURI;
    }

}
