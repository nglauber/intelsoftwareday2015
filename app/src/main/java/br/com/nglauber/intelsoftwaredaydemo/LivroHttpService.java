package br.com.nglauber.intelsoftwaredaydemo;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.com.nglauber.intelsoftwaredaydemo.model.Editora;

public class LivroHttpService {

    public Editora carregarEditora(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://dl.dropboxusercontent.com/u/6802536/livros_novatec.json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();

            Gson gson = new Gson();
            return gson.fromJson(json, Editora.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
