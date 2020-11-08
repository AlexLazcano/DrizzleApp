import java.util.LinkedList;
import java.util.List;

/**
 * group class will store the information of the group
 * @author Haoyuan Zhao
 * @version 1
 * @since 2020-11-6
 */

public class Group {
    private int groupId;
    private String groupName;
    private List<User> groupMember;

    /**
     * constructor: build the list of user and add this group
     * into group list
     * @param newPool the pool be added to
     */
    public Group(UserPool newPool, String groupName, int groupId) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupMember = new LinkedList<>();
        newPool.addGroup(this);
    }

    /**
     * group member getter
     * @return a list of user
     */
    public List<User> getGroupMember() {
        return groupMember;
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
     * @param newUser the new member
     */
    public void addMember(User newUser){
        this.groupMember.add(newUser);
        if ( !newUser.getMyGroup().contains(this)) {
            newUser.joinGroup(this);
        }
        return;
    }

    /**
     * remove the user from group member, and also call leaveGroup for user if
     * the user.mygroup currently still contain this group
     * @param quitUser the user want to quit
     */
    public void quitGroup(User quitUser){
        this.groupMember.remove(quitUser);
        if ( quitUser.getMyGroup().contains(this) ){
            quitUser.leaveGroup(this);
        }
        return;
    }

    /**
     * count the member in this group
     * @return  the number of the member
     */
    public int groupSize(){
        return groupMember.size();
    }

    /**
     * search the user in this group by user id
     * @param userId target user id
     * @return the index of the user(index for the user list in this group), return -1 if not find
     */
    private int memberSearch(int userId){
        for( int i = 0 ; i < this.groupSize(); ++i){
            if ( this.groupMember.get(i).getUserId() == userId) {
                return i;
            }
        }
        return -1;
    }

    /**
     * print group information, only for test
     */
    public void printGroupInfo(){
        System.out.println("Group name: " + groupName + "\nGroup size :" + this.groupSize() + "\nThis group has " +
                "following members:");
        for( int i = 0; i < this.groupSize(); ++i){
            System.out.println( (i+1) + "th:\nUser name: " + this.groupMember.get(i).getName() + "\n" );
        }
    }
}
