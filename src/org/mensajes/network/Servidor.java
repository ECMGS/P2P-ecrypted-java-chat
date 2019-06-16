 package org.mensajes.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Servidor {

	private static ServerSocket sockServer;
	
	// Inicia el servidor, el cual es capaz de escuchar mensajes
	public Servidor(int puerto) {
		try {
			sockServer = new ServerSocket(puerto);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error en la creación del servidor para que el otro usuario se conecte", "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	//  Pone el listener del servidor con un Callback
	public static void aceptarMensajes(GetCallback call) {
		
		// Este proceso se lleva en un hilo aparte para evitar parar el programa
		Thread escucha = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Socket conexion = sockServer.accept();
					
					PrintWriter out = new PrintWriter(conexion.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
					
					while (true) {
						String msg = in.readLine();
						
						call.get(msg);
					}
					
					
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "El usuario ha salido del chat", "ERROR", JOptionPane.INFORMATION_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		
		// El programa inicia el hilo
		escucha.start();
	}
	
}
