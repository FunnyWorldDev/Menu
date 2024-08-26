package org.oftx.menu;

import org.bukkit.Bukkit;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.util.FormImage;

import java.util.List;
import java.util.function.Consumer;

import static org.bukkit.Bukkit.getLogger;

public class FormBuilder {

    public static Form buildSimpleForm(String title, String content, List<ButtonInfo> buttonsInfo, Consumer<SimpleFormResponse> resultHandler) {
        if (title == null || title.isEmpty()) {
            title = "";
        }

        if (content == null || content.isEmpty()) {
            content = "";
        }

        SimpleForm.Builder formBuilder = SimpleForm.builder().title(title).content(content);
        for (ButtonInfo buttonInfo : buttonsInfo) {
            String label = buttonInfo.getLabel();
            String imgPath = buttonInfo.getImgPath();
            if (imgPath == null || imgPath.isEmpty()) {
                formBuilder.button(label);
            } else {
//                getLogger().info("[imgPath] " + imgPath);
                formBuilder.button(label, FormImage.Type.PATH, imgPath);
            }
        }

        try {
            formBuilder.validResultHandler(resultHandler);
            return formBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().warning("以上错误在处理表单结果时发生(validResultHandler)");
            return null;
        }
    }
}
