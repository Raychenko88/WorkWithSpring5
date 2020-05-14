package org.example;


import org.example.dao.UserDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.example");
        context.getEnvironment().setActiveProfiles("dev");
        UserDAO userDAO = context.getBean(UserDAO.class);
    }
}
