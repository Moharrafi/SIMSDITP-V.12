package simsditp;

import Database.SQLConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class spp extends javax.swing.JPanel {

    private DefaultTableModel tabmode;

    public spp() {
        initComponents();
        muatDataSiswa();
        tampilkanDataSiswaDanStatus();
        datatable();
        addComboBoxListener();
        txtlabel.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtlabel1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtlabel2.setBackground(new java.awt.Color(0, 0, 0, 1));
        jlabel.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcari.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtcari1.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnipd.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnisn.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtnama.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtkelas.setBackground(new java.awt.Color(0, 0, 0, 1));
        tabelsiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dapatkan baris yang diklik
                int row = tabelsiswa.getSelectedRow();
                // Tampilkan tombol edit, hapus, dan batal jika ada baris yang dipilih
                if (row != -1) {
                    batal.setVisible(true);
                    tambah.setVisible(true); // Sembunyikan tombol tambah
                } else {
                    batal.setVisible(false);
                    tambah.setVisible(false); // Tampilkan tombol tambah kembali
                }
            }
        });

        batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Sembunyikan tombol-tombol
                batal.setVisible(false);
                tabelsiswa.clearSelection();
                tambah.setVisible(true);
                kosong();
            }
        });

        keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Sembunyikan tombol-tombol
                batal.setVisible(false);
                tabelsiswa.clearSelection();
                tambah.setVisible(true);
                kosong();
            }
        });
    }

    protected void kosong() {
        txtnipd.setText("");
        txtnama.setText("");
        jtanggal.setDate(null);
        txtbutton.clearSelection();
    }

    private void addComboBoxListener() {
        jkelas.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    tampilkanDataSiswaDanStatus();
                }
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

    private List<Map<String, Object>> dataSiswa = new ArrayList<>();

    public void muatDataSiswa() {
        // Query untuk memuat data siswa yang statusnya "Aktif"
        String query = "SELECT s.nipd, s.nisn, s.nama_siswa, s.kelas, s.status_bantuan, "
                + "SUM(CASE WHEN sp.komponen = 'SPP' THEN sp.jumlah_bayar ELSE 0 END) AS totalSPPBayar, "
                + "SUM(CASE WHEN sp.komponen = 'Kegiatan Bulanan' THEN sp.jumlah_bayar ELSE 0 END) AS totalKegiatanBayar "
                + "FROM siswa s "
                + "LEFT JOIN spp sp ON s.nisn = sp.nisn "
                + "WHERE s.status = 'Aktif' " // Pastikan hanya siswa dengan status "Aktif" yang dimuat
                + "GROUP BY s.nipd, s.nisn, s.nama_siswa, s.kelas, s.status_bantuan";

        try (ResultSet result = SQLConnection.doQuery(query)) {
            while (result.next()) {
                // Simpan data siswa ke dalam list
                Map<String, Object> siswa = new HashMap<>();
                siswa.put("nipd", result.getString("nipd"));
                siswa.put("nisn", result.getString("nisn"));
                siswa.put("nama_siswa", result.getString("nama_siswa"));
                siswa.put("kelas", result.getString("kelas"));
                siswa.put("status_bantuan", result.getString("status_bantuan"));
                siswa.put("totalSPPBayar", result.getLong("totalSPPBayar"));
                siswa.put("totalKegiatanBayar", result.getLong("totalKegiatanBayar"));
                dataSiswa.add(siswa);
            }
        } catch (SQLException e) {
            spp.showErrorDialog("Gagal memuat data siswa: " + e.getMessage());
            e.printStackTrace();  // Untuk keperluan debugging
        }
    }

    public void tampilkanDataSiswaDanStatus() {
        // Header tabel
        Object[] kolom = {"No", "NIPD", "NISN", "Nama Siswa", "Kelas", "Nominal"};
        DefaultTableModel tabmode = new DefaultTableModel(null, kolom);

        // Dapatkan kelas yang dipilih
        String kelasDipilih = (String) jkelas.getSelectedItem();

        // Urutkan data siswa berdasarkan kelas (ascending)
        dataSiswa.sort((s1, s2) -> {
            String kelas1 = (String) s1.get("kelas");
            String kelas2 = (String) s2.get("kelas");
            return kelas1.compareToIgnoreCase(kelas2);
        });

        // Mendapatkan bulan sekarang
        Calendar today = Calendar.getInstance();
        String bulanSekarang = String.valueOf(today.get(Calendar.MONTH) + 1);

        int no = 1;
        for (Map<String, Object> siswa : dataSiswa) {
            String kelas = (String) siswa.get("kelas");
            String status = (String) siswa.get("status");

            // Pastikan hanya siswa dengan status "Aktif" yang ditampilkan
            if ("Tidak Aktif".equalsIgnoreCase(status)) {
                continue;
            }

            // Filter data berdasarkan kelas yang dipilih, jika ada
            if (kelasDipilih != null && !kelasDipilih.equals("-- Pilih Kelas --") && !kelasDipilih.equals(kelas)) {
                continue;
            }

            // Ambil data siswa
            String nipd = (String) siswa.get("nipd");
            String nisn = (String) siswa.get("nisn");
            String namaSiswa = (String) siswa.get("nama_siswa");

            // Hitung total tunggakan menggunakan getRincianPembayaran, dengan nisn dan bulan
            List<Object[]> rincian = getRincianPembayaran(nisn, bulanSekarang);  // Menambahkan bulanSekarang sebagai parameter
            long totalTunggakan = rincian.stream()
                    .mapToLong(row -> parseRupiah((String) row[3]))
                    .sum();

            // Jika tidak ada tunggakan, lewati siswa ini
            if (totalTunggakan <= 0) {
                continue;
            }

            // Format total tunggakan ke format ribuan dengan Rp
            String totalTunggakanFormatted = String.format("Rp %,d", totalTunggakan);

            // Tambahkan data siswa ke tabel siswa
            tabmode.addRow(new Object[]{no++, nipd, nisn, namaSiswa, kelas, totalTunggakanFormatted});
        }

        // Set model untuk tabel siswa
        tabelsiswa.setModel(tabmode);
        styletabel();
        batal.setVisible(false);
        tambah.setVisible(false);
    }

    private void styletabel() {
        tabelsiswa.setRowHeight(25); // Tinggi baris
        tabelsiswa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelsiswa.setForeground(Color.BLACK);

        // Header
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

        // Atur lebar kolom dengan lebih fleksibel
        int[] columnWidths = {10, 30, 80, 280, 40, 80}; // Lebar untuk setiap kolom
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

//        tabelsiswa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk isi tabel

        for (int i = 0; i < tabelsiswa.getColumnCount(); i++) {
            if (i != 6) {
                tabelsiswa.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }
        }

        // Alternating row colors
        tabelsiswa.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
    }

    protected void datatable() {
        // Header tabel
        Object[] Baris = {"NIPD", "NISN", "Nama Siswa", "Kelas", "Periode", "Bayar", "Tanggal Transaksi", "Komponen", "Status"};
        DefaultTableModel tabmode = new DefaultTableModel(null, Baris);

        // Query SQL
        String query = "SELECT nipd, nisn, nama_siswa, kelas, periode, jumlah_bayar, tanggal, komponen, status FROM spp ORDER BY tanggal DESC";

        try (ResultSet result = SQLConnection.doQuery(query)) {
            // Format untuk tanggal
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault());

            // Format untuk Rupiah (tanpa desimal)
            DecimalFormat rupiahFormat = new DecimalFormat("###,###");

            // Iterasi hasil query
            while (result.next()) {
                String nipd = result.getString("nipd");
                String nisn = result.getString("nisn");
                String namaSiswa = result.getString("nama_siswa");
                String kelas = result.getString("kelas");
                String periode = result.getString("periode");
                long jumlahBayar = result.getLong("jumlah_bayar");  // Ambil sebagai long
                String tanggal = result.getString("tanggal");
                String komponen = result.getString("komponen");
                String status = result.getString("status");

                // Format jumlah_bayar ke Rupiah dengan tanda ribuan
                String jumlahFormatted = "Rp " + rupiahFormat.format(jumlahBayar);

                // Validasi data null
                if (nipd != null && nisn != null && namaSiswa != null && kelas != null && periode != null
                        && jumlahFormatted != null && tanggal != null && komponen != null && status != null) {
                    String formattedTanggal = "";

                    // Format tanggal
                    try {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
                        formattedTanggal = sdf.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        formattedTanggal = tanggal;
                    }

                    // Tambahkan data ke tabel
                    tabmode.addRow(new Object[]{nipd, nisn, namaSiswa, kelas, periode, jumlahFormatted, formattedTanggal, komponen, status});
                } else {
                    System.err.println("Data null ditemukan, lewati baris.");
                }
            }
            // Set model tabel
            tabelstatus.setModel(tabmode);

            // Pengaturan tampilan tabel
            aturTampilanTabel();

        } catch (SQLException e) {
            spp.showErrorDialog("Data gagal dipanggil: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void aturTampilanTabel() {
        tabelstatus.setRowHeight(30);
        tabelstatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelstatus.setForeground(Color.BLACK);

        JTableHeader header = tabelstatus.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 123, 255));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBackground(new Color(0, 123, 255));
        headerRenderer.setForeground(Color.WHITE);

        TableColumnModel columnModel = tabelstatus.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
        }

        columnModel.getColumn(0).setPreferredWidth(4);  // nipd
        columnModel.getColumn(1).setPreferredWidth(10);  // Nisn
        columnModel.getColumn(2).setPreferredWidth(125); // Nama Siswa
        columnModel.getColumn(3).setPreferredWidth(10);  // kelas
        columnModel.getColumn(4).setPreferredWidth(10);  // periode
        columnModel.getColumn(5).setPreferredWidth(20);  // bayar
        columnModel.getColumn(6).setPreferredWidth(50); // tanggal
        columnModel.getColumn(7).setPreferredWidth(50); // komponen
        columnModel.getColumn(8).setPreferredWidth(18); // status

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tabelstatus.getColumnCount(); i++) {
            tabelstatus.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);

        }

        tabelstatus.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {
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
        }
        );
    }

    protected void tabelrincian(String nisn) {
        // Mendefinisikan nama kolom tabel
        Object[] Baris = {"No", "Komponen", "Periode", "Tunggakan", "Jumlah Bayar", ""};
        DefaultTableModel tabmode = new DefaultTableModel(null, Baris);
        tabelrincian.setModel(tabmode);

        // Mendapatkan bulan sekarang
        Calendar calendar = Calendar.getInstance();
        String bulanSekarang = String.valueOf(calendar.get(Calendar.MONTH) + 1); // Mengonversi int ke String
        List<Object[]> data = getRincianPembayaran(nisn, bulanSekarang);  // Mengirimkan nisn dan bulan sebagai String

        // Menambahkan data ke tabel
        for (Object[] row : data) {
            tabmode.addRow(row);
        }

        // Menetapkan renderer dan editor untuk kolom terakhir (kolom 5)
        tabelrincian.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tabelrincian.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JTextField(), tabmode, tabelrincian));

        // Mengatur gaya tampilan tabel
        tabelrincian.setRowHeight(28);
        tabelrincian.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelrincian.setForeground(Color.BLACK);

        // Mengatur header tabel
        JTableHeader header = tabelrincian.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 123, 255));  // Warna biru
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        // Renderer untuk header kolom agar terlihat lebih baik
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBackground(new Color(0, 123, 255));  // Warna biru
        headerRenderer.setForeground(Color.WHITE);

        TableColumnModel columnModel = tabelrincian.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Renderer untuk isi tabel agar rata tengah
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah untuk isi tabel

        // Mengatur rendering cell tabel kecuali kolom terakhir
        for (int i = 0; i < tabelrincian.getColumnCount(); i++) {
            if (i != 5) {  // Kolom terakhir tidak diberi renderer
                tabelrincian.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }
        }

        // Menambahkan warna bergantian pada baris tabel untuk tampilan yang lebih baik
        tabelrincian.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    component.setBackground(new Color(240, 240, 240));  // Abu muda untuk baris genap
                } else {
                    component.setBackground(Color.WHITE);  // Putih untuk baris ganjil
                }
                if (isSelected) {
                    component.setBackground(new Color(173, 216, 230));  // Biru muda saat dipilih
                }
                return component;
            }
        });
    }

    // Pastikan metode ini ada dalam kelas spp atau kelas yang mengelola data siswa
    private String getKelasSiswa(String nisn) {
        // Misalnya, dataSiswa adalah list yang menyimpan data siswa
        for (Map<String, Object> siswa : dataSiswa) {
            if (siswa.get("nisn").equals(nisn)) {
                return (String) siswa.get("kelas");  // Mengembalikan kelas siswa berdasarkan NISN
            }
        }
        return null;  // Return null jika NISN tidak ditemukan
    }

    private Map<String, Integer> bulanMap = new HashMap<String, Integer>() {
        {
            put("Januari", 1);
            put("Februari", 2);
            put("Maret", 3);
            put("April", 4);
            put("Mei", 5);
            put("Juni", 6);
            put("Juli", 7);
            put("Agustus", 8);
            put("September", 9);
            put("Oktober", 10);
            put("November", 11);
            put("Desember", 12);
        }
    };

    private List<Object[]> getRincianPembayaran(String nisn, String bulanSekarang) {
        List<Object[]> data = new ArrayList<>();
        int bulanSekarangInt = Integer.parseInt(bulanSekarang);  // Mengonversi bulanSekarang ke int (sekarang dari input)

        String kelasSiswa = getKelasSiswa(nisn);  // Ambil kelas siswa berdasarkan nisn
        int sppTunggakanPerBulan = (kelasSiswa.equals("1") || kelasSiswa.equals("2")) ? 150000 : 100000;
        int kegiatanTunggakanPerBulan = 150000;

        // Query untuk mengambil rincian pembayaran (sudah sesuai dengan pemrosesan tunggakan)
        try {
            String query = "SELECT s.nisn, s.status_bantuan, p.komponen, p.periode, SUM(p.jumlah_bayar) AS totalBayar "
                    + "FROM siswa s LEFT JOIN spp p ON s.nisn = p.nisn "
                    + "WHERE s.nisn = '" + nisn + "' "
                    + "GROUP BY p.komponen, p.periode, s.status_bantuan";
            ResultSet rs = SQLConnection.doQuery(query);

            Map<String, Integer> pembayaranSPP = new HashMap<>();
            Map<String, Integer> pembayaranKegiatan = new HashMap<>();
            String statusBantuan = "Tidak"; // Default

            while (rs.next()) {
                if (rs.getString("status_bantuan") != null) {
                    statusBantuan = rs.getString("status_bantuan");
                }
                String komponen = rs.getString("komponen");
                String periode = rs.getString("periode");
                int totalBayar = rs.getInt("totalBayar");

                if ("SPP".equalsIgnoreCase(komponen)) {
                    pembayaranSPP.put(periode, totalBayar);
                } else if ("K.Bulanan".equalsIgnoreCase(komponen)) {
                    pembayaranKegiatan.put(periode, totalBayar);
                }
            }
            rs.close();

            // Jika status bantuan "Ya", kurangi biaya SPP sebesar 100000
            if ("Ya".equalsIgnoreCase(statusBantuan)) {
                sppTunggakanPerBulan -= 100000;
            }

            int nomorUrut = 1;

            for (int i = 1; i <= bulanSekarangInt; i++) {
                String namaBulan = getNamaBulan(i - 1); // Ambil nama bulan dari 0-11
                int bulan = bulanMap.getOrDefault(namaBulan, -1);

                if (bulan != -1) {
                    // SPP Tunggakan
                    int sisaTunggakanSPP = sppTunggakanPerBulan - pembayaranSPP.getOrDefault(namaBulan, 0);
                    if (sisaTunggakanSPP > 0) {
                        data.add(new Object[]{nomorUrut++, "SPP", namaBulan, formatRupiah(sisaTunggakanSPP), "", "Isi Tunggakan"});
                    }

                    // Kegiatan Bulanan Tunggakan
                    int sisaTunggakanKegiatan = kegiatanTunggakanPerBulan - pembayaranKegiatan.getOrDefault(namaBulan, 0);
                    if (sisaTunggakanKegiatan > 0) {
                        data.add(new Object[]{nomorUrut++, "Kegiatan Bulanan", namaBulan, formatRupiah(sisaTunggakanKegiatan), "", "Isi Tunggakan"});
                    }
                } else {
                    System.out.println("Bulan tidak valid: " + namaBulan);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private String getNamaBulan(int bulan) {
        String[] namaBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        return namaBulan[bulan];
    }

    private static String formatRupiah(long amount) {
        return String.valueOf(amount);
    }

    private static int parseRupiah(String rupiah) {
        String cleanString = rupiah.replace("Rp", "").replace(" ", "").replace(".", "").replace(",", "");
        try {
            return Integer.parseInt(cleanString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;

        }
    }

    static class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            return this;
        }
    }

    static class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean isPushed;
        private DefaultTableModel model;
        private JTable tabelrincian;

        public ButtonEditor(JTextField textField, DefaultTableModel model, JTable tabelrincian) {
            super(textField);
            this.model = model;
            this.tabelrincian = tabelrincian; // Initialize JTable
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(Color.GREEN);
            button.setForeground(Color.WHITE);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                String tunggakanStr = (String) model.getValueAt(tabelrincian.getSelectedRow(), 3);  // Kolom Tunggakan

                int tunggakan = parseRupiah(tunggakanStr);

                model.setValueAt(formatRupiah(tunggakan), tabelrincian.getSelectedRow(), 4); // Format menjadi angka tanpa simbol "Rp"
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
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
        dataspp = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelsiswa = new javax.swing.JTable();
        txtcari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tambah = new javax.swing.JButton();
        batal = new javax.swing.JButton();
        jkelas = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        riwayat = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtlabel = new javax.swing.JTextField();
        tambahspp = new javax.swing.JPanel();
        jlabel = new javax.swing.JLabel();
        txtlabel1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtnipd = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        keluar = new javax.swing.JButton();
        simpan = new javax.swing.JButton();
        jtanggal = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelrincian = new javax.swing.JTable();
        txtkelas = new javax.swing.JTextField();
        txtnisn = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        statusbayar = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelstatus = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtcari1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        batal1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
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

        batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/batal.png"))); // NOI18N
        batal.setText("Batal");
        batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalActionPerformed(evt);
            }
        });

        jkelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Kelas --", "1", "2", "3", "4", "5", "6" }));
        jkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkelasActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Daftar Siswa");

        riwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        riwayat.setText("Riwayat Transaksi");
        riwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riwayatActionPerformed(evt);
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
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(tambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(batal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(riwayat))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 480, Short.MAX_VALUE)
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
                            .addComponent(batal)
                            .addComponent(jkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(riwayat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(txtcari, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Data SPP");

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

        javax.swing.GroupLayout datasppLayout = new javax.swing.GroupLayout(dataspp);
        dataspp.setLayout(datasppLayout);
        datasppLayout.setHorizontalGroup(
            datasppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datasppLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(datasppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtlabel)
                    .addGroup(datasppLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 1060, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        datasppLayout.setVerticalGroup(
            datasppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datasppLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(datasppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(datasppLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(dataspp, "card2");

        jlabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlabel.setText("Pembayaran");

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

        jLabel22.setText("Rincian Tagihan :");

        jLabel23.setText("Nama Siswa");

        txtnipd.setEditable(false);
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

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("NIPD");

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

        jLabel21.setText("Tanggal Bayar");

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

        tabelrincian.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tabelrincian);

        txtkelas.setEditable(false);
        txtkelas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkelasActionPerformed(evt);
            }
        });
        txtkelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkelasKeyPressed(evt);
            }
        });

        txtnisn.setEditable(false);
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

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("NISN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnipd)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1132, Short.MAX_VALUE)
                    .addComponent(txtkelas)
                    .addComponent(txtnisn)
                    .addComponent(txtnama)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jtanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(simpan)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(keluar)))
                            .addComponent(jLabel24)
                            .addComponent(jLabel20)
                            .addComponent(jLabel23))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(0, 0, 0)
                .addComponent(txtnipd, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addGap(0, 0, 0)
                .addComponent(txtnisn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addGap(0, 0, 0)
                .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addGap(0, 0, 0)
                .addComponent(txtkelas, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout tambahsppLayout = new javax.swing.GroupLayout(tambahspp);
        tambahspp.setLayout(tambahsppLayout);
        tambahsppLayout.setHorizontalGroup(
            tambahsppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahsppLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(tambahsppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tambahsppLayout.createSequentialGroup()
                        .addComponent(jlabel)
                        .addGap(0, 1004, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtlabel1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        tambahsppLayout.setVerticalGroup(
            tambahsppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahsppLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(tambahsppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlabel)
                    .addGroup(tambahsppLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainpanel.add(tambahspp, "card2");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        tabelstatus.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelstatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelstatusMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabelstatus);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Daftar Riwayat Pembayaran");

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

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N

        batal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/kembali.png"))); // NOI18N
        batal1.setText("Kembali");
        batal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batal1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1132, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(batal1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(batal1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setText("Riwayat Transaksi");

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

        javax.swing.GroupLayout statusbayarLayout = new javax.swing.GroupLayout(statusbayar);
        statusbayar.setLayout(statusbayarLayout);
        statusbayarLayout.setHorizontalGroup(
            statusbayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusbayarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(statusbayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtlabel2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, statusbayarLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 942, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        statusbayarLayout.setVerticalGroup(
            statusbayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusbayarLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(statusbayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(statusbayarLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(statusbayar, "card2");

        add(mainpanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void tabelsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswaMouseClicked
        int selectedRowIndex = tabelsiswa.getSelectedRow();
        int selectedModelRowIndex = tabelsiswa.convertRowIndexToModel(selectedRowIndex);

        if (selectedModelRowIndex != -1) {
            DefaultTableModel model = (DefaultTableModel) tabelsiswa.getModel();
            String nipd = model.getValueAt(selectedModelRowIndex, 1).toString();
            String nisn = model.getValueAt(selectedModelRowIndex, 2).toString();
            String namaSiswa = model.getValueAt(selectedModelRowIndex, 3).toString();
            String kelas = model.getValueAt(selectedModelRowIndex, 4).toString();

            // Menghapus "Rp" dan mengganti titik (.) dengan koma (,) agar bisa diproses oleh NumberFormat
            String nominalStr = model.getValueAt(selectedModelRowIndex, 5).toString()
                    .replace("Rp", "") // Hapus Rp
                    .replace(".", "") // Hapus titik pemisah ribuan
                    .trim();

            int nominal = 0;
            try {
                // Gunakan NumberFormat untuk parsing
                NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                Number number = format.parse(nominalStr);
                nominal = number.intValue();
            } catch (ParseException e) {
                e.printStackTrace(); // Cetak error jika parsing gagal
            }

            txtnipd.setText(nipd);
            txtnisn.setText(nisn);
            txtnama.setText(namaSiswa);
            txtkelas.setText(kelas);

            // Perbarui tabel rincian berdasarkan Nominal
            tabelrincian(nisn);
        }

        batal.setVisible(true);
        tambah.setVisible(true);
        setTableNonEditable();
        txtnipd.setEditable(false);
        txtnama.setEditable(false);
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
        }
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

        mainpanel.add(tambahspp);
        mainpanel.repaint();
        mainpanel.revalidate();

        tabelsiswa.clearSelection();

        int selectedRowIndex = tabelsiswa.getSelectedRow();
        if (selectedRowIndex != -1) {
            DefaultTableModel model = (DefaultTableModel) tabelsiswa.getModel();
            String nisn = model.getValueAt(selectedRowIndex, 1).toString(); // Ambil nisn siswa

            if (nisn != null && !nisn.isEmpty()) {
                // Memanggil fungsi tabelrincian untuk memperbarui data rincian pembayaran
                tabelrincian(nisn);
            } else {
                JOptionPane.showMessageDialog(null, "NISN tidak valid. Silakan pilih siswa yang benar!");
            }
        } else {
        }
        tabelrincian(txtnisn.getText());
    }//GEN-LAST:event_tambahActionPerformed

// Contoh fungsi untuk mengambil nominal dan pembayaran berdasarkan NISN
    private int getNominalSPP(String nisn) {
        // Implementasikan logika untuk mengambil nominal SPP berdasarkan nisn
        return 300000; // Misalnya nominal SPP 300000
    }

    private int getNominalJasa(String nisn) {
        // Implementasikan logika untuk mengambil nominal Uang Jasa berdasarkan nisn
        return 150000; // Misalnya nominal Uang Jasa 150000
    }

    private int getPembayaranSPP(String nisn) {
        // Implementasikan logika untuk mengambil pembayaran SPP berdasarkan nisn
        return 100000; // Misalnya pembayaran SPP sudah 100000
    }

    private int getPembayaranJasa(String nisn) {
        // Implementasikan logika untuk mengambil pembayaran Uang Jasa berdasarkan nisn
        return 50000; // Misalnya pembayaran Uang Jasa sudah 50000
    }

    private void batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalActionPerformed
        try {
            // Path ke GIF loading
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
            textLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Mengatur font
            textLabel.setForeground(Color.BLACK); // Warna teks agar kontras dengan transparansi

            // Membuat panel transparan untuk menampung GIF dan teks
            JPanel loadingPanel = new JPanel(new BorderLayout());
            loadingPanel.setOpaque(false); // Membuat panel transparan
            loadingPanel.add(loadingLabel, BorderLayout.CENTER);
            loadingPanel.add(textLabel, BorderLayout.SOUTH);

            // Membuat dialog loading dengan background transparan
            JDialog loadingDialog = new JDialog();
            loadingDialog.setUndecorated(true); // Menghilangkan border
            loadingDialog.setModal(false); // Tidak memblokir UI
            loadingDialog.setLayout(new BorderLayout());
            loadingDialog.setBackground(new Color(0, 0, 0, 0)); // Membuat transparan
            loadingDialog.add(loadingPanel, BorderLayout.CENTER);
            loadingDialog.pack();
            loadingDialog.setLocationRelativeTo(null);

            // SwingWorker untuk menangani proses pemuatan ulang data
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    tampilkanDataSiswaDanStatus();
                    return null;
                }

                @Override
                protected void done() {
                    SwingUtilities.invokeLater(() -> {
                        loadingDialog.dispose(); // Tutup loading setelah selesai
                        txtcari.setText("Pencarian");
                        tabelsiswa.clearSelection();
                        tabelsiswa.setRowSorter(null);
                    });
                }
            };

            // Menjalankan loading dialog dan memulai proses
            SwingUtilities.invokeLater(() -> {
                loadingDialog.setVisible(true);
                worker.execute();
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saat memuat ulang data: " + e.getMessage());
        }
    }//GEN-LAST:event_batalActionPerformed

    private void jtanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtanggal1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtanggal1KeyPressed

    private void jtanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtanggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtanggalKeyPressed

    private void txtnamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamaKeyPressed

    }//GEN-LAST:event_txtnamaKeyPressed

    private void txtnamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamaActionPerformed

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
        // Path ke GIF loading
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
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Mengatur font
        textLabel.setForeground(Color.BLACK); // Warna teks agar kontras dengan transparansi

        // Membuat panel transparan untuk menampung GIF dan teks
        JPanel loadingPanel = new JPanel(new BorderLayout());
        loadingPanel.setOpaque(false); // Membuat panel transparan
        loadingPanel.add(loadingLabel, BorderLayout.CENTER);
        loadingPanel.add(textLabel, BorderLayout.SOUTH);

        // Membuat dialog loading dengan background transparan
        JDialog loadingDialog = new JDialog();
        loadingDialog.setUndecorated(true); // Menghilangkan border
        loadingDialog.setModal(false); // Tidak memblokir UI
        loadingDialog.setLayout(new BorderLayout());
        loadingDialog.setBackground(new Color(0, 0, 0, 0)); // Membuat transparan
        loadingDialog.add(loadingPanel, BorderLayout.CENTER);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(null);

        // SwingWorker untuk menangani proses pemuatan ulang data
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                return null;
            }

            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    // Hapus panel lama dan tampilkan panel dataspp
                    mainpanel.removeAll();
                    mainpanel.add(dataspp); // Asumsi dataspp adalah JPanel Anda
                    mainpanel.repaint();
                    mainpanel.revalidate();

                    // Tampilkan data siswa dan status
                    tampilkanDataSiswaDanStatus();

                    // Tutup loading dialog setelah selesai
                    loadingDialog.dispose();
                });
            }
        };

        // Tampilkan loading dialog dan mulai worker
        SwingUtilities.invokeLater(() -> {
            loadingDialog.setVisible(true);
            worker.execute();
        });
    }//GEN-LAST:event_keluarActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = (jtanggal.getDate() != null) ? sdf.format(jtanggal.getDate()) : "";

        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tanggal tidak boleh kosong!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tabelrincian.getModel();

        try {
            int totalBayar = 0;
            boolean isLunas = true;

            for (int i = 0; i < model.getRowCount(); i++) {
                String komponen = model.getValueAt(i, 1).toString();
                String periode = model.getValueAt(i, 2).toString();
                String tunggakanStr = model.getValueAt(i, 3) != null ? model.getValueAt(i, 3).toString() : "0";
                String jumlahBayarStr = model.getValueAt(i, 4) != null ? model.getValueAt(i, 4).toString() : "0";

                if (tunggakanStr.isEmpty()) {
                    tunggakanStr = "0";
                }
                if (jumlahBayarStr.isEmpty()) {
                    jumlahBayarStr = "0";
                }

                int tunggakan = parseRupiah(tunggakanStr);
                int jumlahBayar = parseRupiah(jumlahBayarStr);

                if (jumlahBayar > 0) {
                    String status = (tunggakan - jumlahBayar == 0) ? "Lunas" : "Nunggak";

                    if ("Kegiatan Bulanan".equalsIgnoreCase(komponen)) {
                        komponen = "K.Bulanan";
                    }

                    String query = "INSERT INTO spp (nipd, nisn, nama_siswa, kelas, periode, jumlah_bayar,"
                            + " tanggal, komponen, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    SQLConnection.doPreparedUpdate(
                            query,
                            txtnipd.getText(),
                            txtnisn.getText(),
                            txtnama.getText(),
                            txtkelas.getText(),
                            periode,
                            jumlahBayar,
                            date,
                            komponen,
                            status
                    );

                    tunggakan -= jumlahBayar;

                    if (tunggakan > 0) {
                        isLunas = false;
                        model.setValueAt(formatRupiah(tunggakan), i, 3);
                        model.setValueAt("", i, 4);
                    } else {
                        model.removeRow(i);
                        i--;
                    }

                    totalBayar += jumlahBayar;
                } else {
                    isLunas = false;
                }
            }

            String status = isLunas ? "Lunas" : "Nunggak";
            spp.showSuccessDialog("Data berhasil disimpan dengan status: " + status);

            // Path ke GIF loading
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
            textLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Mengatur font
            textLabel.setForeground(Color.BLACK); // Warna teks agar kontras dengan transparansi

            // Membuat panel transparan untuk menampung GIF dan teks
            JPanel loadingPanel = new JPanel(new BorderLayout());
            loadingPanel.setOpaque(false); // Membuat panel transparan
            loadingPanel.add(loadingLabel, BorderLayout.CENTER);
            loadingPanel.add(textLabel, BorderLayout.SOUTH);

            // Membuat dialog loading dengan background transparan
            JDialog loadingDialog = new JDialog();
            loadingDialog.setUndecorated(true); // Menghilangkan border
            loadingDialog.setModal(false); // Tidak memblokir UI
            loadingDialog.setLayout(new BorderLayout());
            loadingDialog.setBackground(new Color(0, 0, 0, 0)); // Membuat transparan
            loadingDialog.add(loadingPanel, BorderLayout.CENTER);
            loadingDialog.pack();
            loadingDialog.setLocationRelativeTo(null);

            // SwingWorker untuk menangani proses pemuatan ulang data
            // SwingWorker untuk menangani proses pemuatan ulang data
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    tampilkanDataSiswaDanStatus();
                    return null;
                }

                @Override
                protected void done() {
                    SwingUtilities.invokeLater(() -> {
                        keluarActionPerformed(null);
                        dataSiswa.clear(); // Hapus data lama
                        muatDataSiswa(); // Muat data terbaru
                        tampilkanDataSiswaDanStatus();
                        jtanggal.setDate(null);
                        loadingDialog.dispose();
                    });
                }
            };

            // Menjalankan loading dialog dan memulai proses
            SwingUtilities.invokeLater(() -> {
                loadingDialog.setVisible(true);
                worker.execute();
            });

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_simpanActionPerformed

//    private void tampilkanLoadingGif() {
    //        // Path lokasi gambar loading
    //        String gifPath = "/img/loading.gif"; // Sesuaikan lokasi
    //
    //        // Ambil URL gambar dari resource
    //        URL gifURL = getClass().getResource(gifPath);
    //        if (gifURL == null) {
    //            System.err.println("GIF loading tidak ditemukan: " + gifPath);
    //            retrn;
    //        }
    //
    //        // Buat ikon loading
    //        ImageIcon loadingIcon = new ImageIcon(gifURL);
    //        JLabel loadingLabel = new JLabel("Menyimpan data...", loadingIcon, JLabel.CENTER);
    //        loadingLabel.setHorizontalTextPosition(JLabel.CENTER);
    //        loadingLabel.setVerticalTextPosition(JLabel.BOTTOM);
    //
    //        // Buat dialog loading
    //        JOptionPane optionPane = new JOptionPane("", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    //        JDialog loadingDialog = optionPane.createDialog("Loading");
    //        loadingDialog.setModal(false);
    //        loadingDialog.add(loadingLabel, BorderLayout.CENTER);
    //        loadingDialog.pack();
    //        loadingDialog.setLocationRelativeTo(null);
    //
    //        // Gunakan SwingWorker agar tidak menghambat UI
    //        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
    //            @Override
    //            protected Void doInBackground() throws Exception {
    //                Thread.sleep(2000); // Simulasi loading selama 2 detik
    //                return null;
    //            }
    //
    //            @Override
    //            protected void done() {
    //                SwingUtilities.invokeLater(() -> loadingDialog.dispose()); // Tutup loading setelah selesai
    //            }
    //        };
    //
    //        // Tampilkan dialog di thread Swing
    //        SwingUtilities.invokeLater(() -> {
    //            loadingDialog.setVisible(true);
    //            worker.execute();
    //        });
    //    }

    private void txtnipdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnipdKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtnama.requestFocus();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtnipdKeyPressed

    private void txtnipdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnipdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnipdActionPerformed

    private void txtlabel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtlabel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtlabel1ActionPerformed

    private void jkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkelasActionPerformed
        jkelas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tampilkanDataSiswaDanStatus();
            }
        });
    }//GEN-LAST:event_jkelasActionPerformed

    private void tabelstatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelstatusMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelstatusMouseClicked

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
        DefaultTableModel obj = (DefaultTableModel) tabelstatus.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        tabelstatus.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(txtcari1.getText()));
        obj1.setRowFilter(RowFilter.regexFilter("(?i)" + txtcari1.getText()));
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

    private void riwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riwayatActionPerformed
        try {
            System.out.println("Mengakses panel statusbayar...");
            mainpanel.removeAll();
            mainpanel.repaint();
            mainpanel.revalidate();

            mainpanel.add(statusbayar);
            mainpanel.repaint();
            mainpanel.revalidate();

            tabelsiswa.clearSelection();
            datatable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_riwayatActionPerformed

    private void batal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batal1ActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataspp);
        mainpanel.repaint();
        mainpanel.revalidate();
        tampilkanDataSiswaDanStatus();
    }//GEN-LAST:event_batal1ActionPerformed

    private void txtkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkelasActionPerformed

    private void txtkelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkelasKeyPressed

    private void txtnisnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnisnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnisnActionPerformed

    private void txtnisnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnisnKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnisnKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton batal;
    private javax.swing.JButton batal1;
    private javax.swing.JPanel dataspp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JComboBox<String> jkelas;
    private javax.swing.JLabel jlabel;
    private com.toedter.calendar.JDateChooser jtanggal;
    private javax.swing.JButton keluar;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JButton riwayat;
    private javax.swing.JButton simpan;
    private javax.swing.JPanel statusbayar;
    private javax.swing.JTable tabelrincian;
    private javax.swing.JTable tabelsiswa;
    private javax.swing.JTable tabelstatus;
    private javax.swing.JButton tambah;
    private javax.swing.JPanel tambahspp;
    private javax.swing.ButtonGroup txtbutton;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtcari1;
    private javax.swing.JTextField txtkelas;
    private javax.swing.JTextField txtlabel;
    private javax.swing.JTextField txtlabel1;
    private javax.swing.JTextField txtlabel2;
    private javax.swing.JTextField txtnama;
    private javax.swing.JTextField txtnipd;
    private javax.swing.JTextField txtnisn;
    // End of variables declaration//GEN-END:variables
}
