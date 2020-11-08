import java.util.LinkedList;
import java.util.List;

/**
 * User pool class will manege the list of users and groups
 * @author Haoyuan Zhao
 * @version 1
 * @since 2020-11-6
 */

public class userPool {
    private List<user> user_list;
    private List<group> group_list;

    /**
     * Constructor:
     * build the list of user and group
     */
    public userPool(){
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
    public void addUser(user newUser){
        user_list.add(newUser);
        return;
    }

    /**
     * search the user by user id
     * @param userId user's id
     * @return the index of target user, return -1 if not find
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
     * add a group into the group list
     * @param newGroup the group be added
     */
    public void addGroup(group newGroup){
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
     * search the user by user id
     * @param userId user's id
     * @return the target user(the class </user>), return null if not find
     */
    public user findUserById(int userId){
        int userIndex = userSearch(userId);
        if ( userIndex != -1){
            return user_list.get(userIndex);
        }
        return null;
    }
}
