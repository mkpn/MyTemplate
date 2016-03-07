package com.template.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.ResponseBody;

/**
 * Created by makoto on 2016/03/08.
 *
 * JSONのストリングを渡すと、Gsonオブジェクト生成用のコードを生成するよ
 * TODOでGetterやSetterを作成するコメントが生成されるから、Alt + Insertで作ってね
 * 完璧な生成はできないんやで。。。
 * JSONArrayの中身が複数の肩を持つJSONだったりすると上手くいかないよ
 */
public class CreateGsonDefinitionHelper {

    public static void createDefinition(ResponseBody responseBody) throws IOException {
        System.out.println("=====GSON Definition================================\n\n");

        StringBuilder stringBuilder = buildGsonDefinitionString(responseBody.string(), new StringBuilder(), 0);

        stringBuilder.append("\n // TODO MUST create Getter & Setter\n");
        System.out.println(stringBuilder.toString());

        System.out.println("\n\n====================================================");
    }


    private static StringBuilder buildGsonDefinitionString(String s, StringBuilder builder, int indent) {
        try {
            JSONObject jObject = new JSONObject(s);
            Iterator iter = jObject.keys();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                String value = jObject.getString(key);
                // シンプルな key と value
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    builder.append("@Expose\n")
                            .append("private ")
                            .append("String ")
                            .append(key)
                            .append(";\n\n");

                } else if (value.startsWith("{") && value.endsWith("}")) {
                    // valueがJson　のケース
                    builder.append("@Expose\n")
                            .append("private ")
                            .append(makeFirstCharacterUpperCase(key) + " ")
                            .append(key)
                            .append(";\n\n");

                    builder.append("public class ")
                            .append(makeFirstCharacterUpperCase(key))
                            .append(" { \n");
                    buildGsonDefinitionString(value, builder, indent + 4);

                    builder.append("\n // TODO MUST create Getter & Setter\n")
                            .append("\n }\n\n");
                } else if (value.startsWith("[") && value.endsWith("]")) {
                    builder.append("@Expose\n")
                            .append("private ")
                            .append("List<")
                            .append(makeFirstCharacterUpperCase(key))
                            .append("> ")
                            .append(key)
                            .append(";\n\n // TODO 単数形になってるか確認すること\n");

                    builder.append("public class ")
                            .append(makeFirstCharacterUpperCase(key))
                            .append(" { \n");

                    // valueがjsonの配列　のケース
                    int nextIndent = indent + 4;

                    JSONArray jsonArray = new JSONArray(value);
                    buildGsonDefinitionString(jsonArray.get(0).toString(), builder, nextIndent); // 配列の中身が全部同じJSONなら動く

                    builder.append("\n // TODO MUST create Getter & Setter\n")
                            .append("\n }\n\n");
                } else if (isNum(value)) {
                    builder.append("@Expose\n")
                            .append("private ")
                            .append("int ")
                            .append(key)
                            .append(";\n\n");
                } else {
                    builder.append("@Expose\n")
                            .append("private ")
                            .append("String ")
                            .append(key)
                            .append(";\n\n");
                }
            }
            return builder;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return builder;
    }


    static boolean isNum(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static String makeFirstCharacterUpperCase(String string){
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

}
