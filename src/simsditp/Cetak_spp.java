package simsditp;

import Database.SQLConnection;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.joda.time.LocalDate;

public class Cetak_spp extends javax.swing.JPanel {

    public Cetak_spp() {
        initComponents();
        updateCombo();
        updateCombo1();
        txtlabel2.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcari.setBackground(new java.awt.Color(0, 0, 0, 1));
        jkelas1.setVisible(false);
        jbulan1.setVisible(false);
        cetaklunas.setVisible(false);
        cetaknunggak.setVisible(false);
        label3.setVisible(false);
    }

//    combBox Pilih Kelas
    private void updateCombo() {
        jkelas.removeAllItems();

        // Tambahkan placeholder
        jkelas.addItem("-- Pilih Kelas --");

        try {
            String query = "SELECT DISTINCT kelas FROM kelas";
            ResultSet rs = SQLConnection.doQuery(query);
            while (rs.next()) {
                jkelas.addItem(rs.getString("kelas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat data kelas: " + e.getMessage());
        }

        // Tambahkan ActionListener untuk memuat data tabel berdasarkan kelas dan bulan
        jkelas.addActionListener(e -> {
            String selectedKelas = (String) jkelas.getSelectedItem();
            int selectedBulan = jbulan.getMonth() + 1; // Bulan berbasis 0, tambahkan 1
            int selectedTahun = jtahun.getYear();

            if (selectedKelas != null && !selectedKelas.equals("-- Pilih Kelas --")) {
                datatable1(selectedKelas, selectedBulan, selectedTahun); // Memanggil dengan urutan yang benar
            } else {
                // Kosongkan tabel jika placeholder dipilih
                tabelspp.setModel(new DefaultTableModel(
                        null,
                        new String[]{"NISN", "Nama Siswa", "Kelas", "Bulan", "Bayar", "Tanggal", "Status"}
                ));
            }
        });

        jbulan.addPropertyChangeListener(e -> {
            // Cek apakah perubahan yang terjadi adalah perubahan bulan
            if ("month".equals(e.getPropertyName())) {
                String selectedKelas = (String) jkelas.getSelectedItem();
                int selectedBulan = jbulan.getMonth() + 1; // Bulan berbasis 0, tambahkan 1
                int selectedTahun = LocalDate.now().getYear(); // Mendapatkan tahun saat ini

                // Jika kelas sudah dipilih, maka panggil fungsi datatable1 dengan bulan dan tahun yang dipilih
                if (selectedKelas != null && !selectedKelas.equals("-- Pilih Kelas --")) {
                    datatable1(selectedKelas, selectedBulan, selectedTahun); // Memanggil dengan urutan yang benar
                } else {
                    // Kosongkan tabel jika tidak ada kelas yang dipilih
                    tabelspp.setModel(new DefaultTableModel(
                            null,
                            new String[]{"NISN", "Nama Siswa", "Kelas", "Bulan", "Bayar", "Tanggal", "Status"}
                    ));
                }
            }
        });

        jtahun.addPropertyChangeListener(e -> {
            // Cek apakah perubahan yang terjadi adalah perubahan tahun
            if ("year".equals(e.getPropertyName())) {
                String selectedKelas = (String) jkelas.getSelectedItem();
                int selectedTahun = jtahun.getYear();
                int selectedBulan = jbulan.getMonth() + 1; // Bulan berbasis 0, tambahkan 1

                // Jika kelas sudah dipilih, maka panggil fungsi datatable1 dengan kelas, bulan, dan tahun yang dipilih
                if (selectedKelas != null && !selectedKelas.equals("-- Pilih Kelas --")) {
                    datatable1(selectedKelas, selectedBulan, selectedTahun); // Memanggil dengan urutan yang benar
                } else {
                    // Kosongkan tabel jika tidak ada kelas yang dipilih
                    tabelspp.setModel(new DefaultTableModel(
                            null,
                            new String[]{"NISN", "Nama Siswa", "Kelas", "Bulan", "Bayar", "Tanggal", "Status"}
                    ));
                }
            }
        });
    }

    private void updateCombo1() {
        jkelas1.removeAllItems();

        // Tambahkan placeholder
        jkelas1.addItem("-- Pilih Kelas --");

        try {
            String query = "SELECT DISTINCT kelas FROM kelas";
            ResultSet rs = SQLConnection.doQuery(query);
            while (rs.next()) {
                jkelas1.addItem(rs.getString("kelas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat data kelas: " + e.getMessage());
        }

        // Tambahkan ActionListener untuk memuat data tabel berdasarkan kelas dan bulan
        jkelas1.addActionListener(e -> {
            String selectedKelas = (String) jkelas1.getSelectedItem();
            int selectedBulan = jbulan1.getMonth() + 1; // Bulan berbasis 0, tambahkan 1
            int selectedTahun = LocalDate.now().getYear(); // Mendapatkan tahun saat ini

            if (selectedKelas != null && !selectedKelas.equals("-- Pilih Kelas --")) {
                // Memanggil datatable1 dengan kelas, bulan, dan tahun
                datatable1(selectedKelas, selectedBulan, selectedTahun);
            } else {
                // Kosongkan tabel jika placeholder dipilih
                tabelspp.setModel(new DefaultTableModel(
                        null,
                        new String[]{"NISN", "Nama Siswa", "Kelas", "Bulan", "Bayar", "Tanggal", "Status"}
                ));
            }
        });

        jbulan1.addPropertyChangeListener(e -> {
            // Cek apakah perubahan yang terjadi adalah perubahan bulan
            if ("month".equals(e.getPropertyName())) {
                String selectedKelas = (String) jkelas1.getSelectedItem();
                int selectedBulan = jbulan1.getMonth() + 1; // Bulan berbasis 0, tambahkan 1
                int selectedTahun = LocalDate.now().getYear(); // Mendapatkan tahun saat ini

                // Jika kelas sudah dipilih, maka panggil fungsi datatable1 dengan bulan dan tahun yang dipilih
                if (selectedKelas != null && !selectedKelas.equals("-- Pilih Kelas --")) {
                    datatable1(selectedKelas, selectedBulan, selectedTahun);  // Memanggil dengan tiga parameter
                } else {
                    // Kosongkan tabel jika tidak ada kelas yang dipilih
                    tabelspp.setModel(new DefaultTableModel(
                            null,
                            new String[]{"NISN", "Nama Siswa", "Kelas", "Bulan", "Bayar", "Tanggal", "Status"}
                    ));
                }
            }
        });

    }

    protected void datatable(String kelas, int bulan, int tahun) {
        Object[] Baris = {"NIPD", "NISN", "Nama Siswa", "Kelas", "Bulan", "Bayar", "Tanggal", "Komponen", "Status"};

        // Override DefaultTableModel untuk membuat tabel tidak dapat diedit
        DefaultTableModel tabmode = new DefaultTableModel(null, Baris) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua sel tidak bisa diedit
            }
        };

        try {
            // Query dengan filter berdasarkan kelas, bulan, dan tahun
            String query = "SELECT nipd, nisn, nama_siswa, kelas, periode, jumlah_bayar, tanggal, komponen, status "
                    + "FROM spp WHERE kelas = ? AND MONTH(tanggal) = ? AND YEAR(tanggal) = ? "
                    + "ORDER BY tanggal DESC, nisn ASC";

            // Eksekusi query dengan parameter kelas, bulan, dan tahun
            ResultSet result = SQLConnection.doPreparedQuery(query, kelas, bulan, tahun);

            // Format untuk angka Rupiah
            NumberFormat rupiahFormat = NumberFormat.getNumberInstance(new Locale("id", "ID"));

            while (result.next()) {
                String jumlahBayar = result.getString("jumlah_bayar");

                // Format jumlah bayar menjadi Rp150.000
                String jumlahFormatted = "Rp " + rupiahFormat.format(Double.parseDouble(jumlahBayar));

                tabmode.addRow(new Object[]{
                    result.getString("nipd"),
                    result.getString("nisn"),
                    result.getString("nama_siswa"),
                    result.getString("kelas"),
                    result.getString("periode"),
                    jumlahFormatted, // Ubah format jumlah_bayar
                    result.getString("tanggal"),
                    result.getString("komponen"),
                    result.getString("status")
                });
            }

            tabelspp.setModel(tabmode);

            // Terapkan renderer modern untuk tabel
            tabelspp.setRowHeight(20); // Tinggi baris
            tabelspp.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Font isi tabel
            tabelspp.setForeground(Color.BLACK); // Warna teks

            // Mengatur header tabel
            JTableHeader header = tabelspp.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 14));
            header.setBackground(new Color(0, 123, 255)); // Warna biru modern
            header.setForeground(Color.WHITE); // Teks putih
            header.setReorderingAllowed(false); // Non-reorderable kolom

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk header
            headerRenderer.setBackground(new Color(0, 123, 255)); // Warna biru
            headerRenderer.setForeground(Color.WHITE); // Teks putih

            TableColumnModel columnModel = tabelspp.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            columnModel.getColumn(0).setPreferredWidth(10);  // Nipd
            columnModel.getColumn(1).setPreferredWidth(10); // NISN
            columnModel.getColumn(2).setPreferredWidth(100);  // nama
            columnModel.getColumn(3).setPreferredWidth(10);  // kelas
            columnModel.getColumn(4).setPreferredWidth(15);  // bulan
            columnModel.getColumn(5).setPreferredWidth(15); //bayar
            columnModel.getColumn(6).setPreferredWidth(20); // tanggal
            columnModel.getColumn(6).setPreferredWidth(20); // status

            // Mengatur isi tabel
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk isi tabel

            for (int i = 0; i < tabelspp.getColumnCount(); i++) {
                tabelspp.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            // Alternating row colors (striped rows)
            tabelspp.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

            DefaultTableModel obj = (DefaultTableModel) tabelspp.getModel();
            TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
            tabelspp.setRowSorter(obj1);

            // Event listener untuk pencarian
            obj.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.DELETE) {
                        updateFilter();
                    }
                }

                private void updateFilter() {
                    String searchText = txtcari.getText();
                    if (searchText.isEmpty()) {
                        obj1.setRowFilter(null);
                    } else {
                        try {
                            RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText);
                            obj1.setRowFilter(rf);
                        } catch (java.util.regex.PatternSyntaxException ex) {
                            return;
                        }
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e);
        }
    }

    protected void datatable1(String kelas, int bulan, int tahun) {
        Object[] Baris = {"NIPD", "NISN", "Nama Siswa", "Kelas", "Bulan", "Bayar", "Tanggal", "Komponen", "Status"};

        // Override DefaultTableModel untuk membuat tabel tidak dapat diedit
        DefaultTableModel tabmode = new DefaultTableModel(null, Baris) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua sel tidak bisa diedit
            }
        };

        try {
            // Query dengan filter berdasarkan kelas, bulan, dan tahun
            String query = "SELECT nipd, nisn, nama_siswa, kelas, periode, jumlah_bayar, tanggal, komponen, status "
                    + "FROM spp WHERE kelas = ? AND MONTH(tanggal) = ? AND YEAR(tanggal) = ? "
                    + "ORDER BY tanggal DESC, nisn ASC";

            // Eksekusi query dengan parameter kelas, bulan, dan tahun
            ResultSet result = SQLConnection.doPreparedQuery(query, kelas, bulan, tahun);

            // Formatter untuk format angka ribuan dengan simbol "Rp "
            NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("id", "ID"));

            while (result.next()) {
                // Format jumlah_bayar ke dalam format "Rp 150.000"
                String jumlahBayar = "Rp " + formatter.format(result.getDouble("jumlah_bayar"));

                tabmode.addRow(new Object[]{
                    result.getString("nipd"),
                    result.getString("nisn"),
                    result.getString("nama_siswa"),
                    result.getString("kelas"),
                    result.getString("periode"),
                    jumlahBayar, // Format Rp
                    result.getString("tanggal"),
                    result.getString("komponen"),
                    result.getString("status")
                });
            }

            tabelspp.setModel(tabmode);

            // Terapkan renderer modern untuk tabel
            tabelspp.setRowHeight(20); // Tinggi baris
            tabelspp.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Font isi tabel
            tabelspp.setForeground(Color.BLACK); // Warna teks

            // Mengatur header tabel
            JTableHeader header = tabelspp.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 14));
            header.setBackground(new Color(0, 123, 255)); // Warna biru modern
            header.setForeground(Color.WHITE); // Teks putih
            header.setReorderingAllowed(false); // Non-reorderable kolom

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk header
            headerRenderer.setBackground(new Color(0, 123, 255)); // Warna biru
            headerRenderer.setForeground(Color.WHITE); // Teks putih

            TableColumnModel columnModel = tabelspp.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            columnModel.getColumn(0).setPreferredWidth(10);  // Nipd
            columnModel.getColumn(1).setPreferredWidth(10); // NISN
            columnModel.getColumn(2).setPreferredWidth(100);  // nama
            columnModel.getColumn(3).setPreferredWidth(10);  // kelas
            columnModel.getColumn(4).setPreferredWidth(15);  // bulan
            columnModel.getColumn(5).setPreferredWidth(15); // bayar
            columnModel.getColumn(6).setPreferredWidth(20); // tanggal
            columnModel.getColumn(6).setPreferredWidth(20); // status

            // Mengatur isi tabel
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk isi tabel

            for (int i = 0; i < tabelspp.getColumnCount(); i++) {
                tabelspp.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            // Alternating row colors (striped rows)
            tabelspp.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

            DefaultTableModel obj = (DefaultTableModel) tabelspp.getModel();
            TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
            tabelspp.setRowSorter(obj1);

            // Event listener untuk pencarian
            obj.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.DELETE) {
                        updateFilter();
                    }
                }

                private void updateFilter() {
                    String searchText = txtcari.getText();
                    if (searchText.isEmpty()) {
                        obj1.setRowFilter(null);
                    } else {
                        try {
                            RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText);
                            obj1.setRowFilter(rf);
                        } catch (java.util.regex.PatternSyntaxException ex) {
                            return;
                        }
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e);
        }
    }

    protected void tabeltanggal(Date tanggalAwal, Date tanggalAkhir) {
        Object[] Baris = {"NIPD", "NISN", "Nama Siswa", "Kelas", "Bulan", "Bayar", "Tanggal", "Komponen", "Status"};

        // Override DefaultTableModel untuk membuat tabel tidak dapat diedit
        DefaultTableModel tabmode = new DefaultTableModel(null, Baris) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua sel tidak bisa diedit
            }
        };

        try {
            // Query untuk filter data berdasarkan tanggal
            String query = "SELECT nipd, nisn, nama_siswa, kelas, DATE_FORMAT(tanggal, '%M %Y') AS periode, "
                    + "jumlah_bayar, tanggal, komponen, status "
                    + "FROM spp WHERE tanggal BETWEEN ? AND ? "
                    + "ORDER BY tanggal DESC, nisn ASC";

            // Eksekusi query dengan SQLConnection.doPreparedQuery
            ResultSet result = SQLConnection.doPreparedQuery(query,
                    new java.sql.Date(tanggalAwal.getTime()),
                    new java.sql.Date(tanggalAkhir.getTime()));

            // Isi tabel dengan data dari hasil query
            while (result.next()) {
                tabmode.addRow(new Object[]{
                    result.getString("nipd"),
                    result.getString("nisn"),
                    result.getString("nama_siswa"),
                    result.getString("kelas"),
                    result.getString("periode"),
                    result.getString("jumlah_bayar"),
                    result.getDate("tanggal"),
                    result.getString("komponen"),
                    result.getString("status")
                });
            }

            tabelspp.setModel(tabmode);

            tabelspp.setRowHeight(20); // Tinggi baris
            tabelspp.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
            tabelspp.setForeground(Color.BLACK); // Warna teks

            // Mengatur header tabel
            JTableHeader header = tabelspp.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 14));
            header.setBackground(new Color(0, 123, 255)); // Warna biru modern
            header.setForeground(Color.WHITE); // Teks putih
            header.setReorderingAllowed(false); // Non-reorderable kolom

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk header
            headerRenderer.setBackground(new Color(0, 123, 255)); // Warna biru
            headerRenderer.setForeground(Color.WHITE); // Teks putih

            TableColumnModel columnModel = tabelspp.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            columnModel.getColumn(0).setPreferredWidth(10);  // Nipd
            columnModel.getColumn(1).setPreferredWidth(10); // NISN
            columnModel.getColumn(2).setPreferredWidth(100);  // nama
            columnModel.getColumn(3).setPreferredWidth(10);  // kelas
            columnModel.getColumn(4).setPreferredWidth(15);  // bulan
            columnModel.getColumn(5).setPreferredWidth(15); //bayar
            columnModel.getColumn(6).setPreferredWidth(20); // tanggal
            columnModel.getColumn(6).setPreferredWidth(20); // status

            // Mengatur isi tabel
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk isi tabel

            for (int i = 0; i < tabelspp.getColumnCount(); i++) {
                tabelspp.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            // Alternating row colors (striped rows)
            tabelspp.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

            DefaultTableModel obj = (DefaultTableModel) tabelspp.getModel();
            TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
            tabelspp.setRowSorter(obj1);

            // Event listener untuk pencarian
            obj.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.DELETE) {
                        updateFilter();
                    }
                }

                private void updateFilter() {
                    String searchText = txtcari.getText();
                    if (searchText.isEmpty()) {
                        obj1.setRowFilter(null);
                    } else {
                        try {
                            RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText);
                            obj1.setRowFilter(rf);
                        } catch (java.util.regex.PatternSyntaxException ex) {
                            return;
                        }
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e);
        }
    }

