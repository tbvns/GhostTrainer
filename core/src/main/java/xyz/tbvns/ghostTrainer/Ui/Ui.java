package xyz.tbvns.ghostTrainer.Ui;

import xyz.tbvns.ghostTrainer.Config.Config;
import xyz.tbvns.ghostTrainer.Config.ConfigManager;
import xyz.tbvns.ghostTrainer.Game.AimeTrainerRenderer;
import xyz.tbvns.ghostTrainer.Main;
import xyz.tbvns.ghostTrainer.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Ui {
    private static boolean showed = false;
    public static JFrame frame;

    public static void showSettings() {
        if (showed) {
            Arrays.stream(JFrame.getFrames()).forEach(Frame::dispose);
            return;
        }

        frame = new JFrame("GT Settings");
        frame.setSize(200, 300);
        frame.toFront();
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        UIUtils.center(frame);

        JPanel panel = new JPanel(){{
            setBorder(new EmptyBorder(15, 15, 15, 15));
            add(new JButton("Targets color"){{
                setPreferredSize(new Dimension(frame.getWidth() - 30, 30));
                addActionListener(a -> {
                    JFrame colorFrame = new JFrame("Color picker");
                    JPanel p = new JPanel();
                    JColorChooser colorChooser = new JColorChooser(new Color(Config.r / 255, Config.g / 255, Config.b / 255, Config.a / 255));
                    JButton b = new JButton("Save"){{
                        addActionListener(a -> {
                            colorFrame.dispose();
                            Config.r = colorChooser.getColor().getRed();
                            Config.g = colorChooser.getColor().getGreen();
                            Config.b = colorChooser.getColor().getBlue();
                            Config.a = colorChooser.getColor().getAlpha();
                            ConfigManager.save();
                        });
                    }};
                    p.add(colorChooser);
                    p.add(b);
                    colorFrame.setContentPane(p);
                    colorFrame.setSize(650, 390);
                    colorFrame.setUndecorated(true);
                    colorFrame.setAlwaysOnTop(true);
                    colorFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    UIUtils.center(colorFrame);
                    colorFrame.setVisible(true);
                });
            }});

            AtomicInteger VFov = new AtomicInteger(Config.fov);
            AtomicInteger VCount = new AtomicInteger(Config.ballCount);
            final float[] VSize = {Config.size};
            final int[] VAIDelay = {Config.AIDelay};

            JPanel fov = new JPanel(new BorderLayout()) {{
                add(new JLabel("Field of view:") {{
                    setAlignmentX(LEFT_ALIGNMENT);
                }}, BorderLayout.WEST);
                JComboBox<Integer> box = new JComboBox<>() {{
                    for (int i = 50; i <= 120; i++) {
                        addItem(i);
                    }
                    setSelectedIndex(Config.fov - 50);
                    setPreferredSize(new Dimension(60, 25));
                    addItemListener(i -> {
                        VFov.set(getSelectedIndex() + 50);
                    });
                }};
                add(box, BorderLayout.EAST);
                setPreferredSize(new Dimension(frame.getWidth() - 30, 30));
            }};
            add(fov);

            JPanel ballCount = new JPanel(new BorderLayout()) {{
                add(new JLabel("Ball counts:") {{
                    setAlignmentX(LEFT_ALIGNMENT);
                }}, BorderLayout.WEST);
                JComboBox<Integer> box = new JComboBox<>() {{
                    for (int i = 1; i <= 20; i++) {
                        addItem(i);
                    }
                    setSelectedIndex(Config.ballCount - 1);
                    setPreferredSize(new Dimension(60, 25));
                    addItemListener(i -> {
                        VCount.set(getSelectedIndex() + 1);
                    });
                }};
                add(box, BorderLayout.EAST);
                setPreferredSize(new Dimension(frame.getWidth() - 30, 30));
            }};
            add(ballCount);
            add(new JPanel(new BorderLayout()) {{
                setPreferredSize(new Dimension(frame.getWidth() - 30, 60));
                add(new JLabel("Targets size:"), BorderLayout.WEST);
                JLabel nb = new JLabel(String.valueOf(Config.size));
                add(nb, BorderLayout.EAST);
                JSlider slider = new JSlider(1, 20) {{
                    setValue(Math.round(Config.size * 10));
                    setPreferredSize(new Dimension(frame.getWidth() - 30, 30));
                }};
                slider.addChangeListener(c_ -> {
                    nb.setText(String.valueOf((float) slider.getValue() / 10));
                    VSize[0] = (float) slider.getValue() / 10;
                });
                JPanel sliderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                sliderPanel.add(slider);
                add(sliderPanel, BorderLayout.SOUTH);
            }});

            add(new JPanel(new BorderLayout()) {{
                setPreferredSize(new Dimension(frame.getWidth() - 30, 60));
                add(new JLabel("AI Delay:"), BorderLayout.WEST);
                JLabel nb = new JLabel((float) Config.AIDelay / 1000 + "s");
                add(nb, BorderLayout.EAST);
                JSlider slider = new JSlider(1, 40) {{
                    setValue(Math.round((float) Config.AIDelay / 50));
                    setPreferredSize(new Dimension(frame.getWidth() - 30, 30));
                }};
                slider.addChangeListener(c_ -> {
                    nb.setText((float) (slider.getValue() * 50) / 1000 + "s");
                    VAIDelay[0] = slider.getValue() * 50;
                });
                JPanel sliderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                sliderPanel.add(slider);
                add(sliderPanel, BorderLayout.SOUTH);
            }});
            add(new JButton("Save"){{
                setPreferredSize(new Dimension(frame.getWidth() - 30, 30));
                addActionListener(a -> {
                    frame.dispose();
                    Config.fov = VFov.get();
                    Config.ballCount = VCount.get();
                    Config.size = VSize[0];
                    Config.AIDelay = VAIDelay[0];
                    ConfigManager.save();
                    AimeTrainerRenderer.reload();
                });
            }});
        }};

        panel.setLayout(new FlowLayout());
        frame.setContentPane(panel);

        showed = true;
        Main.show = false;

        frame.setVisible(true);
        frame.requestFocus();
        new Thread(() -> {
           while (frame.isShowing()) {
               Utils.sleep(100);
           }
           showed = false;
        }){{
            setName("FrameChecker");
            setDaemon(true);
            start();
        }};

    }
}
