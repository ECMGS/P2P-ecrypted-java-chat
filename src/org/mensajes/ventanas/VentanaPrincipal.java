package org.mensajes.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.mensajes.crypto.Cifrado;
import org.mensajes.network.Cliente;
import org.mensajes.network.GetCallback;
import org.mensajes.network.SentCallback;
import org.mensajes.network.Servidor;
import org.mensajes.parser.ParseMSG;
import org.mensajes.ventanas.estilo.Paleta;

// Clase donde va la ventana
public class VentanaPrincipal extends JFrame {
	
	// Constantes de la clase
	private final int ANCHO = 500;
	private final int ALTO = 600;
	
	private final String TITULO = "Mensajes";
	
	private final int DEAULT_CLOP = JFrame.EXIT_ON_CLOSE;
	
	private final boolean REDIMENSIONABLE = true;
	
	// Componentes del JFrame
	private JPanel panel;
	
	// Constructor de la ventana PARAMETROS DE LA CLASE
	public VentanaPrincipal() {
		// Configuramos el JFrame
		setSize(ANCHO, ALTO);
		setTitle(TITULO);
		setDefaultCloseOperation(DEAULT_CLOP);
		setResizable(REDIMENSIONABLE);
		
		// Iniciamos los componentes
		panel = new marcoVentanaPrincipal();
		//Añadimos los componentes
		add(panel);
	}
	
	// Muestra la ventana
	public void mostrarVentana() {
		setVisible(true);
	}
	
	// Muestra la ventana si mostrar es igual a true, si no, la oculta
	public void mostrarVentana(boolean mostrar) {
		setVisible(mostrar);
	}
	
}

class marcoVentanaPrincipal extends JPanel {
	
	// Variables de la clase
	private JEditorPane conversacion;
	private JTextField entrada;
	
	//Stack de mensakes
	private String mensajes;
	
	// Constructor de la clase iniciamos, configuramos y mostramos
	public marcoVentanaPrincipal() {
		super(new BorderLayout());
		
		conversacion = new JEditorPane();
		entrada = new JTextField();
		
		mensajes = "";
		
		// Configuramos el panel donde se va a llevar a cabo la conversación
		conversacion.setContentType("text/html");
		conversacion.setEditable(false);
		conversacion.setBackground(Paleta.COLOR_PRIMARIO);
		
		conversacion.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		conversacion.setForeground(Color.WHITE);
		conversacion.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		conversacion.setText("");
		
		// Configuramos la entrada de texto
		entrada.setBackground(Paleta.COLOR_SECUNDARIO);
		entrada.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Paleta.COLOR_CLARO));
		entrada.setForeground(Color.white);
		entrada.setPreferredSize(new Dimension(0,30));
		
		entrada.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enviarMensaje(entrada.getText());
			}
		});
		
		// Añadimos los elementos al panel
		add(conversacion, BorderLayout.CENTER);
		add(entrada, BorderLayout.SOUTH);
		
		// Aceptamos mensajes en el servidor
		Servidor.aceptarMensajes(new GetCallback() {
			
			@Override
			public String send() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void get(String msg) {
				recivirMensaje(msg);
			}
		});
		
	}
	
	// Método para enviar mensaje al otro usuario
	private void enviarMensaje(String msg) {
		
		// Si hay un espacio al inicio lo elimina
		if (msg.charAt(0) == ' ') {
			msg = msg.substring(1, msg.length());
		}
		
		// Borra el texto del input
		entrada.setText("");
		
		System.out.println(msg);
		mensajes += ParseMSG.parseMensajeEnv(msg);
		conversacion.setText(mensajes);
		
		// Envia el mensaje y establece un callback para cuando el otro usuario haya recibido el mensaje con exito
		Cliente.enviarMensaje(Cifrado.cifrarMSG(msg), null);
	}
	
	private void recivirMensaje(String msg) {
		mensajes += ParseMSG.parseMensajeRecv(Cifrado.descifrarMSG(msg));
		
		conversacion.setText(mensajes);
	}
	
	
}
