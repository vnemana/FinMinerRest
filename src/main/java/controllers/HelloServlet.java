package controllers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@WebServlet(name="hello", urlPatterns = "/")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FileReader homePageFileReader = new FileReader
                ("/home/mahesh/code/FinMiner-2.0/FinMinerRest/src/main" +
                        "/resources/html/home_page.html");
        BufferedReader homePageBuffReader = new BufferedReader
                (homePageFileReader);
        String st;
        while ((st = homePageBuffReader.readLine()) != null) {
            resp.getWriter().write(st);
        }
    }
}
