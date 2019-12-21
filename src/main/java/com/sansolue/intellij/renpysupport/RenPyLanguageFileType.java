package com.sansolue.intellij.renpysupport;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.sansolue.intellij.renpysupport.module.RenPyModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RenPyLanguageFileType extends LanguageFileType {

    public static final RenPyModuleType INSTANCE = new RenPyModuleType();

    public RenPyLanguageFileType() {
        super(RenPyLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "RenPy";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "RenPy script";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "rpy";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return RenPyIcons.RENPY_ICON;
    }
}
