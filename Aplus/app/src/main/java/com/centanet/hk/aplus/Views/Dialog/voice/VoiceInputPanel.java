package com.centanet.hk.aplus.Views.Dialog.voice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.StringUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by oreo on 17/11/2017.
 */

public class VoiceInputPanel extends DialogFragment implements View.OnClickListener, RecognitionListener, VoiceView.OnRecordListener {

    public interface EventListener {
        void onConfirmClick(String msg);

        void onCancel();
    }

    private EventListener eventListener;

    private TextView labSubText;
    private TextView labConfirm;
    private View mainview;
    private VoiceView voiceView;

    private String speechResult = StringUtil.Empty;
    private SpeechRecognizer speechRecognizer = null;
    private boolean isFinish = false;
    private boolean isSpeaking = false;
    private boolean isVoiceValue = false;

    public static VoiceInputPanel newInstance() {
        return new VoiceInputPanel();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_voice_input_panel, null);
        initFind(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(false);
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.CENTER;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(attributes);
        recognizeSpeechDirectly();
        return dialog;
    }

    private void initFind(View view) {
        mainview = view.findViewById(R.id.mainLayout);
        labSubText = (TextView) view.findViewById(R.id.labSubTitle);
        labConfirm = (TextView) view.findViewById(R.id.labConfirm);
        voiceView = (VoiceView) view.findViewById(R.id.voiceview);
        mainview.setOnClickListener(this);
        labConfirm.setOnClickListener(this);
        voiceView.setOnClickListener(this);
    }

    private void recognizeSpeechDirectly() {
        voiceView.setOnRecordListener(this);
        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                MyApplication.getContext().getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        getSpeechRecognizer().startListening(recognizerIntent);
    }

    private SpeechRecognizer getSpeechRecognizer() {
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(MyApplication.getContext());
        speechRecognizer.setRecognitionListener(this);
        return speechRecognizer;
    }

    // Getter/Setter
    public EventListener getEventListener() {
        return eventListener;
    }

    public VoiceInputPanel setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
        return this;
    }


    @Override
    public void onResume() {
        super.onResume();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!isSpeaking)
                    setVisibility(false);
            }
        };
        timer.schedule(timerTask, 3 * 60 * 1000);
    }

    private void setVisibility(boolean visible) {
        try {
            if (speechRecognizer != null) {
                speechRecognizer.destroy();
                speechRecognizer = null;
            }
            if (!visible) {
                dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implements
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainLayout:
                if (eventListener != null) {
                    eventListener.onCancel();
                }
                setVisibility(false);
                break;
            case R.id.labConfirm:
                eventListener.onConfirmClick(speechResult);
                setVisibility(false);
                break;
            case R.id.voiceview:
                if (isFinish) {
                    recognizeSpeechDirectly();
                }
                break;
        }
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        L.d("onReadyForSpeech", "");

    }

    @Override
    public void onBeginningOfSpeech() {
        L.d("onBeginningOfSpeech", "");
        isSpeaking = true;
    }

    @Override
    public void onRmsChanged(float rmsdB) {
//        L.d("onRmsChanged", isSpeaking + "");
        if (rmsdB > 9) {
            L.d("onRmsChanged", rmsdB + "");
            isVoiceValue = true;
        }
        voiceView.animateRadius(rmsdB * toPx(20));
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        L.d("onBufferReceived", "");

    }

    @Override
    public void onEndOfSpeech() {
        L.d("onEndOfSpeech", isVoiceValue + "");
        if (!isVoiceValue)
            setVisibility(false);
        else isVoiceValue = false;
        isSpeaking = false;
    }

    @Override
    public void onError(int error) {
        isFinish = true;
        if (isFinish) {
            recognizeSpeechDirectly();
        }
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        L.d("voice", matches.toString());

        if (matches != null && matches.size() > 0) {
            speechResult = matches.get(0);
            isFinish = true;
            labConfirm.performClick();
        }else setVisibility(false);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        L.d("onPartialResults", "");
        //google基本都会忽略部分结果
        //该方法几乎很少有机会能够被调用到
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
    }

    @Override
    public void onRecordStart() {
    }

    @Override
    public void onRecordFinish() {
    }

    public static void show(Activity activity, boolean isBackPressCancel, EventListener eventListener) {
        if (SpeechRecognizer.isRecognitionAvailable(activity)) {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            Fragment prev = activity.getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            VoiceInputPanel voiceInputPanel = VoiceInputPanel.newInstance();
            voiceInputPanel.setEventListener(eventListener);
            voiceInputPanel.show(ft, "dialog");
        }
    }

    public int toPx(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void onPause() {
        super.onPause();
        setVisibility(false);
    }
}
