package ru.academits.java.glushkov.minesweeper.gui;

public class Stopwatch implements Runnable {
    private int second;
    private int minute;
    private int hour;
    private final CountersPanel countersPanel;

    public Stopwatch(CountersPanel countersPanel) {
        this.countersPanel = countersPanel;
    }

    @Override
    public void run() {
        try {
            Thread thread = Thread.currentThread();

            while (!thread.isInterrupted()) {
                Thread.sleep(1000);
                second++;

                if (second == 60) {
                    minute++;
                    second = 0;
                }

                if (minute == 60) {
                    hour++;
                    minute = 0;
                }

                countersPanel.setCurrentTime(getCurrentTime());
            }
        } catch (InterruptedException ignored) {
        }
    }

    private String getCurrentTime() {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}