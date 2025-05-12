package simsditp;

import Database.SQLConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.PatternSyntaxException;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import jnafilechooser.api.JnaFileChooser;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class Siswa extends javax.swing.JPanel {

    private DefaultTableModel tabmode;

    public Siswa() {
        initComponents();
        kosong();
        updateCombo();
        updateCombo1();
        updateCombo2();
        txtlabel.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtlabel1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtlabel2.setBackground(new java.awt.Color(0, 0, 0, 1));
        jlabel.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcari.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcari1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnipd.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnisn.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnama.setBackground(new java.awt.Color(0, 0, 0, 1));
        txttempatlahir.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnipd1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnisn1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnama1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txttempatlahir1.setBackground(new java.awt.Color(0, 0, 0, 1));
        label3.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnipd2.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnisn2.setBackground(new java.awt.Color(0, 0, 0, 1));
        namasiswa.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtdaftar.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtlabel3.setBackground(new java.awt.Color(0, 0, 0, 1));
        tabelsiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tabelsiswa.getSelectedRow();

                if (row != -1) {
                    edit.setVisible(true);
                    hapus.setVisible(true);
                    batal.setVisible(true);
                    tambah.setVisible(false);
                    bantuan.setVisible(false);
                    excel.setVisible(false);
                    txtlulus.setVisible(false);
                } else {
                    edit.setVisible(false);
                    hapus.setVisible(false);
                    batal.setVisible(false);
                    tambah.setVisible(true);
                    bantuan.setVisible(true);
                    excel.setVisible(true);
                    txtlulus.setVisible(true);
                }
            }
        });

        tabelbantuan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tabelsiswa.getSelectedRow();
                if (row != -1) {
                    hapus1.setVisible(false);
                    batal2.setVisible(false);
                } else {
                    hapus1.setVisible(true);
                    batal2.setVisible(true);
                }
            }
        });

        batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Sembunyikan tombol-tombol
                edit.setVisible(false);
                hapus.setVisible(false);
                batal.setVisible(false);
                tabelsiswa.clearSelection();
                tambah.setVisible(true);
                kosong();
//                autonumber();
            }
        });
        keluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Sembunyikan tombol-tombol
                edit.setVisible(false);
                hapus.setVisible(false);
                batal.setVisible(false);
                tabelsiswa.clearSelection();
                tambah.setVisible(true);
                kosong();
