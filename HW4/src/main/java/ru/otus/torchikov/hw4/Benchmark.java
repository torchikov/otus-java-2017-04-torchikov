package ru.otus.torchikov.hw4;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergei on 22.04.17.e
 *
 */
public class Benchmark implements BenchmarkMBean {
    private int size;

    void start() throws InterruptedException {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException NOP) {
        }
        List<Object> list = new ArrayList<>();
        Object[] array = new Object[size];
        GCMonitor gcMonitor = new GCMonitor();
        gcMonitor.startGCMonitoring();

        int n = 0;
        int currentSize = size;
        while (n < Integer.MAX_VALUE) {
            int i = n % currentSize;
            array[i] = new String(new char[0]);
            if (n % 110 == 0) {//Для учечки, уменьшить число для увеличения времени работы приложения
                list.add(new String(new char[0]));
            }
            n++;

            if (n % currentSize == 0) {
                currentSize = size;
                array = new Object[currentSize];
            }
        }
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getSize() {
        return this.size;
    }


}
