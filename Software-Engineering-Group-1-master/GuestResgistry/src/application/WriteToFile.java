package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteToFile {

	public  void write (String[] locals) throws IOException{
		
		int i = 0;
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("LocationOfVisitor.txt")));
		
		while(i < locals.length && locals[i] != null){
			System.out.println("This is a thing: " + locals[i]);
			writer.println(locals[i]);
			i++;
		}
		
	}
	
}
