import java.sql.*;
import java.util.ArrayList;

public class ServiceManager {

    public void addService(String number, String owner, String model, String details, String date) {
        String sql = "INSERT INTO services (vehicle_number, owner_name, model, service_details, service_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, number);
            stmt.setString(2, owner);
            stmt.setString(3, model);
            stmt.setString(4, details);
            stmt.setDate(5, Date.valueOf(date));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ServiceRecord> getAllServices() {
        ArrayList<ServiceRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM services";

        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ServiceRecord(
                        rs.getInt("id"),
                        rs.getString("vehicle_number"),
                        rs.getString("owner_name"),
                        rs.getString("model"),
                        rs.getString("service_details"),
                        rs.getDate("service_date").toString()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ServiceRecord getServiceByNumber(String number) {
        String sql = "SELECT * FROM services WHERE vehicle_number = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, number);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ServiceRecord(
                        rs.getInt("id"),
                        rs.getString("vehicle_number"),
                        rs.getString("owner_name"),
                        rs.getString("model"),
                        rs.getString("service_details"),
                        rs.getDate("service_date").toString()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateServiceDetails(int id, String newDetails) {
        String sql = "UPDATE services SET service_details = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newDetails);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteService(int id) {
        String sql = "DELETE FROM services WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ServiceRecord> getServicesByOwner(String ownerName) {
        ArrayList<ServiceRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM services WHERE owner_name = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ownerName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new ServiceRecord(
                        rs.getInt("id"),
                        rs.getString("vehicle_number"),
                        rs.getString("owner_name"),
                        rs.getString("model"),
                        rs.getString("service_details"),
                        rs.getDate("service_date").toString()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
