import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ModuleCoverGUI extends JFrame {

    private Image originalImage;
    private ImagePanel imagePanel;

    public ModuleCoverGUI() {

        setTitle("Eco-Defense Learning Module");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ✅ LOAD IMAGE
        ImageIcon icon = new ImageIcon("module cover.png");
        originalImage = icon.getImage();

        // ✅ CUSTOM PANEL (for centered drawing)
        imagePanel = new ImagePanel();
        add(imagePanel, BorderLayout.CENTER);

        // ✅ BUTTON PANEL
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);

        JButton startBtn = new JButton("Start Learning ▶");
        startBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        startBtn.setBackground(new Color(0, 120, 180));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        startBtn.addActionListener(e -> {
            new LearningModuleGUI().setVisible(true);
            dispose();
        });

        bottomPanel.add(startBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ✅ INNER CLASS (handles perfect image rendering)
    class ImagePanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (originalImage == null) return;

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imgWidth = originalImage.getWidth(null);
            int imgHeight = originalImage.getHeight(null);

            // ✅ Calculate scale (maintain aspect ratio)
            double widthRatio = (double) panelWidth / imgWidth;
            double heightRatio = (double) panelHeight / imgHeight;
            double scale = Math.min(widthRatio, heightRatio);

            int newWidth = (int) (imgWidth * scale);
            int newHeight = (int) (imgHeight * scale);

            // ✅ Centering position
            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;

            // ✅ Draw background (padding area)
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, panelWidth, panelHeight);

            // ✅ Draw scaled image (centered)
            g.drawImage(originalImage, x, y, newWidth, newHeight, this);
        }
    }
}
