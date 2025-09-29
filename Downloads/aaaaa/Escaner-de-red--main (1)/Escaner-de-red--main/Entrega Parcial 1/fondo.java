package escaner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class fondo extends JFrame implements ActionListener {

	// Se crea una clase fondo que extiende el JFrame(lo que permite hacer interfaces graficas) e implementa el ActionListener(Es alguien que va a escuchar todo el tiempo que se realicen las acciones)

		//Atributos de GUI (interfaz grafica de usuario)

		private JButton btnIngresar; // Botón
		private JPanel panelDer; // Panel
		private JPanel panelIzq; // Panel 
		
		public fondo() {
		
			//Diseño
			setTitle("Escaner de red"); // El nombre de la ventana 
			setSize(620,320); // Cuanto ocupa (en px)
			setLayout(new BorderLayout()); // Un tipo de como pone la ventana 
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Que se cierre cuando toques la X

			//2 JPanel
			panelIzq = new JPanel(new GridBagLayout()); // Hace el panel de la izquierda con un tipo de layout
			add(panelIzq, BorderLayout.WEST); // añade el panel a la ventana en el west(oeste) 
			panelDer = new JPanel(); // Hace el panel de la derecha sin layout
			panelDer.setBackground(Color.GRAY); // Le pone el color a la ventana principal (derecha)

			GridBagConstraints gbc=new GridBagConstraints(); // GridBagConstraints: es un objeto que permite restringir otros objetos enuna grilla(en esta linea crea un objeto de tipo gbc)
			gbc.fill = GridBagConstraints.HORIZONTAL; // 
			gbc.insets = new Insets(10, 10, 10, 10);
			
			gbc.gridx=0; // Le asigna 0 a las columnas
			gbc.gridy=0; //Le asigna 0 a las filas
	
			gbc.gridy++;
			btnIngresar= new JButton("Ingresar");
			panelIzq.add(btnIngresar,gbc);
			
			// Pone una fila mas abajo (asi los botones quedan uno abajo del otro) Hace el boton Nosotros , lo añade al panel de la izquierda con las restricciones de la grilla anteriormente creadas  por le objeto.
			
			add(panelDer, BorderLayout.CENTER); // Añade el panel a la ventana en el cento
			
			// Cada vez que toques un bóton le va a poner un Action Listener y le va a mandar un objeto en el que estan cada boton y va a reaccionar (basicamente lo tocas y hace algo :v)

			btnIngresar.addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) { // Creo un void ActionPerformert(Tiene todas lñas reacciones del codigo) que recibe el Action Event (Casda vez que pasa un evento (click) tiene la infromacion de lo que paso)
			
			// getSource(metodo para saber con que boton interactuaron)
			
			if (e.getSource() == btnIngresar) { // """" btnServicios """ Servicios
				mostrarPanel(new IngresarPanel());}
			
		}

		// Esto es un método para mostrar el panel, que le llega como objeto un Panel de tipo JPanel cn las siguientes funciones.
		private void mostrarPanel(JPanel panel) {
			panelDer.removeAll(); // Remuebe todo del panel de la derecha para que no se sobreescriban
			panelDer.add(panel, new GridBagConstraints()); 
			panelDer.revalidate();//Recalcula el diseño por el cambio de JPanel.
			panelDer.repaint();//Colocar para que el nuevo panel se acomode bien y aparezca.
		}	

		public static void main(String[] args) {

			fondo fondo= new fondo();
			fondo.setVisible(true);

		}
	}


