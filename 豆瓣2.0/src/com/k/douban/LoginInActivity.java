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
            progressDialog.dismiss();//���յ���Ϣ֮��������ʧ
            switch (msg.what) {
                case NEED_CAPTCHA:
                    mCaptchaLinearLayout.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) msg.obj;
                    mImageViewCaptcha.setImageBitmap(bitmap);
                    Toast.makeText(getApplicationContext(), "��Ҫ��֤��", Toast.LENGTH_SHORT).show();
                    break;
                case NOT_NEED_CAPTCHA:
                    mCaptchaLinearLayout.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "������֤��", Toast.LENGTH_SHORT).show();
                    break;
                case GET_CAPTCHA_ERROR:
                    Toast.makeText(getApplicationContext(), "��ѯ��֤��ʧ��", Toast.LENGTH_SHORT).show();
                    break;
                case LOGIN_SUCCESS:
                    finish();
//                    Toast.makeText(getApplicationContext(),"��¼�ɹ�",Toast.LENGTH_SHORT).show();
                    break;
                case LOGIN_FAIL:
                    Toast.makeText(getApplicationContext(), "��¼ʧ��", Toast.LENGTH_SHORT).show();
                    break;
                case LOGIN_FAIL_ERROR:
                    Toast.makeText(getApplicationContext(), "��¼ʧ��,ERROR", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //��ֵ�ؼ�
        //��ӵ���¼�
        setupView();
        setLinster();
    }

    /**
     * ��ʼ���ؼ�*/
    private void setupView() {
        mNameEditText = (EditText) this.findViewById(R.id.EditTextEmail);
        mPwdEditText = (EditText) this.findViewById(R.id.EditTextPassword);
        mCaptchaLinearLayout = (LinearLayout) this.findViewById(R.id.Captcha);
        mEditTextCaptchaValue = (TextView) this.findViewById(R.id.EditTextCaptchaValue);
        mImageViewCaptcha = (ImageView) this.findViewById(R.id.ImageViewCaptcha);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        //�ж��Ƿ���Ҫ��֤��,�������̺߳ͻ�ȡͼƬ
        getCaptcha();


    }

    /**
     * ��ȡ��֤��*/
    private void getCaptcha() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("���ڲ�ѯ��֤��");
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    result = NetUtil.isNeedCaptcha(getApplicationContext());
                    if (result != null) {
                        //��ȡ��֤������Ӧ��ͼƬ
                        String imagePath = getResources().getString(R.string.captchaurl) + result + "&amp;size=s";
                        Bitmap bitmap = NetUtil.getImage(imagePath); //�õ�ͼƬ

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
     * ���õ���¼�*/
    private void setLinster() {
        btnExit.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        mImageViewCaptcha.setOnClickListener(this);//ÿ�ε����֤�룬�ͽ��и���

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                System.out.println("����˵�¼��ť");
                final String name = mNameEditText.getText().toString();
                final String pwd = mPwdEditText.getText().toString();
                if (name.equals("") || pwd.equals("")) {
                    Toast.makeText(this, "�û��������벻��Ϊ��", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (result != null) {
                        final String captchaValue = mEditTextCaptchaValue.getText().toString();
                        if (captchaValue.equals("")) {
                            Toast.makeText(this, "��֤�벻��Ϊ��", Toast.LENGTH_SHORT).show();
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
                System.out.print("������˳�");
                finish();
                break;
            case R.id.ImageViewCaptcha:
                getCaptcha();
                break;
        }
    }

    /**
     * ��¼����*/
    private void login(final String name, final String pwd, final String captchaValue) {
        //��¼�Ĳ���
        System.out.print("������login ����");
        progressDialog.setMessage("���ڵ�¼");
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
