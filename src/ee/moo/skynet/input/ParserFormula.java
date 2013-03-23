package ee.moo.skynet.input;

import ee.moo.skynet.Formula;
import ee.moo.skynet.Node;
import ee.moo.skynet.NodeType;
import ee.moo.skynet.alphabet.AlphabetFormula;
import ee.moo.skynet.util.Stack;

/**
 * User: tarmo
 * Date: 3/23/13
 * Time: 4:47 PM
 */
public class ParserFormula extends Parser {

    private enum State {
        FOO,
        BAR
    }

    private AlphabetFormula alphabet = new AlphabetFormula();

    @Override
    public Formula parse(String input) {

        Formula formula = new Formula();
        Formula current = formula;

        Stack<Formula> stack;

        stack = new Stack<Formula>();
        stack.push(formula);

        Lexer lexer = new Lexer(input, alphabet);
        Token token;

        while ((token = lexer.next()) != null) {

            switch (token.getType()) {

                case LEFTPAREN:

                    stack.push(current);

                    current.setLeft(new Formula(new Node()));
                    current = current.getLeft();
                    break;

                case INVERSION:
                    // TODO: implement inversion

                    break;


                case STATEMENT:

                    current.setNode(new Node(NodeType.STATEMENT, token.getData()));
                    current = stack.pop();

                    break;

                case CONJUNCTION:

                    stack.push(current);

                    current.setNode(new Node(NodeType.CONJUNCTION));
                    current.setRight(new Formula(new Node()));

                    current = current.getRight();
                    break;


                case DISJUNCTION:

                    stack.push(current);

                    current.setNode(new Node(NodeType.DISJUNCTION));
                    current.setRight(new Formula(new Node()));

                    current = current.getRight();
                    break;

                case IMPLICATION:

                    stack.push(current);

                    current.setNode(new Node(NodeType.IMPLICATION));
                    current.setRight(new Formula(new Node()));

                    current = current.getRight();
                    break;

                case EQUIVALENCE:

                    stack.push(current);

                    current.setNode(new Node(NodeType.EQUIVALENCE));
                    current.setRight(new Formula(new Node()));

                    current = current.getRight();
                    break;


                case RIGHTPAREN:

                    current = stack.pop();
                    break;

                case WHITESPACE:
                    break;

                default:
                    throw new ParserException(String.format("Illegal token: %s", token));

            }
        }

        return formula;
    }

}
