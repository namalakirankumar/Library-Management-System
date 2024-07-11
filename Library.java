import java.io.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class LibraryManagementSystem implements ActionListener {

    JFrame frame;
    JButton addBook, removeBook, reserveBook, displayBook, exit, login;
    JTextField username, password;
    boolean loggedIn = true;
    JScrollPane scrollPane;

    LibraryManagementSystem() {
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addBook = new JButton("Add a Book");
        addBook.setBounds(100, 50, 250, 50);
        frame.add(addBook);
        addBook.addActionListener(this);

        removeBook = new JButton("Remove a Book");
        removeBook.setBounds(100, 125, 250, 50);
        frame.add(removeBook);
        removeBook.addActionListener(this);

        reserveBook = new JButton("Reserve a Book");
        reserveBook.setBounds(100, 200, 250, 50);
        frame.add(reserveBook);
        reserveBook.addActionListener(this);

        displayBook = new JButton("Display Books");
        displayBook.setBounds(100, 275, 250, 50);
        frame.add(displayBook);
        displayBook.addActionListener(this);

        exit = new JButton("Exit");
        exit.setBounds(100, 350, 250, 50);
        frame.add(exit);
        exit.addActionListener(this);

        frame.setSize(450, 500);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Scanner input = new Scanner(System.in);

        if (e.getSource() == addBook && loggedIn) {
            String title = JOptionPane.showInputDialog(frame, "Enter the title of the book: ");
            String author = JOptionPane.showInputDialog(frame, "Enter the author of the book: ");
            String pin = JOptionPane.showInputDialog(frame, "Enter the pin number of the book: ");
            String row = JOptionPane.showInputDialog(frame, "Enter the stack number: ");

            try {
                FileWriter fw = new FileWriter("library.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(title + "," + author + "," + pin + "," + row + "," + "false");
                bw.newLine();
                bw.close();
                JOptionPane.showMessageDialog(frame, "Book added successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error writing to file!");
            }
        } else if (e.getSource() == removeBook && loggedIn) {
            String pin = JOptionPane.showInputDialog(frame, "Enter the pin number of the book to remove: ");
            boolean found = false;
            try {
                File inputFile = new File("library.txt");
                File tempFile = new File("temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens[2].equals(pin)) {
                        found = true;
                        continue;
                    }
                    writer.write(line + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
                if (!inputFile.delete()) {
                    JOptionPane.showMessageDialog(frame, "Could not delete file!");
                    return;
                }
                if (!tempFile.renameTo(inputFile)) {
                    JOptionPane.showMessageDialog(frame, "Could not rename file!");
                }
                if (found) {
                    JOptionPane.showMessageDialog(frame, "Book removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Book with the given pin number not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error reading from file!");
            }
        } else if (e.getSource() == reserveBook && loggedIn) {
            String pin = JOptionPane.showInputDialog(frame, "Enter the pin number of the book to reserve: ");
            boolean found = false;
            try {
                File inputFile = new File("library.txt");
                File tempFile = new File("temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens[2].equals(pin) ) {
                        found = true;
                        tokens[4] = "true";
                        line = tokens[0] + "," + tokens[1] + "," + tokens[2] + "," + tokens[3] + "," + tokens[4];
                    }
                    writer.write(line + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
                if (!inputFile.delete()) {
                    JOptionPane.showMessageDialog(frame, "Could not delete file!");
                    return;
                }
                if (!tempFile.renameTo(inputFile)) {
                    JOptionPane.showMessageDialog(frame, "Could not rename file!");
                }
                if (found) {
                    JOptionPane.showMessageDialog(frame, "Book reserved successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Book with the given pin number not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error reading from file!");
            }
        } else  if (e.getSource() == displayBook && loggedIn) {
            String output = "";
            try {
                File inputFile = new File("library.txt");
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    output += "Title: " + tokens[0] + "\n";
                    output += "Author: " + tokens[1] + "\n";
                    output += "Pin No: " + tokens[2] + "\n";
                    output += "Stack No: " + tokens[3] + "\n";
                    output += "Reserved: " + tokens[4] + "\n\n";
                }
                reader.close();

                JOptionPane.showMessageDialog(frame, output.toString(), "Books", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error reading from file!");
            }
        } else if (e.getSource() == exit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new LibraryManagementSystem();
    }
}