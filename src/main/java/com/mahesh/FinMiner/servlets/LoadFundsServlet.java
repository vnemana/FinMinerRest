package com.mahesh.FinMiner.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@WebServlet(name="hello", urlPatterns = "/")
public class LoadFundsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = this.getServletContext().getRealPath("/");
        String home_page_path = path + "WEB-INF/classes/html/home_page.html";
        FileReader homePageFileReader = new FileReader
                (home_page_path);
        BufferedReader homePageBuffReader = new BufferedReader
                (homePageFileReader);
        String st;
        while ((st = homePageBuffReader.readLine()) != null) {
            resp.getWriter().write(st);
        }
    }
}
