package two.isay.com.simpletext.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;


/**
 * Created by cywu4 on 2017/10/27.
 * <p>
 * 解决TextView包含英文数字提前换行问题（有较强的针对性）
 */

public class FillTextView extends TextView {

    private int mLayoutWidth = 0;
    private static int screenWidth = 0;

    public FillTextView(Context context) {
        super(context);
    }

    public FillTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            screenWidth = dm.widthPixels;
        }
    }


    public void setFillText(CharSequence text) {
        Paint paint = getPaint();
        if (TextUtils.isEmpty(text) || paint == null || getLayoutWidth() == 0) {
            setText(text);
            return;
        }
        String str = text.toString();
        String s1 = "";
        String s2 = "";
        if (paint.measureText(str) > mLayoutWidth) {
            for (int i = 15, length = str.length(); i < length; i++) {
                if (i <= length) {
                    if (paint.measureText(str.substring(0, i)) > mLayoutWidth) {
                        s1 = str.substring(0, i - 1);
                        s2 = str.replace(s1, "");
                        break;
                    }
                }
            }
        }
        if (!TextUtils.isEmpty(s1)) {
            if (!TextUtils.isEmpty(s2)) {
                str = s1 + "\n" + s2;
            } else {
                str = s1;
            }
        }
        setText(str);
    }


    private int getLayoutWidth() {
        if (mLayoutWidth == 0) {
            mLayoutWidth = screenWidth - getPaddingLeft() - getPaddingRight();
        }
        if (mLayoutWidth < 0) {
            mLayoutWidth = 0;
        }
        return mLayoutWidth;
    }


}
