package com.k.douban;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.k.douban.util.NetUtil;

/**
 * Created by AAAAA on 2015/12/7.
 */
public class LoginInActivity extends Activity implements View.OnClickListener {

    protected static final int NEED_CAPTCHA = 10;
    protected static final int NOT_NEED_CAPTCHA = 11;
    protected static final int GET_CAPTCHA_ERROR = 12;
    protected static final int LOGIN_SUCCESS = 13;
    protected static final int LOGIN_FAIL = 14;
    protected static final int LOGIN_FAIL_ERROR = 15;
    private EditText mNameEditText, mPwdEditText;
    private LinearLayout mCaptchaLinearLayout;
    private TextView mEditTextCaptchaValue;
    private ImageView mImageViewCaptcha;
    private Button btnExit, btnLogin;
    private ProgressDialog progressDialog;
    private String result = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();//接收到消息之后，让其消失
            switch (msg.what) {
                case NEED_CAPTCHA:
                    mCaptchaLinearLayout.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) msg.obj;
                    mImageViewCaptcha.setImageBitmap(bitmap);
                    Toast.makeText(getApplicationContext(), "需要验证码", Toast.LENGTH_SHORT).show();
                    break;
                case NOT_NEED_CAPTCHA:
                    mCaptchaLinearLayout.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "不需验证码", Toast.LENGTH_SHORT).show();
                    break;
                case GET_CAPTCHA_ERROR:
                    Toast.makeText(getApplicationContext(), "查询验证码失败", Toast.LENGTH_SHORT).show();
                    break;
                case LOGIN_SUCCESS:
                    finish();
//                    Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    break;
                case LOGIN_FAIL:
                    Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
                    break;
                case LOGIN_FAIL_ERROR:
                    Toast.makeText(getApplicationContext(), "登录失败,ERROR", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //赋值控件
        //添加点击事件
        setupView();
        setLinster();
    }

    /**
     * 初始化控件*/
    private void setupView() {
        mNameEditText = (EditText) this.findViewById(R.id.EditTextEmail);
        mPwdEditText = (EditText) this.findViewById(R.id.EditTextPassword);
        mCaptchaLinearLayout = (LinearLayout) this.findViewById(R.id.Captcha);
        mEditTextCaptchaValue = (TextView) this.findViewById(R.id.EditTextCaptchaValue);
        mImageViewCaptcha = (ImageView) this.findViewById(R.id.ImageViewCaptcha);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        //判断是否需要验证码,并启动线程和获取图片
        getCaptcha();


    }

    /**
     * 获取验证码*/
    private void getCaptcha() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查询验证码");
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    result = NetUtil.isNeedCaptcha(getApplicationContext());
                    if (result != null) {
                        //获取验证码所对应的图片
                        String imagePath = getResources().getString(R.string.captchaurl) + result + "&amp;size=s";
                        Bitmap bitmap = NetUtil.getImage(imagePath); //拿到图片

                        Message message = new Message();
                        message.what = NEED_CAPTCHA;
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = NOT_NEED_CAPTCHA;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    Message message = new Message();
                    message.what = GET_CAPTCHA_ERROR;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 设置点击事件*/
    private void setLinster() {
        btnExit.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        mImageViewCaptcha.setOnClickListener(this);//每次点击验证码，就进行更换

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                System.out.println("点击了登录按钮");
                final String name = mNameEditText.getText().toString();
                final String pwd = mPwdEditText.getText().toString();
                if (name.equals("") || pwd.equals("")) {
                    Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (result != null) {
                        final String captchaValue = mEditTextCaptchaValue.getText().toString();
                        if (captchaValue.equals("")) {
                            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            login(name, pwd, captchaValue);
                        }
                    } else {
                        login(name, pwd, "");
                    }
                }
                break;
            case R.id.btnExit:
                System.out.print("点击了退出");
                finish();
                break;
            case R.id.ImageViewCaptcha:
                getCaptcha();
                break;
        }
    }

    /**
     * 登录操作*/
    private void login(final String name, final String pwd, final String captchaValue) {
        //登录的操作
        System.out.print("调用了login 方法");
        progressDialog.setMessage("正在登录");
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    boolean flag = NetUtil.Login(name, pwd, captchaValue, result, getApplicationContext());
                    if (flag) {
                        Message message = new Message();
                        message.what = LOGIN_SUCCESS;
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = LOGIN_FAIL;
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = LOGIN_FAIL_ERROR;
                    handler.sendMessage(message);
                }
            }
        }.start();
    }
}
