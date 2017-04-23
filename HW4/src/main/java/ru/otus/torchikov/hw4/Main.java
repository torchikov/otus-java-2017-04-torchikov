package ru.otus.torchikov.hw4;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created by sergei on 22.04.17.
 *
 */

/*
JVM Options для запуска:
 -Xms512m
 -Xmx512m
 -verbose:gc
 -Xloggc:./logs/gc_pid_%p.log
 -XX:+PrintGCDateStamps
 -XX:+PrintGCDetails
 -XX:+UseGCLogFileRotation
 -XX:NumberOfGCLogFiles=10
 -XX:GCLogFileSize=1M


 Варианты GC:
 1) -XX:+UseSerialGC

 2) -XX:+UseParallelGC
    -XX:+UseParallelOldGC

 3) -XX:+UseParNewGC
    -XX:+UseConcMarkSweepGC

 4) -XX:+UseG1GC
*/
public class Main {
    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        System.out.println();

        int size = 5 * 1000 * 1000;
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus.torchikov:type=Benchmark");
        Benchmark bean = new Benchmark();
        mBeanServer.registerMBean(bean, name);

        bean.setSize(size);
        bean.start();
    }
}
