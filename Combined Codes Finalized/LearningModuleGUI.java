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

    public LearningModuleGUI() {
        setTitle("Eco-Defense Learning App");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 30, 30));

        // ✅ TITLE
        JLabel title = new JLabel("Eco-Defense Learning Module", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        // ✅ TABS
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

        add(tabs, BorderLayout.NORTH);

        // ✅ MAIN PANEL
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(new Color(30, 30, 30));

        imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setOpaque(true);
        imageLabel.setBackground(new Color(45, 45, 45));
        JScrollPane imgScroll = new JScrollPane(imageLabel);

        textArea = new JTextArea();
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textArea.setForeground(new Color(220, 220, 220));
        textArea.setBackground(new Color(45, 45, 45));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setMargin(new Insets(20, 20, 20, 20));
        JScrollPane txtScroll = new JScrollPane(textArea);

        mainPanel.add(imgScroll);
        mainPanel.add(txtScroll);
        add(mainPanel, BorderLayout.CENTER);

        // ✅ BOTTOM PANEL
        JPanel bottom = new JPanel(new BorderLayout());

        JButton prev = new JButton("◀ Previous");
        JButton next = new JButton("Next ▶");
        JButton startQuiz = new JButton("Start Quiz ✅");

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

        // ✅ QUIZ BUTTON ACTION
        startQuiz.addActionListener(e -> {
            new ScenarioQuiz().launchQuiz();   // ✅ call your quiz logic
        });


        JPanel nav = new JPanel();
        nav.add(prev);
        nav.add(next);
        nav.add(startQuiz); // ✅ added

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        slideTracker = new JLabel("", SwingConstants.CENTER);

        bottom.add(progressBar, BorderLayout.NORTH);
        bottom.add(nav, BorderLayout.CENTER);
        bottom.add(slideTracker, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);

        // ✅ RESIZE IMAGE
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LearningModuleGUI().setVisible(true));
    }
}