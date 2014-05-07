/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vaadin;

import com.vaadin.ui.*;
import com.vaadin.ui.LoginForm;
import java.awt.Component;

/**
 *
 * @author Администратор
 */
 // Add an instance of this class to your UI
    public abstract class MyLoginForm extends LoginForm {
                protected HorizontalLayout createContent(TextField userNameField, PasswordField passwordField, Button loginButton) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setSpacing(true);
            layout.setMargin(true);

            layout.addComponent(userNameField);
            layout.addComponent(passwordField);
            layout.addComponent(loginButton);
            layout.setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);
            return layout;
        }

       
        protected void login(String userName, String password) {
            System.err.println(
                "Logged in with user name " + userName +
                " and password of length " + password.length()
            );
        }
    }
