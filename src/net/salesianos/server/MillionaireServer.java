package net.salesianos.server;

import net.salesianos.model.Question;
import java.io.*;
import java.net.*;
import java.util.*;

public class MillionaireServer {
    private static final int PORT = 12345;
    private static List<Question> questions = new ArrayList<>();
    private static Map<String, Integer> scores = new HashMap<>();

    public static void main(String[] args) {
        loadQuestions();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor esperando jugadores...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo jugador conectado!");
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadQuestions() {
        questions.add(new Question("¿Cuál es la capital de Francia?", "Madrid", "Berlín", "París", "Lisboa", 'C'));
        questions.add(new Question("¿Cuántos planetas tiene el sistema solar?", "7", "8", "9", "10", 'B'));
        questions.add(new Question("¿Quién escribió 'Don Quijote'?", "Cervantes", "García Márquez", "Borges", "Shakespeare", 'A'));
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

            //    out.println("¡Bienvenido a ¿Quién quiere ser millonario?! Escribe tu nombre:");
                String playerName = in.readLine();
                int score = 0;

                for (Question q : questions) {
                    out.println(q.toString());
                    String answer = in.readLine().trim().toUpperCase();
                    if (answer.charAt(0) == q.getCorrectAnswer()) {
                        score += 10;
                        out.println("✅ ¡Correcto! +10 puntos");
                    } else {
                        out.println("❌ Incorrecto. La respuesta correcta era " + q.getCorrectAnswer());
                    }
                }

                scores.put(playerName, score);
                out.println("Juego terminado. Tu puntuación: " + score);
                out.println("El Ranking actual por puntos obtenidos es:");
                scores.forEach((name, points) -> out.println(name + ": " + points + " puntos"));

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
