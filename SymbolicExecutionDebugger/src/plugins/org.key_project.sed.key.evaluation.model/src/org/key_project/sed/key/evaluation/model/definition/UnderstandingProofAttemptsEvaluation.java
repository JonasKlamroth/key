package org.key_project.sed.key.evaluation.model.definition;

import java.net.URL;
import java.util.List;

import org.key_project.sed.key.evaluation.model.Activator;
import org.key_project.sed.key.evaluation.model.random.UnderstandingProofAttemptsRandomFormOrderComputer;
import org.key_project.sed.key.evaluation.model.tooling.JavaProjectModifier;
import org.key_project.sed.key.evaluation.model.tooling.JavaProjectModifier.FileDefinition;
import org.key_project.sed.key.evaluation.model.tooling.ProofAttemptJavaProjectModifier;
import org.key_project.sed.key.evaluation.model.validation.FixedValueValidator;
import org.key_project.sed.key.evaluation.model.validation.NotUndefinedValueValidator;
import org.key_project.util.java.CollectionUtil;

public class UnderstandingProofAttemptsEvaluation extends AbstractEvaluation {
   /**
    * The only instance of this class.
    */
   public static UnderstandingProofAttemptsEvaluation INSTANCE = new UnderstandingProofAttemptsEvaluation();
   
   /**
    * The name of the {@link Tool} representing 'KeY'.
    */
   public static final String KEY_TOOL_NAME = "KeY";

   /**
    * The name of the {@link Tool} representing 'SED'.
    */
   public static final String SED_TOOL_NAME = "SED";
   
   /**
    * Page name of proof 1.
    */
   public static final String PROOF_1_PAGE_NAME = "proof1";

   /**
    * Page name of proof 2.
    */
   public static final String PROOF_2_PAGE_NAME = "proof2";

   /**
    * Page name of proof 3.
    */
   public static final String PROOF_3_PAGE_NAME = "proof3";

   /**
    * Page name of proof 4.
    */
   public static final String PROOF_4_PAGE_NAME = "proof4";

   /**
    * Page name of the send evaluation page.
    */
   public static final String SEND_EVALUATION_PAGE_NAME = "sendEvaluation";

