import javax.swing.*;
import java.awt.*;

public class EnigmaFrame extends JFrame {

    private JComboBox<Integer> rotor1, rotor2, rotor3;
    private JTextField posField;
    private JTextArea inputArea, outputArea;

    public EnigmaFrame() {
        setTitle("Enigma GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        Integer[] rotors = {1, 2, 3, 4, 5};

        rotor1 = new JComboBox<>(rotors);
        rotor2 = new JComboBox<>(rotors);
        rotor3 = new JComboBox<>(rotors);

        // Set preferred size so the boxes line up nicer kinda like the ss
        rotor1.setPreferredSize(new Dimension(60, 22));
        rotor2.setPreferredSize(new Dimension(60, 22));
        rotor3.setPreferredSize(new Dimension(60, 22));

        posField = new JTextField(4);

        inputArea = new JTextArea(8, 40);
        outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);

        JButton encryptBtn = new JButton("Encrypt");
        JButton decryptBtn = new JButton("Decrypt");

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Inner"));
        topPanel.add(rotor1);
        topPanel.add(new JLabel("Middle"));
        topPanel.add(rotor2);
        topPanel.add(new JLabel("Outer"));
        topPanel.add(rotor3);
        topPanel.add(new JLabel("Initial Position"));
        topPanel.add(posField);
        topPanel.add(encryptBtn);
        topPanel.add(decryptBtn);

        // Margins for spacing
        JPanel centerPanel = new JPanel(new BorderLayout());

        centerPanel.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.WEST);
        centerPanel.add(Box.createRigidArea(new Dimension(15, 0)), BorderLayout.EAST);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);

        // Panels for input and output areas
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
        inputPanel.add(new JLabel("Input"), BorderLayout.NORTH);
        inputPanel.add(inputArea, BorderLayout.CENTER);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
        outputPanel.add(new JLabel("Output"), BorderLayout.NORTH);
        outputPanel.add(outputArea, BorderLayout.CENTER);

        JPanel stackedIO = new JPanel(new BorderLayout());
        stackedIO.add(inputPanel, BorderLayout.NORTH);
        stackedIO.add(outputPanel, BorderLayout.CENTER);

        centerPanel.add(stackedIO, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        encryptBtn.addActionListener(e -> runEnigma("encrypt"));
        decryptBtn.addActionListener(e -> runEnigma("decrypt"));
        pack();
        setVisible(true);
    }

    private void runEnigma(String mode) {
        try {
            String[] args = {
                rotor1.getSelectedItem().toString(),

                rotor2.getSelectedItem().toString(),
                rotor3.getSelectedItem().toString(),
                posField.getText().trim(),
                mode,
                inputArea.getText().replace("\n", "").trim()
            };
            
            // Used try catch in case coms throws something
            String result = Comms.run(args);
            outputArea.setText(result);
        } catch (Exception ex) {
            outputArea.setText("Something broke " + ex.getMessage());
        }
    }
}
