package com.arthurfmg.mycomics.rest.model;

import com.arthurfmg.mycomics.common.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class UserModel extends ComicVineModel{
    private String email;
    private String senha;
    private String nome;
    private String idUsuario;
    private String idVolume;

    public UserModel(){

    }

    public void Salvar(){
        DatabaseReference referencia = ConfigFirebase.getFirebase();
        referencia.child("Usuarios").child(getIdUsuario()).setValue(this);
    }

    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String id) {
        this.idUsuario = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVolume() {
        return idVolume;
    }

    public void setVolume(String idVolume) {
        this.idVolume = idVolume;
    }
}
