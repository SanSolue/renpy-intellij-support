package com.sansolue.intellij.renpysupport.module;

import com.intellij.ide.util.projectWizard.WizardInputField;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;

public class RenPyWizardInputField extends WizardInputField<RenPySdkComboBox> {

    private RenPySdkComboBox component = new RenPySdkComboBox();

    public RenPyWizardInputField() {
        super("RENPY_SDK", findMostRecentSdkPath());
    }

    @Override
    public String getLabel() {
        return "RenPy SDK";
    }

    private static String findMostRecentSdkPath() {
        Sdk sdk = ProjectJdkTable.getInstance().findMostRecentSdkOfType(RenPySdkType.getInstance());
        return sdk != null ? sdk.getName() : null;
    }

    @Override
    public RenPySdkComboBox getComponent() {
        return component;
    }

    @Override
    public String getValue() {
        Sdk sdk = component.getSelectedSdk();
        return sdk == null? "":sdk.getHomePath();
    }
}
