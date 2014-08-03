/**
 * Author : Samuel Bezerra Gomes
 * Student Number : 2869370
 **/

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFile {
    private String actor;
    private String stillActor; //If there is just the name of the movie in line (in the .list file) it must keep the same actor as before
    private String movie;
    private Graph<String> graph = new Graph<String>();
    public File source;
    public int maxLinesSmallFile = 8454;
    public int maxLinesBigFile = 15934003;


    public ReadFile(String choice, String aFileName) throws FileNotFoundException {

        source = new File(aFileName);

        // Applying different methods for processing each file
        // If it is the small version we apply a simpler regex in a method without the use of threads
        if(choice.charAt(0)=='1'){
            readFile(choice);
        }
        else if(choice.charAt(0)=='2'){
            readFile(choice);
        }

        // If not we use threads to read this BIG list file
        else if(choice.charAt(0)=='3'){
            long fileSizeInKB = source.length() / 1024;
            System.out.println("\nFile size: " + fileSizeInKB + " KB");
            System.out.println("Processing actors and movies...\nThis operation can take a long time because the size of the file...\n\nReading line: ");

            int numCores = Runtime.getRuntime().availableProcessors(); //Get the number of cores
            System.out.println("\nNumber of cores in processor: "+numCores);
            ExecutorService executorService = Executors.newFixedThreadPool(numCores);

            for(int i=0; i<numCores; i++) {
                Runnable processLine = new threadForBigFile(i, source, (maxLinesBigFile / numCores) * i - (maxLinesBigFile / numCores), (maxLinesBigFile / numCores) * i);
                executorService.execute(processLine);
            }
            while (!executorService.isTerminated()) {
            }
        }

    }

    private void readFile(String choice) throws FileNotFoundException{
        Scanner scanner = new Scanner(new FileReader(source));
        int i = 0;
        try{
            while(scanner.hasNextLine()){
                if(choice.charAt(0)=='1'){
                    System.out.print(i + " read lines from maxLine: " + maxLinesSmallFile);
                    System.out.print("\r");
                    String line = scanner.nextLine();
                    processLineFromShortFile(line);
                }
                else if(choice.charAt(0)=='2'){
                    System.out.print(i + " read lines from maxLine: " + maxLinesBigFile);
                    System.out.print("\r");
                    String line = scanner.nextLine();
                    processLineFromBigFile(line);
                }
                i++;
            }
        }
        finally{
            // Finished reading :)
            System.out.println("\nFinished reading file");
            scanner.close();
        }

    }

    private void processLineFromShortFile(String line){
        String l = line;
        String[] results = l.split("\\->");
        actor = results[0];
        movie = results[1];
        if( !graph.containsNode(actor)){
            graph.addNode(actor);
        }
        if( !graph.containsNode(movie)){
            graph.addNode(movie);
        }
        if( !graph.containsEdge(actor,movie)){
            graph.addEdge(actor, movie);
        }
    }
    //The job for the threadpool
    public class threadForBigFile  implements Runnable {

        public File source;
        public int minLine;
        public int maxLine;
        int j;

        threadForBigFile(int j, File sourceFile, int min, int max) {
            this.source = sourceFile;
            this.minLine = min;
            this.maxLine = max;
            this.j=j;
        }

        public void run() {

            Scanner scanner = null;
            try {
                System.out.println("\nThread " + j + ":");
                scanner = new Scanner(new FileReader(source));
                String line = "";
                int i;
                for (i = minLine; i < maxLine; i++) { //Beginning the search in line 240
                    System.out.print(i + " read lines from maxLine: " + maxLine);
                    System.out.print("\r");
                    processLineFromBigFile(line);
                    line = scanner.nextLine();
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                // Finished reading :)
                System.out.println("\nFinished reading file");
                scanner.close();
            }
        }


    }
    //Processing the original IMDB file
    private void processLineFromBigFile(String line){
        String l = line;

        //Using Regex to get the Actor's name
        Pattern p = Pattern.compile("[^\\t(]*"); //searching for the first character of the line until the first parenthesis
        Matcher m = p.matcher(l);
        String[] resultActor = new String[2];
        if(m.find()) {
            resultActor = m.group().split("\\, "); //Splitting the full name result in a array
            //example: resultActor[0] = Bacon and resultActor[1] = Kevin
        }
        if(resultActor.length > 1) {
            String firstName = resultActor[1];
            String secondName = resultActor[0];

            actor = firstName+" "+secondName;
            stillActor = actor;
        }
        //Emptying the array for the next use
        Arrays.fill(resultActor, null);

        //Using Regex to get the Movie name
        Pattern p2 = Pattern.compile("\\t.*\\).");
        Matcher m2 = p2.matcher(l);
        String resultMovie = "";
        if(m2.find()){
            resultMovie = (String) m2.group().subSequence(1, m2.group().length()-1);
        }

        //removing blank space before the movie string
        movie = resultMovie.trim();

        //System.out.println("\nActor: "+stillActor);
        //System.out.println("Movie: "+movie);

        if( !graph.containsNode(stillActor)){
            graph.addNode(stillActor);
        }
        if( !graph.containsNode(movie)){
            graph.addNode(movie);
        }
        if( !graph.containsEdge(stillActor,movie)){
            graph.addEdge(stillActor, movie);
        }

    }
    //getGraph Method
    public Graph<String> getGraph(){
        return graph;
    }

}
