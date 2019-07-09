package com.example.login.Modelo;

import java.io.Serializable;

public class ModeloCadastro implements Serializable {

    private Long _id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String descricao;
    private byte [] foto1;
    private byte [] fotoperfil;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getFotoperfil() {
        return fotoperfil;
    }

    public void setFotoperfil(byte[] fotoperfil) {
        this.fotoperfil = fotoperfil;
    }

    public byte[] getFoto1() {
        return foto1;
    }

    public void setFoto1(byte[] foto1) {
        this.foto1 = foto1;
    }
}
