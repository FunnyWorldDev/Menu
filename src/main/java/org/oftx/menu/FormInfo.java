package org.oftx.menu;

import java.util.List;

public class FormInfo {

    private String formId;
    private String formTitle;
    private String formContent;
    private List<Button> buttons;

    private static String mainFormId = "mainForm" + Math.random();

    public FormInfo(String formId, String formTitle, String formContent, List<Button> buttons) {
        this.formId = formId;
        this.formTitle = formTitle;
        this.formContent = formContent;
        this.buttons = buttons;
    }

    public String getFormId() {
        return formId;
    }

    public String getFormTitle() {
        return formTitle;
    }

    public String getFormContent() {
        return formContent;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public static String getMainFormId() {
        return mainFormId;
    }

}