// Fungsi untuk mengatur desain tabel
    private void setupTableDesign(JTable table) {
        table.setRowHeight(20); // Tinggi baris
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
        table.setForeground(Color.BLACK); // Warna teks

        // Mengatur header tabel
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 123, 255)); // Warna biru modern
        header.setForeground(Color.WHITE); // Teks putih
        header.setReorderingAllowed(false); // Non-reorderable kolom

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk header
        headerRenderer.setBackground(new Color(0, 123, 255)); // Warna biru
        headerRenderer.setForeground(Color.WHITE); // Teks putih

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Alternating row colors (striped rows)
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
    }

    public void cetak2(String kelas, int bulan) {
        try {
            // Validasi input kelas
            if (kelas == null || kelas.equals("-- Pilih Kelas --")) {
                JOptionPane.showMessageDialog(null, "Silahkan pilih kelas.");
                return;
            }

            // Validasi input bulan
            if (bulan < 1 || bulan > 12) {
                JOptionPane.showMessageDialog(null, "Silahkan pilih bulan yang valid.");
                return;
            }

            // Dapatkan koneksi database
            Connection c = SQLConnection.getConnection();

            // Akses file laporan JRXML dari resources
            InputStream reportSourceStream = getClass().getResourceAsStream("/Laporan/Laporan_spp.jrxml");
            if (reportSourceStream == null) {
                throw new FileNotFoundException("File laporan tidak ditemukan di resources!");
            }

            // Kompilasi laporan dari InputStream
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSourceStream);

            // Format nama bulan dan tahun dalam bahasa Indonesia
            Locale indonesiaLocale = new Locale("id", "ID");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, bulan - 1); // Bulan dimulai dari 0 (Januari = 0)
            String formattedMonthYear = new SimpleDateFormat("MMMM yyyy", indonesiaLocale).format(calendar.getTime());

            // Siapkan parameter untuk laporan
            Map<String, Object> params = new HashMap<>();
            params.put("kelas", kelas); // Parameter untuk kelas
            params.put("bulan", bulan); // Parameter untuk bulan (format angka 1-12)
            params.put("tanggal", formattedMonthYear); // Nama bulan dalam format teks (e.g., Januari 2025)

            // Isi laporan dengan data dari database
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, c);

            // Tentukan lokasi output laporan (gunakan folder sementara)
            String reportDest = System.getProperty("java.io.tmpdir") + "/Laporan_Spp.html";

            // Ekspor laporan ke file HTML
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);

            // Tampilkan laporan
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mencetak laporan: " + e.getMessage());
        }
    }

    public void cetaklunas(String kelas, int bulan) {
        try {
            // Validasi input kelas
            if (kelas == null || kelas.equals("-- Pilih Kelas --")) {
                JOptionPane.showMessageDialog(null, "Silahkan pilih kelas.");
                return;
            }

            // Validasi input bulan
            if (bulan < 1 || bulan > 12) {
                JOptionPane.showMessageDialog(null, "Silahkan pilih bulan yang valid.");
                return;
            }

            // Dapatkan koneksi database
            Connection c = SQLConnection.getConnection();

            // Akses file laporan JRXML dari resources
            InputStream reportSourceStream = getClass().getResourceAsStream("/Laporan/spplunas.jrxml");
            if (reportSourceStream == null) {
                throw new FileNotFoundException("File laporan tidak ditemukan di resources!");
            }

            // Kompilasi laporan dari InputStream
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSourceStream);

            // Format nama bulan dan tahun dalam bahasa Indonesia
            Locale indonesiaLocale = new Locale("id", "ID");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, bulan - 1); // Bulan dimulai dari 0 (Januari = 0)
            String formattedMonthYear = new SimpleDateFormat("MMMM yyyy", indonesiaLocale).format(calendar.getTime());

            // Siapkan parameter untuk laporan
            Map<String, Object> params = new HashMap<>();
            params.put("kelas", kelas); // Parameter untuk kelas
            params.put("bulan", bulan); // Parameter untuk bulan (format angka 1-12)
            params.put("tanggal", formattedMonthYear); // Nama bulan dalam format teks (e.g., Januari 2025)

            // Isi laporan dengan data dari database
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, c);

