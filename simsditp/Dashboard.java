package simsditp;

import Database.Session;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Dashboard extends javax.swing.JFrame {

    public Dashboard() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setCustomIcon();
        updateTahunAkademik();
        execute();

        if (Session.getValidStatus()) {
            String username = Session.getRole();  // Mengambil username dari session
            if (username != null) {
                labelRole.setText(username);  // Menampilkan username di label (atau yang sesuai)
            } else {
                System.err.println("Session.getUserId() returned null.");
            }
        }
    }

    public void updateTahunAkademik() {
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);  // Tahun sekarang
        int currentMonth = now.get(Calendar.MONTH);  // Bulan sekarang (0-11)

        // Tentukan tahun akademik berdasarkan bulan
        String tahunAkademik;
        if (currentMonth < Calendar.JULY) {
            // Jika bulan sebelum Juli, tahun akademik masih tahun ajaran sebelumnya (misalnya 2024/2025)
            tahunAkademik = (currentYear - 1) + "/" + currentYear;
        } else {
            // Jika bulan setelah Juni, tahun akademik sudah berganti (misalnya 2025/2026)
            tahunAkademik = currentYear + "/" + (currentYear + 1);
        }

        // Menampilkan tahun akademik di txttahun (misalnya menggunakan JTextField)
        txttahun.setText(tahunAkademik);
    }

    private void setCustomIcon() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/simsditp.png"));

        // Set gambar kosong sebagai ikon
        this.setIconImage(icon.getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sidebar = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pn_menu = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        navbar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        labelRole = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txttahun = new javax.swing.JLabel();
        content = new javax.swing.JPanel();
        panelutama = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        sidebar.setBackground(new java.awt.Color(255, 255, 255));
        sidebar.setPreferredSize(new java.awt.Dimension(230, 415));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        pn_menu.setBackground(new java.awt.Color(57, 72, 82));
        pn_menu.setLayout(new javax.swing.BoxLayout(pn_menu, javax.swing.BoxLayout.Y_AXIS));
        pn_menu.add(filler1);

        jScrollPane1.setViewportView(pn_menu);

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        getContentPane().add(sidebar, java.awt.BorderLayout.LINE_START);

        navbar.setBackground(new java.awt.Color(44, 121, 199));
        navbar.setPreferredSize(new java.awt.Dimension(713, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simsditp-5.png"))); // NOI18N

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user.png"))); // NOI18N
        jLabel4.setToolTipText("");

        labelRole.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        labelRole.setForeground(new java.awt.Color(255, 255, 255));
        labelRole.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelRole.setText("LEVEL");
        labelRole.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Sekolah Dasar Islam Teladan Pulogadung -");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Sekolah Dasar");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Aplikasi Manajemen");

        txttahun.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txttahun.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout navbarLayout = new javax.swing.GroupLayout(navbar);
        navbar.setLayout(navbarLayout);
        navbarLayout.setHorizontalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txttahun, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navbarLayout.createSequentialGroup()
                        .addComponent(labelRole, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );
        navbarLayout.setVerticalGroup(
            navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(navbarLayout.createSequentialGroup()
                .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(navbarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, navbarLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, 0)
                                .addComponent(labelRole))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, navbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(navbarLayout.createSequentialGroup()
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(jLabel7))
                                .addComponent(txttahun, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        getContentPane().add(navbar, java.awt.BorderLayout.PAGE_START);

        content.setBackground(new java.awt.Color(255, 255, 255));

        panelutama.setBackground(new java.awt.Color(255, 255, 255));
        panelutama.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelutama, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelutama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(content, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(801, 585));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowOpened
        panelutama.add(new Home());
        panelutama.repaint();
        panelutama.revalidate();
    }

    public static void main(String args[]) {
        Login loginWindow = new Login();
        Dashboard dashboardWindow = new Dashboard();

        // Ketika pengguna berhasil login, tutup jendela login dan tampilkan dashboard
        if (Session.getValidStatus()) {
            loginWindow.dispose(); // Tutup jendela login
            dashboardWindow.setVisible(true); // Tampilkan jendela dashboard
        } else {
            // Tampilkan jendela login saat aplikasi dimulai
            loginWindow.setVisible(true);
        }
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelRole;
    private javax.swing.JPanel navbar;
    private javax.swing.JPanel panelutama;
    private javax.swing.JPanel pn_menu;
    private javax.swing.JPanel sidebar;
    private javax.swing.JLabel txttahun;
    // End of variables declaration//GEN-END:variables

    private void execute() {
        ImageIcon iconMaster = new ImageIcon(getClass().getResource("/img/master1.png"));
        ImageIcon iconHome = new ImageIcon(getClass().getResource("/img/home1.png"));
        ImageIcon iconTransaksi = new ImageIcon(getClass().getResource("/img/proses.png"));
        ImageIcon iconReport = new ImageIcon(getClass().getResource("/img/report1.png"));
        ImageIcon iconlogout = new ImageIcon(getClass().getResource("/img/keluar.png"));
        ImageIcon iconsiswa = new ImageIcon(getClass().getResource("/img/siswa1.png"));
        ImageIcon iconguru = new ImageIcon(getClass().getResource("/img/pegawai.png"));
        ImageIcon iconmapel = new ImageIcon(getClass().getResource("/img/buku.png"));
        ImageIcon iconSpp = new ImageIcon(getClass().getResource("/img/spp.png"));
        ImageIcon iconAbsen = new ImageIcon(getClass().getResource("/img/absen1.png"));
        ImageIcon iconNilai = new ImageIcon(getClass().getResource("/img/nilai1.png"));
        ImageIcon iconkelas = new ImageIcon(getClass().getResource("/img/kelas.png"));

        MenuItem masBarang1 = new MenuItem(null, true, iconsiswa, "Siswa", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                Siswa siswa = new Siswa();
                panelutama.add(siswa);
                siswa.setVisible(true);
                panelutama.repaint();
                panelutama.revalidate();

                siswa.kosong();
                siswa.datatable();
            }
        });

        MenuItem masBarang2 = new MenuItem(null, true, iconguru, "Pegawai", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                Pegawai pegawai = new Pegawai();
                panelutama.add(pegawai);
                pegawai.setVisible(true); // Menampilkan Siswa
                panelutama.repaint();
                panelutama.revalidate();

                pegawai.kosong();
                pegawai.datatable();
            }
        });

        MenuItem masBarang3 = new MenuItem(null, true, iconkelas, "Kelas", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                Kelas kelas = new Kelas();
                panelutama.add(kelas);
                kelas.setVisible(true); // Menampilkan Kelas
                panelutama.repaint();
                panelutama.revalidate();

                kelas.tampilkanDataSiswa();
                kelas.updateTabelGuru();
            }
        });

        MenuItem masBarang4 = new MenuItem(null, true, iconmapel, "Mata Pelajaran", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                Mapel mapel = new Mapel();
                panelutama.add(mapel);
                mapel.setVisible(true); // Menampilkan Siswa
                panelutama.repaint();
                panelutama.revalidate();

                mapel.kosong();
                mapel.datatable();
            }
        });

