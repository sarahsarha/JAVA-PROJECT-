
/**
 * Class      : ScenarioQuiz 
 * Creator    : Annie
 * Tester     : Najla
 * Description: Implements GameLogic. Manages 20 disaster-preparedness quiz questions,
 *              a survival countdown timer, score tracking and motivational feedback.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ScenarioQuiz implements GameLogic {

    // ── Colors ───────────────────────────────────────────────────
    private static final Color BG_DARK    = new Color(10, 18, 35);
    private static final Color BG_CARD    = new Color(18, 30, 55);
    private static final Color BG_TOPBAR  = new Color(14, 24, 45);
    private static final Color TEAL       = new Color(0, 188, 180);
    private static final Color TEAL_DARK  = new Color(0, 140, 135);
    private static final Color NAVY_BTN   = new Color(22, 40, 75);
    private static final Color NAVY_HOVER = new Color(30, 58, 105);
    private static final Color TEXT_WHITE = new Color(230, 240, 255);
    private static final Color TEXT_MUTED = new Color(130, 155, 195);
    private static final Color GREEN_TICK = new Color(50, 210, 140);
    private static final Color RED_CROSS  = new Color(240, 85, 85);
    private static final Color GOLD       = new Color(255, 195, 50);

    // ── Attributes ───────────────────────────────────────────────
    private int currentScore;
    private int timeRemaining;
    private String currentScenario;
    private ArrayList<Question> questions;
    private int currentQuestionIndex;
    private String playerName = "";

    // GUI
    private JFrame  frame;
    private JLabel  timerLabel;
    private JLabel  scoreLabel;
    private JLabel  progressLabel;
    private JLabel  questionLabel;
    private JLabel  feedbackLabel;
    private JPanel  optionsPanel;
    private JPanel  progressBarPanel;
    private Timer   swingTimer;

    // ── Constructor ──────────────────────────────────────────────
    public ScenarioQuiz() {
        this.currentScore         = 0;
        this.timeRemaining        = 0;
        this.currentScenario      = "Climate & Disaster Preparedness";
        this.questions            = new ArrayList<>();
        this.currentQuestionIndex = 0;
        loadQuestions();
    }

    // ── GameLogic Overrides ──────────────────────────────────────
    @Override
    public void startSurvivalTimer(int seconds) {
        this.timeRemaining = seconds;
        updateTimerLabel();
        swingTimer = new Timer(1000, e -> {
            timeRemaining--;
            updateTimerLabel();
            if (timeRemaining <= 0) {
                swingTimer.stop();
                endQuiz("Time's up!");
            }
        });
        swingTimer.start();
    }

    @Override
    public void calculatePoints(boolean isCorrect) {
        if (isCorrect) currentScore += 10;
        updateScoreLabel();
    }

    // ── Load Questions ───────────────────────────────────────────
    public void loadQuestions() {
        questions.clear();

        questions.add(new Question(
            "Which SDG is most directly related to climate action?",
            new String[]{"A.  SDG 6", "B.  SDG 11", "C.  SDG 13", "D.  SDG 15"}, "C", "MCQ"));
        questions.add(new Question(
            "What is the FIRST thing to do during a flood warning?",
            new String[]{"A.  Wait indoors", "B.  Move to higher ground", "C.  Call friends", "D.  Pack electronics"}, "B", "MCQ"));
        questions.add(new Question(
            "Which gas is the PRIMARY cause of the greenhouse effect?",
            new String[]{"A.  Oxygen", "B.  Nitrogen", "C.  Carbon Dioxide", "D.  Hydrogen"}, "C", "MCQ"));
        questions.add(new Question(
            "A wildfire is approaching. Which way should you evacuate?",
            new String[]{"A.  Towards the fire", "B.  Perpendicular to wind", "C.  Downhill only", "D.  Stay and defend"}, "B", "MCQ"));
        questions.add(new Question(
            "What MUST an emergency kit include at minimum?",
            new String[]{"A.  Laptop and charger", "B.  Water, food and first aid", "C.  Jewelry and documents", "D.  Extra clothing only"}, "B", "MCQ"));
        questions.add(new Question(
            "How many days of water per person should an emergency kit have?",
            new String[]{"A.  1 day", "B.  2 days", "C.  3 days", "D.  7 days"}, "C", "MCQ"));
        questions.add(new Question(
            "Which renewable energy source helps reduce disaster risk?",
            new String[]{"A.  Coal", "B.  Natural Gas", "C.  Solar Power", "D.  Nuclear"}, "C", "MCQ"));
        questions.add(new Question(
            "What does the Richter scale measure?",
            new String[]{"A.  Hurricane speed", "B.  Earthquake magnitude", "C.  Flood level", "D.  Wildfire spread"}, "B", "MCQ"));
        questions.add(new Question(
            "During a thunderstorm, which location is SAFEST?",
            new String[]{"A.  Under a tall tree", "B.  In an open field", "C.  Inside a sturdy building", "D.  Near a metal fence"}, "C", "MCQ"));
        questions.add(new Question(
            "What percent of Earth's fresh water is drinkable without treatment?",
            new String[]{"A.  70%", "B.  30%", "C.  10%", "D.  Less than 1%"}, "D", "MCQ"));
        questions.add(new Question(
            "Rising sea levels are mainly caused by?",
            new String[]{"A.  Increased rainfall", "B.  Melting ice caps and thermal expansion", "C.  Ocean currents", "D.  Moon gravity"}, "B", "MCQ"));
        questions.add(new Question(
            "Which system alerts communities to tsunamis?",
            new String[]{"A.  Weather satellites", "B.  Ocean buoy sensor networks", "C.  Air sirens only", "D.  Radio only"}, "B", "MCQ"));

        questions.add(new Question("Deforestation increases the risk of landslides.", null, "TRUE", "TF"));
        questions.add(new Question("You should use elevators during a building fire.", null, "FALSE", "TF"));
        questions.add(new Question("SDG 13 specifically targets climate action.", null, "TRUE", "TF"));
        questions.add(new Question("Burning fossil fuels reduces greenhouse gas emissions.", null, "FALSE", "TF"));
        questions.add(new Question("It is safe to walk through ankle-deep moving flood water.", null, "FALSE", "TF"));
        questions.add(new Question("Mangrove forests help protect coastlines from storm surges.", null, "TRUE", "TF"));
        questions.add(new Question("A Category 5 hurricane has lower wind speed than Category 3.", null, "FALSE", "TF"));
        questions.add(new Question("Drought can increase the risk of wildfires.", null, "TRUE", "TF"));
    }

    // ── Overloads ────────────────────────────────────────────────
    public void evaluateAnswer(String ans) {
        if (currentQuestionIndex >= questions.size()) return;
        boolean correct = questions.get(currentQuestionIndex).checkAnswer(ans);
        calculatePoints(correct);
        showFeedback(correct);
        moveToNext();
    }

    public void evaluateAnswer(int selIdx, int corrIdx) {
        boolean correct = (selIdx == corrIdx);
        calculatePoints(correct);
        showFeedback(correct);
        moveToNext();
    }

    public String generateMotivationalMessage(double percentage) {
        if (percentage >= 80) return "Outstanding! You are a disaster preparedness expert!";
        if (percentage >= 60) return "That's good! Keep learning to save more lives!";
        if (percentage >= 40) return "Good try! Review the SDG 13 learning modules.";
        if (percentage >= 20) return "You can do better! Don't give up on climate action!";
        return "Don't give up! Every expert started as a beginner!";
    }

    // ════════════════════════════════════════════════════════════
    //  LAUNCH
    // ════════════════════════════════════════════════════════════
    public void launchQuiz() {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}

        frame = new JFrame("DisasterReady — Survival Quiz");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();  // auto size based on components
        frame.setMinimumSize(new Dimension(900, 650)); // prevent too small
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(BG_DARK);
        frame.setVisible(true);
        showStartScreen();
    }

    // ════════════════════════════════════════════════════════════
    //  START SCREEN
    // ════════════════════════════════════════════════════════════
    private void showStartScreen() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG_DARK);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JPanel accent = new JPanel();
        accent.setBackground(TEAL);
        accent.setMaximumSize(new Dimension(60, 4));
        accent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emojiLbl = centeredLabel("🌍", 58, Font.PLAIN, TEXT_WHITE);
        JLabel titleLbl = centeredLabel("DisasterReady", 24, Font.BOLD, TEXT_WHITE);
        JLabel sdgLbl   = centeredLabel("SDG 13 · Climate Action", 12, Font.PLAIN, TEAL);
        JLabel descLbl  = new JLabel(
            "<html><body style='width:260px;text-align:center'>" +
            "Test your knowledge on climate change and disaster preparedness. " +
            "Answer 20 questions before the timer runs out!" +
            "</body></html>");
        descLbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        descLbl.setForeground(TEXT_MUTED);
        descLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel divider = new JPanel();
        divider.setBackground(new Color(30, 55, 95));
        divider.setMaximumSize(new Dimension(280, 1));
        divider.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel infoBox = new JPanel(new GridLayout(1, 3, 8, 0));
        infoBox.setBackground(BG_DARK);
        infoBox.setMaximumSize(new Dimension(320, 70));
        infoBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoBox.add(infoCard("20",    "Questions"));
        infoBox.add(infoCard("120s",  "Timer"));
        infoBox.add(infoCard("10pts", "Per Answer"));

        JLabel nameLbl = new JLabel("Enter your name:");
        nameLbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        nameLbl.setForeground(TEXT_MUTED);
        nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(280, 40));
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField.setBackground(BG_CARD);
        nameField.setForeground(TEXT_WHITE);
        nameField.setCaretColor(TEAL);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(45, 75, 130), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startBtn = new JButton("Start Quiz");
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.setBackground(TEAL_DARK);
        startBtn.setForeground(Color.WHITE);
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        startBtn.setFocusPainted(false);
        startBtn.setBorderPainted(false);
        startBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startBtn.setMaximumSize(new Dimension(320, 50));
        startBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { startBtn.setBackground(TEAL); }
            public void mouseExited(MouseEvent e)  { startBtn.setBackground(TEAL_DARK); }
        });
        startBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                nameLbl.setText("Please enter your name!");
                nameLbl.setForeground(RED_CROSS);
                return;
            }
            playerName = name;
            showQuizScreen();
        });

        panel.add(emojiLbl);
        panel.add(Box.createVerticalStrut(6));
        panel.add(accent);
        panel.add(Box.createVerticalStrut(10));
        panel.add(titleLbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(sdgLbl);
        panel.add(Box.createVerticalStrut(14));
        panel.add(descLbl);
        panel.add(Box.createVerticalStrut(16));
        panel.add(divider);
        panel.add(Box.createVerticalStrut(16));
        panel.add(infoBox);
        panel.add(Box.createVerticalStrut(20));
        panel.add(nameLbl);
        panel.add(Box.createVerticalStrut(6));
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(startBtn);

        wrapper.add(panel);
        frame.getContentPane().removeAll();
        frame.getContentPane().setBackground(BG_DARK);
        frame.add(wrapper, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private JPanel infoCard(String value, String label) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(30, 55, 95), 1),
            BorderFactory.createEmptyBorder(10, 6, 10, 6)
        ));
        JLabel valLbl = centeredLabel(value, 16, Font.BOLD, TEAL);
        JLabel lblLbl = centeredLabel(label, 10, Font.PLAIN, TEXT_MUTED);
        card.add(valLbl);
        card.add(Box.createVerticalStrut(2));
        card.add(lblLbl);
        return card;
    }

    // ════════════════════════════════════════════════════════════
    //  QUIZ SCREEN
    // ════════════════════════════════════════════════════════════
    private void showQuizScreen() {
        frame.getContentPane().removeAll();
        frame.getContentPane().setBackground(BG_DARK);
        frame.setLayout(new BorderLayout());
        frame.add(buildTopBar(),  BorderLayout.NORTH);
        frame.add(buildCenter(),  BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        displayQuestion();
        startSurvivalTimer(120);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_TOPBAR);

        JPanel titleStrip = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 10));
        titleStrip.setBackground(TEAL_DARK);
        JLabel appTitle = new JLabel("🌍  DISASTERREADY");
        appTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        appTitle.setForeground(Color.WHITE);
        JLabel tagline = new JLabel("SDG 13 · Climate Action");
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 11));
        tagline.setForeground(new Color(200, 240, 235));
        titleStrip.add(appTitle);
        titleStrip.add(tagline);

        JPanel statsRow = new JPanel(new GridLayout(1, 3, 0, 0));
        statsRow.setBackground(BG_TOPBAR);
        statsRow.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        scoreLabel    = statLabel("Score: 0", TEAL);
        timerLabel    = statLabel("⏱  120s", GOLD);
        progressLabel = statLabel("Q 1 / 20", TEXT_MUTED);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        progressLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        statsRow.add(scoreLabel);
        statsRow.add(timerLabel);
        statsRow.add(progressLabel);

        progressBarPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(); int h = getHeight();
                g2.setColor(new Color(30, 48, 80));
                g2.fillRoundRect(0, 0, w, h, h, h);
                double pct = questions.isEmpty() ? 0 : (double) currentQuestionIndex / questions.size();
                int fill = (int)(w * pct);
                if (fill > 0) { g2.setColor(TEAL); g2.fillRoundRect(0, 0, fill, h, h, h); }
            }
        };
        progressBarPanel.setPreferredSize(new Dimension(0, 5));
        progressBarPanel.setBackground(BG_TOPBAR);

        JPanel topWrap = new JPanel(new BorderLayout());
        topWrap.setBackground(BG_TOPBAR);
        topWrap.add(statsRow,         BorderLayout.CENTER);
        topWrap.add(progressBarPanel, BorderLayout.SOUTH);

        bar.add(titleStrip, BorderLayout.NORTH);
        bar.add(topWrap,    BorderLayout.CENTER);
        return bar;
    }

    private JPanel buildCenter() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(BG_DARK);
        wrap.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(30, 55, 95), 1),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));

        JLabel typeTag = new JLabel("QUESTION");
        typeTag.setFont(new Font("SansSerif", Font.BOLD, 10));
        typeTag.setForeground(TEAL);
        typeTag.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        questionLabel.setForeground(TEXT_WHITE);
        questionLabel.setVerticalAlignment(SwingConstants.TOP);

        JPanel qTop = new JPanel();
        qTop.setLayout(new BoxLayout(qTop, BoxLayout.Y_AXIS));
        qTop.setBackground(BG_CARD);
        qTop.add(typeTag);
        qTop.add(Box.createVerticalStrut(6));
        qTop.add(questionLabel);
        card.add(qTop, BorderLayout.NORTH);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(BG_CARD);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
        card.add(optionsPanel, BorderLayout.CENTER);

        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        feedbackLabel.setForeground(GREEN_TICK);
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        card.add(feedbackLabel, BorderLayout.SOUTH);

        wrap.add(card, BorderLayout.CENTER);
        return wrap;
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            endQuiz("All questions answered!");
            return;
        }
        Question q = questions.get(currentQuestionIndex);
        feedbackLabel.setText(" ");
        progressLabel.setText("Q " + (currentQuestionIndex + 1) + " / " + questions.size());
        progressBarPanel.repaint();

        // Key fix: use card width minus padding for wrapping
        int labelWidth = frame.getWidth() - 70;
        questionLabel.setText("<html><div style='width:240px'>" + q.getQuestionText() + "</div></html>");

        optionsPanel.removeAll();

        if (q.getQuestionType().equals("MCQ")) {
            String[] opts = q.getOptions();
            final int[] corrIdx = {0};
            for (int i = 0; i < opts.length; i++) {
                if (q.checkAnswer(String.valueOf(opts[i].charAt(0)))) {
                    corrIdx[0] = i; break;
                }
            }
            for (int i = 0; i < opts.length; i++) {
                final int idx = i;
                JButton btn = buildOptionButton(opts[i]);
                btn.addActionListener(e -> { disableOptions(); evaluateAnswer(idx, corrIdx[0]); });
                optionsPanel.add(btn);
                optionsPanel.add(Box.createVerticalStrut(8));
            }
        } else {
            JButton t = buildTFButton("✔  TRUE",  new Color(20, 80, 55));
            JButton f = buildTFButton("✘  FALSE", new Color(80, 20, 30));
            t.addActionListener(e -> { disableOptions(); evaluateAnswer("TRUE"); });
            f.addActionListener(e -> { disableOptions(); evaluateAnswer("FALSE"); });
            optionsPanel.add(t);
            optionsPanel.add(Box.createVerticalStrut(10));
            optionsPanel.add(f);
        }

        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private JButton buildOptionButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(NAVY_BTN);
        btn.setForeground(TEXT_WHITE);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(45, 75, 130), 1),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(NAVY_HOVER);
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(TEAL, 1),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)
                ));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(NAVY_BTN);
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(45, 75, 130), 1),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)
                ));
            }
        });
        return btn;
    }

    private JButton buildTFButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        return btn;
    }

    private void showFeedback(boolean correct) {
        if (correct) {
            feedbackLabel.setForeground(GREEN_TICK);
            feedbackLabel.setText("✔  Correct!  +10 points");
        } else {
            feedbackLabel.setForeground(RED_CROSS);
            feedbackLabel.setText("✘  Incorrect.  Keep going!");
        }
    }

    private void moveToNext() {
        currentQuestionIndex++;
        Timer delay = new Timer(900, e -> displayQuestion());
        delay.setRepeats(false);
        delay.start();
    }

    private void disableOptions() {
        for (Component c : optionsPanel.getComponents())
            if (c instanceof JButton) c.setEnabled(false);
    }

    // ════════════════════════════════════════════════════════════
    //  END SCREEN
    // ════════════════════════════════════════════════════════════
    private void endQuiz(String reason) {
        if (swingTimer != null) swingTimer.stop();
        int total        = questions.size() * 10;
        int correctCount = currentScore / 10;
        double pct       = ((double) currentScore / total) * 100;
        String msg       = generateMotivationalMessage(pct);
        String emoji     = pct >= 80 ? "🏆" : pct >= 60 ? "👍" : pct >= 40 ? "💪" : pct >= 20 ? "📚" : "🌱";

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG_DARK);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JPanel accent = new JPanel();
        accent.setBackground(TEAL);
        accent.setMaximumSize(new Dimension(60, 4));
        accent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emojiLbl  = centeredLabel(emoji, 52, Font.PLAIN, TEXT_WHITE);
        JLabel titleLbl  = centeredLabel("Quiz Complete!", 22, Font.BOLD, TEXT_WHITE);
        JLabel reasonLbl = centeredLabel(reason, 12, Font.PLAIN, TEXT_MUTED);
        JLabel pctLbl    = centeredLabel(String.format("%.0f%%", pct), 52, Font.BOLD, TEAL);
        JLabel scoreLbl  = centeredLabel(currentScore + " / " + total + " points", 14, Font.PLAIN, TEXT_WHITE);

        JPanel breakdownBox = new JPanel(new GridLayout(1, 3, 0, 0));
        breakdownBox.setBackground(BG_CARD);
        breakdownBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(30, 55, 95), 1),
            BorderFactory.createEmptyBorder(12, 8, 12, 8)
        ));
        breakdownBox.setMaximumSize(new Dimension(320, 70));
        breakdownBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        breakdownBox.add(statBox(String.valueOf(correctCount), "Correct", GREEN_TICK));
        breakdownBox.add(statBox(String.valueOf(questions.size() - correctCount), "Wrong", RED_CROSS));
        breakdownBox.add(statBox(String.valueOf(questions.size()), "Total", TEXT_MUTED));

        JLabel msgLbl = new JLabel(
            "<html><body style='width:280px;text-align:center'>" + msg + "</body></html>");
        msgLbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        msgLbl.setForeground(new Color(160, 210, 230));
        msgLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton retry = new JButton("Try Again");
        retry.setAlignmentX(Component.CENTER_ALIGNMENT);
        retry.setBackground(TEAL_DARK);
        retry.setForeground(Color.WHITE);
        retry.setFont(new Font("SansSerif", Font.BOLD, 15));
        retry.setFocusPainted(false);
        retry.setBorderPainted(false);
        retry.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        retry.setMaximumSize(new Dimension(320, 50));
        retry.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { retry.setBackground(TEAL); }
            public void mouseExited(MouseEvent e)  { retry.setBackground(TEAL_DARK); }
            
        });
        retry.addActionListener(e -> {
            currentScore = 0;
            currentQuestionIndex = 0;
            loadQuestions();
            showStartScreen();
        });

        panel.add(emojiLbl);
        panel.add(Box.createVerticalStrut(6));
        panel.add(accent);
        panel.add(Box.createVerticalStrut(10));
        panel.add(titleLbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(reasonLbl);
        panel.add(Box.createVerticalStrut(16));
        panel.add(pctLbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(scoreLbl);
        panel.add(Box.createVerticalStrut(14));
        panel.add(breakdownBox);
        panel.add(Box.createVerticalStrut(14));
        panel.add(msgLbl);
        panel.add(Box.createVerticalStrut(20));
        panel.add(retry);

        wrapper.add(panel);
        frame.getContentPane().removeAll();
        frame.getContentPane().setBackground(BG_DARK);
        frame.add(wrapper, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();

        Leaderboard leaderboard = new Leaderboard("leaderboard.txt");
        leaderboard.loadFromFile("leaderboard.txt");
        leaderboard.addScore(playerName, currentScore);
        leaderboard.saveToFile("leaderboard.txt");
        leaderboard.displayTopPlayers();
    }

    // ── Helpers ──────────────────────────────────────────────────
    private void updateTimerLabel() {
        if (timerLabel == null) return;
        timerLabel.setText("⏱  " + timeRemaining + "s");
        timerLabel.setForeground(timeRemaining <= 15 ? RED_CROSS : GOLD);
    }

    private void updateScoreLabel() {
        if (scoreLabel == null) return;
        scoreLabel.setText("Score: " + currentScore);
    }

    private JLabel statLabel(String text, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setForeground(color);
        return l;
    }

    private JLabel centeredLabel(String text, int size, int style, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", style, size));
        l.setForeground(color);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private JPanel statBox(String value, String label, Color valueColor) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(BG_CARD);
        JLabel valLbl = centeredLabel(value, 22, Font.BOLD, valueColor);
        JLabel lblLbl = centeredLabel(label, 11, Font.PLAIN, TEXT_MUTED);
        box.add(valLbl);
        box.add(Box.createVerticalStrut(2));
        box.add(lblLbl);
        return box;
    }

    public void startQuiz() {
    javax.swing.JOptionPane.showMessageDialog(null, "Quiz Started!\n(Your logic runs here)");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScenarioQuiz().launchQuiz());
    }
}