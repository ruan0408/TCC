package com.intellij.smartcity;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

/**
 * Created by ruan0408 on 9/02/2016.
 */
public class SmartCityToolWindowFactory implements ToolWindowFactory {

    private ToolWindow myToolWindow;

    public SmartCityToolWindowFactory() {

    }

    // Create the tool window content.
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        myToolWindow = toolWindow;
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
//    public SmartCityToolWindowFactory() {
//        hideToolWindowButton.addActionListener(e -> myToolWindow.hide(null));
//        if (refreshToolWindowButton == null) System.out.println("AUEHAUHEUAHUEAHUHAUEHAH");
//        refreshToolWindowButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                SmartCityToolWindowFactory.this.currentDateTime();
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

