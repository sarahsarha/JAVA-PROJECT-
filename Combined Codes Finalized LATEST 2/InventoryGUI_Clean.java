import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class InventoryGUI_Clean extends JFrame {

    private EmergencyKit kit = new EmergencyKit();

    private JTextArea logArea;
    private JProgressBar progressBar;
    private JLabel statusLabel, checklistLabel, itemsLabel;
    private JComboBox<String> disasterDropdown;

    private final Color BG = new Color(30,30,36);
    private final Color CARD = new Color(55,55,70);
    private final Color TEXT = new Color(240,240,240);
    private final Color GREEN = new Color(34,197,94);
    private final Color RED = new Color(239,68,68);

    public InventoryGUI_Clean() {

        setTitle("Inventory");
        setSize(380,700);   // ✅ mobile size
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(BG);
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // ===== TITLE =====
        JLabel title = new JLabel("Emergency Kit", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD,20));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel("Tap items to pack", SwingConstants.CENTER);
        subtitle.setForeground(Color.GRAY);

        root.add(title);
        root.add(subtitle);
        root.add(Box.createVerticalStrut(12));

        // ===== BUTTONS =====
        JPanel btnPanel = new JPanel(new GridLayout(3,2,8,8));
        btnPanel.setMaximumSize(new Dimension(350,180));
        btnPanel.setOpaque(false);

        JButton flash = createBtn("Flashlight");
        JButton mask  = createBtn("Mask");
        JButton radio = createBtn("Radio");
        JButton aid   = createBtn("First Aid");
        JButton water = createBtn("Water");

        btnPanel.add(flash);
        btnPanel.add(mask);
        btnPanel.add(radio);
        btnPanel.add(aid);
        btnPanel.add(water);

        root.add(btnPanel);
        root.add(Box.createVerticalStrut(10));

        // ✅ ===== ITEMS DISPLAY (MAIN FIX) =====

        // ===== STATUS PANEL =====
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(CARD);
        statusPanel.setMaximumSize(new Dimension(350,140));

        statusLabel = new JLabel("NOT READY", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD,18));
        statusLabel.setForeground(Color.RED);  // stronger red

        
        checklistLabel = new JLabel("", SwingConstants.CENTER);
        checklistLabel.setFont(new Font("Segoe UI", Font.BOLD,15));   // bigger text
        checklistLabel.setForeground(Color.WHITE);                    // better contrast
        checklistLabel.setOpaque(true);
        checklistLabel.setBackground(new Color(70, 70, 90));          // visible background


        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(checklistLabel, BorderLayout.CENTER);

        root.add(statusPanel);
        root.add(Box.createVerticalStrut(10));

        // ===== LOG =====
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(20,20,25));
        logArea.setForeground(new Color(167,139,250));
        logArea.setFont(new Font("Consolas", Font.PLAIN,14));

        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setMaximumSize(new Dimension(350,120));

        root.add(logScroll);
        root.add(Box.createVerticalStrut(10));

        // ===== PROGRESS =====
        progressBar = new JProgressBar(0,100);
        progressBar.setStringPainted(true);
        progressBar.setMaximumSize(new Dimension(350,25));

        root.add(progressBar);
        root.add(Box.createVerticalStrut(8));

        // ===== CONTROLS =====
        JPanel control = new JPanel();
        control.setOpaque(false);

        disasterDropdown = new JComboBox<>(new String[]{"Storm","Flood","Wildfire"});

        JButton start = createBtn("Start");
        JButton reset = createBtn("Reset");

        control.add(disasterDropdown);
        control.add(start);
        control.add(reset);

        root.add(control);

        // ===== SCROLL =====
        JScrollPane scroll = new JScrollPane(root);
        scroll.setBorder(null);
        setContentPane(scroll);

        // ===== ACTIONS =====
        flash.addActionListener(e -> addItem("flashlight"));
        mask.addActionListener(e -> addItem("mask"));
        radio.addActionListener(e -> addItem("radio"));
        aid.addActionListener(e -> addItem("first aid"));
        water.addActionListener(e -> {
            kit.addItem("water",1);
            log("Water added");
            refreshUI();
        });

        reset.addActionListener(e -> resetKit());

        start.addActionListener(e -> {
            try {
                kit.ventureIntoScenario((String)disasterDropdown.getSelectedItem());
                JOptionPane.showMessageDialog(this,"Ready for scenario!");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this,"Kit incomplete!");
            }
        });

        refreshUI();
    }

    // ===== BUTTON STYLE =====
    private JButton createBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD,15));
        b.setPreferredSize(new Dimension(100,55));
        b.setBackground(new Color(60,60,80));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);

        b.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){ b.setBackground(new Color(80,80,110)); }
            public void mouseExited(MouseEvent e){ b.setBackground(new Color(60,60,80)); }
        });

        return b;
    }

    // ===== LOGIC =====
    private void addItem(String item) {
        kit.addItem(item);
        log("Added: " + item);
        refreshUI();
    }

    private void refreshUI() {

        int water = kit.getWaterSupplyDays();

        // ✅ CHECK STATUS
        int score = Math.min(
            (water >= 3 ? 40 : water * 13) +
            (kit.isHasFirstAid() ? 30 : 0) +
            (kit.getItems().size() > 0 ? 30 : 0),
            100
        );

        progressBar.setValue(score);
        progressBar.setForeground(score == 100 ? GREEN : RED);

        statusLabel.setText(score == 100 ? "READY" : "NOT READY");
        statusLabel.setForeground(score == 100 ? GREEN : RED);

        checklistLabel.setText("<html>Water: " + water + "/3 days<br>"
                + "First Aid: " + (kit.isHasFirstAid() ? "YES" : "NO") + "</html>");
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
    }

    private void resetKit() {
        kit = new EmergencyKit();
        logArea.setText("");
        log("Inventory reset");
        refreshUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryGUI_Clean().setVisible(true));
    }
}