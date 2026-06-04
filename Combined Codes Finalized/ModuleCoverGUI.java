import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ModuleCoverGUI extends JFrame {

    private Image originalImage;

    public ModuleCoverGUI() {

        setTitle("Eco-Defense Learning Module");
        setSize(950, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ✅ LOAD IMAGE
        ImageIcon icon = new ImageIcon("module cover.png");
        originalImage = icon.getImage();

        // ✅ IMAGE PANEL
        ImagePanel imagePanel = new ImagePanel();
        add(imagePanel, BorderLayout.CENTER);

        // ✅ BOTTOM PANEL (modern look)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(15, 15, 15));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton startBtn = new JButton("Start Learning");

        // ✅ MODERN BUTTON STYLE
        startBtn.setFont(new Font("Segoe UI", Font.BOLD, 17));
        startBtn.setForeground(Color.WHITE);
        startBtn.setBackground(new Color(0, 160, 220));
        startBtn.setFocusPainted(false);
        startBtn.setBorder(BorderFactory.createEmptyBorder(14, 35, 14, 35));
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ✅ SMOOTH HOVER EFFECT
        startBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                startBtn.setBackground(new Color(0, 190, 255));
            }
            public void mouseExited(MouseEvent e) {
                startBtn.setBackground(new Color(0, 160, 220));
            }
        });

        // ✅ BUTTON ACTION
        startBtn.addActionListener(e -> {
            new LearningModuleGUI().setVisible(true);
            dispose();
        });

        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(startBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ✅ CUSTOM IMAGE PANEL (premium look)
    class ImagePanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (originalImage == null) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imgWidth = originalImage.getWidth(null);
            int imgHeight = originalImage.getHeight(null);

            // ✅ Maintain aspect ratio
            double widthRatio = (double) panelWidth / imgWidth;
            double heightRatio = (double) panelHeight / imgHeight;
            double scale = Math.min(widthRatio, heightRatio);

            int newWidth = (int) (imgWidth * scale);
            int newHeight = (int) (imgHeight * scale);

            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;

            // ✅ Soft dark background (better than pure black)
            g2.setColor(new Color(20, 20, 20));
            g2.fillRect(0, 0, panelWidth, panelHeight);

            // ✅ DRAW IMAGE
            g2.drawImage(originalImage, x, y, newWidth, newHeight, this);

            // ✅ OVERLAY GRADIENT (premium look 🔥)
            GradientPaint gradient = new GradientPaint(
                    0, panelHeight - 180, new Color(0, 0, 0, 0),
                    0, panelHeight, new Color(0, 0, 0, 200)
            );
            g2.setPaint(gradient);
            g2.fillRect(0, panelHeight - 180, panelWidth, 180);
        }
    }

    // ✅ MAIN METHOD
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ModuleCoverGUI().setVisible(true));
    }
}