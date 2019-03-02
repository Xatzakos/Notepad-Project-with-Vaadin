package com.orcs.NoteProJ;


import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.navigator.*;
import java.sql.*;


@PushStateNavigation
@SpringUI  // ksekinaei me autin tin klassi ws vasiko UI
@Theme("valo")
public class NoteLoginActivity extends UI implements View {

    private VerticalLayout layout;
    private Button register, enter;
    private TextField usernameReg, passwordReg, emailReg;
    private TextField usernameLog, passwordLog;
    private TextField nameReg,surnameReg;
    String getUsr ="null",getPwd ="null",getEmail="null";
    int getId=-1 ;

    protected void init(VaadinRequest vaadinRequest) //oi sunartiseis pou tha treksei ws UI
     {
        setupLayout();
        addHeader();
        addForm();
    }

    private void addForm() {
        HorizontalLayout formLayout = new HorizontalLayout();

        Label usr = new Label("Username: ");
        usr.addStyleName(ValoTheme.LABEL_BOLD);
        usernameLog = new TextField();
        formLayout.addComponents(usr, usernameLog);
        layout.addComponent(formLayout);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        formLayout = new HorizontalLayout();
        Label psd = new Label("Password:   ");
        psd.addStyleName(ValoTheme.LABEL_BOLD);
        passwordLog = new PasswordField();
        formLayout.addComponents(psd, passwordLog);
        layout.addComponent(formLayout);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        formLayout = new HorizontalLayout();
        enter = new Button("Login");

        enter.addStyleName(ValoTheme.BUTTON_PRIMARY);
        formLayout.addComponent(enter);
        layout.addComponent(formLayout);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        for (int i = 0; i < 3; i++) {  // add some spaces
            formLayout = new HorizontalLayout();
            layout.addComponent(formLayout);
        }
        formLayout = new HorizontalLayout();
        Label rgstr = new Label("Dont Have an account?");
        formLayout.addComponent(rgstr);
        layout.addComponent(formLayout);

        formLayout = new HorizontalLayout();
        register = new Button("Register");
        register.addStyleName(ValoTheme.BUTTON_SMALL);
        register.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        formLayout.addComponent(register);
        layout.addComponent(formLayout);

        final Navigator navigator = new Navigator(this, this);
        navigator.addView("basicActivity", NoteBasicActivity.class);

        enter.addClickListener(clickEvent ->
        {
            if (usernameLog.isEmpty() || passwordLog.isEmpty()) {
                Notification.show("Please Enter some Data", Notification.Type.ERROR_MESSAGE);
            } else {
                try {
                    doLogin();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        register.addClickListener(clickEvent -> {
            registerForm();
        });
    }

    private void registerForm() {
        Window regWindow = new Window("Register Form");
        regWindow.center();
        regWindow.setHeight("500");
        regWindow.setWidth("450px");

        UI.getCurrent().addWindow(regWindow);
        VerticalLayout layout = new VerticalLayout();

        Button submit = new Button("Register", VaadinIcons.ENTER_ARROW);
        usernameReg = new TextField("Username: ");
        passwordReg = new PasswordField("Password: ");
        emailReg = new TextField("e-mail: ");
        nameReg=new TextField("Name:");
        surnameReg=new TextField("Surname");

        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponents(usernameReg, passwordReg,nameReg,surnameReg, emailReg);
        layout.addComponent(submit);
        submit.addStyleName(ValoTheme.BUTTON_FRIENDLY);


        submit.addClickListener(clickEvent -> {
            if (usernameReg.isEmpty() || passwordReg.isEmpty() || emailReg.isEmpty() || nameReg.isEmpty() || surnameReg.isEmpty()) {
                Notification.show("Invalid Values",
                        "Please Enter Some Data",
                        Notification.Type.ERROR_MESSAGE);
            } else {
                doRegister();
                regWindow.close();
            }
        });

        regWindow.setContent(layout);
    }

    private void addHeader() {
        Label header = new Label("Login");
        header.addStyleName(ValoTheme.LABEL_H1);
        header.addStyleName(ValoTheme.LABEL_COLORED);
        layout.addComponent(header);
    }

    private void setupLayout() {
        layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(layout);
    }

    private void doRegister() {
        NoteSQL noteSQL=new NoteSQL();
        noteSQL.register(usernameReg.getValue(),passwordReg.getValue(), emailReg.getValue(),nameReg.getValue(),surnameReg.getValue());
    }

    private void doLogin() throws SQLException {

        String usrname = usernameLog.getValue();
        String pwd=passwordLog.getValue();


        NoteSQL noteSQL = new NoteSQL();
        ResultSet rs= noteSQL.login(usrname);
        final Navigator navigator = new Navigator(this, this);
        navigator.addView("login", NoteLoginActivity.class);
        navigator.addView("basicActivity", NoteBasicActivity.class);

        try {
            while (rs.next()) {
                getUsr=rs.getString("username");
                getPwd=rs.getString("password");
                getEmail=rs.getString("email");
                getId=rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if(usrname.compareTo(getUsr)==0 && pwd.compareTo(getPwd)==0)
        {
            Notification.show("Login Succesfull",Notification.Type.HUMANIZED_MESSAGE);
            navigator.navigateTo("basicActivity" + "/" + getId); //open new activity and parse the ID
        }
        else
        {
            Notification.show("Incorect Username or Password",Notification.Type.ERROR_MESSAGE);
        }
    }
}