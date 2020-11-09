public class forTest {
    public static void main (String args[]){
        userPool newPool = new userPool();
        user user1 = new user(newPool);
        user1.setName("Haoyuan Zhao");
        user1.setUserId(100);

        user user2 = new user(newPool);
        user2.setName("114514");
        user2.setUserId(123);

        user user3 = new user(newPool);
        user3.setName("RNM退钱");
        user3.setUserId(4396);

        user user4 = new user(newPool);
        user4.setName("马保国");
        user4.setUserId(250);

        group group1 = new group(newPool,"yygq组",101);
        group1.addMember(user1);
        group1.addMember(user2);
        group1.printGroupInfo();

        group group2 = new group(newPool,"整活组",102);
        group2.addMember(user2);
        group2.addMember(user3);
        group2.addMember(user4);
        group2.printGroupInfo();

        user1.printMyGroupInfo();
        user2.printMyGroupInfo();

        group2.quitGroup(123);
        group2.printGroupInfo();
        user2.printMyGroupInfo();

        System.out.println("We have " + newPool.numOfGroups() + " groups, and " +
                            newPool.numOfUsers() + " users");
    }
}
