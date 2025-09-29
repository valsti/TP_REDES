package escaner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IngresarPanel {



	public class ServiciosPanel extends JPanel {

	    private JComboBox<String> comboServicios;
	    private JButton btnPagar;
	    private JLabel lblTitulo;

	    public ServiciosPanel() {
	        setLayout(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();

	        // ---------- 1. JLabel centrado arriba ----------
	        lblTitulo = new JLabel("Seleccione el servicio");
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.insets = new Insets(10, 10, 20, 10); //margenes
	        // gbc.anchor = GridBagConstraints.CENTER;
	        add(lblTitulo, gbc);

	        // ---------- 2. JComboBox con opciones ----------
	        comboServicios = new JComboBox<>(new String[] { "Ip inicio ", "Fin del rango" });
	        gbc.gridy++;
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        gbc.insets = new Insets(10, 10, 10, 10);
	        add(comboServicios, gbc);

	        // ---------- 3. JButton deshabilitado al principio ----------
	        btnPagar = new JButton("Deshabilitado");
	        btnPagar.setEnabled(false); // para que no lo puedas tocar
	        gbc.gridy++;
	        add(btnPagar, gbc);

	        // ---------- Listener para el JComboBox ----------
	        
	    

}
