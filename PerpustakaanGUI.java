import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PerpustakaanGUI {
    private JFrame frame;
    private JPanel loginPanel, daftarPanel, menuPanel, pustakawanDashboard;
    private JTable table;
    private DefaultTableModel tableModel;
    private java.util.List<User> userList = new ArrayList<>();
    private java.util.List<Buku> bukuList = new ArrayList<>();
    private List<Peminjaman> peminjamanList = new ArrayList<>();
    private List<Pengembalian> pengembalianList = new ArrayList<>();
    private JTextField searchField;

    private User currentUser;

    // Inner class untuk data peminjaman
    private static class Peminjaman {
        String idBuku;
        String namaBuku;
        String namaPengunjung;
        String tanggalPinjam;

        Peminjaman(String idBuku, String namaBuku, String namaPengunjung, String tanggalPinjam) {
            this.idBuku = idBuku;
            this.namaBuku = namaBuku;
            this.namaPengunjung = namaPengunjung;
            this.tanggalPinjam = tanggalPinjam;
        }
    }

    // Inner class untuk data pengembalian
    private static class Pengembalian {
        String idBuku;
        String namaBuku;
        String namaPengunjung;
        double denda;
        boolean sudahBayar;

        Pengembalian(String idBuku, String namaBuku, String namaPengunjung, double denda, boolean sudahBayar) {
            this.idBuku = idBuku;
            this.namaBuku = namaBuku;
            this.namaPengunjung = namaPengunjung;
            this.denda = denda;
            this.sudahBayar = sudahBayar;
        }
    }

    private java.util.Map<String, java.util.List<BorrowedBook>> borrowedBooksMap = new HashMap<>();

    private static class BorrowedBook {
        Buku buku;
        LocalDate tanggalPinjam;
        LocalDate tanggalKembali;
        double denda;

        BorrowedBook(Buku buku, LocalDate tanggalPinjam, LocalDate tanggalKembali, double denda) {
            this.buku = buku;
            this.tanggalPinjam = tanggalPinjam;
            this.tanggalKembali = tanggalKembali;
            this.denda = denda;
        }
    }

    public PerpustakaanGUI() {
        initUser();
        initBuku();
        initSampleData();
        initFrame();
        showLoginPanel();
    }

    private void initUser() {
        userList.add(new User("001", "admin", "admin123", "pustakawan"));
        userList.add(new User("002", "budi", "budi123", "pengunjung"));
        userList.add(new User("003", "sari", "sari123", "pengunjung"));
        userList.add(new User("004", "andi", "andi123", "pengunjung"));
    }

    private void initBuku() {
        bukuList.add(new Buku("B001", "Java Dasar", "Ani", 2018, "Tersedia"));
        bukuList.add(new Buku("B002", "Pemrograman Web", "Budi", 2020, "Dipinjam"));
        bukuList.add(new Buku("B003", "Belajar PHP", "Citra", 2019, "Tersedia"));
        bukuList.add(new Buku("B004", "Database MySQL", "Deni", 2021, "Dipinjam"));
    }

    private void initSampleData() {
        // Sample data peminjaman
        peminjamanList.add(new Peminjaman("B002", "Pemrograman Web", "Budi", "2024-01-15"));
        peminjamanList.add(new Peminjaman("B004", "Database MySQL", "Sari", "2024-01-20"));

        // Sample data pengembalian
        pengembalianList.add(new Pengembalian("B001", "Java Dasar", "Andi", 5000.0, false));
        pengembalianList.add(new Pengembalian("B003", "Belajar PHP", "Budi", 0.0, true));
    }

    private void initFrame() {
        frame = new JFrame("Aplikasi Perpustakaan");
        frame.setSize(900, 700);
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
        tfId.setMaximumSize(new Dimension(300, 30));
        gbc.gridx = 1;
        formLogin.add(tfId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formLogin.add(new JLabel("Nama:"), gbc);

        JTextField tfNama = new JTextField(15);
        tfNama.setMaximumSize(new Dimension(300, 30));
        gbc.gridx = 1;
        formLogin.add(tfNama, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formLogin.add(new JLabel("Password:"), gbc);

        JPasswordField tfPassword = new JPasswordField(15);
        tfPassword.setMaximumSize(new Dimension(300, 30));
        gbc.gridx = 1;
        formLogin.add(tfPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnMasuk = new JButton("Masuk");
        JButton btnDaftar = new JButton("Daftar");
        JButton btnKembali = new JButton("Kembali");

        btnMasuk.setPreferredSize(new Dimension(100, 30));
        btnDaftar.setPreferredSize(new Dimension(100, 30));
        btnKembali.setPreferredSize(new Dimension(100, 30));
        btnMasuk.setBackground(new Color(173, 216, 230));
        btnDaftar.setBackground(new Color(144, 238, 144));
        btnKembali.setBackground(new Color(255, 204, 204));

        btnPanel.add(btnMasuk);
        btnPanel.add(btnDaftar);
        btnPanel.add(btnKembali);

        formLogin.add(btnPanel, gbc);

        // Tombol aksi
        btnMasuk.addActionListener(e -> {
            String id = tfId.getText();
            String nama = tfNama.getText();
            String password = new String(tfPassword.getPassword());
            
            for (User u : userList) {
                if (u.getId().equals(id) && u.getNama().equals(nama) && 
                    u.getPassword().equals(password) && u.getRole().equals(role)) {
                    currentUser = u;
                    if (role.equals("pustakawan")) {
                        showPustakawanDashboard();
                    } else {
                        showMainMenu();
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Login gagal. Periksa kembali data Anda.");
        });

        btnDaftar.addActionListener(e -> showDaftarPanel(role));
        btnKembali.addActionListener(e -> showLoginPanel());

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

        // GridBagLayout untuk form, label di kiri, field di kanan
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 6, 1, 0); // Jarak antar field sangat kecil
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Baris ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel idLabel = new JLabel("ID:");
        formPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        JTextField tfId = new JTextField();
        tfId.setPreferredSize(new Dimension(250, 24));
        formPanel.add(tfId, gbc);

        // Baris Nama
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel namaLabel = new JLabel("Nama:");
        formPanel.add(namaLabel, gbc);
        gbc.gridx = 1;
        JTextField tfNama = new JTextField();
        tfNama.setPreferredSize(new Dimension(300, 24));
        formPanel.add(tfNama, gbc);

        // Baris Password
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passLabel = new JLabel("Password:");
        formPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        JPasswordField tfPassword = new JPasswordField();
        tfPassword.setPreferredSize(new Dimension(300, 24));
        formPanel.add(tfPassword, gbc);

        // Tombol daftar & kembali
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnSubmit = new JButton("Daftar");
        btnSubmit.setPreferredSize(new Dimension(120, 26));
        btnSubmit.setBackground(new Color(144, 238, 144));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.setPreferredSize(new Dimension(120, 26));
        btnKembali.setBackground(new Color(255, 204, 204));

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.add(btnSubmit);
        btnPanel.add(btnKembali);
        formPanel.add(btnPanel, gbc);

        daftarPanel.add(title);
        daftarPanel.add(Box.createVerticalStrut(0));
        daftarPanel.add(formPanel);

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

        btnKembali.addActionListener(e -> showLoginPanel()); // Aksi kembali ke halaman selamat datang

        frame.setContentPane(daftarPanel);
        frame.revalidate();
    }

    private void showPustakawanDashboard() {
        pustakawanDashboard = new JPanel(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titleLabel = new JLabel("Dashboard Perpustakaan Universitas Singaperbangsa Karawang");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JLabel infoLabel = new JLabel("ID: " + currentUser.getId() + " | Nama: " + currentUser.getNama());
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(separator);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(infoLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnPengunjung = new JButton("Pengunjung");
        JButton btnBuku = new JButton("Buku");
        JButton btnPeminjaman = new JButton("Peminjaman");
        JButton btnKembalikan = new JButton("Kembalikan");

        // Set ukuran yang sama untuk semua button
        Dimension buttonSize = new Dimension(200, 80);
        btnPengunjung.setPreferredSize(buttonSize);
        btnBuku.setPreferredSize(buttonSize);
        btnPeminjaman.setPreferredSize(buttonSize);
        btnKembalikan.setPreferredSize(buttonSize);

        // Set font yang lebih besar
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        btnPengunjung.setFont(buttonFont);
        btnBuku.setFont(buttonFont);
        btnPeminjaman.setFont(buttonFont);
        btnKembalikan.setFont(buttonFont);

        // Set warna background
        btnPengunjung.setBackground(new Color(173, 216, 230));
        btnBuku.setBackground(new Color(144, 238, 144));
        btnPeminjaman.setBackground(new Color(255, 182, 193));
        btnKembalikan.setBackground(new Color(255, 218, 185));

        // Add action listeners
        btnPengunjung.addActionListener(e -> showDaftarPengunjung());
        btnBuku.addActionListener(e -> showDaftarBuku());
        btnPeminjaman.addActionListener(e -> showDaftarPeminjaman());
        btnKembalikan.addActionListener(e -> showDaftarPengembalian());

        buttonPanel.add(btnPengunjung);
        buttonPanel.add(btnBuku);
        buttonPanel.add(btnPeminjaman);
        buttonPanel.add(btnKembalikan);

        // Logout button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(255, 99, 71));
        btnLogout.addActionListener(e -> {
            currentUser = null;
            showLoginPanel();
        });
        bottomPanel.add(btnLogout);

        pustakawanDashboard.add(headerPanel, BorderLayout.NORTH);
        pustakawanDashboard.add(buttonPanel, BorderLayout.CENTER);
        pustakawanDashboard.add(bottomPanel, BorderLayout.SOUTH);

        frame.setContentPane(pustakawanDashboard);
        frame.revalidate();
    }

    private void showDaftarPengunjung() {
        JPanel pengunjungPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Daftar Pengunjung", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        String[] columnNames = {"ID Pengunjung", "Nama Pengunjung"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        // Tambahkan data pengunjung (filter user dengan role pengunjung)
        for (User user : userList) {
            if ("pengunjung".equals(user.getRole())) {
                model.addRow(new Object[]{user.getId(), user.getNama()});
            }
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> showPustakawanDashboard());

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(btnKembali);

        pengunjungPanel.add(titleLabel, BorderLayout.NORTH);
        pengunjungPanel.add(scrollPane, BorderLayout.CENTER);
        pengunjungPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.setContentPane(pengunjungPanel);
        frame.revalidate();
    }

    private void showDaftarBuku() {
        JPanel bukuPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Daftar Buku", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        String[] columnNames = {"ID", "Judul", "Penulis", "Tahun Terbit", "Status", "Action"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Hanya kolom action yang bisa diedit
            }
        };

        refreshBukuTable(model);

        JTable table = new JTable(model);
        
        // Tambahkan button renderer dan editor untuk kolom action
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), model, table));

        JScrollPane scrollPane = new JScrollPane(table);

        // Panel untuk button tambah dan kembali
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JButton btnTambah = new JButton("+ Tambah Buku");
        btnTambah.setBackground(new Color(144, 238, 144));
        btnTambah.addActionListener(e -> showTambahBukuDialog(model));
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(btnTambah);
        topPanel.add(rightPanel, BorderLayout.EAST);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> showPustakawanDashboard());

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(btnKembali);

        bukuPanel.add(topPanel, BorderLayout.NORTH);
        bukuPanel.add(scrollPane, BorderLayout.CENTER);
        bukuPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.setContentPane(bukuPanel);
        frame.revalidate();
    }

    private void refreshBukuTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Buku buku : bukuList) {
            model.addRow(new Object[]{
                buku.getId(), 
                buku.getJudul(), 
                buku.getPenulis(), 
                buku.getTahunTerbit(), 
                buku.getStatus(),
                "Edit | Hapus"
            });
        }
    }

    private void showTambahBukuDialog(DefaultTableModel model) {
        JDialog dialog = new JDialog(frame, "+ Tambah Buku", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(frame);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField tfId = new JTextField(15);
        JTextField tfJudul = new JTextField(15);
        JTextField tfPenulis = new JTextField(15);
        JTextField tfTahun = new JTextField(15);
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Tersedia", "Dipinjam"});

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Buku:"), gbc);
        gbc.gridx = 1;
        panel.add(tfId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Judul:"), gbc);
        gbc.gridx = 1;
        panel.add(tfJudul, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Penulis:"), gbc);
        gbc.gridx = 1;
        panel.add(tfPenulis, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Tahun Terbit:"), gbc);
        gbc.gridx = 1;
        panel.add(tfTahun, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        panel.add(cbStatus, gbc);

        JPanel buttonPanel = new JPanel();
        JButton btnSimpan = new JButton("Simpan");
        JButton btnBatal = new JButton("Batal");

        btnSimpan.addActionListener(e -> {
            try {
                String id = tfId.getText().trim();
                String judul = tfJudul.getText().trim();
                String penulis = tfPenulis.getText().trim();
                int tahun = Integer.parseInt(tfTahun.getText().trim());
                String status = (String) cbStatus.getSelectedItem();

                if (id.isEmpty() || judul.isEmpty() || penulis.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Semua field harus diisi!");
                    return;
                }

                // Cek ID duplikat
                for (Buku b : bukuList) {
                    if (b.getId().equals(id)) {
                        JOptionPane.showMessageDialog(dialog, "ID Buku sudah ada!");
                        return;
                    }
                }

                bukuList.add(new Buku(id, judul, penulis, tahun, status));
                refreshBukuTable(model);
                dialog.dispose();
                JOptionPane.showMessageDialog(frame, "Buku berhasil ditambahkan!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Tahun harus berupa angka!");
            }
        });

        btnBatal.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnBatal);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showDaftarPeminjaman() {
        JPanel peminjamanPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Daftar Peminjaman", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        String[] columnNames = {"ID Buku", "Nama Buku", "Nama Pengunjung", "Tanggal Dipinjam"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        for (Peminjaman p : peminjamanList) {
            model.addRow(new Object[]{p.idBuku, p.namaBuku, p.namaPengunjung, p.tanggalPinjam});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> showPustakawanDashboard());

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(btnKembali);

        peminjamanPanel.add(titleLabel, BorderLayout.NORTH);
        peminjamanPanel.add(scrollPane, BorderLayout.CENTER);
        peminjamanPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.setContentPane(peminjamanPanel);
        frame.revalidate();
    }

    private void showDaftarPengembalian() {
        JPanel pengembalianPanel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Daftar Pengembalian", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        String[] columnNames = {"ID Buku", "Nama Buku", "Nama Pengunjung", "Jumlah Denda", "Status Pembayaran"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        for (Pengembalian p : pengembalianList) {
            String denda = p.denda > 0 ? "Rp " + String.format("%.0f", p.denda) : "Tidak ada denda";
            String status = p.sudahBayar ? "Sudah Bayar" : "Belum Bayar";
            model.addRow(new Object[]{p.idBuku, p.namaBuku, p.namaPengunjung, denda, status});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> showPustakawanDashboard());

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(btnKembali);

        pengembalianPanel.add(titleLabel, BorderLayout.NORTH);
        pengembalianPanel.add(scrollPane, BorderLayout.CENTER);
        pengembalianPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.setContentPane(pengembalianPanel);
        frame.revalidate();
    }

    // Button Renderer untuk kolom action
    class ButtonRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private JButton editButton = new JButton("Edit");
        private JButton deleteButton = new JButton("Hapus");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
            editButton.setPreferredSize(new Dimension(60, 25));
            deleteButton.setPreferredSize(new Dimension(60, 25));
            editButton.setBackground(new Color(173, 216, 230));
            deleteButton.setBackground(new Color(255, 99, 71));
            add(editButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Button Editor untuk kolom action
    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editButton = new JButton("Edit");
        private JButton deleteButton = new JButton("Hapus");
        private DefaultTableModel model;
        private JTable table;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox, DefaultTableModel model, JTable table) {
            super(checkBox);
            this.model = model;
            this.table = table;
            
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
            editButton.setPreferredSize(new Dimension(60, 25));
            deleteButton.setPreferredSize(new Dimension(60, 25));
            editButton.setBackground(new Color(173, 216, 230));
            deleteButton.setBackground(new Color(255, 99, 71));

            editButton.addActionListener(e -> editBuku());
            deleteButton.addActionListener(e -> deleteBuku());

            panel.add(editButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }

        private void editBuku() {
            Buku buku = bukuList.get(currentRow);
            showEditBukuDialog(buku);
            fireEditingStopped();
        }

        private void deleteBuku() {
            int confirm = JOptionPane.showConfirmDialog(frame, 
                "Apakah Anda yakin ingin menghapus buku ini?", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                bukuList.remove(currentRow);
                refreshBukuTable(model);
                JOptionPane.showMessageDialog(frame, "Buku berhasil dihapus!");
            }
            fireEditingStopped();
        }

        @Override
        public Object getCellEditorValue() {
            return "Edit | Hapus";
        }
    }

    private void showEditBukuDialog(Buku buku) {
        JDialog dialog = new JDialog(frame, "Edit Buku", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(frame);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        JTextField tfId = new JTextField(15);
        JTextField tfJudul = new JTextField(15);
        JTextField tfPenulis = new JTextField(15);
        JTextField tfTahun = new JTextField(15);
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Tersedia", "Dipinjam"});
        // Set existing values
        tfId.setText(buku.getId());
        tfJudul.setText(buku.getJudul());
        tfPenulis.setText(buku.getPenulis());
        tfTahun.setText(String.valueOf(buku.getTahunTerbit()));
        cbStatus.setSelectedItem(buku.getStatus());

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Buku:"), gbc);
        gbc.gridx = 1;
        panel.add(tfId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Judul:"), gbc);
        gbc.gridx = 1;
        panel.add(tfJudul, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Penulis:"), gbc);
        gbc.gridx = 1;
        panel.add(tfPenulis, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Tahun Terbit:"), gbc);
        gbc.gridx = 1;
        panel.add(tfTahun, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        panel.add(cbStatus, gbc);

        JPanel buttonPanel = new JPanel();
        JButton btnSimpan = new JButton("Simpan");
        JButton btnBatal = new JButton("Batal");

        btnSimpan.addActionListener(e -> {
            try {
                String id = tfId.getText().trim();
                String judul = tfJudul.getText().trim();
                String penulis = tfPenulis.getText().trim();
                int tahun = Integer.parseInt(tfTahun.getText().trim());
                String status = (String) cbStatus.getSelectedItem();
            
            if (id.isEmpty() || judul.isEmpty() || penulis.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Semua field harus diisi!");
                    return;
            }

            // Update buku
                buku.setId(id);
                buku.setJudul(judul);
                buku.setPenulis(penulis);
                buku.setTahunTerbit(tahun);
                buku.setStatus(status);

        refreshBukuTable((DefaultTableModel) table.getModel());
                dialog.dispose();
                JOptionPane.showMessageDialog(frame, "Buku berhasil diperbarui!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Tahun harus berupa angka!");
            }
        });  

        btnBatal.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnBatal);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showMainMenu() {
        menuPanel = new JPanel(new BorderLayout());

        // Panel untuk search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Cari Buku:");
        searchField = new JTextField(20);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        String[] columnNames = {"ID", "Judul", "Penulis", "Tahun", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add mouse listener to table rows
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    Buku selectedBuku = bukuList.get(row);
                    showBookDetailPanel(selectedBuku);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());

        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton userBtn = new JButton("User");
        leftButtonPanel.add(userBtn);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // Remove Pinjam Buku and Kembalikan Buku buttons as requested
        // JButton pinjamBtn = new JButton("Pinjam Buku");
        // JButton kembaliBtn = new JButton("Kembalikan Buku");
        JButton logoutBtn = new JButton("Logout");

        // rightButtonPanel.add(pinjamBtn);
        // rightButtonPanel.add(kembaliBtn);
        rightButtonPanel.add(logoutBtn);

        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        // pinjamBtn.addActionListener(e -> pinjamBuku());
        // kembaliBtn.addActionListener(e -> kembalikanBuku());
        logoutBtn.addActionListener(e -> {
            currentUser = null; // clear current user on logout
            showLoginPanel();
        });

        userBtn.addActionListener(e -> showUserInfo());

        menuPanel.add(searchPanel, BorderLayout.NORTH);
        menuPanel.add(scrollPane, BorderLayout.CENTER);
        menuPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Tambahkan listener untuk searchField
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterTable(searchField.getText());
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterTable(searchField.getText());
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterTable(searchField.getText());
            }
        });

        refreshTable(bukuList);
        frame.setContentPane(menuPanel);
        frame.revalidate();
    }

    private void showBookDetailPanel(Buku buku) {
    JPanel detailPanel = new JPanel(new BorderLayout());

    JLabel titleLabel = new JLabel("Detail Buku", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
    titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
    detailPanel.add(titleLabel, BorderLayout.NORTH);

    // Panel tengah: cover dan detail (sejajar di tengah)
    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(0, 30, 0, 30); // jarak kiri-kanan antar panel
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;

    // Cover Panel
    JPanel coverPanel = new JPanel();
    coverPanel.setOpaque(false);
    JLabel coverLabel = new JLabel();
    coverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    String coverPath;
    if (buku.getJudul().equalsIgnoreCase("Java Dasar")) {
        coverPath = "cover/java.jpg";
        if (!new java.io.File(coverPath).exists()) coverPath = "cover/java.jpeg";
    } else if (buku.getJudul().equalsIgnoreCase("Pemrograman Web")) {
        coverPath = "cover/pbw.jpg";
        if (!new java.io.File(coverPath).exists()) coverPath = "cover/pbw.jpeg";
    } else if (buku.getJudul().equalsIgnoreCase("Belajar PHP")) {
        coverPath = "cover/php.jpg";
        if (!new java.io.File(coverPath).exists()) coverPath = "cover/php.jpeg";
    } else if (buku.getJudul().equalsIgnoreCase("Database MySQL")) {
        coverPath = "cover/sql.jpg";
        if (!new java.io.File(coverPath).exists()) coverPath = "cover/sql.jpeg";
    } else {
        coverPath = "cover/" + buku.getJudul() + ".jpg";
        if (!new java.io.File(coverPath).exists()) coverPath = "cover/" + buku.getJudul() + ".jpeg";
    }
    java.io.File coverFile = new java.io.File(coverPath);
    ImageIcon coverIcon;
    if (coverFile.exists()) {
        coverIcon = new ImageIcon(coverPath);
    } else {
        BufferedImage img = new BufferedImage(200, 270, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 200, 270);
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("No Image", 45, 135);
        g.dispose();
        coverIcon = new ImageIcon(img);
    }
    Image img = coverIcon.getImage().getScaledInstance(200, 270, Image.SCALE_SMOOTH);
    coverLabel.setIcon(new ImageIcon(img));
    coverPanel.add(coverLabel);

    // Panel detail (keterangan buku)
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setOpaque(false);
    infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

    Font infoFont = new Font("Arial", Font.PLAIN, 22);

    JLabel idLabel = new JLabel("ID                   : " + buku.getId());
    idLabel.setFont(infoFont);
    idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel judulLabel = new JLabel("Judul              : " + buku.getJudul());
    judulLabel.setFont(infoFont);
    judulLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel penulisLabel = new JLabel("Penulis           : " + buku.getPenulis());
    penulisLabel.setFont(infoFont);
    penulisLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel tahunLabel = new JLabel("Tahun Terbit  : " + buku.getTahunTerbit());
    tahunLabel.setFont(infoFont);
    tahunLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel statusLabel = new JLabel("Status            : " + buku.getStatus());
    statusLabel.setFont(infoFont);
    statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    infoPanel.add(idLabel);
    infoPanel.add(Box.createVerticalStrut(8));
    infoPanel.add(judulLabel);
    infoPanel.add(Box.createVerticalStrut(8));
    infoPanel.add(penulisLabel);
    infoPanel.add(Box.createVerticalStrut(8));
    infoPanel.add(tahunLabel);
    infoPanel.add(Box.createVerticalStrut(8));
    infoPanel.add(statusLabel);

    // Tambahkan ke centerPanel dengan GridBagLayout
    gbc.gridx = 0;
    centerPanel.add(coverPanel, gbc);
    gbc.gridx = 1;
    centerPanel.add(infoPanel, gbc);

    detailPanel.add(centerPanel, BorderLayout.CENTER);

    // Panel tombol di bawah
    JPanel buttonPanel = new JPanel();
    JButton actionBtn = new JButton(buku.getStatus().equals("Tersedia") ? "Pinjam" : "Kembalikan");
    JButton batalBtn = new JButton("Batal");
    actionBtn.setPreferredSize(new Dimension(120, 38));
    batalBtn.setPreferredSize(new Dimension(120, 38));
    actionBtn.setFont(new Font("Arial", Font.BOLD, 16));
    batalBtn.setFont(new Font("Arial", Font.BOLD, 16));
    buttonPanel.add(actionBtn);
    buttonPanel.add(batalBtn);

    detailPanel.add(centerPanel, BorderLayout.CENTER);
    detailPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Action listeners
    actionBtn.addActionListener(e -> {
        if (buku.getStatus().equals("Tersedia")) {
            buku.setStatus("Dipinjam (kembali: " + LocalDate.now().plusDays(7) + ")");
            if (currentUser != null) {
                java.util.List<BorrowedBook> borrowedList = borrowedBooksMap.getOrDefault(currentUser.getId(), new ArrayList<>());
                boolean alreadyBorrowed = false;
                for (BorrowedBook bb : borrowedList) {
                    if (bb.buku.getId().equals(buku.getId())) {
                        alreadyBorrowed = true;
                        break;
                    }
                }
                if (!alreadyBorrowed) {
                    borrowedList.add(new BorrowedBook(buku, LocalDate.now(), LocalDate.now().plusDays(7), 0));
                    borrowedBooksMap.put(currentUser.getId(), borrowedList);
                    refreshTable(bukuList);
                    showMainMenu();
                } else {
                    JOptionPane.showMessageDialog(frame, "Anda sudah meminjam buku ini.");
                }
            }
        } else {
            buku.setStatus("Tersedia");
            if (currentUser != null) {
                java.util.List<BorrowedBook> borrowedList = borrowedBooksMap.getOrDefault(currentUser.getId(), new ArrayList<>());
                BorrowedBook toRemove = null;
                for (BorrowedBook bb : borrowedList) {
                    if (bb.buku.getId().equals(buku.getId())) {
                        LocalDate now = LocalDate.now();
                        if (now.isAfter(bb.tanggalKembali)) {
                            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(bb.tanggalKembali, now);
                            bb.denda = daysLate * 5000;
                        } else {
                            bb.denda = 0;
                        }
                        toRemove = bb;
                        break;
                    }
                }
                if (toRemove != null) borrowedList.remove(toRemove);
                borrowedBooksMap.put(currentUser.getId(), borrowedList);
            }
            refreshTable(bukuList);
            showMainMenu();
        }
    });
    batalBtn.addActionListener(e -> showMainMenu());

    frame.setContentPane(detailPanel);
    frame.revalidate();
}

    private void refreshTable(java.util.List<Buku> list) {
        tableModel.setRowCount(0);
        for (Buku b : list) {
            tableModel.addRow(new Object[]{b.getId(), b.getJudul(), b.getPenulis(), b.getTahunTerbit(), b.getStatus()});
        }
    }

    private void kembalikanBuku() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String status = (String) tableModel.getValueAt(selectedRow, 4);
            if (!status.equals("Tersedia")) {
                Buku buku = bukuList.get(selectedRow);
                buku.setStatus("Tersedia");
                refreshTable(bukuList);

                // Update borrowedBooksMap for currentUser
                if (currentUser != null) {
                    java.util.List<BorrowedBook> borrowedList = borrowedBooksMap.getOrDefault(currentUser.getId(), new ArrayList<>());

                    BorrowedBook toRemove = null;
                    for (BorrowedBook bb : borrowedList) {
                        if (bb.buku.getId().equals(buku.getId())) {
                            // Calculate denda if returned late
                            LocalDate now = LocalDate.now();
                            if (now.isAfter(bb.tanggalKembali)) {
                                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(bb.tanggalKembali, now);
                                bb.denda = daysLate * 5000; // example fine 5000 per day
                            } else {
                                bb.denda = 0;
                            }
                            toRemove = bb;
                            break;
                        }
                    }
                    if (toRemove != null) {
                        borrowedList.remove(toRemove);
                    }
                    borrowedBooksMap.put(currentUser.getId(), borrowedList);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Buku belum dipinjam.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Pilih buku terlebih dahulu.");
        }
    }

    private void showUserInfo() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(frame, "Tidak ada user yang login.");
            return;
        }

        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Nama: " + currentUser.getNama());
        JLabel idLabel = new JLabel("ID: " + currentUser.getId());

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(idLabel);
        userInfoPanel.add(Box.createVerticalStrut(10));

        String[] columnNames = {"Judul", "Tanggal Pinjam", "Tanggal Pengembalian", "Denda"};
        DefaultTableModel borrowedTableModel = new DefaultTableModel(columnNames, 0);
        JTable borrowedTable = new JTable(borrowedTableModel);
        JScrollPane scrollPane = new JScrollPane(borrowedTable);
        scrollPane.setPreferredSize(new Dimension(500, 200));

        java.util.List<BorrowedBook> borrowedList = borrowedBooksMap.getOrDefault(currentUser.getId(), new ArrayList<>());
        for (BorrowedBook bb : borrowedList) {
            borrowedTableModel.addRow(new Object[]{
                bb.buku.getJudul(),
                bb.tanggalPinjam,
                bb.tanggalKembali,
                bb.denda > 0 ? "Rp " + bb.denda : "-"
            });
        }

        userInfoPanel.add(scrollPane);

        JOptionPane.showMessageDialog(frame, userInfoPanel, "Informasi Akun", JOptionPane.INFORMATION_MESSAGE);
    }

    private void filterTable(String query) {
        if (query == null || query.trim().isEmpty()) {
            refreshTable(bukuList);
            return;
        }
        String lowerQuery = query.toLowerCase();
        java.util.List<Buku> filtered = new ArrayList<>();
        for (Buku b : bukuList) {
            if (b.getJudul().toLowerCase().contains(lowerQuery) || b.getPenulis().toLowerCase().contains(lowerQuery)) {
                filtered.add(b);
            }
        }
        refreshTable(filtered);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PerpustakaanGUI::new);
    }
}
