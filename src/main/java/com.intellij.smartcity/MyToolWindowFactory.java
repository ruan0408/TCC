package com.intellij.smartcity;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

/**
 * Created by ruan0408 on 9/02/2016.
 */

//TODO Hide tool window if the Smart City Framework is not part of the project.
public class MyToolWindowFactory implements ToolWindowFactory, Condition {

    private ToolWindow myToolWindow;
    private ActionToolbar toolbar;
    private SimpleToolWindowPanel toolWindowPanel;

    public MyToolWindowFactory() {
        toolWindowPanel = new SimpleToolWindowPanel(true, true);
    }

    // Create the tool window content.
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        toolbar = createToolbar(project);
        myToolWindow.getComponent().add(toolWindowPanel);
        toolWindowPanel.setToolbar(toolbar.getComponent());
    }

    @Override
    public boolean value(Object o) {
        return true;
    }

    private ActionToolbar createToolbar(final Project project) {
        DefaultActionGroup groupFromConfig = (DefaultActionGroup) ActionManager.getInstance().getAction("SmartCity.Toolbar");
        DefaultActionGroup group = new DefaultActionGroup(groupFromConfig); // copy required (otherwise config action group gets modified)

        //ActionManager.getInstance().createButtonToolbar()
//        DefaultActionGroup filterGroup = new DefaultActionGroup();
//        Iterable<ChangesFilter> filters = changesFilters.getFilters();
//        for (ChangesFilter filter : filters) {
//            filterGroup.add(filter.getAction(project));
//        }
//        filterGroup.add(new Separator());
//        group.add(filterGroup, Constraints.FIRST);
//
//        changesFilters.addObserver(new Observer() {
//            @Override
//            public void update(Observable observable, Object o) {
//                reloadChanges(project, true);
//            }
//        });

        return ActionManager.getInstance().createActionToolbar("SmartCity.Toolbar", group, true);
    }
}

//    private JButton refreshToolWindowButton;
//    private JButton hideToolWindowButton;
//    private JLabel currentDate;
//    private JLabel currentTime;
//    private JLabel timeZone;
//    private JPanel myToolWindowContent;
//    private ToolWindow myToolWindow;
//
//
//    public MyToolWindowFactory() {
//        hideToolWindowButton.addActionListener(e -> myToolWindow.hide(null));
//        if (refreshToolWindowButton == null) System.out.println("AUEHAUHEUAHUEAHUHAUEHAH");
//        refreshToolWindowButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                MyToolWindowFactory.this.currentDateTime();
//            }
//        });
//    }
//
//    // Create the tool window content.
//    @Override
//    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
//        myToolWindow = toolWindow;
//        this.currentDateTime();
//        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
//        Content content = contentFactory.createContent(myToolWindowContent, "", false);
//        toolWindow.getContentManager().addContent(content);
//
//    }
//
//    public void currentDateTime() {
//        // Get current date and time
//        Calendar instance = Calendar.getInstance();
//        currentDate.setText(String.valueOf(instance.get(Calendar.DAY_OF_MONTH)) + "/"
//                + String.valueOf(instance.get(Calendar.MONTH) + 1) + "/" + String.valueOf(instance.get(Calendar.YEAR)));
//        URL url = getClass().getResource("calendarIcon.png");
//        if (url == null) System.out.println("url is null");
//        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("calendarIcon.png"));
//        if (icon == null) System.out.println("icon is null");
//        currentDate.setIcon(icon);
//        int min = instance.get(Calendar.MINUTE);
//        String strMin;
//        if (min < 10) {
//            strMin = "0" + String.valueOf(min);
//        } else {
//            strMin = String.valueOf(min);
//        }
//        currentTime.setText(instance.get(Calendar.HOUR_OF_DAY) + ":" + strMin);
//        currentTime.setIcon(new ImageIcon(getClass().getClassLoader().getResource("timeIcon.png")));
//        // Get time zone
//        long gmt_Offset = instance.get(Calendar.ZONE_OFFSET); // offset from GMT in milliseconds
//        String str_gmt_Offset = String.valueOf(gmt_Offset / 3600000);
//        str_gmt_Offset = (gmt_Offset > 0) ? "GMT + " + str_gmt_Offset : "GMT - " + str_gmt_Offset;
//        timeZone.setText(str_gmt_Offset);
//        timeZone.setIcon(new ImageIcon(getClass().getClassLoader().getResource("timeZoneIcon.png")));
//
//
//    }

