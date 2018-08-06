package com.arthurfmg.mycomics.rest.model;

import com.arthurfmg.mycomics.common.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class UserModel {
    private String email;
    private String senha;
    private String nome;
    private String id;

    public UserModel(){

    }

    public void Salvar(){
        DatabaseReference referencia = ConfigFirebase.getFirebase();
        referencia.child("Usuarios").child(getId()).setValue(this);
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
