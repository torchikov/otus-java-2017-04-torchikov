package ru.otus.torchikov.hw4;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergei on 22.04.17.
 *
 */
public class GCMonitor {
    private AtomicInteger youngGCCount = new AtomicInteger();
    private AtomicLong youngTime = new AtomicLong();
    private AtomicInteger oldGCCount = new AtomicInteger();
    private AtomicLong oldTime = new AtomicLong();

    public void startGCMonitoring() throws InterruptedException {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        System.out.println("Working garbage collectors: ");
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            System.out.println(gcbean.getName());

            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                    long duration = info.getGcInfo().getDuration();

                    if (info.getGcName().equals("PS Scavenge")
                            || info.getGcName().equals("Copy")
                            || info.getGcName().equals("ParNew")
                            || info.getGcName().equals("G1 Young Generation")) {
                        youngGCCount.incrementAndGet();
                        youngTime.addAndGet(duration);
                    } else if (info.getGcName().equals("PS MarkSweep")
                            || info.getGcName().equals("MarkSweepCompact")
                            || info.getGcName().equals("ConcurrentMarkSweep")
                            || info.getGcName().equals("G1 Old Generation")) {
                        oldGCCount.incrementAndGet();
                        oldTime.addAndGet(duration);
                    }
                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
        Thread t = new Thread(() -> {
            while (true) {
                CountDownLatch trigger = new CountDownLatch(1);
                try {
                    trigger.await(1, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println();
                System.out.println("For 1 minute GC for young generation was started " + youngGCCount.get() + " times, spent " + youngTime.get() + "ms to collect garbage");
                System.out.println("For 1 minute GC for old generation was started: " + oldGCCount.get() + " times, spent " + oldTime.get() + "ms to collect garbage");
                youngGCCount.set(0);
                youngTime.set(0L);
                oldGCCount.set(0);
                oldTime.set(0L);

            }
        });
        t.setDaemon(true);
        t.start();
    }
}
