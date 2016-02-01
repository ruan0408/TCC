package com.themachineplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.io.File;
import java.io.IOException;

/**
 * Created by ruan0408 on 30/01/2016.
 */
public class SmartCityRunner extends AnAction {

    private File simulatorFolder =
            new File("/Users/ruan0408/Documents/faculdade/tcc/Sim-Diasca-2.2.9/mock-simulators/smart-city/src");

    private File output = new File("/Users/ruan0408/Documents/faculdade/tcc/out.txt");
    private String[] cmd = new String[]{"make","smart_city_run"};
    Process simulation = null;

    @Override
    public void actionPerformed(AnActionEvent e) {

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.directory(simulatorFolder);
            processBuilder.redirectOutput(output);
            simulation = processBuilder.start();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    protected boolean isSimulationRunning() {
        return (simulation != null) ? true : false;
    }
}
