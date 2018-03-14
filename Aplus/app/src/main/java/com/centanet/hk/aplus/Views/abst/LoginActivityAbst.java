package com.centanet.hk.aplus.Views.abst;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Views.basic.BasicActivty;

/**
 * Created by mzh1608258 on 2018/1/2.
 */

public abstract class LoginActivityAbst extends BasicActivty {

    private EditText account;
    private EditText password;

    private ILoginManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        init();
    }


    private void initViews() {
        account = this.findViewById(R.id.login_account);
        password = this.findViewById(R.id.login_password);
    }

    private void init() {
        manager = setLoginHelper();
    }


    /**
     * 登入按鈕
     *
     * @param v
     */
    public void login(View v) throws Exception {
        if (manager == null) {
            throw new Exception("ILoginManager is null ! pls check!!");
        }else{
            manager.login(v, account.getText().toString(), password.getText().toString());
        }

    }



    protected abstract ILoginManager setLoginHelper();


    public interface ILoginManager {
        void login(View v, String account, String password);
    }
}
