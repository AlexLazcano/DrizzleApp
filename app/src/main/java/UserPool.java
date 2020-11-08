import java.util.LinkedList;
import java.util.List;

/**
 * User pool class will manege the list of users and groups
 * @author Haoyuan Zhao
 * @version 1
 * @since 2020-11-6
 */

public class UserPool {
    private List<User> user_list;
    private List<Group> group_list;

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

    /**
     * Clean up this user, remove him from all groups he belonged to and
     * remove this user from user pool
     * @param userId the user's id
     */
    public void writtenOff(int userId){
        for ( int i = 0; i < this.findUserById(userId).getMyGroup().size(); ++i){
            this.findUserById(userId).getMyGroup().get(i).quitGroup(this.findUserById(userId));
        }
        this.user_list.remove(this.findUserById(userId));
        return;
    }

    /**
     * search the user by user id
     * @param userId user's id
     * @return the target user(the class </user>), return null if not find
     */
    public User findUserById(int userId){
        int userIndex = userSearch(userId);
        if ( userIndex != -1){
            return user_list.get(userIndex);
        }
        return null;
    }

    public void printAllInfo(){
        System.out.println("Now user pool have " + this.numOfUsers() +" users, and " + this.numOfGroups() + " groups" +
                "\nThe user information are shown as following:");
        for( int i =0; i < user_list.size(); ++i){
            this.user_list.get(i).printUserInfo();
        }
        System.out.println("The group information is shown as following:");
        for( int i = 0 ; i < group_list.size(); ++i){
            this.group_list.get(i).printGroupInfo();
        }
    }
}
