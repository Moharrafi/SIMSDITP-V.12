package simsditp;

import Database.SQLConnection;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class Nilai extends javax.swing.JPanel {

    private DefaultTableModel tabmode;

    /**
     * Creates new form Nilai
     */
    public Nilai() {
        initComponents();

        updateCombo();
        comboPredikat(predikat_sikap);
        comboPredikat(predikat_keterampilan);
        comboPredikat(predikat_kompetensi);
        comboMapel();
        txtcari.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtlabel1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcariNilaiSiswa.setBackground(new java.awt.Color(0, 0, 0, 1));

        btnInputNilai.setVisible(false);
        batal.setVisible(false);

        tabelsiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dapatkan baris yang diklik
                int row = tabelsiswa.getSelectedRow();
                // show tombol jika ada baris yang dipilih
                if (row != -1) {
                    btnInputNilai.setVisible(true);
                    batal.setVisible(true);
                } else {
                    // hide tombol jika ada baris yang dipilih
                    btnInputNilai.setVisible(false);
                    batal.setVisible(false);
                }
            }
        });

        batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Sembunyikan tombol    -tombol
                btnInputNilai.setVisible(false);
                batal.setVisible(false);
                tabelsiswa.clearSelection();
            }
        });

        tabelNilaiSiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dapatkan baris yang diklik
                int row = tabelNilaiSiswa.getSelectedRow();
                // show tombol jika ada baris yang dipilih
                if (row != -1) {
                    btnEditNilaiSiswa.setVisible(true);
                    btnHapusNilaiSiswa.setVisible(true);
                    btnCancelNilaiSiswa.setVisible(true);
                    btnTambahNilaiSiswa.setVisible(false); // Sembunyikan tombol tambah
                } else {
                    btnEditNilaiSiswa.setVisible(false);
                    btnHapusNilaiSiswa.setVisible(false);
                    btnCancelNilaiSiswa.setVisible(false);
                    btnTambahNilaiSiswa.setVisible(true); // Tampilkan tombol tambah kembali
                }
            }
        });

        batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Sembunyikan tombol-tombol
                btnInputNilai.setVisible(false);
                batal.setVisible(false);
                tabelsiswa.clearSelection();
            }
        });
    }

    private void updateCombo() {
        // Hapus semua item dalam combo box sebelum menambahkan item baru
        jkelas.removeAllItems();

        // Tambahkan placeholder jika diperlukan
        jkelas.addItem("-- Pilih Kelas --");

        try {
            String query = "SELECT DISTINCT kelas FROM guru WHERE kelas IS NOT NULL ORDER BY kelas ASC";
            ResultSet result = SQLConnection.doQuery(query);

            while (result.next()) {
                jkelas.addItem(result.getString("kelas")); // Tambahkan kembali item dari hasil query
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Setel placeholder sebagai item yang dipilih secara default
        jkelas.setSelectedItem("-- Pilih Kelas --");
    }

    private void comboPredikat(JComboBox<String> item) {
        item.removeAllItems();

        // Tambahkan placeholder jika diperlukan
        item.addItem("-- Pilih Predikat --");

        String[] predikat = {"A", "B", "C", "D", "E"};

        for (String p : predikat) {
            item.addItem(p);
        }

        // Setel placeholder sebagai item yang dipilih secara default
        item.setSelectedItem("-- Pilih Predikat --");
    }

    private void comboMapel() {
        // Hapus semua item dalam combo box sebelum menambahkan item baru
        mapel_id.removeAllItems();

        // Tambahkan placeholder jika diperlukan
        mapel_id.addItem("-- Pilih Mapel --");

        try {
            String query = "SELECT id_mapel, nama_mapel FROM mapel";
            ResultSet result = SQLConnection.doQuery(query);

            while (result.next()) {
                mapel_id.addItem(result.getString("id_mapel") + "-" + result.getString("nama_mapel")); // Tambahkan kembali item dari hasil query
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Setel placeholder sebagai item yang dipilih secara default
        mapel_id.setSelectedItem("-- Pilih Mapel --");
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

//    protected void kosong() {
//        semester.setSelectedItem(0);
//        tahun_ajaran.setText("");
//        nilai_tugas.setText("");
//        nilai_uts.setText("");
//        nilai_uas.setText("");
//        catatan.setText("");
//        predikat_sikap.setSelectedItem(0);
//        predikat_kompetensi.setSelectedItem(0);
//        predikat_keterampilan.setSelectedItem(0);
//        mapel_id.setSelectedItem(0);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainpanel = new javax.swing.JPanel();
        dataSiswa = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelsiswa = new javax.swing.JTable();
        txtcari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jkelas = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelguru = new javax.swing.JTable();
        btnInputNilai = new javax.swing.JButton();
        batal = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtlabel1 = new javax.swing.JTextField();
        jlabel = new javax.swing.JLabel();
        PanelNilai = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        nipd = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        nama_siswa = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        nisn = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        semester = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        tahun_ajaran = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        predikat_sikap = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        mapel_id = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        nilai_tugas = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        nilai_uts = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        nilai_uas = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        predikat_keterampilan = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        predikat_kompetensi = new javax.swing.JComboBox<>();
        simpan = new javax.swing.JButton();
        keluar = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        catatan = new javax.swing.JTextArea();
        dataNilai = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelNilaiSiswa = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        txtcariNilaiSiswa = new javax.swing.JTextField();
        btnHapusNilaiSiswa = new javax.swing.JButton();
        btnEditNilaiSiswa = new javax.swing.JButton();
        btnCancelNilaiSiswa = new javax.swing.JButton();
        btnTambahNilaiSiswa = new javax.swing.JButton();
        TitleNilaiSiswa = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        mainpanel1 = new javax.swing.JPanel();
        dataSiswa1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelsiswa1 = new javax.swing.JTable();
        txtcari1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jkelas1 = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelguru1 = new javax.swing.JTable();
        btnInputNilai1 = new javax.swing.JButton();
        batal1 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtlabel2 = new javax.swing.JTextField();
        jlabel1 = new javax.swing.JLabel();
        PanelNilai1 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        id_siswa1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        nama_siswa1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        nisn1 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        semester1 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        tahun_ajaran1 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        predikat_sikap1 = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        mapel_id1 = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        nilai_mapel1 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        nilai_tugas1 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        nilai_uts1 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        nilai_uas1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        predikat_keterampilan1 = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        predikat_kompetensi1 = new javax.swing.JComboBox<>();
        simpan1 = new javax.swing.JButton();
        keluar1 = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        catatan1 = new javax.swing.JTextArea();
        dataNilai1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelNilaiSiswa1 = new javax.swing.JTable();
        jLabel43 = new javax.swing.JLabel();
        txtcariNilaiSiswa1 = new javax.swing.JTextField();
        btnHapusNilaiSiswa1 = new javax.swing.JButton();
        btnEditNilaiSiswa1 = new javax.swing.JButton();
        btnCancelNilaiSiswa1 = new javax.swing.JButton();
        btnTambahNilaiSiswa1 = new javax.swing.JButton();
        TitleNilaiSiswa1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        mainpanel.setLayout(new java.awt.CardLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel3.setAutoscrolls(true);
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

        btnInputNilai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnInputNilai.setText("Input Nilai");
        btnInputNilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputNilaiActionPerformed(evt);
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
        jLabel3.setText("Kelas");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Murid Kelas");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Wali Kelas");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jkelas, 0, 272, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel3)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(btnInputNilai)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(batal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(batal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInputNilai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );

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

        jlabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlabel.setText("Data Nilai Siswa");

        javax.swing.GroupLayout dataSiswaLayout = new javax.swing.GroupLayout(dataSiswa);
        dataSiswa.setLayout(dataSiswaLayout);
        dataSiswaLayout.setHorizontalGroup(
            dataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataSiswaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(dataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataSiswaLayout.createSequentialGroup()
                        .addComponent(jlabel)
                        .addGap(832, 833, Short.MAX_VALUE))
                    .addGroup(dataSiswaLayout.createSequentialGroup()
                        .addGroup(dataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtlabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        dataSiswaLayout.setVerticalGroup(
            dataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataSiswaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataSiswaLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlabel))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(dataSiswa, "card2");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Input Nilai Siswa");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        nipd.setEditable(false);
        nipd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nipdActionPerformed(evt);
            }
        });

        jLabel7.setText("NIPD");

        nama_siswa.setEditable(false);
        nama_siswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nama_siswaActionPerformed(evt);
            }
        });

        jLabel8.setText("Nama Siswa");

        nisn.setEditable(false);
        nisn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nisnActionPerformed(evt);
            }
        });

        jLabel9.setText("NISN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nipd, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(190, 190, 190)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nama_siswa, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nisn, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nama_siswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nisn)
                    .addComponent(nipd))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setPreferredSize(new java.awt.Dimension(957, 69));

        semester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Pilih Semester ---", "Ganjil", "Genap" }));
        semester.setName(""); // NOI18N
        semester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                semesterActionPerformed(evt);
            }
        });

        jLabel10.setText("Semester");

        jLabel11.setText("Tahun Ajaran");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(274, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(163, 163, 163))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(semester, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(tahun_ajaran, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(314, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tahun_ajaran)
                    .addComponent(semester, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setPreferredSize(new java.awt.Dimension(957, 69));

        jLabel12.setText("Sikap");

        predikat_sikap.setName(""); // NOI18N
        predikat_sikap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                predikat_sikapActionPerformed(evt);
            }
        });

        jLabel14.setText("Mata Pelajaran");

        mapel_id.setName(""); // NOI18N
        mapel_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mapel_idActionPerformed(evt);
            }
        });

        jLabel17.setText("Nilai Tugas");

        nilai_tugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nilai_tugasActionPerformed(evt);
            }
        });

        jLabel18.setText("Nilai UTS");

        nilai_uts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nilai_utsActionPerformed(evt);
            }
        });

        jLabel19.setText("Nilai UAS");

        nilai_uas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nilai_uasActionPerformed(evt);
            }
        });

        jLabel13.setText("Keterampilan");

        predikat_keterampilan.setName(""); // NOI18N
        predikat_keterampilan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                predikat_keterampilanActionPerformed(evt);
            }
        });

        jLabel15.setText("Kompetensi");

        predikat_kompetensi.setName(""); // NOI18N
        predikat_kompetensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                predikat_kompetensiActionPerformed(evt);
            }
        });

        simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        simpan.setText("Simpan");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        keluar.setText("Batal");
        keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluarActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setText("Input Nilai Siswa");

        jLabel22.setText("Catatan");

        catatan.setColumns(20);
        catatan.setRows(5);
        jScrollPane4.setViewportView(catatan);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(nilai_uas, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nilai_tugas, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(predikat_keterampilan, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(0, 418, Short.MAX_VALUE))
                                    .addComponent(predikat_sikap, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(41, 41, 41))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(simpan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(keluar)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(predikat_kompetensi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mapel_id, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nilai_uts)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel22))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(predikat_sikap, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(predikat_kompetensi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13))
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(predikat_keterampilan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mapel_id, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, 0)
                        .addComponent(nilai_uts, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, 0)
                        .addComponent(nilai_tugas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(0, 0, 0)
                        .addComponent(nilai_uas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(simpan)
                            .addComponent(keluar))))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelNilaiLayout = new javax.swing.GroupLayout(PanelNilai);
        PanelNilai.setLayout(PanelNilaiLayout);
        PanelNilaiLayout.setHorizontalGroup(
            PanelNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(PanelNilaiLayout.createSequentialGroup()
                .addGroup(PanelNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelNilaiLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel6)
                        .addGap(0, 818, Short.MAX_VALUE))
                    .addGroup(PanelNilaiLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(PanelNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE))))
                .addContainerGap())
        );
        PanelNilaiLayout.setVerticalGroup(
            PanelNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNilaiLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(PanelNilai, "card3");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tabelNilaiSiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "null", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelNilaiSiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelNilaiSiswaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelNilaiSiswa);

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N

        txtcariNilaiSiswa.setText("Pencarian");
        txtcariNilaiSiswa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtcariNilaiSiswa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcariNilaiSiswaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcariNilaiSiswaFocusLost(evt);
            }
        });
        txtcariNilaiSiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtcariNilaiSiswaMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtcariNilaiSiswaMouseExited(evt);
            }
        });
        txtcariNilaiSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariNilaiSiswaActionPerformed(evt);
            }
        });
        txtcariNilaiSiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariNilaiSiswaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariNilaiSiswaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcariNilaiSiswaKeyTyped(evt);
            }
        });

        btnHapusNilaiSiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hapus.png"))); // NOI18N
        btnHapusNilaiSiswa.setText("Delete");
        btnHapusNilaiSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusNilaiSiswaActionPerformed(evt);
            }
        });

        btnEditNilaiSiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btnEditNilaiSiswa.setText("Edit");
        btnEditNilaiSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditNilaiSiswaActionPerformed(evt);
            }
        });

        btnCancelNilaiSiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        btnCancelNilaiSiswa.setText("Batal");
        btnCancelNilaiSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelNilaiSiswaActionPerformed(evt);
            }
        });

        btnTambahNilaiSiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnTambahNilaiSiswa.setText("Tambah");
        btnTambahNilaiSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahNilaiSiswaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnTambahNilaiSiswa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditNilaiSiswa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusNilaiSiswa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelNilaiSiswa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 446, Short.MAX_VALUE)
                        .addComponent(txtcariNilaiSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnHapusNilaiSiswa)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEditNilaiSiswa)
                        .addComponent(btnCancelNilaiSiswa)
                        .addComponent(btnTambahNilaiSiswa))
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtcariNilaiSiswa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
        );

        TitleNilaiSiswa.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        TitleNilaiSiswa.setText("Data Nilai Siswa");

        jButton1.setText("Kembali");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataNilaiLayout = new javax.swing.GroupLayout(dataNilai);
        dataNilai.setLayout(dataNilaiLayout);
        dataNilaiLayout.setHorizontalGroup(
            dataNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataNilaiLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(dataNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dataNilaiLayout.createSequentialGroup()
                        .addGroup(dataNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(TitleNilaiSiswa))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        dataNilaiLayout.setVerticalGroup(
            dataNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataNilaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TitleNilaiSiswa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(dataNilai, "card2");

        add(mainpanel, "card2");

        jPanel6.setLayout(new java.awt.CardLayout());

        mainpanel1.setLayout(new java.awt.CardLayout());

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });

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
        jScrollPane5.setViewportView(tabelsiswa1);

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

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N

        jkelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkelas1ActionPerformed(evt);
            }
        });

        tabelguru1.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelguru1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelguru1MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tabelguru1);

        btnInputNilai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnInputNilai1.setText("Input Nilai");
        btnInputNilai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputNilai1ActionPerformed(evt);
            }
        });

        batal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        batal1.setText("Batal");
        batal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batal1ActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setText("Kelas");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setText("Murid Kelas");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("Wali Kelas");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jkelas1, 0, 272, Short.MAX_VALUE)
                                .addComponent(jLabel26)
                                .addComponent(jLabel24)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(btnInputNilai1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(batal1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(batal1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInputNilai1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );

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

        jlabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlabel1.setText("Data Nilai Siswa");

        javax.swing.GroupLayout dataSiswa1Layout = new javax.swing.GroupLayout(dataSiswa1);
        dataSiswa1.setLayout(dataSiswa1Layout);
        dataSiswa1Layout.setHorizontalGroup(
            dataSiswa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataSiswa1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(dataSiswa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataSiswa1Layout.createSequentialGroup()
                        .addComponent(jlabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dataSiswa1Layout.createSequentialGroup()
                        .addGroup(dataSiswa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtlabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        dataSiswa1Layout.setVerticalGroup(
            dataSiswa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataSiswa1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dataSiswa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataSiswa1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlabel1))
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel1.add(dataSiswa1, "card2");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel27.setText("Input Nilai Siswa");

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        id_siswa1.setEditable(false);
        id_siswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_siswa1ActionPerformed(evt);
            }
        });

        jLabel28.setText("ID Siswa");

        nama_siswa1.setEditable(false);
        nama_siswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nama_siswa1ActionPerformed(evt);
            }
        });

        jLabel29.setText("Nama Siswa");

        nisn1.setEditable(false);
        nisn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nisn1ActionPerformed(evt);
            }
        });

        jLabel30.setText("NISN");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id_siswa1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(190, 190, 190)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nama_siswa1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nisn1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nama_siswa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nisn1)
                    .addComponent(id_siswa1))
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel9.setPreferredSize(new java.awt.Dimension(957, 69));

        semester1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Pilih Semester ---", "Ganjil", "Genap" }));
        semester1.setName(""); // NOI18N
        semester1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                semester1ActionPerformed(evt);
            }
        });

        jLabel31.setText("Semester");

        jLabel32.setText("Tahun Ajaran");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(274, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(163, 163, 163))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(semester1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(tahun_ajaran1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(314, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tahun_ajaran1)
                    .addComponent(semester1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel10.setPreferredSize(new java.awt.Dimension(957, 69));

        jLabel33.setText("Sikap");

        predikat_sikap1.setName(""); // NOI18N
        predikat_sikap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                predikat_sikap1ActionPerformed(evt);
            }
        });

        jLabel34.setText("Mata Pelajaran");

        mapel_id1.setName(""); // NOI18N
        mapel_id1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mapel_id1ActionPerformed(evt);
            }
        });

        jLabel35.setText("Nilai Mapel");

        nilai_mapel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nilai_mapel1ActionPerformed(evt);
            }
        });

        jLabel36.setText("Nilai Tugas");

        nilai_tugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nilai_tugas1ActionPerformed(evt);
            }
        });

        jLabel37.setText("Nilai UTS");

        nilai_uts1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nilai_uts1ActionPerformed(evt);
            }
        });

        jLabel38.setText("Nilai UAS");

        nilai_uas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nilai_uas1ActionPerformed(evt);
            }
        });

        jLabel39.setText("Keterampilan");

        predikat_keterampilan1.setName(""); // NOI18N
        predikat_keterampilan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                predikat_keterampilan1ActionPerformed(evt);
            }
        });

        jLabel40.setText("Kompetensi");

        predikat_kompetensi1.setName(""); // NOI18N
        predikat_kompetensi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                predikat_kompetensi1ActionPerformed(evt);
            }
        });

        simpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        simpan1.setText("Save");
        simpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan1ActionPerformed(evt);
            }
        });

        keluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        keluar1.setText("Cancel");
        keluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluar1ActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel41.setText("Input Nilai Siswa");

        jLabel42.setText("Catatan");

        catatan1.setColumns(20);
        catatan1.setRows(5);
        jScrollPane7.setViewportView(catatan1);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(simpan1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(keluar1))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel33))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel39))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel35))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel36))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nilai_mapel1)
                            .addComponent(predikat_keterampilan1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(predikat_sikap1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nilai_tugas1)))
                .addGap(41, 41, 41)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(predikat_kompetensi1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mapel_id1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nilai_uts1)
                    .addComponent(nilai_uas1)
                    .addComponent(jScrollPane7)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(jLabel38)
                            .addComponent(jLabel37)
                            .addComponent(jLabel34)
                            .addComponent(jLabel40))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel41)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel33)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(predikat_sikap1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(predikat_kompetensi1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(jLabel39))
                .addGap(0, 0, 0)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(predikat_keterampilan1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mapel_id1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(0, 0, 0)
                        .addComponent(nilai_mapel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel36)
                        .addGap(0, 0, 0)
                        .addComponent(nilai_tugas1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(0, 0, 0)
                        .addComponent(nilai_uts1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel38)
                        .addGap(0, 0, 0)
                        .addComponent(nilai_uas1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel42)
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(keluar1)
                            .addComponent(simpan1))))
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelNilai1Layout = new javax.swing.GroupLayout(PanelNilai1);
        PanelNilai1.setLayout(PanelNilai1Layout);
        PanelNilai1Layout.setHorizontalGroup(
            PanelNilai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(PanelNilai1Layout.createSequentialGroup()
                .addGroup(PanelNilai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelNilai1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel27)
                        .addGap(0, 818, Short.MAX_VALUE))
                    .addGroup(PanelNilai1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(PanelNilai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1014, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 1014, Short.MAX_VALUE))))
                .addContainerGap())
        );
        PanelNilai1Layout.setVerticalGroup(
            PanelNilai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNilai1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainpanel1.add(PanelNilai1, "card3");

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tabelNilaiSiswa1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "null", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelNilaiSiswa1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelNilaiSiswa1MouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tabelNilaiSiswa1);

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N

        txtcariNilaiSiswa1.setText("Pencarian");
        txtcariNilaiSiswa1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtcariNilaiSiswa1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcariNilaiSiswa1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcariNilaiSiswa1FocusLost(evt);
            }
        });
        txtcariNilaiSiswa1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtcariNilaiSiswa1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtcariNilaiSiswa1MouseExited(evt);
            }
        });
        txtcariNilaiSiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariNilaiSiswa1ActionPerformed(evt);
            }
        });
        txtcariNilaiSiswa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariNilaiSiswa1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariNilaiSiswa1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcariNilaiSiswa1KeyTyped(evt);
            }
        });

        btnHapusNilaiSiswa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hapus.png"))); // NOI18N
        btnHapusNilaiSiswa1.setText("Delete");
        btnHapusNilaiSiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusNilaiSiswa1ActionPerformed(evt);
            }
        });

        btnEditNilaiSiswa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btnEditNilaiSiswa1.setText("Edit");
        btnEditNilaiSiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditNilaiSiswa1ActionPerformed(evt);
            }
        });

        btnCancelNilaiSiswa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        btnCancelNilaiSiswa1.setText("Batal");
        btnCancelNilaiSiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelNilaiSiswa1ActionPerformed(evt);
            }
        });

        btnTambahNilaiSiswa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnTambahNilaiSiswa1.setText("Tambah");
        btnTambahNilaiSiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahNilaiSiswa1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(btnTambahNilaiSiswa1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditNilaiSiswa1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusNilaiSiswa1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelNilaiSiswa1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 446, Short.MAX_VALUE)
                        .addComponent(txtcariNilaiSiswa1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel43)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnHapusNilaiSiswa1)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEditNilaiSiswa1)
                        .addComponent(btnCancelNilaiSiswa1)
                        .addComponent(btnTambahNilaiSiswa1))
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtcariNilaiSiswa1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
        );

        TitleNilaiSiswa1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        TitleNilaiSiswa1.setText("Data Nilai Siswa");

        jButton2.setText("Kembali");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataNilai1Layout = new javax.swing.GroupLayout(dataNilai1);
        dataNilai1.setLayout(dataNilai1Layout);
        dataNilai1Layout.setHorizontalGroup(
            dataNilai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataNilai1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(dataNilai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dataNilai1Layout.createSequentialGroup()
                        .addGroup(dataNilai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(TitleNilaiSiswa1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        dataNilai1Layout.setVerticalGroup(
            dataNilai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataNilai1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(TitleNilaiSiswa1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel1.add(dataNilai1, "card2");

        jPanel6.add(mainpanel1, "card2");

        add(jPanel6, "card3");
    }// </editor-fold>//GEN-END:initComponents

    private void tabelsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswaMouseClicked
        if (evt.getClickCount() == 1) { // Pastikan hanya single click
            int selectedRowIndex = tabelsiswa.getSelectedRow();
            if (selectedRowIndex != -1) { // Pastikan indeks baris valid
                DefaultTableModel model = (DefaultTableModel) tabelsiswa.getModel();
                String id = model.getValueAt(selectedRowIndex, 0).toString();
                String nisnSiswa = model.getValueAt(selectedRowIndex, 1).toString();
                String nama = model.getValueAt(selectedRowIndex, 2).toString();

                nipd.setText(id);
                nisn.setText(nisnSiswa);
                nama_siswa.setText(nama);
                datatableNilaiSiswa();
            }
        }
    }//GEN-LAST:event_tabelsiswaMouseClicked

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

    private void txtcariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariMouseClicked

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
        DefaultTableModel obj = (DefaultTableModel) tabelsiswa.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        tabelsiswa.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(txtcari.getText()));
        obj1.setRowFilter(RowFilter.regexFilter("(?i)" + txtcari.getText()));
    }//GEN-LAST:event_txtcariKeyReleased

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyTyped

    private void jkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkelasActionPerformed
        jkelas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTabelGuru();
                tampilkanDataSiswa();
            }
        });
    }//GEN-LAST:event_jkelasActionPerformed

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
                    + "WHERE kelas = ? AND status = 'Aktif'";

            ResultSet result = SQLConnection.doPreparedQuery(query, kelas);

            // Bersihkan isi tabel siswa sebelum menambahkan data baru
            DefaultTableModel model = (DefaultTableModel) tabelsiswa.getModel();
            model.setRowCount(0);

            // Tambahkan judul kolom ke tabel siswa
            model.setColumnIdentifiers(new Object[]{"NIPD", "NISN", "Nama Siswa", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir"});

            // Tambahkan data siswa ke dalam tabel siswa
            while (result.next()) {
                String nipd = result.getString("nipd");
                String nisnSiswa = result.getString("nisn");
                String namaSiswa = result.getString("nama_siswa");
                String jenisKelamin = result.getString("kelamin");
                String tempatLahir = result.getString("tempat_lahir");
                String tanggalLahir = result.getString("tanggal_lahir");

                // Tambahkan baris baru ke dalam tabel siswa
                model.addRow(new Object[]{nipd, nisnSiswa, namaSiswa, jenisKelamin, tempatLahir, tanggalLahir});
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

            columnModel.getColumn(0).setPreferredWidth(10);  // Nipd
            columnModel.getColumn(1).setPreferredWidth(10); // Nisn
            columnModel.getColumn(2).setPreferredWidth(100);  // Nama
            columnModel.getColumn(3).setPreferredWidth(20);  // JK
            columnModel.getColumn(4).setPreferredWidth(20);  // TEMPAT LAHIR
            columnModel.getColumn(5).setPreferredWidth(20); // Tanggal Lahir

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

    private void tabelguruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelguruMouseClicked

    }//GEN-LAST:event_tabelguruMouseClicked

    private void btnInputNilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputNilaiActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataNilai);
        mainpanel.repaint();
        mainpanel.revalidate();
        tabelsiswa.clearSelection();

        TitleNilaiSiswa.setText("Data Nilai Siswa " + nama_siswa.getText() + "(" + nipd.getText() + ")");
    }//GEN-LAST:event_btnInputNilaiActionPerformed

    private void batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalActionPerformed
        try {
            tabelsiswa.clearSelection();
            tabelsiswa.setRowSorter(null);
            tampilkanDataSiswa();
            txtcari.setText("Pencarian");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saat memuat ulang data: " + e.getMessage());
        }
    }//GEN-LAST:event_batalActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void semesterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semesterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_semesterActionPerformed

    private void nisnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nisnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nisnActionPerformed

    private void nama_siswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nama_siswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nama_siswaActionPerformed

    private void nipdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nipdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nipdActionPerformed

    protected void datatableNilaiSiswa() {
        Object[] Baris = {"NISN", "Kode Mapel", "Nama Mata Pelajaran", "Semester", "Tahun Ajaran"};
        tabmode = new DefaultTableModel(null, Baris);

        try {
            String query = "SELECT nilai.*, mapel.id_mapel, mapel.nama_mapel FROM nilai "
                    + "JOIN mapel ON mapel.id_mapel = nilai.mapel_id "
                    + "JOIN siswa ON siswa.nisn = nilai.nisn "
                    + "WHERE siswa.nisn = ?";
            ResultSet result = SQLConnection.doPreparedQuery(
                    query,
                    nisn.getText()
            );

            while (result.next()) {
                tabmode.addRow(new Object[]{
                    result.getString("nisn"),
                    result.getString("id_mapel"),
                    result.getString("nama_mapel"),
                    result.getString("semester"),
                    result.getString("tahun_ajaran")
                });
            }

            tabelNilaiSiswa.setModel(tabmode);
            btnEditNilaiSiswa.setVisible(false);
            btnHapusNilaiSiswa.setVisible(false);
            btnCancelNilaiSiswa.setVisible(false);
            btnTambahNilaiSiswa.setVisible(true);

            // Pengaturan tampilan tabel
            tabelNilaiSiswa.setRowHeight(20); // Tinggi baris
            tabelNilaiSiswa.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
            tabelNilaiSiswa.setForeground(Color.BLACK); // Warna teks

            // Pengaturan header tabel
            JTableHeader header = tabelNilaiSiswa.getTableHeader();
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
            TableColumnModel columnModel = tabelNilaiSiswa.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            // Renderer untuk isi tabel
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah
            for (int i = 0; i < tabelNilaiSiswa.getColumnCount(); i++) {
                tabelNilaiSiswa.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            // Alternating row colors (striped rows)
            tabelNilaiSiswa.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

            DefaultTableModel obj = (DefaultTableModel) tabelNilaiSiswa.getModel();
            TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
            tabelNilaiSiswa.setRowSorter(obj1);
            obj.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.DELETE) {
                        // Data telah dihapus, perbarui filter
                        updateFilter();
                    }
                }

                private void updateFilter() {
                    String searchText = txtcariNilaiSiswa.getText();
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
        } catch (SQLException e) {
            System.out.println(e);
            Nilai.showErrorDialog("data gagal dipanggil" + e);
        }
    }

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataNilai);
        mainpanel.repaint();
        mainpanel.revalidate();
        kosong();
    }//GEN-LAST:event_keluarActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        JTextField[] requiredTextFields = new JTextField[]{
            tahun_ajaran,
            nilai_tugas,
            nilai_uts,
            nilai_uas
        };
        JComboBox[] requiredComboBox = new JComboBox[]{
            semester,
            predikat_sikap,
            predikat_keterampilan,
            predikat_kompetensi
        };

        for (JTextField tf : requiredTextFields) {
            if (tf.getText().equals("")) {
                JOptionPane.showMessageDialog(null, " wajib diisi.");
                return;
            }
        }
        for (JComboBox cb : requiredComboBox) {
            if (cb.getSelectedItem().toString().equals("-- Pilih Mapel --")) {
                JOptionPane.showMessageDialog(null, " wajib diisi.");
                return;
            }
            if (cb.getSelectedItem().toString().equals("-- Pilih Semester --")) {
                JOptionPane.showMessageDialog(null, " wajib diisi.");
                return;
            }
            if (cb.getSelectedItem().toString().equals("-- Pilih Predikat --")) {
                JOptionPane.showMessageDialog(null, cb.toString() + " wajib diisi.");
                return;
            }
        }
        try {
            Boolean insert = false;
            String idMapel = mapel_id.getSelectedItem().toString().split("-")[0];
            if (idNilai > 0) {
                String query = "UPDATE nilai SET mapel_id=?,nisn=?,semester=?,tahun_ajaran=?,nilai_tugas=?,nilai_uts=?,nilai_uas=?,predikat_sikap=?,predikat_keterampilan=?,predikat_kompetensi=?,catatan=? WHERE id =?";
                insert = SQLConnection.doPreparedUpdate(
                        query,
                        idMapel,
                        nisn.getText(),
                        semester.getSelectedItem().toString(),
                        tahun_ajaran.getText(),
                        nilai_tugas.getText(),
                        nilai_uts.getText(),
                        nilai_uas.getText(),
                        predikat_sikap.getSelectedItem().toString(),
                        predikat_keterampilan.getSelectedItem().toString(),
                        predikat_kompetensi.getSelectedItem().toString(),
                        catatan.getText(),
                        idNilai
                );
            } else {
                String query = "INSERT INTO nilai (mapel_id,nisn,semester,tahun_ajaran,nilai_tugas,nilai_uts,nilai_uas,predikat_sikap,predikat_keterampilan,predikat_kompetensi,catatan) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                insert = SQLConnection.doPreparedUpdate(
                        query,
                        idMapel,
                        nisn.getText(),
                        semester.getSelectedItem().toString(),
                        tahun_ajaran.getText(),
                        nilai_tugas.getText(),
                        nilai_uts.getText(),
                        nilai_uas.getText(),
                        predikat_sikap.getSelectedItem().toString(),
                        predikat_keterampilan.getSelectedItem().toString(),
                        predikat_kompetensi.getSelectedItem().toString(),
                        catatan.getText()
                );
            }

            if (Boolean.TRUE.equals(insert)) {
                Nilai.showSuccessDialog("data berhasil disimpan");
                keluarActionPerformed(null);
            }
        } catch (SQLException e) {
            Nilai.showErrorDialog("data gagal disimpan" + e);
        }
        datatableNilaiSiswa();
    }//GEN-LAST:event_simpanActionPerformed

    private void predikat_kompetensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predikat_kompetensiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_predikat_kompetensiActionPerformed

    private void predikat_keterampilanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predikat_keterampilanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_predikat_keterampilanActionPerformed

    private void nilai_uasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nilai_uasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nilai_uasActionPerformed

    private void nilai_utsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nilai_utsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nilai_utsActionPerformed

    private void nilai_tugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nilai_tugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nilai_tugasActionPerformed

    private void mapel_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mapel_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mapel_idActionPerformed

    private void predikat_sikapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predikat_sikapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_predikat_sikapActionPerformed

    private void tabelNilaiSiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelNilaiSiswaMouseClicked
        if (evt.getClickCount() == 1) { // Pastikan hanya single click
            int selectedRowIndex = tabelNilaiSiswa.getSelectedRow();
            int selectedModelRowIndex = tabelNilaiSiswa.convertRowIndexToModel(selectedRowIndex);
            if (selectedModelRowIndex != -1) { // Pastikan indeks baris valid
                DefaultTableModel model = (DefaultTableModel) tabelNilaiSiswa.getModel();
                String nisnStr = model.getValueAt(selectedModelRowIndex, 0).toString(); // NISN
                String mapelIdStr = model.getValueAt(selectedModelRowIndex, 1).toString(); // Ambil mapel_id dari kolom tabel

                try {
                    // Query SQL dengan filter berdasarkan NISN dan mapel_id
                    String query = "SELECT nilai.id, nilai.nisn, nilai.semester, nilai.tahun_ajaran, nilai.nilai_tugas, "
                            + "nilai.nilai_uts, nilai.nilai_uas, nilai.predikat_sikap, nilai.predikat_keterampilan, "
                            + "nilai.predikat_kompetensi, nilai.catatan, mapel.id_mapel, mapel.nama_mapel "
                            + "FROM nilai "
                            + "JOIN mapel ON mapel.id_mapel = nilai.mapel_id "
                            + "WHERE nilai.nisn = ? AND mapel.id_mapel = ?";

                    // Eksekusi query dengan prepared statement
                    ResultSet result = SQLConnection.doPreparedQuery(query, nisnStr, mapelIdStr);

                    if (result.next()) { // Jika data ditemukan
                        // Set nilai ke masing-masing field
                        idNilai = result.getInt("id");
                        mapel_id.setSelectedItem(result.getString("id_mapel") + "-" + result.getString("nama_mapel"));
                        nisn.setText(result.getString("nisn"));
                        semester.setSelectedItem(result.getString("semester"));
                        tahun_ajaran.setText(result.getString("tahun_ajaran"));
                        nilai_tugas.setText(result.getString("nilai_tugas"));
                        nilai_uts.setText(result.getString("nilai_uts"));
                        nilai_uas.setText(result.getString("nilai_uas"));
                        predikat_sikap.setSelectedItem(result.getString("predikat_sikap"));
                        predikat_keterampilan.setSelectedItem(result.getString("predikat_keterampilan"));
                        predikat_kompetensi.setSelectedItem(result.getString("predikat_kompetensi"));
                        catatan.setText(result.getString("catatan"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Data tidak ditemukan untuk NISN: " + nisnStr + " dan Mapel ID: " + mapelIdStr);
                    }

                } catch (SQLException e) {
                    // Log error untuk debugging
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengambil data: " + e.getMessage());
                }
            }
        }
        setTableNonEditable();
    }//GEN-LAST:event_tabelNilaiSiswaMouseClicked

    private void setTableNonEditable() {
        for (int i = 0; i < tabelNilaiSiswa.getColumnCount(); i++) {
            Class<?> col_class = tabelNilaiSiswa.getColumnClass(i);
            tabelNilaiSiswa.setDefaultEditor(col_class, null); // Menetapkan editor default ke null untuk semua kolom
        }
    }

    private void txtcariNilaiSiswaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswaFocusGained
        String Pencarian = txtcariNilaiSiswa.getText();
        if (Pencarian.equals("Pencarian")) {
            txtcariNilaiSiswa.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswaFocusGained

    private void txtcariNilaiSiswaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswaFocusLost
        String username = txtcariNilaiSiswa.getText();
        if (username.equals("") || username.equals("Pencarian")) {
            txtcariNilaiSiswa.setText("Pencarian");
        }
    }//GEN-LAST:event_txtcariNilaiSiswaFocusLost

    private void txtcariNilaiSiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswaMouseClicked

    }//GEN-LAST:event_txtcariNilaiSiswaMouseClicked

    private void txtcariNilaiSiswaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswaMouseExited

    private void txtcariNilaiSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswaActionPerformed

    private void txtcariNilaiSiswaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswaKeyPressed

    private void txtcariNilaiSiswaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswaKeyReleased
        DefaultTableModel obj = (DefaultTableModel) tabelNilaiSiswa.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        tabelNilaiSiswa.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(txtcariNilaiSiswa.getText()));
        obj1.setRowFilter(RowFilter.regexFilter("(?i)" + txtcariNilaiSiswa.getText()));
    }//GEN-LAST:event_txtcariNilaiSiswaKeyReleased

    private void txtcariNilaiSiswaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswaKeyTyped

    private void btnHapusNilaiSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusNilaiSiswaActionPerformed
        int barisTerpilih = tabelNilaiSiswa.getSelectedRow();

        if (barisTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            int konfirmasi = JOptionPane.showConfirmDialog(null, "<html>Apakah Anda Akan Menghapus Data Nilai <b>" + nama_siswa.getText() + "</b> </html>", "Konfirmasi Dialog", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    String query = "DELETE FROM nilai WHERE id = ?";
                    Boolean delete = SQLConnection.doPreparedUpdate(query, idNilai);

                    if (Boolean.TRUE.equals(delete)) {
                        Nilai.showSuccessDialog("data berhasil dihapus");
                        kosong();
                        btnTambahNilaiSiswa.setVisible(true);
                    }
                } catch (SQLException e) {
                    Nilai.showErrorDialog("data gagal dihapus: " + e);
                }
            }
            datatableNilaiSiswa();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusNilaiSiswaActionPerformed

    private void btnEditNilaiSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditNilaiSiswaActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        if (idNilai != -1) { // Pastikan idNilai sudah diisi dari tabel
            // Logika ini akan dipengaruhi oleh tabelNilaiSiswaMouseClicked
            System.out.println("Edit data untuk ID Nilai: " + idNilai);
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data dari tabel terlebih dahulu!");
        }

        mainpanel.add(PanelNilai);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_btnEditNilaiSiswaActionPerformed

    private void btnCancelNilaiSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelNilaiSiswaActionPerformed
        datatableNilaiSiswa();
    }//GEN-LAST:event_btnCancelNilaiSiswaActionPerformed

    private void btnTambahNilaiSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahNilaiSiswaActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(PanelNilai);
        mainpanel.repaint();
        mainpanel.revalidate();
        tabelNilaiSiswa.clearSelection();// TODO add your handling code here:
        idNilai = 0;
    }//GEN-LAST:event_btnTambahNilaiSiswaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataSiswa);
        mainpanel.repaint();
        mainpanel.revalidate();

        btnInputNilai.setVisible(false);
        batal.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtlabel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel1ActionPerformed

    private void tabelsiswa1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswa1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelsiswa1MouseClicked

    private void txtcari1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcari1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1FocusGained

    private void txtcari1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcari1FocusLost
        // TODO add your handling code here:
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
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1KeyReleased

    private void txtcari1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1KeyTyped

    private void jkelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jkelas1ActionPerformed

    private void tabelguru1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelguru1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelguru1MouseClicked

    private void btnInputNilai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputNilai1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInputNilai1ActionPerformed

    private void batal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_batal1ActionPerformed

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel7MouseClicked

    private void txtlabel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel2ActionPerformed

    private void id_siswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_siswa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_siswa1ActionPerformed

    private void nama_siswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nama_siswa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nama_siswa1ActionPerformed

    private void nisn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nisn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nisn1ActionPerformed

    private void semester1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semester1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_semester1ActionPerformed

    private void predikat_sikap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predikat_sikap1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_predikat_sikap1ActionPerformed

    private void mapel_id1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mapel_id1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mapel_id1ActionPerformed

    private void nilai_mapel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nilai_mapel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nilai_mapel1ActionPerformed

    private void nilai_tugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nilai_tugas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nilai_tugas1ActionPerformed

    private void nilai_uts1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nilai_uts1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nilai_uts1ActionPerformed

    private void nilai_uas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nilai_uas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nilai_uas1ActionPerformed

    private void predikat_keterampilan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predikat_keterampilan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_predikat_keterampilan1ActionPerformed

    private void predikat_kompetensi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predikat_kompetensi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_predikat_kompetensi1ActionPerformed

    private void simpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simpan1ActionPerformed

    private void keluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keluar1ActionPerformed

    private void tabelNilaiSiswa1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelNilaiSiswa1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelNilaiSiswa1MouseClicked

    private void txtcariNilaiSiswa1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswa1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswa1FocusGained

    private void txtcariNilaiSiswa1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswa1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswa1FocusLost

    private void txtcariNilaiSiswa1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswa1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswa1MouseClicked

    private void txtcariNilaiSiswa1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswa1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswa1MouseExited

    private void txtcariNilaiSiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswa1ActionPerformed

    private void txtcariNilaiSiswa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswa1KeyPressed

    private void txtcariNilaiSiswa1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswa1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswa1KeyReleased

    private void txtcariNilaiSiswa1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariNilaiSiswa1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariNilaiSiswa1KeyTyped

    private void btnHapusNilaiSiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusNilaiSiswa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusNilaiSiswa1ActionPerformed

    private void btnEditNilaiSiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditNilaiSiswa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditNilaiSiswa1ActionPerformed

    private void btnCancelNilaiSiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelNilaiSiswa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelNilaiSiswa1ActionPerformed

    private void btnTambahNilaiSiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahNilaiSiswa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahNilaiSiswa1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void kosong() {
        mapel_id.setSelectedItem("-- Pilih Mapel --");
        semester.setSelectedItem("-- Pilih Semester --");
        tahun_ajaran.setText("");
        nilai_tugas.setText("");
        nilai_uts.setText("");
        nilai_uas.setText("");
        predikat_sikap.setSelectedItem("-- Pilih Predikat --");
        predikat_keterampilan.setSelectedItem("-- Pilih Predikat --");
        predikat_kompetensi.setSelectedItem("-- Pilih Predikat --");
        catatan.setText("");
    }

    private int idNilai = 0;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelNilai;
    private javax.swing.JPanel PanelNilai1;
    private javax.swing.JLabel TitleNilaiSiswa;
    private javax.swing.JLabel TitleNilaiSiswa1;
    private javax.swing.JButton batal;
    private javax.swing.JButton batal1;
    private javax.swing.JButton btnCancelNilaiSiswa;
    private javax.swing.JButton btnCancelNilaiSiswa1;
    private javax.swing.JButton btnEditNilaiSiswa;
    private javax.swing.JButton btnEditNilaiSiswa1;
    private javax.swing.JButton btnHapusNilaiSiswa;
    private javax.swing.JButton btnHapusNilaiSiswa1;
    private javax.swing.JButton btnInputNilai;
    private javax.swing.JButton btnInputNilai1;
    private javax.swing.JButton btnTambahNilaiSiswa;
    private javax.swing.JButton btnTambahNilaiSiswa1;
    private javax.swing.JTextArea catatan;
    private javax.swing.JTextArea catatan1;
    private javax.swing.JPanel dataNilai;
    private javax.swing.JPanel dataNilai1;
    private javax.swing.JPanel dataSiswa;
    private javax.swing.JPanel dataSiswa1;
    private javax.swing.JTextField id_siswa1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JComboBox<String> jkelas;
    private javax.swing.JComboBox<String> jkelas1;
    private javax.swing.JLabel jlabel;
    private javax.swing.JLabel jlabel1;
    private javax.swing.JButton keluar;
    private javax.swing.JButton keluar1;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel mainpanel1;
    private javax.swing.JComboBox<String> mapel_id;
    private javax.swing.JComboBox<String> mapel_id1;
    private javax.swing.JTextField nama_siswa;
    private javax.swing.JTextField nama_siswa1;
    private javax.swing.JTextField nilai_mapel1;
    private javax.swing.JTextField nilai_tugas;
    private javax.swing.JTextField nilai_tugas1;
    private javax.swing.JTextField nilai_uas;
    private javax.swing.JTextField nilai_uas1;
    private javax.swing.JTextField nilai_uts;
    private javax.swing.JTextField nilai_uts1;
    private javax.swing.JTextField nipd;
    private javax.swing.JTextField nisn;
    private javax.swing.JTextField nisn1;
    private javax.swing.JComboBox<String> predikat_keterampilan;
    private javax.swing.JComboBox<String> predikat_keterampilan1;
    private javax.swing.JComboBox<String> predikat_kompetensi;
    private javax.swing.JComboBox<String> predikat_kompetensi1;
    private javax.swing.JComboBox<String> predikat_sikap;
    private javax.swing.JComboBox<String> predikat_sikap1;
    private javax.swing.JComboBox<String> semester;
    private javax.swing.JComboBox<String> semester1;
    private javax.swing.JButton simpan;
    private javax.swing.JButton simpan1;
    private javax.swing.JTable tabelNilaiSiswa;
    private javax.swing.JTable tabelNilaiSiswa1;
    private javax.swing.JTable tabelguru;
    private javax.swing.JTable tabelguru1;
    private javax.swing.JTable tabelsiswa;
    private javax.swing.JTable tabelsiswa1;
    private javax.swing.JTextField tahun_ajaran;
    private javax.swing.JTextField tahun_ajaran1;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtcari1;
    private javax.swing.JTextField txtcariNilaiSiswa;
    private javax.swing.JTextField txtcariNilaiSiswa1;
    private javax.swing.JTextField txtlabel1;
    private javax.swing.JTextField txtlabel2;
    // End of variables declaration//GEN-END:variables
}
