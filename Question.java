
public class Question {
	private double discrimination;
	private double difficulty;
	
	public Question(double discrimination, double difficulty) {
		this.discrimination = discrimination;
		this.difficulty = difficulty;
	}
	
	public double getDiscrimination() {
		return discrimination;
	}
	public void setDiscrimination(double discrimination) {
		this.discrimination = discrimination;
	}
	public double getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}
	public String getQuestionLine(){
		return this.discrimination + " " + this.difficulty;
	}

	@Override
	public String toString() {
		return "Question [discrimination=" + discrimination + ", difficulty=" + difficulty + "]";
	}
}
