import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.BufferedImage;

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

    // ✅ ZOOM + DRAG
    private double zoomFactor = 1.0;
    private final double ZOOM_STEP = 0.1;
    private int dragStartX, dragStartY;
    private int imgOffsetX = 0, imgOffsetY = 0;

    // ✅ DARK THEME
    private final Color BG_MAIN = new Color(30, 30, 30);
    private final Color BG_PANEL = new Color(50, 50, 60);
    private final Color TEXT_COLOR = new Color(220, 220, 220);
    private final Color BTN_COLOR = new Color(55, 55, 65);
    private final Color BTN_HOVER = new Color(80, 80, 95);

    public LearningModuleGUI() {

        setTitle("Eco-Defense Learning App");
        setSize(380, 700);
        setResizable(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_MAIN);

        // ===== TOP =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG_MAIN);

        JLabel title = new JLabel("Eco-Defense Learning Module", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(TEXT_COLOR);

        JTabbedPane tabs = new JTabbedPane();
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

        // ===== MAIN =====
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBackground(BG_MAIN);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel imageContainer = new JPanel(new GridBagLayout());
        imageContainer.setBackground(BG_PANEL);
        imageContainer.add(imageLabel);

        // ✅ ZOOM
        imageLabel.addMouseWheelListener(e -> {
            if (e.getPreciseWheelRotation() < 0) {
                zoomFactor += ZOOM_STEP;
            } else {
                zoomFactor -= ZOOM_STEP;
            }

            zoomFactor = Math.max(0.5, Math.min(zoomFactor, 3.0));
            resizeImage();
        });

        // ✅ DRAG
        imageLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dragStartX = e.getX();
                dragStartY = e.getY();
            }
        });

        imageLabel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - dragStartX;
                int dy = e.getY() - dragStartY;

                imgOffsetX += dx;
                imgOffsetY += dy;

                dragStartX = e.getX();
                dragStartY = e.getY();

                resizeImage();
            }
        });

        // ===== TEXT =====
        textArea = new JTextArea();
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textArea.setForeground(TEXT_COLOR);
        textArea.setBackground(BG_PANEL);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane txtScroll = new JScrollPane(textArea);

        mainPanel.add(imageContainer);
        mainPanel.add(txtScroll);

        add(mainPanel, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JButton prev = styleButton(new JButton("◀ Prev"));
        JButton next = styleButton(new JButton("Next ▶"));
        JButton quiz = styleButton(new JButton("Quiz"));
        JButton leaderboard = styleButton(new JButton("🏆"));
        JButton inventory = styleButton(new JButton("📦"));

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

        quiz.addActionListener(e -> new ScenarioQuiz().launchQuiz());

        leaderboard.addActionListener(e -> {
            Leaderboard lb = new Leaderboard("leaderboard.txt");
            lb.loadFromFile("leaderboard.txt");
            lb.displayTopPlayers();
        });

        inventory.addActionListener(e -> new InventoryGUI_Clean().setVisible(true));

        JPanel nav = new JPanel(new GridLayout(2, 3, 5, 5));
        nav.setBackground(BG_MAIN);
        nav.add(prev);
        nav.add(next);
        nav.add(quiz);
        nav.add(leaderboard);
        nav.add(inventory);

        progressBar = new JProgressBar();
        slideTracker = new JLabel("", SwingConstants.CENTER);
        slideTracker.setForeground(TEXT_COLOR);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(BG_MAIN);
        bottom.add(progressBar, BorderLayout.NORTH);
        bottom.add(nav, BorderLayout.CENTER);
        bottom.add(slideTracker, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);

        // ✅ RESIZE LISTENER
        addComponentListener(new ComponentAdapter() {
            Timer timer;

            public void componentResized(ComponentEvent e) {
                if (timer != null) timer.stop();

                timer = new Timer(100, ev -> resizeImage());
                timer.setRepeats(false);
                timer.start();
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

        if (new File(file).exists()) {
            originalImage = new ImageIcon(file).getImage();

            // ✅ RESET VIEW
            zoomFactor = 1.0;
            imgOffsetX = 0;
            imgOffsetY = 0;

            resizeImage();
        }

        textArea.setText(descriptions[currentIndex]);

        progressBar.setMaximum(imageFiles.length);
        progressBar.setValue(currentIndex + 1);

        slideTracker.setText("Slide " + (currentIndex + 1) + "/" + imageFiles.length);
    }

    // ✅ RESIZE + ZOOM + DRAG FULL FIX
    private void resizeImage() {
        if (originalImage == null) return;

        int w = imageLabel.getParent().getWidth();
        int h = imageLabel.getParent().getHeight();

        if (w <= 0 || h <= 0) return;

        int imgW = originalImage.getWidth(null);
        int imgH = originalImage.getHeight(null);

        double baseScale = Math.min((double) w / imgW, (double) h / imgH);
        double scale = baseScale * zoomFactor;

        int newW = (int) (imgW * scale);
        int newH = (int) (imgH * scale);

        BufferedImage buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();

        int x = (w - newW) / 2 + imgOffsetX;
        int y = (h - newH) / 2 + imgOffsetY;

        g2.drawImage(originalImage, x, y, newW, newH, null);
        g2.dispose();

        imageLabel.setIcon(new ImageIcon(buffer));
    }

    private JButton styleButton(JButton btn) {
        btn.setBackground(BTN_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);

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