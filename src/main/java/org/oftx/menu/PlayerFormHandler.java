package org.oftx.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static org.bukkit.Bukkit.getLogger;
import static org.oftx.menu.StringUtils.trContent;

public class PlayerFormHandler {

    public static void PlayerFormHandle(Player player, @Nullable FormInfo formInfo) {

        if (isNull(formInfo)) formInfo = Menu.mainFormInfo;
        String title = formInfo.getFormTitle();
        String content = trContent(formInfo.getFormContent(), player);

        List<ButtonInfo> buttonsInfo = new ArrayList<>();
        List<Button> shownButtons = new ArrayList<>();

        // 给玩家显示的可以看见的按钮
        for (Button button : formInfo.getButtons()) {
            Boolean isPermissionEmpty = button.getPermission() == null || button.getPermission().isEmpty();
            if (isPermissionEmpty || player.hasPermission(button.getPermission())) {
                String type = button.getType();
                Boolean showArrow = button.getShowarrow();
                if ("command".equals(type)) {
                    buttonsInfo.add(new ButtonInfo(button.getLabel() + (showArrow ? " >" : ""), button.getImage()));
                } else if ("menu".equals(type)) {
                    buttonsInfo.add(new ButtonInfo(button.getTitle(), button.getImage()));
                }
                shownButtons.add(button);
            }
        }

        @Nullable List<Button> finalShownButtons = shownButtons;
        Consumer<SimpleFormResponse> resultHandler = response -> {
            int clickedButtonIndex = response.clickedButtonId();
            Button clickedButton = finalShownButtons.get(clickedButtonIndex);
            String buttonCommand = clickedButton.getCommand();
            String buttonRunas = clickedButton.getRunas();
            String buttonType = clickedButton.getType();
            if ("command".equals(buttonType)) {
                if ("player".equals(buttonRunas)) {
                    player.performCommand(buttonCommand);
                } else if ("console".equals(buttonRunas)) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), buttonCommand);
                }
            } else if ("menu".equals(buttonType)) {
                String formId = clickedButton.getId();
                String formTitle = clickedButton.getTitle();
                String formContent = clickedButton.getContent();
                List<Button> buttons = clickedButton.getButtons();
                PlayerFormHandle(player, new FormInfo(formId, formTitle, formContent, buttons));
            }
        };

        Form form = FormBuilder.buildSimpleForm(title, content, buttonsInfo, resultHandler);

        if (form == null) {
            player.sendMessage("表单创建失败，无法发送。");
            getLogger().warning("表单创建失败，无法发送。");
        } else {
            FloodgateApi.getInstance().sendForm(player.getUniqueId(), form);
        }
    }

}
