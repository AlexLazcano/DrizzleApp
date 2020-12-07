package com.example.Drizzle;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;

/**
 * User pool class will manege the list of users and groups
 * @author Haoyuan Zhao & Markus Mattila
 * @version 1
 * @since 2020-11-6
 */

public class UserPool {
    private List<User> user_list;
    private List<Group> group_list;
    private String TAG = "UserPool";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Constructor:
     * build the list of user and group
     */
    public UserPool(){
        this.user_list = new LinkedList<>();
        this.group_list = new LinkedList<>();
    }

    /**
     * count the number of users
     * @return the number of user
     */
    public int numOfUsers(){
        return user_list.size();
    }

    /**
     * add a user into the user list
     * @param newUser the user be added
     */
    public void addUser(User newUser){
        user_list.add(newUser);
        return;
    }

    /**
     * search the user by user id
     * @param userId user's id
     * @return the index of target user, return -1 if not find
     */
    private int userSearch(String userId){
        for ( int i = 0;i < this.numOfUsers(); i++) {
            if (this.user_list.get(i).getUserId() == userId) {
                return i;
            }
        }
        return -1;
    }

    /**
     * count the number of groups
     * @return the number of groups
     */
    public int numOfGroups(){
        return group_list.size();
    }

    /**
     * add a group into the group list
     * @param newGroup the group be added
     */
    public void addGroup(Group newGroup){
        group_list.add(newGroup);
        return;
    }

    /**
     * search the group by group id
     * @param groupId group's id
     * @return the index of target group, return -1 if not find
     */
    public int groupSearch(int groupId){
        for ( int i = 0;i < this.numOfGroups(); i++) {
            if (this.group_list.get(i).getGroupId() == groupId) {
                return i;
            }
        }
        return -1;
    }

//    /**
//     * Clean up this user, remove him from all groups he belonged to and
//     * remove this user from user pool
//     * @param userId the user's id
//     */
//    public void writtenOff(int userId){
//        for ( int i = 0; i < this.findUserById(userId).getMyGroup().size(); ++i){
//            this.findUserById(userId).getMyGroup().get(i).quitGroup(this.findUserById(userId));
//        }
//        this.user_list.remove(this.findUserById(userId));
//        return;
//    }

    /**
     * search the user by user id
     * @param userId user's id
     * @return the target user(the class </user>), return null if not find
     */
    public User findUserById(String userId){
        int userIndex = userSearch(userId);
        if ( userIndex != -1){
            return user_list.get(userIndex);
        }
        return null;
    }

    public void printAllInfo(){
        System.out.println("Now user pool have " + this.numOfUsers() +" users, and " + this.numOfGroups() + " groups" +
                "\nThe user information are shown as following:");
        for( int i = 0; i < user_list.size(); ++i){
            this.user_list.get(i).printUserInfo();
        }
        System.out.println("The group information is shown as following:");
        for( int i = 0 ; i < group_list.size(); ++i){
            this.group_list.get(i).printGroupInfo();
        }
    }

    public String outPutAllInfo(){
        String Info = "";
        if (user_list.size() > 0){
            Info += "Now user pool have " + this.numOfUsers() +" users,The user information are shown as following:\n";
            for ( int i = 0; i < user_list.size(); ++i){
                Info += user_list.get(i).outPutUserInfo();
                Info += "\n";
            }
        }
        if (group_list.size() > 0){
            Info +=  "Now user pool have " + this.numOfGroups() +" groups,The group information are shown as following:\n";
            for ( int i = 0; i < group_list.size(); ++i){
                Info += group_list.get(i).outputGroupInfo();
                Info += "\n";
            }
        }
        return Info;
    }

    /**
     * Places a user into a corresponding (or new) group, as per similarities with existing groups. Requires refinement.
     * @param userId: The ID of the user to be strategically placed into a group.
     * @param groupSize: Preferred size of group for to-be-placed user (small, medium or large)
     * @param studyTopic: Preferrred study topic of group for to-be-placed user.
     */
    void groupMatchUser(String userId, String groupSize, String studyTopic) // searching user, user pool, user's preferred group size, topic of study
    {
        User newUser = this.findUserById(userId); // find user with which we are to operate

        if (newUser == null)
        {
            System.out.println( "User ID not found. Aborting match process...");
            return;
        }
        else if (!(groupSize == "small" || groupSize == "medium" || groupSize == "large"))
        {
            System.out.println( "Invalid group size. Aborting match process...");
            return;
        }


        List<Group> filteredList = new LinkedList<>();
        Group closestGroup = group_list.get(0); //  closest group in list. COMPILER WHINES IF UNINITIALIZED.
        double cGroupIndex = -1; // similarity index for closest group.
        double tempIndex = 0;  // similarity index for comparison.
        double minMatch = 0.75; // so... this is our arbitrary starting minimum for matching groups. Change if necessary.

        final double MATCH_SUB = 0.01; // decrease incremement.
        final double LOW_BOUND = 0.50; // lowest possible match index.
        final long SLEEP_TIME = 30; // seconds spent in between searches, in seconds.

        for (int i = 0; i < group_list.size(); i++)
            filteredList.add(group_list.get(i)); // because we don't want to modify the original list.

        for (int j = 0; j < filteredList.size(); j++) // removes all groups that are not correctly sized, studying same topic or are full.
        {
            if ((filteredList.get(j).getGroupSize() != groupSize) || (filteredList.get(j).getStudyTopic() != studyTopic) || filteredList.get(j).isFull())
                filteredList.remove(j);
        }

        int k = 0; // loop counter.
        do
        {
            if (k > 0)
            {
                // TimeUnit.SECONDS.sleep(SLEEP_TIME); // sleep in-between searches. Compiler whining again. Fix later. Eventually crucial, because this needs to be delayed.
                minMatch -= k * MATCH_SUB; // lower minimum match index each
            }

            for (int i = 0; i < filteredList.size(); i++)
            {
                tempIndex = 0;
                for (int j = 0; j < filteredList.get(i).getGroupCount(); j++) // O(n^2). may be slow... V1, though... Right?
                    tempIndex += findUserById(filteredList.get(i).getGroupMember().get(j)).simIndex(newUser);

                tempIndex /= filteredList.get(i).getGroupCount();
                if (tempIndex > cGroupIndex)
                {
                    closestGroup = filteredList.get(i); // better group found: set as best.
                    cGroupIndex = tempIndex; // set index as highest - basis for comparison, next loop.
                }
            }
            k++;
        }
        while (cGroupIndex < minMatch && minMatch >= LOW_BOUND); // best match doesn't surpass or match minimum? try again.

        if (minMatch >= LOW_BOUND)
        {
            closestGroup.addMember(newUser.getUserId()); // !!!
            return;
        }

        Group newGroup = new Group("New Group", 0, groupSize, studyTopic); // no groups good enough. create a new one.
        this.addGroup(newGroup);
    }
}


