package com.example.Drizzle;

import java.util.LinkedList;
import java.util.List;

public class forTest {
    public static void main (String args[]){
        List<User> userList = new LinkedList<>();
        UserPool newPool = new UserPool();
        for ( int i = 0; i < 4; ++i) {
            User user = new User(newPool);
            user.setUserId(i+100);
            userList.add(user);
        }
        userList.get(0).setName("Haoyuan Zhao");
        userList.get(1).setName("Joe");
        userList.get(2).setName("Bill");
        userList.get(3).setName("Jack");

        Group group1 = new Group(newPool,"Amazing group",101,"small","mathematics");
        Group group2 = new Group(newPool,"Sunday group",102,"small", "physics");

        group1.addMember(userList.get(0));
        group1.addMember(userList.get(3));
        group2.addMember(userList.get(0));
        group2.addMember(userList.get(1));
        group2.addMember(userList.get(2));

        newPool.printAllInfo();

        group1.quitGroup(userList.get(0));
        newPool.writtenOff(userList.get(2).getUserId());

        System.out.println("After the quit option:\n");

        newPool.printAllInfo();

    }

}