//                autonumber();
            }
        });
        edit.setVisible(false);
    }

    protected void kosong() {
        txtnipd.setText("");
        txtnisn.setText("");
        txtnama.setText("");
        cbkelamin.setSelectedItem(0);
        cbkelas.setSelectedItem(0);
        txttempatlahir.setText("");
        jtanggal1.setDate(null);
        txtbutton.clearSelection();
    }

    protected void kosong1() {
        txtnisn1.setText("");
        txtnama1.setText("");
        cbkelamin1.setSelectedItem(0);
        cbkelas1.setSelectedItem(0);
        txttempatlahir1.setText("");
        jtanggal1.setDate(null);
        txtbutton.clearSelection();
    }

    public static void showSuccessDialog(String message) {
        ImageIcon checkIcon = new ImageIcon("src/img/check.png"); // Sesuaikan path gambar

        JOptionPane.showMessageDialog(null, message, "Sukses",
                JOptionPane.INFORMATION_MESSAGE, checkIcon);
    }

    public static void showErrorDialog(String message) {
        ImageIcon checkIcon = new ImageIcon("src/img/gagal.png"); // Sesuaikan path gambar

        JOptionPane.showMessageDialog(null, message, "Error",
                JOptionPane.INFORMATION_MESSAGE, checkIcon);
    }

    protected void datatable() {
        Object[] Baris = {"No", "Nama Siswa", "NIPD", "NISN", "Kelas", "Status", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir"};
        tabmode = new DefaultTableModel(null, Baris);

        try {
            String query = "SELECT nama_siswa, nipd, nisn, kelas, status, kelamin, tempat_lahir, "
                    + "tanggal_lahir FROM siswa WHERE status_lulus = 'Belum lulus' "
                    + "ORDER BY kelas ASC, nama_siswa ASC";
            ResultSet result = SQLConnection.doQuery(query);

            int no = 1; // Inisialisasi nomor urut
            while (result.next()) {
                tabmode.addRow(new Object[]{
                    no++, // Tambahkan nomor urut
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),});
            }

            tabelsiswa.setModel(tabmode);
            edit.setVisible(false);
            hapus.setVisible(false);
            batal.setVisible(false);
            bantuan.setVisible(true);
            excel.setVisible(true);
            txtlulus.setVisible(true);

            // Pengaturan tampilan tabel
            tabelsiswa.setRowHeight(20);
            tabelsiswa.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
            tabelsiswa.setForeground(Color.BLACK);

            // Pengaturan header tabel
            JTableHeader header = tabelsiswa.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Font header
            header.setBackground(new Color(0, 123, 255)); // Warna biru
            header.setForeground(Color.WHITE); // Teks putih
            header.setReorderingAllowed(false); // Non-reorderable kolom

            // Renderer untuk header tabel
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah
            headerRenderer.setBackground(new Color(0, 123, 255));
            headerRenderer.setForeground(Color.WHITE);

            // Terapkan renderer header ke semua kolom
            TableColumnModel columnModel = tabelsiswa.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            // Atur lebar kolom dengan lebih fleksibel
            int[] columnWidths = {5, 151, 10, 40, 10, 10, 10, 80, 50}; // Lebar untuk setiap kolom
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
                columnModel.getColumn(i).setMinWidth(5); // Opsional: Tetapkan lebar minimum
                columnModel.getColumn(i).setMaxWidth(300); // Opsional: Tetapkan lebar maksimum
            }

            // Renderer untuk isi tabel
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah
            for (int i = 0; i < tabelsiswa.getColumnCount(); i++) {
                tabelsiswa.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            // Alternating row colors (striped rows)
            tabelsiswa.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (row % 2 == 0) {
                        component.setBackground(new Color(240, 240, 240)); // Warna abu muda
                    } else {
                        component.setBackground(Color.WHITE); // Warna putih
                    }
                    if (isSelected) {
                        component.setBackground(new Color(173, 216, 230)); // Warna biru muda saat dipilih
                    }
                    return component;
                }
            });

            DefaultTableModel obj = (DefaultTableModel) tabelsiswa.getModel();
            TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
            tabelsiswa.setRowSorter(obj1);

            // Tambahkan listener untuk mendeteksi penghapusan data
            obj.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.DELETE) {
                        datatable(); // Refresh tabel untuk menghitung ulang nomor urut
                    }
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e.getMessage());
        }
    }

    protected void datatable1(String kelas) {
        // Header tabel
        Object[] Baris = {"No", "Nama Siswa", "NIPD", "NISN", "Kelas", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir"};
        tabmode = new DefaultTableModel(null, Baris);

        // Query untuk mengambil data siswa berdasarkan kelas, status_lulus 'Belum lulus', dan status 'Aktif'
        String sql = "SELECT nama_siswa, nipd, nisn, kelas, kelamin, tempat_lahir, tanggal_lahir "
                + "FROM siswa "
                + "WHERE kelas = ? AND status_lulus = 'Belum lulus' AND status = 'Aktif'";

        try {
            // Eksekusi query dengan PreparedStatement
            PreparedStatement statement = SQLConnection.getConnection().prepareStatement(sql);
            statement.setString(1, kelas);
            ResultSet result = statement.executeQuery();

            // Mengisi data ke tabel
            int no = 1; // Nomor urut
            while (result.next()) {
                tabmode.addRow(new Object[]{
                    no++, // Nomor urut
                    result.getString("nama_siswa"),
                    result.getString("nipd"),
                    result.getString("nisn"),
                    result.getString("kelas"),
                    result.getString("kelamin"),
                    result.getString("tempat_lahir"),
                    result.getString("tanggal_lahir"),});
            }

            // Tampilkan data di JTable
            tabelsiswa1.setModel(tabmode);

            // Atur visibilitas tombol
            edit.setVisible(false);
            hapus.setVisible(false);
            batal.setVisible(false);
            bantuan.setVisible(true);

            // Pengaturan tampilan tabel
            tabelsiswa1.setRowHeight(20);
            tabelsiswa1.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
            tabelsiswa1.setForeground(Color.BLACK);

            // Pengaturan header tabel
            JTableHeader header = tabelsiswa1.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Font header
            header.setBackground(new Color(0, 123, 255)); // Warna biru
            header.setForeground(Color.WHITE); // Teks putih
            header.setReorderingAllowed(false); // Non-reorderable kolom

            // Renderer untuk header tabel
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah
            headerRenderer.setBackground(new Color(0, 123, 255));
            headerRenderer.setForeground(Color.WHITE);

            // Terapkan renderer header ke semua kolom
            TableColumnModel columnModel = tabelsiswa1.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            // Atur lebar kolom dengan lebih fleksibel
            int[] columnWidths = {5, 151, 10, 40, 10, 10, 80, 50}; // Lebar untuk setiap kolom
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
                columnModel.getColumn(i).setMinWidth(5); // Opsional: Tetapkan lebar minimum
                columnModel.getColumn(i).setMaxWidth(300); // Opsional: Tetapkan lebar maksimum
            }

            // Renderer untuk isi tabel
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah
            for (int i = 0; i < tabelsiswa1.getColumnCount(); i++) {
                tabelsiswa1.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            // Alternating row colors (striped rows)
            tabelsiswa1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (row % 2 == 0) {
                        component.setBackground(new Color(240, 240, 240)); // Warna abu muda
                    } else {
                        component.setBackground(Color.WHITE); // Warna putih
                    }
                    if (isSelected) {
                        component.setBackground(new Color(173, 216, 230)); // Warna biru muda saat dipilih
                    }
                    return component;
                }
            });

            DefaultTableModel obj = (DefaultTableModel) tabelsiswa1.getModel();
            TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
            tabelsiswa1.setRowSorter(obj1);

            // Tambahkan listener untuk mendeteksi penghapusan data
            obj.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.DELETE) {
                        datatable(); // Refresh tabel untuk menghitung ulang nomor urut
                    }
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e.getMessage());
        }
    }

    protected void tabelbantuan() {
        Object[] Baris = {"NISN", "NIPD", "Nama Siswa", "Kelas", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir", "Status Penerima"};
        tabmode = new DefaultTableModel(null, Baris) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Semua sel tidak bisa diedit
                return false;
            }
        };

        try {
            String query = "SELECT nisn, nipd, nama_siswa, kelas, kelamin, tempat_lahir, tanggal_lahir, status_bantuan "
                    + "FROM siswa WHERE status_bantuan = 'YA' ORDER BY nisn ASC";
            ResultSet result = SQLConnection.doQuery(query);

            while (result.next()) {
                tabmode.addRow(new Object[]{
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),});
            }
            tabelbantuan.setModel(tabmode);
            hapus1.setVisible(false);
            batal2.setVisible(false);

            // Pengaturan tampilan tabel
            tabelbantuan.setRowHeight(20);
            tabelbantuan.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
            tabelbantuan.setForeground(Color.BLACK);

            // Pengaturan header tabel
            JTableHeader header = tabelbantuan.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Font header
            header.setBackground(new Color(0, 123, 255)); // Warna biru
            header.setForeground(Color.WHITE); // Teks putih
            header.setReorderingAllowed(false); // Non-reorderable kolom

            // Renderer untuk header tabel
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah
            headerRenderer.setBackground(new Color(0, 123, 255));
            headerRenderer.setForeground(Color.WHITE);

            // Terapkan renderer header ke semua kolom
            TableColumnModel columnModel = tabelbantuan.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            int[] columnWidths = {10, 10, 100, 10, 10, 15, 10, 10}; // Lebar untuk setiap kolom
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
                columnModel.getColumn(i).setMinWidth(5); // Opsional: Tetapkan lebar minimum
                columnModel.getColumn(i).setMaxWidth(300); // Opsional: Tetapkan lebar maksimum
            }

            // Renderer untuk isi tabel
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah
            for (int i = 0; i < tabelbantuan.getColumnCount(); i++) {
                tabelbantuan.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            // Alternating row colors (striped rows)
            tabelbantuan.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (row % 2 == 0) {
                        component.setBackground(new Color(240, 240, 240)); // Warna abu muda
                    } else {
                        component.setBackground(Color.WHITE); // Warna putih
                    }
                    if (isSelected) {
                        component.setBackground(new Color(173, 216, 230)); // Warna biru muda saat dipilih
                    }
                    return component;
                }
            });

            DefaultTableModel obj = (DefaultTableModel) tabelbantuan.getModel();
            TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
            tabelbantuan.setRowSorter(obj1);
            obj.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.DELETE) {
                        // Data telah dihapus, perbarui filter
                        updateFilter();
                    }
                }

                private void updateFilter() {
                    String searchText = txtcari.getText();
                    // Periksa apakah ada teks pencarian yang diterapkan
                    if (searchText.isEmpty()) {
                        // Jika tidak ada teks pencarian, hapus filter dan tampilkan semua data
                        obj1.setRowFilter(null);
                    } else {
                        // Jika ada teks pencarian, terapkan filter
                        RowFilter<DefaultTableModel, Object> rf = null;
                        try {
                            rf = RowFilter.regexFilter("(?i)" + searchText);
                        } catch (java.util.regex.PatternSyntaxException e) {
                            return;
                        }
                        obj1.setRowFilter(rf);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "data gagal dipanggil" + e);
        }
    }

