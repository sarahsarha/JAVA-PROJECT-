
/**
 * Class      : Question
 * Creator    : Member 3
 * Tester     : Member 3
 * Description: Represents a single quiz question with its type, options and correct answer.
 */
public class Question {

    // ── Attributes ──────────────────────────────────────────────
    private String questionText;
    private String[] options;      // null for True/False questions
    private String correctAnswer;
    private String questionType;   // "MCQ" or "TF"

    // ── Constructor ─────────────────────────────────────────────
    public Question(String questionText, String[] options,
                    String correctAnswer, String questionType) {
        this.questionText  = questionText;
        this.options       = options;
        this.correctAnswer = correctAnswer;
        this.questionType  = questionType;
    }

    // ── Getters ─────────────────────────────────────────────────
    public String getQuestionText() { return questionText; }
    public String[] getOptions()    { return options; }
    public String getQuestionType() { return questionType; }

    /**
     * Checks whether the supplied answer matches the correct answer.
     * Case-insensitive so "true" and "TRUE" both pass.
     */
    public boolean checkAnswer(String ans) {
        return correctAnswer.equalsIgnoreCase(ans.trim());
    }
}