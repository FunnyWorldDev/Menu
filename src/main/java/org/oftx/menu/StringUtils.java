package org.oftx.menu;

import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.oftx.menu.EconomyManager.getMoney;

public class StringUtils {

    public static String trContent(String str, Player player) {

        Map<String, String> variables = new HashMap<>();

        if (str == null) return null;
        if (str.isEmpty()) return "";

        if (str.contains("<playerName>")) {
            variables.put("<playerName>", player.getName());
        }

        if (str.contains("<date&time>")) {
            variables.put("<date&time>", getCurrentDateAndTime());
        }

        if (str.contains("<resName>")) {
            String resName = null;
            if (DependenciesChecker.isResidenceLoaded()) {
                ClaimedResidence res = ResidenceApi.getResidenceManager().getByLoc(player.getLocation());
                if (res != null) resName = res.getName();
            }
            variables.put("<resName>", resName);
        }

        if (str.contains("<resOwner>")) {
            String resOwner = null;
            if (DependenciesChecker.isResidenceLoaded()) {
                ClaimedResidence res = ResidenceApi.getResidenceManager().getByLoc(player.getLocation());
                if (res != null) resOwner = res.getOwner();
            }
            variables.put("<resOwner>", resOwner);
        }

        if (str.contains("<money>")) {
            Double money = null;
            if (DependenciesChecker.isEconomyPluginLoaded()) {
                money = getMoney(player);
            }
            variables.put("<money>", String.format("%.2f", money));
        }

        str = replaceVariables(str, variables);

        return str;
    }

    public static String getCurrentDateAndTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }

    public static String getCurrentDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }

    public static String getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }

    public static String replaceVariables(String input, Map<String, String> variables) {
        // 匹配并处理所有的方括号内容（包括换行符）
        Pattern pattern = Pattern.compile("\\[(.*?)\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String bracketContent = matcher.group(1);
            String processedContent = bracketContent;
            boolean containsNull = false;

            // 检查方括号内的所有变量
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                String placeholder = entry.getKey();
                String value = entry.getValue();

                if (bracketContent.contains(placeholder)) {
                    if (value == null) {
                        containsNull = true;
                        break;
                    } else {
                        processedContent = processedContent.replace(placeholder, value);
                    }
                }
            }

            // 如果当前方括号内存在为 null 的变量，删除整个方括号内容
            if (containsNull) {
                matcher.appendReplacement(result, "");
            } else {
                matcher.appendReplacement(result, processedContent);
            }
        }
        matcher.appendTail(result);

        // 最后处理不在方括号内的变量替换
        String finalResult = result.toString();
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            String placeholder = entry.getKey();
            String value = entry.getValue();

            if (value != null) {
                finalResult = finalResult.replace(placeholder, value);
            } else {
                finalResult = finalResult.replace(placeholder, "null");
            }
        }

        // 返回最终结果
        return finalResult.trim();
    }
}
