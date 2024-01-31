import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class register extends JFrame {
    private JTextField nameField, idField, mobileField, dobField, addressField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderGroup;
    private List<User> userList;
    private DefaultTableModel tableModel;

    public register() {
        // Set the frame properties
        setTitle("User Form");
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 4, 10));
        formPanel.setBorder(new TitledBorder("Registration Form"));
        
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(10);
        formPanel.add(idLabel);
        formPanel.add(idField);

        JLabel mobileLabel = new JLabel("Mobile:");
        mobileField = new JTextField(15);
        formPanel.add(mobileLabel);
        formPanel.add(mobileField);

        JLabel genderLabel = new JLabel("Gender:");
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        formPanel.add(genderLabel);
        formPanel.add(maleRadioButton);
        formPanel.add(new JPanel());
        formPanel.add(femaleRadioButton);


        JLabel dobLabel = new JLabel("Date of Birth:");
        dobField = new JTextField(10);
        formPanel.add(dobLabel);
        formPanel.add(dobField);

        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField(20);
        formPanel.add(addressLabel);
        formPanel.add(addressField);

        JButton submitButton = new JButton("Submit");
        submitButton.setIcon(new ImageIcon("submit_icon.png")); // Example: Add an icon to the submit button
        submitButton.setBackground(Color.GREEN); // Example: Set background color
        submitButton.setForeground(Color.BLACK); // Example: Set text color
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()){
                    addUserToDatabase();
                    clearFields();
                    refreshTable();
                }
            }
        });

        
        // Create table
        tableModel = new DefaultTableModel();
        JTable dataTable = new JTable(tableModel);

        // Set layout
        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(submitButton, BorderLayout.SOUTH);
        mainPanel.add(new JScrollPane(dataTable), BorderLayout.CENTER);

        // Add components to the frame
        add(mainPanel, BorderLayout.CENTER);
        

        // Initialize the user list
        userList = new ArrayList<>();
    }

    private boolean validateFields() {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String mobile = mobileField.getText().trim();
        String dob = dobField.getText().trim();
        String address = addressField.getText().trim();

        // Validate name
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate ID uniqueness
        for (User user : userList) {
            if (user.getId().equals(id)) {
                JOptionPane.showMessageDialog(this, "ID already exists. Please enter a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Validate mobile number format
        if (!mobile.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 10-digit mobile number.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate date of birth format (assuming MM/DD/YYYY)
        if (!dob.matches("\\d{2}/\\d{2}/\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid date of birth (MM/DD/YYYY).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate address
        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a address.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void refreshTable() {
        //clear existing data
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[]{"Name", "ID", "Mobile", "Gender", "Date Of Birth", "Address"});

        //populate the table with user data
        for (User user : userList) {
            Object[] rowData = {user.getName(), user.getId(), user.getMobile(),
            user.getGender(), user.getDob(), user.getAddress()};
            tableModel.addRow(rowData);
        }
    
    }

    private void addUserToDatabase() {
        String name = nameField.getText();
        String id = idField.getText();
        String mobile = mobileField.getText();
        String gender = maleRadioButton.isSelected() ? "Male" : "Female";
        String dob = dobField.getText();
        String address = addressField.getText();   

        // Add user to the in-memory database
        userList.add(new User(name, id, mobile, gender, dob, address));

        // Display success message
        JOptionPane.showMessageDialog(this, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Print the current users in the database (for demonstration purposes)
        System.out.println("Current Users in the Database:");
        for (User user : userList) {
            System.out.println(user);
        }
    }

    private void clearFields() {
        nameField.setText("");
        idField.setText("");
        mobileField.setText("");
        genderGroup.clearSelection();
        dobField.setText("");
        addressField.setText("");
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Set the look and feel to Nimbus for better appearance (optional)
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }

            register userForm = new register();
            userForm.setVisible(true);
        });
    }
}

class User {
    private String name;
    private String id;
    private String mobile;
    private String gender;
    private String dob;
    private String address;

    public User(String name, String id, String mobile, String gender, String dob, String address) {
        this.name = name;
        this.id = id;
        this.mobile = mobile;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}