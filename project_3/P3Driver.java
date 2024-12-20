package mxn220038;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class P3Driver {
    public static void main(String[] args) throws Exception {
        Scanner in;
        if (args.length > 0 && !args[0].equals("-")) {
            File file = new File(args[0]);
            in = new Scanner(file);
        } else {
            in = new Scanner(System.in);
        }
        
		boolean VERBOSE = false;
		if (args.length > 1) { 
			VERBOSE = Boolean.parseBoolean(args[1]); 
		}
	
	    String operation = "";
		int lineno = 0;
		
		MDS mds = new MDS();
		Timer timer = new Timer();
		int id, result, total = 0, price;
		List<Integer> name = new LinkedList<>();
		String filePath = "./p3-t4output.txt";
	
		whileloop:
	        while (in.hasNext()) {
			    lineno++;
			    result = 0;
			    operation = in.next();
			    
			    if(operation.charAt(0) == '#') {
			    	in.nextLine();
			    	continue;
			    }
			    
			    switch (operation) {
			    case "End":
			    	break whileloop;
			    case "Insert":
			    	id = in.nextInt();
			    	price = in.nextInt();
			    	name.clear();
					while(true) {
					    int val = in.nextInt();
					    if(val == 0) { break; }
					    else { name.add(val); }
					}
					result = mds.insert(id, price, name);
					break;
			    case "Find":
					id = in.nextInt();
					result = mds.find(id);
					break;
			    case "Delete":
					id = in.nextInt();
					result = mds.delete(id);
					break;
			    case "FindMinPrice":
					result = mds.findMinPrice(in.nextInt());
					break;
			    case "FindMaxPrice":
					result = mds.findMaxPrice(in.nextInt());
					break;
			    case "FindPriceRange":
					result = mds.findPriceRange(in.nextInt(), in.nextInt(), in.nextInt());
					break;
			    case "RemoveNames":
					id = in.nextInt();
					name.clear();
					while(true) {
					    int val = in.nextInt();
					    if(val == 0) { break; }
					    else { name.add(val); }
					}
					result = mds.removeNames(id, name);
					break;
			    default:
			    	System.out.println("Unknown operation: " + operation);
			    }
			    
			    total += result;
			    
			    if(VERBOSE) { 
			    	System.out.println(lineno + "\t" + operation + "\t" + result + "\t" + total); 
			    	saveToTextFile(lineno + "\t" + operation + "\t" + result + "\t" + total, filePath);
			        //saveToTextFile("This will also be appended to the file.", filePath);
			    	
			 
			        System.out.println("Messages saved to " + filePath);
			    }
		}
		
		System.out.println(total);
		System.out.println(timer.end());
		
		
		
		
    }

    public static void saveToTextFile(String message, String filePath) {
    	try (FileWriter fileWriter =new FileWriter(filePath, true); // 'true' enables appending
    			PrintWriter printWriter=new PrintWriter(fileWriter)) {
    	            printWriter.println(message);
    	        } catch (IOException e) {
    	            System.err.println("An error occurred while writing to the file: " + e.getMessage());
    	        }
    	    }
    
    public static class Timer {
		long startTime, endTime, elapsedTime, memAvailable, memUsed;
	
		public Timer() {
		    startTime = System.currentTimeMillis();
		}
	
		public void start() {
		    startTime = System.currentTimeMillis();
		}
	
		public Timer end() {
		    endTime = System.currentTimeMillis();
		    elapsedTime = endTime-startTime;
		    memAvailable = Runtime.getRuntime().totalMemory();
		    memUsed = memAvailable - Runtime.getRuntime().freeMemory();
		    return this;
		}
	
		public String toString() {
		    return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
		}
	}
}
