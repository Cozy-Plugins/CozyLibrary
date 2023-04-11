package com.github.cozyplugins.cozylibrary;

import com.github.cozyplugins.cozylibrary.messages.MessageManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains tests for the message manager.
 */
public class MessageManagerTest {

    @Test
    public void parseListTest() {
        List<String> messageList = new ArrayList<>();
        messageList.add("&8test1");
        messageList.add("&6test2");

        System.out.println(MessageManager.parse(messageList));
    }
}
