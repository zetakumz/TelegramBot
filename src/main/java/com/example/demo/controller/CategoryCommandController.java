package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Location;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CategoryCommandController {
    public CategoryCommandController() {
    }

    //REQUISIÇÔES GET:


    //GET ALL
    public List<Category> listallCategories() throws IOException {
        URL url = new URL("https://manage-bot-ufrn.herokuapp.com/category");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        ObjectMapper obj = new ObjectMapper();
        List<Category> categories = obj.readValue(content.toString(), new TypeReference<List<Category>>() {});
        return categories;
    }


    //GET BY ID
    public Category categoryByID (String id) throws IOException {
        URL url = new URL("https://manage-bot-ufrn.herokuapp.com/category/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        if(con.getResponseCode() != 200) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        ObjectMapper obj = new ObjectMapper();
        return obj.readValue(content.toString(), Category.class);
    }


    //GET BY NAME
    public Category byCategoryName(String categoria) throws IOException {
        URL url = new URL("https://manage-bot-ufrn.herokuapp.com/category/byCategoryName/" + categoria);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        if(con.getResponseCode() != 200) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        ObjectMapper obj = new ObjectMapper();
        return obj.readValue(content.toString(), Category.class);
    }


    //GET BY DESCRIPTION
    public List<Category> byCategoryDescription(String categoria) throws IOException {
        URL url = new URL("https://manage-bot-ufrn.herokuapp.com/category/byCategoryDescription/" + categoria);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        ObjectMapper obj = new ObjectMapper();
        return obj.readValue(content.toString(), new TypeReference<List<Category>>() {});
    }












    //REQUISIÇÃO POST

    public String PostCategory(Category category) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonInputString = ow.writeValueAsString(category);

        URL url = new URL("http://manage-bot-ufrn.herokuapp.com/category");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            con.getResponseCode();
        }
        if(con.getResponseCode()==200) {
            return "Cadastro de categoria feito com sucesso";
        } else {
            return "Ops! Houve um erro no cadastro da categoria";
        }
    }

    //REQUISIÇÃO DELETE
    public String DeleteCategoria(String id) throws IOException {
        URL url = new URL("https://manage-bot-ufrn.herokuapp.com/category/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        if(con.getResponseCode()==200) {
            return "Remoção de categoria feita com sucesso";
        } else {
            return "Ops! Houve um erro na remoção";
        }
    }
}