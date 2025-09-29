package gui;

import logic.Controlador;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;

public class VentanaNetScan extends JFrame {

    private Controlador controlador;
    private JButton btnN, btnA, btnE;
    private JButton btnGuardarTxt, btnGuardarCsv; // NUEVO
    private JTextArea salidaArea;

    public VentanaNetScan(Controlador controlador) {
        super("NetScan - Herramientas netstat");
        this.controlador = controlador;

        setSize(720, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // independiente
        setLayout(new BorderLayout(8,8));

        // Panel de botones
        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        btnN = new JButton("netstat -n");
        btnA = new JButton("netstat -a");
        btnE = new JButton("netstat -e");

        btnN.setToolTipText("Mostrar direcciones y puertos en formato numérico");
        btnA.setToolTipText("Enumerar todas las conexiones y puertos (listen + established)");
        btnE.setToolTipText("Mostrar estadísticas de interfaz (paquetes enviados/recibidos)");

        botonPanel.add(btnN);
        botonPanel.add(btnA);
        botonPanel.add(btnE);

        // Botones nuevos
        btnGuardarTxt = new JButton("Guardar TXT");
        btnGuardarCsv = new JButton("Guardar CSV");
        botonPanel.add(btnGuardarTxt);
        botonPanel.add(btnGuardarCsv);

        // Área de salida
        salidaArea = new JTextArea();
        salidaArea.setEditable(false);
        salidaArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(salidaArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(botonPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Listeners existentes
        btnN.addActionListener(e -> {
            salidaArea.setText(""); // limpiar
            controlador.runNetstat("-n", salidaArea);
        });
        btnA.addActionListener(e -> {
            salidaArea.setText("");
            controlador.runNetstat("-a", salidaArea);
        });
        btnE.addActionListener(e -> {
            salidaArea.setText("");
            controlador.runNetstat("-e", salidaArea);
        });

        // Listeners nuevos
        btnGuardarTxt.addActionListener(e -> guardarArchivo("txt"));
        btnGuardarCsv.addActionListener(e -> guardarArchivo("csv"));
    }

    // Método auxiliar para guardar
    private void guardarArchivo(String tipo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar como " + tipo.toUpperCase());

        int seleccion = fileChooser.showSaveDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = fileChooser.getSelectedFile();

                // agregar extensión si no está
                if (!archivo.getName().toLowerCase().endsWith("." + tipo)) {
                    archivo = new File(archivo.getAbsolutePath() + "." + tipo);
                }

                try (FileWriter writer = new FileWriter(archivo)) {
                    String contenido = salidaArea.getText();

                    if (tipo.equals("csv")) {
                        // Reemplazar tabulaciones o múltiples espacios por comas
                        contenido = contenido.replaceAll("\\s+", ",");
                    }

                    writer.write(contenido);
                }

                JOptionPane.showMessageDialog(this,
                        "Archivo guardado en: " + archivo.getAbsolutePath(),
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
