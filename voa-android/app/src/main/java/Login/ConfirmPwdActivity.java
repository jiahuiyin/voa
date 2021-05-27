package Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.voa.R;

import java.net.SocketOption;

public class ConfirmPwdActivity extends AppCompatActivity {
    private Button btn_gotoNext;
    private EditText editText_confirmPwd;
    private String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pwd);
        btn_gotoNext=findViewById(R.id.confirm_btn_gonext);
        editText_confirmPwd=findViewById(R.id.confirm_pwd);
        btn_gotoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd=editText_confirmPwd.getText().toString();
                if (!pwd.equals(getIntent().getExtras().getString("firstPwd"))){
                    Toast.makeText(ConfirmPwdActivity.this,"两次密码不一致",Toast.LENGTH_LONG).show();
                    System.out.println(getIntent().getExtras().getString("firstPwd"));
                    System.out.println(pwd);
                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(ConfirmPwdActivity.this);
                    builder.setTitle("提示")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(ConfirmPwdActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            });
                    if(getIntent().getExtras().getBoolean("request")){
                        builder.setMessage("注册成功,快去登录吧！");}
                    else {
                        builder.setMessage("密码找回成功");
                    }
                    builder.show();
                }
            }
        });
    }
}