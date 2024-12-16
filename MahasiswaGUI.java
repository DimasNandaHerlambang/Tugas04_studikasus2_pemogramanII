import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MahasiswaGUI extends JFrame {
    private MahasiswaDAO mahasiswaDAO;
    private JTable tabelMahasiswa;
    private DefaultTableModel modelTabel;
    
    private JTextField txtNIM, txtNama, txtJurusan, txtEmail, txtIPK;
    private JButton btnTambah, btnUpdate, btnHapus, btnBersih;

    public MahasiswaGUI() {
        mahasiswaDAO = new MahasiswaDAO();
        
        setTitle("Aplikasi Manajemen Mahasiswa");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inisialisasi Tabel
        String[] kolom = {"NIM", "Nama", "Jurusan", "Email", "IPK"};
        modelTabel = new DefaultTableModel(kolom, 0);
        tabelMahasiswa = new JTable(modelTabel);
        JScrollPane scrollPane = new JScrollPane(tabelMahasiswa);
        add(scrollPane, BorderLayout.CENTER);

        // Panel Input
        JPanel panelInput = new JPanel(new GridLayout(6, 2));
        panelInput.add(new JLabel("NIM:"));
        txtNIM = new JTextField();
        panelInput.add(txtNIM);
        
        panelInput.add(new JLabel("Nama:"));
        txtNama = new JTextField();
        panelInput.add(txtNama);
        
        panelInput.add(new JLabel("Jurusan:"));
        txtJurusan = new JTextField();
        panelInput.add(txtJurusan);
        
        panelInput.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelInput.add(txtEmail);
        
        panelInput.add(new JLabel("IPK:"));
        txtIPK = new JTextField();
        panelInput.add(txtIPK);

        // Panel Tombol
        JPanel panelTombol = new JPanel();
        btnTambah = new JButton("Tambah");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnBersih = new JButton("Bersihkan");
        
        panelTombol.add(btnTambah);
        panelTombol.add(btnUpdate);
        panelTombol.add(btnHapus);
        panelTombol.add(btnBersih);

        panelInput.add(panelTombol);
        add(panelInput, BorderLayout.SOUTH);

        // Event Listeners
        tabelMahasiswa.getSelectionModel().addListSelectionListener(e -> {
            int barisTerpilih = tabelMahasiswa.getSelectedRow();
            if (barisTerpilih != -1) {
                txtNIM.setText(tabelMahasiswa.getValueAt(barisTerpilih, 0).toString());
                txtNama.setText(tabelMahasiswa.getValueAt(barisTerpilih, 1).toString());
                txtJurusan.setText(tabelMahasiswa.getValueAt(barisTerpilih, 2).toString());
                txtEmail.setText(tabelMahasiswa.getValueAt(barisTerpilih, 3).toString());
                txtIPK.setText(tabelMahasiswa.getValueAt(barisTerpilih, 4).toString());
            }
        });

        btnTambah.addActionListener(e -> tambahMahasiswa());
        btnUpdate.addActionListener(e -> updateMahasiswa());
        btnHapus.addActionListener(e -> hapusMahasiswa());
        btnBersih.addActionListener(e -> bersihkanForm());

        muatDataMahasiswa();
    }

    private void muatDataMahasiswa() {
        modelTabel.setRowCount(0);
        List<Mahasiswa> daftarMahasiswa = mahasiswaDAO.getAllMahasiswa();
        
        for (Mahasiswa mahasiswa : daftarMahasiswa) {
            modelTabel.addRow(new Object[]{
                mahasiswa.getNim(), 
                mahasiswa.getNama(), 
                mahasiswa.getJurusan(), 
                mahasiswa.getEmail(), 
                mahasiswa.getIpk()
            });
        }
    }

    private void tambahMahasiswa() {
        try {
            int nim = Integer.parseInt(txtNIM.getText());
            String nama = txtNama.getText();
            String jurusan = txtJurusan.getText();
            String email = txtEmail.getText();
            double ipk = Double.parseDouble(txtIPK.getText());

            Mahasiswa mahasiswaBaru = new Mahasiswa(nim, nama, jurusan, email, ipk);
            mahasiswaDAO.tambahMahasiswa(mahasiswaBaru);
            muatDataMahasiswa();
            bersihkanForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Masukkan data dengan benar!");
        }
    }

    private void updateMahasiswa() {
        try {
            int nim = Integer.parseInt(txtNIM.getText());
            String nama = txtNama.getText();
            String jurusan = txtJurusan.getText();
            String email = txtEmail.getText();
            double ipk = Double.parseDouble(txtIPK.getText());

            Mahasiswa mahasiswaUpdate = new Mahasiswa(nim, nama, jurusan, email, ipk);
            mahasiswaDAO.updateMahasiswa(mahasiswaUpdate);
            muatDataMahasiswa();
            bersihkanForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Masukkan data dengan benar!");
        }
    }

    private void hapusMahasiswa() {
        try {
            int nim = Integer.parseInt(txtNIM.getText());
            mahasiswaDAO.hapusMahasiswa(nim);
            muatDataMahasiswa();
            bersihkanForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
        }
    }

    private void bersihkanForm() {
        txtNIM.setText("");
        txtNama.setText("");
        txtJurusan.setText("");
        txtEmail.setText("");
        txtIPK.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MahasiswaGUI().setVisible(true);
        });
    }
}
