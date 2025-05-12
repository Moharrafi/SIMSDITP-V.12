package simsditp;

import Database.SQLConnection;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class Kelas extends javax.swing.JPanel {

    private DefaultTableModel tabmode;

    public Kelas() {
        initComponents();
        updateCombo();
        updateCombo1();
        updateCombo2();
        updateCombo3();
        updateCombo4();
        hapus.setVisible(false);
        batal.setVisible(false);
        txtlabel.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtlabel1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtlabel3.setBackground(new java.awt.Color(0, 0, 0, 1));
        jlabel.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcari.setBackground(new java.awt.Color(0, 0, 0, 1));
        tabelguru.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dapatkan baris yang diklik
                int row = tabelguru.getSelectedRow();
                // Tampilkan tombol edit, hapus, dan batal jika ada baris yang dipilih
                if (row != -1) {
                    hapus.setVisible(true);
                    batal.setVisible(true);
                    tambah.setVisible(false); // Sembunyikan tombol tambah
                } else {
                    hapus.setVisible(false);
                    batal.setVisible(false);
                    tambah.setVisible(true); // Tampilkan tombol tambah kembali
                }
            }
        });

        batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Sembunyikan tombol-tombol
                hapus.setVisible(false);
                batal.setVisible(false);
                tabelsiswa.clearSelection();
                tambah.setVisible(true);
            }
        });
        keluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Sembunyikan tombol-tombol
                hapus.setVisible(false);
                batal.setVisible(false);
                tabelsiswa.clearSelection();
                tambah.setVisible(true);
            }
        });
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

    //menampilkan kelas comboBox pada tambah menu
    private void updateCombo() {
        cbkelas.removeAllItems();
        cbkelas.addItem("-- Pilih Kelas --");

        try {
            String query = "SELECT * FROM kelas";
            ResultSet result = SQLConnection.doQuery(query);

            while (result.next()) {
                cbkelas.addItem(result.getString("kelas"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cbkelas.setSelectedItem("-- Pilih Kelas --");
        cbkelas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbkelas.getSelectedItem() != null && cbkelas.getSelectedItem().equals("-- Pilih Kelas --")) {
                    cbkelas.removeItem("-- Pilih Kelas --");
                }
            }
        });
    }

    private void updateCombo4() {
        try {
            String query = "SELECT * FROM kelas";
            ResultSet result = SQLConnection.doQuery(query);

            while (result.next()) {
                cbkelas1.addItem(result.getString("kelas"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCombo1() {
        try {
            String query = "SELECT p.nama_pegawai FROM pegawai p WHERE p.jabatan = ?";
            ResultSet result = SQLConnection.doPreparedQuery(query, "Guru Kelas");

            while (result.next()) {
                cbguru.addItem(result.getString("nama_pegawai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCombo3() {
        try {
            String query = "SELECT p.nama_pegawai FROM pegawai p WHERE p.jabatan = ?";
            ResultSet result = SQLConnection.doPreparedQuery(query, "Guru Kelas");

            while (result.next()) {
                cbguru1.addItem(result.getString("nama_pegawai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    combBox Pilih Kelas

    private void updateCombo2() {
        // Hapus semua item dalam combo box sebelum menambahkan item baru
        jkelas.removeAllItems();

        // Tambahkan placeholder jika diperlukan
        jkelas.addItem("-- Pilih Kelas --");

        try {
            String query = "SELECT DISTINCT kelas FROM guru WHERE kelas IS NOT NULL ORDER BY kelas ASC";
            ResultSet result = SQLConnection.doQuery(query);

            while (result.next()) {
                jkelas.addItem(result.getString("kelas"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        // Setel placeholder sebagai item yang dipilih secara default
        jkelas.setSelectedItem("-- Pilih Kelas --");

        // Tambahkan ActionListener untuk menghapus placeholder saat item lain dipilih
        jkelas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jkelas.getSelectedItem() != null && jkelas.getSelectedItem().equals("-- Pilih Kelas --")) {
                    jkelas.removeItem("-- Pilih Kelas --");
                    updateCombo2();
                }
            }
        });
    }

    void updateTabelGuru() {
        // Bersihkan isi tabel guru sebelum menambahkan data baru
        DefaultTableModel model = (DefaultTableModel) tabelguru.getModel();
        model.setRowCount(0);

        // Ambil nilai yang dipilih dari combo box jkelas
        String kelas = (String) jkelas.getSelectedItem();

        Object[] Baris = {"Wali Kelas"};
        tabmode = new DefaultTableModel(null, Baris);
        // Buat query SQL untuk mengambil data guru berdasarkan kelas yang dipilih
        try {
            String query = "SELECT nama_guru FROM guru WHERE kelas = ?";
            ResultSet result = SQLConnection.doPreparedQuery(query, kelas);

            // Tambahkan data guru ke dalam tabel guru
            while (result.next()) {
                String namaGuru = result.getString("nama_guru");
                model.addRow(new Object[]{namaGuru});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void tampilkanDataSiswa() {
        // Ambil nilai kelas yang dipilih dari combo box jkelas
        String kelas = (String) jkelas.getSelectedItem();

        // Buat query SQL untuk mengambil data siswa berdasarkan kelas yang dipilih
        try {
            String query = "SELECT nipd, nisn, nama_siswa, kelamin, tempat_lahir, tanggal_lahir "
                    + "FROM siswa "
                    + "WHERE kelas = ?";
            ResultSet result = SQLConnection.doPreparedQuery(query, kelas);

            // Bersihkan isi tabel siswa sebelum menambahkan data baru
            DefaultTableModel model = (DefaultTableModel) tabelsiswa.getModel();
            model.setRowCount(0);

            // Tambahkan judul kolom ke tabel siswa
            model.setColumnIdentifiers(new Object[]{"No", "NIPD", "NISN", "Nama Siswa", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir"});

            // Tambahkan data siswa ke dalam tabel siswa
            int no = 1;
            while (result.next()) {
                String nipd = result.getString("nipd");
                String nisn = result.getString("nisn");
                String namaSiswa = result.getString("nama_siswa");
                String jenisKelamin = result.getString("kelamin");
                String tempatLahir = result.getString("tempat_lahir");
                String tanggalLahir = result.getString("tanggal_lahir");

                // Tambahkan baris baru ke dalam tabel siswa
                model.addRow(new Object[]{no++, nipd, nisn, namaSiswa, jenisKelamin, tempatLahir, tanggalLahir});
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

            // Atur lebar kolom dengan lebih fleksibel
            int[] columnWidths = {30, 50, 80, 300, 40, 100, 100}; // Lebar untuk setiap kolom
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
            }

//            tabelsiswa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
        dataKelas = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelsiswa = new javax.swing.JTable();
        txtcari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tambah = new javax.swing.JButton();
        jkelas = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelguru = new javax.swing.JTable();
        hapus = new javax.swing.JButton();
        batal = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtlabel = new javax.swing.JTextField();
        tambahKelas = new javax.swing.JPanel();
        jlabel = new javax.swing.JLabel();
        txtlabel1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        cbkelas = new javax.swing.JComboBox<>();
        cbguru = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        keluar = new javax.swing.JButton();
        simpan = new javax.swing.JButton();
        editKelas = new javax.swing.JPanel();
        jlabel1 = new javax.swing.JLabel();
        txtlabel3 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        cbkelas1 = new javax.swing.JComboBox<>();
        cbguru1 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        keluar2 = new javax.swing.JButton();
        simpan2 = new javax.swing.JButton();

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

        jkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkelasActionPerformed(evt);
            }
        });

        tabelguru.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Nama Guru"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelguru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelguruMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelguru);

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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Wali Kelas");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Murid Kelas");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(batal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tambah)
                            .addComponent(jkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hapus)
                            .addComponent(batal))
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtcari)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                        .addGap(8, 8, 8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Data Kelas");

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

        javax.swing.GroupLayout dataKelasLayout = new javax.swing.GroupLayout(dataKelas);
        dataKelas.setLayout(dataKelasLayout);
        dataKelasLayout.setHorizontalGroup(
            dataKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataKelasLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(dataKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtlabel)
                    .addGroup(dataKelasLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 983, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        dataKelasLayout.setVerticalGroup(
            dataKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataKelasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dataKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(dataKelasLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(dataKelas, "card2");

        jlabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlabel.setText("Data Tambah Kelas");

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

        jLabel22.setText("Kelas");

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

        cbguru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbguruActionPerformed(evt);
            }
        });
        cbguru.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbguruKeyPressed(evt);
            }
        });

        jLabel24.setText("Guru");

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
                    .addComponent(cbguru, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel22)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(keluar)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbkelas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbguru, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keluar)
                    .addComponent(simpan))
                .addContainerGap())
        );

        javax.swing.GroupLayout tambahKelasLayout = new javax.swing.GroupLayout(tambahKelas);
        tambahKelas.setLayout(tambahKelasLayout);
        tambahKelasLayout.setHorizontalGroup(
            tambahKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahKelasLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(tambahKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtlabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tambahKelasLayout.createSequentialGroup()
                        .addComponent(jlabel)
                        .addGap(0, 885, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tambahKelasLayout.setVerticalGroup(
            tambahKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahKelasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(tambahKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlabel)
                    .addGroup(tambahKelasLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainpanel.add(tambahKelas, "card2");

        jlabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlabel1.setText("Data Tambah Kelas");

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

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel23.setText("Kelas");

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

        cbguru1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbguru1ActionPerformed(evt);
            }
        });
        cbguru1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbguru1KeyPressed(evt);
            }
        });

        jLabel31.setText("Guru");

        keluar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        keluar2.setText("Batal");
        keluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluar2ActionPerformed(evt);
            }
        });

        simpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        simpan2.setText("Simpan");
        simpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbguru1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel23)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(simpan2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(keluar2)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbkelas1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbguru1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keluar2)
                    .addComponent(simpan2))
                .addContainerGap())
        );

        javax.swing.GroupLayout editKelasLayout = new javax.swing.GroupLayout(editKelas);
        editKelas.setLayout(editKelasLayout);
        editKelasLayout.setHorizontalGroup(
            editKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editKelasLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(editKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtlabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, editKelasLayout.createSequentialGroup()
                        .addComponent(jlabel1)
                        .addGap(0, 885, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        editKelasLayout.setVerticalGroup(
            editKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editKelasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(editKelasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlabel1)
                    .addGroup(editKelasLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainpanel.add(editKelas, "card2");

        add(mainpanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtlabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabelActionPerformed

    private void cbguruKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbguruKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbguruKeyPressed

    private void cbguruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbguruActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbguruActionPerformed

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataKelas);
        mainpanel.repaint();
        mainpanel.revalidate();
        updateCombo2();
    }//GEN-LAST:event_keluarActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        try {
            // Validasi input
            if (cbguru.getSelectedItem() == null || cbkelas.getSelectedItem() == null) {
                Kelas.showErrorDialog("Harap pilih guru dan kelas terlebih dahulu.");
                return; // Menghentikan eksekusi jika ada nilai kosong
            }

            // Query insert
            String query = "INSERT INTO guru (nama_guru, kelas) VALUES (?,?)";
            Boolean insert = SQLConnection.doPreparedUpdate(
                    query,
                    cbguru.getSelectedItem().toString().trim(), // nama_guru
                    cbkelas.getSelectedItem().toString().trim() // kelas
            );

            // Cek hasil insert
            if (Boolean.TRUE.equals(insert)) {
                Kelas.showSuccessDialog("Data berhasil disimpan!");
                keluarActionPerformed(null); // Keluar dari form setelah menyimpan
            } else {
                Kelas.showErrorDialog("Data gagal disimpan.");
            }

        } catch (SQLException e) {
            // Tampilkan pesan kesalahan
            Kelas.showErrorDialog("Data gagal disimpan: " + e.getMessage());
            e.printStackTrace(); // Debugging tambahan
        }
    }//GEN-LAST:event_simpanActionPerformed

    private void cbkelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbkelasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cbguru.requestFocus();
            cbguru.showPopup();
        }// TODO add your handling code here:
    }//GEN-LAST:event_cbkelasKeyPressed

    private void cbkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelasActionPerformed

    private void txtlabel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel1ActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalActionPerformed

        jkelas.revalidate();
        jkelas.repaint();
    }//GEN-LAST:event_batalActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        int barisTerpilih = tabelguru.getSelectedRow();

        if (barisTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            String namaGuru = tabelguru.getValueAt(barisTerpilih, 0).toString(); // Dapatkan nama guru dari tabel guru yang dipilih

            int konfirmasi = JOptionPane.showConfirmDialog(null, "<html>Apakah Anda Akan Menghapus Data Wali kelas <b>" + namaGuru + "</b> ? </html>",
                    "Konfirmasi Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    // Ambil kelas yang diajar oleh guru yang dihapus
                    String kelasGuru = null;

                    String sqlKelas = "SELECT kelas FROM guru WHERE nama_guru = ?";
                    ResultSet rsKelas = SQLConnection.doPreparedQuery(sqlKelas, namaGuru);

                    if (rsKelas.next()) {
                        kelasGuru = rsKelas.getString("kelas");
                    }

                    String query = "DELETE FROM guru WHERE nama_guru = ?";
                    Boolean delete = SQLConnection.doPreparedUpdate(query, namaGuru);

                    if (Boolean.TRUE.equals(delete)) {
                        Kelas.showSuccessDialog("Data berhasil dihapus");
                        // Perbarui ComboBox cbguru
//                        String guruHapus = namaGuru;
//                        cbguru.removeItem(guruHapus);
                        // Perbarui ComboBox cbkelas
                        if (kelasGuru != null) {
                            jkelas.removeItem(kelasGuru);
                        }
                        // Perbarui tampilan ComboBox cbkelas
                        jkelas.revalidate();
                        jkelas.repaint();
                        // Perbarui ComboBox dengan menambahkan ulang item-item baru
                        updateCombo2();
                        // Perbarui tampilan tabel guru
                        updateTabelGuru();
                        // Perbarui tampilan tabel siswa
                        tampilkanDataSiswa();
                        // Sembunyikan tombol edit, hapus, dan batal setelah penghapusan data
                        cbguru.requestFocus();
                        hapus.setVisible(false);
                        batal.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Data dengan nama guru '" + namaGuru + "' tidak ditemukan");
                    }
                    tambah.setVisible(true);
                } catch (SQLException e) {
                    Kelas.showErrorDialog("Data gagal dihapus: " + e.getMessage());
                }
            }
        }

    }//GEN-LAST:event_hapusActionPerformed

    private void jkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkelasActionPerformed
        jkelas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTabelGuru();
                tampilkanDataSiswa();
            }
        });
    }//GEN-LAST:event_jkelasActionPerformed

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(tambahKelas);
        mainpanel.repaint();
        mainpanel.revalidate();
        tabelguru.clearSelection();
    }//GEN-LAST:event_tambahActionPerformed

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyTyped

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        DefaultTableModel obj = (DefaultTableModel) tabelsiswa.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        tabelsiswa.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(txtcari.getText()));
        obj1.setRowFilter(RowFilter.regexFilter("(?i)" + txtcari.getText()));
    }//GEN-LAST:event_txtcariKeyReleased

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyPressed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void txtcariMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariMouseExited

    private void txtcariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariMouseClicked

    }//GEN-LAST:event_txtcariMouseClicked

    private void txtcariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariFocusLost
        String username = txtcari.getText();
        if (username.equals("") || username.equals("Pencarian")) {
            txtcari.setText("Pencarian");
        }
    }//GEN-LAST:event_txtcariFocusLost

    private void txtcariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariFocusGained
        String Pencarian = txtcari.getText();
        if (Pencarian.equals("Pencarian")) {
            txtcari.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariFocusGained

    private void tabelsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswaMouseClicked

    }//GEN-LAST:event_tabelsiswaMouseClicked

    private void tabelguruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelguruMouseClicked
        int selectedRowIndex = tabelguru.getSelectedRow();
        if (selectedRowIndex != -1) { // Pastikan indeks baris valid
            DefaultTableModel model = (DefaultTableModel) tabelguru.getModel();
            String namaGuru = model.getValueAt(selectedRowIndex, 0).toString(); // Ambil nama guru dari tabel guru

            // Setelah mendapatkan nama guru, Anda dapat menggunakan nilai ini untuk mengatur ComboBox cbguru
            if (namaGuru != null) {
                cbguru1.setSelectedItem(namaGuru);
            } else {
                // Jika namaGuru masih null, berikan pesan atau lakukan tindakan lain sesuai kebutuhan Anda
            }
        }
    }//GEN-LAST:event_tabelguruMouseClicked

    private void simpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan2ActionPerformed
        try {
            String query = "UPDATE guru SET nama_guru=? where kelas=?";
            Boolean update = SQLConnection.doPreparedUpdate(
                    query,
                    cbguru1.getSelectedItem().toString(),
                    cbkelas1.getSelectedItem().toString()
            );

            if (Boolean.TRUE.equals(update)) {
                Kelas.showSuccessDialog("Data berhasil diubah");
                keluar2ActionPerformed(null);
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada data yang diubah");
            }
        } catch (SQLException e) {
            Kelas.showErrorDialog("Data gagal diubah: " + e.getMessage());
        }
    }//GEN-LAST:event_simpan2ActionPerformed

    private void keluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluar2ActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataKelas);
        mainpanel.repaint();
        mainpanel.revalidate();
        jkelas.revalidate();
        jkelas.repaint();
    }//GEN-LAST:event_keluar2ActionPerformed

    private void cbguru1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbguru1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbguru1KeyPressed

    private void cbguru1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbguru1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbguru1ActionPerformed

    private void cbkelas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbkelas1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelas1KeyPressed

    private void cbkelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelas1ActionPerformed

    private void txtlabel3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton batal;
    private javax.swing.JComboBox<String> cbguru;
    private javax.swing.JComboBox<String> cbguru1;
    private javax.swing.JComboBox<String> cbkelas;
    private javax.swing.JComboBox<String> cbkelas1;
    private javax.swing.JPanel dataKelas;
    private javax.swing.JPanel editKelas;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> jkelas;
    private javax.swing.JLabel jlabel;
    private javax.swing.JLabel jlabel1;
    private javax.swing.JButton keluar;
    private javax.swing.JButton keluar2;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JButton simpan;
    private javax.swing.JButton simpan2;
    private javax.swing.JTable tabelguru;
    private javax.swing.JTable tabelsiswa;
    private javax.swing.JButton tambah;
    private javax.swing.JPanel tambahKelas;
    private javax.swing.ButtonGroup txtbutton;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtlabel;
    private javax.swing.JTextField txtlabel1;
    private javax.swing.JTextField txtlabel3;
    // End of variables declaration//GEN-END:variables
}
