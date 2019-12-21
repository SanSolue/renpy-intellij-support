package com.sansolue.intellij.renpysupport.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;

import javax.swing.*;

public class RenPyModuleWizardStep extends ModuleWizardStep {

    private RenPySdkPanel root;
    private RenPyModuleBuilder builder;

    public RenPyModuleWizardStep(RenPyModuleBuilder builder) {
        this.builder = builder;
        this.root = new RenPySdkPanel();
    }

    @Override
    public JComponent getComponent() {
        return root;
    }

    @Override
    public void updateDataModel() {
        builder.setSdk(root.getSdk());
    }
}
