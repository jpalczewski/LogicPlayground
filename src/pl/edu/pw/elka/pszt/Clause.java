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

    public Clause(Clause c) {
        this.literals = new ArrayList<>();
        for(Literal l : c.literals)
        {
            this.literals.add(new Literal(l));
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clause clause = (Clause) o;

        return literals.equals(clause.literals);
    }

    @Override
    public int hashCode() {
        return literals.hashCode();
    }
}
