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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Cetak_pegawai extends javax.swing.JPanel {

    public Cetak_pegawai() {
        initComponents();
        tampilkanPegawai();
        txtlabel2.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcari1.setBackground(new java.awt.Color(0, 0, 0, 1));
    }

    void tampilkanPegawai() {
        // Buat query SQL untuk mengambil data siswa berdasarkan kelas yang dipilih
        String sql = "SELECT * FROM pegawai";
        try {
            ResultSet rs = SQLConnection.doPreparedQuery(sql);

            // Bersihkan isi tabel siswa sebelum menambahkan data baru
            DefaultTableModel model = (DefaultTableModel) tabelpegawai.getModel();
            model.setRowCount(0);

            // Tambahkan judul kolom ke tabel siswa
            model.setColumnIdentifiers(new Object[]{"Id Pegawai", "Nama Pegawai", "Jenis Kelamin", "Jabatan", "Jenis Pegawai", "nip"});

            // Tambahkan data siswa ke dalam tabel
            while (rs.next()) {
                String id = rs.getString("id_pegawai");
                String nama = rs.getString("nama_pegawai");
                String kelamin = rs.getString("kelamin");
                String jabatan = rs.getString("jabatan");
                String jenis = rs.getString("jenis_pegawai");
                String nip = rs.getString("nip");

                // Tambahkan baris baru ke dalam tabel siswa
                model.addRow(new Object[]{id, nama, kelamin, jabatan, jenis, nip});
            }
            
            // Pengaturan tampilan tabel
            tabelpegawai.setRowHeight(20); // Tinggi baris
            tabelpegawai.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
            tabelpegawai.setForeground(Color.BLACK); // Warna teks

            // Pengaturan header tabel
            JTableHeader header = tabelpegawai.getTableHeader();
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
            TableColumnModel columnModel = tabelpegawai.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            // Renderer untuk isi tabel
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah
            for (int i = 0; i < tabelpegawai.getColumnCount(); i++) {
                tabelpegawai.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

            // Alternating row colors (striped rows)
            tabelpegawai.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data siswa: " + ex.getMessage());
        }
    }

    public void cetak2() {
        try {

            Connection c = SQLConnection.getConnection();

            // Akses file laporan dari resources
            InputStream reportSourceStream = getClass().getResourceAsStream("/Laporan/Laporan_pegawai.jrxml");
            if (reportSourceStream == null) {
                throw new FileNotFoundException("File laporan tidak ditemukan di resources!");
            }

            // Kompilasi laporan dari InputStream
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSourceStream);

            Locale indonesiaLocale = new Locale("id", "ID");
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy", indonesiaLocale);
            String formattedDate = dateFormatter.format(new java.util.Date());

            // Siapkan parameter untuk laporan
            Map<String, Object> params = new HashMap<>();
            params.put("tanggal", formattedDate);

            // Isi laporan dengan data dari database
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, c);

            // Tentukan lokasi output laporan (temp folder)
            String reportDest = System.getProperty("java.io.tmpdir") + "/Laporan_Siswa.html";

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
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelpegawai = new javax.swing.JTable();
        cetak2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtlabel2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        mainpanel1 = new javax.swing.JPanel();
        cetakabsen1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtcari2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jkelas1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelsiswa1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        cetak3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtlabel3 = new javax.swing.JTextField();

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

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Pegawai :");

        tabelpegawai.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelpegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpegawaiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelpegawai);

        cetak2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        cetak2.setText("Cetak");
        cetak2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetak2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1116, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(cetak2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cetak2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Cetak Data Pegawai");

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
            .addGroup(cetakabsenLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(cetakabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtlabel2)
                    .addGroup(cetakabsenLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 905, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jPanel1.setLayout(new java.awt.CardLayout());

        mainpanel1.setLayout(new java.awt.CardLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        txtcari2.setText("Pencarian");
        txtcari2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtcari2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcari2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcari2FocusLost(evt);
            }
        });
        txtcari2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtcari2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtcari2MouseExited(evt);
            }
        });
        txtcari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcari2ActionPerformed(evt);
            }
        });
        txtcari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcari2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcari2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcari2KeyTyped(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N

        jkelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkelas1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Murid Kelas");

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

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Kelas     :");

        cetak3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        cetak3.setText("Cetak");
        cetak3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetak3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cetak3, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(jkelas1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcari2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cetak3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcari2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setText("Cetak Data Siswa");

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
                        .addComponent(jLabel10)
                        .addGap(0, 937, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        cetakabsen1Layout.setVerticalGroup(
            cetakabsen1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cetakabsen1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(cetakabsen1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(cetakabsen1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel1.add(cetakabsen1, "card2");

        jPanel1.add(mainpanel1, "card2");

        add(jPanel1, "card3");
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
        DefaultTableModel obj = (DefaultTableModel) tabelpegawai.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        tabelpegawai.setRowSorter(obj1);
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

    private void tabelpegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpegawaiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelpegawaiMouseClicked

    private void cetak2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetak2ActionPerformed
        cetak2();
    }//GEN-LAST:event_cetak2ActionPerformed

    private void txtcari2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcari2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari2FocusGained

    private void txtcari2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcari2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari2FocusLost

    private void txtcari2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcari2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari2MouseClicked

    private void txtcari2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcari2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari2MouseExited

    private void txtcari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcari2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari2ActionPerformed

    private void txtcari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari2KeyPressed

    private void txtcari2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari2KeyReleased

    private void txtcari2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari2KeyTyped

    private void jkelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jkelas1ActionPerformed

    private void tabelsiswa1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswa1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelsiswa1MouseClicked

    private void cetak3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetak3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cetak3ActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel5MouseClicked

    private void txtlabel3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cetak2;
    private javax.swing.JButton cetak3;
    private javax.swing.JPanel cetakabsen;
    private javax.swing.JPanel cetakabsen1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JComboBox<String> jkelas1;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel mainpanel1;
    private javax.swing.JTable tabelpegawai;
    private javax.swing.JTable tabelsiswa1;
    private javax.swing.ButtonGroup txtbutton;
    private javax.swing.JTextField txtcari1;
    private javax.swing.JTextField txtcari2;
    private javax.swing.JTextField txtlabel2;
    private javax.swing.JTextField txtlabel3;
    // End of variables declaration//GEN-END:variables
}
