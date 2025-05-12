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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.PatternSyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class Pegawai extends javax.swing.JPanel {

    private DefaultTableModel tabmode;

    public Pegawai() {
        initComponents();
        autonumber();
        txtlabel.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtlabel1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtlabel2.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcari.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtid.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnama.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnip.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtid1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnama1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnip1.setBackground(new java.awt.Color(0, 0, 0, 1));
        ubah.setVisible(false);
        hapus.setVisible(false);
        cancel.setVisible(false);
        tabelpegawai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dapatkan baris yang diklik
                int row = tabelpegawai.getSelectedRow();
                // Tampilkan tombol edit, hapus, dan batal jika ada baris yang dipilih
                if (row != -1) {
                    ubah.setVisible(true);
                    hapus.setVisible(true);
                    cancel.setVisible(true);
                    tambah.setVisible(false); // Sembunyikan tombol tambah
                } else {
                    ubah.setVisible(false);
                    hapus.setVisible(false);
                    cancel.setVisible(false);
                    tambah.setVisible(true); // Tampilkan tombol tambah kembali
                }
            }
        });

        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Sembunyikan tombol-tombol
                ubah.setVisible(false);
                hapus.setVisible(false);
                cancel.setVisible(false);
                tabelpegawai.clearSelection();
                tambah.setVisible(true);
                kosong();
                autonumber();
            }
        });
        batal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Sembunyikan tombol-tombol
                ubah.setVisible(false);
                hapus.setVisible(false);
                cancel.setVisible(false);
                tabelpegawai.clearSelection();
                tambah.setVisible(true);
                kosong();
                autonumber();
            }
        });
    }

    protected void kosong() {
        txtnama.setText("");
        cbjabatan.setSelectedItem(0);
        cbjenispegawai.setSelectedItem(0);
        cbkelamin.setSelectedItem(0);
        cbkelamin1.setSelectedItem(0);
        cbkelamin1.setSelectedItem(0);
        txtnip.setText("");
        txtbutton.clearSelection();
    }

    protected void kosong1() {
        txtnama1.setText("");
        cbjabatan1.setSelectedItem(0);
        cbjenispegawai1.setSelectedItem(0);
        txtnip1.setText("");
        txtbutton.clearSelection();
    }

    private void autonumber() {
        try {
            String query = "SELECT MAX(id_pegawai) AS id FROM pegawai";
            ResultSet result = SQLConnection.doQuery(query);

            while (result.next()) {
                Object[] obj = new Object[1];
                obj[0] = result.getString("id");
                if (obj[0] == null) {
                    obj[0] = "P000";
                }
                String str = (String) obj[0];
                String kd = str.substring(1, 4);
                int int_code = Integer.parseInt(kd);
                int_code++;
                String a = String.format("%03d", int_code);
                String b = "P" + a;
                txtid.setText(b);
                txtid.setEditable(false);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
        Object[] Baris = {"No", "ID Pegawai", "Nama Pegawai", "JK", "Jabatan", "Jenis Pegawai", "NIP / NI PPPK"};
        tabmode = new DefaultTableModel(null, Baris);

        try {
            String query = "SELECT * FROM pegawai ORDER BY id_pegawai ASC";
            ResultSet result = SQLConnection.doQuery(query);

            int no = 1;
            while (result.next()) {
                tabmode.addRow(new Object[]{
                    no, // Nomor urut
                    result.getString(1), // ID Pegawai
                    result.getString(2), // Nama Pegawai
                    result.getString(3), // Jenis Kelamin
                    result.getString(4), // Jabatan
                    result.getString(5), // Jenis Pegawai
                    result.getString(6) // NIP / NI PPPK
                });
                no++;
            }
            tabelpegawai.setModel(tabmode);
            tambah.setVisible(true);
            ubah.setVisible(false);
            hapus.setVisible(false);
            cancel.setVisible(false);

            // Terapkan renderer modern untuk tabel
            tabelpegawai.setRowHeight(20); // Tinggi baris
            tabelpegawai.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font isi tabel
            tabelpegawai.setForeground(Color.BLACK); // Warna teks

            // Mengatur header tabel
            JTableHeader header = tabelpegawai.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 14));
            header.setBackground(new Color(0, 123, 255)); // Warna biru modern
            header.setForeground(Color.WHITE); // Teks putih
            header.setReorderingAllowed(false);

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

            // Atur lebar kolom dengan lebih fleksibel
            int[] columnWidths = {50, 80, 300, 50, 100, 100, 200}; // Lebar untuk setiap kolom
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
            }
            
//            tabelpegawai.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

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
            DefaultTableModel obj = (DefaultTableModel) tabelpegawai.getModel();
            TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
            tabelpegawai.setRowSorter(obj1);
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
            Pegawai.showErrorDialog("data gagal dipanggil" + e);
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
        dataPegawai = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelpegawai = new javax.swing.JTable();
        txtcari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ubah = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        tambah = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtlabel = new javax.swing.JTextField();
        tambahpegawai = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtnip = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cbjenispegawai = new javax.swing.JComboBox<>();
        simpan = new javax.swing.JButton();
        batal = new javax.swing.JButton();
        cbjabatan = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        cbkelamin = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtlabel1 = new javax.swing.JTextField();
        editPegawai = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtid1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtnip1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cbjenispegawai1 = new javax.swing.JComboBox<>();
        simpan1 = new javax.swing.JButton();
        batal1 = new javax.swing.JButton();
        cbjabatan1 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        txtnama1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        cbkelamin1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtlabel2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        mainpanel1 = new javax.swing.JPanel();
        dataPegawai1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelpegawai1 = new javax.swing.JTable();
        txtcari1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ubah1 = new javax.swing.JButton();
        hapus1 = new javax.swing.JButton();
        cancel1 = new javax.swing.JButton();
        tambah1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtlabel3 = new javax.swing.JTextField();
        tambahpegawai1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        txtid2 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtnip2 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cbjenispegawai2 = new javax.swing.JComboBox<>();
        simpan2 = new javax.swing.JButton();
        batal2 = new javax.swing.JButton();
        cbjabatan2 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        txtnama2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtlabel4 = new javax.swing.JTextField();
        editPegawai1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        txtid3 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtnip3 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        cbjenispegawai3 = new javax.swing.JComboBox<>();
        simpan3 = new javax.swing.JButton();
        batal3 = new javax.swing.JButton();
        cbjabatan3 = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        txtnama3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtlabel5 = new javax.swing.JTextField();

        setLayout(new java.awt.CardLayout());

        mainpanel.setLayout(new java.awt.CardLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

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
        jScrollPane1.setViewportView(tabelpegawai);

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
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N

        ubah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        ubah.setText("Edit");
        ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahActionPerformed(evt);
            }
        });

        hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hapus.png"))); // NOI18N
        hapus.setText("Hapus");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        cancel.setText("Batal");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        tambah.setText("Tambah");
        tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ubah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancel)
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcari, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(ubah)
                        .addComponent(hapus)
                        .addComponent(cancel)
                        .addComponent(tambah)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Data Pegawai");

        txtlabel.setEditable(false);
        txtlabel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtlabel.setCaretColor(java.awt.Color.lightGray);
        txtlabel.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtlabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlabelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataPegawaiLayout = new javax.swing.GroupLayout(dataPegawai);
        dataPegawai.setLayout(dataPegawaiLayout);
        dataPegawaiLayout.setHorizontalGroup(
            dataPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPegawaiLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(dataPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtlabel)
                    .addGroup(dataPegawaiLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 845, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        dataPegawaiLayout.setVerticalGroup(
            dataPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPegawaiLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dataPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(dataPegawaiLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(dataPegawai, "card2");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        jPanel4.setToolTipText("");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Nama Pegawai");

        txtid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidActionPerformed(evt);
            }
        });
        txtid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtidKeyPressed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Jenis Pegawai");

        txtnip.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnipActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("NIP / NIKKI");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("Jabatan");

        cbjenispegawai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CPNS", "PNS", "PPPK", "KKI", "HONOR", "Tidak Ada" }));
        cbjenispegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjenispegawaiActionPerformed(evt);
            }
        });
        cbjenispegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbjenispegawaiKeyPressed(evt);
            }
        });

        simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        simpan.setText("Simpan");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        batal.setText("Batal");
        batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalActionPerformed(evt);
            }
        });

        cbjabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kepala Sekolah", "Wakil Kepala Sekolah", "Tata Usaha", "Unit Perpustakaan", "Guru Kelas", "Guru Olahraga", "Guru B. Inggris", "Guru BTQ", "Guru PJOK", "Guru Pramuka", "Penjaga Sekolah" }));
        cbjabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjabatanActionPerformed(evt);
            }
        });
        cbjabatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbjabatanKeyPressed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("ID Pegawai");

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

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setText("Jenis Kelamin");

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtid)
                    .addComponent(txtnama)
                    .addComponent(cbjabatan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbjenispegawai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtnip)
                    .addComponent(cbkelamin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(batal))
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbkelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbjabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbjenispegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnip, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpan)
                    .addComponent(batal))
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Tambah Data Pegawai");

        txtlabel1.setEditable(false);
        txtlabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtlabel1.setCaretColor(java.awt.Color.lightGray);
        txtlabel1.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtlabel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlabel1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tambahpegawaiLayout = new javax.swing.GroupLayout(tambahpegawai);
        tambahpegawai.setLayout(tambahpegawaiLayout);
        tambahpegawaiLayout.setHorizontalGroup(
            tambahpegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambahpegawaiLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(tambahpegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtlabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tambahpegawaiLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 751, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tambahpegawaiLayout.setVerticalGroup(
            tambahpegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahpegawaiLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(tambahpegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(tambahpegawaiLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(tambahpegawai, "card2");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        jPanel5.setToolTipText("");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setText("Nama Pegawai");

        txtid1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtid1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtid1ActionPerformed(evt);
            }
        });
        txtid1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtid1KeyPressed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setText("Jenis Pegawai");

        txtnip1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnip1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnip1ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setText("NIP / NI PPPK");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("Jabatan");

        cbjenispegawai1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CPNS", "PNS", "PPPK", "KKI", "HONOR", "Tidak Ada" }));
        cbjenispegawai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjenispegawai1ActionPerformed(evt);
            }
        });
        cbjenispegawai1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbjenispegawai1KeyPressed(evt);
            }
        });

        simpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        simpan1.setText("Simpan");
        simpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan1ActionPerformed(evt);
            }
        });

        batal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        batal1.setText("Batal");
        batal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batal1ActionPerformed(evt);
            }
        });

        cbjabatan1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kepala Sekolah", "Wakil Kepala Sekolah", "Tata Usaha", "Unit Perpustakaan", "Guru Kelas", "Guru Olahraga", "Guru B. Inggris", "Guru BTQ", "Guru PJOK", "Guru Pramuka", "Penjaga Sekolah" }));
        cbjabatan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjabatan1ActionPerformed(evt);
            }
        });
        cbjabatan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbjabatan1KeyPressed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("ID Pegawai");

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

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel39.setText("Jenis Kelamin");

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtid1)
                    .addComponent(txtnama1)
                    .addComponent(cbjabatan1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbjenispegawai1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtnip1)
                    .addComponent(cbkelamin1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(simpan1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(batal1))
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtid1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnama1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbkelamin1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbjabatan1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbjenispegawai1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnip1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpan1)
                    .addComponent(batal1))
                .addContainerGap())
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Edit Data Pegawai");

        txtlabel2.setEditable(false);
        txtlabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtlabel2.setCaretColor(java.awt.Color.lightGray);
        txtlabel2.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtlabel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlabel2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editPegawaiLayout = new javax.swing.GroupLayout(editPegawai);
        editPegawai.setLayout(editPegawaiLayout);
        editPegawaiLayout.setHorizontalGroup(
            editPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPegawaiLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(editPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtlabel2)
                    .addGroup(editPegawaiLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 794, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        editPegawaiLayout.setVerticalGroup(
            editPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPegawaiLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(editPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(editPegawaiLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(editPegawai, "card2");

        add(mainpanel, "card2");

        jPanel1.setLayout(new java.awt.CardLayout());

        mainpanel1.setLayout(new java.awt.CardLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        tabelpegawai1.setModel(new javax.swing.table.DefaultTableModel(
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
        ));
        tabelpegawai1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpegawai1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelpegawai1);

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
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N

        ubah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        ubah1.setText("Edit");
        ubah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubah1ActionPerformed(evt);
            }
        });

        hapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hapus.png"))); // NOI18N
        hapus1.setText("Hapus");
        hapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapus1ActionPerformed(evt);
            }
        });

        cancel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        cancel1.setText("Batal");
        cancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel1ActionPerformed(evt);
            }
        });

        tambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        tambah1.setText("Tambah");
        tambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambah1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(tambah1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ubah1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapus1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcari1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(ubah1)
                        .addComponent(hapus1)
                        .addComponent(cancel1)
                        .addComponent(tambah1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Data Pegawai");

        txtlabel3.setEditable(false);
        txtlabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtlabel3.setCaretColor(java.awt.Color.lightGray);
        txtlabel3.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtlabel3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlabel3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataPegawai1Layout = new javax.swing.GroupLayout(dataPegawai1);
        dataPegawai1.setLayout(dataPegawai1Layout);
        dataPegawai1Layout.setHorizontalGroup(
            dataPegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPegawai1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(dataPegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtlabel3)
                    .addGroup(dataPegawai1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 845, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        dataPegawai1Layout.setVerticalGroup(
            dataPegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPegawai1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dataPegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(dataPegawai1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel1.add(dataPegawai1, "card2");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        jPanel7.setToolTipText("");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("Nama Pegawai");

        txtid2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtid2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtid2ActionPerformed(evt);
            }
        });
        txtid2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtid2KeyPressed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setText("Jenis Pegawai");

        txtnip2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnip2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnip2ActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel30.setText("NIP / NIKKI");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel31.setText("Jabatan");

        cbjenispegawai2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CPNS", "PNS", "PPPK", "KKI", "HONOR", "Tidak Ada" }));
        cbjenispegawai2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjenispegawai2ActionPerformed(evt);
            }
        });
        cbjenispegawai2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbjenispegawai2KeyPressed(evt);
            }
        });

        simpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        simpan2.setText("Save");
        simpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan2ActionPerformed(evt);
            }
        });

        batal2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        batal2.setText("Cancel");
        batal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batal2ActionPerformed(evt);
            }
        });

        cbjabatan2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kepala Sekolah", "Wakil Kepala Sekolah", "Guru Kelas", "Guru Penjasorkes", "Guru P A I", "Operator Sekolah", "Penjaga Sekolah" }));
        cbjabatan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjabatan2ActionPerformed(evt);
            }
        });
        cbjabatan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbjabatan2KeyPressed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setText("ID Pegawai");

        txtnama2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnama2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnama2ActionPerformed(evt);
            }
        });
        txtnama2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnama2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtid2)
                    .addComponent(txtnama2)
                    .addComponent(cbjabatan2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbjenispegawai2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(simpan2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(batal2))
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtnip2))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addGap(10, 10, 10)
                .addComponent(txtid2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel28)
                .addGap(10, 10, 10)
                .addComponent(txtnama2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel31)
                .addGap(10, 10, 10)
                .addComponent(cbjabatan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addGap(10, 10, 10)
                .addComponent(cbjenispegawai2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel30)
                .addGap(10, 10, 10)
                .addComponent(txtnip2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpan2)
                    .addComponent(batal2))
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Tambah Data Pegawai");

        txtlabel4.setEditable(false);
        txtlabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtlabel4.setCaretColor(java.awt.Color.lightGray);
        txtlabel4.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtlabel4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlabel4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tambahpegawai1Layout = new javax.swing.GroupLayout(tambahpegawai1);
        tambahpegawai1.setLayout(tambahpegawai1Layout);
        tambahpegawai1Layout.setHorizontalGroup(
            tambahpegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambahpegawai1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(tambahpegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtlabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tambahpegawai1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 747, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tambahpegawai1Layout.setVerticalGroup(
            tambahpegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahpegawai1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(tambahpegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(tambahpegawai1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel1.add(tambahpegawai1, "card2");

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        jPanel8.setToolTipText("");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel33.setText("Nama Pegawai");

        txtid3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtid3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtid3ActionPerformed(evt);
            }
        });
        txtid3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtid3KeyPressed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel34.setText("Jenis Pegawai");

        txtnip3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnip3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnip3ActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel35.setText("NIP / NIKKI");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel36.setText("Jabatan");

        cbjenispegawai3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CPNS", "PNS", "PPPK", "KKI", "HONOR", "Tidak Ada" }));
        cbjenispegawai3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjenispegawai3ActionPerformed(evt);
            }
        });
        cbjenispegawai3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbjenispegawai3KeyPressed(evt);
            }
        });

        simpan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        simpan3.setText("Save");
        simpan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan3ActionPerformed(evt);
            }
        });

        batal3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        batal3.setText("Cancel");
        batal3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batal3ActionPerformed(evt);
            }
        });

        cbjabatan3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kepala Sekolah", "Wakil Kepala Sekolah", "Guru Kelas", "Guru Penjasorkes", "Guru P A I", "Operator Sekolah", "Penjaga Sekolah" }));
        cbjabatan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjabatan3ActionPerformed(evt);
            }
        });
        cbjabatan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbjabatan3KeyPressed(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setText("ID Pegawai");

        txtnama3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtnama3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnama3ActionPerformed(evt);
            }
        });
        txtnama3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnama3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtid3)
                    .addComponent(txtnama3)
                    .addComponent(cbjabatan3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbjenispegawai3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtnip3)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(simpan3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(batal3)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel37)
                .addGap(10, 10, 10)
                .addComponent(txtid3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel33)
                .addGap(10, 10, 10)
                .addComponent(txtnama3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel36)
                .addGap(10, 10, 10)
                .addComponent(cbjabatan3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addGap(10, 10, 10)
                .addComponent(cbjenispegawai3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel35)
                .addGap(10, 10, 10)
                .addComponent(txtnip3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpan3)
                    .addComponent(batal3))
                .addContainerGap())
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("Edit Data Pegawai");

        txtlabel5.setEditable(false);
        txtlabel5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtlabel5.setCaretColor(java.awt.Color.lightGray);
        txtlabel5.setDisabledTextColor(new java.awt.Color(240, 240, 240));
        txtlabel5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtlabel5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editPegawai1Layout = new javax.swing.GroupLayout(editPegawai1);
        editPegawai1.setLayout(editPegawai1Layout);
        editPegawai1Layout.setHorizontalGroup(
            editPegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPegawai1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(editPegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtlabel5)
                    .addGroup(editPegawai1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 794, Short.MAX_VALUE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        editPegawai1Layout.setVerticalGroup(
            editPegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPegawai1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(editPegawai1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(editPegawai1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel1.add(editPegawai1, "card2");

        jPanel1.add(mainpanel1, "card2");

        add(jPanel1, "card3");
    }// </editor-fold>//GEN-END:initComponents

    private void tabelpegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpegawaiMouseClicked
        int selectedRowIndex = tabelpegawai.getSelectedRow();
        int selectedModelRowIndex = tabelpegawai.convertRowIndexToModel(selectedRowIndex);
        if (selectedModelRowIndex != -1) {
            DefaultTableModel model = (DefaultTableModel) tabelpegawai.getModel();

            // Mendapatkan nilai dari setiap kolom pada baris yang dipilih
            String a = model.getValueAt(selectedModelRowIndex, 1).toString();  // id_pegawai
            String b = model.getValueAt(selectedModelRowIndex, 2).toString();  // nama_pegawai
            String c = model.getValueAt(selectedModelRowIndex, 3).toString();  // kelamin
            String d = model.getValueAt(selectedModelRowIndex, 4).toString();  // jabatan
            String e = model.getValueAt(selectedModelRowIndex, 5).toString();  // jenis_pegawai
            String f = model.getValueAt(selectedModelRowIndex, 6).toString();  // nip

            // Mengisi data pada form berdasarkan baris yang dipilih
            txtid1.setText(a);
            txtnama1.setText(b);

            // Menyesuaikan nilai kelamin berdasarkan "L" atau "P"
            if ("L".equals(c)) {
                cbkelamin1.setSelectedItem("Laki-Laki");
            } else if ("P".equals(c)) {
                cbkelamin1.setSelectedItem("Perempuan");
            } else {
                cbkelamin1.setSelectedItem("");  // Jika tidak ada kecocokan, kosongkan
            }

            // Mengisi data lainnya
            cbjabatan1.setSelectedItem(d);
            cbjenispegawai1.setSelectedItem(e);
            txtnip1.setText(f);
        }

        // Menonaktifkan editing pada tabel setelah memilih
        setTableNonEditable();
    }//GEN-LAST:event_tabelpegawaiMouseClicked
    private void setTableNonEditable() {
        for (int i = 0; i < tabelpegawai.getColumnCount(); i++) {
            Class<?> col_class = tabelpegawai.getColumnClass(i);
            tabelpegawai.setDefaultEditor(col_class, null); // Menetapkan editor default ke null untuk semua kolom
        }
    }
    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed

    }//GEN-LAST:event_txtcariKeyPressed

    private void txtlabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabelActionPerformed

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        DefaultTableModel obj = (DefaultTableModel) tabelpegawai.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        tabelpegawai.setRowSorter(obj1);

        try {
            String searchText = txtcari.getText();
            obj1.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));  // Case-insensitive filter
        } catch (PatternSyntaxException e) {
            obj1.setRowFilter(null);  // Reset filter jika terjadi kesalahan pada regex
        }
    }//GEN-LAST:event_txtcariKeyReleased

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
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariFocusLost

    private void txtidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidActionPerformed

    private void txtidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidKeyPressed

    private void txtnipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnipActionPerformed

    private void cbjenispegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjenispegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjenispegawaiActionPerformed

    private void cbjenispegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbjenispegawaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjenispegawaiKeyPressed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        try {

            String kelamin;
            if (cbkelamin.getSelectedItem().toString().trim().equalsIgnoreCase("Laki-Laki")) {
                kelamin = "L";
            } else {
                kelamin = "P";
            }

            String query = "INSERT INTO pegawai (id_pegawai, nama_pegawai, kelamin, jabatan, jenis_pegawai, nip) VALUES (?,?,?,?,?,?)";
            Boolean insert = SQLConnection.doPreparedUpdate(
                    query,
                    txtid.getText(),
                    txtnama.getText(),
                    kelamin,
                    cbjabatan.getSelectedItem().toString(),
                    cbjenispegawai.getSelectedItem().toString(),
                    txtnip.getText()
            );

            if (Boolean.TRUE.equals(insert)) {
                Pegawai.showSuccessDialog("Data berhasil disimpan");
                kosong();
                autonumber();
                batalActionPerformed(null);
                datatable();
            }
        } catch (SQLException e) {
            Pegawai.showErrorDialog("Data gagal disimpan: " + e);
        }
    }//GEN-LAST:event_simpanActionPerformed

    private void ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(editPegawai);
        mainpanel.repaint();
        mainpanel.revalidate();        // TODO add your handling code here:
    }//GEN-LAST:event_ubahActionPerformed

    private void batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataPegawai);
        mainpanel.repaint();
        mainpanel.revalidate();       // TODO add your handling code here:
    }//GEN-LAST:event_batalActionPerformed

    private void cbjabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjabatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjabatanActionPerformed

    private void cbjabatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbjabatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjabatanKeyPressed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        int barisTerpilih = tabelpegawai.getSelectedRow();

        if (barisTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            int konfirmasi = JOptionPane.showConfirmDialog(null, "<html>Apakah Anda Akan Menghapus Data Pegawai  <b>" + txtnama1.getText() + "</b> ?</html>",
                    "Konfirmasi Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (konfirmasi == JOptionPane.YES_OPTION) {

                try {
                    String query = "DELETE FROM pegawai WHERE id_pegawai = ?";
                    Boolean delete = SQLConnection.doPreparedUpdate(query, txtid1.getText());

                    if (Boolean.TRUE.equals(delete)) {
                        Pegawai.showSuccessDialog("data berhasil dihapus");
                        datatable();
                        txtcari.setText("Pencarian");
                    }
                } catch (SQLException e) {
                    Pegawai.showErrorDialog("data gagal dihapus" + e);
                }
            }
        }
    }//GEN-LAST:event_hapusActionPerformed

    private void txtnamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamaActionPerformed

    private void txtnamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamaKeyPressed

    private void txtlabel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel1ActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        try {
            tabelpegawai.clearSelection();
            tabelpegawai.setRowSorter(null);
            datatable();
            txtcari.setText("Pencarian");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saat memuat ulang data: " + e.getMessage());
        }
    }//GEN-LAST:event_cancelActionPerformed

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(tambahpegawai);
        mainpanel.repaint();
        mainpanel.revalidate();
        tabelpegawai.clearSelection();
    }//GEN-LAST:event_tambahActionPerformed

    private void txtid1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtid1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtid1ActionPerformed

    private void txtid1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtid1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtid1KeyPressed

    private void txtnip1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnip1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnip1ActionPerformed

    private void cbjenispegawai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjenispegawai1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjenispegawai1ActionPerformed

    private void cbjenispegawai1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbjenispegawai1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjenispegawai1KeyPressed

    private void simpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan1ActionPerformed
        try {
            // Mendapatkan nilai kelamin
            String kelamin;
            if (cbkelamin1.getSelectedItem().toString().trim().equalsIgnoreCase("Laki-Laki")) {
                kelamin = "L";
            } else {
                kelamin = "P";
            }

            // Query update
            String query = "UPDATE pegawai SET nama_pegawai=?, kelamin=?,"
                    + "jabatan=?, jenis_pegawai=?, nip=? WHERE id_pegawai=?";

            // Melakukan update dengan urutan parameter yang benar
            Boolean update = SQLConnection.doPreparedUpdate(
                    query,
                    txtnama1.getText().trim(), // nama_pegawai
                    kelamin, // kelamin
                    cbjabatan1.getSelectedItem().toString(), // jabatan
                    cbjenispegawai1.getSelectedItem().toString(), // jenis_pegawai
                    txtnip1.getText().trim(), // nip
                    txtid1.getText().trim() // id_pegawai untuk WHERE
            );

            // Cek hasil update
            if (Boolean.TRUE.equals(update)) {
                Pegawai.showSuccessDialog("Data berhasil diubah!");
                kosong1(); // Membersihkan input
                batal1ActionPerformed(null); // Reset form
            }

        } catch (SQLException e) {
            // Menampilkan pesan kesalahan
            Pegawai.showErrorDialog("Data gagal diubah: " + e.getMessage());
            e.printStackTrace(); // Debugging tambahan
        }

        // Refresh tabel
        datatable();
        kosong();
    }//GEN-LAST:event_simpan1ActionPerformed

    private void batal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batal1ActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataPegawai);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_batal1ActionPerformed

    private void cbjabatan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjabatan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjabatan1ActionPerformed

    private void cbjabatan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbjabatan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjabatan1KeyPressed

    private void txtnama1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnama1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnama1ActionPerformed

    private void txtnama1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnama1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnama1KeyPressed

    private void txtlabel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel2ActionPerformed

    private void tabelpegawai1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpegawai1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelpegawai1MouseClicked

    private void txtcari1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcari1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1FocusGained

    private void txtcari1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcari1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1FocusLost

    private void txtcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcari1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1ActionPerformed

    private void txtcari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1KeyPressed

    private void txtcari1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcari1KeyReleased

    private void ubah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubah1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ubah1ActionPerformed

    private void hapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapus1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hapus1ActionPerformed

    private void cancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancel1ActionPerformed

    private void tambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambah1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tambah1ActionPerformed

    private void txtlabel3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel3ActionPerformed

    private void txtid2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtid2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtid2ActionPerformed

    private void txtid2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtid2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtid2KeyPressed

    private void txtnip2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnip2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnip2ActionPerformed

    private void cbjenispegawai2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjenispegawai2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjenispegawai2ActionPerformed

    private void cbjenispegawai2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbjenispegawai2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjenispegawai2KeyPressed

    private void simpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simpan2ActionPerformed

    private void batal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batal2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_batal2ActionPerformed

    private void cbjabatan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjabatan2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjabatan2ActionPerformed

    private void cbjabatan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbjabatan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjabatan2KeyPressed

    private void txtnama2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnama2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnama2ActionPerformed

    private void txtnama2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnama2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnama2KeyPressed

    private void txtlabel4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel4ActionPerformed

    private void txtid3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtid3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtid3ActionPerformed

    private void txtid3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtid3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtid3KeyPressed

    private void txtnip3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnip3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnip3ActionPerformed

    private void cbjenispegawai3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjenispegawai3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjenispegawai3ActionPerformed

    private void cbjenispegawai3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbjenispegawai3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjenispegawai3KeyPressed

    private void simpan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simpan3ActionPerformed

    private void batal3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batal3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_batal3ActionPerformed

    private void cbjabatan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjabatan3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjabatan3ActionPerformed

    private void cbjabatan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbjabatan3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbjabatan3KeyPressed

    private void txtnama3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnama3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnama3ActionPerformed

    private void txtnama3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnama3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnama3KeyPressed

    private void txtlabel5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel5ActionPerformed

    private void cbkelaminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelaminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelaminActionPerformed

    private void cbkelaminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbkelaminKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelaminKeyPressed

    private void cbkelamin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkelamin1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelamin1ActionPerformed

    private void cbkelamin1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbkelamin1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkelamin1KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton batal;
    private javax.swing.JButton batal1;
    private javax.swing.JButton batal2;
    private javax.swing.JButton batal3;
    private javax.swing.JButton cancel;
    private javax.swing.JButton cancel1;
    private javax.swing.JComboBox<String> cbjabatan;
    private javax.swing.JComboBox<String> cbjabatan1;
    private javax.swing.JComboBox<String> cbjabatan2;
    private javax.swing.JComboBox<String> cbjabatan3;
    private javax.swing.JComboBox<String> cbjenispegawai;
    private javax.swing.JComboBox<String> cbjenispegawai1;
    private javax.swing.JComboBox<String> cbjenispegawai2;
    private javax.swing.JComboBox<String> cbjenispegawai3;
    private javax.swing.JComboBox<String> cbkelamin;
    private javax.swing.JComboBox<String> cbkelamin1;
    private javax.swing.JPanel dataPegawai;
    private javax.swing.JPanel dataPegawai1;
    private javax.swing.JPanel editPegawai;
    private javax.swing.JPanel editPegawai1;
    private javax.swing.JButton hapus;
    private javax.swing.JButton hapus1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel mainpanel1;
    private javax.swing.JButton simpan;
    private javax.swing.JButton simpan1;
    private javax.swing.JButton simpan2;
    private javax.swing.JButton simpan3;
    private javax.swing.JTable tabelpegawai;
    private javax.swing.JTable tabelpegawai1;
    private javax.swing.JButton tambah;
    private javax.swing.JButton tambah1;
    private javax.swing.JPanel tambahpegawai;
    private javax.swing.JPanel tambahpegawai1;
    private javax.swing.ButtonGroup txtbutton;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtcari1;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtid1;
    private javax.swing.JTextField txtid2;
    private javax.swing.JTextField txtid3;
    private javax.swing.JTextField txtlabel;
    private javax.swing.JTextField txtlabel1;
    private javax.swing.JTextField txtlabel2;
    private javax.swing.JTextField txtlabel3;
    private javax.swing.JTextField txtlabel4;
    private javax.swing.JTextField txtlabel5;
    private javax.swing.JTextField txtnama;
    private javax.swing.JTextField txtnama1;
    private javax.swing.JTextField txtnama2;
    private javax.swing.JTextField txtnama3;
    private javax.swing.JTextField txtnip;
    private javax.swing.JTextField txtnip1;
    private javax.swing.JTextField txtnip2;
    private javax.swing.JTextField txtnip3;
    private javax.swing.JButton ubah;
    private javax.swing.JButton ubah1;
    // End of variables declaration//GEN-END:variables
}
