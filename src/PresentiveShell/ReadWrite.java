package PresentiveShell;

import java.io.*;
import java.util.LinkedList;

/***
 * This class is extension class
 * which is convenient for users to read input file and write outputs to the file
 */
public class ReadWrite{

    FileWriter writer;
    LinkedList<String> readFile(File inputFile) throws IOException {
        FileReader reader = new FileReader(inputFile);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String temp;
        LinkedList<String> input = new LinkedList<String>();
        while((temp = bufferedReader.readLine()) != null){
            input.add(temp);
        }
        bufferedReader.close();
        return input;
    }
    void writeFile(File outputFile, LinkedList<String> outputList) throws IOException {
        FileWriter writer = new FileWriter(outputFile);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        for (String str:outputList) {
            if (!str.equals("\n")){
                str = str + " ";
            }
            bufferedWriter.write(str);
        }
        bufferedWriter.close();
    }

}
