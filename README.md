Java-Kevin-Bacon-Game
=====================
This code was written in Java with javac version 1.7.0_45
To run this code from the command line type
```bash
$ javac -Xlint src/Kevin.java
$ java src/Kevin
```
Note: If there is any problem to find the javac or java command in CMD on Windows, just set the right path for the JDK bin folder:

 set PATH=%PATH%;C:\Program Files (x86)\Java\jdk1.(VERSION)\bin

Follow the steps on screen prompts for program usage
or refers to the second version of the proposal.

=================================

Step-by-step example interaction:

- Introduction: In this assignment i decided to make it like a game for a better understanding of the assignment logic and for a better interaction with the user.

- How-to: 
    1)First of all the actors.list file is too big for reading in a simple program, so i’ve  implemented the 3 options for the user to choose between:
	1. Small version of IMDB Actor's list");
	2. Full version  of IMDB Actor's list (can take DAYS to process the file)");
	3. Full version  of IMDB Actor's list with threads (can take HOURS to process the file)

	- For the 1st option, i found a smaller version of the IMDB actor’s list file with 8.454 lines on the internet to simplify the task for reading the file instead of the original one with 15.934.003 lines.

	- The 2nd option is the most time consuming for reading the big IMDB file, because it will download the actors.list.gz file and after process it without using threads.

	- The 3rd option is the fastest way to read the big IMDB file using a thread pool that uses all cores of the CPU to do the task. First it detects the available processors in the machine with the method Runtime.getRuntime().availableProcessors() that returns a int value. Then it starts the thread pool with a Runnable instance of the threadForBigFile job. 
	Code:
		```java
				int numCores = Runtime.getRuntime().availableProcessors(); //Get the number of cores
        System.out.println("\nNumber of cores in processor: "+numCores);
        ExecutorService executorService = Executors.newFixedThreadPool(numCores);

        for(int i=0; i<numCores; i++) {
            Runnable processLine = new threadForBigFile(i, source, (maxLinesBigFile / numCores) * i - (maxLinesBigFile / numCores), (maxLinesBigFile / numCores) * i);
            executorService.execute(processLine);
        }
		```
	NOTE: This approach just works with SSD(Solid state drive), otherwise if using multiple threads to read from a single mechanical disk (or HDD) will hurt performance instead of improving it. “The above happens because the disks mechanical head needs to keep seeking the next position to read. Using multiple threads means that when each thread gets a chance to run it will direct the head to a different section of the disk, thus making it bounce between disk areas inefficiently.”
Source: 
<a>http://stackoverflow.com/questions/10397075/using-threadpools-threading-for-reading-large-txt-files.</a> 

		2) To read both of files, the small version and the original one, and stock certains parts of data like “actors” and “movies” i preferred to use the most commonly solution: Regex!

	To do it with the small file i used:
	Code: 
		 ```java
		 String[] results = l.split("\\->");
		 actor = results[0];
        	 movie = results[1];
		 ```
	And with the big file:
	Code:
		```java
		//Searching for actors
		Pattern p = Pattern.compile("[^\\t(]*");   //searching for the first character of the line until the first parenthesis
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

		//Searching for movies
		Pattern p2 = Pattern.compile("\\t.*\\).");
        	Matcher m2 = p2.matcher(l);
        	String resultMovie = "";
        	if(m2.find()){
        	    resultMovie = (String) m2.group().subSequence(1, m2.group().length()-1);
        	}
	
	        //removing blank space before the movie string
	        movie = resultMovie.trim();
	  ```
    3) To find the shortest path in the data network i implemented the DijkstraAlgorithm class that takes the file.getGraph method from Kevin.class, the firstActor and the secondActor to search for the shortest path between the two actors. To do that i splitted all nodes into two different sets, then a node is moved to the settled set if and only if a shortest path from the first actor to this node was found.
    4) The user must put a name for the 1st and 2nd actor that exists in the list files, otherwise the user gets the following error message:
	Code:
  ```java      
	if (!graph.containsNode(fActor)) {
            System.out.println("\nSorry, '" + fActor + "' could not be found. Please choose another.");
            return;
	}
  ```
    5) At the end to implement a “game” program type, i compared the Bacon number that comes out from the result of the DijkstraAlgorithm class with a bet from the user:

	int result = DijkstraAlgorithm.distance/2;
	Code:
		```java
    if(bet==result) {
      System.out.println("\nYou Win! Do you want to try again? (Y or N)");
      }
      else{
      System.out.println("\nYou loose! Do you want to try again? (Y or N)");
    }      
		```
