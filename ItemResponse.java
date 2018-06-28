

public class ItemResponse {
	private Question question;
	private Student student;

	public ItemResponse(Question q, Student s) {
		this.question = q;
		this.student = s;
	}

	public double getProbability() {
		return (
			Math.exp(
				this.question.getDiscrimination()
				*
				(this.student.getAbility() - this.question.getDifficulty())
			)
		) / (
			(
				1
				+
				(
					Math.exp(
						this.question.getDiscrimination()
						*
						(this.student.getAbility() - this.question.getDifficulty())
					)
				)
			)
		);

	}

	public String getProbabilityReturnFormatted() {
		return String.format("%.6f%%", this.getProbability() * 100);
	}
}
