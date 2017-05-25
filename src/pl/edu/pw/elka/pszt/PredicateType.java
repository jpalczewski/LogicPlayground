package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.Arguments.ArgumentType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erxyi on 25.05.2017.
 */
public class PredicateType {
    String name;
    ArrayList<ArgumentType> arguments;

    public PredicateType(String name, ArrayList<ArgumentType> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public PredicateType(String name) {
        this.name = name;
        arguments = new ArrayList<>();
    }

    public void addArgument(ArgumentType at) {
        arguments.add(at);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append('(');
        for (int i = 0; i < arguments.size(); i++) {
            sb.append(arguments.get(i).toString());
            if(i != arguments.size()-1)
                sb.append(',');
        }
        sb.append(')');
        return sb.toString();
    }
}
