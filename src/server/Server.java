package server;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    
    private Socket socket;

    public static void main(String[] args) throws ClassNotFoundException {
        new Server().startServer();
    }

    public void startServer() throws ClassNotFoundException {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server is running...");
            //new MessagesFrame().setVisible(true);
            while(true) {
                socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);
                process();
            }
        }catch (IOException e) {
            System.out.println(e);
        }
    }
    
    private void process(){
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            double result=0;
            double num1 = (double) ois.readObject();
            double num2 = (double) ois.readObject();
            char operator = (char) ois.readObject();
            System.out.println("L'operation est : "+num1+operator+num2);
            switch(operator) {
                case'+' -> result=num1+num2;
                case'-' -> result=num1-num2;
                case'*' -> result=num1*num2;
                case'/' -> result=num1/num2;
            }
            num1=result;
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
            System.out.println("Resultat envoye : "+result);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
