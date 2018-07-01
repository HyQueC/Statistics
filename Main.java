import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Arrays;


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

	public static void resolveExam(Question question, int iterations, double[] probStudent, ItemResponse[] answerStudent, Student[] students, Random rand, int[][] scorePerExam){
		// Response objects
		answerStudent[0] = new ItemResponse(question, students[0]);
		answerStudent[1] = new ItemResponse(question, students[1]);
		answerStudent[2]= new ItemResponse(question, students[2]);
		answerStudent[3] = new ItemResponse(question, students[3]);
		answerStudent[4] = new ItemResponse(question, students[4]);

		// Aggregation variables
		probStudent[0] = answerStudent[0].getProbability();
		probStudent[1] = answerStudent[1].getProbability();
		probStudent[2] = answerStudent[2].getProbability();
		probStudent[3] = answerStudent[3].getProbability();
		probStudent[4] = answerStudent[4].getProbability();

		for (int j = 0; j < iterations; j++) {
			if (rand.nextFloat() < probStudent[0]) {
				scorePerExam[0][j]++;
			}
			if (rand.nextFloat() < probStudent[1]) {
				scorePerExam[1][j]++;
			}
			if (rand.nextFloat() < probStudent[2]) {
				scorePerExam[2][j]++;
			}
			if (rand.nextFloat() < probStudent[3]) {
				scorePerExam[3][j]++;
			}
			if (rand.nextFloat() < probStudent[4]) {
				scorePerExam[4][j]++;
			}
		}
	}
	public static void solvePartTwoOne()throws FileNotFoundException, UnsupportedEncodingException {
		Pool p = new Pool();
		ArrayList<Question> questionPool = p.readQuestionFile("questoes.txt");
		ArrayList<Student> studentPool = new ArrayList<Student>();
		int[][] answerPool = p.readAnswerFile("respostas.txt");
		PrintWriter response = p.getFileWriter("II1.txt");


		// Estimate ability of each student from the test's results
			// Get the derivative from ItemResponse formula
			// Apply one of the methods (Bisection, Newton-Raphson, Secant Method).

	}

	public static  void solvePartTwoTwo() throws FileNotFoundException, UnsupportedEncodingException {
		Pool p = new Pool();
		Student[] students = p.readStudentFile("II1.txt", 5);
		PrintWriter response = p.getFileWriter("II2.txt");
		ItemResponse[] answerStudent = new ItemResponse[5];
		double[] probStudent = new double[5];


		int[] N = { 10,20,50, 100};
		int iterations = 1000000;

		for(int i = 0; i < N.length; i++){
			Exam e = createBestTest(N[i]);
			Question question;
			Random rand = new Random();

			int[][] scorePerExam = new int[students.length][iterations];

			for (Iterator<Question> iterator = e.fifoQuestions.iterator(); iterator.hasNext(); ) {
				question = (Question) iterator.next();
				resolveExam(question, iterations, probStudent, answerStudent, students, rand, scorePerExam);
			}

			int[] studentIndexBetterThanFixed = new int[4];

			for (int studentIndex = 3; studentIndex >= 0; studentIndex--) {
				for (int iterationIndex = 0; iterationIndex < iterations; iterationIndex++) {
					if (scorePerExam[4][iterationIndex] > scorePerExam[studentIndex][iterationIndex]) {
						studentIndexBetterThanFixed[studentIndex]++;
					}
				}
			}

			for (int a = 0; a < studentIndexBetterThanFixed.length; a++) {
				double finalProbability = ((double) studentIndexBetterThanFixed[a] / (double) iterations);
				System.out.println("Aluno 5 foi melhor que o " + Integer.toString(a) + " (+1) " + Integer.toString(studentIndexBetterThanFixed[a]) + " vezes de " + Integer.toString(iterations) + " (" + Double.toString(finalProbability * 100) + "%)");
				response.print(String.format("%.40f", (finalProbability)) + " ");
			}

			response.println();

		}
		response.close();

	}

	public static void solvePartTwoThree() throws FileNotFoundException, UnsupportedEncodingException{
		Pool p = new Pool();
		Student[] students = p.readStudentFile("II1.txt", 5);
		PrintWriter response = p.getFileWriter("II3.txt");
		ItemResponse[] answerStudent = new ItemResponse[5];
		double[] probStudent = new double[5];

		int[] N = { 10,20,50, 100};
		int iterations = 1000000;

		for(int i = 0; i < N.length; i++){
			Exam e = createBestTest(N[i]);
			Question question;
			Random rand = new Random();

			int[][] scorePerExam = new int[students.length][iterations];

			for (Iterator<Question> iterator = e.fifoQuestions.iterator(); iterator.hasNext(); ) {
				question = (Question) iterator.next();
				resolveExam(question, iterations, probStudent, answerStudent, students, rand, scorePerExam);
			}
			/*
				For each X in scorePerExam[studentIndex][X] we have one of the Iterations-results of a Exam made with N[i] number of questions
				So, to create a confidence interval with alpha = 0.1 from the results of the Student, we acquire the frequency of his notes,
				all of the 'Iterations', ordenate them in an array and search for the shorter interval of notes that includes 90% of the student's results in the exam.
			*/
			int k = 0;
			double[][] confidenceInterval = new double[students.length][2];
			while(k < students.length) {
				Arrays.sort(scorePerExam[k]);
				confidenceInterval[k][0] = scorePerExam[k][0];
				confidenceInterval[k][1] = scorePerExam[k][scorePerExam[k].length-1];
				for(int a = 0, j = (int) (iterations*0.9) -1; j < iterations; a++, j++){
					if( scorePerExam[k][j] - scorePerExam[k][a] <  confidenceInterval[k][1] - confidenceInterval[k][0]){
						confidenceInterval[k][0] = scorePerExam[k][a];
						confidenceInterval[k][1] = scorePerExam[k][j];
					}
				}
				k++;
			}

			for (int a = 0; a < confidenceInterval.length; a++) {
				System.out.println("Aluno: "+ (a+1) +" Inicio do intervalo "+ (confidenceInterval[a][0]* 100/N[i])+ " fim do intervalo "+ (confidenceInterval[a][1]* 100/N[i]));
				response.print( (confidenceInterval[a][0]* 100/N[i]) + " " + (confidenceInterval[a][1]* 100/N[i]) + " " );
			}

			response.println();

		}
		response.close();

	}

	public static void solvePartTwoFour() throws FileNotFoundException, UnsupportedEncodingException{

	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {


	}

}
