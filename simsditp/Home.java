package simsditp;

import Database.SQLConnection;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class Home extends javax.swing.JPanel {

    private DefaultTableModel tabmode;
//    private RoundedPanel jPanel2, jPanel3, jPanel4, jPanel5;

    public Home() {
        initComponents();
        jumlahDataMaster();
//        datatable();
        datatable1();

        customizePanel(jPanel2, 20, new Color(0, 151, 167));
        customizePanel(jPanel3, 20, new Color(0, 151, 167));
        customizePanel(jPanel4, 20, new Color(0, 151, 167));
        customizePanel(jPanel5, 20, new Color(0, 151, 167));
    }

    // Method untuk mengatur panel menjadi rounded
    private void customizePanel(JPanel panel, int radius, Color color) {
        panel.setBackground(color); // Set warna latar belakang
        panel.setBorder(BorderFactory.createEmptyBorder()); // Hapus border default jika ada
        panel.setOpaque(false); // Buat transparan
        panel.setUI(new RoundedPanelUI(radius)); // Terapkan UI custom
    }

    // UI Custom untuk membuat JPanel dengan sudut rounded tanpa border
    private static class RoundedPanelUI extends javax.swing.plaf.PanelUI {

        private final int radius;

        public RoundedPanelUI(int radius) {
            this.radius = radius;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Menggambar latar belakang dengan sudut melengkung
            g2d.setColor(c.getBackground());
            g2d.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), radius, radius);

            // Hilangkan kode border
            g2d.dispose();
        }
    }

    public class RoundedPanel extends JPanel {

        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false); // Membuat panel transparan agar latar belakang bisa digambar ulang
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Menggambar latar belakang dengan sudut melengkung
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            // Hilangkan kode border
            g2d.dispose();
        }
    }

