
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class InventoryGUI_Clean extends JFrame {
    private EmergencyKit kit = new EmergencyKit();
    private JTextArea logArea;
    private JProgressBar progressBar;
    private JLabel statusLabel, checklistLabel;
    private JComboBox<String> disasterDropdown;
    private ParticlePanel centerPanel;

    private final Color COLOR_BG_DARK = new Color(30, 30, 36), COLOR_PANEL_CORE = new Color(42, 42, 53);
    private final Color COLOR_TEXT_MAIN = new Color(244, 244, 245), COLOR_TEXT_MUTED = new Color(161, 161, 170);
    private final Color COLOR_EMERALD = new Color(34, 197, 94), COLOR_CRIMSON = new Color(239, 68, 68), COLOR_ACCENT_BLU = new Color(3, 105, 161);

    public InventoryGUI_Clean() {
        setTitle("Eco-Defense Inventory Dashboard");
        setSize(950, 620);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel rootPanel = new JPanel(new BorderLayout(15, 15));
        rootPanel.setBackground(COLOR_BG_DARK);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(rootPanel);

        // Header Panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setOpaque(false);
        JLabel title = new JLabel("EMERGENCY KIT PREPAREDNESS", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26)); title.setForeground(COLOR_TEXT_MAIN);
        JLabel subtitle = new JLabel("Pack your digital survival kit to fulfill safety checks across disaster simulations.", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13)); subtitle.setForeground(COLOR_TEXT_MUTED);
        headerPanel.add(title); headerPanel.add(subtitle);
        rootPanel.add(headerPanel, BorderLayout.NORTH);

        // Left Supply Button Deck
        JPanel leftPanel = new JPanel(new GridLayout(5, 1, 0, 12));
        leftPanel.setOpaque(false); leftPanel.setPreferredSize(new Dimension(180, 0));
        JButton btnFlashlight = createModernButton("Flashlight", COLOR_ACCENT_BLU), btnMask = createModernButton("N95 Mask", COLOR_ACCENT_BLU);
        JButton btnRadio = createModernButton("Radio", COLOR_ACCENT_BLU), btnFirstAid = createModernButton("First Aid Kit", COLOR_ACCENT_BLU);
        JButton btnWater = createModernButton("+1 Day Water", new Color(29, 78, 216));
        leftPanel.add(btnFlashlight); leftPanel.add(btnMask); leftPanel.add(btnRadio); leftPanel.add(btnFirstAid); leftPanel.add(btnWater);
        rootPanel.add(leftPanel, BorderLayout.WEST);

        // Center Card Dashboard Layout
        centerPanel = new ParticlePanel(); centerPanel.setLayout(new BorderLayout(10, 15));
        centerPanel.setBackground(COLOR_PANEL_CORE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(63, 63, 70), 1), BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        statusLabel = new JLabel("INCOMPLETE", SwingConstants.CENTER); statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 22)); statusLabel.setForeground(COLOR_CRIMSON);
        checklistLabel = new JLabel("", SwingConstants.CENTER); checklistLabel.setFont(new Font("Segoe UI", Font.BOLD, 15)); checklistLabel.setForeground(COLOR_TEXT_MAIN);
        centerPanel.add(statusLabel, BorderLayout.NORTH); centerPanel.add(checklistLabel, BorderLayout.CENTER);
        rootPanel.add(centerPanel, BorderLayout.CENTER);

        // Right Activity Terminal Log Console 
        JPanel rightPanel = new JPanel(new BorderLayout(0, 8)); rightPanel.setOpaque(false); rightPanel.setPreferredSize(new Dimension(260, 0));
        JLabel logHeader = new JLabel("SYSTEM PROFILE ACTIVITY LOG", JLabel.LEFT); logHeader.setFont(new Font("Segoe UI", Font.BOLD, 11)); logHeader.setForeground(COLOR_TEXT_MUTED);
        logArea = new JTextArea(); logArea.setEditable(false); logArea.setFont(new Font("Consolas", Font.PLAIN, 13)); logArea.setForeground(new Color(167, 139, 250)); logArea.setBackground(new Color(24, 24, 27)); logArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(logArea); scrollPane.setBorder(BorderFactory.createLineBorder(new Color(63, 63, 70), 1));
        rightPanel.add(logHeader, BorderLayout.NORTH); rightPanel.add(scrollPane, BorderLayout.CENTER);
        rootPanel.add(rightPanel, BorderLayout.EAST);

        // Bottom Progress & Control Navigation Board
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 15)); bottomPanel.setOpaque(false); bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        progressBar = new JProgressBar(0, 100); progressBar.setStringPainted(true); progressBar.setFont(new Font("Segoe UI", Font.BOLD, 12)); progressBar.setForeground(COLOR_CRIMSON); progressBar.setBackground(new Color(24, 24, 27)); progressBar.setBorder(BorderFactory.createLineBorder(new Color(63, 63, 70), 1)); progressBar.setPreferredSize(new Dimension(0, 22));
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0)); controlPanel.setOpaque(false);
        JLabel disasterLabel = new JLabel("Select Target Simulation:"); disasterLabel.setFont(new Font("Segoe UI", Font.BOLD, 13)); disasterLabel.setForeground(COLOR_TEXT_MAIN);
        disasterDropdown = new JComboBox<>(new String[]{"Storm", "Flood", "Wildfire"}); disasterDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 13)); disasterDropdown.setBackground(COLOR_PANEL_CORE); disasterDropdown.setForeground(COLOR_TEXT_MAIN); disasterDropdown.setBorder(BorderFactory.createLineBorder(new Color(92, 92, 112), 1));
        JButton enterBtn = createModernButton("Launch Scenario Simulation", COLOR_EMERALD), clearBtn = createModernButton("Reset Kit", new Color(113, 113, 122));
        enterBtn.setFont(new Font("Segoe UI", Font.BOLD, 13)); enterBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        clearBtn.setFont(new Font("Segoe UI", Font.BOLD, 13)); clearBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        controlPanel.add(disasterLabel); controlPanel.add(disasterDropdown); controlPanel.add(enterBtn); controlPanel.add(clearBtn);
        bottomPanel.add(progressBar, BorderLayout.NORTH); bottomPanel.add(controlPanel, BorderLayout.CENTER);
        rootPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Unified Short-Line Event Actions
        btnFlashlight.addActionListener(e -> { addItem("flashlight"); centerPanel.spawnEffect("lightning"); });
        btnMask.addActionListener(e -> { addItem("n95 mask"); centerPanel.spawnEffect("gas"); });
        btnRadio.addActionListener(e -> { addItem("radio"); centerPanel.spawnEffect("radio"); });
        btnFirstAid.addActionListener(e -> { addItem("first aid"); centerPanel.spawnEffect("medical"); });
        btnWater.addActionListener(e -> { kit.addItem("water", 1); log("💧 Water reserves expanded +1 day."); refreshUI(); centerPanel.spawnEffect("water"); });
        clearBtn.addActionListener(e -> resetKit());
        enterBtn.addActionListener(e -> {
            String disaster = (String) disasterDropdown.getSelectedItem();
            try {
                kit.ventureIntoScenario(disaster);
                JOptionPane.showMessageDialog(this, "ACCESS GRANTED: Kit verified ready for the " + disaster + " profile.", "Simulation Launch Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IncompleteKitException ex) {
                JOptionPane.showMessageDialog(this, "CRITICAL OVERRIDE:\n" + ex.getMessage(), "Security Check Lockout", JOptionPane.ERROR_MESSAGE);
                log(" Simulation access rejected: Incomplete gear layout.");
            }
        });

        refreshUI(); centerPanel.startAnimationLoop();
    }

    private JButton createModernButton(String text, Color baseColor) {
        JButton button = new JButton(text); button.setFont(new Font("Segoe UI", Font.BOLD, 13)); button.setForeground(Color.WHITE); button.setBackground(baseColor);
        button.setFocusPainted(false); button.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12)); button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setBackground(baseColor.brighter()); }
            @Override public void mouseExited(MouseEvent e) { button.setBackground(baseColor); }
        });
        return button;
    }

    private void addItem(String item) { kit.addItem(item); log("📦 Packed item tool: " + item.toUpperCase()); refreshUI(); }

    private void refreshUI() {
        int water = kit.getWaterSupplyDays();
        boolean hasFirstAid = kit.isHasFirstAid(), hasUtil = kit.getItems().contains("flashlight") || kit.getItems().contains("n95 mask") || kit.getItems().contains("radio");
        String checkWater = water >= 3 ? "<b style='color:#22c55e;'>✔ READY</b> ("+water+"/3 Days)" : "<b style='color:#ef4444;'>✖ DEFICIENT</b> ("+water+"/3 Days)";
        String checkMed = hasFirstAid ? "<b style='color:#22c55e;'>✔ SECURED</b>" : "<b style='color:#ef4444;'>✖ MISSING</b>";
        String checkUtil = hasUtil ? "<b style='color:#22c55e;'>✔ EQUIPPED</b>" : "<b style='color:#ef4444;'>✖ MISSING</b>";

        checklistLabel.setText("<html><body style='font-family:Segoe UI; text-align:center; color:#f4f4f5;'><div style='font-size:14px; line-height:2.2em;'>Hydration Supplies (3 Days): &nbsp; " + checkWater + "<br>🩺 Emergency Medical Pack: &nbsp; " + checkMed + "<br>🛠️ Essential Survival Utility: &nbsp; " + checkUtil + "</div></body></html>");

        int score = Math.min((water >= 3 ? 40 : water * 13) + (hasFirstAid ? 30 : 0) + (hasUtil ? 30 : 0), 100);
        progressBar.setValue(score);
        progressBar.setForeground(score == 100 ? COLOR_EMERALD : COLOR_CRIMSON);
        statusLabel.setText(score == 100 ? "READY FOR FIELD OPERATION" : "STATUS DEFICIENT: SECURE PACK REQUIRED");
        statusLabel.setForeground(score == 100 ? COLOR_EMERALD : COLOR_CRIMSON);
    }

    private void log(String msg) { logArea.append(" " + msg + "\n"); }
    private void resetKit() { kit = new EmergencyKit(); logArea.setText(""); refreshUI(); log("🔄 Inventory manifest wiped clean. Session reset."); }

    // Particle Frame Graphics Panel 
    class ParticlePanel extends JPanel {
        private final ArrayList<CustomVisualParticle> particles = new ArrayList<>();
        private final Random rand = new Random();
        public ParticlePanel() { setOpaque(true); setDoubleBuffered(true); }

        public synchronized void spawnEffect(String type) {
            int w = getWidth(), h = getHeight();
            if (type.equals("water")) for (int i = 0; i < 30; i++) particles.add(new CustomVisualParticle(rand.nextInt(Math.max(w, 100)), h - 20, "water", rand));
            else if (type.equals("lightning")) for (int i = 0; i < 3; i++) particles.add(new CustomVisualParticle(w / 2 + (rand.nextInt(60) - 30), 20, "lightning", rand));
            else if (type.equals("gas")) for (int i = 0; i < 25; i++) particles.add(new CustomVisualParticle(10, rand.nextInt(Math.max(h, 100)), "gas", rand));
            else if (type.equals("medical")) for (int i = 0; i < 20; i++) particles.add(new CustomVisualParticle(w / 2, h / 2, "medical", rand));
            else if (type.equals("radio")) for (int i = 0; i < 4; i++) { CustomVisualParticle p = new CustomVisualParticle(w / 2, h / 2, "radio", rand); p.size = i * 40; particles.add(p); }
        }

        public void startAnimationLoop() { new Timer(20, e -> { updateParticles(); repaint(); }).start(); }
        private synchronized void updateParticles() { Iterator<CustomVisualParticle> it = particles.iterator(); while (it.hasNext()) { CustomVisualParticle p = it.next(); p.move(); if (p.isDead()) it.remove(); } }
        @Override protected void paintComponent(Graphics g) { super.paintComponent(g); Graphics2D g2 = (Graphics2D) g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); synchronized (this) { for (CustomVisualParticle p : particles) p.draw(g2); } Toolkit.getDefaultToolkit().sync(); }
    }

    // Graphical Vector Properties Interface Block 
    class CustomVisualParticle {
        String type; double x, y, velX, velY; int size, alpha, fadeSpeed; Color color; int[] boltX, boltY;

        public CustomVisualParticle(int startX, int startY, String type, Random rand) {
            this.type = type; this.x = startX; this.y = startY; this.alpha = 240;
            if (type.equals("water")) { velX = (rand.nextDouble() * 4) - 2; velY = -(rand.nextDouble() * 4 + 3); size = rand.nextInt(8) + 6; fadeSpeed = rand.nextInt(3) + 4; color = new Color(56, 189, 248); }
            else if (type.equals("lightning")) { velX = 0; velY = 0; fadeSpeed = 25; color = new Color(254, 240, 138); int segs = 8; boltX = new int[segs]; boltY = new int[segs]; boltX[0] = startX; boltY[0] = startY; for(int j = 1; j < segs; j++) { boltX[j] = boltX[j-1] + (rand.nextInt(41) - 20); boltY[j] = boltY[j-1] + 45; } }
            else if (type.equals("gas")) { velX = rand.nextDouble() * 3 + 2; velY = (rand.nextDouble() * 2) - 1; size = rand.nextInt(30) + 25; fadeSpeed = rand.nextInt(3) + 3; color = new Color(156, 163, 175); }
            else if (type.equals("medical")) { double ang = rand.nextDouble() * 2 * Math.PI, spd = rand.nextDouble() * 4 + 2; velX = Math.cos(ang) * spd; velY = Math.sin(ang) * spd; size = rand.nextInt(6) + 10; fadeSpeed = rand.nextInt(2) + 5; color = new Color(239, 68, 68); }
            else if (type.equals("radio")) { velX = 0; velY = 0; size = 10; fadeSpeed = 4; color = new Color(148, 163, 184); }
        }

        public void move() { if (type.equals("radio")) size += 5; else { x += velX; y += velY; } alpha = Math.max(alpha - fadeSpeed, 0); }
        public boolean isDead() { return alpha <= 0; }

        public void draw(Graphics2D g2) {
            g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
            if (type.equals("water")) g2.fillOval((int) x, (int) y, size, size + 2);
            else if (type.equals("lightning")) { g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); for (int i = 0; i < boltX.length - 1; i++) g2.drawLine(boltX[i], boltY[i], boltX[i+1], boltY[i+1]); }
            else if (type.equals("gas")) g2.fillOval((int) x, (int) y, size, size);
            else if (type.equals("medical")) { int px = (int) x, py = (int) y, th = size / 3; g2.fillRect(px - size / 2, py - th / 2, size, th); g2.fillRect(px - th / 2, py - size / 2, th, size); }
            else if (type.equals("radio")) { g2.setStroke(new BasicStroke(2.5f)); g2.drawOval((int) x - size / 2, (int) y - size / 2, size, size); }
        }
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new InventoryGUI_Clean().setVisible(true)); }
}