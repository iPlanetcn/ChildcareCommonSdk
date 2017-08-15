package com.childcare.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 可点击的组合视图,包括图标,标题,指示图标等
 *
 * @author john
 * @since 2017-08-09
 */
public class CellItemView extends LinearLayout {

    public CellItemView(Context context) {
        this(context, null);
    }

    public CellItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_cell_item, this, true);

        ImageView ivIcon = findViewById(R.id.iv_icon);
        TextView tvTitle = findViewById(R.id.tv_title);
        ImageView ivIndicator = findViewById(R.id.iv_indicator);

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CellItemView, 0, 0);
        String title = a.getString(R.styleable.CellItemView_itemTitle);
        Drawable icon = a.getDrawable(R.styleable.CellItemView_itemIcon);
        Drawable indicator = a.getDrawable(R.styleable.CellItemView_itemIndicator);
        a.recycle();

        if (title != null) {
            tvTitle.setText(title);
        }

        if (icon != null) {
            ivIcon.setImageDrawable(icon);
        }

        if (indicator != null) {
            ivIndicator.setImageDrawable(indicator);
        }
    }
}
