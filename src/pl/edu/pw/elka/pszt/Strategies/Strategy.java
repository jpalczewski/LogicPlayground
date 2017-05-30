package pl.edu.pw.elka.pszt.Strategies;

import pl.edu.pw.elka.pszt.KnowledgeBase;

import java.util.ArrayList;

/**
 * Created by erxyi on 30.05.2017.
 */
public abstract class Strategy {

    ArrayList<KnowledgeBase> steps;

    public Strategy(KnowledgeBase knownFacts) {
        steps = new ArrayList<>();
        steps.add(knownFacts);
    }

    public abstract void solve();
}