// Fungsi untuk memperbarui filter tabel
    private void updateFilter(String searchText, TableRowSorter<DefaultTableModel> sorter) {
        if (sorter != null) {
            try {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
            } catch (PatternSyntaxException e) {
                sorter.setRowFilter(null); // Jika teks pencarian tidak valid
            }
        }
    }

    private void updateCombo() {
        try {
            String query = "SELECT * FROM kelas";
            ResultSet result = SQLConnection.doQuery(query);
            while (result.next()) {
                cbkelas.addItem(result.getString("kelas"));
            }
        } catch (SQLException e) {
        }
    }

    private void updateCombo1() {
        try {
            String query = "SELECT * FROM kelas";
            ResultSet result = SQLConnection.doQuery(query);
            while (result.next()) {
                cbkelas1.addItem(result.getString("kelas"));
            }
        } catch (SQLException e) {
        }
    }

    private void updateCombo2() {
        // Hapus semua item di combo box
        cbkelas2.removeAllItems();

        // Tambahkan placeholder
        cbkelas2.addItem("-- Pilih Kelas --");

        try {
            // Ambil data kelas dari database
            String query = "SELECT kelas FROM kelas ORDER BY kelas DESC";
            ResultSet rs = SQLConnection.doQuery(query);

            while (rs.next()) {
                cbkelas2.addItem(rs.getString("kelas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat data kelas: " + e.getMessage());
        }

        // Tambahkan ItemListener untuk mendeteksi perubahan pilihan kelas
        cbkelas2.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedKelas = (String) cbkelas2.getSelectedItem();

                // Pastikan kelas yang dipilih valid (bukan placeholder)
                if (!"-- Pilih Kelas --".equals(selectedKelas)) {
                    datatable1(selectedKelas); // Panggil fungsi datatable1() dengan parameter kelas
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
        dataSiswa = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelsiswa = new javax.swing.JTable();
        txtcari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tambah = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        batal = new javax.swing.JButton();
        bantuan = new javax.swing.JButton();
        excel = new javax.swing.JButton();
        txtlulus = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtlabel = new javax.swing.JTextField();
        tambahSiswa = new javax.swing.JPanel();
        jlabel = new javax.swing.JLabel();
        txtlabel1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cbkelas = new javax.swing.JComboBox<>();
        txttempatlahir = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtnipd = new javax.swing.JTextField();
        cbkelamin = new javax.swing.JComboBox<>();
        txtnisn = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        keluar = new javax.swing.JButton();
        simpan = new javax.swing.JButton();
        jtanggal1 = new com.toedter.calendar.JDateChooser();
        editSiswa = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtlabel2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        txttempatlahir1 = new javax.swing.JTextField();
        cbkelamin1 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        txtnisn1 = new javax.swing.JTextField();
        txtnipd1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtnama1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        cbkelas1 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        keluar1 = new javax.swing.JButton();
        simpan1 = new javax.swing.JButton();
        jtanggal = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        cbstatus = new javax.swing.JComboBox<>();
        dataPenerima = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtnipd2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        simpan2 = new javax.swing.JButton();
        batal1 = new javax.swing.JButton();
        txtnisn2 = new javax.swing.JTextField();
        namasiswa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        label3 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        txtdaftar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelbantuan = new javax.swing.JTable();
        txtcari1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        hapus1 = new javax.swing.JButton();
        batal2 = new javax.swing.JButton();
        kelulusan = new javax.swing.JPanel();
        mainpanel1 = new javax.swing.JPanel();
        cetakabsen1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cbkelas2 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelsiswa1 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        lulus = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtlabel3 = new javax.swing.JTextField();

        setLayout(new java.awt.CardLayout());

        mainpanel.setLayout(new java.awt.CardLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

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
        jScrollPane1.setViewportView(tabelsiswa);

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

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N

        tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        tambah.setText("Tambah");
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });

        edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        edit.setText("Edit");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hapus.png"))); // NOI18N
        hapus.setText("Hapus");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        batal.setText("Batal");
        batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalActionPerformed(evt);
            }
        });

        bantuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        bantuan.setText("Penerima Bantuan");
        bantuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bantuanActionPerformed(evt);
            }
        });

        excel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Microsoft Excel 2019.png"))); // NOI18N
        excel.setText("Import Excel");
        excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelActionPerformed(evt);
            }
        });

        txtlulus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        txtlulus.setText("kelulusan");
        txtlulus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlulusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(batal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bantuan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(excel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtlulus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tambah)
                        .addComponent(edit)
                        .addComponent(hapus)
                        .addComponent(batal)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bantuan))
                    .addComponent(excel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtlulus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Data Siswa");

        txtlabel.setEditable(false);
        txtlabel.setBackground(new java.awt.Color(255, 255, 255));
        txtlabel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtlabel.setCaretColor(java.awt.Color.lightGray);
        txtlabel.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtlabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlabelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataSiswaLayout = new javax.swing.GroupLayout(dataSiswa);
        dataSiswa.setLayout(dataSiswaLayout);
        dataSiswaLayout.setHorizontalGroup(
            dataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataSiswaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(dataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataSiswaLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(897, 1013, Short.MAX_VALUE))
                    .addGroup(dataSiswaLayout.createSequentialGroup()
                        .addGroup(dataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtlabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        dataSiswaLayout.setVerticalGroup(
            dataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataSiswaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(dataSiswaLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(dataSiswa, "card2");

        jlabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlabel.setText("Data Tambah Siswa");

        txtlabel1.setEditable(false);
        txtlabel1.setBackground(new java.awt.Color(255, 255, 255));
        txtlabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtlabel1.setCaretColor(java.awt.Color.lightGray);
        txtlabel1.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtlabel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlabel1ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel22.setText("Jenis Kelamin");

        jLabel23.setText("Nama Siswa");

        cbkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkelasActionPerformed(evt);
            }
        });
        cbkelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbkelasKeyPressed(evt);
            }
        });

        txttempatlahir.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txttempatlahir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttempatlahirActionPerformed(evt);
            }
        });
        txttempatlahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttempatlahirKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("NIPD");

        txtnipd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnipd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnipdActionPerformed(evt);
            }
        });
        txtnipd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnipdKeyPressed(evt);
            }
        });

        cbkelamin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laki-Laki", "Perempuan" }));
        cbkelamin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkelaminActionPerformed(evt);
            }
        });
        cbkelamin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbkelaminKeyPressed(evt);
            }
        });

        txtnisn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnisn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnisnActionPerformed(evt);
            }
        });
        txtnisn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnisnKeyPressed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("NISN");

        jLabel24.setText("Kelas");

        txtnama.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamaActionPerformed(evt);
            }
        });
        txtnama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnamaKeyPressed(evt);
            }
        });

        jLabel21.setText("Tanggal Lahir");

        jLabel20.setText("Tempat Lahir");

        keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        keluar.setText("Batal");
        keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluarActionPerformed(evt);
            }
        });

        simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        simpan.setText("Simpan");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jtanggal1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txttempatlahir, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cbkelamin, javax.swing.GroupLayout.Alignment.LEADING, 0, 1088, Short.MAX_VALUE)
                        .addComponent(cbkelas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtnama, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtnisn, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtnipd, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(0, 1, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(395, 395, 395)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtanggal1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keluar)
                    .addComponent(simpan)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel4)
                    .addGap(0, 0, 0)
                    .addComponent(txtnipd, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel19)
                    .addGap(0, 0, 0)
                    .addComponent(txtnisn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel23)
                    .addGap(0, 0, 0)
                    .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel24)
                    .addGap(0, 0, 0)
                    .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel22)
                    .addGap(0, 0, 0)
                    .addComponent(cbkelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel20)
                    .addGap(0, 0, 0)
                    .addComponent(txttempatlahir, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(103, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout tambahSiswaLayout = new javax.swing.GroupLayout(tambahSiswa);
        tambahSiswa.setLayout(tambahSiswaLayout);
        tambahSiswaLayout.setHorizontalGroup(
            tambahSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahSiswaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(tambahSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tambahSiswaLayout.createSequentialGroup()
                        .addComponent(jlabel)
                        .addGap(873, 887, Short.MAX_VALUE))
                    .addGroup(tambahSiswaLayout.createSequentialGroup()
                        .addGroup(tambahSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtlabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        tambahSiswaLayout.setVerticalGroup(
            tambahSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahSiswaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(tambahSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlabel)
                    .addGroup(tambahSiswaLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainpanel.add(tambahSiswa, "card2");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Edit Data Siswa");

        txtlabel2.setEditable(false);
        txtlabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtlabel2.setCaretColor(java.awt.Color.lightGray);
        txtlabel2.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtlabel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlabel2ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setAutoscrolls(true);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setText("Nama Siswa");

        txttempatlahir1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txttempatlahir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttempatlahir1ActionPerformed(evt);
            }
        });
        txttempatlahir1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttempatlahir1KeyPressed(evt);
            }
        });

        cbkelamin1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laki-Laki", "Perempuan" }));
        cbkelamin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkelamin1ActionPerformed(evt);
            }
        });
        cbkelamin1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbkelamin1KeyPressed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("Tanggal Lahir");

        txtnisn1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnisn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnisn1ActionPerformed(evt);
            }
        });
        txtnisn1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnisn1KeyPressed(evt);
            }
        });

        txtnipd1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnipd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnipd1ActionPerformed(evt);
            }
        });
        txtnipd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnipd1KeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("NIPD");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("Tempat Lahir");

        txtnama1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnama1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnama1ActionPerformed(evt);
            }
        });
        txtnama1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnama1KeyPressed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setText("NISN");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel30.setText("Kelas");

        cbkelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkelas1ActionPerformed(evt);
            }
        });
        cbkelas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbkelas1KeyPressed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("Jenis Kelamin");

        keluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        keluar1.setText("Batal");
        keluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluar1ActionPerformed(evt);
            }
        });

        simpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        simpan1.setText("Simpan");
        simpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan1ActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel31.setText("Status");

        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif", "Tidak Aktif" }));
        cbstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbkelamin1, 0, 1116, Short.MAX_VALUE)
                    .addComponent(txttempatlahir1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jtanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(simpan1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(keluar1)))
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtnipd1)
                        .addComponent(txtnisn1)
                        .addComponent(txtnama1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(0, 1025, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addGap(0, 0, 0)
                .addComponent(cbkelamin1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel26)
                .addGap(0, 0, 0)
                .addComponent(txttempatlahir1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keluar1)
                    .addComponent(simpan1))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel6)
                    .addGap(0, 0, 0)
                    .addComponent(txtnipd1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel25)
                    .addGap(0, 0, 0)
                    .addComponent(txtnisn1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel29)
                    .addGap(0, 0, 0)
                    .addComponent(txtnama1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(316, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout editSiswaLayout = new javax.swing.GroupLayout(editSiswa);
        editSiswa.setLayout(editSiswaLayout);
        editSiswaLayout.setHorizontalGroup(
            editSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editSiswaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(editSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editSiswaLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 923, Short.MAX_VALUE))
                    .addGroup(editSiswaLayout.createSequentialGroup()
                        .addGroup(editSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtlabel2))
                        .addContainerGap())))
        );
        editSiswaLayout.setVerticalGroup(
            editSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editSiswaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(editSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(editSiswaLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainpanel.add(editSiswa, "card2");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtnipd2.setEditable(false);
        txtnipd2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnipd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnipd2ActionPerformed(evt);
            }
        });
        txtnipd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnipd2KeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("NISN");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Nama Siswa");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("NIPD");

        simpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        simpan2.setText("Simpan");
        simpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan2ActionPerformed(evt);
            }
        });

        batal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        batal1.setText("Batal");
        batal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batal1ActionPerformed(evt);
            }
        });

        txtnisn2.setText("Masukan NISN");
        txtnisn2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnisn2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtnisn2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtnisn2FocusLost(evt);
            }
        });
        txtnisn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnisn2ActionPerformed(evt);
            }
        });
        txtnisn2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnisn2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnisn2KeyReleased(evt);
            }
        });

        namasiswa.setEditable(false);
        namasiswa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        namasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namasiswaActionPerformed(evt);
            }
        });
        namasiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                namasiswaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnipd2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(simpan2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(batal1)))
                        .addGap(0, 934, Short.MAX_VALUE))
                    .addComponent(txtnisn2)
                    .addComponent(namasiswa))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnipd2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnisn2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(batal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(simpan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Status Penerima Bantuan");

        label3.setEditable(false);
        label3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        label3.setCaretColor(java.awt.Color.lightGray);
        label3.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        label3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                label3ActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtdaftar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtdaftar.setText("Daftar Siswa Penerima Bantuan");
        txtdaftar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtdaftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdaftarActionPerformed(evt);
            }
        });

        tabelbantuan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tabelbantuan);

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

        hapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hapus.png"))); // NOI18N
        hapus1.setText("Hapus");
        hapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapus1ActionPerformed(evt);
            }
        });

        batal2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        batal2.setText("Batal");
        batal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batal2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtdaftar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapus1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(batal2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1120, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hapus1)
                        .addComponent(batal2))
                    .addComponent(txtdaftar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtcari1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout dataPenerimaLayout = new javax.swing.GroupLayout(dataPenerima);
        dataPenerima.setLayout(dataPenerimaLayout);
        dataPenerimaLayout.setHorizontalGroup(
            dataPenerimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPenerimaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(dataPenerimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label3)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dataPenerimaLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        dataPenerimaLayout.setVerticalGroup(
            dataPenerimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPenerimaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dataPenerimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(dataPenerimaLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(dataPenerima, "card2");

        kelulusan.setLayout(new java.awt.CardLayout());

        mainpanel1.setLayout(new java.awt.CardLayout());

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N

        cbkelas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkelas2ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Murid Kelas");

        tabelsiswa1.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelsiswa1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsiswa1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelsiswa1);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Kelas     :");

        lulus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lulus.png"))); // NOI18N
        lulus.setText("Naik Kelas");
        lulus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lulusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1116, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lulus, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(cbkelas2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbkelas2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel11))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lulus, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setText("Data Kelulusan");

        txtlabel3.setEditable(false);
        txtlabel3.setBackground(new java.awt.Color(255, 255, 255));
        txtlabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtlabel3.setCaretColor(java.awt.Color.lightGray);
        txtlabel3.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtlabel3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlabel3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cetakabsen1Layout = new javax.swing.GroupLayout(cetakabsen1);
        cetakabsen1.setLayout(cetakabsen1Layout);
        cetakabsen1Layout.setHorizontalGroup(
            cetakabsen1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cetakabsen1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(cetakabsen1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtlabel3)
                    .addGroup(cetakabsen1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 958, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        cetakabsen1Layout.setVerticalGroup(
            cetakabsen1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cetakabsen1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(cetakabsen1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(cetakabsen1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel1.add(cetakabsen1, "card2");

        kelulusan.add(mainpanel1, "card2");

        mainpanel.add(kelulusan, "card3");

        add(mainpanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void tabelsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswaMouseClicked
        int selectedRowIndex = tabelsiswa.getSelectedRow();
        int selectedModelRowIndex = tabelsiswa.convertRowIndexToModel(selectedRowIndex);
        if (selectedModelRowIndex != -1) { // Pastikan indeks baris valid
            DefaultTableModel model = (DefaultTableModel) tabelsiswa.getModel();
            String a = model.getValueAt(selectedModelRowIndex, 2).toString();
            String b = model.getValueAt(selectedModelRowIndex, 3).toString();
            String c = model.getValueAt(selectedModelRowIndex, 1).toString();
            String d = model.getValueAt(selectedModelRowIndex, 4).toString();
            String e = model.getValueAt(selectedModelRowIndex, 5).toString();
            String f = model.getValueAt(selectedModelRowIndex, 6).toString();
            String g = model.getValueAt(selectedModelRowIndex, 7).toString();
            String h = model.getValueAt(selectedModelRowIndex, 8).toString();

            txtnipd1.setText(a);
            txtnisn1.setText(b);
            txtnama1.setText(c);
            cbkelas1.setSelectedItem(d);
            cbstatus.setSelectedItem(e);
            cbkelamin1.setSelectedItem(f);
            txttempatlahir1.setText(g);

            if ("L".equals(f)) {
                cbkelamin1.setSelectedItem("Laki-Laki");
            } else if ("P".equals(f)) {
                cbkelamin1.setSelectedItem("Perempuan");
            } else {
                cbkelamin1.setSelectedItem("");  // Jika tidak ada kecocokan, kosongkan
            }

            // Cek apakah nilai g (tanggal) tidak kosong atau null
            if (h != null && !h.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date tanggal = sdf.parse(h);
                    jtanggal.setDate(tanggal);
                } catch (ParseException ex) {
                    ex.printStackTrace(); // Tangani error parsing tanggal
                    JOptionPane.showMessageDialog(null, "Format tanggal tidak valid.");
                }
            }
        }
        setTableNonEditable();
    }//GEN-LAST:event_tabelsiswaMouseClicked
    private void setTableNonEditable() {
        for (int i = 0; i < tabelsiswa.getColumnCount(); i++) {
            Class<?> col_class = tabelsiswa.getColumnClass(i);
            tabelsiswa.setDefaultEditor(col_class, null); // Menetapkan editor default ke null untuk semua kolom
        }
    }
    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyPressed

    private void txtlabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabelActionPerformed

    private void txtcariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariMouseClicked

    }//GEN-LAST:event_txtcariMouseClicked

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyTyped

    @SuppressWarnings("unchecked")
    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        TableRowSorter<DefaultTableModel> sorter;
        if (tabelsiswa.getRowSorter() instanceof TableRowSorter) {
            sorter = (TableRowSorter<DefaultTableModel>) tabelsiswa.getRowSorter();
        } else {
            DefaultTableModel model = (DefaultTableModel) tabelsiswa.getModel();
            sorter = new TableRowSorter<>(model);
            tabelsiswa.setRowSorter(sorter);
        }
        // Terapkan filter pencarian
        try {
            String searchText = txtcari.getText();
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive filter
        } catch (PatternSyntaxException e) {
            sorter.setRowFilter(null); // Reset filter jika terjadi kesalahan pada regex
        }
    }//GEN-LAST:event_txtcariKeyReleased

    private void txtcariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariFocusGained
        String Pencarian = txtcari.getText();
        if (Pencarian.equals("Pencarian")) {
            txtcari.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariFocusGained

    private void txtcariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariFocusLost
        String username = txtcari.getText();
        if (username.equals("") || username.equals("Pencarian")) {
            txtcari.setText("Pencarian");
        }
    }//GEN-LAST:event_txtcariFocusLost

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void txtcariMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariMouseExited

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(tambahSiswa);
        mainpanel.repaint();
        mainpanel.revalidate();
        tabelsiswa.clearSelection();
        kosong();
    }//GEN-LAST:event_tambahActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(editSiswa);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_editActionPerformed

    private void batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalActionPerformed
        try {
            tabelsiswa.clearSelection();
            tabelsiswa.setRowSorter(null);
            datatable();
            txtcari.setText("Pencarian");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saat memuat ulang data: " + e.getMessage());
        }
    }//GEN-LAST:event_batalActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        int barisTerpilih = tabelsiswa.getSelectedRow();

        if (barisTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            // Ambil nama siswa dari kolom nama di tabel
            String namaSiswa = tabelsiswa.getValueAt(barisTerpilih, 1).toString(); // Asumsikan kolom ke-1 adalah nama
            String idSiswa = tabelsiswa.getValueAt(barisTerpilih, 0).toString(); // Asumsikan kolom ke-0 adalah id_siswa

            // Tampilkan dialog konfirmasi
            int konfirmasi = JOptionPane.showConfirmDialog(
                    this,
                    "<html>Apakah Anda akan menghapus data siswa <b>" + namaSiswa + "</b> ?</html>",
                    "Konfirmasi Dialog",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    // Query SQL untuk menghapus data berdasarkan id_siswa
                    String query = "DELETE FROM siswa WHERE nama_siswa = ?";
                    try (Connection conn = SQLConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(query)) {

                        pst.setString(1, namaSiswa);
                        int rowsAffected = pst.executeUpdate();

                        if (rowsAffected > 0) {
                            Siswa.showSuccessDialog("Data berhasil dihapus");
                            // Refresh tabel dan kosongkan form
                            datatable();
                            kosong();
                            tambah.setVisible(true);
                            txtcari.setText("Pencarian");
                        } else {
                            JOptionPane.showMessageDialog(this, "Data tidak ditemukan atau gagal dihapus", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_hapusActionPerformed

    private void txtnisn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnisn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnisn1ActionPerformed

    private void txtnisn1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnisn1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnisn1KeyPressed

    private void txttempatlahir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttempatlahir1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttempatlahir1ActionPerformed

    private void txttempatlahir1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttempatlahir1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttempatlahir1KeyPressed

    private void cbkelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelas1ActionPerformed

    private void cbkelas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbkelas1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelas1KeyPressed

    private void simpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan1ActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (jtanggal.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Tanggal harus diisi.");
            return;
        }

        String date = sdf.format(jtanggal.getDate());

        if (txtnipd1.getText().isEmpty()
                || txtnisn1.getText().isEmpty()
                || txtnama1.getText().isEmpty()
                || cbkelas1.getSelectedItem() == null
                || cbstatus.getSelectedItem() == null
                || cbkelamin1.getSelectedItem() == null
                || txttempatlahir1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua data harus diisi.");
            return;
        }

        try {
            String querySelect = "SELECT id_siswa FROM siswa WHERE nisn = ?";
            int idSiswa = -1;

            try (PreparedStatement psSelect = SQLConnection.getConnection().prepareStatement(querySelect)) {
                psSelect.setString(1, txtnisn1.getText());
                try (ResultSet rs = psSelect.executeQuery()) {
                    if (rs.next()) {
                        idSiswa = rs.getInt("id_siswa");
                    } else {
                        Siswa.showErrorDialog("NISN Tidak Dapat Diubah");
                        return;
                    }
                }
            }

            System.out.println("ID Siswa ditemukan: " + idSiswa);

            // Konversi jenis kelamin
            String kelamin;
            if (cbkelamin1.getSelectedItem().toString().trim().equalsIgnoreCase("Laki-Laki")) {
                kelamin = "L";
            } else {
                kelamin = "P";
            }

            System.out.println("Kelamin yang akan disimpan: " + kelamin);

            String queryUpdate = "UPDATE siswa SET nisn=?, nipd=?, nama_siswa=?, kelas=?, status=?, kelamin=?, tempat_lahir=?, tanggal_lahir=? WHERE id_siswa=?";

            try (PreparedStatement psUpdate = SQLConnection.getConnection().prepareStatement(queryUpdate)) {
                psUpdate.setString(1, txtnisn1.getText());
                psUpdate.setString(2, txtnipd1.getText());
                psUpdate.setString(3, txtnama1.getText());
                psUpdate.setString(4, cbkelas1.getSelectedItem().toString());
                psUpdate.setString(5, cbstatus.getSelectedItem().toString());
                psUpdate.setString(6, kelamin);
                psUpdate.setString(7, txttempatlahir1.getText());
                psUpdate.setString(8, date);
                psUpdate.setInt(9, idSiswa);

                int rowsUpdated = psUpdate.executeUpdate();

                if (rowsUpdated > 0) {
                    Siswa.showSuccessDialog("Data berhasil diubah.");
                } else {
                    JOptionPane.showMessageDialog(null, "Data gagal diubah. Tidak ada perubahan.");
                }
            }

            kosong1();
            datatable();
            keluar1ActionPerformed(null);
            tambah.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
        }
    }//GEN-LAST:event_simpan1ActionPerformed

    private void keluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluar1ActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataSiswa);
        mainpanel.repaint();
        mainpanel.revalidate();
        bantuan.setVisible(true);
        excel.setVisible(true);
        txtlulus.setVisible(true);
    }//GEN-LAST:event_keluar1ActionPerformed

    private void cbkelamin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelamin1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelamin1ActionPerformed

    private void cbkelamin1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbkelamin1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelamin1KeyPressed

    private void txtnama1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnama1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnama1ActionPerformed

    private void txtnama1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnama1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnama1KeyPressed

    private void jtanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtanggal1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtanggal1KeyPressed

    private void txtnipd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnipd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnipd1ActionPerformed

    private void txtnipd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnipd1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnipd1KeyPressed

    private void txtlabel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel2ActionPerformed

    private void jtanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtanggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtanggalKeyPressed

    private void txtnamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cbkelas.requestFocus();
            cbkelas.showPopup();
        }
    }//GEN-LAST:event_txtnamaKeyPressed

    private void txtnamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamaActionPerformed

    private void cbkelaminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbkelaminKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txttempatlahir.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelaminKeyPressed

    private void cbkelaminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelaminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelaminActionPerformed

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataSiswa);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_keluarActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(jtanggal1.getDate());
        try {
            String kelamin;
            if (cbkelamin.getSelectedItem().toString().trim().equalsIgnoreCase("Laki-Laki")) {
                kelamin = "L";
            } else {
                kelamin = "P";
            }

            // Pastikan 'status' memiliki nilai
            String status = "Aktif";
            String status_lulus = "Belum lulus";

            // Query untuk menyimpan data siswa
            String query = "INSERT INTO siswa (nipd, nisn, nama_siswa, kelas, status, kelamin, tempat_lahir, tanggal_lahir, status_bantuan, status_lulus) VALUES (?,?,?,?,?,?,?,?,?,?)";

            // Eksekusi query dengan parameter
            Boolean insert = SQLConnection.doPreparedUpdate(
                    query,
                    txtnipd.getText(),
                    txtnisn.getText(),
                    txtnama.getText(),
                    cbkelas.getSelectedItem().toString(),
                    status, // Tambahkan nilai 'status' di sini
                    kelamin,
                    txttempatlahir.getText(),
                    date,
                    "Tidak", // Nilai default untuk kolom status_bantuan
                    status_lulus
            );

            if (Boolean.TRUE.equals(insert)) {
                Siswa.showSuccessDialog("Data berhasil disimpan");
                kosong();
                datatable();
                keluarActionPerformed(null);
            }
        } catch (SQLException e) {
            Siswa.showErrorDialog("Data gagal disimpan: " + e.getMessage());
        }
    }//GEN-LAST:event_simpanActionPerformed

    private void cbkelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbkelasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cbkelamin.requestFocus();
            cbkelamin.showPopup();
        }// TODO add your handling code here:
    }//GEN-LAST:event_cbkelasKeyPressed

    private void cbkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelasActionPerformed

    private void txttempatlahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttempatlahirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttempatlahirKeyPressed

    private void txttempatlahirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttempatlahirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttempatlahirActionPerformed

    private void txtnisnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnisnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtnama.requestFocus();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtnisnKeyPressed

    private void txtnisnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnisnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnisnActionPerformed

    private void txtnipdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnipdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnipdActionPerformed

    private void txtnipdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnipdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtnisn.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtnipdKeyPressed

    private void txtlabel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel1ActionPerformed

    private void bantuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bantuanActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataPenerima);
        mainpanel.repaint();
        mainpanel.revalidate();
        tabelbantuan();
        hapus1.setVisible(false);
        batal2.setVisible(false);
    }//GEN-LAST:event_bantuanActionPerformed

    private void txtnipd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnipd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnipd2ActionPerformed

    private void txtnipd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnipd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnipd2KeyPressed

    private void simpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan2ActionPerformed
