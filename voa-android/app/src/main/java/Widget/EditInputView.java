package Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.voa.R;

/**
 * <p>Title: EditInputView</p>
 * <p>Description: 带头部输入框 </p>
 * <p>Copyright: </p>
 * <p>Company: </p>
 */

public class EditInputView extends RelativeLayout {

    TextView tvHead;
    EditText etInput;

    public EditInputView(Context context) {
        super(context);
        init(context, null);
    }

    public EditInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context   上下文
     * @param attrs     资源
     */
    private void init(Context context, AttributeSet attrs) {
        // 初始化对象
        initView(context);
        // 获取资源对象
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditInputView);
        // 初始化输入框
        initEdit(context, typedArray);

        // 初始化头部文字
        CharSequence headText = typedArray.getText(R.styleable.EditInputView_headerText);
        if (TextUtils.isEmpty(headText)) {
            // 头部为空时
            tvHead.setVisibility(GONE);
        } else {
            // 头部不为空时
            tvHead.setVisibility(VISIBLE);
            initHeaderText(context, typedArray, headText);
        }

        // 回收资源对象
        typedArray.recycle();
    }

    /**
     * 初始化视图
     *
     * @param context 上下文
     */
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.common_edit_input_view, this);
        tvHead = findViewById(R.id.tv_edit_head);
        etInput = findViewById(R.id.et_edit_input);
    }

    /**
     * 初始化输入框
     *
     * @param context       上下文
     * @param typedArray    资源对象
     */
    private void initEdit(Context context, TypedArray typedArray) {
        // 初始内容
        CharSequence editText = typedArray.getText(R.styleable.EditInputView_text);
        if (!TextUtils.isEmpty(editText)) {
            etInput.setText(editText);
        }
        // 字体大小
        setViewTextSize(etInput, R.styleable.EditInputView_textSize, typedArray);
        // 字体颜色
        setViewTextColor(context, etInput, R.styleable.EditInputView_textColor, typedArray);
        // 设置间距
        setEditPadding(typedArray);
        // 设置边距
        setEditMargin(typedArray);
        // 输入类型限制
        setLimitInputType(typedArray);
        // 输入长度限制
        setLimitInputLen(typedArray);
        // 输入限制：可输入性
        setInputBoolean(typedArray);
        // 输入字体排列位置
        setInputGravity(typedArray);

        initEditHint(context, typedArray);
    }

    /**
     * 设置字体大小
     *
     * @param view          被设置对象
     * @param attrId        属性Id
     * @param typedArray    资源对象
     */
    private void setViewTextSize(TextView view, int attrId, TypedArray typedArray) {
        float size = typedArray.getDimension(attrId, 14 * view.getPaint().density);
        view.getPaint().setTextSize(size);
    }

    /**
     * 设置字体风格
     *
     * @param view          被设置对象
     * @param attrId        属性Id
     * @param typedArray    资源对象
     */
    private void setViewTextStyle(TextView view, int attrId, TypedArray typedArray) {
        int style = typedArray.getInt(attrId, Typeface.NORMAL);
        view.setTypeface(Typeface.defaultFromStyle(style));
    }

    /**
     * 设置字体颜色
     *
     * @param context       上下文
     * @param view          被设置对象
     * @param attrId        属性Id
     * @param typedArray    资源对象
     */
    private void setViewTextColor(Context context, TextView view, int attrId, TypedArray typedArray) {
        int color = typedArray.getColor(attrId,
                ContextCompat.getColor(context, R.color.c_2B303C));
        view.setTextColor(color);
    }

    /**
     * 设置输入框间距
     *
     * @param typedArray 资源对象
     */
    private void setEditPadding(TypedArray typedArray) {
        // 开始间距
        int paddingStart = (int)typedArray.getDimension(R.styleable.EditInputView_inputPaddingStart, 0);
        // 结束间距
        int paddingEnd = (int)typedArray.getDimension(R.styleable.EditInputView_inputPaddingEnd, 0);
        // 顶部间距
        int paddingTop = (int)typedArray.getDimension(R.styleable.EditInputView_inputPaddingTop, 0);
        // 底部间距
        int paddingBottom = (int)typedArray.getDimension(R.styleable.EditInputView_inputPaddingBottom, 0);
        etInput.setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom);
    }

    /**
     * 设置输入框边距
     *
     * @param typedArray 资源对象
     */
    private void setEditMargin(TypedArray typedArray) {
        // 开始边距
        int marginStart = (int)typedArray.getDimension(R.styleable.EditInputView_inputMarginStart, 0);
        // 结束边距
        int marginEnd = (int)typedArray.getDimension(R.styleable.EditInputView_inputMarginEnd, 0);
        // 顶部边距
        int marginTop = (int)typedArray.getDimension(R.styleable.EditInputView_inputMarginTop, 0);
        // 底部边距
        int marginBottom = (int)typedArray.getDimension(R.styleable.EditInputView_inputMarginBottom, 0);
        LayoutParams layoutParams = (LayoutParams)etInput.getLayoutParams();
        layoutParams.setMargins(marginStart, marginTop, marginEnd, marginBottom);
        etInput.setLayoutParams(layoutParams);
    }

    /**
     * 设置输入类型限制
     *
     * @param typedArray    资源对象
     */
    private void setLimitInputType(TypedArray typedArray) {
        etInput.setInputType(typedArray.getInt(R.styleable.EditInputView_android_inputType, EditorInfo.TYPE_NULL));
    }

    /**
     * 设置输入长度限制
     *
     * @param typedArray    资源对象
     */
    private void setLimitInputLen(TypedArray typedArray) {
        int len = typedArray.getInteger(R.styleable.EditInputView_maxLength, 0);
        if (len > 0) {
            setMaxLength(len);
        }
    }

    /**
     * 输入限制：可输入性
     *
     * @param typedArray    资源对象
     */
    private void setInputBoolean(TypedArray typedArray) {
        etInput.setEnabled(typedArray.getBoolean(R.styleable.EditInputView_android_enabled, true));
    }

    /**
     * 输入字体排列位置
     *
     * @param typedArray    资源对象
     */
    private void setInputGravity(TypedArray typedArray) {
        etInput.setGravity(typedArray.getInt(R.styleable.EditInputView_android_gravity,
                Gravity.END|Gravity.CENTER_VERTICAL));
    }

    /**
     * 初始化输入框提示文字
     *
     * @param context       上上下文
     * @param typedArray    资源对象
     */
    private void initEditHint(Context context, TypedArray typedArray) {
        CharSequence hintText = typedArray.getText(R.styleable.EditInputView_hint);
        if (!TextUtils.isEmpty(hintText)) {
            // 提示文字不为空
            // 提示内容
            etInput.setHint(hintText);
            // 提示文字颜色
            int color = typedArray.getColor(R.styleable.EditInputView_hintColor,
                    ContextCompat.getColor(context, R.color.c_D2D0DC));
            etInput.setHintTextColor(color);
        }
    }

    /**
     * 初始化头部文字
     *
     * @param context       上下文
     * @param typedArray    资源对象
     * @param text          头部文字
     */
    private void initHeaderText(Context context, TypedArray typedArray, CharSequence text) {
        // 头部字体风格
        setViewTextStyle(tvHead, R.styleable.EditInputView_headerTextStyle, typedArray);
        // 头部字体颜色
        setViewTextColor(context, tvHead, R.styleable.EditInputView_headerTextColor, typedArray);
        // 头部字体大小
        setViewTextSize(tvHead, R.styleable.EditInputView_headerTextSize, typedArray);
        // 头部开始间距
        int paddingStart = (int)typedArray.getDimension(R.styleable.EditInputView_headerPaddingStart, 0);
        // 头部结束间距
        int paddingEnd = (int)typedArray.getDimension(R.styleable.EditInputView_headerPaddingEnd, 0);
        // 头部顶部间距
        int paddingTop = (int)typedArray.getDimension(R.styleable.EditInputView_headerPaddingTop, 0);
        // 头部底部间距
        int paddingBottom = (int)typedArray.getDimension(R.styleable.EditInputView_headerPaddingBottom, 0);

        tvHead.setText(text);
        tvHead.setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom);
    }

    /**
     * 设置头部内容
     *
     * @param text 被设置内容
     */
    public void setHeadText(CharSequence text) {
        if (tvHead != null) {
            tvHead.setText(text);
        }
    }

    /**
     * 获取内容
     *
     * @return 内容
     */
    public CharSequence getText() {
        if (etInput == null) {
            return null;
        } else {
            return etInput.getText();
        }
    }

    /**
     * 设置内容
     *
     * @param text 被设置内容
     */
    public void setText(CharSequence text) {
        if (etInput != null) {
            etInput.setText(text);
        }
    }

    /**
     * 设置内容颜色
     *
     * @param colorId 颜色资源Id
     */
    public void setTextColor(@ColorRes int colorId) {
        if (etInput != null) {
            etInput.setTextColor(ContextCompat.getColor(getContext(), colorId));
        }
    }

    /**
     * 设置最大输入限制
     *
     * @param len   限制值
     */
    public void setMaxLength(int len) {
        etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(len)});
    }

    public TextView getHeadTextView() {
        return tvHead;
    }

    public EditText getInputEditView() {
        return etInput;
    }
}
