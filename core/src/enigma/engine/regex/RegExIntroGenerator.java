package enigma.engine.regex;

import java.util.ArrayList;

import enigma.engine.regex.components.KleeneStarRegExComponent;
import enigma.engine.regex.components.OrComponent;
import enigma.engine.regex.components.RegularExpression;
import enigma.engine.regex.components.TerminalRegExComponent;

public class RegExIntroGenerator {

	ArrayList<InstructionData> regularExpressions = new ArrayList<InstructionData>();

	public RegExIntroGenerator() {

		addExpression1();
		addExpression2();
		addExpression3(); // 2 way or
		addExpression4(); // 3 way or
		addExpression5();
		introduceLambda();
		addExpressionX1();
		addExpressionX2();
		addExpressionX3();
		addExpressionX4(); // last module that includes language stuff
		addExpressionX5();
		addExpressionX6();
		addExpressionX7();
		addExpressionX7a();
		addExpressionX7b();
		addExpressionX7c();
		addExpressionX7d();
		addExpressionX78a();
		addExpressionX8();
		addExpressionX9();
		addExpressionX10();

//		language1();
//		language2();
//		language3();
//		language5();
//		language6();
//		language7();
//		language8();
//		language9();
//		language10();
//		language12();
//		language13();
//		language14();
//		language15();
//		language16();
//		language17();

	}

	public void addReview(){//ArrayList<InstructionData> toAddTo) {
		// save original before changing field 
		//ArrayList<InstructionData> temp = regularExpressions;

		// change this field so the below methods add to the parameter
		//regularExpressions = toAddTo;

		language1();
		language2();
		language3();
		language5();
		language6();
		language7();
		language8();
		language9();
		language10();
		language12();
		language13();
		language14();
		language15();
		language16();
		language17();

		// restore temporary back to field
		//regularExpressions = temp;

	}

	private int dataIter = 0;

	public InstructionData getCurrentData() {
		return regularExpressions.get(dataIter);
	}

	public InstructionData nextDataSet() {
		if (dataIter + 1 < regularExpressions.size()) {
			dataIter += 1;
			return regularExpressions.get(dataIter);
		} else {
			return regularExpressions.get(dataIter);
		}
	}

	public InstructionData prevDataSet() {
		if (dataIter - 1 > 0) {
			dataIter--;
			return regularExpressions.get(dataIter);
		} else {
			return regularExpressions.get(dataIter);
		}
	}

	public void dispose() {
		for (InstructionData data : regularExpressions) {
			data.dispose();
		}

	}

	private void addExpression1() {
		InstructionData data = new InstructionData();
		data.regex = new RegularExpression(new TerminalRegExComponent("bb"));
		data.regex.setLock(true);

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("This is a regular expression!");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Regular expressions can be used to generate strings.");

		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.SET_KBUTTON_FLAG_FALSE);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Give it a try, click and drag from the expression.\n" + "          (click on 'bb' and drag far to the right)");

		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.correctOutputs.add("bb");

