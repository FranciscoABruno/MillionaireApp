package net.salesianos.client;

import java.io.*;
import java.net.*;

public class MillionaireClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor. Introduce tu nombre:");
            String name = userInput.readLine();
            out.println(name);

            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage); // muestra cada línea recibida
            
                // Si detecta que se están enviando las opciones (A, B, C, D) espera las siguientes líneas antes de pedir la respuesta
                if (serverMessage.contains("A)")) {  
                    for (int i = 0; i < 3; i++) { // lee las siguientes 3 líneas de opciones (B, C, D) y guarda en variables
                        System.out.println(in.readLine());
                    }
            
                    System.out.print("Selecciona la opcion que creas correcta: ");
                    String answer = userInput.readLine();
                    out.println(answer);
                    out.flush();
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
