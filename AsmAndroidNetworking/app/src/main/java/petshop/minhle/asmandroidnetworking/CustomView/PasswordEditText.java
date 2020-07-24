package petshop.minhle.asmandroidnetworking.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.minhle.asmandroidnetworking.R;



public class PasswordEditText extends androidx.appcompat.widget.AppCompatEditText {
    Drawable eye, eye2;
    Boolean visible = false;
    Boolean useStrike = false;
    int ALPHA = (int) (255 * .40f);


    public PasswordEditText(Context context) {
        super(context);
        khoitao(null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        khoitao(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        khoitao(attrs);
    }

    @Override
    public TextUtils.TruncateAt getEllipsize() {
        return super.getEllipsize();
    }


    private void khoitao(AttributeSet attrs) {
        if(attrs != null){
            TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs,R.styleable.PasswordEditText,0,0);
            this.useStrike = array.getBoolean(R.styleable.PasswordEditText_useStrike,false);
        }

        eye = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_black_24dp).mutate();
        eye2 = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off_black_24dp).mutate();

        caidat();
    }


    private void caidat() {
        setInputType(InputType.TYPE_CLASS_TEXT | (visible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawables = getCompoundDrawables();

        Drawable drawable = useStrike && !visible ? eye2 : eye;
        drawable.setAlpha(ALPHA);
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawable, drawables[3]);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() >= getRight() - getCompoundDrawables()[2].getBounds().width()) {
            visible = !visible;
            caidat();
            invalidate();
        }

        return super.onTouchEvent(event);
    }
}
