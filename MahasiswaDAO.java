import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaDAO {
    public List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();
        String query = "SELECT * FROM mahasiswa";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Mahasiswa mahasiswa = new Mahasiswa(
                    rs.getInt("nim"),
                    rs.getString("nama"),
                    rs.getString("jurusan"),
                    rs.getString("email"),
                    rs.getDouble("ipk")
                );
                mahasiswaList.add(mahasiswa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mahasiswaList;
    }

    public void tambahMahasiswa(Mahasiswa mahasiswa) {
        String query = "INSERT INTO mahasiswa (nim, nama, jurusan, email, ipk) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, mahasiswa.getNim());
            pstmt.setString(2, mahasiswa.getNama());
            pstmt.setString(3, mahasiswa.getJurusan());
            pstmt.setString(4, mahasiswa.getEmail());
            pstmt.setDouble(5, mahasiswa.getIpk());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMahasiswa(Mahasiswa mahasiswa) {
        String query = "UPDATE mahasiswa SET nama=?, jurusan=?, email=?, ipk=? WHERE nim=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, mahasiswa.getNama());
            pstmt.setString(2, mahasiswa.getJurusan());
            pstmt.setString(3, mahasiswa.getEmail());
            pstmt.setDouble(4, mahasiswa.getIpk());
            pstmt.setInt(5, mahasiswa.getNim());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hapusMahasiswa(int nim) {
        String query = "DELETE FROM mahasiswa WHERE nim=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, nim);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}