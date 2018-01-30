package two.isay.com.simpletext.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * 不自动提前换行的TextView
 * Created by cywu4 on 2018/1/26.
 */

public class FillTextView extends TextView {

    //文字显示的宽度
    private int mWidthBoring;
    //原文字
    private String mText;

    public FillTextView(Context context) {
        super(context);
    }

    public FillTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            mWidthBoring = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            mWidthBoring = getWidth() - getPaddingLeft() - getPaddingRight();
        }
    }


    /**
     * 获取文字内容
     *
     * @return 原文字
     */
    @Override
    public CharSequence getText() {
        return mText;
    }


    /**
     * 设置文字
     *
     * @param text 显示的文字
     */
    public void setText(String text) {
        mText = text;
        int length = text == null ? 0 : text.length();
        if (length <= 1) {
            setText(text, BufferType.NORMAL);
            return;
        }
        TextPaint paint = getPaint();
        //估算每行文字个数
        int charWidth = (int) paint.measureText(text.substring(0, 1));
        if (charWidth + 1 > mWidthBoring) {
            setText(text, BufferType.NORMAL);
            return;
        }
        int rowCount = mWidthBoring / charWidth;
        //切割内容文字
        StringBuilder builder = new StringBuilder();
        int start = 0;
        int end = start + rowCount;
        while (true) {
            //不能超出最大长度
            if (end > length) {
                end = length;
            }
            //测量start~end字符的长度
            if (paint.measureText(text.substring(start, end)) > mWidthBoring) {
                while (paint.measureText(text.substring(start, --end)) > mWidthBoring) {
                    continue;
                }
            } else {
                if (end == length) {
                    builder.append(text.substring(start, end));//结束所有循环
                    break;
                }
                while (paint.measureText(text.substring(start, ++end)) < mWidthBoring) {
                    if (end == length) {
                        ++end;
                        break;//结束本次内循环
                    }
                }
                --end;//超出宽度前的一个
            }
            builder.append(text.substring(start, end));
            if (end < length) {
                builder.append("\n");
            } else if (end >= length) {
                break;
            }
            //重置起始位
            rowCount = end - start;
            start = end;
            end = start + rowCount;
        }
        setText(builder.toString(), BufferType.NORMAL);
    }

}
