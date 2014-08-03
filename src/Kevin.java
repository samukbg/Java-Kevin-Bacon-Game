/**
 * Author : Samuel Bezerra Gomes
 * Student Number : 2869370
 **/

import java.io.*;
import java.net.URI;
import java.util.*;

public class Kevin {

    //Instantiating objects

    private static Graph graph;
    private static Scanner s = null;

    // Main class
    
    public static void main(String[] args) throws IOException {

        String choice;
        int wins=0;
        do {
            for(int i=0; i<25; i++)
            {
                System.out.println("\b");
            }

            System.out.println("\n============= Welcome to Kevin Bacon 'Game' =============\n");

            System.out.println("You have won "+wins+" times\n");

            // Gets the actor's file first:
            System.out.println("Please choose here the file you want to use: ");
            System.out.println("1. Small version of IMDB Actor's list");
            System.out.println("2. Full version  of IMDB Actor's list (can take DAYS to process the file)");
            System.out.println("3. Full version  of IMDB Actor's list with threads (can take HOURS to process the file)");
            System.out.println("0. Exit ");
            System.out.print("\n> ");

            s = new Scanner(System.in);
            choice = s.nextLine();
            String filename = "";

            switch (choice.charAt(0)) {
                case '1':
                    filename = "smallListOfActors.txt";
                    break;

                case '2':
                    System.out.println("Downloading actors.list from IMDB...");
                    Downloader.download("ftp://ftp.fu-berlin.de/pub/misc/movies/database/actors.list.gz", System.getProperty("user.dir") + "/actors.list.gz");
                    filename = "actors.list.gz";
                    break;

                case '3':
                    Downloader.download("ftp://ftp.fu-berlin.de/pub/misc/movies/database/actors.list.gz", System.getProperty("user.dir") + "/actors.list.gz");
                    filename = "actors.list.gz";
                    break;

                default:
                    System.exit(0);
            }

            ReadFile file = new ReadFile(choice, filename);
            graph = file.getGraph();

            // Getting "Source" actor
            System.out.println("\nPlease enter a name for the 1st actor: ");
            System.out.print("\n> ");
            s = new Scanner(System.in);
            String sActor = s.nextLine();

            // Getting "Final" actor
            System.out.println("\nNow enter a name for the 2nd actor: ");
            System.out.print("\n> ");
            s = new Scanner(System.in);
            String fActor = s.nextLine();

            if (!graph.containsNode(fActor)) {
                System.out.println("\nSorry, '" + fActor + "' could not be found. Please choose another.");
                return;
            }

            Collection<String> movies = graph.getNextNodes(fActor);
            Collection<String> movies2 = graph.getNextNodes(sActor);

            // Getting "Final" actor
            System.out.println("\nNow tell me what is your 'Bacon Number' bet? (1 to 6) ");
            System.out.print("\n> ");
            s = new Scanner(System.in);
            int bet = Integer.parseInt(s.nextLine());

            DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph, sActor, fActor);
            int result = DijkstraAlgorithm.distance/2;

            if(bet==result) {
                System.out.println("\nYou Win! Do you want to try again? (Y or N)");
                wins++;
            }
            else{
                System.out.println("\nYou loose! Do you want to try again? (Y or N)");
            }
            result = 0;
            System.out.print("\n> ");

            s = new Scanner(System.in);
            choice = s.nextLine();

        }while (choice.charAt(0)!='0' && choice.charAt(0)!='N');

        System.exit(0);

    }

}
