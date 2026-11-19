import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Latihan GUI Java Swing - Aplikasi Biodata Mahasiswa
 *
 * 1. Kontainer Tingkat Atas : JFrame
 * 2. Kontainer Menengah     : JPanel, JScrollPane
 * 3. Komponen Atom          : JLabel, JTextField, JComboBox, JButton, JTextArea
 * 4. Kelas Tata Letak       : BorderLayout, GridLayout, FlowLayout
 * 5. Pendengar Peristiwa    : ActionListener (antarmuka),
 *                             MouseAdapter & WindowAdapter (kelas adaptor)
 */
public class AplikasiBiodata extends JFrame {

    private JTextField tfNim;
    private JTextField tfNama;
    private JComboBox<String> cbProdi;
    private JTextArea taHasil;
    private JButton btnSimpan;
    private JButton btnReset;

    public AplikasiBiodata() {
        // ===== Kontainer Tingkat Atas (JFrame) =====
        setTitle("Aplikasi Biodata Mahasiswa");
        setSize(450, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Tata letak 1: BorderLayout

        // ===== Kontainer Menengah: panel judul (FlowLayout) =====
        JPanel panelJudul = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Tata letak 2
        JLabel lblJudul = new JLabel("FORM BIODATA MAHASISWA");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        panelJudul.add(lblJudul);

        // ===== Kontainer Menengah: panel form (GridLayout) =====
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 8, 8)); // Tata letak 3
        panelForm.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        tfNim = new JTextField();
        tfNama = new JTextField();
        cbProdi = new JComboBox<>(new String[]{
                "Teknik Informatika", "Sistem Informasi", "Ilmu Komputer", "Teknik Komputer"});

        panelForm.add(new JLabel("NIM"));
        panelForm.add(tfNim);
        panelForm.add(new JLabel("Nama"));
        panelForm.add(tfNama);
        panelForm.add(new JLabel("Program Studi"));
        panelForm.add(cbProdi);

        // ===== Area hasil dengan JScrollPane =====
        taHasil = new JTextArea(6, 20);
        taHasil.setEditable(false);
        JScrollPane scroll = new JScrollPane(taHasil);
        scroll.setBorder(BorderFactory.createTitledBorder("Hasil"));

        // Panel tengah: form di atas, hasil di bawah (BorderLayout)
        JPanel panelTengah = new JPanel(new BorderLayout(5, 5));
        panelTengah.add(panelForm, BorderLayout.NORTH);
        panelTengah.add(scroll, BorderLayout.CENTER);

        // ===== Panel tombol (FlowLayout) =====
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSimpan = new JButton("Simpan");
        btnReset = new JButton("Reset");
        panelTombol.add(btnSimpan);
        panelTombol.add(btnReset);

        // Susun ke JFrame
        add(panelJudul, BorderLayout.NORTH);
        add(panelTengah, BorderLayout.CENTER);
        add(panelTombol, BorderLayout.SOUTH);

        // ===== Pendengar Peristiwa =====

        // (a) Antarmuka ActionListener
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanData();
            }
        });
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        // (b) Kelas Adaptor MouseAdapter (hanya override metode yang dibutuhkan)
        MouseAdapter hover = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getSource()).setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton) e.getSource()).setBackground(null);
            }
        };
        btnSimpan.addMouseListener(hover);
        btnReset.addMouseListener(hover);

        // (c) Kelas Adaptor WindowAdapter (konfirmasi saat menutup jendela)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int pilih = JOptionPane.showConfirmDialog(AplikasiBiodata.this,
                        "Yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (pilih == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });
    }

    private void simpanData() {
        String nim = tfNim.getText().trim();
        String nama = tfNama.getText().trim();
        if (nim.isEmpty() || nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM dan Nama wajib diisi!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        taHasil.append("NIM   : " + nim + "\n"
                + "Nama  : " + nama + "\n"
                + "Prodi : " + cbProdi.getSelectedItem() + "\n"
                + "-----------------------------\n");
    }

    private void resetForm() {
        tfNim.setText("");
        tfNama.setText("");
        cbProdi.setSelectedIndex(0);
        taHasil.setText("");
        tfNim.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AplikasiBiodata().setVisible(true));
    }
}
