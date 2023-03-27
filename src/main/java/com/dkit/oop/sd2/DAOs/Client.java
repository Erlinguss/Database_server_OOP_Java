/** CLIENT                                                  February 2021
 *
 * This Client program asks the user to input commands to be sent to the server.
 *
 * There are only two valid commands in the protocol: "Time" and "Echo"
 *
 * If user types "Time" the server should reply with the current server time.
 *
 * If the user types "Echo" followed by a message, the server will echo back the message.
 * e.g. "Echo Nice to meet you"
 *
 * If the user enters any other input, the server will not understand, and
 * will send back a message to the effect.
 *
 * NOte: You must run the server before running this the client.
 * (Both the server and the client will be running together on this computer)
 */
package com.dkit.oop.sd2.DAOs;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        Client client = new Client();
        client.start();
    }
    public void start()
    {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 9999);  // connect to server socket
            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort() );

            System.out.println("Client message: The Client is running and has connected to the server");

            System.out.println("Please enter a command:  (\"Time\" to get time, or \"Echo message\" to get echo) \n>");
            String command = in.nextLine();

            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);   // true => auto flush buffers



            Scanner socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply



            //===============================================
            if(command.startsWith("displayAllRestaurants"))   //we expect the server to return all restaurants
            {
                socketWriter.println(command);
                String JsonString= socketReader.nextLine();
                System.out.println("Client message: Response from server Time: " + JsonString);
            }
            else  if(command.startsWith("getById"))   //we expect the server to return restaurant by id
            {
                System.out.println("Please enter a Restaurant id:\n>");
                String strId = in.nextLine();

                String request = command + " " + strId;
                socketWriter.println(request);          //SEND REQUEST TO SERVER

                String JsonString = socketReader.nextLine(); // WAIT FOR RESPONSE FROM SERVER

                if(JsonString.equals("{}"))
                    System.out.println( "There was no Restaurant for the id you specified");
                else {
                    System.out.println(JsonString);
                }

            }
            else                            // the user has entered the Echo command or an invalid command
            {
                System.out.println("Invalid command");
            }
            socket.close();

        } catch (IOException e) {
            System.out.println("Client message: IOException: "+e);
        }
    }


}