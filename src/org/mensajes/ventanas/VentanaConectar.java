package org.mensajes.ventanas;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.mensajes.crypto.Cifrado;
import org.mensajes.network.Cliente;
import org.mensajes.network.Servidor;
import org.mensajes.ventanas.estilo.Decoracion;
import org.mensajes.ventanas.estilo.Paleta;

public class VentanaConectar extends JFrame {
	
	// Constantes de la clase
	private final int ANCHO = 470;
	private final int ALTO = 500;
	
	private final String TITULO = "Configuracion del chat";
	
	private final int DEAULT_CLOP = JFrame.EXIT_ON_CLOSE;
		
	private final boolean REDIMENSIONABLE = false;
	
	public VentanaConectar () {
		// Configuramos el JFrame
		setSize(ANCHO, ALTO);
		setTitle(TITULO);
		setDefaultCloseOperation(DEAULT_CLOP);
		setResizable(REDIMENSIONABLE);
		
		// Añadimos el marco
		MarcoConectar marco = new MarcoConectar();
		
		add(marco);
	}
	
	// Muestra la ventana
	public void mostrarVentana() {
		setVisible(true);
	}
	
	// Muestra la ventana si mostrar es igual a true, si no, la oculta
	public void mostrarVentana(boolean mostrar) {
		setVisible(mostrar);
	}
	
	// Cierrra la ventana
	public void cerrarVentana() {
		dispose();
	}
	
}

class MarcoConectar extends JPanel {

	// Variables necesarias para comprobar que están las dos cosas iniciadas y pasar al chat
	private boolean clientActivated, serverActivated;
	
	// Objetos del marco que obligatoriamente tienen que ser públicos para acceder a sus propiedades y controlarlos
	private JPanel pPrincipal, pInitserver, pInitClient, pCrypto;
	private JButton btn_crypto, btn_InitServer, btn_InitClient;
	private JTextField Key_txt, SPort_txt, CPort_txt, CIP_txt;
	
