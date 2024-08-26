package org.oftx.menu;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.oftx.menu.Menu.configuredItemType;
import static org.oftx.menu.Menu.useItemOpenMenu;

public class ConfigUtils {

    private static File menuConfigFile = null;
    private static File configFile = null;

    public static void setMenuConfigFile(JavaPlugin plugin) {
        menuConfigFile = new File(plugin.getDataFolder(), "menu.yml");
        configFile = new File(plugin.getDataFolder(), "config.yml");
    }

    /*
    配置文件说明
    - - - - - -
    [命令按钮] 点击按钮后执行对应命令
    示例:
      type: command - 声明按钮类型为命令按钮 (必选)
      id: resfuncmenu - 按钮 ID (必选)
      label: 领地功能 - 按钮上面显示的标签 (必选)
      command: res - 按下按钮后执行的命令 (必选)
      runas: player - 以什么身份执行命令 player/console (可选，默认player)
      showarrow: true - 在按钮标签后显示一个箭头（>） (可选，默认true)
      image: textures/ui/icon_recipe_nature - 按钮前面的图片 (可选)
      permission: menu.op - 显示这个按钮需要的权限 (可选)

    [菜单按钮] 点击按钮后进入相应的菜单
    示例:
      type: menu - 声明按钮类型为菜单按钮 (必选)
      id: opfuncmenu - 按钮 ID (必选)
      title: 管理员功能 - 按钮上面显示的标签&子菜单的标题 (必选)
      content: '' - 菜单的内容 (可选，默认为空字符串)
      showarrow: true - 在按钮标签后显示一个箭头（>） (可选，默认true)
      image: textures/ui/permissions_op_crown - 按钮前面的图片 (可选)
      permission: menu.op - 显示这个按钮需要的权限 (可选)
      buttons:
        - type: command
          id: chgamerule
          label: 游戏规则
          command: chgamerule
          runas: player
          showarrow: true
          image: textures/ui/sign
          permission: menu.op - 这个菜单包含的命令按钮 (必选)
     */

