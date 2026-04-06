//package SimpleClock;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class SimpleClock extends JFrame {

    SimpleDateFormat timeFormat;
    SimpleDateFormat dayFormat;
    SimpleDateFormat dateFormat;

    JLabel timeLabel;
    JLabel dayLabel;
    JLabel dateLabel;
    JButton toggleFormatButton;
    JButton toggleZoneButton; // New button for GMT toggle

    String time;
    String day;
    String date;

    boolean is24Hour = false;
    boolean isGMT = false; // Flag for TimeZone state

    SimpleClock() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Digital Clock");
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.setSize(1150, 300); // Increased height for more buttons
        this.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        dayFormat = new SimpleDateFormat("EEEE");
        dateFormat = new SimpleDateFormat("dd MMMMM, yyyy");

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 59));
        timeLabel.setBackground(Color.BLACK);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setOpaque(true);
        timeLabel.setBorder(new EmptyBorder(10, 15, 10, 15));

        dayLabel = new JLabel();
        dayLabel.setFont(new Font("Ink Free", Font.BOLD, 34));

        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Ink Free", Font.BOLD, 30));

        // Format Toggle Button (12/24h)
        toggleFormatButton = new JButton("Switch to 24h");
        toggleFormatButton.setFocusable(false);
        toggleFormatButton.addActionListener(e -> toggleTimeFormat());

        // TimeZone Toggle Button (Local/GMT)
        toggleZoneButton = new JButton("Switch to GMT");
        toggleZoneButton.setFocusable(false);
        toggleZoneButton.addActionListener(e -> toggleTimeZone());

        mainPanel.add(timeLabel);
        mainPanel.add(dayLabel);
        mainPanel.add(dateLabel);
        mainPanel.add(toggleFormatButton);
        mainPanel.add(toggleZoneButton);

        this.add(mainPanel);
        this.setVisible(true);

        setTimer();
    }

    private void toggleTimeFormat() {
        is24Hour = !is24Hour;
        updateFormats(); // Re-apply the pattern and current timezone
    }

    private void toggleTimeZone() {
        isGMT = !isGMT;
        if (isGMT) {
            toggleZoneButton.setText("Switch to Local");
        } else {
            toggleZoneButton.setText("Switch to GMT");
        }
        updateFormats();
    }

    // Centralized method to handle pattern and timezone changes
    private void updateFormats() {
        String pattern = is24Hour ? "HH:mm:ss" : "hh:mm:ss a";
        timeFormat = new SimpleDateFormat(pattern);
        
        toggleFormatButton.setText(is24Hour ? "Switch to 12h" : "Switch to 24h");

        // Apply TimeZone to all formatters
        TimeZone tz = isGMT ? TimeZone.getTimeZone("GMT") : TimeZone.getDefault();
        timeFormat.setTimeZone(tz);
        dayFormat.setTimeZone(tz);
        dateFormat.setTimeZone(tz);
    }

    public void setTimer() {
        Thread clockThread = new Thread(() -> {
            while (true) {
                SwingUtilities.invokeLater(() -> {
                    Calendar now = Calendar.getInstance();
                    time = timeFormat.format(now.getTime());
                    timeLabel.setText(time);

                    day = dayFormat.format(now.getTime());
                    dayLabel.setText(day);

                    date = dateFormat.format(now.getTime());
                    dateLabel.setText(date);
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        clockThread.setDaemon(true);
        clockThread.start();
    }

    public static void main(String[] args) {
        new SimpleClock();
    }
}