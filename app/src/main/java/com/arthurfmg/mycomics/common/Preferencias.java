package com.arthurfmg.mycomics.common;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {
    private Context contexto;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "MyComicsPreferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;
    private String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private String CHAVE_NOME = "nomeUsuarioLogado";

    public Preferencias(Context contextoParametro){
        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }

    public void salvarDados(String identificadorUsuario, String nomeUsuario){

        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.putString(CHAVE_NOME, nomeUsuario);
        editor.commit();

    }

    public String getIdentificador (){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNOME (){
        return preferences.getString(CHAVE_NOME, null);
    }
}
