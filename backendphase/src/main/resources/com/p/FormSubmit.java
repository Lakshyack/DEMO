package com.p;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/FormSubmitted")
public class FormSubmit extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper obj = new ObjectMapper();
        Map<String, String> responseMap = new HashMap<>();

        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }

            Map<String, String> jsonMap = obj.readValue(stringBuilder.toString(), Map.class);
            String email = jsonMap.get("email");
            String password = jsonMap.get("password");

            // Log the received data (consider using a proper logging framework in production)
            System.out.println("Received email: " + email);
            System.out.println("Received password: " + password);

            // Prepare the response
            responseMap.put("status", "success");
            responseMap.put("message", "Form submitted successfully");
            responseMap.put("email", email);
            responseMap.put("password", password);
            responseMap.put("name", "lakshya");




        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("status", "error");
            responseMap.put("message", "Failed to process form data");
        } finally {
            // Send the JSON response
            String jsonResponse = obj.writeValueAsString(responseMap);
            resp.getWriter().write(jsonResponse);
        }
    }
}
