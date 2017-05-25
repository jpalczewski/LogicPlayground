package pl.edu.pw.elka.pszt;

import pl.edu.pw.elka.pszt.Arguments.ArgumentType;

import java.util.ArrayList;

/**
 * Created by erxyi on 25.05.2017.
 */
public class Predicate {
    PredicateType type;
    String[] argumentValues;


    public Predicate(PredicateType type) {
        this.type = type;
        this.argumentValues = new String[type.arguments.size()];
    }

    public void setArgumentValue(ArgumentType at, String value)
    {
        int typeLocation = type.arguments.indexOf(at);
        if(typeLocation == -1)
            throw new RuntimeException("Type not found in predicate!");

        argumentValues[typeLocation] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name);
        sb.append('(');
        for (int i = 0; i < type.arguments.size(); i++) {
            String s = argumentValues[i];
            if(s==null)
                sb.append(type.arguments.get(i).toString());
            else
                sb.append(s);
            if(i != type.arguments.size()-1)
                sb.append(',');
        }
        sb.append(')');
        return sb.toString();
    }
}
