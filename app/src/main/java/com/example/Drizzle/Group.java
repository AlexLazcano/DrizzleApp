package com.example.Drizzle;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;

/**
 * group class will store the information of the group
 * @author Haoyuan Zhao & Markus Mattila
 * @version 1
 * @since 2020-11-6
 */

public class Group {
    private int groupId;
    private String groupName;
    private List<Integer> groupMemberId;
    private String size; // size (small, medium or large) of the Group.
    private String studyTopic; // topic of study for the Group.

    public Group(){}
    /**
     * constructor: build the list of user and add this group
     * into group list
     * @param newPool the pool be added to
     */
    public Group(UserPool newPool, String groupName, int groupId, String size, String studyTopic) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupMemberId = new LinkedList<>();

        if (size.equals("small") || size.equals("medium") || size.equals("large"))
            this.size = size; // the allocated size for the Group. So, small, medium or large.
        else
            this.size = "unspecified"; // no or invalid specified size.

        // input verification for study topic? we need a bank of options that are considered acceptable topics.
        this.studyTopic = studyTopic; // the allocated study topic. For instance, "mathematics."

        newPool.addGroup(this);
    }

    /**
     * group member getter
     * @return a list of user
     */
    public List<Integer> getGroupMember() {
        return groupMemberId;
    }

    /**
     * group id getter
     * @return group id
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * group name getter
     * @return group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * group id setter
     * @param groupId group id
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }


    /**
     * group name setter
     * @param groupName group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * add new member into the group, and also call joinGroup for user if
     * the user.mygroup currently not contain this group
     * @param newUserId the new member
     */
    public void addMember(int newUserId){
        this.groupMemberId.add(newUserId);
        DocumentReference groupListGetter = FirebaseFirestore.getInstance().document("UserList/"+newUserId);
        groupListGetter.update("myGroup", FieldValue.arrayUnion(this.getGroupId()));
        return;
    }

    /**
     * remove the user from group member, and also call leaveGroup for user if
     * the user.mygroup currently still contain this group
     * @param quitUserId the user want to quit
     */
    public void quitGroup(int quitUserId){
        this.groupMemberId.remove(quitUserId);
        DocumentReference groupListGetter = FirebaseFirestore.getInstance().document("UserList/"+quitUserId);
        groupListGetter.update("myGroup", FieldValue.arrayRemove(this.getGroupId()));
        return;
    }

    /**
     * count the member in this group
     * @return  the number of the member
     */
    public int getGroupCount(){
        return groupMemberId.size();
    }

    public String getGroupSize() // no corresponding setter.
    {
        return size;
    }

    public String getStudyTopic() // no corresponding setter.
    {
        return studyTopic;
    }

    /**
     Test to see if the group is full as per specification of small, medium and large.
     */
    public boolean isFull()
    {
        final int S_MAX = 3, M_MAX = 5, L_MAX = 8;

        return ((size.equals("small")) && (groupMemberId.size() == S_MAX)) ||
                ((size.equals("medium")) && (groupMemberId.size() == M_MAX)) ||
                ((size.equals("large")) && (groupMemberId.size() == L_MAX)); // sloppy.
    }
}