		// add this sub module to the list
		regularExpressions.add(data);
	}

	private void addExpression2() {
		InstructionData data = new InstructionData();
		data.regex = new RegularExpression(new TerminalRegExComponent("abab"));
		data.regex.setLock(true);

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Here's another regular expression.");

		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.SET_KBUTTON_FLAG_FALSE);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.correctOutputs.add("abab");
		data.instructionTexts.add("Generate a string from it, please.");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("This regex generates a language of only 1 string.");

		regularExpressions.add(data);
	}

	private void addExpression3() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
					new OrComponent(
							false, 	//this prevents parenthesis around the or
							new RegularExpression(new TerminalRegExComponent("a")),
							new RegularExpression(new TerminalRegExComponent("b"))
							)
				);
		//@formatter:on

		data.regex.setLock(true);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Here's a new kind of regular expression.");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("The + symbol is the  OR  symbol.");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.SET_KBUTTON_FLAG_FALSE);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.correctOutputs.add("a");
		data.correctOutputs.add("b");
		data.instructionTexts.add("See what happens when you build a string!");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("You can only choose one or the other.");

		regularExpressions.add(data);
	}

	private void addExpression4() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new OrComponent(
						false, 	//this prevents parenthesis around the or
						new RegularExpression(new TerminalRegExComponent("aa")),
						new RegularExpression(new TerminalRegExComponent("bbaa")),
						new RegularExpression(new TerminalRegExComponent("ba")),
						new RegularExpression(new TerminalRegExComponent("a")),
						new RegularExpression(new TerminalRegExComponent("bbb"))
						)
			);
		//@formatter:on

		data.regex.setLock(true);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Here's  another  Regular  Expression.");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.SET_KBUTTON_FLAG_FALSE);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.correctOutputs.add("aa");
		data.correctOutputs.add("bbaa");
		data.correctOutputs.add("ba");
		data.correctOutputs.add("a");
		data.correctOutputs.add("bbb");

		data.instructionTexts.add("You may choose any of the 5");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("There are only 5 possible strings to make...");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Therefore, the regex's language is 'finite'!");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("The language finite because it only makes 5 strings.");

		regularExpressions.add(data);
	}

	private void addExpression5() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new OrComponent(
						true, 	//this prevents parenthesis around the or
						new RegularExpression(new TerminalRegExComponent("aa")),
						new RegularExpression(new TerminalRegExComponent("bb")),
						new RegularExpression(new TerminalRegExComponent("ab"))
						),
				new TerminalRegExComponent("aba")
			);
		//@formatter:on

		data.regex.setLock(true);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("This one is a bit different than before.");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.SET_KBUTTON_FLAG_FALSE);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.correctOutputs.add("aaaba");
		data.correctOutputs.add("bbaba");
		data.correctOutputs.add("ababa");
		data.instructionTexts.add("Generate a string.");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("There are only 3 possible strings to make...");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Therefore, the regex's language consists of 3 strings");

		regularExpressions.add(data);
	}

	private void introduceLambda() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new TerminalRegExComponent("")
			);
		//@formatter:on

		// data.regex.setLock(true);
		data.drawLambda = true;
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("The Greek symbol below is called Lambda!");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("It's the symbol for the \"Empty String\"");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("It represents a string without any content;\n i.e. the \"Empty String\".");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("When a regex produces string with 0 letters,\n   then think of it as producing lambda");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Only special regular expressions\ncan make lambda; let's look at one");

		regularExpressions.add(data);

	}

	private void addExpressionX1() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
						new TerminalRegExComponent("a")
				)
			);
		//@formatter:on

		data.regex.setLock(true);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("The * below is called a kleene star");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Think of it as:       \"0 or more of\".");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("This one makes \"0 or more\" 'a' letters");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("If it doesn't generate anything,\nthen it is said to generate lambda!");

		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("I'll draw the lambda for you :)\n");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Try it out!");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Notice the text stays yellow after you drag.");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Kleene star's will go to infinity;\n you must choose when it stops!");
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("well, until you drag something it is going to produce
		// lambda.");

		regularExpressions.add(data);

	}

	private void addExpressionX2() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
						new OrComponent(
								false,
								new RegularExpression(new TerminalRegExComponent("a")),
								new RegularExpression(new TerminalRegExComponent("b"))
								)
				)
			);
		//@formatter:on

		data.regex.setLock(true);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("This one has an OR within the kleene star!");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("This means you can choose either letter");

		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		// data.actions.add(InstructionAction.SET_KBUTTON_FLAG_FALSE);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.STRING_LENGTH_ATLEAST_MINSIZE);
		data.instructionTexts.add("Play around -- make a string of at least 5 length");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("Is there a string made of 'a's and 'b's\n that this regex
		// can't produce?");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("Nope! It can generate any string of \n0 or more 'a's or 'b's
		// concatenated...");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("           The langauge it generates is\n {LAMBDA,  a,  b,  aa,  ab,  ba,  bb,  aaa,  ...}");

		regularExpressions.add(data);

	}

	private void addExpressionX3() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
						new OrComponent(
								false,
								new RegularExpression(new TerminalRegExComponent("aa")),
								new RegularExpression(new TerminalRegExComponent("b"))
								)
				)
				
			);
		//@formatter:on

		data.regex.setLock(true);
		data.minSize = 6;
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("This one's slightly different.");

		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		// data.actions.add(InstructionAction.SET_KBUTTON_FLAG_FALSE);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.STRING_LENGTH_ATLEAST_MINSIZE);
		data.instructionTexts.add("Play around -- Make a string of at least length 6");

		regularExpressions.add(data);

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("Can you think of some small strings\n that this regex can't
		// produce?");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("Some I thought of:\n a, abb, bba, aaa");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add(" Think you could list the language this\n string creates in
		// lexicographic ordering?");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("         The langauge it generates is\n {LAMBDA,  b,  aa,  bb,  aab,  baa, bbb, ...}");

	}

	private void addExpressionX4() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
			new OrComponent(false,
				new KleeneStarRegExComponent(
						false,
						new RegularExpression(new TerminalRegExComponent("b"))
				),
				new RegularExpression(
						new TerminalRegExComponent("aa"),
						new KleeneStarRegExComponent(
								false,
								new RegularExpression(new TerminalRegExComponent("b"))
							)
				),
				new RegularExpression(new TerminalRegExComponent("a"))
			)
			);
		//@formatter:on

		data.regex.setLock(true);

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("This one's a bit more complicated.");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("The OR has 3 potential elements\n" + "                'b*', 'aab*', and 'a'");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Notice that some letters are not yet highlighted...");

		// data.actions.add(InstructionAction.UNLOCKREGEX);
		// data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("To understand why, play around with some strings.\n                            ['r' key resets the regex]");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("If the middle element (aab*) is chosen,\n" + "  then 'aa' must come before any 'b's\n" + "This is why b was not initially highlighted.");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Make the string \"aa\",\n(hint: choose 0 'b's)\n   ['r' key resets]");
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.correctOutputs.add("aa");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("          You may have noticed the lambda,\n which element do you think can produce lambda?");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("         The first 'b*' allows for\n the empty string (lambda) to be generated");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("The Language generated by this regular expression is\n" + "the
		// union of the languages produced by the 3 elements.\n" + "{LAMBDA, b, bb, ...} U {aaa,
		// aaba, ...} U {aabba, aabbba, ...}");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("{LAMBDA, b, bb, ...} U {aaa, aaba, ...} U {aabba, aabbba,
		// ...}");

		regularExpressions.add(data);

	}

	private void addExpressionX5() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
					new KleeneStarRegExComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("a"))
					),
					new KleeneStarRegExComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("b"))
					)
				);
		//@formatter:on
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Look at this!");
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("Think it can make any string of 'a's or 'b's?");

		data.regex.setLock(true);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Try to make aaba! (hint: you can't!)");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("  Once you take the first 'b',\nthere's no going back to 'a's");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("The reason 'b' is yellow in the beginning" + "\n          is because you can take 0 'a's");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.correctOutputs.add("aabbb");
		data.instructionTexts.add("please make aabbb\n     ['r' key to reset]");

		regularExpressions.add(data);
	}

	private void addExpressionX6() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
					new KleeneStarRegExComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("a"))
					),
					new KleeneStarRegExComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("b"))
					)
				)
			);
		//@formatter:on

		data.regex.setLock(true);

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("This one's almost the same as the last...");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("But now, the entire expression is within a kleene star!");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.instructionTexts.add("Try and make 'aaba' now!\n        [press 'r' to reset]");
		data.correctOutputs.add("aaba");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("          The same rules as before apply\n          (no more 'a's once a 'b' is taken)" + "\nHowever, we can take 0 more more of 'a*b*'");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("           So, if we need an 'a' after a 'b',\n" + "then we use the outter * to get a second 'a*b*' ");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Let's move to a new regular expression");

		regularExpressions.add(data);

	}

	private void addExpressionX7() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new OrComponent(
					new RegularExpression(new TerminalRegExComponent("ab")),
					new RegularExpression(new TerminalRegExComponent("ba"))
				),
				new KleeneStarRegExComponent(
						new OrComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("aa")),
							new RegularExpression(new TerminalRegExComponent("bb"))
						)
				),
				new OrComponent(
						new RegularExpression(new TerminalRegExComponent("ab")),
						new RegularExpression(new TerminalRegExComponent("ba"))
				)
			);
		//@formatter:on

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Alright, let's see what you can do! ;)");

		data.regex.setLock(true);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.instructionTexts.add("Try and make 'baaaab'.\n      [press 'r' to reset]");
		data.correctOutputs.add("baaaab");

		regularExpressions.add(data);
	}

	private void addExpressionX7a() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new OrComponent(
					new RegularExpression(new TerminalRegExComponent("ab")),
					new RegularExpression(new TerminalRegExComponent("ba"))
				),
				new KleeneStarRegExComponent(
						new OrComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("aa")),
							new RegularExpression(new TerminalRegExComponent("bb"))
						)
				),
				new OrComponent(
						new RegularExpression(new TerminalRegExComponent("ab")),
						new RegularExpression(new TerminalRegExComponent("ba"))
				)
			);
		//@formatter:on

		data.regex.setLock(true);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.instructionTexts.add("How about 'abbbaaab'.\n     [press 'r' to reset]");
		data.correctOutputs.add("abbbaaab");

		regularExpressions.add(data);
	}

	private void addExpressionX7b() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new OrComponent(
					new RegularExpression(new TerminalRegExComponent("ab")),
					new RegularExpression(new TerminalRegExComponent("ba"))
				),
				new KleeneStarRegExComponent(
						new OrComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("aa")),
							new RegularExpression(new TerminalRegExComponent("bb"))
						)
				),
				new OrComponent(
						new RegularExpression(new TerminalRegExComponent("ab")),
						new RegularExpression(new TerminalRegExComponent("ba"))
				)
			);
		//@formatter:on

		data.regex.setLock(true);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.instructionTexts.add("What about 'baab'?\n  [press 'r' to reset]");
		data.correctOutputs.add("baab");

		regularExpressions.add(data);
	}

	private void addExpressionX7c() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new OrComponent(
					new RegularExpression(new TerminalRegExComponent("ab")),
					new RegularExpression(new TerminalRegExComponent("ba"))
				),
				new KleeneStarRegExComponent(
						new OrComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("aa")),
							new RegularExpression(new TerminalRegExComponent("bb"))
						)
				),
				new OrComponent(
						new RegularExpression(new TerminalRegExComponent("ab")),
						new RegularExpression(new TerminalRegExComponent("ba"))
				)
			);
		//@formatter:on

		data.regex.setLock(true);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.instructionTexts.add("Make 'abaabbba'\n[press 'r' to reset]");
		data.correctOutputs.add("abaabbba");

		regularExpressions.add(data);
	}

	private void addExpressionX7d() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new OrComponent(
					new RegularExpression(new TerminalRegExComponent("ab")),
					new RegularExpression(new TerminalRegExComponent("ba"))
				),
				new KleeneStarRegExComponent(
						new OrComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("aa")),
							new RegularExpression(new TerminalRegExComponent("bb"))
						)
				),
				new OrComponent(
						new RegularExpression(new TerminalRegExComponent("ab")),
						new RegularExpression(new TerminalRegExComponent("ba"))
				)
			);
		//@formatter:on

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("     Let's try one without highlight\n That's more like an exam scenario.");

		data.regex.setLock(true);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		// data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.instructionTexts.add("Try and make 'babbab'. [press 'r' to reset]\n" + "note: highlight is turned off - trust in the force!");
		data.correctOutputs.add("babbab");

		regularExpressions.add(data);
	}

	private void addExpressionX78a() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
						new OrComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("aa")),
							new RegularExpression(new TerminalRegExComponent("bb"))
						)
				)
			);
		//@formatter:on

		data.regex.setLock(true);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.DRAW_LAMBDA);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("Alright, think this regex can generate\n" + " a string with
		// odd letters?");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("Sorry -- it can't :(");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.instructionTexts.add("Try to make 'aaaabb'. :)\n         ['r' to reset]");
		data.correctOutputs.add("aaaabb");
		regularExpressions.add(data);
	}

	private void addExpressionX8() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
					new OrComponent(
						false,
						new RegularExpression(new TerminalRegExComponent("a")),
						new RegularExpression(new TerminalRegExComponent("b"))
					)
				),
				new RegularExpression(new TerminalRegExComponent("bb")),
				new KleeneStarRegExComponent(
						new OrComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("a")),
							new RegularExpression(new TerminalRegExComponent("b"))
						)
				)
			);
		//@formatter:on
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Another strange one...");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("Can you think of a string this cannot make?");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("Here's some; 'a', 'b' 'aa'");

		data.regex.setLock(true);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Would you please make me an 'ababbbaba'?");
		data.correctOutputs.add("ababbbaba");

		regularExpressions.add(data);
	}

	private void addExpressionX9() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
					new OrComponent(
							false,
							new TerminalRegExComponent("aa"),
							new TerminalRegExComponent("ab"),
							new TerminalRegExComponent("ba"),
							new TerminalRegExComponent("bb")
						)
				),
				new OrComponent(
					new TerminalRegExComponent("a"),
					new TerminalRegExComponent("b")
				)
			);
		//@formatter:on
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("There is more than meets the eye to this one.");
		//
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("Does it produce even or odd strings?");
		//
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("This one can only make odd length strings.");

		data.regex.setLock(true);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.correctOutputs.add("ababa");
		data.instructionTexts.add("Create 'ababa'. ['r' to reset]");

		regularExpressions.add(data);
	}

	private void addExpressionX10() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
					new TerminalRegExComponent("aa")
				),
				new TerminalRegExComponent("a"),
				new KleeneStarRegExComponent(
					new TerminalRegExComponent("bb")
				)

			);
		//@formatter:on

		data.regex.setLock(true);
		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.actions.add(InstructionAction.DRAW_STRING_MATCHES);
		data.correctOutputs.add("aaaaabb");
		data.instructionTexts.add("One last practice before review, Please make 'aaaaabb'");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Bravo! You've made it very far\n in a very short amount of time!");

		//TODO load text
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Press K to start load - this may take a moment.");
		
		data.actions.add(InstructionAction.LOAD_NEXT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Done!");
		
		regularExpressions.add(data);
	}

	private void language1() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
					new OrComponent(
							false, 	//this prevents parenthesis around the or
							new RegularExpression(new TerminalRegExComponent("a")),
							new RegularExpression(new TerminalRegExComponent("b"))
							)
				);
		//@formatter:on

		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "the language of 'a' or 'b'");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Here starts the review.");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("The languages for the previous\nregular expressions are provided.");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("You may play with the regular expressions,\nor simply skip over them.");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("I'll occasionally drop note about a\nlanguage if one is needed.");

		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("Thank you for testing my app. :D");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {a,  b}.");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("This language is 'FINITE'; it has a specific number of strings");

		regularExpressions.add(data);
	}

	private void language2() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new OrComponent(
						false, 	//this prevents parenthesis around the or
						new RegularExpression(new TerminalRegExComponent("aa")),
						new RegularExpression(new TerminalRegExComponent("bbaa")),
						new RegularExpression(new TerminalRegExComponent("ba")),
						new RegularExpression(new TerminalRegExComponent("a")),
						new RegularExpression(new TerminalRegExComponent("bbb"))
						)
			);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {ab,  bbaa,  ba,  a,  bbb}.");

		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("There's a regular expression for any finite language.\n" + "You can make the regex with 'a lot' of OR symbols.");
		regularExpressions.add(data);
	}

	private void language3() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new OrComponent(
						true, 	//this prevents parenthesis around the or
						new RegularExpression(new TerminalRegExComponent("aa")),
						new RegularExpression(new TerminalRegExComponent("bb")),
						new RegularExpression(new TerminalRegExComponent("ab"))
						),
				new TerminalRegExComponent("aba")
			);
		//@formatter:on

		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {aaaba,  ababa,  bbaba}.");
		regularExpressions.add(data);
	}

	private void language5() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
						new TerminalRegExComponent("a")
				)
			);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {LAMBDA,  a,  aa,  aaa,  aaaa,  ...}.");
		regularExpressions.add(data);

	}

	private void language6() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
						new OrComponent(
								false,
								new RegularExpression(new TerminalRegExComponent("a")),
								new RegularExpression(new TerminalRegExComponent("b"))
								)
				)
			);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {a,  b,  aa,  ab,  ba,  bb,  aaa, ...}.");
		regularExpressions.add(data);

	}

	private void language7() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
						new OrComponent(
								false,
								new RegularExpression(new TerminalRegExComponent("aa")),
								new RegularExpression(new TerminalRegExComponent("b"))
								)
				)
				
			);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {b,  aa,  bb,  aab,  baa,  bbb, ...}.");
		regularExpressions.add(data);
	}

	private void language8() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
			new OrComponent(false,
				new KleeneStarRegExComponent(
						false,
						new RegularExpression(new TerminalRegExComponent("b"))
				),
				new RegularExpression(
						new TerminalRegExComponent("aa"),
						new KleeneStarRegExComponent(
								false,
								new RegularExpression(new TerminalRegExComponent("b"))
							)
				),
				new RegularExpression(new TerminalRegExComponent("a"))
			)
			);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("                                       Language\n{LAMBDA, b, bb, bbb, ..} U {aa, aab, aabb, ...} U {a}.");
		regularExpressions.add(data);
	}

	private void language9() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
					new KleeneStarRegExComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("a"))
					),
					new KleeneStarRegExComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("b"))
					)
				);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {a,  b,  aa,  ab,  bb,  aaa,  aab, abb ...}.");
		regularExpressions.add(data);
	}

	private void language10() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
					new KleeneStarRegExComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("a"))
					),
					new KleeneStarRegExComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("b"))
					)
				)
			);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {LAMBDA,  a,  b,  aa,  ab,  ba,  bb,  aaa,  ...}.");
		regularExpressions.add(data);
	}

	private void language12() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new OrComponent(
					new RegularExpression(new TerminalRegExComponent("ab")),
					new RegularExpression(new TerminalRegExComponent("ba"))
				),
				new KleeneStarRegExComponent(
						new OrComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("aa")),
							new RegularExpression(new TerminalRegExComponent("bb"))
						)
				),
				new OrComponent(
						new RegularExpression(new TerminalRegExComponent("ab")),
						new RegularExpression(new TerminalRegExComponent("ba"))
				)
			);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {abab,  abba,  baab,  baba,  abaaab,  ...}.");
		regularExpressions.add(data);
	}

	private void language13() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
						new OrComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("aa")),
							new RegularExpression(new TerminalRegExComponent("bb"))
						)
				)
			);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.DRAW_LAMBDA);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {LAMBDA,  aa,  bb,  aaaa,  aabb,  bbbb, aaaaaa}.");
		regularExpressions.add(data);
	}

	private void language14() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
					new OrComponent(
						false,
						new RegularExpression(new TerminalRegExComponent("a")),
						new RegularExpression(new TerminalRegExComponent("b"))
					)
				),
				new RegularExpression(new TerminalRegExComponent("bb")),
				new KleeneStarRegExComponent(
						new OrComponent(
							false,
							new RegularExpression(new TerminalRegExComponent("a")),
							new RegularExpression(new TerminalRegExComponent("b"))
						)
				)
			);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {bb,  abb,  bbb,  abba,  abbb,  bbba,  bbbb,  ...}.");
		regularExpressions.add(data);
	}

	private void language15() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
					new OrComponent(
							false,
							new TerminalRegExComponent("aa"),
							new TerminalRegExComponent("ab"),
							new TerminalRegExComponent("ba"),
							new TerminalRegExComponent("bb")
						)
				),
				new OrComponent(
					new TerminalRegExComponent("a"),
					new TerminalRegExComponent("b")
				)
			);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("{LAMBDA, aaa, aab, aba, abb, baa, bab, bba, bbb, aaaaa, ...}.");
		regularExpressions.add(data);
	}

	private void language16() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
				new KleeneStarRegExComponent(
					new TerminalRegExComponent("aa")
				),
				new TerminalRegExComponent("a"),
				new KleeneStarRegExComponent(
					new TerminalRegExComponent("bb")
				)

			);
		//@formatter:on
		data.regex.setLock(true);
		// data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		// data.instructionTexts.add("English explanation:\n"
		// + "");

		data.actions.add(InstructionAction.UNLOCKREGEX);
		data.actions.add(InstructionAction.HIGHLIGHT);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);
		data.instructionTexts.add("Language = {a, aaa, abb, aaaaa, aaabb, abbbb, ...}.");
		regularExpressions.add(data);

		// data.instructionTexts.add("You've completed the introduction to\n regular expressions
		// module!");
		// regularExpressions.add(data);
	}

	private void language17() {
		InstructionData data = new InstructionData();
		//@formatter:off
		data.regex = new RegularExpression(
					new TerminalRegExComponent("")
			);
		//@formatter:on
		data.regex.setLock(true);
		data.actions.add(InstructionAction.SET_KBUTTON_FLAG_FALSE);
		data.actions.add(InstructionAction.PRINT_INSTRUCTION);

		data.instructionTexts.add("You've completed the introduction to\n      regular expressions module!");
		regularExpressions.add(data);
	}
}
