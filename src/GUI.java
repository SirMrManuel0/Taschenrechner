import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame {

    private JTextField displayField;
    private Taschenrechner rechner;
    private Font font;
    private JTextField nField;
    private JTextField mField;
    private JFrame potenzBruch;

    public GUI() {
        rechner = new Taschenrechner();
        font = new Font("SansSerif", Font.PLAIN, 20);

        setTitle("Taschenrechner");
        setSize(new Dimension(700, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();

        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        displayField = new JTextField();
        displayField.setEditable(true);
        displayField.setPreferredSize(new Dimension(700, 200));
        displayField.setFont(font);
        panel.add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 6));

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
            buttonPanel.add(button);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            switch (buttonText) {
                case "=" -> {
                    String expression = displayField.getText();
                    displayField.setText(rechner.interpreter(expression));
                }
                case "AC" -> displayField.setText("");
                case "^m\\n" -> {potenzBruch();}
                case "Bestätigen" -> {
                    String m = mField.getText();
                    String n = nField.getText();
                    potenzBruch.dispose();

                    displayField.setText(displayField.getText() + "^" + m + "\\" + n);
                }
                case "DEL" -> displayField.setText(displayField.getText().substring(0, displayField.getText().length() - 1));
                default -> displayField.setText(displayField.getText() + buttonText);
            }
        }
    }


    private void potenzBruch() {
        JPanel panel = new JPanel();
        ImageIcon imageIcon = new ImageIcon("img/potenzBruchBild.jpg");
        JLabel image = new JLabel(imageIcon);
        JButton okButton = new JButton("Bestätigen");
        JLabel n = new JLabel("n");
        JLabel m = new JLabel("m");

        potenzBruch = new JFrame("Potenz mit Bruch");

        panel.setLayout(new GridLayout(4, 2));

        nField = new JTextField("2");
        mField = new JTextField("1");

        okButton.setFont(font);
        mField.setFont(font);
        nField.setFont(font);
        m.setFont(font);
        n.setFont(font);



        okButton.addActionListener(new ButtonClickListener());

        panel.add(m);
        panel.add(mField);
        panel.add(n);
        panel.add(nField);
        panel.add(new JLabel());
        panel.add(okButton);
        panel.add(image);


        potenzBruch.add(panel);
        potenzBruch.setSize(new Dimension(650, 400));
        potenzBruch.setLocationRelativeTo(null);
        potenzBruch.setVisible(true);
    }

}