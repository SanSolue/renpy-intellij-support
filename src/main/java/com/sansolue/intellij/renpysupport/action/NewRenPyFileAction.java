package com.sansolue.intellij.renpysupport.action;

import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.actions.CreateFromTemplateAction;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.sansolue.intellij.renpysupport.RenPyIcons;
import com.sansolue.intellij.renpysupport.module.RenPyModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NewRenPyFileAction extends CreateFromTemplateAction<PsiFile> {

    public NewRenPyFileAction() {
        super("RenPy Script", null, RenPyIcons.RENPY_ICON);
    }

    @Override
    protected boolean isAvailable(DataContext dataContext) {
        final Module module = LangDataKeys.MODULE.getData(dataContext);
        final ModuleType moduleType = module == null ? null : ModuleType.get(module);
        final boolean isRenPyModule = moduleType instanceof RenPyModuleType;
        return super.isAvailable(dataContext) && isRenPyModule;
    }

    @Nullable
    @Override
    protected PsiFile createFile(String name, String templateName, PsiDirectory dir) {
        return null;
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {

    }

    @Override
    protected String getActionName(PsiDirectory directory, @NotNull String newName, String templateName) {
        return "Creating File " + newName;
    }
}
