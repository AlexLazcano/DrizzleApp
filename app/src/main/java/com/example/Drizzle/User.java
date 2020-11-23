package com.example.Drizzle;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;

/**
 * user class will store the information of the user
 * @author Haoyuan Zhao & Markus Mattila
 * @version 1
 * @since 2020-11-6
 */

public class User {
    private String name;
    private int userId;
    private List<Integer> myGroupId;
    private String postalCode;
    private String gender;
    //private int transportation;
    private String major;
    private String minor;
    private int personality;
    private List<String> currentClassed;
    private String biography;
    private float rating;
    private String school;
    private List<String> favPastClass;
    private List<Integer> friendList;

    public User(){}

    //constructor:
    public User(UserPool newPool){
        this.myGroupId = new LinkedList<>();
        this.currentClassed = new LinkedList<>();
        this.favPastClass = new LinkedList<>();
        this.friendList = new LinkedList<>();
        newPool.addUser(this);
    }

    //getter:

    public String getName() {
        return name;
    }
    public int getUserId() {
        return userId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getGender() {
        return gender;
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }

    public int getPersonality() {
        return personality;
    }

    public List<String> getCurrentClassed() {
        return currentClassed;
    }

    public String getBiography() {
        return biography;
    }

    public float getRating() {
        return rating;
    }

    public String getSchool() {
        return school;
    }

    public List<String> getFavPastClass() {
        return favPastClass;
    }

    public List<Integer> getMyGroup() {
        return myGroupId;
    }

    //setter:
    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public void setPersonality(int personality) {
        this.personality = personality;
    }

    public void setCurrentClassed(List<String> currentClassed) {
        this.currentClassed = currentClassed;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setFavPastClass(List<String> favPastClass) {
        this.favPastClass = favPastClass;
    }

    /**
     *  Add the group into this.mygroup, call addMember() for newGroup if
     *  newGroup.groupMember currently not contain this user
     * @param newGroupId target group
     */
    public void joinGroup(int newGroupId){
        this.myGroupId.add(newGroupId);
        DocumentReference userListGetter = FirebaseFirestore.getInstance().document("GroupList/"+newGroupId);
        userListGetter.update("groupMember", FieldValue.arrayUnion(this.getUserId()));
        return;
    }

    /**
     *  remove the group from this.mygroup, call quitMember() for newGroup if
     *  newGroup.groupMember currently still contain this user
     * @param newGroupId target group
     */
    public void leaveGroup(int newGroupId){
        this.myGroupId.remove(newGroupId);
        DocumentReference userListGetter = FirebaseFirestore.getInstance().document("GroupList/"+newGroupId);
        userListGetter.update("groupMember", FieldValue.arrayRemove(this.getUserId()));
        return;
    }

    /**
     * print user information, only for test
     */
    public void printUserInfo(){
        System.out.println("User name: " + name +
                            "\nUserId: "+ userId +
                            "\nPostal code: "+ postalCode +
                            "\nGender: "+ gender +
                            "\nMajor: "+ major +
                            "\nMinor: "+ minor +
                            "\nPersonality: "+ personality +
                            "\nCurrent enrolled classes: "+ currentClassed +
                            "\nBiography: "+ biography +
                            "\nRating: "+ rating +
                            "\nSchool: "+ school +
                            "\nFavourite past class: "+ favPastClass +"\n");
        return;
    }

//    /**
//     * print group information for this user, only for test
//     */
//    public void printMyGroupInfo(){
//        System.out.println(this.getName() + " have " + this.myGroupId.size() + " groups:\n");
//        for ( int i = 0; i < this.myGroupId.size(); ++i) {
//            System.out.println( "Group name " + myGroupId.get(i).getGroupName() +
//                                "\nGroup Id " + myGroupId.get(i).getGroupId()+"\n");
//        }
//    }

    /**
     * Produce a numerical value representing similarity between users. Simplified, for now.
     */
    public double simIndex(User compareTo)
    {
        final int NUM_OF_REL_ATTS = 6;
        float index = 0;

        if (major.equals(compareTo.getMajor())) index++;
        if (minor.equals(compareTo.getMinor())) index++;
        if (personality == compareTo.getPersonality()) index++; //SLOPPY. Change later?
        if (major.equals(compareTo.getMajor())) index++;
        if (minor.equals(compareTo.getMinor())) index++;
        if (school.equals(compareTo.getSchool())) index++;
        if (favPastClass == compareTo.getFavPastClass()) index++;

        return index / NUM_OF_REL_ATTS;
    }

    public int addFriend(int newFriendId){
        this.friendList.add(newFriendId);
        DocumentReference friendListGetter = FirebaseFirestore.getInstance().document("UserList/"+newFriendId);
        friendListGetter.update("friendList", FieldValue.arrayUnion(this.getUserId()));
        return 1;
    }

    public int removeFriend(int rmvFriendId){
        this.friendList.remove(rmvFriendId);
        DocumentReference friendListGetter = FirebaseFirestore.getInstance().document("UserList/"+rmvFriendId);
        friendListGetter.update("friendList", FieldValue.arrayRemove(this.getUserId()));
        return 1;
    }

    public List<Integer> getFriendList() {
        return friendList;
    }

}
