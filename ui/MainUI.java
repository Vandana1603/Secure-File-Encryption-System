package ui;

import crypto.AESEncryption;
import hash.HashVerifier;
import java.awt.*;
import java.io.File;
import javax.swing.*;

public class MainUI extends JFrame {

    private File selectedFile;
    private JLabel statusLabel;
    private JPasswordField passwordField;

    public MainUI() {

        setTitle("Secure File Encryption System");
        setSize(500, 300);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton selectBtn = new JButton("Select File");
        JButton encryptBtn = new JButton("Encrypt");
        JButton decryptBtn = new JButton("Decrypt");

        passwordField = new JPasswordField(20);

        statusLabel = new JLabel("Select a file");

        add(new JLabel("Password:"));
        add(passwordField);

        add(selectBtn);
        add(encryptBtn);
        add(decryptBtn);
        add(statusLabel);

        selectBtn.addActionListener(e -> selectFile());
        encryptBtn.addActionListener(e -> encryptFile());
        decryptBtn.addActionListener(e -> decryptFile());

        setVisible(true);
    }

    private void selectFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            statusLabel.setText("Selected: " + selectedFile.getName());
        }
    }

    private void encryptFile() {

        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Select file first!");
            return;
        }

        try {
            String password = new String(passwordField.getPassword());

            


            File encryptedFile = new File(selectedFile.getAbsolutePath() + ".enc");

            AESEncryption.encryptFile(selectedFile, encryptedFile, password);

            String hash = HashVerifier.generateHash(encryptedFile);

            statusLabel.setText("Encrypted Successfully");
            JOptionPane.showMessageDialog(this, "Hash:\n" + hash);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decryptFile() {

        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Select file first!");
            return;
        }

        try {

            String password = new String(passwordField.getPassword());
            String originalName = selectedFile.getName();

if (originalName.endsWith(".enc")) {
    originalName = originalName.substring(0, originalName.length() - 4);
}

File decryptedFile = new File("decrypted_" + originalName);

            AESEncryption.decryptFile(selectedFile, decryptedFile, password);

            statusLabel.setText("Decrypted Successfully");
            Desktop.getDesktop().open(decryptedFile);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Wrong password or corrupted file!");
            e.printStackTrace();
        }
    }
}