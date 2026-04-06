//package SimpleClock;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;


public class SimpleClock extends JFrame {
    
        Calendar calendar;
        SimpleDateFormat timeFormat;
        SimpleDateFormat dayFormat;
        SimpleDateFormat dateFormat;
    
        JLabel timeLabel;
        JLabel dayLabel;
        JLabel dateLabel;
        String time;
        String day;
        String date;

        SimpleClock() {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("Digital Clock");
            this.setLayout(new FlowLayout());
            this.setSize(350, 220);
            this.setResizable(false);
    
            timeFormat = new SimpleDateFormat("hh:mm:ss a");
            dayFormat=new SimpleDateFormat("EEEE");
            dateFormat=new SimpleDateFormat("dd MMMMM, yyyy");
            timeLabel = new JLabel();
            timeLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 59));
            timeLabel.setBackground(Color.BLACK);
            timeLabel.setForeground(Color.WHITE);
            timeLabel.setOpaque(true);
            dayLabel=new JLabel();
            dayLabel.setFont(new Font("Ink Free",Font.BOLD,34));
    
            dateLabel=new JLabel();
            dateLabel.setFont(new Font("Ink Free",Font.BOLD,30));
    
    
            this.add(timeLabel);
            this.add(dayLabel);
            this.add(dateLabel);
            this.setVisible(true);
    
            setTimer();
        }
    
        public void setTimer() {
            // Create and start a new thread for the clock updates
            Thread clockThread = new Thread(() -> {
                while (true) {
                    // Safely update UI from the worker thread using SwingUtilities.invokeLater
                    SwingUtilities.invokeLater(() -> {
                        time = timeFormat.format(Calendar.getInstance().getTime());
                        timeLabel.setText(time);
        
                        day = dayFormat.format(Calendar.getInstance().getTime());
                        dayLabel.setText(day);
        
                        date = dateFormat.format(Calendar.getInstance().getTime());
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
            
            // Set thread as daemon so it stops when the application exits
            clockThread.setDaemon(true);
            clockThread.start();
        }
        public static void main(String[] args) {
            new SimpleClock();
        }
    }
