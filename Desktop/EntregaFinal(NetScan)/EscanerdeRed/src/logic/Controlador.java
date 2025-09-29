package logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;

public class Controlador {

    private volatile boolean paradaSolicitada = false;

    public Controlador() {
        // constructor vacío por ahora
    }

    public void escanearRango(List<String> ips, int tiempoLimite, DefaultTableModel model, JProgressBar barraProgreso) throws ScanTimeOutException {
        paradaSolicitada = false;
        barraProgreso.setValue(0);
        barraProgreso.setMaximum(ips.size());
        long inicio = System.currentTimeMillis();

        int index = 0;

        for (String ip : ips) {
            if (paradaSolicitada) {
                break;
            }

            long inicioPing = System.currentTimeMillis();
            String respuesta = huboRespuesta(ip);
            long finPing = System.currentTimeMillis();
            String hostname = obtenerNombre(ip);

            int progress = ++index;

            SwingUtilities.invokeLater(() -> {
                model.addRow(new Object[]{ip, hostname, respuesta, finPing - inicioPing});
                barraProgreso.setValue(progress);
                barraProgreso.setString((progress * 100 / ips.size()) + "%");
            });

            long fin = System.currentTimeMillis();
            if (fin - inicio >= tiempoLimite) {
                throw new ScanTimeOutException("Se dejará de escanear el rango de IPs. El tiempo de espera está agotado.");
            }
        }
    }

    private static String obtenerNombre(String ip) {
        try {
            ProcessBuilder pb = new ProcessBuilder("nslookup", ip);
            pb.redirectErrorStream(true);

            Process proceso = pb.start();
            BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));

            String resultado = "El nombre no fue encontrado";
            String line;

            while ((line = lector.readLine()) != null) {
                if (line.contains("Nombre:") || line.toLowerCase().contains("name")) {
                    // intenta partir por "Nombre:" o "Name:"
                    if (line.contains("Nombre:")) {
                        return line.split("Nombre:")[1].trim();
                    } else if (line.toLowerCase().contains("name")) {
                        // puede venir en inglés: "Name:"
                        String[] parts = line.split(":");
                        if (parts.length > 1) return parts[1].trim();
                    }
                }
            }
            return resultado;

        } catch (Exception e) {
            return "Error";
        }
    }

    private static String huboRespuesta(String ip) {
        try {
            // ping -n 1 ip  (Windows)
            ProcessBuilder pb = new ProcessBuilder("ping", "-n", "1", ip);
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String line;

            while ((line = lector.readLine()) != null) {
                // checks básicos para Windows en castellano/inglés
                if (line.contains("bytes=") || line.toLowerCase().contains("bytes")) {
                    return "Conectado";
                } else if (line.toLowerCase().contains("host") && line.toLowerCase().contains("unreachable")) {
                    return "Inaccesible";
                } else if (line.contains("(100% perdidos)") || line.toLowerCase().contains("100% loss")) {
                    return "No Conectado";
                }
            }
            return "No Conectado";
        } catch (Exception e) {
            return "Error, sin respuesta";
        }
    }

    // stopScan: marca la bandera para que el proceso de escaneo deje de iterar
    public void stopScan() {
        paradaSolicitada = true;
    }

    // Guardado existente (lo dejamos igual que tenías)
    public void guardarResultados(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar resultados");
        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            try (java.io.FileWriter writer = new java.io.FileWriter(fileChooser.getSelectedFile() + ".csv")) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < model.getColumnCount(); i++) {
                    writer.write(model.getColumnName(i) + (i < model.getColumnCount() - 1 ? "," : ""));
                }
                writer.write("\n");
                for (int r = 0; r < model.getRowCount(); r++) {
                    for (int c = 0; c < model.getColumnCount(); c++) {
                        writer.write(model.getValueAt(r, c).toString() + (c < model.getColumnCount() - 1 ? "," : ""));
                    }
                    writer.write("\n");
                }
                JOptionPane.showMessageDialog(null, "Archivo guardado con éxito.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al guardar archivo: " + e.getMessage());
            }
        }
    }

    /**
     * runNetstat: ejecuta "netstat" con la bandera indicada y escribe la salida en el JTextArea
     * - flag puede ser "-n", "-a" o "-e"
     * - ejecuta en background y actualiza la UI con SwingUtilities.invokeLater
     */
    public void runNetstat(String flag, JTextArea area) {
        new Thread(() -> {
            try {
                // Comando base: "netstat" y la bandera
                ProcessBuilder pb = new ProcessBuilder("netstat", flag);
                pb.redirectErrorStream(true);
                Process process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                // Escribe línea a línea en el JTextArea (append en EDT)
                while ((line = reader.readLine()) != null) {
                    final String l = line;
                    SwingUtilities.invokeLater(() -> {
                        area.append(l + "\n");
                    });
                }
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    area.append("Error al ejecutar netstat: " + ex.getMessage() + "\n");
                });
            }
        }).start();
    }
}
