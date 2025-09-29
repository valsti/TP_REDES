package giu;

import logic.NetworkScanner;
import logic.ScanTimeOutException;
import logic.DeviceInfo;
import logic.IPRangeOutofBoundsException;
import util.IPUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {

    private JTextField startIPField, endIPField;
    private JTable resultTable;
    private JProgressBar progressBar;
    private NetworkScanner scanner;

    public MainWindow() {
        super("🌐 Escáner de Red");
        scanner = new NetworkScanner();

        // Colores base
        Color bgColor = new Color(240, 248, 255); // Azul muy claro
        Color panelColor = new Color(200, 220, 240); // Celeste suave
        Color buttonScan = new Color(100, 200, 150); // Verde suave
        Color buttonClear = new Color(220, 100, 100); // Rojo suave

        setLayout(new BorderLayout());
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(bgColor);

        // Panel superior
        JPanel topPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        topPanel.setBackground(panelColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        startIPField = new JTextField();
        endIPField = new JTextField();

        JButton scanBtn = new JButton("🔍 Escanear");
        scanBtn.setBackground(buttonScan);
        scanBtn.setForeground(Color.WHITE);
        scanBtn.setFocusPainted(false);

        JButton clearBtn = new JButton("🗑 Limpiar");
        clearBtn.setBackground(buttonClear);
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFocusPainted(false);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(100, 149, 237)); // Azul acero

        topPanel.add(new JLabel("IP Inicio:"));
        topPanel.add(startIPField);
        topPanel.add(scanBtn);
        topPanel.add(new JLabel("IP Fin:"));
        topPanel.add(endIPField);
        topPanel.add(clearBtn);

        add(topPanel, BorderLayout.NORTH);
        add(progressBar, BorderLayout.SOUTH);

        // Tabla personalizada
        resultTable = new JTable(new DefaultTableModel(
                new Object[]{"IP", "Nombre", "Conectado", "Tiempo (ms)"}, 0));

        resultTable.setRowHeight(25);
        resultTable.setSelectionBackground(new Color(173, 216, 230)); // Azul claro al seleccionar

        // Colores alternos en filas
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                return c;
            }
        };
        for (int i = 0; i < resultTable.getColumnCount(); i++) {
            resultTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Encabezado de tabla personalizado
        JTableHeader header = resultTable.getTableHeader();
        header.setBackground(new Color(100, 149, 237));
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD));

        add(new JScrollPane(resultTable), BorderLayout.CENTER);

        // Acciones botones
        scanBtn.addActionListener(e -> startScan());
        clearBtn.addActionListener(e -> clearTable());
    }

    private void startScan() {
        String startIP = startIPField.getText();
        String endIP = endIPField.getText();

        if (!IPUtils.isValidIP(startIP) || !IPUtils.isValidIP(endIP)) {
            JOptionPane.showMessageDialog(this, "IP inválida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ArrayList<String> ips = new ArrayList<>();
        
        try {
        	 ips.addAll(IPUtils.generateIPRange(startIP, endIP));
        	 
 
      
        }
        catch(IPRangeOutofBoundsException e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        progressBar.setMaximum(ips.size());
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        model.setRowCount(0);

        new Thread(() -> {
        	List<DeviceInfo> devices = new ArrayList<>();
        	
        	try {
             devices.addAll(NetworkScanner.scanRange(ips, 1000));
        	}
        	  catch (ScanTimeOutException e)
            {
            	JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);}
            	
            SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < devices.size(); i++) {
                    DeviceInfo d = devices.get(i);
 
                    model.addRow(new Object[]{
                        d.getIp(), d.getHostname(),
                        d.getConnected(),
                        d.getResponseTime()
                    });
                
                    
                    
                  
                    progressBar.setValue(i + 1);
                    progressBar.setString((i + 1) + " / " + ips.size());
                }
            });
        }).start();
    }

    private void clearTable() {
        ((DefaultTableModel) resultTable.getModel()).setRowCount(0);
        progressBar.setValue(0);
        progressBar.setString("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
