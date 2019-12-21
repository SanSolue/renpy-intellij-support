package com.sansolue.intellij.renpysupport;

import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;

public class RenPyLanguage extends Language {

    public static final RenPyLanguage INSTANCE = new RenPyLanguage();

    public RenPyLanguage() {
        super("RenPy", "text/renpy");
    }
}
