package pl.edu.pw.elka.pszt.Arguments;

import java.util.ArrayList;

/**
 * Created by erxyi on 25.05.2017.
 */



public class ArgumentDictionary {
    ArrayList<String> arguments;

    public ArgumentDictionary() {
        arguments = new ArrayList<>();
    }

    public ArgumentType createNewArgument(String name)
    {
        if(arguments.contains(name))
            return new ArgumentType(this, arguments.indexOf(name));
        else
        {
            arguments.add(name);
            return new ArgumentType(this, arguments.indexOf(name));
        }
    }
}