//    protected void datatable() {
//        // Header tabel
//        Object[] Baris = {"No", "Kelas", "Laki-Laki", "Perempuan", "Jumlah"};
//        tabmode = new DefaultTableModel(null, Baris);
//
//        try {
//            // Query SQL yang diperbaiki
//            String query = "SELECT kelas, "
//                    + "COUNT(CASE WHEN kelamin = 'L' THEN 1 END) AS jumlah_laki_laki, "
//                    + "COUNT(CASE WHEN kelamin = 'P' THEN 1 END) AS jumlah_perempuan, "
//                    + "COUNT(*) AS jumlah_total "
//                    + "FROM siswa "
//                    + "GROUP BY kelas";
//
//            // Menjalankan query
//            ResultSet hasil = SQLConnection.doQuery(query);
//
//            int no = 1;
//            while (hasil.next()) {
//                // Mengambil data dari hasil query
//                String kelas = hasil.getString("kelas");
//                int jumlahLakiLaki = hasil.getInt("jumlah_laki_laki");
//                int jumlahPerempuan = hasil.getInt("jumlah_perempuan");
//                int jumlahTotal = hasil.getInt("jumlah_total");
//
//                // Menambahkan baris ke tabel
//                tabmode.addRow(new Object[]{
//                    no++, // Nomor
//                    kelas, // Kelas
//                    jumlahLakiLaki, // Jumlah laki-laki
//                    jumlahPerempuan, // Jumlah perempuan
//                    jumlahTotal // Total jumlah
//                });
//            }
//
//            tabelgender.setModel(tabmode);
//
//            // Pengaturan tampilan tabel
//            tabelgender.setRowHeight(20); // Tinggi baris
//            tabelgender.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
//            tabelgender.setForeground(Color.BLACK); // Warna teks
//
//            // Pengaturan header tabel
//            JTableHeader header = tabelgender.getTableHeader();
//            header.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Font header
//            header.setBackground(new Color(0, 123, 255)); // Warna biru
//            header.setForeground(Color.WHITE); // Teks putih
//            header.setReorderingAllowed(false); // Non-reorderable kolom
//
//            // Renderer untuk header tabel
//            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
//            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah
//            headerRenderer.setBackground(new Color(0, 123, 255));
//            headerRenderer.setForeground(Color.WHITE);
//
//            // Terapkan renderer header ke semua kolom
//            TableColumnModel columnModel = tabelgender.getColumnModel();
//            for (int i = 0; i < columnModel.getColumnCount(); i++) {
//                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
//            }
//            columnModel.getColumn(0).setPreferredWidth(5);
//            columnModel.getColumn(1).setPreferredWidth(8);
//            columnModel.getColumn(2).setPreferredWidth(8);
//            columnModel.getColumn(3).setPreferredWidth(8);
//            columnModel.getColumn(4).setPreferredWidth(8);
//
//            // Renderer untuk isi tabel
//            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
//            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah
//            for (int i = 0; i < tabelgender.getColumnCount(); i++) {
//                tabelgender.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
//
//            }
//
//            // Alternating row colors (striped rows)
//            tabelgender.setDefaultRenderer(Object.class,
//                    new DefaultTableCellRenderer() {
//                @Override
//                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                    if (row % 2 == 0) {
//                        component.setBackground(new Color(240, 240, 240)); // Warna abu muda
//                    } else {
//                        component.setBackground(Color.WHITE); // Warna putih
//                    }
//                    if (isSelected) {
//                        component.setBackground(new Color(173, 216, 230)); // Warna biru muda saat dipilih
//                    }
//                    return component;
//                }
//            }
//            );
//
//            DefaultTableModel obj = (DefaultTableModel) tabelgender.getModel();
//            TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
//            tabelgender.setRowSorter(obj1);
//            obj.addTableModelListener(new TableModelListener() {
//                @Override
//                public void tableChanged(TableModelEvent e) {
//                    if (e.getType() == TableModelEvent.DELETE) {
//                        // Data telah dihapus, perbarui filter
//                    }
//                }
//            });
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Data gagal dipanggil: " + e.getMessage());
//        }
//    }

    protected void datatable1() {
        Object[] Baris = {"NIPD", "NISN", "Nama Siswa", "Kelas", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir"};
        tabmode = new DefaultTableModel(null, Baris);

        try {
            String query = "SELECT * FROM siswa where nisn like '%" + "%' or nama_siswa like '%" + "%' order by id_siswa asc";
            ResultSet hasil = SQLConnection.doQuery(query);

            while (hasil.next()) {
                tabmode.addRow(new Object[]{
                    hasil.getString(2),
                    hasil.getString(3),
                    hasil.getString(4),
                    hasil.getString(5),
                    hasil.getString(7),
                    hasil.getString(8),
                    hasil.getString(9),});
            }
            tabelsiswa.setModel(tabmode);
            // Pengaturan tampilan tabel
            tabelsiswa.setRowHeight(20); // Tinggi baris
            tabelsiswa.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
            tabelsiswa.setForeground(Color.BLACK); // Warna teks

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

            // Renderer untuk isi tabel
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah
            for (int i = 0; i < tabelsiswa.getColumnCount(); i++) {
                tabelsiswa.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);

            }

            // Alternating row colors (striped rows)
            tabelsiswa.setDefaultRenderer(Object.class,
                    new DefaultTableCellRenderer() {
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
            }
            );

            DefaultTableModel obj = (DefaultTableModel) tabelsiswa.getModel();
            TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
            tabelsiswa.setRowSorter(obj1);
            obj.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getType() == TableModelEvent.DELETE) {
                        // Data telah dihapus, perbarui filter
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "data gagal dipanggil" + e);
        }
    }

    private void jumlahDataMaster() {
        ResultSet rowPetugas = null;
        ResultSet rowKelas = null;
        ResultSet rowSiswa = null;
        Statement stmt = null;

        try {

            String queryPetugas = "SELECT COUNT(*) AS total FROM pegawai";
            rowPetugas = SQLConnection.doQuery(queryPetugas);
            if (rowPetugas.next()) {
                int total = rowPetugas.getInt("total");
                jml_pegawai.setText(Integer.toString(total));
            }

            String queryKelas = "SELECT COUNT(*) AS total FROM kelas";
            rowKelas = SQLConnection.doQuery(queryKelas);
            if (rowKelas.next()) {
                int total = rowKelas.getInt("total");
                jml_kelas.setText(Integer.toString(total));
            }

            String querySiswa = "SELECT COUNT(*) AS total FROM siswa";
            rowSiswa = SQLConnection.doQuery(querySiswa);
            if (rowSiswa.next()) {
                int total = rowSiswa.getInt("total");
                jml_siswa.setText(Integer.toString(total));
            }

            String queryMapel = "SELECT COUNT(*) AS total FROM mapel";
            rowSiswa = SQLConnection.doQuery(queryMapel);
            if (rowSiswa.next()) {
                int total = rowSiswa.getInt("total");
                jml_mapel.setText(Integer.toString(total));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rowPetugas != null) {
                    rowPetugas.close();
                }
                if (rowKelas != null) {
                    rowKelas.close();
                }
                if (rowSiswa != null) {
                    rowSiswa.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jml_siswa = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jml_pegawai = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jml_kelas = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jml_mapel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        operator1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelsiswa = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        chart1 = new simsditp.chart();

        setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Jumlah Siswa");

        jml_siswa.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jml_siswa.setForeground(new java.awt.Color(255, 255, 255));
        jml_siswa.setText("0");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/student.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jml_siswa)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jml_siswa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGap(30, 30, 30))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Jumlah Pegawai");

        jml_pegawai.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jml_pegawai.setForeground(new java.awt.Color(255, 255, 255));
        jml_pegawai.setText("0");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/admin.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jml_pegawai)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jml_pegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addGap(30, 30, 30))
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));

        jLabel7.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Jumlah Kelas");

        jml_kelas.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jml_kelas.setForeground(new java.awt.Color(255, 255, 255));
        jml_kelas.setText("0");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Keelas.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jml_kelas)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jml_kelas, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));

        jLabel10.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Jumlah Mapel");

        jml_mapel.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jml_mapel.setForeground(new java.awt.Color(255, 255, 255));
        jml_mapel.setText("0");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buku_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jml_mapel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jml_mapel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        jLabel2.setText("NPSN ");

        jLabel5.setText("Status ");

        jLabel8.setText("Kabupaten / Kota");

        jLabel11.setText("Kecamatan");

        jLabel13.setText("Operator  Dapodik");

        jLabel14.setText("Akreditasi");

        jLabel15.setText("Kepala Sekolah");

        jLabel16.setText("Provinsi");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setText(": Swasta");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setText(": 20103932");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/univ.png"))); // NOI18N
        jLabel25.setText("SD ISLAM TELADAN PULOGADUNG PAGI");

        jLabel26.setText("Kurikulum");

        jLabel28.setText("Status Kepemilikan");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setText(": KEC. CAKUNG");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel29.setText(": KOTA JAKARTA TIMUR");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel30.setText(": PROV. D.K.I. JAKARTA");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setText(": Susanto M.Pd");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setText(": B");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setText(": Merdeka");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel36.setText(":  Yayasan");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sekolah.png"))); // NOI18N

        operator1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        operator1.setText(": Vivin Susera Sabna");

        jLabel20.setText("Alamat");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setText(": Jl. Raya Bekasi Km. 18");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel26)
                            .addComponent(jLabel28)
                            .addComponent(jLabel20)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(jLabel27)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(operator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(operator1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(jLabel36))))
                .addContainerGap())
        );

        jScrollPane3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        tabelsiswa.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tabelsiswa);

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guru.png"))); // NOI18N
        jLabel17.setText("Data Peserta Didik");
        jLabel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(chart1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chart1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private simsditp.chart chart1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jml_kelas;
    private javax.swing.JLabel jml_mapel;
    private javax.swing.JLabel jml_pegawai;
    private javax.swing.JLabel jml_siswa;
    private javax.swing.JLabel operator1;
    private javax.swing.JTable tabelsiswa;
    // End of variables declaration//GEN-END:variables
}
