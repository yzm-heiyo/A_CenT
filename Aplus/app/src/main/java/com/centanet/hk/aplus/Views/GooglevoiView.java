package com.centanet.hk.aplus.Views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.Dialog.TextDialog;
import com.centanet.hk.aplus.Views.Dialog.voice.VoiceInputPanel;

import java.util.ArrayList;

public class GooglevoiView extends AppCompatActivity implements View.OnClickListener, VoiceInputPanel.EventListener {

    private static final int RESULT_SPEECH = 22;
    private Button voice, dialog;
    private TextView voiceTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_voice_text);
        voiceTxt = findViewById(R.id.google_voice_txt);
        voice = findViewById(R.id.google_voice_btn);
        dialog = findViewById(R.id.google_voice_dialog);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                VoiceInputPanel voiceInputPanel = VoiceInputPanel.newInstance();
//                voiceInputPanel.setEventListener(new VoiceInputPanel.EventListener() {
//                    @Override
//                    public void onConfirmClick(String msg) {
//                        voiceTxt.setText(msg);
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
//                voiceInputPanel.show(getFragmentManager(), "");
                VoiceInputPanel.show(GooglevoiView.this, false, GooglevoiView.this);

            }
        });
        voice.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        SimpleTipsDialog dialog = new SimpleTipsDialog();
        dialog.setDialogCancelOnTouchOutside(false);
        dialog.show(getSupportFragmentManager(),"");

        //开启语音识别功能
        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //设置模式，目前设置的是自由识别模式
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //提示语言开始文字，就是效果图上面的文字
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please start your voice");
//        开始识别，这里检测手机是否支持语音识别并且捕获异常
        try {
            startActivityForResult(intent, RESULT_SPEECH);
            voiceTxt.setText("");
        } catch (ActivityNotFoundException a) {
            Toast t = Toast.makeText(getApplicationContext(),
                    "Opps! Your device doesn't support Speech to Text",
                    Toast.LENGTH_SHORT);
            t.show();
        }
    }

    //接收返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && data != null) {
                    //返回结果是一个list，我们一般取的是第一个最匹配的结果
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    voiceTxt.setText(text.get(0));
                }
//                break;
//            }
//        }
    }

    @Override
    public void onConfirmClick(String msg) {
        voiceTxt.setText(msg);

    }

    @Override
    public void onCancel() {
    }
}
