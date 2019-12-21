package com.sansolue.intellij.renpysupport.module;

import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.xmlb.XmlSerializer;
import com.sansolue.intellij.renpysupport.RenPyIcons;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class RenPySdkType extends SdkType {

    public RenPySdkType() {
        super("renpy");
    }

//    region - SDK Path

    @Nullable
    @Override
    public String suggestHomePath() {
        return System.getenv("RENPY_HOME");
    }

    private boolean isValidSdkHome(VirtualFile home) {
        VirtualFile renpyExe = home.findChild("renpy.exe");
        VirtualFile renpyPy = home.findChild("renpy.py");
        VirtualFile renpySh = home.findChild("renpy.sh");
        VirtualFile libDir = home.findChild("lib");
        VirtualFile renpyDir = home.findChild("renpy");
        return (
                (renpyExe != null && renpyExe.exists() && !renpyExe.isDirectory()) &&
                        (renpyPy != null && renpyPy.exists() && !renpyPy.isDirectory()) &&
                        (renpySh != null && renpySh.exists() && !renpySh.isDirectory()) &&
                        (libDir != null && libDir.exists() && libDir.isDirectory()) &&
                        (renpyDir != null && renpyDir.exists() && renpyDir.isDirectory()));
    }

    @Override
    public boolean isValidSdkHome(String path) {
        VirtualFile home = LocalFileSystem.getInstance().findFileByIoFile(new File(path));
        if (home != null && home.exists()) {
            if (home.isDirectory()) {
                return isValidSdkHome(home);
            } else {
                return isValidSdkHome(home.getParent());
            }
        }
        return false;
    }

//    endregion

    @NotNull
    @Override
    public String suggestSdkName(@Nullable String currentSdkName, String sdkHome) {
        return String.format("RenPy SDK (%s)", getVersionString(sdkHome));
    }

    @Nullable
    @Override
    public AdditionalDataConfigurable createAdditionalDataConfigurable(@NotNull SdkModel sdkModel, @NotNull SdkModificator sdkModificator) {
        return null;
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return "RenPy SDK";
    }

    @Nullable
    @Override
    public SdkAdditionalData loadAdditionalData(@NotNull Element additional) {
        return XmlSerializer.deserialize(additional, RenPySdkData.class);
    }

    @Override
    public void saveAdditionalData(@NotNull SdkAdditionalData additionalData, @NotNull Element additional) {
        if (additionalData instanceof RenPySdkData) {
            XmlSerializer.serializeInto(additionalData, additional);
        }
    }

    @Override
    public Icon getIcon() {
        return RenPyIcons.RENPY_ICON;
    }

    @NotNull
    @Override
    public Icon getIconForAddAction() {
        return getIcon();
    }

    public static SdkTypeId getInstance() {
        return SdkType.findInstance(RenPySdkType.class);
    }

    @Nullable
    @Override
    public String getVersionString(@NotNull String h) {
        VirtualFile home = LocalFileSystem.getInstance().findFileByIoFile(new File(h));
        if (home != null) {
            VirtualFile initFile = home.findFileByRelativePath("renpy/__init__.py");
            if (initFile != null && initFile.exists() && !initFile.isDirectory()) {
                try {
                    BufferedReader r = new BufferedReader(new InputStreamReader(initFile.getInputStream()));
                    Optional<String> line = r.lines().limit(100).filter(l -> l.startsWith("version_tuple = (")).findFirst();
                    if (line.isPresent()) {
                        String ll = line.get().strip().substring(17);
                        ll = ll.substring(0, ll.length() - 2);
                        return Arrays.stream(ll.split(","))
                                .limit(3)
                                .map(String::strip)
                                .collect(Collectors.joining("_"));

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Nullable
    @Override
    public String getVersionString(@NotNull Sdk sdk) {
        if (sdk.getHomePath() != null) return getVersionString(sdk.getHomePath());
        return null;
    }
}
