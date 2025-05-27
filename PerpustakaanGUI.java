import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PerpustakaanGUI {
    private JFrame frame;
    private JPanel loginPanel, daftarPanel, menuPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private java.util.List<User> userList = new ArrayList<>();
    private java.util.List<Buku> bukuList = new ArrayList<>();
    private JTextField searchField;

    private User currentUser; 


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
        bukuList.add(new Buku("B003", "Belajar PHP ", "Budi", 2020, "Tersedia"));

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
    JButton btnKembali = new JButton("Kembali"); // Tambah tombol kembali

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
            if (u.getId().equals(id) && u.getNama().equals(nama) && u.getPassword().equals(password) && u.getRole().equals(role)) {
                currentUser = u;
                showMainMenu();
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Login gagal.");
    });

    btnDaftar.addActionListener(e -> showDaftarPanel(role));
    btnKembali.addActionListener(e -> showLoginPanel()); // Aksi kembali ke halaman selamat datang

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

    // Removed unused refreshTable() method without parameters

    private void showBookDetailPanel(Buku buku) {
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel titleLabel = new JLabel("Detail Buku");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel idLabel = new JLabel("ID: " + buku.getId());
        idLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel judulLabel = new JLabel("Judul: " + buku.getJudul());
        judulLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        judulLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel penulisLabel = new JLabel("Penulis: " + buku.getPenulis());
        penulisLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        penulisLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tahunLabel = new JLabel("Tahun Terbit: " + buku.getTahunTerbit());
        tahunLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        tahunLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statusLabel = new JLabel("Status: " + buku.getStatus());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        JButton actionBtn;
        JButton batalBtn = new JButton("Batal");

        actionBtn = new JButton(buku.getStatus().equals("Tersedia") ? "Pinjam" : "Kembalikan");

        actionBtn.setPreferredSize(new Dimension(100, 30));
        batalBtn.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(actionBtn);
        buttonPanel.add(batalBtn);

        actionBtn.addActionListener(e -> {
            if (buku.getStatus().equals("Tersedia")) {
                // Borrow book: update status and borrowedBooksMap
                buku.setStatus("Dipinjam (kembali: " + LocalDate.now().plusDays(7) + ")");
                if (currentUser != null) {
                    java.util.List<BorrowedBook> borrowedList = borrowedBooksMap.getOrDefault(currentUser.getId(), new ArrayList<>());
                    borrowedList.add(new BorrowedBook(buku, LocalDate.now(), LocalDate.now().plusDays(7), 0));
                    borrowedBooksMap.put(currentUser.getId(), borrowedList);
                }
                refreshTable(bukuList);
                showMainMenu();
            } else {
                // Return book: update status and borrowedBooksMap, calculate denda
                buku.setStatus("Tersedia");
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
                refreshTable(bukuList);
                showMainMenu();
            }
        });

        batalBtn.addActionListener(e -> showMainMenu());

        detailPanel.add(titleLabel);
        detailPanel.add(Box.createVerticalStrut(20));
        detailPanel.add(idLabel);
        detailPanel.add(judulLabel);
        detailPanel.add(penulisLabel);
        detailPanel.add(tahunLabel);
        detailPanel.add(statusLabel);
        detailPanel.add(Box.createVerticalStrut(30));
        detailPanel.add(buttonPanel);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PerpustakaanGUI::new);
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
}
