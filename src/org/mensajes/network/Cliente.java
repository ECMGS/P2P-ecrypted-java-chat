package org.mensajes.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Cliente {
	
	// Variables de la clase
	private static Socket sockCliente;
	
	private static PrintWriter out;
	private static BufferedReader in;
	
	// Inicia el cliente, el cual tiene la capacidad de enviar mensajes
	public Cliente(String IP, int puerto) {
		try {
			sockCliente = new Socket(IP, puerto);
			out = new PrintWriter(sockCliente.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sockCliente.getInputStream()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error al conectaese al otro usuario\n¿Seguro que esta conectado?", "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	// Método para enviar los mensajes
	public static void enviarMensaje(String msg, SentCallback call) {
		System.out.println("enviado a "+sockCliente.getLocalAddress()+"\nmsg: "+msg);
		out.println(msg);
		Thread get = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String ret;
				try {
					ret = in.readLine();
					System.out.println(ret);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error en la verificacion de la recepción del mensaje por parte del otro usuario", "ERROR", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		
		get.start();
	}
}
