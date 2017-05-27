package pl.edu.pw.elka.pszt;

import java.util.ArrayList;

/**
 * Created by erxyi on 27.05.2017.
 */
public class Clause {
    ArrayList<Literal> literals;


    public Clause(ArrayList<Literal> literals) {
        this.literals = literals;
    }

    public Clause() {
        this.literals = new ArrayList<>();
    }

    public void addLiteral(Literal l)
    {
        this.literals.add(l);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < literals.size(); i++) {
            sb.append(literals.get(i).toString());
            if(i  < literals.size()-1)
                sb.append(" v ");
        }
        return sb.toString();
    }
}
