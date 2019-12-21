package com.sansolue.intellij.renpysupport.module;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.AccessToken;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.fileChooser.PathChooserDialog;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.labels.ActionLink;
import com.intellij.util.download.DownloadableFileDescription;
import com.intellij.util.download.DownloadableFileService;
import com.intellij.util.download.FileDownloader;
import com.intellij.util.io.ZipUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RenPySdkPanel extends JPanel {

    public static final String LAST_USED_RENPY_HOME = "LAST_USED_RENPY_HOME";

    private RenPySdkComboBox comboBox;
    private ActionLink sdkDownloadLink;


    public RenPySdkPanel() {
        comboBox = new RenPySdkComboBox();
        sdkDownloadLink = new ActionLink("Download and Install RenPy SDK", new AnAction() {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                FileChooserDescriptor descriptor = new FileChooserDescriptor(false, true, false, false, false, false);
                PathChooserDialog pathChooser = FileChooserFactory.getInstance()
                        .createPathChooser(descriptor, null, RenPySdkPanel.this);
                pathChooser.choose(VfsUtil.getUserHomeDir(), new FileChooser.FileChooserConsumer() {
                    @Override
                    public void cancelled() {
                    }

                    @Override
                    public void consume(List<VirtualFile> virtualFiles) {
                        if (virtualFiles.size() == 1) {
                            VirtualFile dir = virtualFiles.get(0);
                            String dirName = dir.getName();
                            AccessToken accessToken = ApplicationManager.getApplication().acquireWriteActionLock(RenPySdkPanel.class);
                            try {
                                if (!dirName.toLowerCase().contains("renpy")) {
                                    try {
                                        dir = dir.createChildDirectory(this, "RenPy");
                                    } catch (IOException e) {//
                                    }
                                }
                                DownloadableFileService fileService = DownloadableFileService.getInstance();
                                DownloadableFileDescription fileDescription = fileService.createFileDescription("https://www.renpy.org/dl/7.3.5/renpy-7.3.5-sdk.zip", "renpy-7.3.5-sdk.zip");
                                FileDownloader downloader = fileService.createDownloader(Arrays.asList(fileDescription), null, RenPySdkPanel.this, "renpy-7.3.5-sdk.zip");
                                VirtualFile[] files = downloader.toDirectory(dir.getPath()).download();
                                if (files != null && files.length == 1) {
                                    try {
                                        ZipUtil.extract(VfsUtil.virtualToIoFile(files[0]), VfsUtil.virtualToIoFile(dir), null);
                                        PropertiesComponent.getInstance().setValue(LAST_USED_RENPY_HOME, dir.getPath());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        dir.refresh(false, true);
                                    }
                                }
                            } finally {
                                accessToken.finish();
                            }
                        }
                    }
                });
            }
        });
        add(comboBox);
        add(sdkDownloadLink, BorderLayout.PAGE_END);
    }

    public Sdk getSdk() {
        return comboBox.getSelectedSdk();
    }

    public void setSdk(Sdk sdk) {
        comboBox.getComboBox().setSelectedItem(sdk);
    }
}
