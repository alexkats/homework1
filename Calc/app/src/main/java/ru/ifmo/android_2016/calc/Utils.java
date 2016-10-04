package ru.ifmo.android_2016.calc;

import android.widget.TextView;

/**
 * @author Alexey Katsman
 * @since 04.10.16
 */

@SuppressWarnings({"unused", "StaticMethodOnlyUsedInOneClass", "WeakerAccess"})
public class Utils {

    private Utils() {
    }

    public static void normalizeOutput(TextView output) {
        CharSequence text = output.getText();
        int length = text.length();

        if (text.charAt(length - 1) == '.') {
            output.setText(text.subSequence(0, length - 1));
        }
    }

    public static CharSequence deleteTrailingZeros(CharSequence s) {
        int currentLength = s.length();

        for (int i = currentLength - 1; i > -1; i--) {
            if (s.charAt(i) == '0') {
                currentLength--;
                continue;
            }

            break;
        }

        if (s.charAt(currentLength - 1) == '.') {
            currentLength--;
        }

        return s.subSequence(0, currentLength);
    }
}
