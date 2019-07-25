package com.reactlibrary.common;

import com.facebook.react.bridge.WritableMap;
import com.reactlibrary.json.RNHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class Utils {
    private RNHelper mHelper;
    private WritableMap mapWrite;

    public void Utils() {
        this.mHelper = new RNHelper();
    }

    public WritableMap getDataInflate(InputStream in) {
        try {
            // Decompress the bytes
            Inflater deCompressor = new Inflater(true);
            InflaterInputStream input = new InflaterInputStream(in, deCompressor);

            BufferedReader b = new BufferedReader(new InputStreamReader(input, "UTF8"));
            String line;
            StringBuilder out = new StringBuilder();
            while ((line = b.readLine()) != null) {
                out.append(line);
            }

            b.close();
            input.close();
            deCompressor.end();

            String response = out.toString();
            String validJson = formatString(response);

            mapWrite = mHelper.jsonToReact(validJson);
            return mapWrite;

        } catch (Exception e) {
            mapWrite.putString("message", e.getMessage());
        }

        return mapWrite;
    }

    private String formatString(String text) {
        text = text.replace("\\n", "");
        text = text.replace("\\", "");
        text = text.replace(": ", ":");

        text = text.replace(":\"\",", ":[dot_mama]");
        text = text.replace(":\"\"]", ":[dot_mama1]");
        text = text.replace(":\"\"}", ":[dot_mama2]");
        text = text.replace("\"\"", "\"");
        text = text.replace(":[dot_mama]", ":\"\",");
        text = text.replace(":[dot_mama1]", ":\"\"]");
        text = text.replace(":[dot_mama2]", ":\"\"}");
        text = text.replace("\"[", "[");
        text = text.replace("\"{", "{");
        text = text.replace("}\"", "}");
        text = text.replace("]\"", "]");
        text = text.replace("\"\"", "null");

        return text;
    }
}
