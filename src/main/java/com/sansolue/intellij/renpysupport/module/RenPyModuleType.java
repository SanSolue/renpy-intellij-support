package com.sansolue.intellij.renpysupport.module;

import com.intellij.openapi.module.ModuleType;
import com.sansolue.intellij.renpysupport.RenPyIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RenPyModuleType extends ModuleType<RenPyModuleBuilder> {

    public static final RenPyModuleType INSTANCE = new RenPyModuleType();


    public RenPyModuleType() {
        super("RENPY_MODULE");
    }

    @NotNull
    @Override
    public RenPyModuleBuilder createModuleBuilder() {
        return new RenPyModuleBuilder();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @NotNull
    @Override
    public String getName() {
        return "RenPy";
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getDescription() {
        return "RenPy module";
    }

    @NotNull
    @Override
    public Icon getNodeIcon(boolean isOpened) {
        return RenPyIcons.RENPY_ICON;
    }
}

