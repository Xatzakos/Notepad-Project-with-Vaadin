package com.orcs.NoteProJ;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.hibernate.boot.jaxb.SourceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteBasicActivity extends Composite implements View {

    VerticalLayout layout;
    Label header , header2;
    TextField searchField;
    private int id;
    private String username,name,surname;
    private String password;
    private String email;

    public NoteBasicActivity() {
        setCompositionRoot(layout = new VerticalLayout());
        setupLayout();
        addHeader();
        addForm();

    }

    private void setupLayout() {  //dimiourgoume ena katheto layout
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
    }

    private void addHeader() { //epikefalida
        header = new Label("wlc usr");
        header2 = new Label("My Notes");
        header.addStyleName(ValoTheme.LABEL_H1);
        header2.addStyleName(ValoTheme.LABEL_H2);
        layout.addComponents(header,header2);
    }

    private void addForm() { //vasiki forma
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setWidth("30%");

        TextField taskField = new TextField();
        taskField.focus();

        Button addButton = new Button("Create a new Note");
        Button searchButton = new Button("Search a Note");
        Button logoutButton = new Button("Logout",VaadinIcons.SIGN_OUT);
        logoutButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        searchField = new TextField();
        formLayout.addComponentsAndExpand(searchField);
        formLayout.addComponent(searchButton);
        layout.addComponent(formLayout);
        HorizontalLayout SearchLayout = new HorizontalLayout();
        SearchLayout.setWidth("40%");
        SearchLayout.addComponentsAndExpand(taskField);
        SearchLayout.addComponents(addButton);
        layout.addComponent(SearchLayout);
        layout.addComponent(logoutButton);

        addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addButton.setIcon(VaadinIcons.PLUS);

        addButton.addClickListener(clickEvent -> { //dimiourgia enos note antikeimenou
            Note note = new Note(taskField.getValue(),id);
            layout.addComponent(note.addLayout());
            taskField.setValue("");
            taskField.focus();
            Page.getCurrent().reload();
        });

        searchButton.addClickListener(clickEvent -> { //anazitisi enos note antikeimenou
            searchNotes();
        });

        logoutButton.addClickListener(clickEvent -> {
            Page.getCurrent().setLocation( "/" );
            VaadinSession.getCurrent().close();
        });
    }

    public void loadNotes()
    {
        ResultSet rs = null;
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        int size=0;
        String Sql = "Select * from notes where userid=?";

        try {
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
               String noteText = rs.getString("note");
                String date=rs.getString("date");
               // String photos=rs.getString("photos");
                String audios=rs.getString("audios");
                String location=rs.getString("location");
                String noteName=rs.getString("noteName");
                int noteId=rs.getInt("noteid");
                Note note = new Note(id,noteName,noteText,date,audios,location,noteId);
                size++;

                layout.addComponent(note.addLayout());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        header2.setValue("My Notes "+ "(total " + size +")");
    }

    public void searchNotes()
    {
        Connection connection = ConnectionConfiguration.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        System.out.println(id);
        Window subWindow = new Window("Results:");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);
        subWindow.center();

        subWindow.setHeight("450");
        subWindow.setWidth("550px");
        UI.getCurrent().addWindow(subWindow);

        String Sql = "Select * from notes where userid=? and (noteName LIKE ? or note LIKE ? or date LIKE ?)";

        try {
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,"%"+searchField.getValue()+"%");
            preparedStatement.setString(3,"%"+searchField.getValue()+"%");
            preparedStatement.setString(4,"%" + searchField.getValue()+"%");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                String noteText = rs.getString("note");
                String date=rs.getString("date");
                // String photos=rs.getString("photos");
                String audios=rs.getString("audios");
                String location=rs.getString("location");
                String noteName=rs.getString("noteName");
                int noteId=rs.getInt("noteid");
                Note note = new Note(id,noteName,noteText,date,audios,location,noteId);

                subContent.addComponent(note.addLayout());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void enter(ViewChangeListener.ViewChangeEvent event) { //get UserID from parameters
        if (event.getParameters() != null) {
            String parsedID = event.getParameters();
            this.id = Integer.parseInt(parsedID);
            NoteSQL noteSQL = new NoteSQL();
            ResultSet rs= noteSQL.BasicActivityLoad(id);
            try {
                while (rs.next()) {
                    username = rs.getString("username");
                    password = rs.getString("password");
                    email = rs.getString("email");
                    name=rs.getString("name");
                    surname=rs.getString("surname");

                }
            } catch (Exception e) {
                System.out.println(e);
            }
            header.setValue("Welcome "+ name + " " + surname);
        }
        loadNotes();
    } //When page Opens
}