//             Tentukan lokasi output laporan (gunakan folder sementara)
            String reportDest = System.getProperty("java.io.tmpdir") + "/Laporan_spplunas.html";

//             Ekspor laporan ke file HTML
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);

            // Tampilkan laporan
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mencetak laporan: " + e.getMessage());
        }
    }

    public void cetaknunggak(String kelas, int bulan) {
        try {
            // Validasi input kelas
            if (kelas == null || kelas.equals("-- Pilih Kelas --")) {
                JOptionPane.showMessageDialog(null, "Silahkan pilih kelas.");
                return;
            }

            // Validasi input bulan
            if (bulan < 1 || bulan > 12) {
                JOptionPane.showMessageDialog(null, "Silahkan pilih bulan yang valid.");
                return;
            }

            // Dapatkan koneksi database
            Connection c = SQLConnection.getConnection();

            // Akses file laporan JRXML dari resources
            InputStream reportSourceStream = getClass().getResourceAsStream("/Laporan/Laporan_sppnunggak.jrxml");
            if (reportSourceStream == null) {
                throw new FileNotFoundException("File laporan tidak ditemukan di resources!");
            }

            // Kompilasi laporan dari InputStream
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSourceStream);

            // Format nama bulan dan tahun dalam bahasa Indonesia
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, bulan - 1); // Bulan dimulai dari 0 (Januari = 0)

            // Siapkan parameter untuk laporan
            Map<String, Object> params = new HashMap<>();
            params.put("kelas", kelas); // Parameter untuk kelas
            params.put("bulan", bulan); // Parameter untuk bulan (format angka 1-12)

            // Isi laporan dengan data dari database
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, c);

            // Tentukan lokasi output laporan (gunakan folder sementara)
            String reportDest = System.getProperty("java.io.tmpdir") + "/Laporan_sppnunggak.html";

            // Ekspor laporan ke file HTML
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);

            // Tampilkan laporan
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mencetak laporan: " + e.getMessage());
        }
    }

    public void setupTable(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    TableModel model = table.getModel();
                    String kelas = (String) model.getValueAt(row, 0);
                    int selectedMonth = jbulan.getMonth() + 1;
                    cetak2(kelas, selectedMonth);
                }
            }
        });
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
        cetakspp = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtcari = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jkelas = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelspp = new javax.swing.JTable();
        label0 = new javax.swing.JLabel();
        cetak = new javax.swing.JButton();
        jbulan = new com.toedter.calendar.JMonthChooser();
        jtahun = new com.toedter.calendar.JYearChooser();
        pilihformat = new javax.swing.JComboBox<>();
        label3 = new javax.swing.JLabel();
        jkelas1 = new javax.swing.JComboBox<>();
        cetaknunggak = new javax.swing.JButton();
        cetaklunas = new javax.swing.JButton();
        jbulan1 = new com.toedter.calendar.JMonthChooser();
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

        txtcari.setText("Pencarian");
        txtcari.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtcari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcariFocusLost(evt);
            }
        });
        txtcari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtcariMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtcariMouseExited(evt);
            }
        });
        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcariKeyTyped(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N

        jkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkelasActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Murid Kelas");

        tabelspp.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelspp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsppMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelspp);

        label0.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label0.setText("Kelas  :");

        cetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        cetak.setText("Cetak");
        cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakActionPerformed(evt);
            }
        });

        pilihformat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Per Kelas", "Per Status" }));
        pilihformat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihformatActionPerformed(evt);
            }
        });

        label3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        label3.setText("Kelas  :");

        jkelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkelas1ActionPerformed(evt);
            }
        });

        cetaknunggak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        cetaknunggak.setText("Nunggak");
        cetaknunggak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetaknunggakActionPerformed(evt);
            }
        });

        cetaklunas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        cetaklunas.setText("Lunas");
        cetaklunas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetaklunasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(label0, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jbulan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtahun, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cetaklunas, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                    .addComponent(jkelas1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jbulan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cetaknunggak, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5))
                        .addGap(18, 318, Short.MAX_VALUE)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel3))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(pilihformat, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pilihformat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label0, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jbulan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jtahun, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jbulan1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cetaklunas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cetaknunggak, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Cetak Data SPP");

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

        javax.swing.GroupLayout cetaksppLayout = new javax.swing.GroupLayout(cetakspp);
        cetakspp.setLayout(cetaksppLayout);
        cetaksppLayout.setHorizontalGroup(
            cetaksppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cetaksppLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(cetaksppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cetaksppLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(cetaksppLayout.createSequentialGroup()
                        .addGroup(cetaksppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtlabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        cetaksppLayout.setVerticalGroup(
            cetaksppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cetaksppLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(cetaksppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(cetaksppLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(cetakspp, "card2");

        add(mainpanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtcariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariFocusGained
        String Pencarian = txtcari.getText();
        if (Pencarian.equals("Pencarian")) {
            txtcari.setText("");
        }
    }//GEN-LAST:event_txtcariFocusGained

    private void txtcariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariFocusLost
        String username = txtcari.getText();
        if (username.equals("") || username.equals("Pencarian")) {
            txtcari.setText("Pencarian");
        }
    }//GEN-LAST:event_txtcariFocusLost

    private void txtcariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariMouseClicked

    private void txtcariMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariMouseExited

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyPressed

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        DefaultTableModel obj = (DefaultTableModel) tabelspp.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        tabelspp.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(txtcari.getText()));
        obj1.setRowFilter(RowFilter.regexFilter("(?i)" + txtcari.getText()));       // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyReleased

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyTyped

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4MouseClicked

    private void txtlabel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel2ActionPerformed

    private void jkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkelasActionPerformed
        String selectedKelas = (String) jkelas.getSelectedItem();
        int selectedBulan = jbulan.getMonth() + 1; // Bulan berbasis 0, tambahkan 1
        int selectedTahun = jtahun.getYear(); // Mendapatkan tahun dari JYearChooser

        if (selectedKelas != null && !selectedKelas.equals("-- Pilih Kelas --")) {
            datatable(selectedKelas, selectedBulan, selectedTahun);
        }
    }//GEN-LAST:event_jkelasActionPerformed

    private void tabelsppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsppMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelsppMouseClicked

    private void cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakActionPerformed
        String selectedClass = (String) jkelas.getSelectedItem();
        int selectedMonth = jbulan.getMonth() + 1;
        cetak2(selectedClass, selectedMonth);
    }//GEN-LAST:event_cetakActionPerformed

    private void jkelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jkelas1ActionPerformed

    private void cetaknunggakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetaknunggakActionPerformed
        String selectedClass = (String) jkelas1.getSelectedItem();
        int selectedMonth = jbulan1.getMonth() + 1;
        cetaknunggak(selectedClass, selectedMonth);
    }//GEN-LAST:event_cetaknunggakActionPerformed

    private void cetaklunasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetaklunasActionPerformed
        String selectedClass = (String) jkelas1.getSelectedItem();
        int selectedMonth = jbulan1.getMonth() + 1;
        cetaklunas(selectedClass, selectedMonth);
    }//GEN-LAST:event_cetaklunasActionPerformed

    private void pilihformatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihformatActionPerformed
        String selectedFormat = (String) pilihformat.getSelectedItem();

        if ("Per Kelas".equalsIgnoreCase(selectedFormat)) {
            // Set komponen untuk HaPer kelas menjadi visible
            jkelas.setVisible(true);
            jbulan.setVisible(true);
            jtahun.setVisible(true);
            cetak.setVisible(true);
            label0.setVisible(true);

            // Set komponen untuk Per Status menjadi tidak visible
            jkelas1.setVisible(false);
            jbulan1.setVisible(false);
            cetaklunas.setVisible(false);
            cetaknunggak.setVisible(false);
            label3.setVisible(false);

        } else if ("Per Status".equalsIgnoreCase(selectedFormat)) {
            // Set komponen untuk Bulanan menjadi visible
            jkelas1.setVisible(true);
            jbulan1.setVisible(true);
            cetaklunas.setVisible(true);
            cetaknunggak.setVisible(true);
            label3.setVisible(true);

            // Set komponen untuk Per kelas menjadi tidak visible
            jkelas.setVisible(false);
            jbulan.setVisible(false);
            jtahun.setVisible(false);
            cetak.setVisible(false);
            label0.setVisible(false);
    }//GEN-LAST:event_pilihformatActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabelspp.getModel();
        model.setRowCount(0);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cetak;
    private javax.swing.JButton cetaklunas;
    private javax.swing.JButton cetaknunggak;
    private javax.swing.JPanel cetakspp;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private com.toedter.calendar.JMonthChooser jbulan;
    private com.toedter.calendar.JMonthChooser jbulan1;
    private javax.swing.JComboBox<String> jkelas;
    private javax.swing.JComboBox<String> jkelas1;
    private com.toedter.calendar.JYearChooser jtahun;
    private javax.swing.JLabel label0;
    private javax.swing.JLabel label3;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JComboBox<String> pilihformat;
    private javax.swing.JTable tabelspp;
    private javax.swing.ButtonGroup txtbutton;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtlabel2;
    // End of variables declaration//GEN-END:variables
}
