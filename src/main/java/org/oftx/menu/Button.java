package org.oftx.menu;

import java.util.List;

public class Button {

    private String type; // menu/command
    private String id;
    private String label;
    private String command;
    private String runas;
    private String image;
    private Boolean showarrow;
    private String permission;
    private String title;
    private String content;
    private List<Button> buttons;

    public List<Button> getButtons() {
        return buttons;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getPermission() {
        return permission;
    }

    public Boolean getShowarrow() {
        return showarrow;
    }

    public String getImage() {
        return image;
    }

    public String getRunas() {
        return runas;
    }

    public String getCommand() {
        return command;
    }

    public String getLabel() {
        return label;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    // 构造函数，用于创建命令按钮
    public Button(String type, String id, String label, String command, String runas, String image, Boolean showarrow, String permission) {
        if (!"command".equals(type)) {
            throw new IllegalArgumentException("类型必须为 'command' 才能使用此构造函数。");
        }
        this.type = type;
        this.id = id;
        this.label = label;
        this.command = command;
        this.runas = runas != null ? runas : "player"; // 默认值为 "player"
        this.image = image;
        this.showarrow = showarrow != null ? showarrow : true; // 默认值为 true
        this.permission = permission;
    }

    // 构造函数，用于创建菜单按钮
    public Button(String type, String id, String title, String content, Boolean showarrow, String image, String permission, List<Button> buttons) {
        if (!"menu".equals(type)) {
            throw new IllegalArgumentException("类型必须为 'menu' 才能使用此构造函数。");
        }
        this.type = type;
        this.id = id;
        this.title = title;
        this.content = content != null ? content : ""; // 默认值为空字符串
        this.showarrow = showarrow != null ? showarrow : true; // 默认值为 true
        this.image = image;
        this.permission = permission;
        this.buttons = buttons;
    }


}