// Ambil data dari JTextField
        String nipd = txtnipd2.getText(); // txtId adalah JTextField
        String namaSiswa = namasiswa.getText(); // txtNamaSiswa adalah JTextField

// Validasi input
        if (nipd.isEmpty() || namaSiswa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Field ID Siswa dan Nama Siswa harus diisi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

// Tampilkan dialog konfirmasi
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda ingin menambahkan siswa \"" + namaSiswa + "\" sebagai penerima bantuan?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Query untuk mengupdate data siswa
            String query = "UPDATE siswa SET status_bantuan = ? WHERE nipd = ? AND nama_siswa = ?";

            try {
                // Menjalankan query menggunakan SQLConnection.doPreparedUpdate
                Boolean update = SQLConnection.doPreparedUpdate(query, "Ya", nipd, namaSiswa);

                if (update) {
                    Siswa.showSuccessDialog("Data berhasil ditambah");
                    txtnipd2.setText("");
                    txtnisn2.setText("");
                    namasiswa.setText("");
                } else {
                    Siswa.showErrorDialog("Data gagal ditambah. Pastikan ID Siswa dan Nama Siswa sesuai");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Jika pengguna memilih "Tidak", batalkan proses
            JOptionPane.showMessageDialog(this, "Proses pembaruan dibatalkan.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        tabelbantuan();
    }//GEN-LAST:event_simpan2ActionPerformed

    private void batal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batal1ActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataSiswa);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_batal1ActionPerformed

    private void label3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_label3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_label3ActionPerformed

    private void txtnisn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnisn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnisn2ActionPerformed

    private void txtnisn2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnisn2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnisn2KeyPressed

    private void namasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namasiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namasiswaActionPerformed

    private void namasiswaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_namasiswaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_namasiswaKeyPressed

    private void txtnisn2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnisn2KeyReleased
        // Ambil NISN dari txtNisn2
        String nisn = txtnisn2.getText();

        // Jika NISN kosong, kosongkan txtId2 dan txtNamaSiswa
        if (nisn.isEmpty()) {
            txtnipd2.setText("");
            namasiswa.setText("");
            return;
        }

        // Query untuk mencari data berdasarkan NISN
        String query = "SELECT nipd, nama_siswa FROM siswa WHERE nisn = ?";

        try {
            // Jalankan query menggunakan SQLConnection
            ResultSet rs = SQLConnection.doPreparedQuery(query, nisn);

            if (rs.next()) {
                // Ambil data id_siswa dan nama_siswa
                String nipd = rs.getString("nipd");
                String namaSiswa = rs.getString("nama_siswa");

                // Set nilai ke JTextField txtId2 dan txtNamaSiswa2
                txtnipd2.setText(nipd);
                namasiswa.setText(namaSiswa);
            } else {
                // Jika tidak ditemukan, kosongkan txtId2 dan txtNamaSiswa2
                txtnipd2.setText("");
                namasiswa.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mencari data: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtnisn2KeyReleased

    private void txtnisn2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnisn2FocusGained
        String nisn = txtnisn2.getText();
        if (nisn.equals("Masukan NISN")) {
            txtnisn2.setText("");
        }
    }//GEN-LAST:event_txtnisn2FocusGained

    private void txtnisn2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnisn2FocusLost
        String nisn = txtnisn2.getText();
        if (nisn.equals("") || nisn.equals("Masukan NISN")) {
            txtnisn2.setText("Masukan NISN");
        }
    }//GEN-LAST:event_txtnisn2FocusLost

    private void txtdaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdaftarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdaftarActionPerformed

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
        DefaultTableModel obj = (DefaultTableModel) tabelbantuan.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        tabelbantuan.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(txtcari1.getText()));
        obj1.setRowFilter(RowFilter.regexFilter("(?i)" + txtcari1.getText()));       // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1KeyReleased

    private void txtcari1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1KeyTyped

    private void hapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapus1ActionPerformed
        int selectedRow = tabelbantuan.getSelectedRow(); // Ambil baris yang dipilih
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diubah status bantuan terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ambil NISN dari baris yang dipilih
        String nisn = tabelbantuan.getValueAt(selectedRow, 0).toString();
        String namaSiswa = tabelbantuan.getValueAt(selectedRow, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus status bantuan pada siswa " + namaSiswa + " ?",
                "Konfirmasi Ubah Status",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Query untuk memperbarui status_bantuan menjadi 'Tidak'
                String query = "UPDATE siswa SET status_bantuan = 'Tidak' WHERE nisn = ? AND status_bantuan = 'Ya'";
                boolean update = SQLConnection.doPreparedUpdate(query, nisn);

                if (update) {
                    Siswa.showSuccessDialog("Data Berhasil Dihapus");
                    tabelbantuan(); // Refresh tabel bantuan
                } else {
                    Siswa.showErrorDialog("Gagal Mengubah Data");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_hapus1ActionPerformed

    private void batal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batal2ActionPerformed
        tabelbantuan();
        hapus1.setVisible(false);
        batal2.setVisible(false);
    }//GEN-LAST:event_batal2ActionPerformed

    private void excelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelActionPerformed
        try {
            // Menampilkan dialog untuk memilih file dengan JnaFileChooser
            File file = showJnaFileChooser();
            if (file != null) {
                System.out.println("File yang dipilih: " + file.getAbsolutePath());
                uploadExcelData(file); // Proses file Excel (sesuaikan dengan kebutuhan Anda)
            } else {
                System.out.println("Tidak ada file yang dipilih.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error saat membuka file chooser: " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_excelActionPerformed

    private void txtlulusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlulusActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(kelulusan);
        mainpanel.repaint();
        mainpanel.revalidate();

        String kelas = (String) cbkelas2.getSelectedItem();
        datatable1(kelas);
    }//GEN-LAST:event_txtlulusActionPerformed

    private void cbkelas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelas2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelas2ActionPerformed

    private void tabelsiswa1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswa1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelsiswa1MouseClicked

    private void lulusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lulusActionPerformed
        try {
            // Ambil nilai kelas yang dipilih
            String kelas = (String) cbkelas2.getSelectedItem();

            // Validasi apakah kelas telah dipilih
            if (kelas == null || kelas.equals("-- Pilih Kelas --")) {
                return;
            }

            // Tampilkan dialog konfirmasi
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Apakah Anda yakin ingin meluluskan siswa dari kelas " + kelas + "?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );

            // Jika pengguna memilih "Tidak", batalkan proses
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Dapatkan koneksi ke database
            Connection c = SQLConnection.getConnection();

            // Tentukan kelas berikutnya
            String kelasBaru = null;
            String statusLulus = null;

            switch (kelas) {
                case "1":
                    kelasBaru = "2";
                    break;
                case "2":
                    kelasBaru = "3";
                    break;
                case "3":
                    kelasBaru = "4";
                    break;
                case "4":
                    kelasBaru = "5";
                    break;
                case "5":
                    kelasBaru = "6";
                    break;
                case "6":
                    statusLulus = "Lulus";
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Kelas tidak valid!");
                    return;
            }

            // Update kelas atau status lulus di database
            String sql;
            if (statusLulus == null) {
                // Jika belum lulus, hanya update kelas
                sql = "UPDATE siswa SET kelas = ? WHERE kelas = ?";
            } else {
                // Jika sudah lulus, update status_lulus
                sql = "UPDATE siswa SET status_lulus = ? WHERE kelas = ?";
            }

            PreparedStatement ps = c.prepareStatement(sql);
            if (statusLulus == null) {
                ps.setString(1, kelasBaru);
                ps.setString(2, kelas);
            } else {
                ps.setString(1, statusLulus);
                ps.setString(2, kelas);
            }

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                String message = (statusLulus == null)
                        ? "Semua siswa dari kelas " + kelas + " telah dipindahkan ke " + kelasBaru + "."
                        : "Semua siswa dari kelas " + kelas + " telah dinyatakan lulus.";
                Siswa.showSuccessDialog(message);
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada data yang diperbarui. Pastikan data sudah benar.");
            }

            // Refresh tabel untuk menampilkan data terbaru
            datatable1(kelas);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
        }
    }//GEN-LAST:event_lulusActionPerformed

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel7MouseClicked

    private void txtlabel3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel3ActionPerformed

    private void cbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbstatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbstatusActionPerformed

    private File showJnaFileChooser() {
        // Membuat instance JnaFileChooser
        JnaFileChooser fileChooser = new JnaFileChooser();
        fileChooser.setTitle("Pilih File Excel");
        fileChooser.addFilter("File Excel", "xls", "xlsx");

        // Menampilkan dialog file chooser
        boolean isFileSelected = fileChooser.showOpenDialog(null);
        if (isFileSelected) {
            return fileChooser.getSelectedFile(); // Kembalikan file yang dipilih
        }
        return null; // Jika tidak ada file yang dipilih
    }

    private void uploadExcelData(File file) {
        String gifPath = "/img/loading1.gif";
        URL gifURL = getClass().getResource(gifPath);
        if (gifURL == null) {
            System.err.println("GIF loading tidak ditemukan: " + gifPath);
            return;
        }

        // Membuat ImageIcon untuk GIF
        ImageIcon loadingIcon = new ImageIcon(gifURL);
        JLabel loadingLabel = new JLabel("", loadingIcon, JLabel.CENTER);

        // Membuat label untuk teks "Loading..."
        JLabel textLabel = new JLabel("Loading...", JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Font lebih besar dan tebal
        textLabel.setForeground(Color.WHITE); // Warna putih agar lebih jelas
//        textLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Tambahkan border hitam agar kontras
        textLabel.setOpaque(false);
        textLabel.setBackground(new Color(0, 0, 0, 150)); // Latar belakang semi-transparan agar terlihat jelas

        // Membuat panel transparan untuk loading
        JPanel loadingPanel = new JPanel(new BorderLayout());
        loadingPanel.setOpaque(false);
        loadingPanel.add(loadingLabel, BorderLayout.CENTER);
        loadingPanel.add(textLabel, BorderLayout.SOUTH);

        // Membuat dialog loading dengan background transparan
        JDialog loadingDialog = new JDialog();
        loadingDialog.setUndecorated(true);
        loadingDialog.setModal(false);
        loadingDialog.setLayout(new BorderLayout());
        loadingDialog.setBackground(new Color(0, 0, 0, 0)); // Transparan
        loadingDialog.add(loadingPanel, BorderLayout.CENTER);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(null);
        loadingDialog.setAlwaysOnTop(true); // Pastikan loading dialog selalu di atas

        // Membuat efek redup di layar dengan JWindow
        JWindow dimBackground = new JWindow();
        dimBackground.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        dimBackground.setBackground(new Color(0, 0, 0, 100)); // Warna hitam transparan
        dimBackground.setOpacity(0.5f); // Atur transparansi redup
        dimBackground.setAlwaysOnTop(true); // Pastikan layar redup tetap di atas semua UI

        // SwingWorker untuk menangani proses upload di background
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                SwingUtilities.invokeLater(() -> {
                    dimBackground.setVisible(true); // Tampilkan layar redup terlebih dahulu
                    loadingDialog.setVisible(true); // Pastikan loading dialog muncul setelah layar redup
                });

                try (FileInputStream fis = new FileInputStream(file)) {
                    XSSFWorkbook workbook = new XSSFWorkbook(fis);
                    XSSFSheet sheet = workbook.getSheetAt(0);

                    for (org.apache.poi.ss.usermodel.Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            continue;
                        }

                        String nipd = getCellValue(row.getCell(0));
                        String nisn = getCellValue(row.getCell(1));
                        String nama_siswa = getCellValue(row.getCell(2));
                        String kelas = getCellValue(row.getCell(3));
                        String kelamin = getCellValue(row.getCell(4));
                        String tempat_lahir = getCellValue(row.getCell(5));
                        String tanggal_lahir = getCellValue(row.getCell(6));

                        if (tanggal_lahir.isEmpty()) {
                            tanggal_lahir = null;
                        }

                        saveToDatabase(nipd, nisn, nama_siswa, kelas, kelamin, tempat_lahir, tanggal_lahir);
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    loadingDialog.dispose(); // Tutup loading dialog lebih dulu
                    dimBackground.dispose(); // Hapus layar redup setelahnya
                    Siswa.showSuccessDialog("Data berhasil diupload");
                });
            }
        };

        // Jalankan proses upload di background
        SwingUtilities.invokeLater(() -> {
            dimBackground.setVisible(true); // Tampilkan layar redup
            loadingDialog.setVisible(true); // Tampilkan loading dialog setelahnya
            worker.execute();
        });
    }

    private String getCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) {
            return "";
        }

        // Untuk POI versi lama, gunakan integer untuk tipe data
        int cellType = cell.getCellType();  // mendapatkan tipe sel dalam bentuk integer

        switch (cellType) {
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:  // Tipe String
                return cell.getStringCellValue();
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:  // Tipe Numeric
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    // Jika itu adalah tanggal, kembalikan dalam format 'yyyy-MM-dd'
                    java.util.Date date = cell.getDateCellValue();
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(date);
                } else {
                    // Jika itu adalah angka biasa, periksa apakah integer atau desimal
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        // Jika bilangan bulat, kembalikan tanpa .0
                        return String.valueOf((long) numericValue);
                    } else {
                        // Jika bilangan desimal, kembalikan apa adanya
                        return String.valueOf(numericValue);
                    }
                }
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:  // Tipe Boolean
                return String.valueOf(cell.getBooleanCellValue());
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA:  // Tipe Formula
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private void saveToDatabase(String nipd, String nisn, String nama_siswa, String kelas, String kelamin, String tempat_lahir, String tanggal_lahir) {
        // Query untuk menyimpan data ke dalam tabel siswa
        String query = "INSERT INTO siswa (nipd, nisn, nama_siswa, kelas, kelamin, tempat_lahir, tanggal_lahir, status_bantuan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Setel parameter untuk query SQL
            pstmt.setString(1, nipd);  // Menggunakan setString untuk String
            pstmt.setString(2, nisn);  // Menggunakan setString untuk String
            pstmt.setString(3, nama_siswa);  // Menggunakan setString untuk String
            pstmt.setString(4, kelas);  // Menggunakan setString untuk String
            pstmt.setString(5, kelamin);  // Menggunakan setString untuk String
            pstmt.setString(6, tempat_lahir);  // Menggunakan setString untuk String
            pstmt.setString(7, tanggal_lahir);  // Menggunakan setString untuk String (kalau tanggal dalam format String)

            // Set nilai untuk status_bantuan, yang selalu "Tidak"
            pstmt.setString(8, "Tidak");  // Masukkan "Tidak" ke dalam kolom status_bantuan

            // Eksekusi query
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                datatable();
            } else {
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Siswa.showErrorDialog("Gagal menyimpan data ke database: " + e.getMessage());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bantuan;
    private javax.swing.JButton batal;
    private javax.swing.JButton batal1;
    private javax.swing.JButton batal2;
    private javax.swing.JComboBox<String> cbkelamin;
    private javax.swing.JComboBox<String> cbkelamin1;
    private javax.swing.JComboBox<String> cbkelas;
    private javax.swing.JComboBox<String> cbkelas1;
    private javax.swing.JComboBox<String> cbkelas2;
    private javax.swing.JComboBox<String> cbstatus;
    private javax.swing.JPanel cetakabsen1;
    private javax.swing.JPanel dataPenerima;
    private javax.swing.JPanel dataSiswa;
    private javax.swing.JButton edit;
    private javax.swing.JPanel editSiswa;
    private javax.swing.JButton excel;
    private javax.swing.JButton hapus;
    private javax.swing.JButton hapus1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel jlabel;
    private com.toedter.calendar.JDateChooser jtanggal;
    private com.toedter.calendar.JDateChooser jtanggal1;
    private javax.swing.JButton keluar;
    private javax.swing.JButton keluar1;
    private javax.swing.JPanel kelulusan;
    private javax.swing.JTextField label3;
    private javax.swing.JButton lulus;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel mainpanel1;
    private javax.swing.JTextField namasiswa;
    private javax.swing.JButton simpan;
    private javax.swing.JButton simpan1;
    private javax.swing.JButton simpan2;
    private javax.swing.JTable tabelbantuan;
    private javax.swing.JTable tabelsiswa;
    private javax.swing.JTable tabelsiswa1;
    private javax.swing.JButton tambah;
    private javax.swing.JPanel tambahSiswa;
    private javax.swing.ButtonGroup txtbutton;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtcari1;
    private javax.swing.JTextField txtdaftar;
    private javax.swing.JTextField txtlabel;
    private javax.swing.JTextField txtlabel1;
    private javax.swing.JTextField txtlabel2;
    private javax.swing.JTextField txtlabel3;
    private javax.swing.JButton txtlulus;
    private javax.swing.JTextField txtnama;
    private javax.swing.JTextField txtnama1;
    private javax.swing.JTextField txtnipd;
    private javax.swing.JTextField txtnipd1;
    private javax.swing.JTextField txtnipd2;
    private javax.swing.JTextField txtnisn;
    private javax.swing.JTextField txtnisn1;
    private javax.swing.JTextField txtnisn2;
    private javax.swing.JTextField txttempatlahir;
    private javax.swing.JTextField txttempatlahir1;
    // End of variables declaration//GEN-END:variables
}
