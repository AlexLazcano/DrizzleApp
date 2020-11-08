import android.service.autofill.AutofillService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class forTest {
    public static void main (String args[]){
        UserPool newPool = new UserPool();
        User user1 = new User(newPool);
        user1.setName("Haoyuan Zhao");
        user1.setUserId(1);

        User user2 = new User(newPool);
        user2.setName("Joe");
        user2.setUserId(2);

        User user3 = new User(newPool);
        user3.setName("Bill");
        user3.setUserId(3);

        User user4 = new User(newPool);
        user4.setName("Jack");
        user4.setUserId(4);

        Group group1 = new Group(newPool,"Amazing group",101);
        Group group2 = new Group(newPool,"Sunday group",102);

        group1.addMember(user1);
        group1.addMember(user4);
        group2.addMember(user1);
        group2.addMember(user3);
        group2.addMember(user2);

        newPool.printAllInfo();

        group1.quitGroup(user1);
        newPool.writtenOff(user3.getUserId());

        System.out.println("After the quit option:\n");

        newPool.printAllInfo();

    }

}