//        MenuItem transaksi1 = new MenuItem(null, true, iconSpp, "SPP", new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                panelutama.removeAll();
//                spp spp = new spp();
//                panelutama.add(spp);
//                spp.setVisible(true); // Menampilkan spp
//                panelutama.repaint();
//                panelutama.revalidate();
//
//            }
//        });
        MenuItem transaksi1 = new MenuItem(null, true, iconSpp, "SPP", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Load GIF dari sumber daya (resource)
                String gifPath = "/img/loading3.gif"; // Sesuaikan lokasi

                URL gifURL = getClass().getResource(gifPath);
                if (gifURL == null) {
                    System.err.println("GIF loading tidak ditemukan: " + gifPath);
                    return;
                }

                // Membuat ikon loading tanpa mengubah ukuran
                ImageIcon loadingIcon = new ImageIcon(gifURL);

                JLabel loadingLabel = new JLabel("Loading...", loadingIcon, JLabel.CENTER);
                loadingLabel.setHorizontalTextPosition(JLabel.CENTER);
                loadingLabel.setVerticalTextPosition(JLabel.BOTTOM);

                // Tampilkan animasi loading di panel utama
                panelutama.removeAll();
                panelutama.add(loadingLabel);
                panelutama.repaint();
                panelutama.revalidate();

                // Gunakan SwingWorker untuk memproses pemuatan panel SPP di background
                SwingWorker<spp, Void> worker = new SwingWorker<spp, Void>() {
                    @Override
                    protected spp doInBackground() throws Exception {
                        // Lakukan proses pemuatan panel SPP
                        return new spp(); // Inisialisasi panel SPP
                    }

                    @Override
                    protected void done() {
                        try {
                            spp sppPanel = get(); // Ambil hasil proses di doInBackground()
                            SwingUtilities.invokeLater(() -> {
                                // Hapus animasi loading dan tampilkan panel SPP
                                panelutama.removeAll();
                                panelutama.add(sppPanel);
                                sppPanel.setVisible(true);
                                panelutama.repaint();
                                panelutama.revalidate();
                            });
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                };

                worker.execute();
            }
        });

        MenuItem transaksi2 = new MenuItem(null, true, iconAbsen, "Absensi", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                Absen absen = new Absen();
                panelutama.add(absen);
                panelutama.repaint();
                panelutama.revalidate();

            }
        });

        MenuItem transaksi3 = new MenuItem(null, true, iconNilai, "Data Nilai", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                Nilai nilai = new Nilai();
                panelutama.add(nilai);
                nilai.setVisible(true);
                panelutama.repaint();
                panelutama.revalidate();

                nilai.tampilkanDataSiswa();
                nilai.updateTabelGuru();
            }
        });

        MenuItem menuHome = new MenuItem(iconHome, false, null, "Dashboard", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                panelutama.add(new Home());
                panelutama.repaint();
                panelutama.revalidate();
            }
        });

        MenuItem menuLogout = new MenuItem(iconlogout, false, null, "Log Out", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (confirmLogout()) {
                    dispose();
                    Login login = new Login();
                    login.setVisible(true);
                    login.revalidate();
                } else {

                }

            }
        });

        MenuItem menuMaster = new MenuItem(iconMaster, false, null, "Master", null, masBarang1, masBarang2, masBarang3,
                masBarang4);
        // MenuItem menuTransaksi = new MenuItem(iconTransaksi, false, null,
        // "Transaksi", null, transaksi1, transaksi2, transaksi3);
        MenuItem report1 = new MenuItem(null, true, iconSpp, "Cetak SPP", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                Cetak_spp cetakspp = new Cetak_spp();
                panelutama.add(cetakspp);
                cetakspp.setVisible(true); // Menampilkan Absen
                panelutama.repaint();
                panelutama.revalidate();

            }
        });

        MenuItem report2 = new MenuItem(null, true, iconsiswa, "Cetak Siswa", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                Cetak_siswa cetaksiswa = new Cetak_siswa();
                panelutama.add(cetaksiswa);
                cetaksiswa.setVisible(true); // Menampilkan Absen
                panelutama.repaint();
                panelutama.revalidate();

            }
        });

        MenuItem report5 = new MenuItem(null, true, iconguru, "Cetak Pegawai", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                Cetak_pegawai cetapegawai = new Cetak_pegawai();
                panelutama.add(cetapegawai);
                cetapegawai.setVisible(true); // Menampilkan Absen
                panelutama.repaint();
                panelutama.revalidate();

            }
        });

        MenuItem report3 = new MenuItem(null, true, iconAbsen, "Cetak Absensi", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                Cetak_absen cetakabsen = new Cetak_absen();
                panelutama.add(cetakabsen);
                cetakabsen.setVisible(true); // Menampilkan Absen
                panelutama.repaint();
                panelutama.revalidate();

            }
        });
        MenuItem report4 = new MenuItem(null, true, iconNilai, "Cetak Nilai", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelutama.removeAll();
                Cetak_nilai nilai = new Cetak_nilai();
                panelutama.add(nilai);
                nilai.setVisible(true); // Menampilkan Absen
                panelutama.repaint();
                panelutama.revalidate();

            }
        });

        String level = Session.getRole();  // Mengambil level yang disimpan di session
        if (level != null && level.equals("Guru")) {
            MenuItem menuTransaksi = new MenuItem(iconTransaksi, false, null, "Transaksi", null, transaksi2, transaksi3);
            addMenu(menuHome, menuTransaksi, menuLogout);
        } else if (level != null && level.equals("Keuangan")) {
            MenuItem menuTransaksi = new MenuItem(iconTransaksi, false, null, "Transaksi", null, transaksi1);
            MenuItem menuReport = new MenuItem(iconReport, false, null, "Report", null, report1);
            addMenu(menuHome, menuTransaksi, menuReport, menuLogout);
        } else {
            MenuItem menuTransaksi = new MenuItem(iconTransaksi, false, null, "Transaksi", null, transaksi1, transaksi2, transaksi3);
            MenuItem menuReport = new MenuItem(iconReport, false, null, "Report", null, report1, report2, report5, report3, report4);
            addMenu(menuHome, menuMaster, menuTransaksi, menuReport, menuLogout);
        }

    }

    void addMenu(MenuItem... menu) {
        for (int i = 0; i < menu.length; i++) {
            pn_menu.add(menu[i]);
            ArrayList<MenuItem> subMenu = menu[i].getSubMenu();
            for (MenuItem m : subMenu) {
                addMenu(m);
            }
        }
        pn_menu.revalidate();
    }

    public boolean confirmLogout() {
        int result = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin logout?", "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

}
