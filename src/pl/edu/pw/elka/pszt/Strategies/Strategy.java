package pl.edu.pw.elka.pszt.Strategies;

import pl.edu.pw.elka.pszt.Clause;
import pl.edu.pw.elka.pszt.KnowledgeBase;

import java.util.ArrayList;

/**
 * Created by erxyi on 30.05.2017.
 */
public abstract class Strategy {

    public ArrayList<KnowledgeBase> getSteps() {
        return steps;
    }

    ArrayList<KnowledgeBase> steps;

    public void setSteps(ArrayList<KnowledgeBase> steps) {
        this.steps = steps;
    }

    public Clause getHipotesis() {
        return hipotesis;
    }

    public void setHipotesis(Clause hipotesis) {
        this.hipotesis = hipotesis;
    }

    Clause hipotesis;

    public Strategy(KnowledgeBase knownFacts) {
        steps = new ArrayList<>();
        steps.add(knownFacts);
    }

    public abstract int runOneStep();

    public void solve() {
        int addedInLastStep = runOneStep();
        while(
                !steps.get(steps.size()-1).isContradictory()
                        &&
                        addedInLastStep != 0
                )
            addedInLastStep = runOneStep();

    }

    public boolean isLastStepContradictory() {
        return steps.get(steps.size()-1).isContradictory();
    }
}
