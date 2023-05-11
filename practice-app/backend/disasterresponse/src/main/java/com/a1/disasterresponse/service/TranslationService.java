package com.a1.disasterresponse.service;

import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import okhttp3.RequestBody;
import okhttp3.FormBody;
import okhttp3.Call;


@Service
public class TranslationService {

    
    

public String translate(String inputText, String destLang) {

    OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
    RequestBody formBody = new FormBody.Builder()
      .add("q", inputText)
      .add("target", destLang)
      .add("format", "text")
      .add("key", "AIzaSyAy9tWrV0cEUu7WQLULnmLYISbCDqzjlGs")
      .build();

    Request request = new Request.Builder()
      .url("https://translation.googleapis.com/language/translate/v2")
      .post(formBody)
      .build();
    try {
    Call call = client.newCall(request);
    Response response = call.execute();
    return response.body().string();
    } catch (IOException e) {
        e.printStackTrace();
    }
    throw new RuntimeException();
    
}

    
}
