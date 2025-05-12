/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package simsditp;

import Database.SQLConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Arafi
 */
public class chart extends javax.swing.JPanel {

    private JPanel chartPanel;

    /**
     * Konstruktor
     */
    public chart() {
        initComponents();
        loadBarChart();
    }

    /**
     * Inisialisasi komponen GUI dengan desain modern
     */
    private void initComponents() {
        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.setPreferredSize(new Dimension(600, 400));

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(240, 240, 240)); // Warna latar belakang yang cerah dan modern
        this.add(chartPanel, BorderLayout.CENTER);

        // Memberikan panel dengan border dan sedikit padding
        chartPanel.setBorder(BorderFactory.createLineBorder(new Color(102, 102, 102), 2)); // Border yang elegan
    }

    /**
     * Memuat data Bar Chart dari database
     */
    private void loadBarChart() {
        try {
            // Query untuk jumlah siswa berdasarkan kelas dan gender
            String query = "SELECT kelas, "
                    + "SUM(CASE WHEN kelamin = 'L' THEN 1 ELSE 0 END) AS jumlah_laki_laki, "
                    + "SUM(CASE WHEN kelamin = 'P' THEN 1 ELSE 0 END) AS jumlah_perempuan "
                    + "FROM siswa "
                    + "WHERE kelas IN ('1', '2', '3', '4', '5', '6') "
                    + "GROUP BY kelas";

            // Mendapatkan ResultSet dari SQLConnection.doQuery()
            ResultSet resultSet = SQLConnection.doQuery(query);

            // Dataset untuk Bar Chart
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            // Menambahkan data ke dataset untuk setiap kelas
            while (resultSet.next()) {
                String kelas = resultSet.getString("kelas");
                int jumlahLakiLaki = resultSet.getInt("jumlah_laki_laki");
                int jumlahPerempuan = resultSet.getInt("jumlah_perempuan");

                // Menambahkan data untuk laki-laki dan perempuan per kelas
                dataset.addValue(jumlahLakiLaki, "Laki-Laki", kelas);
                dataset.addValue(jumlahPerempuan, "Perempuan", kelas);
            }

            // Membuat Bar Chart
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Jumlah Siswa per Kelas", // Judul chart
                    "Kelas", // Label sumbu X
                    "Jumlah Siswa", // Label sumbu Y
                    dataset, // Dataset
                    PlotOrientation.VERTICAL, // Orientasi grafik (vertikal)
                    true, // Tampilkan legenda
                    true, // Tampilkan informasi tooltips
                    false // Tampilkan URL (tidak perlu)
            );

// Mengatur font judul chart agar lebih kecil
            barChart.getTitle().setFont(new Font("Arial", Font.BOLD, 16)); // Mengubah ukuran font menjadi 16

            // Menerapkan tema terang dan mulus ke chart
            barChart.setBackgroundPaint(new Color(255, 255, 255)); // Latar belakang chart yang terang
            CategoryPlot plot = barChart.getCategoryPlot();
            plot.setBackgroundPaint(new Color(240, 240, 240)); // Background plot yang terang
            plot.setDomainGridlinePaint(new Color(200, 200, 200)); // Gridline dengan warna terang
            plot.setRangeGridlinePaint(new Color(200, 200, 200)); // Gridline sumbu Y yang lembut

            // Menyesuaikan margin antar batang menggunakan BarRenderer
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setItemMargin(0.1); // Mengatur margin lebih kecil untuk batang lebih dekat
            renderer.setMaximumBarWidth(0.05); // Mempersempit lebar batang

            // Penataan batang dengan efek transparansi dan warna terang
            renderer.setSeriesPaint(0, new Color(76, 175, 80)); // Batang laki-laki dengan warna hijau terang
            renderer.setSeriesPaint(1, new Color(33, 150, 243)); // Batang perempuan dengan warna biru terang
            renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter()); // Efek batang lebih halus

            // Mengatur jarak antara label sumbu X dan batas bawah
            plot.getDomainAxis().setLowerMargin(0.05); // Mengurangi margin bawah
            plot.getDomainAxis().setUpperMargin(0.05); // Mengurangi margin atas

            // Menampilkan chart di panel
            ChartPanel chart = new ChartPanel(barChart);
            chart.setPreferredSize(new Dimension(200, 100));

            chartPanel.removeAll(); // Hapus konten lama
            chartPanel.add(chart, BorderLayout.CENTER);
            chartPanel.validate(); // Validasi ulang

            // Tutup ResultSet
            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bar chart: " + e.getMessage());
        }
    }

    /**
     * Menjalankan JPanel ini di JFrame untuk testing
     */
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Pie Chart Example");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//        frame.setLayout(new BorderLayout());
//
//        // Menambahkan panel chart ke JFrame
//        chart chartPanel = new chart();
//        frame.add(chartPanel, BorderLayout.CENTER);
//
//        // Memberikan gaya dan posisi modern untuk JFrame
//        frame.setLocationRelativeTo(null); // Center frame
//        frame.setVisible(true);
//    }
}