	// Constructor de la clase
	public MarcoConectar() {
		
		// Se crean los paneles
		pPrincipal = new JPanel();
		pCrypto = new JPanel();
		pInitserver = new JPanel();
		pInitClient = new JPanel();
		
		// Se añaden margenes con un CardLayout
		setLayout(new CardLayout(10,10));
		setBackground(Paleta.COLOR_PRIMARIO);
		
		// Panel principal, donde iran los otros dos paneles, uno con el servidor y otro con el cliente
		pPrincipal = new JPanel();
		pPrincipal.setLayout(new BoxLayout(pPrincipal, BoxLayout.PAGE_AXIS));
		pPrincipal.setBackground(Paleta.COLOR_PRIMARIO);
		
		// Establece las propiedades visuales, tanto del panel del cliente como el del servidor
		setCryptoPropierties();
		setInitserverVPropierties();
		setInitClientVPropierties();
		
		/* 
		*	Listener de los botones (Para establecer la clave criptográfica, iniciar el servidor y el cliente),
		*	simplemente llama a otros métodos que realizan la acción (Para mayor comprensión del código)
		*/
		btn_crypto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCryptoKey();
			}
		});
		
		btn_InitServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initServer();
			}
		});
		
		btn_InitClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initClient();
			}
		});
		
		// Se añaden los paneles secundarios al principal
		pPrincipal.add(pCrypto);
		pPrincipal.add(pInitserver);
		pPrincipal.add(pInitClient);
		
		// El principal se añade al superior con los bordes añadidos
		add(pPrincipal);
	}
	
	// Establece la clave cryptografica en caso de que no se haya cambiado
	private void setCryptoKey() {
		new Cifrado(Key_txt.getText());
	}
	
	// Inicia el servidor, y si el cliente ya se ha iniciado, se abre la pantalla de diálogo
	private void initServer() {
		String spuerto = SPort_txt.getText();
		int puerto;
		try {
			puerto = Integer.parseInt(spuerto);
			new Servidor(puerto);
			JOptionPane.showMessageDialog(null, "Servidor iniciado con éxito, el otro usuario ya se puede conectar", "Servidor iniciado", JOptionPane.INFORMATION_MESSAGE);
			SPort_txt.setEditable(false);
			btn_InitServer.setEnabled(false);
			serverActivated = true;
			if (clientActivated) {
				VentanaPrincipal ventana = new VentanaPrincipal();
				ventana.mostrarVentana();
				// CERRAR ESTA VENTANA FALTA
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Tan solo se pueden introducir numeros en el puerto", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	// Inicia el cliente, y si el servidor ya se ha iniciado, se abre la pantalla de diálogo
	private void initClient() {
		String IP = CIP_txt.getText();
		String spuerto = CPort_txt.getText();
		
		int puerto;
		
		try {
			puerto = Integer.parseInt(spuerto);
			new Cliente(IP, puerto);
			JOptionPane.showMessageDialog(null, "Cliente conectado con éxito", "Cliente iniciado", JOptionPane.INFORMATION_MESSAGE);
			CIP_txt.setEditable(false);
			CPort_txt.setEditable(false);
			btn_InitClient.setEnabled(false);
			if (serverActivated) {
				VentanaPrincipal ventana = new VentanaPrincipal();
				ventana.mostrarVentana();
				// CERRAR ESTA VENTANA FALTA
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Tan solo se pueden introducir numeros en el puerto", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	// Establece las propiedades visuales del panel
	private void setCryptoPropierties() {
		pCrypto.setForeground(Color.WHITE);
		pCrypto.setBackground(Paleta.COLOR_PRIMARIO);
		pCrypto.setLayout(new GridBagLayout());
		pCrypto.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Paleta.COLOR_SECUNDARIO), 
				"Encriptado",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				null,
				Color.WHITE
			)
		);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		c.gridwidth = 1;
		
		// FILA 1
		JLabel lab_key = new JLabel("Clave de encriptado");
		lab_key.setForeground(Color.WHITE);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		pCrypto.add(lab_key,c);
		
		// FILA 2
		Key_txt = new JTextField();
		Decoracion.setBasicEstiloTXT(Key_txt);
		c.gridy = 1;
		c.insets = new Insets(0,6,0,6);
		pCrypto.add(Key_txt, c);
		
		// FILA 3
		btn_crypto = new JButton("Establecer clave");
		Decoracion.setAceptBTN(btn_crypto);
		c.gridy = 2;
		pCrypto.add(btn_crypto, c);
	}
	
	// Establece las propiedades visuales del panel
	private void setInitserverVPropierties() {
		pInitserver.setForeground(Color.WHITE);
		pInitserver.setBackground(Paleta.COLOR_PRIMARIO);
		pInitserver.setLayout(new GridBagLayout());
		pInitserver.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Paleta.COLOR_SECUNDARIO), 
				"Configurar servidor",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				null,
				Color.WHITE
			)
		);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		
		// FILA 1
		JLabel lab_Port = new JLabel("puerto");
		lab_Port.setForeground(Color.WHITE);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 0;
		pInitserver.add(lab_Port, c);
		
		// FILA 2
		SPort_txt = new JTextField();
		Decoracion.setBasicEstiloTXT(SPort_txt);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.insets = new Insets(0,6,0,6);
		pInitserver.add(SPort_txt, c);
		
		// FILA 3
		btn_InitServer = new JButton("Iniciar servidor");
		Decoracion.setAceptBTN(btn_InitServer);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.insets = new Insets(6, 6, 6, 6);
		pInitserver.add(btn_InitServer, c);
	}
	
	// Establece las propiedades visuales del panel
	private void setInitClientVPropierties() {
		pInitClient.setForeground(Color.WHITE);
		pInitClient.setBackground(Paleta.COLOR_PRIMARIO);
		pInitClient.setLayout(new GridBagLayout());
		pInitClient.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Paleta.COLOR_SECUNDARIO), 
				"Configurar cliente",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION,
				null,
				Color.WHITE
			)
		);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(3, 3, 3, 3);
		
		// FILA 1
		JLabel lab_IP = new JLabel("IP");
		lab_IP.setForeground(Color.WHITE);
		c.weightx = 2;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		pInitClient.add(lab_IP, c);
		
		JLabel lab_Port = new JLabel("puerto");
		lab_Port.setForeground(Color.WHITE);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 0;
		pInitClient.add(lab_Port, c);
		
		// FILA 2
		CIP_txt = new JTextField();
		Decoracion.setBasicEstiloTXT(CIP_txt);
		c.weightx = 2;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0,6,0,6);
		pInitClient.add(CIP_txt, c);
		
		CPort_txt = new JTextField();
		Decoracion.setBasicEstiloTXT(CPort_txt);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,6);
		pInitClient.add(CPort_txt, c);
		
		// FILA 3
		btn_InitClient = new JButton("Iniciar Cliente");
		Decoracion.setAceptBTN(btn_InitClient);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.insets = new Insets(6, 6, 6, 6);
		pInitClient.add(btn_InitClient, c);
		
	}
	
}
