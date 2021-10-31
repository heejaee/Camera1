package com.example.camera1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import java.util.List;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URLEncoder;
public class TestResultActivity extends AppCompatActivity {
    private TextView mTextResult;

    private TextToSpeech tts;

    private ImageButton button,fast, slow;
    public TextView medi_info_text,tv;
    public String medi_info_data, put;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        button = (ImageButton) findViewById(R.id.button);
        slow = (ImageButton) findViewById(R.id.slow);
        fast = (ImageButton) findViewById(R.id.fast);

        tv = findViewById(R.id.textResult2);

        Intent secondIntent = getIntent();
        String message = secondIntent.getStringExtra("key");
        tv.setText(message);
        tv.setMovementMethod(new ScrollingMovementMethod());

        //tv.setVisibility(View.INVISIBLE); //안보이게


        medi_info_text = (TextView)findViewById(R.id.medi_info);
        medi_info_text.setMovementMethod(new ScrollingMovementMethod());

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                medi_info_data = getXmlData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        medi_info_text.setText(medi_info_data);
                    }
                });
            }
        }).start();
        ////////////////////




        /////////////////////

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // editText에 있는 문장을 읽는다.
                tts.speak(message,TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.setPitch(1.0f);         // 음성 톤은 기본 설정
                tts.setSpeechRate(2.0f);    // 읽는 속도를 2배 빠르기로 설정

                tts.speak(message,TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        slow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.setPitch(1.0f);         // 음성 톤은 기본 설정
                tts.setSpeechRate(0.5f);    // 읽는 속도를 0.5빠르기로 설정

                tts.speak(message,TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }
    // client에서 open api 를 얻어오는 속도를 확인해봐야 할 듯
    // xml parsing part
    String getXmlData() {
        StringBuffer buffer = new StringBuffer();
//        String item_name = "타이레놀정160밀리그람(아세트아미노펜)";
//        String item_name = "아세탈정";
//        String item_name = "리보트릴정";


        put =tv.getText().toString();
        String item_name=put;



       //String item_name = put;
        //String item_name = "타이레놀정160밀리그람(아세트아미노펜)";
        String serviceKey = "hmx5eKVn6dzxC9m9F%2Bjc8FmFJHFDtJOKYv4f4vwkQwjFvk25B795CSbBsM19hRfc84JnEDacZxVCwjhjuwpDng%3D%3D";

        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService1/getMdcinPrductItem");
            Log.e("MY_TEST", "urlBuilder");
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=hmx5eKVn6dzxC9m9F%2Bjc8FmFJHFDtJOKYv4f4vwkQwjFvk25B795CSbBsM19hRfc84JnEDacZxVCwjhjuwpDng%3D%3D");
            urlBuilder.append("&" + URLEncoder.encode("item_name", "UTF-8") + "=" + URLEncoder.encode(item_name, "UTF-8"));
            URL url = new URL(urlBuilder.toString());
            Log.e("BUS_API_TEST", url.toString());

            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            xpp.next();
            Log.e("this is xpp", xpp.toString());
            int eventType = xpp.getEventType();
            int token;
            String cdata;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ;
                        else if (tag.equals("ITEM_NAME")) {
                            buffer.append("의약품명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if (tag.equals("VALID_TERM")) {
                            buffer.append("유효기간 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if (tag.equals("EE_DOC_DATA")) {
                            xpp.next();
//                            buffer.append("Tag is : " + xpp.getName() + "\n");    //  DOC
                            buffer.append(xpp.getAttributeValue(0) + "\n\n");
                            // 왜인지 모르겠으나 xpp.next() 두번씩 해야 다음 태그로 넘어감
                            xpp.next(); xpp.next();
//                            buffer.append("Tag is : " + xpp.getName() + "\n");    // SECTION
                            xpp.next(); xpp.next();
//                            buffer.append("Tag is : " + xpp.getName() + "\n");   //   ARTICLE
                            buffer.append(xpp.getAttributeValue(0) + "\n");   // 주효능효과 출력
                            xpp.next(); xpp.next();

                            while(!"ARTICLE".equals(xpp.getName())) {
                                // PARAGARPH TAG
                                token = xpp.nextToken();
                                while(token!=XmlPullParser.CDSECT){
                                    token = xpp.nextToken();
                                }
                                cdata = xpp.getText();
                                buffer.append(cdata+ "\n\n");
                                xpp.next(); xpp.next(); xpp.next();
                            }
                            xpp.next();  xpp.next();

//                            buffer.append(xpp.getAttributeValue(0) + "\n");   // 주효능효과 출력
//                            xpp.next(); xpp.next();
//
//                            while(!"ARTICLE".equals(xpp.getName())) {
//                                // PARAGARPH TAG
//                                token = xpp.nextToken();
//                                while(token!=XmlPullParser.CDSECT){
//                                    token = xpp.nextToken();
//                                }
//                                cdata = xpp.getText();
//                                buffer.append(cdata+ "\n");
//                                xpp.next(); xpp.next(); xpp.next();
//                            }
                            xpp.next();
                        } else if (tag.equals("UD_DOC_DATA")) {
                            buffer.append("\n" + "============================" + "\n\n");
                            xpp.next();
//                            buffer.append("Tag is : " + xpp.getName() + "\n");  // DOC
                            buffer.append(xpp.getAttributeValue(0) + "\n");   // 사용상의 주의사항
                            xpp.next(); xpp.next();
//                            buffer.append("Tag is : " + xpp.getName() + "\n");    // SECTION
                            xpp.next(); xpp.next();
//                            buffer.append("Tag is : " + xpp.getName() + "\n");    // ARTICLE
                            buffer.append(xpp.getAttributeValue(0) + "\n");       // 1. 경고
                            xpp.next(); xpp.next();

                            while(!"ARTICLE".equals(xpp.getName())) {
                                if ("p".equals(xpp.getAttributeValue(0))) {
                                    token = xpp.nextToken();
                                    while(token!=XmlPullParser.CDSECT){
                                        token = xpp.nextToken();
                                    }
                                    cdata = xpp.getText();
                                    buffer.append(cdata+ "\n");
                                    xpp.next(); xpp.next(); xpp.next();
                                } else {
                                    xpp.next(); xpp.next(); xpp.next(); xpp.next();
                                }



                            }

                        }
                        else if (tag.equals("NB_DOC_DATA")) {
                            buffer.append("\n" + "============================" + "\n\n");
                            xpp.next();
//                            buffer.append("Tag is : " + xpp.getName() + "\n");  // DOC
                            buffer.append(xpp.getAttributeValue(0) + "\n\n");   // 사용상의 주의사항
                            xpp.next(); xpp.next();
//                            buffer.append("Tag is : " + xpp.getName() + "\n");    // SECTION
                            xpp.next(); xpp.next();
//                            buffer.append("Tag is : " + xpp.getName() + "\n");    // ARTICLE
                            buffer.append(xpp.getAttributeValue(0) + "\n\n");       // 1. 경고
                            xpp.next(); xpp.next();

                            while(!"ARTICLE".equals(xpp.getName())) {
                                // PARAGARPH TAG
                                token = xpp.nextToken();
                                while(token!=XmlPullParser.CDSECT){
                                    token = xpp.nextToken();
                                }
                                cdata = xpp.getText();
                                buffer.append(cdata+ "\n\n");
                                xpp.next(); xpp.next(); xpp.next();
                            }

//                            while(!"SECTION".equals(xpp.getName())) {
//
//                                buffer.append("Tag is : " + xpp.getName() + "\n");
//                                xpp.next(); xpp.next();
//                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return buffer.toString();//StringBuffer 문자열 객체 반환
    }


}