   /**
    * Forbid additional instances.
    */
   private UnderstandingProofAttemptsEvaluation() {
      super("Understanding Proof Attempts");
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected List<Tool> computeTools() {
      URL keyURL = Activator.getDefault().getBundle().getEntry("data/understandingProofAttempts/KeY.html");
      URL sedURL = Activator.getDefault().getBundle().getEntry("data/understandingProofAttempts/SED.html");
      Tool key = new Tool(KEY_TOOL_NAME, keyURL);
      Tool sed = new Tool(SED_TOOL_NAME, sedURL);
      return CollectionUtil.toList(key, sed);
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected List<AbstractForm> computeForms() {
      // Create introduction form
      URL conditionsURL = Activator.getDefault().getBundle().getEntry("data/understandingProofAttempts/conditions.html");
      QuestionPage conditionsPage = new QuestionPage("conditionsPage", 
                                                     "Introduction", 
                                                     "Please read the information and conditions of the evaluation carefully.",
                                                     null,
                                                     new BrowserQuestion("conditions", conditionsURL),
                                                     new RadioButtonsQuestion("acceptConditions",
                                                                              null, 
                                                                              true,
                                                                              "no", 
                                                                              new FixedValueValidator("yes", "Conditions are not accepted."), 
                                                                              new Choice("I &accept the conditions", "yes"), 
                                                                              new Choice("I do &not accept the conditions", "no")));
      QuestionPage backgroundPage = new QuestionPage("backgroundPage", 
                                                     "Background", 
                                                     "Please fill out the form with your background knowledge.",
                                                     null,
                                                     new RadioButtonsQuestion("experienceWithJava",
                                                                              "Experience with Java: ", 
                                                                              true,
                                                                              null, 
                                                                              new NotUndefinedValueValidator("Experience with Java not defined."), 
                                                                              new Choice("None", "None"), 
                                                                              new Choice("< 2 years", "Less than 2 years"), 
                                                                              new Choice(">= 2 years", "More than 2 years")),
                                                     new RadioButtonsQuestion("experienceWithKeY",
                                                                              "Experience with KeY: ", 
                                                                              true,
                                                                              null, 
                                                                              new NotUndefinedValueValidator("Experience with KeY not defined."), 
                                                                              new Choice("None", "None"), 
                                                                              new Choice("< 2 years", "Less than 2 years"), 
                                                                              new Choice(">= 2 years", "More than 2 years")),
                                                     new RadioButtonsQuestion("experienceWithSED",
                                                                              "Experience with SED: ", 
                                                                              true,
                                                                              null, 
                                                                              new NotUndefinedValueValidator("Experience with SED not defined."), 
                                                                              new Choice("None", "None"), 
                                                                              new Choice("< 1 year", "Less than 1 year"), 
                                                                              new Choice(">= 1 year", "More than 1 year")));
      SendFormPage sendConditionsPage = new SendFormPage("sendConditions", 
                                                         "Confirm Sending Content", 
                                                         "Inspect the content to be send.", 
                                                         "Current date and time (nothing else!)");
      FixedForm introductionForm = new FixedForm("introductionForm", 
                                                 new UnderstandingProofAttemptsRandomFormOrderComputer(),
                                                 conditionsPage, 
                                                 backgroundPage,
                                                 sendConditionsPage);
      // Create evaluation form
      ToolPage keyToolPage = new ToolPage(getTool(KEY_TOOL_NAME));
      ToolPage sedToolPage = new ToolPage(getTool(SED_TOOL_NAME));
      QuestionPage proof1Page = createMinQuestionPage(PROOF_1_PAGE_NAME, "Proof Attempt 1");
      QuestionPage proof2Page = createCalendarQuestionPage(PROOF_2_PAGE_NAME, "Proof Attempt 2");
      QuestionPage proof3Page = createMinQuestionPage(PROOF_3_PAGE_NAME, "Proof Attempt 3");
      QuestionPage proof4Page = createMyIntegerQuestionPage(PROOF_4_PAGE_NAME, "Proof Attempt 4");
      SendFormPage sendEvaluationPage = new SendFormPage(SEND_EVALUATION_PAGE_NAME, 
                                                         "Confirm Sending Content", 
                                                         "Inspect the content to be send.", 
                                                         "Current date and time (nothing else!)");
      RandomForm evaluationForm = new RandomForm("evaluationForm", keyToolPage, sedToolPage, proof1Page, proof2Page, proof3Page, proof4Page, sendEvaluationPage);
      // Create thanks form
      QuestionPage thanksPage = new QuestionPage("thanksPage", "Evaluation sucessfully completed", "Thank you for participating in the evaluation.", null);
      FixedForm thanksForm = new FixedForm("thanksForm", thanksPage);
      // Create forms
      return CollectionUtil.toList(introductionForm, evaluationForm, thanksForm);
   }
   
   protected QuestionPage createMyIntegerQuestionPage(String pageName, String title) {
      String howToCloseTitle = "How can the proof be closed?";
      CheckboxQuestion howToCloseQuestion = new CheckboxQuestion("howToClose", 
                                                                 howToCloseTitle, 
                                                                 true,
                                                                 null, 
                                                                 new NotUndefinedValueValidator("Question '" + howToCloseTitle + "' not answered."), 
                                                                 new Choice("Using the auto mode", "Using the auto mode"), 
                                                                 new Choice("Applying rules interactively", "Applying rules interactively"));
      String locationTitle = "Which not specified location(s) have changed?";
      CheckboxQuestion locationQuestion = new CheckboxQuestion("whichLocationsHaveChanged", 
                                                               locationTitle, 
                                                               true,
                                                               null, 
                                                               new NotUndefinedValueValidator("Question '" + locationTitle + "' not answered."), 
                                                               new Choice("self", "self"),
                                                               new Choice("self.value", "self.value"),
                                                               new Choice("summand", "summand"),
                                                               new Choice("summand.value", "summand.value"));
      String thrownExceptionTitle = "Which exception(s) are thrown?";
      CheckboxQuestion thrownExceptionQuestion = new CheckboxQuestion("whichExceptionsAreThrown", 
                                                                      thrownExceptionTitle, 
                                                                      true,
                                                                      null, 
                                                                      new NotUndefinedValueValidator("Question '" + thrownExceptionTitle + "' not answered."), 
                                                                      new Choice("java.lang.NullPointerException", "java.lang.NullPointerException"),
                                                                      new Choice("java.lang.ArithmeticException", "java.lang.ArithmeticException"),
                                                                      new Choice("java.lang.OutOfMemoryError", "java.lang.OutOfMemoryError"));
      String whyOpenTitle = "Why is the proof still open?";
      CheckboxQuestion whyOpenQuestion = new CheckboxQuestion("whyOpen", 
                                                              whyOpenTitle, 
                                                              true,
                                                              null, 
                                                              new NotUndefinedValueValidator("Question '" + whyOpenTitle + "' not answered."), 
                                                              new Choice("Rule application stopped to early, proof is closeable", "Stopped to early", howToCloseQuestion), 
                                                              new Choice("Precondition (summand != null) is not established", "Precondition not established"),
                                                              new Choice("Postcondition (value == \\old(value) + summand.value) does not hold", "Postcondition does not hold"),
                                                              new Choice("Assignable clause does not hold", "Assignable clause does not hold", locationQuestion),
                                                              new Choice("Exception is thrown (normal_behavior violated)", "Exception is thrown", thrownExceptionQuestion));
      String openQuestionTitle = "Is the proof closed?";
      RadioButtonsQuestion openQuestion = new RadioButtonsQuestion("openOrClosed", 
                                                                   openQuestionTitle, 
                                                                   true,
                                                                   null, 
                                                                   new NotUndefinedValueValidator("Question '" + openQuestionTitle + "' not answered."), 
                                                                   new Choice("Yes", "Yes"), 
                                                                   new Choice("No", "No", whyOpenQuestion));
      String executedTitle = "Was statement (value += summand.value) at line 9 executed during symbolic execution of the proof?";
      RadioButtonsQuestion executedQuestion = new RadioButtonsQuestion("executedStatements", 
                                                                       executedTitle, 
                                                                       true,
                                                                       null, 
                                                                       new NotUndefinedValueValidator("Question '" + executedTitle + "' not answered."), 
                                                                       new Choice("Yes", "Yes"),
                                                                       new Choice("No", "No"));
      return new QuestionPage(pageName, 
                              title, 
                              "Please answer the question to the best of your knowledge.", 
                              new ProofAttemptJavaProjectModifier("MyInteger",
                                                                  "add",
                                                                  new String[] {"QMyInteger;"},
                                                                  new FileDefinition("data/understandingProofAttempts/proofMyInteger/MyInteger.proof", JavaProjectModifier.SOURCE_FOLDER_NAME + "/MyInteger.proof", false),
                                                                  new FileDefinition("data/understandingProofAttempts/proofMyInteger/MyInteger.java", JavaProjectModifier.SOURCE_FOLDER_NAME + "/MyInteger.java", true)),
                              new LabelQuestion("generalDescription", "Please inspect the current proof attempt carefully and answer the following questions about it as best as possible."),
                              openQuestion,
                              executedQuestion);
      // TODO: How to fix code or specifications?
   }
   
   protected QuestionPage createMinQuestionPage(String pageName, String title) {
      String howToCloseTitle = "How can the proof be closed?";
      CheckboxQuestion howToCloseQuestion = new CheckboxQuestion("howToClose", 
                                                                 howToCloseTitle, 
                                                                 true,
                                                                 null, 
                                                                 new NotUndefinedValueValidator("Question '" + howToCloseTitle + "' not answered."), 
                                                                 new Choice("Using the auto mode", "Using the auto mode"), 
                                                                 new Choice("Applying rules interactively", "Applying rules interactively"));
      String thrownExceptionTitle = "Which exception(s) are thrown?";
      CheckboxQuestion thrownExceptionQuestion = new CheckboxQuestion("whichExceptionsAreThrown", 
                                                                      thrownExceptionTitle, 
                                                                      true,
                                                                      null, 
                                                                      new NotUndefinedValueValidator("Question '" + thrownExceptionTitle + "' not answered."), 
                                                                      new Choice("java.lang.NullPointerException", "java.lang.NullPointerException"),
                                                                      new Choice("java.lang.ArithmeticException", "java.lang.ArithmeticException"),
                                                                      new Choice("java.lang.ArrayIndexOutOfBoundsException", "java.lang.ArrayIndexOutOfBoundsException"),
                                                                      new Choice("java.lang.ArrayStoreException", "java.lang.ArrayStoreException"),
                                                                      new Choice("java.lang.OutOfMemoryError", "java.lang.OutOfMemoryError"));
      String whyOpenTitle = "Why is the proof still open?";
      CheckboxQuestion whyOpenQuestion = new CheckboxQuestion("whyOpen", 
                                                              whyOpenTitle, 
                                                              true,
                                                              null, 
                                                              new NotUndefinedValueValidator("Question '" + whyOpenTitle + "' not answered."), 
                                                              new Choice("Rule application stopped to early, proof is closeable", "Stopped to early", howToCloseQuestion), 
                                                              new Choice("Precondition (array != null) is not established", "Precondition not established"),
                                                              new Choice("Postcondition (array == null || array.length == 0 ==> \\result == -1) does not hold", "Not found postcondition does not hold", createMinTerminationQuestion("postNotFoundTermination")),
                                                              new Choice("Postcondition (array != null && array.length >= 1 ==> (\\forall int i; i >= 0 && i < array.length; array[\\result] <= array[i])) does not hold", "Found postcondition does not hold", createMinTerminationQuestion("postFoundTermination")),
                                                              new Choice("Assignable clause of method contract does not hold", "Assignable clause of method contract does not hold", createMinLocationQuestion("whichMethodLocationsHaveChanged"), createMinTerminationQuestion("methodAssignableTermination")),
                                                              new Choice("Exception is thrown (normal_behavior violated)", "Exception is thrown", thrownExceptionQuestion),
                                                              new Choice("Loop invariant (i >= 1 && i <= array.length) does not hold initially", "Loop invariant about i does not hold initially", createMinTerminationQuestion("initialITermination")),
                                                              new Choice("Loop invariant (minIndex >= 0 && minIndex < i) does not hold initially", "Loop invariant about minIndex does not hold initially", createMinTerminationQuestion("initialMinIndexTermination")),
                                                              new Choice("Loop invariant (\\forall int j; j >= 0 && j < i; array[minIndex] <= array[j]) does not hold initially", "Loop invariant about array elements does not hold initially", createMinTerminationQuestion("initialArrayElementsTermination")),
                                                              new Choice("Loop invariant (i >= 1 && i <= array.length) is not preserved by loop guard and loop body", "Loop invariant about i is not preserved", createMinTerminationQuestion("preservedITermination")),
                                                              new Choice("Loop invariant (minIndex >= 0 && minIndex < i) is not preserved by loop guard and loop body", "Loop invariant about minIndex is not preserved", createMinTerminationQuestion("preservedMinIndexTermination")),
                                                              new Choice("Loop invariant (\\forall int j; j >= 0 && j < i; array[minIndex] <= array[j]) is not preserved by loop guard and loop body", "Loop invariant about array elements is not preserved", createMinTerminationQuestion("preservedArrayElementsTermination")),
                                                              new Choice("Decreasing term (array.length - i) is not fulfilled by loop", "Decreasing term is not fulfilled", createMinTerminationQuestion("decreasingTermination")),
                                                              new Choice("Assignable clause of loop does not hold", "Assignable clause of loop does not hold", createMinLocationQuestion("whichLoopLocationsHaveChanged"), createMinTerminationQuestion("loopAssignableTermination")));
      String openQuestionTitle = "Is the proof closed?";
      RadioButtonsQuestion openQuestion = new RadioButtonsQuestion("openOrClosed", 
                                                                   openQuestionTitle, 
                                                                   true,
                                                                   null, 
                                                                   new NotUndefinedValueValidator("Question '" + openQuestionTitle + "' not answered."), 
                                                                   new Choice("Yes", "Yes"), 
                                                                   new Choice("No", "No", whyOpenQuestion));
      String executedTitle = "Which statement(s) are executed at least once during symbolic execution of the proof?";
      CheckboxQuestion executedQuestion = new CheckboxQuestion("executedStatements", 
                                                               executedTitle, 
                                                               true,
                                                               null, 
                                                               new NotUndefinedValueValidator("Question '" + executedTitle + "' not answered."), 
                                                               new Choice("None of the statements was executed", "None"),
                                                               new Choice("Line 8 (if (array != null))", "Line 8"),
                                                               new Choice("Line 9 (if (array.length == 0))", "Line 9"),
                                                               new Choice("Line 10 (return -1)", "Line 10"),
                                                               new Choice("Line 12 (array.length == 1)", "Line 12"),
                                                               new Choice("Line 13 (return array[0])", "Line 13"),
                                                               new Choice("Line 16 (int minIndex = 0)", "Line 16"),
                                                               new Choice("Line 24 (int i = 1)", "Line 24 initial"),
                                                               new Choice("Line 24 (i < array.length)", "Line 24 condition"),
                                                               new Choice("Line 24 (i++)", "Line 24 update"),
                                                               new Choice("Line 25 (if (array[i] < array[minIndex]))", "Line 25"),
                                                               new Choice("Line 26 (minIndex = 1)", "Line 26"),
                                                               new Choice("Line 33 (return minIndex)", "Line 33"),
                                                               new Choice("Line 37 (return -1)", "Line 37"));
      return new QuestionPage(pageName, 
                              title, 
                              "Please answer the question to the best of your knowledge.", 
                              new ProofAttemptJavaProjectModifier("ArrayUtil",
                                                                  "minIndex",
                                                                  new String[] {"[I"},
                                                                  new FileDefinition("data/understandingProofAttempts/proofMin/ArrayUtil.proof", JavaProjectModifier.SOURCE_FOLDER_NAME + "/ArrayUtil.proof", false),
                                                                  new FileDefinition("data/understandingProofAttempts/proofMin/ArrayUtil.java", JavaProjectModifier.SOURCE_FOLDER_NAME + "/ArrayUtil.java", true)),
                              new LabelQuestion("generalDescription", "Please inspect the current proof attempt carefully and answer the following questions about it as best as possible."),
                              openQuestion,
                              executedQuestion);
      // TODO: How to fix code or specifications?
   }
   
   protected CheckboxQuestion createMinLocationQuestion(String name) {
      String title = "Which not specified location(s) have changed?";
      return new CheckboxQuestion(name, 
                                  title, 
                                  true,
                                  null, 
                                  new NotUndefinedValueValidator("Question '" + title + "' not answered."), 
                                  new Choice("array", "array"),
                                  new Choice("array.length", "array.length"),
                                  new Choice("array[0]", "array[0]"),
                                  new Choice("array[*]", "array[*]"),
                                  new Choice("minIndex", "minIndex"),
                                  new Choice("i", "i"));
   }
   
   protected CheckboxQuestion createMinTerminationQuestion(String name) {
      String title = "At which execution path?";
      return new CheckboxQuestion(name, 
                                  title, 
                                  true,
                                  null, 
                                  new NotUndefinedValueValidator("Question '" + title + "' not answered."), 
                                  new Choice("Return 1 (array != null & array.length == 0)", "Return 1"),
                                  new Choice("Return 2 (array != null & array.length == 1)", "Return 2"),
                                  new Choice("Return 3 (array != null & array.length > 1)", "Return 3"),
                                  new Choice("Return 4 (array == null)", "Return 4"),
                                  new Choice("Loop End 1 (array[i] < array[minIndex])", "Loop End 1"),
                                  new Choice("Loop End 2 (array[i] >= array[minIndex])", "Loop End 2"));
   }
   
   protected QuestionPage createCalendarQuestionPage(String pageName, String title) {
      String howToCloseTitle = "How can the proof be closed?";
      CheckboxQuestion howToCloseQuestion = new CheckboxQuestion("howToClose", 
                                                                 howToCloseTitle, 
                                                                 true,
                                                                 null, 
                                                                 new NotUndefinedValueValidator("Question '" + howToCloseTitle + "' not answered."), 
                                                                 new Choice("Using the auto mode", "Using the auto mode"), 
                                                                 new Choice("Applying rules interactively", "Applying rules interactively"));
      String thrownExceptionTitle = "Which exception(s) are thrown?";
      CheckboxQuestion thrownExceptionQuestion = new CheckboxQuestion("whichExceptionsAreThrown", 
                                                                      thrownExceptionTitle, 
                                                                      true,
                                                                      null, 
                                                                      new NotUndefinedValueValidator("Question '" + thrownExceptionTitle + "' not answered."), 
                                                                      new Choice("java.lang.NullPointerException", "java.lang.NullPointerException"),
                                                                      new Choice("java.lang.ArithmeticException", "java.lang.ArithmeticException"),
                                                                      new Choice("java.lang.ArrayIndexOutOfBoundsException", "java.lang.ArrayIndexOutOfBoundsException"),
                                                                      new Choice("java.lang.ArrayStoreException", "java.lang.ArrayStoreException"),
                                                                      new Choice("java.lang.OutOfMemoryError", "java.lang.OutOfMemoryError"));
      String whyOpenTitle = "Why is the proof still open?";
      CheckboxQuestion whyOpenQuestion = new CheckboxQuestion("whyOpen", 
                                                              whyOpenTitle, 
                                                              true,
                                                              null, 
                                                              new NotUndefinedValueValidator("Question '" + whyOpenTitle + "' not answered."), 
                                                              new Choice("Rule application stopped to early, proof is closeable", "Stopped to early", howToCloseQuestion), 
                                                              new Choice("Precondition (entry != null) is not established", "Precondition not established"),
                                                              new Choice("Invariant (entrySize >= 0 && entrySize < entries.length) is not established", "Invariant not established"),
                                                              new Choice("Postcondition (entries[\\old(entrySize)] == entry) does not hold", "Postcondition about entry does not hold"),
                                                              new Choice("Postcondition (entrySize == \\old(entrySize) + 1) does not hold", "Postcondition about entrySize does not hold"),
                                                              new Choice("Invariant (entrySize >= 0 && entrySize < entries.length) is not preserved", "Invariant not preserved"),
                                                              new Choice("Assignable clause does not hold", "Assignable clause does not hold", createCalendarLocationQuestion("whichMethodLocationsHaveChanged")),
                                                              new Choice("Exception is thrown (normal_behavior violated)", "Exception is thrown", thrownExceptionQuestion),
                                                              new Choice("Loop invariant (i >= 0 && i <= entries.length) does not hold initially", "Loop invariant about i does not hold initially"),
                                                              new Choice("Loop invariant (\\forall int j; j >= 0 && j < i; newEntries[j] == entries[j]) does not hold initially", "Loop invariant about array elements does not hold initially"),
                                                              new Choice("Loop invariant (i >= 0 && i <= entries.length) is not preserved by loop guard and loop body", "Loop invariant about i is not preserved"),
                                                              new Choice("Loop invariant (\\forall int j; j >= 0 && j < i; newEntries[j] == entries[j]) is not preserved by loop guard and loop body", "Loop invariant about array elements is not preserved"),
                                                              new Choice("Decreasing term (entries.length - i) is not fulfilled by loop", "Decreasing term is not fulfilled"),
                                                              new Choice("Assignable clause of loop does not hold", "Assignable clause of loop does not hold", createCalendarLocationQuestion("whichLoopLocationsHaveChanged")));
      String openQuestionTitle = "Is the proof closed?";
      RadioButtonsQuestion openQuestion = new RadioButtonsQuestion("openOrClosed", 
                                                                   openQuestionTitle, 
                                                                   true,
                                                                   null, 
                                                                   new NotUndefinedValueValidator("Question '" + openQuestionTitle + "' not answered."), 
                                                                   new Choice("Yes", "Yes"), 
                                                                   new Choice("No", "No", whyOpenQuestion));
      String executedTitle = "Which statement(s) are executed at least once during symbolic execution of the proof?";
      CheckboxQuestion executedQuestion = new CheckboxQuestion("executedStatements", 
                                                               executedTitle, 
                                                               true,
                                                               null, 
                                                               new NotUndefinedValueValidator("Question '" + executedTitle + "' not answered."), 
                                                               new Choice("Line 14 (if (entrySize == entries.length))", "Line 14"),
                                                               new Choice("Line 15 (Entry[] newEntries = new Entry[entries.length * 2])", "Line 15"),
                                                               new Choice("Line 22 (int i = 0)", "Line 22 initial"),
                                                               new Choice("Line 22 (i < entries.length)", "Line 22 condition"),
                                                               new Choice("Line 22 (i++)", "Line 22 update"),
                                                               new Choice("Line 23 (newEntries[i] = entries[i])", "Line 23"),
                                                               new Choice("Line 25 (entries = newEntries)", "Line 25"),
                                                               new Choice("Line 27 (entries[entrySize] = entry)", "Line 27"),
                                                               new Choice("Line 28 (entrySize++)", "Line 28"));
      return new QuestionPage(pageName, 
                              title, 
                              "Please answer the question to the best of your knowledge.", 
                              new ProofAttemptJavaProjectModifier("MyInteger",
                                                                  "add",
                                                                  new String[] {"QMyInteger;"},
                                                                  new FileDefinition("data/understandingProofAttempts/proofCalendar/Calendar.proof", JavaProjectModifier.SOURCE_FOLDER_NAME + "/Calendar.proof", false),
                                                                  new FileDefinition("data/understandingProofAttempts/proofCalendar/Calendar.java", JavaProjectModifier.SOURCE_FOLDER_NAME + "/Calendar.java", true)),
                              new LabelQuestion("generalDescription", "Please inspect the current proof attempt carefully and answer the following questions about it as best as possible."),
                              openQuestion,
                              executedQuestion);
      // TODO: How to fix code or specifications?
   }
   
   protected CheckboxQuestion createCalendarLocationQuestion(String name) {
      String title = "Which not specified location(s) have changed?";
      return new CheckboxQuestion(name, 
                                  title, 
                                  true,
                                  null, 
                                  new NotUndefinedValueValidator("Question '" + title + "' not answered."), 
                                  new Choice("None of the statements was executed", "None"),
                                  new Choice("entries", "entries"),
                                  new Choice("entries[entrySize]", "entries[entrySize]"),
                                  new Choice("entries[*]", "entries[*]"),
                                  new Choice("entries.length", "entries.length"),
                                  new Choice("entrySize", "entrySize"),
                                  new Choice("i", "i"),
                                  new Choice("newEntries", "newEntries"),
                                  new Choice("newEntries.length", "newEntries.length"),
                                  new Choice("newEntries[*]", "newEntries[*]"));
   }
   
   public RandomForm getEvaluationForm() {
      return (RandomForm) getForm("evaluationForm");
   }
}