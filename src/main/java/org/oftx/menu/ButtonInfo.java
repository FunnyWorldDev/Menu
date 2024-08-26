package org.oftx.menu;

public class ButtonInfo {
    private String label;
    private String imgPath;

    public ButtonInfo(String label, String imgPath) {
        this.label = label;
        this.imgPath = imgPath;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