    public static void initConfig() {
        if (!configFile.exists()) {
            try {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();

                String yamlContent = "item: CLOCK\n" +
                        "enable-use-item-open-menu: true";

                try (FileWriter writer = new FileWriter(configFile)) {
                    writer.write(yamlContent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!menuConfigFile.exists()) {
            try {
                // 创建文件及其目录
                menuConfigFile.getParentFile().mkdirs();
                menuConfigFile.createNewFile();

                // 定义 YAML 内容
                String yamlContent = "menu:\n" +
                        "  title: 菜单\n" +
                        "  content: |\n" +
                        "    [金币: <money>    ]§7<date&time>§r\n" +
                        "    [  §7·§r 位于 <resOwner> 的领地: <resName>]\n" +
                        "  buttons:\n" +
                        "    - type: command\n" +
                        "      id: resfuncmenu\n" +
                        "      label: 领地功能\n" +
                        "      command: res\n" +
                        "      runas: player\n" +
                        "      showarrow: true\n" +
                        "      image: textures/ui/icon_recipe_nature\n" +
                        "    - type: command\n" +
                        "      id: chdifficulty\n" +
                        "      label: 修改难度\n" +
                        "      command: chdifficulty\n" +
                        "      runas: player\n" +
                        "      showarrow: true\n" +
                        "      image: textures/ui/controller_glyph_color\n" +
                        "    - type: command\n" +
                        "      id: menureload\n" +
                        "      label: 重载菜单配置\n" +
                        "      command: menu reload\n" +
                        "      runas: console\n" +
                        "      showarrow: false\n" +
                        "    - type: menu\n" +
                        "      id: opfuncmenu\n" +
                        "      title: 管理员功能\n" +
                        "      content: ''\n" +
                        "      showarrow: true\n" +
                        "      image: textures/ui/permissions_op_crown\n" +
                        "      permission: menu.op\n" +
                        "      buttons:\n" +
                        "        - type: command\n" +
                        "          id: chgamerule\n" +
                        "          label: 游戏规则\n" +
                        "          command: chgamerule\n" +
                        "          runas: player\n" +
                        "          showarrow: true\n" +
                        "          image: textures/ui/sign\n" +
                        "          permission: menu.op\n" +
                        "        - type: menu\n" +
                        "          id: otherfuncmenu\n" +
                        "          title: 其他功能\n" +
                        "          content: ''\n" +
                        "          showarrow: true\n" +
                        "          buttons: []\n";

                // 将 YAML 内容写入文件
                try (FileWriter writer = new FileWriter(menuConfigFile)) {
                    writer.write(yamlContent);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static FormInfo readConfig() {
        FileConfiguration menuConfig = YamlConfiguration.loadConfiguration(menuConfigFile);
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        configuredItemType = config.getString("item", "CLOCK");
        useItemOpenMenu = config.getBoolean("enable-use-item-open-menu", true);

        // 读取主菜单的基本信息
        String title = menuConfig.getString("menu.title", "菜单");
        String content = menuConfig.getString("menu.content", "");

        // 读取按钮列表
        List<Button> buttons = new ArrayList<>();
        List<Map<?, ?>> buttonConfigs = (List<Map<?, ?>>) menuConfig.getList("menu.buttons");

        if (buttonConfigs != null) {
            for (Map<?, ?> buttonConfig : buttonConfigs) {
                if (buttonConfig instanceof Map) {
                    Map<?, ?> buttonMap = (Map<?, ?>) buttonConfig;
                    String type = (String) buttonMap.get("type");
                    String id = (String) buttonMap.get("id");
                    String label = (String) buttonMap.get("label");
                    String command = (String) buttonMap.get("command");
                    String runas = (String) buttonMap.get("runas");
                    String image = (String) buttonMap.get("image");
                    Boolean showarrow = buttonMap.get("showarrow") != null ? (Boolean) buttonMap.get("showarrow") : true;
                    String permission = (String) buttonMap.get("permission");
                    String menuTitle = (String) buttonMap.get("title");
                    String menuContent = (String) buttonMap.get("content");
                    List<Map<?, ?>> subButtons = (List<Map<?, ?>>) buttonMap.get("buttons");

//                    getLogger().info("[subButtons]" + subButtons);

                    if ("command".equals(type)) {
                        // 创建命令按钮
                        Button button = new Button(type, id, label, command, runas, image, showarrow, permission);
                        buttons.add(button);
                    } else if ("menu".equals(type)) {
                        // 递归读取子菜单按钮
                        List<Button> subButtonList = readSubButtons(subButtons);
                        Button button = new Button(type, id, menuTitle, menuContent, showarrow, image, permission, subButtonList);
                        buttons.add(button);
                    }
                }
            }
        }

        // 创建主菜单表单对象
        return new FormInfo(FormInfo.getMainFormId(), title, content, buttons);
    }

    private static List<Button> readSubButtons(List<Map<?, ?>> buttonConfigs) {
        List<Button> buttons = new ArrayList<>();

        if (buttonConfigs != null) {
            for (Map<?, ?> buttonConfig : buttonConfigs) {
                if (buttonConfig instanceof Map) {
                    Map<?, ?> buttonMap = (Map<?, ?>) buttonConfig;
                    String type = (String) buttonMap.get("type");
                    String id = (String) buttonMap.get("id");
                    String label = (String) buttonMap.get("label");
                    String command = (String) buttonMap.get("command");
                    String runas = (String) buttonMap.get("runas");
                    String image = (String) buttonMap.get("image");
                    Boolean showarrow = buttonMap.get("showarrow") != null ? (Boolean) buttonMap.get("showarrow") : true;
                    String permission = (String) buttonMap.get("permission");
                    String menuTitle = (String) buttonMap.get("title");
                    String menuContent = (String) buttonMap.get("content");
                    List<Map<?, ?>> subButtons = (List<Map<?, ?>>) buttonMap.get("buttons");

//                    getLogger().info("[subButton id]" + image);

                    if ("command".equals(type)) {
                        // 创建命令按钮
                        Button button = new Button(type, id, label, command, runas, image, showarrow, permission);
                        buttons.add(button);
                    } else if ("menu".equals(type)) {
                        // 递归读取子菜单按钮
                        List<Button> subButtonList = readSubButtons(subButtons);
                        Button button = new Button(type, id, menuTitle, menuContent, showarrow, image, permission, subButtonList);
                        buttons.add(button);
                    }
                }
            }
        }

        return buttons;
    }

}
