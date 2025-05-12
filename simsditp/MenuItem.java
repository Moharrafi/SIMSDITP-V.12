package simsditp;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Icon;

public class MenuItem extends javax.swing.JPanel {

    Color defaultcolor, clickedcolor, white;
    private boolean showing = false;
    // Mendefinisikan warna yang digunakan
    private Color defaultColor = new Color(57, 72, 82);  // Warna default
    private Color hoverColor = new Color(190, 190, 190);  // Warna saat hover
    private Color clickedColor = new Color(230, 230, 230);  // Warna saat diklik
//    private Color white = new Color(255, 255, 255);  // Warna putih
    private Color black = new Color(0, 0, 0);  // Warna hitam

    public ArrayList<MenuItem> getSubMenu() {
        return subMenu;
    }

    private final ArrayList<MenuItem> subMenu = new ArrayList<>();

    private ActionListener act;
    private static MenuItem activeMenuItem = null;  // Menyimpan menu aktif
    private boolean isHovered = false;  // Menyimpan status hover

    public MenuItem(Icon icon, boolean sbm, Icon iconsub, String namamenu, ActionListener act, MenuItem... SubMenu) {
        initComponents();

        // Warna default
        defaultColor = new Color(57, 72, 82);  // Warna default latar belakang
        hoverColor = new Color(190, 190, 190); // Warna saat hover
        clickedcolor = new Color(255, 255, 255); // Warna saat aktif (putih)
        white = new Color(255, 255, 255); // Warna teks putih
        black = new Color(0, 0, 0); // Warna teks hitam

        lb_icon.setIcon(icon);
        lb_namamenu.setText(namamenu);
        lb_iconsub.setIcon(iconsub);
        lb_iconsub.setVisible(sbm);

        setBackground(defaultColor);
        lb_namamenu.setForeground(white);

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Jika menu tidak aktif, ubah ke warna hover
                if (activeMenuItem != MenuItem.this) {
                    setBackground(hoverColor);
                    lb_namamenu.setForeground(black); // Teks hitam saat hover
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Jika menu tidak aktif, kembalikan ke warna default
                if (activeMenuItem != MenuItem.this) {
                    setBackground(defaultColor);
                    lb_namamenu.setForeground(white); // Teks putih saat tidak aktif
                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                // Reset menu aktif sebelumnya
                if (activeMenuItem != null && activeMenuItem != MenuItem.this) {
                    activeMenuItem.setBackground(defaultColor); // Kembalikan warna menu sebelumnya ke default
                    activeMenuItem.lb_namamenu.setForeground(white); // Teks putih untuk menu sebelumnya
                }

                // Set menu ini sebagai aktif
                if (activeMenuItem != MenuItem.this) {
                    activeMenuItem = MenuItem.this; // Set menu ini sebagai menu aktif
                    setBackground(hoverColor); // Ganti warna latar menu aktif
                    lb_namamenu.setForeground(black); // Ganti teks menu aktif menjadi hitam
                }

                // Jalankan aksi jika tersedia
                if (act != null) {
                    act.actionPerformed(null);
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                // Pastikan warna tetap clicked untuk menu aktif
                if (activeMenuItem == MenuItem.this) {
                    setBackground(hoverColor);
                    lb_namamenu.setForeground(black);
                }
            }
        });

        // Menyimpan sub-menu jika ada
        if (SubMenu != null) {
            subMenu.addAll(Arrays.asList(SubMenu));
        }

        // Atur ukuran menu
        this.setSize(new Dimension(Integer.MAX_VALUE, 50));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        this.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Sembunyikan sub-menu awalnya
        for (MenuItem item : SubMenu) {
            this.subMenu.add(item);
            item.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_namamenu = new javax.swing.JLabel();
        lb_iconsub = new javax.swing.JLabel();
        lb_icon = new javax.swing.JLabel();

        setBackground(new java.awt.Color(57, 72, 82));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        lb_namamenu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lb_namamenu.setForeground(new java.awt.Color(255, 255, 255));
        lb_namamenu.setText("namaMenu");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lb_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_iconsub, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_namamenu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_namamenu, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(lb_iconsub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lb_icon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if (showing) {
            hideMenu();
        } else {
            showMenu();
            setBackground(Color.gray);
            animateColorChange(hoverColor);
        }
        if (act != null) {
            act.actionPerformed(null);
        }
    }//GEN-LAST:event_formMousePressed

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        setBackground(defaultColor);
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        setBackground(hoverColor);
    }//GEN-LAST:event_formMouseExited

    private void animateColorChange(Color targetColor) {
        // Warna awal
        Color startColor = getBackground();
        // Jumlah langkah animasi yang lebih banyak untuk kelancaran
        int steps = 40;  // Semakin besar, semakin mulus
        // Waktu total animasi (dalam milidetik)
        int duration = 300;  // Total waktu animasi lebih panjang
        // Waktu tunggu antara langkah
        int delay = duration / steps;

        // Menggunakan Timer untuk mengatur perubahan
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int step = 0;

            @Override
            public void run() {
                if (step > steps) {
                    timer.cancel();
                    setBackground(targetColor); // Set warna ke target di akhir animasi
                } else {
                    // Interpolasi linear untuk menghitung warna di langkah ini
                    float t = (float) step / (float) steps;  // Nilai t dari 0 ke 1
                    int r = (int) (startColor.getRed() + t * (targetColor.getRed() - startColor.getRed()));
                    int g = (int) (startColor.getGreen() + t * (targetColor.getGreen() - startColor.getGreen()));
                    int b = (int) (startColor.getBlue() + t * (targetColor.getBlue() - startColor.getBlue()));
                    Color interpolatedColor = new Color(r, g, b);

                    // Perbarui warna di thread utama Swing
                    javax.swing.SwingUtilities.invokeLater(() -> setBackground(interpolatedColor));

                    step++;
                }
            }
        }, 0, delay);  // Jalankan setiap 'delay' milidetik
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lb_icon;
    private javax.swing.JLabel lb_iconsub;
    private javax.swing.JLabel lb_namamenu;
    // End of variables declaration//GEN-END:variables

    private void hideMenu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < subMenu.size(); i++) {
                    sleep();
                    subMenu.get(i).setVisible(false);
                    subMenu.get(i).hideMenu();
                }
                getParent().repaint();
                getParent().revalidate();
                showing = false;
            }
        }).start();
    }

    private void showMenu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < subMenu.size(); i++) {
                    sleep();
                    subMenu.get(i).setVisible(true);
                }
                showing = true;
                getParent().repaint();
                getParent().revalidate();
            }
        }).start();
    }

    private void sleep() {
        try {
            Thread.sleep(20);
        } catch (Exception e) {
        }
    }
}
