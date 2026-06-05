import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class LearningModuleGUI extends JFrame {

    private DisasterModule currentModule;
    private String[] imageFiles;
    private String[] descriptions;
    private int currentIndex = 0;

    private JLabel imageLabel;
    private JTextArea textArea;
    private JProgressBar progressBar;
    private JLabel slideTracker;
    private Image originalImage;

    // ✅ DARK THEME COLORS
    private final Color BG_MAIN = new Color(30, 30, 30);
    private final Color BG_PANEL = new Color(50, 50, 60);
    private final Color TEXT_COLOR = new Color(220, 220, 220);
    private final Color BTN_COLOR = new Color(55, 55, 65);
    private final Color BTN_HOVER = new Color(80, 80, 95);

    public LearningModuleGUI() {

        setTitle("Eco-Defense Learning App");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_MAIN);

        // ===== TOP PANEL (TITLE + TABS) =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG_MAIN);

        JLabel title = new JLabel("Eco-Defense Learning Module", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ✅ Fix tab blue color
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        UIManager.put("TabbedPane.background", BG_MAIN);
        UIManager.put("TabbedPane.foreground", TEXT_COLOR);
        UIManager.put("TabbedPane.selected", BG_PANEL);
        UIManager.put("TabbedPane.contentAreaColor", BG_MAIN);
        UIManager.put("TabbedPane.focus", BG_MAIN);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(BG_PANEL);
        tabs.setForeground(TEXT_COLOR);

        tabs.addTab("Storm", null);
        tabs.addTab("Wildfire", null);
        tabs.addTab("Flood", null);

        tabs.addChangeListener(e -> {
            int index = tabs.getSelectedIndex();
            if (index == 0) currentModule = new StormModule();
            if (index == 1) currentModule = new WildfireModule();
            if (index == 2) currentModule = new FloodModule();
            loadModule();
        });

        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(tabs, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBackground(BG_MAIN);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setOpaque(true);
        imageLabel.setBackground(BG_PANEL);

        JScrollPane imgScroll = new JScrollPane(imageLabel);
        imgScroll.getViewport().setBackground(BG_PANEL);

        textArea = new JTextArea();
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        textArea.setForeground(TEXT_COLOR);
        textArea.setBackground(BG_PANEL);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setMargin(new Insets(20, 20, 20, 20));

        JScrollPane txtScroll = new JScrollPane(textArea);
        txtScroll.getViewport().setBackground(BG_PANEL);

        mainPanel.add(imgScroll);
        mainPanel.add(txtScroll);

        add(mainPanel, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JButton prev = styleButton(new JButton("◀ Previous"));
        JButton next = styleButton(new JButton("Next ▶"));
        JButton startQuiz = styleButton(new JButton("Start Quiz ✅"));
        JButton leaderboardBtn = styleButton(new JButton("🏆 Leaderboard"));
        JButton inventoryBtn = styleButton(new JButton("📦 Inventory"));

        prev.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateView();
            }
        });

        next.addActionListener(e -> {
            if (currentIndex < imageFiles.length - 1) {
                currentIndex++;
                updateView();
            }
        });

        startQuiz.addActionListener(e -> new ScenarioQuiz().launchQuiz());

        leaderboardBtn.addActionListener(e -> {
            Leaderboard lb = new Leaderboard("leaderboard.txt");
            lb.loadFromFile("leaderboard.txt");
            lb.displayTopPlayers();
        });

        inventoryBtn.addActionListener(e -> new InventoryGUI_Clean().setVisible(true));

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        nav.setBackground(BG_MAIN);
        nav.add(prev);
        nav.add(next);
        nav.add(startQuiz);
        nav.add(leaderboardBtn);
        nav.add(inventoryBtn);

        // ===== BOTTOM PANEL =====
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(BG_MAIN);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBackground(BG_PANEL);
        progressBar.setForeground(new Color(100, 180, 120));

        slideTracker = new JLabel("", SwingConstants.CENTER);
        slideTracker.setForeground(TEXT_COLOR);
        slideTracker.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        bottom.add(progressBar, BorderLayout.NORTH);
        bottom.add(nav, BorderLayout.CENTER);
        bottom.add(slideTracker, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);

        // ===== RESIZE IMAGE =====
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resizeImage();
            }
        });

        currentModule = new StormModule();
        loadModule();
    }

    private void loadModule() {
        imageFiles = currentModule.getImages();
        descriptions = currentModule.getDescriptions();
        currentIndex = 0;
        updateView();
    }

    private void updateView() {
        String file = imageFiles[currentIndex];
        File f = new File(file);

        if (f.exists()) {
            ImageIcon icon = new ImageIcon(file);
            originalImage = icon.getImage();
            resizeImage();
        }

        textArea.setText(descriptions[currentIndex]);

        progressBar.setMaximum(imageFiles.length);
        progressBar.setValue(currentIndex + 1);

        slideTracker.setText("Slide " + (currentIndex + 1) + "/" + imageFiles.length);
    }

    private void resizeImage() {
        if (originalImage == null) return;

        int w = imageLabel.getWidth();
        int h = imageLabel.getHeight();

        if (w <= 0 || h <= 0) return;

        Image scaled = originalImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));
    }

    private JButton styleButton(JButton btn) {
        btn.setBackground(BTN_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(BTN_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(BTN_COLOR);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ModuleCoverGUI().setVisible(true));
    }
}