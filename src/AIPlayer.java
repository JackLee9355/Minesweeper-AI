import java.util.ArrayList;

public class AIPlayer implements Player {

    private Board board;

    @Override
    public int[] nextLocation() {

        ArrayList<Rule> rules = getRules();
        double bestOdds = 1.0; // 1.0 represents a explosion.
        ArrayList<Rule> bestRules = new ArrayList<>();
        for(Rule rule : rules) {
            double curOdds = rule.getMines() / (double) rule.getCells().length;
            if (curOdds < bestOdds) {
                bestOdds = curOdds;
                bestRules.clear();
            }
            if(bestOdds == curOdds) {
                bestRules.add(rule);
            }
        }

        Rule randomRule = bestRules.get((int) (Math.random() * bestRules.size()));
        Cell randomCell = randomRule.getCells()[(int) (Math.random() * randomRule.getCells().length)];
        int[] guess = {randomCell.getRow(), randomCell.getColumn()};
        System.out.println("The AI is about to guess " + guess[0] + ", " + guess[1]);
        return guess;
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    private ArrayList<Rule> getRules() {
        ArrayList<Rule> rules = new ArrayList<>();

        // Creates the rules directly observable from the board.
        int allUnrevealed = 0;
        Cell[] allUnrevealedCells = new Cell[board.getCols() * board.getRows() - board.revealedSpaces()];

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Cell cell = board.getCell(i, j);

                if (cell.isRevealed()) {
                    // To remove rules corresponding to certain cells, do so here.

                    Cell[] adjacentCells = board.cellsAdjacentTo(i, j);
                    int unrevealed = 0;
                    Cell[] tmpCells = new Cell[adjacentCells.length];
                    for (Cell adjacentCell : adjacentCells) {
                        if (!adjacentCell.isRevealed()) {
                            tmpCells[unrevealed++] = adjacentCell;
                        }
                    }
                    Cell[] unrevealedCells = new Cell[unrevealed];
                    System.arraycopy(tmpCells, 0, unrevealedCells, 0, unrevealed);

                    rules.add(new Rule(cell.getState().getValue(), unrevealedCells));
                } else {
                    allUnrevealedCells[allUnrevealed++] = cell;
                }
            }
        }

        // this is the rule based simply on how many mines are left on the board relative to the number of unguessed locations.
        rules.add(new Rule(board.getMines(), allUnrevealedCells));

        // Creates rules based on combinations of the other rules.
        int generation = 0;
        boolean newRuleInferred;
        do {
            newRuleInferred = false;
            System.out.println("Generation " + generation++ + " rules created");

            ArrayList<Rule> newRules = new ArrayList<>();

            for(Rule rule : rules) {
                otherRules:
                for (Rule otherRule : rules) {
                    if (!rule.isSubRule(otherRule))
                        continue;
                    Rule newRule = otherRule.subtractRule(rule);
                    for(Rule possibleEqualRules : rules) {
                        if(newRule.equivalentRule(possibleEqualRules))
                            continue otherRules;
                    }
                    newRules.add(newRule);
                    newRuleInferred = true;
                }
            }

            rules.addAll(newRules);

        } while (newRuleInferred);

        return rules;
    }


}
