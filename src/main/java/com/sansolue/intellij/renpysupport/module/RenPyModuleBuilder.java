package com.sansolue.intellij.renpysupport.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleBuilderListener;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.util.ArrayUtil;
import com.sansolue.intellij.renpysupport.RenPyIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RenPyModuleBuilder extends ModuleBuilder implements ModuleBuilderListener {

    private Sdk sdk;

    public RenPyModuleBuilder() {
        addListener(this);
    }


    @Nullable
    @Override
    public String getBuilderId() {
        return "renpy";
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @Override
    public String getDescription() {
        return "RenPy module";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getPresentableName() {
        return "RenPy Module";
    }

    @Override
    public String getGroupName() {
        return "RenPy";
    }

    @Override
    public Icon getNodeIcon() {
        return RenPyIcons.RENPY_ICON;
    }

    @Override
    public RenPyModuleType getModuleType() {
        return RenPyModuleType.INSTANCE;
    }

    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return ArrayUtil.append(super.createWizardSteps(wizardContext, modulesProvider), new RenPyModuleWizardStep(this));
    }

    public void setSdk(Sdk sdk) {
        this.sdk = sdk;
    }

    @Override
    public void moduleCreated(@NotNull Module module) {

    }




}
