import java.awt.*;  
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

class Node {
    int bus_number;
    String Bus_name;
    String start;
    String dest;
    int cap[]; 
    String name[];
    int age[];

    Node next;

    Node(int size) {
        next = null;
        cap = new int[size];
        name = new String[size]; 
        age = new int[size];     
    }
}

public class BusManagementSystem extends JFrame implements ActionListener {
    Node head;
    JTextArea displayArea;
    JTextField busNumberField, busNameField, startField, destField, capacityField, seatField,UserName,age;
    JTextField nameField,ageField; 
    JButton searchButton, bookButton, cancelButton, displayButton;

    
    String registeredUsername;
    String registeredPassword;
    String Username;
    String Age;
    BusManagementSystem() {
        head = null;
        createSignupWindow();
    }

    
    private void createSignupWindow() {
        JFrame signupFrame = new JFrame("Sign Up");
        signupFrame.setSize(400, 300);
        signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signupFrame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();
        JButton signupButton = new JButton("Sign Up");
        JButton cancelButton = new JButton("Cancel");

        signupFrame.add(userLabel);
        signupFrame.add(userText);
        signupFrame.add(passwordLabel);
        signupFrame.add(passwordText);
        signupFrame.add(signupButton);
        signupFrame.add(cancelButton);

        signupButton.addActionListener(e -> {
            registeredUsername = userText.getText(); 
            registeredPassword = new String(passwordText.getPassword());
            if (!registeredUsername.isEmpty() && !registeredPassword.isEmpty()) {
                JOptionPane.showMessageDialog(signupFrame, "Signup Successful!");
                signupFrame.dispose();
                createLoginWindow();
            } else {
                JOptionPane.showMessageDialog(signupFrame, "Please enter all fields.");
            }
        });

        cancelButton.addActionListener(e -> System.exit(0));

        signupFrame.setVisible(true);
    }

    
    private void createLoginWindow() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        loginFrame.add(userLabel);
        loginFrame.add(userText);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordText);
        loginFrame.add(loginButton);
        loginFrame.add(cancelButton);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            if (username.equals(registeredUsername) && password.equals(registeredPassword)) {
                loginFrame.dispose();
                createMainWindow();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid Credentials");
            }
        });

        cancelButton.addActionListener(e -> System.exit(0));

        loginFrame.setVisible(true);
    }

    
    private void createMainWindow() {
        JFrame mainFrame = new JFrame("Bus Management System");
        mainFrame.setSize(600, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(10, 10));

        
        displayArea = new JTextArea(15, 40);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        displayArea.setEditable(false);
        mainFrame.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        
        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));


        inputPanel.add(new JLabel("Bus Number:"));
        busNumberField = new JTextField();
        inputPanel.add(busNumberField);

        inputPanel.add(new JLabel("Bus Name:"));
        busNameField = new JTextField();
        inputPanel.add(busNameField);

        inputPanel.add(new JLabel("Seat Number:"));
        seatField = new JTextField();
        inputPanel.add(seatField);

        inputPanel.add(new JLabel("Starting Location:"));
        startField = new JTextField();
        inputPanel.add(startField);

        inputPanel.add(new JLabel("Destination:"));
        destField = new JTextField();
        inputPanel.add(destField);

        inputPanel.add(new JLabel("Capacity:"));
        capacityField = new JTextField();
        inputPanel.add(capacityField);
        inputPanel.add(new JLabel("Name:"));
        UserName = new JTextField();
        inputPanel.add(UserName);
        inputPanel.add(new JLabel("Age:"));
        age = new JTextField();
        inputPanel.add(age);
        

        mainFrame.add(inputPanel, BorderLayout.NORTH);

       
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        
        searchButton = new JButton("Search Bus");
        bookButton = new JButton("Book Ticket");
        cancelButton = new JButton("Cancel Booking");
        displayButton = new JButton("Display Buses");

        
        searchButton.setBackground(Color.decode("#4CAF50")); 
        searchButton.setForeground(Color.WHITE);
        bookButton.setBackground(Color.decode("#2196F3")); 
        bookButton.setForeground(Color.WHITE);
        cancelButton.setBackground(Color.decode("#f44336")); 
        cancelButton.setForeground(Color.WHITE);
        displayButton.setBackground(Color.decode("#FFC107")); 
        displayButton.setForeground(Color.WHITE);

        
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        searchButton.setFont(buttonFont);
        bookButton.setFont(buttonFont);
        cancelButton.setFont(buttonFont);
        displayButton.setFont(buttonFont);

        
        buttonPanel.add(searchButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(displayButton);

        mainFrame.add(buttonPanel, BorderLayout.SOUTH);

        
        searchButton.addActionListener(this);
        bookButton.addActionListener(this);
        cancelButton.addActionListener(this);
        displayButton.addActionListener(this);

        
        populateDefaultBuses();
        mainFrame.setVisible(true);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String command = e.getActionCommand();
        switch (command) {
            case "Search Bus":
                searchBus();
                break;
            case "Book Ticket":
                bookTicket();
                break;
            case "Cancel Booking":
                cancelBooking();
                break;
            case "Display Buses":
                createStorageWindow();
                break;
        }
    }

    
    void populateDefaultBuses() {
        insert(101, "Express", "Perundurai", "Erode", 60);
        insert(102, "SuperFast", "Konnavaikal", "Erode", 60);
        insert(103, "Local", "Coimbatore", "Erode", 60);
    }

    
    void insert(int bus_number, String Bus_name, String start, String dest, int cap) {
        Node l = new Node(cap);
        l.bus_number = bus_number;
        l.Bus_name = Bus_name;
        l.start = start;
        l.dest = dest;
        if (head == null) {
            head = l;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = l;
        }
    }

    
    void searchBus() {
        String busNumberInput = busNumberField.getText();
        
        if (busNumberInput.isEmpty()) {
            displayArea.append("Please enter a bus number to search.\n");
        } else {
            try {
                int busNum = Integer.parseInt(busNumberInput);
                Node temp = head;
                while (temp != null && temp.bus_number != busNum) {
                    temp = temp.next;
                }
                if (temp != null) {
                    busNumberField.setText(String.valueOf(temp.bus_number));
                    busNameField.setText(temp.Bus_name);
                    startField.setText(temp.start);
                    destField.setText(temp.dest);
                    capacityField.setText(String.valueOf(temp.cap.length));
                    displayArea.append("Bus " + temp.Bus_name + " found.\n");
                } else {
                    displayArea.append("No such bus found.\n");
                }
            } catch (NumberFormatException ex) {
                displayArea.append("Invalid bus number format.\n");
            }
        }
    }

    
    void bookTicket() {
        String seatNumInput = seatField.getText();
        Username = UserName.getText();
        Age = age.getText();
        if (seatNumInput.isEmpty()) {
            displayArea.append("Please enter a seat number to book.\n");
            return;
        }
        try {
            int seatNum = Integer.parseInt(seatNumInput) - 1; 
            String busNumberInput = busNumberField.getText();
            if (busNumberInput.isEmpty()) {
                displayArea.append("Please select a bus first.\n");
                return;
            }
            int busNum = Integer.parseInt(busNumberInput);
            Node temp = head;
            while (temp != null && temp.bus_number != busNum) {
                temp = temp.next;
            }
            if (temp != null) {
                if (seatNum < 0 || seatNum >= temp.cap.length) {
                    displayArea.append("Invalid seat number.\n");
                } else if (temp.cap[seatNum] == 1) {
                    displayArea.append("Seat " + (seatNum + 1) + " is already booked.\n");
                } else {
                    temp.cap[seatNum] = 1; 
                    
                    temp.name[seatNum] = Username; 
                    temp.age[seatNum] = Integer.parseInt(Age); 
                    displayArea.append("Ticket is booked successfully.\n");
                    displayArea.append("Booked seats for bus number " + temp.bus_number + " (" + temp.Bus_name + ").\n");
                    displayArea.append("Seat: " + (seatNum + 1) + " " + Username + " (Age: " +Age + ")\n");
                }
            } else {
                displayArea.append("Bus not found.\n");
            }
        } catch (NumberFormatException ex) {
            displayArea.append("Invalid seat number format.\n");
        }
    }

    
    void cancelBooking() {
        String seatNumInput = seatField.getText();
        if (seatNumInput.isEmpty()) {
            displayArea.append("Please enter a seat number to cancel booking.\n");
            return;
        }
        try {
            int seatNum = Integer.parseInt(seatNumInput) - 1; 
            String busNumberInput = busNumberField.getText();
            if (busNumberInput.isEmpty()) {
                displayArea.append("Please select a bus first.\n");
                return;
            }
            int busNum = Integer.parseInt(busNumberInput);
            Node temp = head;
            while (temp != null && temp.bus_number != busNum) {
                temp = temp.next;
            }
            if (temp != null) {
                if (seatNum < 0 || seatNum >= temp.cap.length) {
                    displayArea.append("Invalid seat number.\n");
                } else if (temp.cap[seatNum] == 0) {
                    displayArea.append("Seat " + (seatNum + 1) + " is not booked.\n");
                } else {
                    temp.cap[seatNum] = 0; 
                    temp.name[seatNum] = null; 
                    temp.age[seatNum] = 0; 
                    displayArea.append("Booking for seat " + (seatNum + 1) + " cancelled successfully.\n");
                }
            } else {
                displayArea.append("Bus not found.\n");
            }
        } catch (NumberFormatException ex) {
            displayArea.append("Invalid seat number format.\n");
        }
    }

    
    void createStorageWindow() {
        JFrame storageFrame = new JFrame("Stored Buses");
        storageFrame.setSize(600, 300);
        storageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        String[] columnNames = {"Bus Number", "Bus Name", "From", "To", "Total Seats", "Booked Seats", "Available Seats"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        
        
        Node temp = head;
        while (temp != null) {
            int totalSeats = temp.cap.length;
            int bookedSeats = 0;
            for (int seat : temp.cap) {
                if (seat == 1) {
                    bookedSeats++;
                }
            }
            int availableSeats = totalSeats - bookedSeats;
            
            Object[] rowData = {
                temp.bus_number,
                temp.Bus_name,
                temp.start,
                temp.dest,
                totalSeats,
                bookedSeats,
                availableSeats
            };
            tableModel.addRow(rowData);
            temp = temp.next;
        }

        
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 12));

        
        JScrollPane scrollPane = new JScrollPane(table);
        storageFrame.add(scrollPane);

        storageFrame.setVisible(true);
    }

    
    public static void main(String[] args) {
        new BusManagementSystem();
    }
}
