import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;


/**
 * This class represents a graphical user interface for a calculator application.
 */
public class GUI extends JFrame {

    private JTextField displayField;
    private Taschenrechner rechner;
    private Font font;
    private JTextField nField;
    private JTextField mField;
    private JFrame potenzBruch;
    private int width;
    private int heigth;
    private final Color FOREGROUND_COLOR;
    private final Color BACKGROUND_COLOR;


    /**
     * Constructor for the GUI class.
     * Initializes the calculator and sets up the GUI.
     */
    public GUI() {
        // Initialisierung des Taschenrechners und anderer GUI-Komponenten
        rechner = new Taschenrechner();
        font = new Font("SansSerif", Font.PLAIN, 20);

        // Ermitteln der Bildschirmgröße
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        heigth = (int)Math.round(screenHeight / 1.7);
        width = (int)Math.round(screenHeight / 1.8);
        FOREGROUND_COLOR = Color.WHITE;
        BACKGROUND_COLOR = Color.BLACK;

        // GUI-Fenster-Einstellungen
        setTitle("Taschenrechner");
        setSize(new Dimension(width, heigth));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);

        // Initialisieren der GUI-Komponenten
        initComponents();

        setLocationRelativeTo(null);
    }

    /**
     * Initializes the components of the GUI.
     */
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Textfeld für die Anzeige des Taschenrechner-Ausdrucks
        displayField = new JTextField();
        displayField.setEditable(true);
        displayField.setPreferredSize(new Dimension(width, (int)Math.round(heigth/3)));
        displayField.setFont(font);
        displayField.setBackground(BACKGROUND_COLOR);
        displayField.setForeground(FOREGROUND_COLOR);
        panel.add(displayField, BorderLayout.NORTH);

        // Panel für die Schaltflächen des Taschenrechners
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 6));

        // Schaltflächenbeschriftungen
        String[] buttonLabels = {
                "7", "8", "9", "/", "DEL", "AC",
                "4", "5", "6", "*", "^", "(",
                "1", "2", "3", "-", "*10^", ")",
                ".", "0", "+", "^1\\2", "^m\\n", "="
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            button.setFont(font);
            button.setBackground(BACKGROUND_COLOR);
            button.setForeground(FOREGROUND_COLOR);

            buttonPanel.add(button);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
    }

    /**
     * ActionListener for buttons in the GUI.
     */
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            switch (buttonText) {
                case "=" -> {
                    // Berechnung und Anzeige des Ergebnisses
                    String expression = displayField.getText();
                    displayField.setText(rechner.interpreter(expression));
                }
                case "AC" -> displayField.setText(""); // Alles löschen
                case "^m\\n" -> {potenzBruch();} // Öffnen des Potenz-Bruch-Fensters
                case "Bestätigen" -> {
                    // Potenz-Bruch-Werte bestätigen und im Hauptfeld anzeigen
                    String m = mField.getText();
                    String n = nField.getText();
                    potenzBruch.dispose();
                    displayField.setText(displayField.getText() + "^" + m + "\\" + n);
                }
                case "DEL" -> displayField.setText(displayField.getText().substring(0, displayField.getText().length() - 1));// Lösche das letzte Zeichen aus dem Anzeigefeld
                default -> displayField.setText(displayField.getText() + buttonText); // Text zur Anzeige hinzufügen
            }
        }
    }

    /**
     * Displays a dialog for entering a power with a fraction.
     */
    private void potenzBruch() {
        // Ein neues Panel für das Potenz-Bruch-Fenster erstellen
        JPanel panel = new JPanel();

        // Ein Bild-Icon aus einer Datei laden und in einem JLabel anzeigen
        ImageIcon imageIcon = new ImageIcon("img/potenzBruchBild.jpg");
        JLabel image = new JLabel(imageIcon);

        // Eine Schaltfläche "Bestätigen" erstellen und Labels für n und m hinzufügen
        JButton okButton = new JButton("Bestätigen");
        JLabel n = new JLabel("n");
        JLabel m = new JLabel("m");

        // Das Fenster für die Bruchpotenz initialisieren
        potenzBruch = new JFrame("Potenz mit Bruch");
        potenzBruch.setBackground(BACKGROUND_COLOR);
        potenzBruch.setForeground(FOREGROUND_COLOR);

        // Das Panel in ein 4x2 Rasterlayout aufteilen
        panel.setLayout(new GridLayout(4, 2));

        // Textfelder für m und n erstellen und gestalten
        nField = new JTextField("2");
        mField = new JTextField("1");
        nField.setForeground(FOREGROUND_COLOR);
        mField.setForeground(FOREGROUND_COLOR);
        mField.setBackground(BACKGROUND_COLOR);
        nField.setBackground(BACKGROUND_COLOR);
        n.setBackground(BACKGROUND_COLOR);
        m.setBackground(BACKGROUND_COLOR);
        n.setForeground(FOREGROUND_COLOR);
        m.setForeground(FOREGROUND_COLOR);

        // Schaltfläche "Bestätigen" und Textfelder gestalten
        okButton.setForeground(FOREGROUND_COLOR);
        okButton.setBackground(BACKGROUND_COLOR);
        panel.setBackground(BACKGROUND_COLOR);
        panel.setForeground(FOREGROUND_COLOR);
        okButton.setFont(font);
        mField.setFont(font);
        nField.setFont(font);
        m.setFont(font);
        n.setFont(font);


        // Den ActionListener für die "Bestätigen"-Schaltfläche hinzufügen
        okButton.addActionListener(new ButtonClickListener());

        // Komponenten zum Panel hinzufügen
        panel.add(m);
        panel.add(mField);
        panel.add(n);
        panel.add(nField);
        panel.add(new JLabel()); // Leerer Platzhalter
        panel.add(okButton);
        panel.add(image); // Das Bild anzeigen

        // Das Panel zum Bruchpotenz-Fenster hinzufügen
        potenzBruch.add(panel);

        // Fenstergröße, Position einstellen und sichtbar machen
        potenzBruch.setSize(new Dimension(width + 50, (int)Math.round(heigth/1.75)));
        potenzBruch.setLocationRelativeTo(null);
        potenzBruch.setVisible(true);
    }

}