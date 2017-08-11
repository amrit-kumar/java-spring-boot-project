package com.test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RestController
@RequestMapping("/api")
public class StudentController {

    @RequestMapping("/test")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAll() {
        Connection con = null;
        JSONArray array = new JSONArray();
        JSONObject objFinal = new JSONObject();
        try {
            con = SqlConnection.getConn();
            PreparedStatement pst = con.prepareStatement("select * from student");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("roll_num", rs.getString(1));
                obj.put("name", rs.getString(2));
                obj.put("age", rs.getInt(3));
                array.put(obj);
            }

            objFinal.put("data", array);
        } catch (Exception ex) {
            System.out.println("error  :" + ex);
        }
        return objFinal + "";
    }

     @RequestMapping(value = "/postall", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public String insertData(@RequestBody String s) {
        JsonParser parer = new JsonParser();
        JsonObject obj = (JsonObject) parer.parse(s);
        Gson gson = new Gson();
        Student feed = gson.fromJson(obj, Student.class);
        try {
            Connection con = SqlConnection.getConn();
            int age = feed.getAge();
            String name = feed.getName();
            String roll_num = feed.getRoll_num();
            String insertQuery = "insert into student (roll_num, name, age) values('" + roll_num + "','" + name + "'," + age + ")";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.println("error:   " + e);
        }

        return "inserted";
    }
}