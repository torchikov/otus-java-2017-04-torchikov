package ru.torchikov.msgsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
public final class MessageSystem {
    private static final int DEFAULT_STEP_TIME = 10;

    private final Map<Address, ConcurrentLinkedQueue<Message>> messagesMap = new HashMap<>();

    public void addAddressee(Addressee addressee) {
        messagesMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());
        registerAddressee(addressee);
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void registerAddressee(Addressee addressee) {
        new Thread(() -> {
            while (true) {
                ConcurrentLinkedQueue<Message> queue = messagesMap.get(addressee.getAddress());
                while (!queue.isEmpty()) {
                    Message message = queue.poll();
                    message.exec(addressee);
                }
                try {
                    Thread.sleep(MessageSystem.DEFAULT_STEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
