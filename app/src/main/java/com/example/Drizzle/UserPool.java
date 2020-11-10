package com.example.Drizzle;
import com.example.Drizzle.Group;
import com.example.Drizzle.User;

import java.util.LinkedList;
import java.util.List;
//import java.util.concurrent.TimeUnit; 

/**
 * User pool class will manege the list of users and groups
 * @author Haoyuan Zhao & Markus Mattila
 * @version 1
 * @since 2020-11-6
 */

public class UserPool {
    private List<User> user_list;
    private List<Group> group_list;

    /**
     * Constructor:
     * build the list of User and Group
     */
    public UserPool(){
        this.user_list = new LinkedList<>();
        this.group_list = new LinkedList<>();
    }

    /**
     * count the number of users
     * @return the number of User
     */
    public int numOfUsers(){
        return user_list.size();
    }

    /**
     * add a User into the User list
     * @param newUser the User be added
     */
    public void addUser(User newUser){
        user_list.add(newUser);
        return;
    }

    /**
     * search the User by User id
     * @param userId User's id
     * @return the index of target User, return -1 if not find
     */
    private int userSearch(int userId){
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
     * add a Group into the Group list
     * @param newGroup the Group be added
     */
    public void addGroup(Group newGroup){
        group_list.add(newGroup);
        return;
    }

    /**
     * search the Group by Group id
     * @param groupId Group's id
     * @return the index of target Group, return -1 if not find
     */
    public int groupSearch(int groupId){
        for ( int i = 0;i < this.numOfGroups(); i++) {
            if (this.group_list.get(i).getGroupId() == groupId) {
                return i;
            }
        }
        return -1;
    }

    //wait to build
//    /**
//     *
//     * @param tarId
//     * @return
//     */
//    public int writtenOff(int tarId){
//        return 0;
//    }

    /**
     * search the User by User id
     * @param userId User's id
     * @return the target User(the class </User>), return null if not find
     */
    public User findUserById(int userId){
        int userIndex = userSearch(userId);
        if ( userIndex != -1){
            return user_list.get(userIndex);
        }
        return null;
    }


    /** 
    * Places a user into a corresponding (or new) group, as per similarities with existing groups. Requires refinement.
    * @param userId: The ID of the user to be strategically placed into a group.
    * @param groupSize: Preferred size of group for to-be-placed user (small, medium or large)
    * @param studyTopic: Preferrred study topic of group for to-be-placed user.
    */
    void groupMatchUser(int userId, String groupSize, String studyTopic) // searching user, user pool, user's preferred group size, topic of study
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
                    tempIndex += filteredList.get(i).getGroupMember(j).simIndex(newUser);

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
            closestGroup.addMember(newUser); // !!!
            return;
        }

        Group newGroup = new Group(this, "New Group", 0, groupSize, studyTopic); // no groups good enough. create a new one.
    }
}