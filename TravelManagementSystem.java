package Travel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class TravelManagementSystem extends JFrame {

    // Input fields
    private JTextField firstnameField, surnameField, addressField, postcodeField, telephoneField, emailField;
    private JComboBox<String> departureComboBox, accommodationComboBox;

    // Flight details
    private JRadioButton standardClass, economyClass, firstClass;
    private JRadioButton singleTicket, returnTicket;
    private JRadioButton adultYes, childYes;

    // Extras
    private JCheckBox airportTaxCheck, airMilesCheck, insuranceCheck, luggageCheck;

    // Output fields
    private JTextField subTotalField, taxField, totalField;
    private JTextArea receiptArea;

    private static final DecimalFormat CURRENCY = new DecimalFormat("0.00");

    // Theme state
    private boolean isDark = false;

    // Background image file name (place it next to .java)
    private static final String BACKGROUND_IMAGE = "rohit.jpg";


    // MYSQL CONFIG
    private static final String DB_URL = "jdbc:mysql://localhost:3306/travelDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";     // your username
    private static final String DB_PASS = "Rohit@#$789";         // your password

    private void saveToDatabase() {
        String sql = "INSERT INTO bookings (firstname, surname, address, postcode, phone, email, subtotal, tax, total, booking_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, firstnameField.getText());
            pst.setString(2, surnameField.getText());
            pst.setString(3, addressField.getText());
            pst.setString(4, postcodeField.getText());
            pst.setString(5, telephoneField.getText());
            pst.setString(6, emailField.getText());
            pst.setString(7, subTotalField.getText());
            pst.setString(8, taxField.getText());
            pst.setString(9, totalField.getText());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Booking Saved Successfully!",
                    "MySQL",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error Saving to Database:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    public TravelManagementSystem() {
        setTitle("Travel Management System");
        setSize(1200, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Background panel (paints image)
        BackgroundPanel bgPanel = new BackgroundPanel("rohit.jpg");
        bgPanel.setLayout(new BorderLayout());
        add(bgPanel, BorderLayout.CENTER);

        // Transparent content container with padding
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setOpaque(false);
        contentWrapper.setBorder(new EmptyBorder(12, 12, 12, 12));
        bgPanel.add(contentWrapper, BorderLayout.CENTER);

        // Header (includes theme toggle)
        JPanel header = createHeader();
        header.setOpaque(false);
        contentWrapper.add(header, BorderLayout.NORTH);

        // Main panel (three columns) placed on a semi-transparent card for contrast
        JPanel card = new JPanel(new GridLayout(1, 3, 10, 10));
        card.setBorder(new EmptyBorder(10, 10, 10, 10));
        card.setOpaque(false);

        // Wrap card inside a translucent overlay panel for readability
        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        overlay.setBackground(new Color(255, 255, 255, 190)); // light translucent by default
        overlay.setOpaque(true);
        overlay.add(card, BorderLayout.CENTER);

        // Add columns
        card.add(createLeftPanel());
        card.add(createMiddlePanel());
        card.add(createRightPanel());

        // place overlay centered with margin
        contentWrapper.add(overlay, BorderLayout.CENTER);

        applyTheme(); // set initial theme colors

        setVisible(true);
    }

    // HEADER PANEL
    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);

        JLabel title = new JLabel("Travel Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        right.setOpaque(false);

        JButton themeToggle = createFlatButton("Dark Mode");
        themeToggle.setPreferredSize(new Dimension(120, 36));
        themeToggle.addActionListener(e -> {
            isDark = !isDark;
            themeToggle.setText(isDark ? "Light Mode" : "Dark Mode");
            applyTheme();
        });

        right.add(themeToggle);

        panel.add(title, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        return panel;
    }

    // MAIN 3-COLUMN PANELS
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Customer Details"));
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        firstnameField = new JTextField();
        surnameField = new JTextField();
        addressField = new JTextField();
        postcodeField = new JTextField();
        telephoneField = new JTextField();
        emailField = new JTextField();

        int row = 0;
        addField(panel, gbc, row++, "Firstname:", firstnameField);
        addField(panel, gbc, row++, "Surname:", surnameField);
        addField(panel, gbc, row++, "Address:", addressField);
        addField(panel, gbc, row++, "Postcode:", postcodeField);
        addField(panel, gbc, row++, "Telephone:", telephoneField);
        addField(panel, gbc, row++, "Email:", emailField);

        // Flight details below
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.0;

        JPanel flightPanel = new JPanel(new GridLayout(3, 1, 6, 6));
        flightPanel.setBorder(BorderFactory.createTitledBorder("Flight Details"));
        flightPanel.setOpaque(false);

        // Flight class
        standardClass = new JRadioButton("Standard");
        economyClass = new JRadioButton("Economy");
        firstClass = new JRadioButton("First Class", true);
        ButtonGroup classGroup = new ButtonGroup();
        classGroup.add(standardClass);
        classGroup.add(economyClass);
        classGroup.add(firstClass);

        JPanel classPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        classPanel.setOpaque(false);
        classPanel.add(standardClass);
        classPanel.add(economyClass);
        classPanel.add(firstClass);

        // Ticket Type
        singleTicket = new JRadioButton("Single");
        returnTicket = new JRadioButton("Return", true);
        ButtonGroup ticketGroup = new ButtonGroup();
        ticketGroup.add(singleTicket);
        ticketGroup.add(returnTicket);

        JPanel ticketPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ticketPanel.setBorder(BorderFactory.createTitledBorder("Ticket"));
        ticketPanel.setOpaque(false);
        ticketPanel.add(singleTicket);
        ticketPanel.add(returnTicket);

        // Pax
        adultYes = new JRadioButton("Adult");
        childYes = new JRadioButton("Child");

        JPanel paxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paxPanel.setBorder(BorderFactory.createTitledBorder("Passengers"));
        paxPanel.setOpaque(false);
        paxPanel.add(adultYes);
        paxPanel.add(childYes);

        flightPanel.add(classPanel);
        flightPanel.add(ticketPanel);
        flightPanel.add(paxPanel);

        panel.add(flightPanel, gbc);

        return panel;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JPanel createMiddlePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setOpaque(false);

        // Trip
        JPanel tripPanel = new JPanel(new GridLayout(3, 2, 6, 6));
        tripPanel.setBorder(BorderFactory.createTitledBorder("Trip Details"));
        tripPanel.setOpaque(false);

        departureComboBox = new JComboBox<>(new String[]{"Gatwick", "Heathrow", "Manchester"});
        accommodationComboBox = new JComboBox<>(new String[]{"Extra", "Standard", "None"});

        tripPanel.add(new JLabel("Departure:"));
        tripPanel.add(departureComboBox);

        tripPanel.add(new JLabel("Destination:"));
        tripPanel.add(new JTextField("Nigeria - Lagos (5 Days)"));

        tripPanel.add(new JLabel("Accommodation:"));
        tripPanel.add(accommodationComboBox);

        // Extras
        JPanel extrasPanel = new JPanel(new GridLayout(2, 2, 6, 6));
        extrasPanel.setBorder(BorderFactory.createTitledBorder("Extras"));
        extrasPanel.setOpaque(false);

        airportTaxCheck = new JCheckBox("Airport Tax");
        airMilesCheck = new JCheckBox("Air Miles +20000");
        insuranceCheck = new JCheckBox("Insurance");
        luggageCheck = new JCheckBox("Extra Luggage");

        extrasPanel.add(airportTaxCheck);
        extrasPanel.add(airMilesCheck);
        extrasPanel.add(insuranceCheck);
        extrasPanel.add(luggageCheck);

        // Totals
        JPanel totalPanel = new JPanel(new GridLayout(4, 2, 6, 6));
        totalPanel.setBorder(BorderFactory.createTitledBorder("Cost Summary"));
        totalPanel.setOpaque(false);

        subTotalField = new JTextField("£0.00");
        taxField = new JTextField("£0.00");
        totalField = new JTextField("£0.00");
        subTotalField.setEditable(false);
        taxField.setEditable(false);
        totalField.setEditable(false);

        totalPanel.add(new JLabel("Subtotal:"));
        totalPanel.add(subTotalField);
        totalPanel.add(new JLabel("Tax:"));
        totalPanel.add(taxField);
        totalPanel.add(new JLabel("Total:"));
        totalPanel.add(totalField);

        // Buttons area with modern flat buttons
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        btns.setOpaque(false);

        JButton totalBtn = createFlatButton("Calculate Total");
        JButton resetBtn = createFlatButton("Reset");

        totalBtn.addActionListener(e -> calculateTotal());
        resetBtn.addActionListener(e -> resetFields());

        btns.add(totalBtn);
        btns.add(resetBtn);

        // Place the two components (totals + buttons) in the same panel region
        JPanel totalAndBtns = new JPanel(new BorderLayout());
        totalAndBtns.setOpaque(false);
        totalAndBtns.add(totalPanel, BorderLayout.CENTER);
        totalAndBtns.add(btns, BorderLayout.SOUTH);

        panel.add(tripPanel);
        panel.add(extrasPanel);
        panel.add(totalAndBtns);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Receipt"));
        panel.setOpaque(false);

        receiptArea = new JTextArea();
        receiptArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        receiptArea.setEditable(false);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
        btnPanel.setOpaque(false);

        JButton receiptBtn = createFlatButton("Generate Receipt");
        JButton exitBtn = createFlatButton("Exit");

        receiptBtn.addActionListener(e -> generateReceipt());
        exitBtn.addActionListener(e -> System.exit(0));

        btnPanel.add(receiptBtn);
        btnPanel.add(exitBtn);

        panel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ---------- UI Helpers: flat button and hover ----------
    private JButton createFlatButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        // background will be set by applyTheme()
        btn.addMouseListener(new MouseAdapter() {
            Color original;
            @Override
            public void mouseEntered(MouseEvent e) {
                original = btn.getBackground();
                btn.setBackground(original.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(original);
            }
        });
        return btn;
    }

    // Theme application (manual dark/light, modifies overlay & button colors)
    private void applyTheme() {
        // Find overlay (the translucent panel added to frame center)
        Component[] comps = getContentPane().getComponents();
        // we added bgPanel at CENTER, overlay is child of bgPanel; but simpler: traverse all panels and adjust
        Color overlayLight = new Color(255, 255, 255, 200);
        Color overlayDark = new Color(20, 20, 20, 200);
        Color textLight = Color.BLACK;
        Color textDark = Color.WHITE;
        Color buttonLight = new Color(45, 140, 255); // blue
        Color buttonDark = new Color(70, 130, 180); // muted blue

        // walk component tree and set styles
        SwingUtilities.invokeLater(() -> {
            setBackgroundForAll(this, isDark, overlayLight, overlayDark, textLight, textDark, buttonLight, buttonDark);
            repaint();
        });
    }

    private void setBackgroundForAll(Container root, boolean dark,
                                     Color overlayLight, Color overlayDark,
                                     Color textLight, Color textDark,
                                     Color buttonLight, Color buttonDark) {
        for (Component c : root.getComponents()) {
            if (c instanceof JPanel) {
                JPanel p = (JPanel) c;
                // if panel has a titled border or is the translucent overlay (we check opacity)
                if (p.isOpaque()) {
                    // leave background for full-window bgPanel alone
                    // for translucent overlay panels, set different color
                    if (p.getBackground().getAlpha() > 0 && p.getBackground().getAlpha() < 255) {
                        p.setBackground(dark ? overlayDark : overlayLight);
                    } else {
                        // non-overlay Opaque panels inside overlay keep transparent feel
                        // leave as is or set slightly transparent backgrounds for inner panels that had opaque false
                    }
                }
                // update children recursively
                setBackgroundForAll(p, dark, overlayLight, overlayDark, textLight, textDark, buttonLight, buttonDark);
            }
            if (c instanceof JLabel) {
                c.setForeground(dark ? textDark : textLight);
            }
            if (c instanceof JTextField) {
                JTextField tf = (JTextField) c;
                tf.setBackground(dark ? new Color(40, 40, 40) : Color.WHITE);
                tf.setForeground(dark ? Color.WHITE : Color.BLACK);
                tf.setCaretColor(dark ? Color.WHITE : Color.BLACK);
            }
            if (c instanceof JTextArea) {
                JTextArea ta = (JTextArea) c;
                ta.setBackground(dark ? new Color(30, 30, 30) : new Color(250, 250, 250));
                ta.setForeground(dark ? Color.WHITE : Color.BLACK);
            }
            if (c instanceof JButton) {
                JButton b = (JButton) c;
                b.setOpaque(true);
                b.setBorderPainted(false);
                b.setForeground(Color.WHITE);
                b.setBackground(dark ? buttonDark : buttonLight);
            }
            if (c instanceof JCheckBox) {
                JCheckBox cb = (JCheckBox) c;
                cb.setOpaque(false);
                cb.setForeground(dark ? textDark : textLight);
            }
            if (c instanceof JRadioButton) {
                JRadioButton rb = (JRadioButton) c;
                rb.setOpaque(false);
                rb.setForeground(dark ? textDark : textLight);
            }
            if (c instanceof JComboBox) {
                JComboBox<?> cbx = (JComboBox<?>) c;
                cbx.setBackground(dark ? new Color(50, 50, 50) : Color.WHITE);
                cbx.setForeground(dark ? Color.WHITE : Color.BLACK);
            }
            if (c instanceof JScrollPane) {
                JScrollPane sp = (JScrollPane) c;
                JViewport vp = sp.getViewport();
                Component view = vp.getView();
                if (view != null) {
                    view.setBackground(isDark ? new Color(30, 30, 30) : new Color(250, 250, 250));
                }
            }
            // if the component is a container, recurse deeper
            if (c instanceof Container) {
                setBackgroundForAll((Container) c, dark, overlayLight, overlayDark, textLight, textDark, buttonLight, buttonDark);
            }
        }
    }

    // LOGIC --------------------------------------------------
    private void calculateTotal() {
        double base = 0;

        if (firstClass.isSelected()) base = 400;
        else if (economyClass.isSelected()) base = 250;
        else if (standardClass.isSelected()) base = 150;

        if (returnTicket.isSelected()) base *= 1.8;

        if (adultYes.isSelected()) base += 50;
        if (childYes.isSelected()) base += 25;

        if ("Extra".equals(accommodationComboBox.getSelectedItem())) base += 100;

        if (luggageCheck.isSelected()) base += 25;

        double tax = base * 0.05;
        double carCost = 26.03;

        double finalTotal = base + tax + carCost;

        subTotalField.setText("£" + CURRENCY.format(base));
        taxField.setText("£" + CURRENCY.format(tax + carCost));
        totalField.setText("£" + CURRENCY.format(finalTotal));
    }

    private void resetFields() {
        firstnameField.setText("");
        surnameField.setText("");
        addressField.setText("");
        postcodeField.setText("");
        telephoneField.setText("");
        emailField.setText("");

        firstClass.setSelected(true);
        returnTicket.setSelected(true);

        adultYes.setSelected(false);
        childYes.setSelected(false);

        airportTaxCheck.setSelected(false);
        airMilesCheck.setSelected(false);
        insuranceCheck.setSelected(false);
        luggageCheck.setSelected(false);

        subTotalField.setText("£0.00");
        taxField.setText("£0.00");
        totalField.setText("£0.00");
        receiptArea.setText("");
    }

    private void generateReceipt() {
        // validate email and phone
        String email = emailField.getText().trim();
        String phone = telephoneField.getText().trim();

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!isValidPhone(phone)) {
            JOptionPane.showMessageDialog(this, "Phone must be 10 digits (numbers only).", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        String receipt = """
                ===============================
                       TRAVEL RECEIPT
                ===============================

                Name: %s
                Address: %s
                Postcode: %s
                Telephone: %s
                Email: %s

                Subtotal: %s
                Tax: %s
                Total: %s

                Date: %s     Time: %s

                Thank you for using our service!
                """.formatted(
                firstnameField.getText(),
                surnameField.getText(),
                addressField.getText(),
                postcodeField.getText(),
                telephoneField.getText(),
                emailField.getText(),
                subTotalField.getText(),
                taxField.getText(),
                totalField.getText(),
                now.toLocalDate(),
                now.toLocalTime().withNano(0)
        );

        receiptArea.setText(receipt);
        saveToDatabase();
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) return false;
        // simple regex, accepts common formats
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }

    private boolean isValidPhone(String phone) {
        if (phone == null) return false;
        // digits only, 10 digits typical; adjust if needed
        return phone.matches("^[0-9]{10}$");
    }

    // BACKGROUND PANEL (paints travel themed image and slight vignette)
    private static class BackgroundPanel extends JPanel {
        private Image bgImage;

        public BackgroundPanel(String imagePath) {
            try {
                bgImage = ImageIO.read(new File(imagePath));
            } catch (Exception e) {
                System.err.println("Background image not found: " + imagePath + " — using plain color.");
                bgImage = null;
            }
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bgImage != null) {
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                // dark vignette to make overlay readable
                Graphics2D g2 = (Graphics2D) g.create();
                float alpha = 0.25f;
                g2.setColor(new Color(0, 0, 0, Math.round(alpha * 255)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            } else {
                // nice fallback gradient background
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 60, 120), 0, getHeight(), new Color(100, 160, 220));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        }
    }

    public static void main(String[] args) {
        // no external LAF required
        SwingUtilities.invokeLater(TravelManagementSystem::new);
    }
}


