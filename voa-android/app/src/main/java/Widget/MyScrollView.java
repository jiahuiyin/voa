package Widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView
{
    private View convertView;
    private Rect originalRect=new Rect();
    private int startY;

    public MyScrollView(Context context)
    {
        super(context);
    }
    public MyScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        //获取子视图
        convertView = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        //记录原来的位置
        originalRect.set(l,t,r,b);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                //记录第一次的手指触摸位置
                startY = (int) ev.getY();
            }
            break;
            case MotionEvent.ACTION_MOVE:
            {
                //记录拖动时的手指触摸位置
                int offsetY = ((int) ev.getY()) - startY;
                //让子视图跟随手指拖动
                convertView.layout(originalRect.left,originalRect.top+(int)(offsetY *0.5f)
                        ,originalRect.right,originalRect.bottom+(int)(offsetY *0.5f));
            }
            break;
            case MotionEvent.ACTION_UP:
            {
                //回弹动画
                TranslateAnimation offsetAnim=new TranslateAnimation(0,0,convertView.getTop(),originalRect.top);
                offsetAnim.setDuration(200);
                convertView.startAnimation(offsetAnim);
                //让子视图回到原来的位置
                convertView.layout(originalRect.left,originalRect.top,originalRect.right,originalRect.bottom);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}