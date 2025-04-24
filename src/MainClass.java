import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainClass {
    private static final ServiceManager manager = new ServiceManager();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Vehicle Service Center Management System");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(9, 1, 10, 10));

        // Title
        JLabel title = new JLabel("Service Center Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(title);

        // Buttons
        JButton addBtn = new JButton("1. Add Customer & Vehicle");
        JButton viewAllBtn = new JButton("2. View All Service Records");
        JButton searchBtn = new JButton("3. Search Vehicle by Number");
        JButton updateBtn = new JButton("4. Update Service Details");
        JButton deleteBtn = new JButton("5. Delete Service Record");
        JButton newServiceBtn = new JButton("6. Add New Service Entry");
        JButton viewByOwnerBtn = new JButton("7. View Services by Owner");
        JButton exitBtn = new JButton("8. Exit");

        frame.add(addBtn);
        frame.add(viewAllBtn);
        frame.add(searchBtn);
        frame.add(updateBtn);
        frame.add(deleteBtn);
        frame.add(newServiceBtn);
        frame.add(viewByOwnerBtn);
        frame.add(exitBtn);

        // Event Listeners
        addBtn.addActionListener(e -> addServiceDialog());
        viewAllBtn.addActionListener(e -> showAllServices());
        searchBtn.addActionListener(e -> searchVehicleDialog());
        updateBtn.addActionListener(e -> updateServiceDialog());
        deleteBtn.addActionListener(e -> deleteServiceDialog());
        newServiceBtn.addActionListener(e -> addServiceDialog()); // reuse add dialog
        viewByOwnerBtn.addActionListener(e -> viewByOwnerDialog());
        exitBtn.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private static void addServiceDialog() {
        JTextField number = new JTextField();
        JTextField owner = new JTextField();
        JTextField model = new JTextField();
        JTextField details = new JTextField();
        JTextField date = new JTextField("YYYY-MM-DD");

        Object[] fields = {
                "Vehicle Number:", number,
                "Owner Name:", owner,
                "Model:", model,
                "Service Details:", details,
                "Service Date:", date
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Add Service Entry", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            manager.addService(
                    number.getText(),
                    owner.getText(),
                    model.getText(),
                    details.getText(),
                    date.getText()
            );
            JOptionPane.showMessageDialog(null, "Service entry added successfully.");
        }
    }

    private static void showAllServices() {
        ArrayList<ServiceRecord> records = manager.getAllServices();
        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No records found.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (ServiceRecord record : records) {
            sb.append(record.toString()).append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "All Services", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void searchVehicleDialog() {
        String number = JOptionPane.showInputDialog("Enter Vehicle Number:");
        ServiceRecord record = manager.getServiceByNumber(number);
        if (record != null) {
            JOptionPane.showMessageDialog(null, record.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No record found for vehicle: " + number);
        }
    }

    private static void updateServiceDialog() {
        String idStr = JOptionPane.showInputDialog("Enter Service ID to update:");
        String newDetails = JOptionPane.showInputDialog("Enter New Service Details:");
        try {
            int id = Integer.parseInt(idStr);
            manager.updateServiceDetails(id, newDetails);
            JOptionPane.showMessageDialog(null, "Service updated successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
        }
    }

    private static void deleteServiceDialog() {
        String idStr = JOptionPane.showInputDialog("Enter Service ID to delete:");
        try {
            int id = Integer.parseInt(idStr);
            manager.deleteService(id);
            JOptionPane.showMessageDialog(null, "Service deleted successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input.");
        }
    }

    private static void viewByOwnerDialog() {
        String owner = JOptionPane.showInputDialog("Enter Owner's Name:");
        ArrayList<ServiceRecord> list = manager.getServicesByOwner(owner);
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No services found for owner: " + owner);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (ServiceRecord sr : list) {
            sb.append(sr.toString()).append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Services by Owner", JOptionPane.INFORMATION_MESSAGE);
    }
}
