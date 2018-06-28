import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Exam {
	LinkedList<Question> fifoQuestions = new LinkedList<Question>();
	LinkedList fifoAnswers = new LinkedList();
	int size = 0;
	
	public Exam(int size) {
		this.size = size;
	}
	
	public void autoGenerate(ArrayList<Question> questions) {
		Random randomGenerator = new Random();
		for(;this.addQuestion(
			questions.get(
				randomGenerator.nextInt(
						questions.size()
				)
			)
		);) {}
	}
		
	public boolean addQuestion(Question question) {
		if (this.fifoQuestions.size() < this.size) {
			this.fifoQuestions.add(question);
			return true;
		}
		
		return false;
	}

	public boolean addAnswer(boolean answer) {
		if (this.fifoAnswers.size() < this.size) {
			this.fifoAnswers.add(answer);
			return true;
		}

		return false;
	}
	
	public Question getFirstQuestion(Question question) {
		return this.fifoQuestions.removeFirst();
	}
	
	@Override
	public String toString() {
		String s = "Exam with " + this.fifoQuestions.size() + " questions of defined " + this.size + " size.";
		s += this.fifoQuestions;
		
		return s;
	}
}
