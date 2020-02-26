package com.reactlibrary.common;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.reactlibrary.json.RNHelper;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.BufferedInputStream;
import java.io.InputStream;
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
            Inflater deCompressor = new Inflater(true);
            InflaterInputStream input = new InflaterInputStream(in, deCompressor);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] buffer = new byte[8192];
            int length;
            BufferedInputStream b = new BufferedInputStream(input);

            while ((length = b.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            String response = new String(out.toByteArray(), "UTF-8");
            String validJson = formatString(response);

            mapWrite = mHelper.jsonToReact(validJson);
            return mapWrite;

        } catch (Exception e) {
            e.printStackTrace();

            mapWrite = Arguments.createMap();
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
