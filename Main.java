import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Main {


	public static Exam createBestTest(int N) throws FileNotFoundException, UnsupportedEncodingException {
		Pool p = new Pool();
		ArrayList<Question> questionPool = p.readQuestionFile("questoes.txt");
		ItemResponse answerStudent4;

		Student[] students = {
				new Student(-1.0),
				new Student(-0.5),
				new Student(0),
				new Student(0.5),
				new Student(1.0),
		};


			Exam e = new Exam(N);
			Question bestQuestion = null;
			Question question;
			ItemResponse selector;
			double prob;
			while (e.fifoQuestions.size() < e.size) {

				prob = 0;
				for (Iterator<Question> iterator = questionPool.iterator(); iterator.hasNext(); ) {
					question = (Question) iterator.next();
					selector = new ItemResponse(question, students[4]);
					answerStudent4 = new ItemResponse(question, students[3]);
					if (selector.getProbability() - answerStudent4.getProbability() > prob && e.fifoQuestions.indexOf(question) == -1) {
						prob = selector.getProbability() - answerStudent4.getProbability();
						bestQuestion = question;
					}
				}
				e.fifoQuestions.add(bestQuestion);
			}


		return e;
	}

	public static void solvePartTwoOne()throws FileNotFoundException, UnsupportedEncodingException {
		Pool p = new Pool();
		ArrayList<Question> questionPool = p.readQuestionFile("questoes.txt");
		ArrayList<Student> studentPool = new ArrayList<Student>();
		int[][] answerPool = new int[100][2000];
		PrintWriter response = p.getFileWriter("II1.txt");



	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		createBestTest(10);


	}

}
