package Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.voa.R;

public class InputPwdActivity extends AppCompatActivity {
    private Button btn_gotoNext;
    private EditText editText_inputPwd;
    private TextView tv_tip;
    private String reg = "(?=.*([a-zA-Z].*))(?=.*[0-9].*)[a-zA-Z0-9-*/+.~!@#$%^&*()]{6,18}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pwd);
        btn_gotoNext=findViewById(R.id.inputpwd_btn_gonext);
        editText_inputPwd=findViewById(R.id.inpupwd_pwd);
        tv_tip=findViewById(R.id.input_tip);
        btn_gotoNext.setOnClickListener(new OnClick());
    }
    public void initVerification(){
        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        if (c >= 0x4e00 && c <= 0X9fff) {
                            // 根据字节码判断
                            // 如果是中文，则清除输入的字符，否则保留
                            s.delete(i,i+1);
                        }
                    }
                }
            }
        };
        editText_inputPwd.addTextChangedListener(textWatcher);
    }

    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String pwd=editText_inputPwd.getText().toString();
            if(!pwd.matches(reg)){
                Toast.makeText(InputPwdActivity.this,"密码格式不正确",Toast.LENGTH_SHORT).show();
                tv_tip.setTextColor(Color.RED);
            }
            else{
                Intent intent=new Intent(InputPwdActivity.this,ConfirmPwdActivity.class);
                intent.putExtra("request",getIntent().getExtras().getBoolean("request"));
                intent.putExtra("phoneNum",getIntent().getExtras().getString("phoneNum"));
                intent.putExtra("firstPwd",pwd);
                startActivity(intent);
            }
        }
    }
}