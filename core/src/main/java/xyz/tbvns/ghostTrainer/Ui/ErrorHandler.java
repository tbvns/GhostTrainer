package xyz.tbvns.ghostTrainer.Ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Arrays;

@Slf4j
public class ErrorHandler {
    @SneakyThrows
    public static void handle(Exception e, boolean isFatal) {
        if (isFatal) {
            Arrays.stream(JFrame.getFrames()).forEach(Frame::dispose);
        }

        JFrame frame = new JFrame("Error: " + e.getMessage());
        frame.setSize(600, 400);
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        JLabel errorLabel = new JLabel("Error: " + e.getMessage()) {{
            setIcon(UIManager.getIcon("OptionPane.errorIcon"));
            setBorder(new EmptyBorder(10, 5, 10, 10));
            setFont(getFont().deriveFont(20F));
            if (isFatal) {
                setText("Fatal error: " + e.getMessage());
            }
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }};
        labelPanel.add(errorLabel);
        labelPanel.add(Box.createHorizontalGlue());
        panel.add(labelPanel);

        JTextArea area = new JTextArea(){{
            setEditable(false);
            setRows(10);
        }};
//        Arrays.stream(e.getStackTrace()).map(el -> el.toString() + "\n").forEach(area::append);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(stream));
        String trace = new String(stream.toByteArray());
        area.append(trace);
        log.error(trace);

        panel.add(new JScrollPane(area){{
            setPreferredSize(new Dimension(300, 300));
            setBorder(new EmptyBorder(0, 0, 10, 0));
        }});

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton button = new JButton("Close") {{
            addActionListener(a -> {
                if (isFatal) {
                    Runtime.getRuntime().exit(1);
                }
                frame.dispose();
            });

            if (isFatal) {
                setBackground(new Color(199, 84, 80));
                setText("End program");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
            setAlignmentX(Component.RIGHT_ALIGNMENT);
        }};

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(button);

        panel.add(buttonPanel);
        frame.setContentPane(panel);
        UIUtils.center(frame);
        frame.setVisible(true);

        if (isFatal) {
            Thread.currentThread().join();
        }
    }

    @SneakyThrows
    public static void handleWithRetryAndUrl(Exception e, boolean isFatal, String url, String additionalMessage) {
        FlatDarculaLaf.setup();
        if (isFatal) {
            Arrays.stream(JFrame.getFrames()).forEach(Frame::dispose);
        }

        JFrame frame = new JFrame("Error: " + e.getMessage());
        frame.setSize(700, 500);
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        JLabel errorLabel = new JLabel("Error: " + e.getMessage()) {{
            setIcon(UIManager.getIcon("OptionPane.errorIcon"));
            setBorder(new EmptyBorder(10, 5, 10, 10));
            setFont(getFont().deriveFont(20F));
            if (isFatal) {
                setText("Fatal error: " + e.getMessage());
            }
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }};
        labelPanel.add(errorLabel);
        labelPanel.add(Box.createHorizontalGlue());
        panel.add(labelPanel);

        JTextArea area = new JTextArea() {{
            setEditable(false);
            setRows(10);
        }};
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(stream));
        String trace = new String(stream.toByteArray());
        area.append(trace);
        log.error(trace);

        panel.add(new JScrollPane(area) {{
            setPreferredSize(new Dimension(300, 300));
            setBorder(new EmptyBorder(0, 0, 10, 0));
        }});

        if (additionalMessage != null && !additionalMessage.isEmpty()) {
            JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            JLabel messageLabel = new JLabel(additionalMessage);
            messageLabel.setFont(messageLabel.getFont().deriveFont(Font.ITALIC, 12f));
            messageLabel.setForeground(new Color(255, 96, 96));
            messagePanel.add(messageLabel);
            messagePanel.setBorder(new EmptyBorder(0, 5, 10, 0));
            panel.add(messagePanel);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton retryButton = new JButton("Retry") {{
            addActionListener(a -> {
                frame.dispose();
                synchronized (frame) {
                    frame.notifyAll();
                }
            });
            setAlignmentX(Component.RIGHT_ALIGNMENT);
        }};

        // Open URL button
        JButton openUrlButton = new JButton("Open URL") {{
            addActionListener(a -> {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    log.error("Failed to open URL: " + url, ex);
                }
            });
            setAlignmentX(Component.RIGHT_ALIGNMENT);
        }};

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(openUrlButton);
        buttonPanel.add(retryButton);
        panel.add(buttonPanel);

        frame.setContentPane(panel);
        UIUtils.center(frame);
        frame.setVisible(true);
        frame.toFront();
        frame.setAlwaysOnTop(true);

        // Freeze the application until the retry button is pressed
        synchronized (frame) {
            frame.wait();
        }

        if (isFatal) {
            Runtime.getRuntime().exit(1);
        }
    }


    public static void warn(String message) {
        log.warn(message);
        JFrame frame = new JFrame("Waning");
        new Thread(() -> {
            JOptionPane.showMessageDialog(frame,
                    message,
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }).start();
    }
}
