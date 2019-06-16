package org.mensajes.ventanas.estilo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextField;

public class Decoracion {
	
	// Decoración por defecto para el JTextField
	public static JTextField setBasicEstiloTXT(JTextField campo) {
		
		campo.setBackground(Paleta.COLOR_SECUNDARIO);
		campo.setBorder(new RoundedBorder(8, Paleta.COLOR_CLARO));
		campo.setForeground(Color.white);
		campo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		campo.setPreferredSize(new Dimension(0,32));
		
		return campo;
	}
	
	// Decoración por defecto para el JButton
	public static JButton setAceptBTN(JButton btn) {
		
		btn.setBackground(Paleta.ACEPTAR);
		btn.setBorder(new RoundedBorder(8, Paleta.ACEPTAR));
		btn.setForeground(Paleta.ACEPTAR_2);
		btn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		btn.setPreferredSize(new Dimension(0,40));
		
		return btn;
	}
	
}
