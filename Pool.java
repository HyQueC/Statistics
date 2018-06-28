import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;



public class Pool {


	private String poolPath = "./";

	public ArrayList<Question> readQuestionFile(String fileName){

		File file = new File(this.poolPath + fileName);
		ArrayList<Question> questions = new ArrayList<Question>();

		try {

	        Scanner sc = new Scanner(file);

	        while (sc.hasNextLine()) {
	        	String[] line = sc.nextLine().split(" ");
	        	questions.add(new Question(Double.parseDouble(line[0]), Double.parseDouble(line[1])));
	        }
	        sc.close();
	    }
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

		return questions;

	}

	public int[][] readAnswerFile(String fileName){

		File file = new File(this.poolPath + fileName);
		int[][] answers = new int[100][2000];

		try {

			Scanner sc = new Scanner(file);

			while (sc.hasNextLine()) {
				String[] line = sc.nextLine().split(" ");

			}
			sc.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return answers;

	}



	public PrintWriter getFileWriter(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(this.poolPath + fileName, "UTF-8");
		return writer;
	}

}
