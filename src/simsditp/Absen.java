package simsditp;

import Database.SQLConnection;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Absen extends javax.swing.JPanel {

    public Absen() {
        initComponents();
        updateCombo();
        updateCombo2();
        txtlabel.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtlabel2.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcari.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcari1.setBackground(new java.awt.Color(0, 0, 0, 1));

        tabelsiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int row = tabelsiswa.getSelectedRow();
                if (row != -1) {
                    batal1.setVisible(true);
                } else {

                    batal1.setVisible(false);
                }
            }
        });
    }

    public static void showSuccessDialog(String message) {
        ImageIcon checkIcon = new ImageIcon("src/img/check.png");

        JOptionPane.showMessageDialog(null, message, "Sukses",
                JOptionPane.INFORMATION_MESSAGE, checkIcon);
    }

    public static void showErrorDialog(String message) {
        ImageIcon checkIcon = new ImageIcon("src/img/gagal.png");

        JOptionPane.showMessageDialog(null, message, "Error",
                JOptionPane.INFORMATION_MESSAGE, checkIcon);
    }

    private void updateCombo() {

        jkelas1.removeAllItems();

        jkelas1.addItem("-- Pilih Kelas --");

        try {
            String query = "SELECT * from kelas";
            ResultSet rs = SQLConnection.doQuery(query);
            while (rs.next()) {
                jkelas1.addItem(rs.getString("kelas"));
            }
        } catch (SQLException e) {
        }

        jkelas1.setSelectedItem("-- Pilih Kelas --");

        jkelas1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jkelas1.getSelectedItem() != null && jkelas1.getSelectedItem().equals("kelas")) {
                    jkelas1.removeItem("-- Pilih Kelas --");
                    jkelas1.revalidate();
                    jkelas1.repaint();
                }
            }
        });
    }

    private void updateCombo2() {

        jkelas.removeAllItems();

        jkelas.addItem("-- Pilih Kelas --");

        try {
            String query = "SELECT * from kelas";
            ResultSet rs = SQLConnection.doQuery(query);
            while (rs.next()) {
                jkelas.addItem(rs.getString("kelas"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        jkelas.setSelectedItem("-- Pilih Kelas --");

        jkelas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jkelas.getSelectedItem() != null && jkelas.getSelectedItem().equals("kelas")) {
                    jkelas.removeItem("-- Pilih Kelas --");
                    jkelas.revalidate();
                    jkelas.repaint();
                }
            }
        });
    }

    public void tampilkanDataSiswa() {

        String kelas = (String) jkelas.getSelectedItem();

        try {

            String query = "SELECT nipd, nisn, nama_siswa, kelamin, kelas FROM siswa"
                    + " WHERE kelas = ? AND status = 'Aktif'";
            ResultSet rs = SQLConnection.doPreparedQuery(
                    query,
                    kelas
            );

            DefaultTableModel model = new DefaultTableModel(new Object[]{"No", "NIPD", "NISN", "Nama Siswa", "Jenis Kelamin", "Kelas", "Presensi", "Keterangan"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {

                    return column == 6 || column == 7;
                }
            };
            tabelsiswa.setModel(model);

            int no = 1;
            while (rs.next()) {
                String nipd = rs.getString("nipd");
                String nisn = rs.getString("nisn");
                String namaSiswa = rs.getString("nama_siswa");
                String jenisKelamin = rs.getString("kelamin");
                String Kelas = rs.getString("kelas");

                model.addRow(new Object[]{no++, nipd, nisn, namaSiswa, jenisKelamin, Kelas, "Hadir", ""});
            }

            JComboBox<String> comboBox = new JComboBox<>(new String[]{"Hadir", "Alpa", "Izin"});
            TableColumn presensiColumn = tabelsiswa.getColumnModel().getColumn(6);
            presensiColumn.setCellEditor(new DefaultCellEditor(comboBox));

            presensiColumn.setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JComboBox<String> comboBox = new JComboBox<>(new String[]{"Hadir", "Alpa", "Izin"});
                    comboBox.setSelectedItem(value);
                    if (isSelected) {
                        comboBox.setBackground(table.getSelectionBackground());
                    } else {
                        comboBox.setBackground(table.getBackground());
                    }
                    return comboBox;
                }
            });

            tabelsiswa.setRowHeight(30);
            tabelsiswa.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            tabelsiswa.setForeground(Color.BLACK);

            JTableHeader header = tabelsiswa.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 14));
            header.setBackground(new Color(0, 123, 255));
            header.setForeground(Color.WHITE);
            header.setReorderingAllowed(false);

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            headerRenderer.setBackground(new Color(0, 123, 255));
            headerRenderer.setForeground(Color.WHITE);

            TableColumnModel columnModel = tabelsiswa.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            int[] columnWidths = {50, 80, 100, 200, 100, 80, 150, 120};
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
            }

            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            for (int i = 0; i < tabelsiswa.getColumnCount(); i++) {
                if (i != 6) {
                    tabelsiswa.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
                }
            }

            tabelsiswa.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (column != 6) {
                        if (row % 2 == 0) {
                            component.setBackground(new Color(240, 240, 240));
                        } else {
                            component.setBackground(Color.WHITE);
                        }
                    }
                    if (isSelected) {
                        component.setBackground(new Color(173, 216, 230));
                    }
                    return component;
                }
            });
            batal1.setVisible(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void tampilkanDataAbsen() {

        String kelas = (String) jkelas1.getSelectedItem();

        java.util.Date currentDate = new java.util.Date();
        java.sql.Date tanggal = new java.sql.Date(currentDate.getTime());

        try {
            String query = "SELECT nipd, nisn, nama_siswa, kelamin, kelas, presensi, keterangan, tanggal FROM absen WHERE kelas = ? AND tanggal = ?";
            ResultSet rs = SQLConnection.doPreparedQuery(
                    query,
                    kelas,
                    tanggal
            );

            DefaultTableModel model = new DefaultTableModel(new Object[]{"No", "NIPD", "NISN", "Nama Siswa", "Jenis Kelamin", "Kelas", "Presensi", "Keterangan", "Tanggal"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {

                    return column == 6 || column == 7;
                }
            };
            tabelsiswa1.setModel(model);

            int no = 1;
            while (rs.next()) {
                String nipd = rs.getString("nipd");
                String nisn = rs.getString("nisn");
                String namaSiswa = rs.getString("nama_siswa");
                String jenisKelamin = rs.getString("kelamin");
                String Kelas = rs.getString("kelas");
                String presensi = rs.getString("presensi");
                String keterangan = rs.getString("keterangan");
                String tanggalStr = rs.getString("tanggal");

                model.addRow(new Object[]{no++, nipd, nisn, namaSiswa, jenisKelamin, Kelas, presensi, keterangan, tanggalStr});
            }

            TableColumn presensiColumn = tabelsiswa1.getColumnModel().getColumn(6);
            JComboBox<String> comboBox = new JComboBox<>(new String[]{"Hadir", "Alpa", "Izin"});
            presensiColumn.setCellEditor(new DefaultCellEditor(comboBox));

            presensiColumn.setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JComboBox<String> comboBox = new JComboBox<>(new String[]{"Hadir", "Alpa", "Izin"});
                    comboBox.setSelectedItem(value);
                    if (isSelected) {
                        comboBox.setBackground(table.getSelectionBackground());
                    } else {
                        comboBox.setBackground(table.getBackground());
                    }
                    return comboBox;
                }
            });

            tabelsiswa1.setRowHeight(30);
            tabelsiswa1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            tabelsiswa1.setForeground(Color.BLACK);

            JTableHeader header = tabelsiswa1.getTableHeader();
            header.setFont(new Font("Segoe UI", Font.BOLD, 14));
            header.setBackground(new Color(0, 123, 255));
            header.setForeground(Color.WHITE);
            header.setReorderingAllowed(false);

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            headerRenderer.setBackground(new Color(0, 123, 255));
            headerRenderer.setForeground(Color.WHITE);

            TableColumnModel columnModel = tabelsiswa1.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }

            int[] columnWidths = {50, 80, 100, 280, 100, 60, 130, 100, 80};
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
            }

            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            for (int i = 0; i < tabelsiswa1.getColumnCount(); i++) {
                if (i != 6) {
                    tabelsiswa1.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
                }
            }

            tabelsiswa1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (row % 2 == 0) {
                        component.setBackground(new Color(240, 240, 240));
                    } else {
                        component.setBackground(Color.WHITE);
                    }
                    if (isSelected) {
                        component.setBackground(new Color(173, 216, 230));
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
        dataabsen = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelsiswa = new javax.swing.JTable();
        txtcari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tambah = new javax.swing.JButton();
        jkelas = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        edit = new javax.swing.JButton();
        batal1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtlabel = new javax.swing.JTextField();
        editabsen = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtcari1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tambah1 = new javax.swing.JButton();
        jkelas1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelsiswa1 = new javax.swing.JTable();
        batal = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtlabel2 = new javax.swing.JTextField();

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
        ));
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

        tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        tambah.setText("Simpan");
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

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Murid Kelas");

        edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        edit.setText("Edit");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        batal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        batal1.setText("Batal");
        batal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batal1ActionPerformed(evt);
            }
        });

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
                        .addComponent(edit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(batal1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 824, Short.MAX_VALUE)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(edit)
                            .addComponent(batal1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4))
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Data Absen");

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

        javax.swing.GroupLayout dataabsenLayout = new javax.swing.GroupLayout(dataabsen);
        dataabsen.setLayout(dataabsenLayout);
        dataabsenLayout.setHorizontalGroup(
            dataabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataabsenLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(dataabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtlabel)
                    .addGroup(dataabsenLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 972, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        dataabsenLayout.setVerticalGroup(
            dataabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataabsenLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(dataabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(dataabsenLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(dataabsen, "card2");

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

        tambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        tambah1.setText("Simpan");
        tambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambah1ActionPerformed(evt);
            }
        });

        jkelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkelas1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Murid Kelas");

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
        ));
        tabelsiswa1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsiswa1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelsiswa1);

        batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/kembali.png"))); // NOI18N
        batal.setText("Kembali");
        batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(tambah1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(batal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 824, Short.MAX_VALUE)
                        .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tambah1)
                            .addComponent(jkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(batal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtcari1)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                        .addGap(8, 8, 8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Edit Absen");

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

        javax.swing.GroupLayout editabsenLayout = new javax.swing.GroupLayout(editabsen);
        editabsen.setLayout(editabsenLayout);
        editabsenLayout.setHorizontalGroup(
            editabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editabsenLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(editabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtlabel2)
                    .addGroup(editabsenLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 981, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        editabsenLayout.setVerticalGroup(
            editabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editabsenLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(editabsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(editabsenLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(editabsen, "card2");

        add(mainpanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtlabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabelActionPerformed

    }//GEN-LAST:event_txtlabelActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked

    }//GEN-LAST:event_jPanel3MouseClicked

    private void tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahActionPerformed
        String kelasSelected = (String) jkelas.getSelectedItem();
        if (kelasSelected.equals("-- Pilih Kelas --")) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih data yang ingin disimpan");
        } else {
            int rowCount = tabelsiswa.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, "Silahkan pilih data yang ingin disimpan");
            } else {
                try {

                    java.util.Date currentDate = new java.util.Date();
                    java.sql.Date tanggal = new java.sql.Date(currentDate.getTime());

                    String checkSql = "SELECT COUNT(*) FROM absen WHERE tanggal = ? AND kelas = ?";
                    ResultSet rs = SQLConnection.doPreparedQuery(checkSql, tanggal, kelasSelected);

                    rs.next();
                    int count = rs.getInt(1);

                    if (count == 0) {

                        String queryAbsen = "INSERT INTO absen (nipd, nisn, nama_siswa, kelamin, kelas, presensi, keterangan, tanggal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                        for (int i = 0; i < rowCount; i++) {
                            String nipd = tabelsiswa.getValueAt(i, 1).toString();
                            String nisn = tabelsiswa.getValueAt(i, 2).toString();
                            String namaSiswa = tabelsiswa.getValueAt(i, 3).toString();
                            String jenisKelamin = tabelsiswa.getValueAt(i, 4).toString();
                            String kelas = tabelsiswa.getValueAt(i, 5).toString();
                            String presensi = tabelsiswa.getValueAt(i, 6).toString();
                            String keterangan = tabelsiswa.getValueAt(i, 7).toString();

                            SQLConnection.doPreparedUpdate(
                                    queryAbsen,
                                    nipd,
                                    nisn,
                                    namaSiswa,
                                    jenisKelamin,
                                    kelas,
                                    presensi,
                                    keterangan,
                                    tanggal
                            );
                        }
                        Absen.showSuccessDialog("Absen berhasil ditambahkan");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data absensi untuk kelas ini pada hari ini sudah ada.");
                    }

                    SQLConnection.closeConnection();
                } catch (SQLException ex) {
                    System.out.println(ex);
                    Absen.showErrorDialog("Absen Gagal Ditambahkan " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }

    }//GEN-LAST:event_tambahActionPerformed

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped

    }//GEN-LAST:event_txtcariKeyTyped

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        DefaultTableModel obj = (DefaultTableModel) tabelsiswa.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        tabelsiswa.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(txtcari.getText()));
        obj1.setRowFilter(RowFilter.regexFilter("(?i)" + txtcari.getText()));
    }//GEN-LAST:event_txtcariKeyReleased

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed

    }//GEN-LAST:event_txtcariKeyPressed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed

    }//GEN-LAST:event_txtcariActionPerformed

    private void txtcariMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcariMouseExited

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
        }
    }//GEN-LAST:event_txtcariFocusGained

    private void tabelsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswaMouseClicked

    }//GEN-LAST:event_tabelsiswaMouseClicked

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

    }//GEN-LAST:event_txtcari1MouseClicked

    private void txtcari1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcari1MouseExited

    }//GEN-LAST:event_txtcari1MouseExited

    private void txtcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcari1ActionPerformed

    }//GEN-LAST:event_txtcari1ActionPerformed

    private void txtcari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari1KeyPressed

    }//GEN-LAST:event_txtcari1KeyPressed

    private void txtcari1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari1KeyReleased
        DefaultTableModel obj = (DefaultTableModel) tabelsiswa1.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        tabelsiswa1.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(txtcari1.getText()));
        obj1.setRowFilter(RowFilter.regexFilter("(?i)" + txtcari1.getText()));
    }//GEN-LAST:event_txtcari1KeyReleased

    private void txtcari1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari1KeyTyped

    }//GEN-LAST:event_txtcari1KeyTyped

    private void tambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambah1ActionPerformed
        int rowCount = tabelsiswa1.getRowCount();

        if (rowCount > 0) {
            try {

                String sql = "UPDATE absen SET presensi = ?, keterangan = ? WHERE nisn = ? AND tanggal = ?";

                java.util.Date currentDate = new java.util.Date();
                java.sql.Date tanggal = new java.sql.Date(currentDate.getTime());

                boolean adaPerubahan = false;

                for (int i = 0; i < rowCount; i++) {
                    String nisn = tabelsiswa1.getValueAt(i, 2).toString();
                    String presensi = tabelsiswa1.getValueAt(i, 6).toString();
                    String keterangan = tabelsiswa1.getValueAt(i, 7).toString();

                    Boolean update = SQLConnection.doPreparedUpdate(sql, presensi, keterangan, nisn, tanggal);

                    if (Boolean.TRUE.equals(update)) {
                        adaPerubahan = true;
                    }
                }

                if (adaPerubahan) {
                    Absen.showSuccessDialog("Data Presensi Berhasil Diperbarui");
                } else {
                    JOptionPane.showMessageDialog(null, "Tidak ada perubahan data yang diperbarui");
                }

                SQLConnection.closeConnection();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error saat memperbarui data presensi");
                ex.printStackTrace();
            }
        } else {
            System.out.println("Tidak ada baris yang dipilih untuk diperbarui");
        }

    }//GEN-LAST:event_tambah1ActionPerformed

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked

    }//GEN-LAST:event_jPanel4MouseClicked

    private void txtlabel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel2ActionPerformed

    }//GEN-LAST:event_txtlabel2ActionPerformed

    private void jkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkelasActionPerformed
        jkelas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tampilkanDataSiswa();
            }
        });
    }//GEN-LAST:event_jkelasActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(editabsen);
        mainpanel.repaint();
        mainpanel.revalidate();
        jkelas1.setSelectedIndex(0);
    }//GEN-LAST:event_editActionPerformed

    private void jkelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkelas1ActionPerformed
        tampilkanDataAbsen();
    }//GEN-LAST:event_jkelas1ActionPerformed

    private void tabelsiswa1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswa1MouseClicked

    }//GEN-LAST:event_tabelsiswa1MouseClicked

    private void batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataabsen);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_batalActionPerformed

    private void batal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batal1ActionPerformed
        try {
            tabelsiswa.clearSelection();
            tabelsiswa.setRowSorter(null);
            tampilkanDataSiswa();
            txtcari.setText("Pencarian");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saat memuat ulang data: " + e.getMessage());
        }
    }//GEN-LAST:event_batal1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton batal;
    private javax.swing.JButton batal1;
    private javax.swing.JPanel dataabsen;
    private javax.swing.JButton edit;
    private javax.swing.JPanel editabsen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> jkelas;
    private javax.swing.JComboBox<String> jkelas1;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JTable tabelsiswa;
    private javax.swing.JTable tabelsiswa1;
    private javax.swing.JButton tambah;
    private javax.swing.JButton tambah1;
    private javax.swing.ButtonGroup txtbutton;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtcari1;
    private javax.swing.JTextField txtlabel;
    private javax.swing.JTextField txtlabel2;
    // End of variables declaration//GEN-END:variables
}
