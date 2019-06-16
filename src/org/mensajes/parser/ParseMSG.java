package org.mensajes.parser;

public class ParseMSG {

	// Da el estilo a los mensajes recividos
	public static String parseMensajeRecv(String msg) {
		
		String ret;
		
		ret = "<p text-align:left;color:green;>"+msg+"</p>";
		
		return ret;
	}
	
	// Da el estilo a los mensajes enviads
	public static String parseMensajeEnv(String msg) {
		String ret;
		
		ret = "<p text-align:right;color:red;>"+msg+"</p>";
		
		return ret;
	}

}
