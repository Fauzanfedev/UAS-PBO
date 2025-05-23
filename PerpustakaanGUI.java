import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PerpustakaanGUI {
    private JFrame frame;
    private JPanel mainPanel, loginPanel, daftarPanel, menuPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private java.util.List<User> userList = new ArrayList<>();
    private java.util.List<Buku> bukuList = new ArrayList<>();

    public PerpustakaanGUI() {
        initUser();
        initBuku();
        initFrame();
        showLoginPanel();
    }

    private void initUser() {
        userList.add(new User("001", "admin", "admin123", "pustakawan"));
        userList.add(new User("002", "budi", "budi123", "pengunjung"));
    }

    private void initBuku() {
        bukuList.add(new Buku("B001", "Java Dasar", "Ani", 2018, "Tersedia"));
        bukuList.add(new Buku("B002", "Pemrograman Web", "Budi", 2020, "Tersedia"));
    }

    private void initFrame() {
        frame = new JFrame("Aplikasi Perpustakaan");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    private void showLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel welcome1 = new JLabel("Selamat datang di Perpustakaan", SwingConstants.CENTER);
        welcome1.setFont(new Font("Arial", Font.BOLD, 24));
        welcome1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcome2 = new JLabel("Universitas Singaperbangsa Karawang", SwingConstants.CENTER);
        welcome2.setFont(new Font("Arial", Font.BOLD, 20));
        welcome2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnPengunjung = new JButton("Masuk sebagai Pengunjung");
        btnPengunjung.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPengunjung.setMaximumSize(new Dimension(250, 35));
        btnPengunjung.setBackground(new Color(204, 229, 255));

        JButton btnPustakawan = new JButton("Masuk sebagai Pustakawan");
        btnPustakawan.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPustakawan.setMaximumSize(new Dimension(250, 35));
        btnPustakawan.setBackground(new Color(204, 255, 204));

        JLabel creditLabel = new JLabel("by Kelompok 2 - 4A", SwingConstants.CENTER);
        creditLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        creditLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        creditLabel.setForeground(Color.GRAY);

        btnPengunjung.addActionListener(e -> showFormLogin("pengunjung"));
        btnPustakawan.addActionListener(e -> showFormLogin("pustakawan"));

        loginPanel.add(Box.createVerticalGlue());
        loginPanel.add(welcome1);
        loginPanel.add(welcome2);
        loginPanel.add(Box.createVerticalStrut(40));
        loginPanel.add(btnPengunjung);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(btnPustakawan);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(creditLabel);
        loginPanel.add(Box.createVerticalGlue());

        frame.setContentPane(loginPanel);
        frame.setVisible(true);
    }

    private void showFormLogin(String role) {
    JPanel formLogin = new JPanel(new GridBagLayout());
    formLogin.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel title = new JLabel("Lengkapi Data Akun Anda");
    title.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridwidth = 2;
    gbc.gridx = 0;
    gbc.gridy = 0;
    formLogin.add(title, gbc);

    gbc.gridwidth = 1;
    gbc.gridy++;
    formLogin.add(new JLabel("ID:"), gbc);

    JTextField tfId = new JTextField(15);
    gbc.gridx = 1;
    formLogin.add(tfId, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    formLogin.add(new JLabel("Nama:"), gbc);

    JTextField tfNama = new JTextField(15);
    gbc.gridx = 1;
    formLogin.add(tfNama, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    formLogin.add(new JLabel("Password:"), gbc);

    JPasswordField tfPassword = new JPasswordField(15);
    gbc.gridx = 1;
    formLogin.add(tfPassword, gbc);

    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;

    JPanel btnPanel = new JPanel(new FlowLayout());
    JButton btnMasuk = new JButton("Masuk");
    JButton btnDaftar = new JButton("Daftar");

    btnMasuk.setPreferredSize(new Dimension(100, 30));
    btnDaftar.setPreferredSize(new Dimension(100, 30));
    btnMasuk.setBackground(new Color(173, 216, 230));
    btnDaftar.setBackground(new Color(144, 238, 144));

    btnPanel.add(btnMasuk);
    btnPanel.add(btnDaftar);

    formLogin.add(btnPanel, gbc);

    // Tombol aksi
    btnMasuk.addActionListener(e -> {
        String id = tfId.getText();
        String nama = tfNama.getText();
        String password = new String(tfPassword.getPassword());
        for (User u : userList) {
            if (u.getId().equals(id) && u.getNama().equals(nama) && u.getPassword().equals(password) && u.getRole().equals(role)) {
                showMainMenu();
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Login gagal.");
    });

    btnDaftar.addActionListener(e -> showDaftarPanel(role));

    frame.setContentPane(formLogin);
    frame.revalidate();
}


    private void showDaftarPanel(String role) {
        daftarPanel = new JPanel();
        daftarPanel.setLayout(new BoxLayout(daftarPanel, BoxLayout.Y_AXIS));
        daftarPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        JLabel title = new JLabel("Form Pendaftaran", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField tfId = new JTextField();
        JTextField tfNama = new JTextField();
        JPasswordField tfPassword = new JPasswordField();
        JButton btnSubmit = new JButton("Daftar");

        btnSubmit.setMaximumSize(new Dimension(150, 30));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSubmit.setBackground(new Color(144, 238, 144));

        daftarPanel.add(title);
        daftarPanel.add(Box.createVerticalStrut(15));
        daftarPanel.add(new JLabel("ID:"));
        daftarPanel.add(tfId);
        daftarPanel.add(Box.createVerticalStrut(10));
        daftarPanel.add(new JLabel("Nama:"));
        daftarPanel.add(tfNama);
        daftarPanel.add(Box.createVerticalStrut(10));
        daftarPanel.add(new JLabel("Password:"));
        daftarPanel.add(tfPassword);
        daftarPanel.add(Box.createVerticalStrut(20));
        daftarPanel.add(btnSubmit);

        btnSubmit.addActionListener(e -> {
            String id = tfId.getText().trim();
            String nama = tfNama.getText().trim();
            String password = new String(tfPassword.getPassword());

            if (id.isEmpty() || nama.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Semua field harus diisi!");
                return;
            }

            for (User u : userList) {
                if (u.getId().equals(id)) {
                    JOptionPane.showMessageDialog(frame, "ID sudah digunakan!");
                    return;
                }
            }

            userList.add(new User(id, nama, password, role));
            JOptionPane.showMessageDialog(frame, "Pendaftaran berhasil, silakan login.");
            showFormLogin(role);
        });

        frame.setContentPane(daftarPanel);
        frame.revalidate();
    }

    private void showMainMenu() {
        menuPanel = new JPanel(new BorderLayout());

        String[] columnNames = {"ID", "Judul", "Penulis", "Tahun", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();
        JButton pinjamBtn = new JButton("Pinjam Buku");
        JButton kembaliBtn = new JButton("Kembalikan Buku");
        JButton logoutBtn = new JButton("Logout");

        buttonPanel.add(pinjamBtn);
        buttonPanel.add(kembaliBtn);
        buttonPanel.add(logoutBtn);

        pinjamBtn.addActionListener(e -> pinjamBuku());
        kembaliBtn.addActionListener(e -> kembalikanBuku());
        logoutBtn.addActionListener(e -> showLoginPanel());

        menuPanel.add(scrollPane, BorderLayout.CENTER);
        menuPanel.add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
        frame.setContentPane(menuPanel);
        frame.revalidate();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Buku b : bukuList) {
            tableModel.addRow(new Object[]{b.getId(), b.getJudul(), b.getPenulis(), b.getTahunTerbit(), b.getStatus()});
        }
    }

    private void pinjamBuku() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String status = (String) tableModel.getValueAt(selectedRow, 4);
            if (status.equals("Tersedia")) {
                bukuList.get(selectedRow).setStatus("Dipinjam (kembali: " + LocalDate.now().plusDays(7) + ")");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Buku sudah dipinjam.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Pilih buku terlebih dahulu.");
        }
    }

    private void kembalikanBuku() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String status = (String) tableModel.getValueAt(selectedRow, 4);
            if (!status.equals("Tersedia")) {
                bukuList.get(selectedRow).setStatus("Tersedia");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Buku belum dipinjam.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Pilih buku terlebih dahulu.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PerpustakaanGUI::new);
    }
}
