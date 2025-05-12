package simsditp;

import Database.SQLConnection;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Cetak_absen extends javax.swing.JPanel {

    public Cetak_absen() {
        initComponents();
        updateCombo();
        updateCombo1();
        tampilkanDataAbsen();
        jtanggal.setDate(new java.util.Date());
        txtlabel2.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcari1.setBackground(new java.awt.Color(0, 0, 0, 1));
        jbulan.setVisible(false);
        jkelas2.setVisible(false);
        txtkelas1.setVisible(false);
        jbulan1.setVisible(false);
        jkelas2.setVisible(false);
        cetak3.setVisible(false);
        jtahun.setVisible(false);
    }

    private void updateCombo() {
        // Hapus semua item dalam combo box sebelum menambahkan item baru
        jkelas1.removeAllItems();

        // Tambahkan placeholder jika diperlukan
        jkelas1.addItem("-- Pilih Kelas --");

        try {
            String query = "SELECT * from kelas";
            ResultSet rs = SQLConnection.doQuery(query);
            while (rs.next()) {
                jkelas1.addItem(rs.getString("kelas")); // Tambahkan kembali item dari hasil query
            }
        } catch (SQLException e) {
        }

        // Setel placeholder sebagai item yang dipilih secara default
        jkelas1.setSelectedItem("-- Pilih Kelas --");

        // Tambahkan ActionListener untuk menghapus placeholder saat item lain dipilih
        jkelas1.addActionListener((ActionEvent e) -> {
            if (jkelas1.getSelectedItem() != null && jkelas1.getSelectedItem().equals("kelas")) {
                jkelas1.removeItem("-- Pilih Kelas --");
                jkelas1.revalidate();
                jkelas1.repaint();
            }
        });
    }

    private void updateCombo1() {
        jkelas2.removeAllItems();
        jkelas2.addItem("-- Pilih Kelas --");

        try {
            int selectedMonth = jbulan1.getMonth() + 1;
            String formattedMonth = (selectedMonth < 10) ? "0" + selectedMonth : String.valueOf(selectedMonth);

            String query = "SELECT * from kelas";
            ResultSet rs = SQLConnection.doQuery(query);

            boolean dataFound = false;
            while (rs.next()) {
                jkelas2.addItem(rs.getString("kelas"));
                dataFound = true;
            }

            // Jika tidak ada data ditemukan, pastikan placeholder tetap ada
            if (!dataFound) {
                jkelas2.setSelectedItem("-- Pilih Kelas --");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data kelas: " + e.getMessage());
        }

        // Setel placeholder sebagai item yang dipilih secara default
        jkelas2.setSelectedItem("-- Pilih Kelas --");

        // Tambahkan ActionListener untuk menghapus placeholder saat item lain dipilih
        jkelas2.addActionListener((ActionEvent e) -> {
            if (jkelas2.getSelectedItem() != null && jkelas2.getSelectedItem().equals("-- Pilih Kelas --")) {
                jkelas2.removeItem("-- Pilih Kelas --");
                jkelas2.revalidate();
                jkelas2.repaint();
            }
        });

        jkelas1.addActionListener(e -> {
            // Ambil kelas yang dipilih
            String selectedKelas = (String) jkelas1.getSelectedItem();

            // Ambil tanggal yang dipilih
            Date selectedDate = jtanggal.getDate();  // jtanggal adalah JDateChooser

            // Pastikan kelas dan tanggal dipilih
            if (selectedKelas != null && !selectedKelas.equals("-- Pilih Kelas --") && selectedDate != null) {
                // Format tanggal jika perlu
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(selectedDate);

                // Panggil fungsi tampilkanDataAbsen dengan kelas dan tanggal yang dipilih
                tampilkanDataAbsen();
            } else {
                // Kosongkan tabel atau beri pesan jika salah satu belum dipilih
                tabelsiswa.setModel(new DefaultTableModel(
                        null,
                        new String[]{"NIPD", "NISN", "Nama Siswa", "Jenis Kelamin", "Kelas", "Presensi", "Keterangan", "Tanggal"}
                ));
            }
        });

        // Tambahkan ActionListener untuk memuat data tabel berdasarkan kelas dan bulan
        jkelas2.addActionListener(e -> {
            String selectedKelas = (String) jkelas2.getSelectedItem();
            int selectedBulan = jbulan1.getMonth() + 1; // Bulan berbasis 0, tambahkan 1

            if (selectedKelas != null && !selectedKelas.equals("-- Pilih Kelas --")) {
                tampilkanDataAbsen1();
            } else {
                // Kosongkan tabel jika placeholder dipilih
                tabelsiswa.setModel(new DefaultTableModel(
                        null,
                        new String[]{"NIPD", "NISN", "Nama Siswa", "Jenis Kelamin", "Kelas", "Presensi", "Keterangan", "Tanggal"}
                ));
            }
        });

        // PropertyChangeListener untuk jtanggal (JDateChooser)
        jtanggal.addPropertyChangeListener(e -> {
            // Cek apakah perubahan yang terjadi adalah perubahan tanggal
            if ("date".equals(e.getPropertyName())) {
                // Ambil kelas yang dipilih
                String selectedKelas = (String) jkelas1.getSelectedItem();

                // Ambil tanggal yang dipilih
                Date selectedDate = jtanggal.getDate();

                // Pastikan kelas dan tanggal dipilih
                if (selectedKelas != null && !selectedKelas.equals("-- Pilih Kelas --") && selectedDate != null) {
                    // Format tanggal jika perlu
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = sdf.format(selectedDate);

                    // Panggil fungsi tampilkanDataAbsen dengan kelas dan tanggal yang dipilih
                    tampilkanDataAbsen();
                } else {
                    // Kosongkan tabel atau beri pesan jika salah satu belum dipilih
                    tabelsiswa.setModel(new DefaultTableModel(
                            null,
                            new String[]{"NIPD", "NISN", "Nama Siswa", "Jenis Kelamin", "Kelas", "Presensi", "Keterangan", "Tanggal"}
                    ));
                }
            }
        });

        jbulan1.addPropertyChangeListener(e -> {
            // Periksa apakah perubahan yang terjadi adalah perubahan bulan
            if ("month".equals(e.getPropertyName())) {
                // Ambil kelas yang dipilih
                String selectedKelas = (String) jkelas2.getSelectedItem();
                int selectedBulan = jbulan1.getMonth() + 1; // Bulan berbasis 0, tambahkan 1

                // Validasi: pastikan kelas telah dipilih
                if (selectedKelas != null && !selectedKelas.equals("-- Pilih Kelas --")) {
                    // Panggil fungsi tampilkanDataAbsen
                    tampilkanDataAbsen1();
                } else {
                    // Kosongkan tabel jika kelas belum dipilih
                    tabelsiswa.setModel(new DefaultTableModel(
                            null,
                            new String[]{"NIPD", "NISN", "Nama Siswa", "Jenis Kelamin", "Kelas", "Presensi", "Keterangan", "Tanggal"}
                    ));
                }
            }
        });

        jtahun.addPropertyChangeListener(e -> {
            // Periksa apakah perubahan yang terjadi adalah perubahan bulan
            if ("year".equals(e.getPropertyName())) {
                // Ambil kelas yang dipilih
                String selectedKelas = (String) jkelas2.getSelectedItem();
                int selectedTahun = jtahun.getYear();

                // Validasi: pastikan kelas telah dipilih
                if (selectedKelas != null && !selectedKelas.equals("-- Pilih Kelas --")) {
                    // Panggil fungsi tampilkanDataAbsen
                    tampilkanDataAbsen1();
                } else {
                    // Kosongkan tabel jika kelas belum dipilih
                    tabelsiswa.setModel(new DefaultTableModel(
                            null,
                            new String[]{"NIPD", "NISN", "Nama Siswa", "Jenis Kelamin", "Kelas", "Presensi", "Keterangan", "Tanggal"}
                    ));
                }
            }
        });

    }

    private void tampilkanDataAbsen() {
        // Ambil nilai kelas yang dipilih dari combo box jkelas1
        String kelas = (String) jkelas1.getSelectedItem();

        // Ambil tanggal yang dipilih dari jtanggal
        java.util.Date tanggalDate = jtanggal.getDate();

        if (kelas == null || kelas.equals("-- Pilih Kelas --") || tanggalDate == null) {
            // Jika kelas atau tanggal belum dipilih, kosongkan tabel dan keluar
            return;
        }

        // Konversi tanggal yang dipilih menjadi objek java.sql.Date
        java.sql.Date tanggal = new java.sql.Date(tanggalDate.getTime());

        String sql = "SELECT nipd, nisn, nama_siswa, kelamin, kelas, presensi, keterangan, tanggal "
                + "FROM absen WHERE kelas = ? AND DATE(tanggal) = ?";

        try (ResultSet rs = SQLConnection.doPreparedQuery(sql, kelas, tanggal)) {

            DefaultTableModel model = (DefaultTableModel) tabelsiswa.getModel();
            model.setRowCount(0);

            // Tambahkan judul kolom ke tabel absen
            model.setColumnIdentifiers(new Object[]{"NIPD", "NISN", "Nama Siswa", "Jenis Kelamin", "Kelas", "Presensi", "Keterangan", "Tanggal"});

            // Tambahkan data absen ke dalam tabel absen
            while (rs.next()) {
                String nipd = rs.getString("nipd");
                String nisn = rs.getString("nisn");
                String namaSiswa = rs.getString("nama_siswa");
                String jenisKelamin = rs.getString("kelamin");
                String kelasSiswa = rs.getString("kelas");
                String presensi = rs.getString("presensi");
                String keterangan = rs.getString("keterangan");

                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                String tanggalFormatted = dateFormatter.format(tanggal);

                // Tambahkan baris baru ke dalam tabel absen
                model.addRow(new Object[]{nipd, nisn, namaSiswa, jenisKelamin, kelasSiswa, presensi, keterangan, tanggalFormatted});
            }

            // Terapkan renderer modern untuk tabel
            tabelsiswa.setRowHeight(20); // Tinggi baris
            tabelsiswa.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
            tabelsiswa.setForeground(Color.BLACK); // Warna teks

            // Mengatur header tabel
            JTableHeader header = tabelsiswa.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 14));
            header.setBackground(new Color(0, 123, 255)); // Warna biru modern
            header.setForeground(Color.WHITE); // Teks putih
            header.setReorderingAllowed(false); // Non-reorderable kolom

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk header
            headerRenderer.setBackground(new Color(0, 123, 255)); // Warna biru
            headerRenderer.setForeground(Color.WHITE); // Teks putih

            TableColumnModel columnModel = tabelsiswa.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            // Mengatur isi tabel
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk isi tabel

            for (int i = 0; i < tabelsiswa.getColumnCount(); i++) {
                tabelsiswa.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            // Alternating row colors (striped rows)
            tabelsiswa.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (row % 2 == 0) {
                        component.setBackground(new Color(240, 240, 240)); // Abu muda
                    } else {
                        component.setBackground(Color.WHITE);
                    }
                    if (isSelected) {
                        component.setBackground(new Color(173, 216, 230)); // Biru muda
                    }
                    return component;
                }
            });

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data absen: " + ex.getMessage());
        }
    }

    private void tampilkanDataAbsen1() {
        String kelas = (String) jkelas2.getSelectedItem();
        int selectedBulan = jbulan1.getMonth() + 1;
        int selectedTahun = jtahun.getYear();

        if (kelas == null || kelas.equals("-- Pilih Kelas --")) {
            return;
        }

        String sql = "SELECT nipd, nisn, nama_siswa, kelamin, kelas, presensi, keterangan, tanggal "
                + "FROM absen WHERE kelas = ? AND MONTH(tanggal) = ? AND YEAR(tanggal) = ?";

        try {
            // Eksekusi query menggunakan prepared statement
            ResultSet rs = SQLConnection.doPreparedQuery(sql, kelas, selectedBulan, selectedTahun);

            // Bersihkan isi tabel absen sebelum menambahkan data baru
            DefaultTableModel model = (DefaultTableModel) tabelsiswa.getModel();
            model.setRowCount(0);

            // Tambahkan judul kolom ke tabel absen
            model.setColumnIdentifiers(new Object[]{"NIPD", "NISN", "Nama Siswa", "Jenis Kelamin", "Kelas", "Presensi", "Keterangan", "Tanggal"});

            // Tambahkan data absen ke dalam tabel absen
            while (rs.next()) {
                String nipd = rs.getString("nipd");
                String nisn = rs.getString("nisn");
                String namaSiswa = rs.getString("nama_siswa");
                String jenisKelamin = rs.getString("kelamin");
                String kelasSiswa = rs.getString("kelas");
                String presensi = rs.getString("presensi");
                String keterangan = rs.getString("keterangan");

                // Format tanggal untuk menampilkan dalam format "dd/MM/yyyy"
                java.sql.Date tanggal = rs.getDate("tanggal");
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                String tanggalFormatted = dateFormatter.format(tanggal);

                // Tambahkan baris baru ke dalam tabel absen
                model.addRow(new Object[]{nipd, nisn, namaSiswa, jenisKelamin, kelasSiswa, presensi, keterangan, tanggalFormatted});
            }

            // Terapkan renderer modern untuk tabel
            tabelsiswa.setRowHeight(20); // Tinggi baris
            tabelsiswa.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
            tabelsiswa.setForeground(Color.BLACK); // Warna teks

            // Mengatur header tabel
            JTableHeader header = tabelsiswa.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 14));
            header.setBackground(new Color(0, 123, 255)); // Warna biru modern
            header.setForeground(Color.WHITE); // Teks putih
            header.setReorderingAllowed(false); // Non-reorderable kolom

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk header
            headerRenderer.setBackground(new Color(0, 123, 255)); // Warna biru
            headerRenderer.setForeground(Color.WHITE); // Teks putih

            TableColumnModel columnModel = tabelsiswa.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            // Mengatur isi tabel
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk isi tabel

            for (int i = 0; i < tabelsiswa.getColumnCount(); i++) {
                tabelsiswa.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            // Alternating row colors (striped rows)
            tabelsiswa.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (row % 2 == 0) {
                        component.setBackground(new Color(240, 240, 240)); // Abu muda
                    } else {
                        component.setBackground(Color.WHITE);
                    }
                    if (isSelected) {
                        component.setBackground(new Color(173, 216, 230)); // Biru muda
                    }
                    return component;
                }
            });

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data absen: " + ex.getMessage());
        }
    }

    public void cetak2(java.sql.Date tanggal, String kelas) {
        try {
            // Dapatkan koneksi ke database
            Connection c = SQLConnection.getConnection();

            // Akses file laporan JRXML dari resources
            InputStream reportSourceStream = getClass().getResourceAsStream("/Laporan/Laporan_Absen.jrxml");
            if (reportSourceStream == null) {
                throw new FileNotFoundException("File laporan tidak ditemukan di resources!");
            }

            // Kompilasi laporan dari InputStream
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSourceStream);

            // Format tanggal dalam bahasa Indonesia
            Locale indonesiaLocale = new Locale("id", "ID");
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", indonesiaLocale);
            String formattedDate = dateFormatter.format(new java.util.Date());

            // Siapkan parameter untuk laporan
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("selectedDate", tanggal);
            parameters.put("kelas", kelas);
            parameters.put("tanggal", formattedDate);

            // Isi laporan dengan data dari database
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, c);

            // Tentukan lokasi output laporan (gunakan folder sementara)
            String reportDest = System.getProperty("java.io.tmpdir") + "/Laporan_Absen.html";

            // Ekspor laporan ke file HTML
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);

            // Tampilkan laporan
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mencetak laporan: " + e.getMessage());
        }
    }

    public void cetak3(String bulanTahun, String kelas) {
        try {
            // Dapatkan koneksi ke database
            Connection c = SQLConnection.getConnection();

            // Akses file laporan JRXML dari resources
            InputStream reportSourceStream = getClass().getResourceAsStream("/Laporan/Laporan_Absen_2.jrxml");
            if (reportSourceStream == null) {
                throw new FileNotFoundException("File laporan tidak ditemukan di resources!");
            }

            // Kompilasi laporan dari InputStream
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSourceStream);

            // Format bulan-tahun untuk ditampilkan dalam laporan
            Locale indonesiaLocale = new Locale("id", "ID");
            SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMM yyyy", indonesiaLocale);

            // Convert bulanTahun (format YYYY-MM) menjadi objek Date untuk format tampilan
            SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM");
            java.util.Date date = inputFormatter.parse(bulanTahun);
            String formattedMonth = monthFormatter.format(date);

            // Siapkan parameter untuk laporan
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("bulanTahun", bulanTahun); // Parameter untuk query
            parameters.put("kelas", kelas);          // Parameter kelas
            parameters.put("formattedMonth", formattedMonth); // Parameter untuk header laporan

            // Query untuk mendapatkan data absen per kelas dan jumlahkan presensi
            String sql = "SELECT \n"
                    + "    s.kelas, \n"
                    + "    s.nipd, \n"
                    + "    s.nisn, \n"
                    + "    s.nama_siswa, \n"
                    + "    s.kelamin,\n"
                    + "    COUNT(CASE WHEN a.presensi = 'Hadir' THEN 1 END) AS total_hadir,\n"
                    + "    COUNT(CASE WHEN a.presensi = 'Izin' THEN 1 END) AS total_izin,\n"
                    + "    COUNT(CASE WHEN a.presensi = 'Alpa' THEN 1 END) AS total_alpa,\n"
                    + "    MAX(a.keterangan) AS keterangan,\n"
                    + "    MAX(a.tanggal) AS tanggal\n"
                    + "FROM \n"
                    + "    siswa s\n"
                    + "LEFT JOIN \n"
                    + "    absen a ON s.nipd = a.nipd AND DATE_FORMAT(a.tanggal, '%Y-%m') = ?\n" // Gunakan ?
                    + "WHERE \n"
                    + "    s.kelas = ?\n" // Gunakan ?
                    + "GROUP BY \n"
                    + "    s.kelas, s.nipd, s.nisn, s.nama_siswa, s.kelamin\n"
                    + "ORDER BY \n"
                    + "    s.nama_siswa;";

            // Eksekusi query dan ambil hasilnya
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, bulanTahun);
            statement.setString(2, kelas);
            ResultSet rs = statement.executeQuery();

            // Isi laporan dengan data dari database
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRResultSetDataSource(rs));

            // Tentukan lokasi output laporan (gunakan folder sementara)
            String reportDest = System.getProperty("java.io.tmpdir") + "/Laporan_Absen_Bulanan.html";

            // Ekspor laporan ke file HTML
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);

            // Tampilkan laporan
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mencetak laporan: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtbutton = new javax.swing.ButtonGroup();
        mainpanel = new javax.swing.JPanel();
        cetakabsen = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtcari1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jkelas1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelsiswa = new javax.swing.JTable();
        jkelas = new javax.swing.JLabel();
        cetak2 = new javax.swing.JButton();
        txttanggal = new javax.swing.JLabel();
        jtanggal = new com.toedter.calendar.JDateChooser();
        pilihformat = new javax.swing.JComboBox<>();
        jbulan = new javax.swing.JLabel();
        txtkelas1 = new javax.swing.JLabel();
        jkelas2 = new javax.swing.JComboBox<>();
        cetak3 = new javax.swing.JButton();
        jbulan1 = new com.toedter.calendar.JMonthChooser();
        jtahun = new com.toedter.calendar.JYearChooser();
        jLabel6 = new javax.swing.JLabel();
        txtlabel2 = new javax.swing.JTextField();

        setLayout(new java.awt.CardLayout());

        mainpanel.setLayout(new java.awt.CardLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        txtcari1.setText("Pencarian");
        txtcari1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtcari1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcari1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcari1FocusLost(evt);
            }
        });
        txtcari1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtcari1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtcari1MouseExited(evt);
            }
        });
        txtcari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcari1ActionPerformed(evt);
            }
        });
        txtcari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcari1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcari1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcari1KeyTyped(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N

        jkelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkelas1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Murid Kelas");

        tabelsiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsiswaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelsiswa);

        jkelas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jkelas.setText("Kelas     :");

        cetak2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        cetak2.setText("Cetak");
        cetak2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetak2ActionPerformed(evt);
            }
        });

        txttanggal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txttanggal.setText("Tanggal :");

        pilihformat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Per Hari", "Per Bulan" }));
        pilihformat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihformatActionPerformed(evt);
            }
        });

        jbulan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbulan.setText("Bulan    :");

        txtkelas1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtkelas1.setText("Kelas     :");

        jkelas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkelas2ActionPerformed(evt);
            }
        });

        cetak3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        cetak3.setText("Cetak");
        cetak3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetak3ActionPerformed(evt);
            }
        });

        jbulan1.setMinimumSize(new java.awt.Dimension(150, 24));
        jbulan1.setPreferredSize(new java.awt.Dimension(150, 22));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pilihformat, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jkelas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txttanggal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jkelas1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jtanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                    .addComponent(cetak2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtkelas1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jbulan, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jbulan1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtahun, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cetak3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jkelas2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1130, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pilihformat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(txttanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jtanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(57, 57, 57))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jkelas1)
                                        .addGap(48, 48, 48))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbulan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jbulan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jtahun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jkelas2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cetak3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cetak2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jLabel5))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcari1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Cetak Absensi");

        txtlabel2.setEditable(false);
        txtlabel2.setBackground(new java.awt.Color(255, 255, 255));
        txtlabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtlabel2.setCaretColor(java.awt.Color.lightGray);
        txtlabel2.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtlabel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlabel2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cetakabsenLayout = new javax.swing.GroupLayout(cetakabsen);
        cetakabsen.setLayout(cetakabsenLayout);
        cetakabsenLayout.setHorizontalGroup(
            cetakabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cetakabsenLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(cetakabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtlabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cetakabsenLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 985, Short.MAX_VALUE)))
                .addContainerGap())
        );
        cetakabsenLayout.setVerticalGroup(
            cetakabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cetakabsenLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(cetakabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(cetakabsenLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(cetakabsen, "card2");

        add(mainpanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtcari1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcari1FocusGained
        String Pencarian = txtcari1.getText();
        if (Pencarian.equals("Pencarian")) {
            txtcari1.setText("");
        }
    }//GEN-LAST:event_txtcari1FocusGained

    private void txtcari1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcari1FocusLost
        String username = txtcari1.getText();
        if (username.equals("") || username.equals("Pencarian")) {
            txtcari1.setText("Pencarian");
        }
    }//GEN-LAST:event_txtcari1FocusLost

    private void txtcari1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcari1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1MouseClicked

    private void txtcari1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcari1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1MouseExited

    private void txtcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcari1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1ActionPerformed

    private void txtcari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1KeyPressed

    private void txtcari1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari1KeyReleased
        DefaultTableModel obj = (DefaultTableModel) tabelsiswa.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        tabelsiswa.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(txtcari1.getText()));
        obj1.setRowFilter(RowFilter.regexFilter("(?i)" + txtcari1.getText()));       // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1KeyReleased

    private void txtcari1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1KeyTyped

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4MouseClicked

    private void txtlabel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel2ActionPerformed

    private void jkelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkelas1ActionPerformed
        tampilkanDataAbsen();        // TODO add your handling code here:
    }//GEN-LAST:event_jkelas1ActionPerformed

    private void tabelsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelsiswaMouseClicked

    private void cetak2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetak2ActionPerformed
        String kelas = (String) jkelas1.getSelectedItem();
        java.util.Date tanggalDate = jtanggal.getDate();
        if (kelas != null && tanggalDate != null) {
            java.sql.Date tanggal = new java.sql.Date(tanggalDate.getTime());
            cetak2(tanggal, kelas);
        } else {
            JOptionPane.showMessageDialog(null, "Silakan pilih kelas dan tanggal terlebih dahulu.");
        }
    }//GEN-LAST:event_cetak2ActionPerformed

    private void pilihformatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihformatActionPerformed
        String selectedFormat = (String) pilihformat.getSelectedItem();

        if ("Per Hari".equalsIgnoreCase(selectedFormat)) {
            // Set komponen untuk Harian menjadi visible
            txttanggal.setVisible(true);
            jkelas.setVisible(true);
            jtanggal.setVisible(true);
            jkelas1.setVisible(true);
            cetak2.setVisible(true);

            // Set komponen untuk Bulanan menjadi tidak visible
            jbulan.setVisible(false);
            txtkelas1.setVisible(false);
            jbulan1.setVisible(false);
            jkelas2.setVisible(false);
            cetak3.setVisible(false);
            jtahun.setVisible(false);

            // Reset nilai jtanggal dan jkelas1
            jtanggal.setDate(new Date());
            jkelas1.setSelectedIndex(0);

        } else if ("Per Bulan".equalsIgnoreCase(selectedFormat)) {
            // Set komponen untuk Bulanan menjadi visible
            jbulan.setVisible(true);
            txtkelas1.setVisible(true);
            jbulan1.setVisible(true);
            jkelas2.setVisible(true);
            cetak3.setVisible(true);
            jtahun.setVisible(true);

            // Set komponen untuk Harian menjadi tidak visible
            txttanggal.setVisible(false);
            jkelas.setVisible(false);
            jtanggal.setVisible(false);
            jkelas1.setVisible(false);
            cetak2.setVisible(false);

            // Reset nilai jbulan1, jkelas2, dan jtahun
//            jbulan1.setMonth(0);
//            jkelas2.setSelectedIndex(0);
            jtahun.setYear(Calendar.getInstance().get(Calendar.YEAR));
        }
        DefaultTableModel model = (DefaultTableModel) tabelsiswa.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_pilihformatActionPerformed

    private void jkelas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkelas2ActionPerformed
        tampilkanDataAbsen1();
    }//GEN-LAST:event_jkelas2ActionPerformed

    private void cetak3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetak3ActionPerformed
        String kelas = (String) jkelas2.getSelectedItem(); // Ambil kelas dari jkelas2
        int bulan = jbulan1.getMonth() + 1; // Ambil bulan dari JMonthChooser (0 = Januari, 11 = Desember)
        int tahun = jtahun.getYear();

        if (kelas != null && bulan > 0) {
            // Format bulan ke dalam 2 digit (contoh: Januari = 01)
            String bulanFormatted = String.format("%02d", bulan);

            // Buat string bulan-tahun untuk query SQL atau parameter lain
            String bulanTahun = tahun + "-" + bulanFormatted;

            // Panggil metode cetak3 dengan parameter bulan dan kelas
            cetak3(bulanTahun, kelas);
        } else {
            JOptionPane.showMessageDialog(null, "Silakan pilih kelas dan bulan terlebih dahulu.");
        }

    }//GEN-LAST:event_cetak3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cetak2;
    private javax.swing.JButton cetak3;
    private javax.swing.JPanel cetakabsen;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jbulan;
    private com.toedter.calendar.JMonthChooser jbulan1;
    private javax.swing.JLabel jkelas;
    private javax.swing.JComboBox<String> jkelas1;
    private javax.swing.JComboBox<String> jkelas2;
    private com.toedter.calendar.JYearChooser jtahun;
    private com.toedter.calendar.JDateChooser jtanggal;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JComboBox<String> pilihformat;
    private javax.swing.JTable tabelsiswa;
    private javax.swing.ButtonGroup txtbutton;
    private javax.swing.JTextField txtcari1;
    private javax.swing.JLabel txtkelas1;
    private javax.swing.JTextField txtlabel2;
    private javax.swing.JLabel txttanggal;
    // End of variables declaration//GEN-END:variables
}
