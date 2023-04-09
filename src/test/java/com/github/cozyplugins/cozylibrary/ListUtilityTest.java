package com.github.cozyplugins.cozylibrary;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ListUtilityTest {

    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");
        list.add("test3");

        System.out.println(list);

        System.out.println(ListUtility.removeTheFirst(list, 2));
    }
}
