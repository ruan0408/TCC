package com.intellij.plugin;

import com.intellij.framework.FrameworkTypeEx;
import com.intellij.framework.addSupport.FrameworkSupportInModuleConfigurable;
import com.intellij.framework.addSupport.FrameworkSupportInModuleProvider;
import com.intellij.icons.AllIcons;
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableModelsProvider;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Condition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Anna Bulenkova
 */
public class FrameworkConfigurator extends FrameworkTypeEx implements Condition<Project>{
    public static final String FRAMEWORK_ID = "SmartCity";
    private static final String supportFile = "/resources/smartCitySupport.txt";

    public FrameworkConfigurator() {
        super(FRAMEWORK_ID);
    }

    public static boolean isFrameworkAvailable(Project project) {
        return new File(getPathToSupportFile(project)).isFile();
    }

    @Override
    public boolean value(Project project) {
        return isFrameworkAvailable(project);
    }

    @NotNull
    @Override
    public FrameworkSupportInModuleProvider createProvider() {
        return  new FrameworkSupportInModuleProvider() {
            @NotNull
            @Override
            public FrameworkTypeEx getFrameworkType() {
                return FrameworkConfigurator.this;
            }

            @NotNull
            @Override
            public FrameworkSupportInModuleConfigurable createConfigurable(@NotNull FrameworkSupportModel model) {
                return new FrameworkSupportInModuleConfigurable() {
                    @Nullable
                    @Override
                    public JComponent createComponent() {
                        return new JCheckBox("Extra Option");
                    }

                    @Override
                    public void addSupport(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ModifiableModelsProvider provider) {
                        //do what you want here: setup a library, generate a specific file, etc
                        generateSmartCitySupportFile(module.getProject());

                        //TODO Add here (?) the support for the apache spark jar.
//                        final FacetManager facetManager = FacetManager.getInstance(module);
//                        ModifiableFacetModel facetModel = facetManager.createModifiableModel();
//
//                        // UI Stuff with your JComponent, Settings updates...
//
//                        Facet facet = FacetManager.getInstance(model.getModule()).addFacet(MyFacetType.getInstance(), "SmartCity", null);
//                        facetModel.addFacet(facet);
//                        facetModel.commit();

                    }
                };
            }

            @Override
            public boolean isEnabledForModuleType(@NotNull ModuleType type) {
                return true;
            }
        };
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return "Smart City Framework";
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return AllIcons.Providers.Apache;
    }

    private void generateSmartCitySupportFile(Project project) {
        File genFile = new File(getPathToSupportFile(project));
        try {
            genFile.getParentFile().mkdirs();
            genFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getPathToSupportFile(Project project) {
        return project.getBaseDir().getPath()+supportFile;
    }
}