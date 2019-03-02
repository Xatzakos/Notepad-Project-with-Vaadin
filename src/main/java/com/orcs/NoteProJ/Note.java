package com.orcs.NoteProJ;

import antlr.StringUtils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Formatter;

public class Note {


    private HorizontalLayout formLayout;
    private TextArea subTextField;
    private VerticalLayout subContent;
    private HorizontalLayout subButtons,subImages;
    private Window subWindow,subWindowCalc;
    private Panel panel = new Panel("Photo Panel");
    private HorizontalLayout PhotoContent = new HorizontalLayout();
    private Image image;

    //variables of note in the base
    private int userId,noteId;
    private String noteText; //note Text
    private String date,photos,audios,location;
    private String noteName;

    NoteSQL noteSQL = new NoteSQL();

    private Formatter x;


    public Note(int id,String noteName,String noteText,String date,String audios,String location,int noteId) {
        this.userId=id;
        this.noteName=noteName;
        this.noteText=noteText;
        this.date=date;
        this.date= this.date.replace("EEST","");
        this.audios=audios;
        this.location=location;
        this.noteId=noteId;
    }  //constructor of the note

    public Note(String noteName,int id) //constructor for creating a new note
    {
        this.noteName=noteName;
        this.userId=id;
        WebBrowser webBrowser = new WebBrowser();
        String date = webBrowser.getCurrentDate().toString();

        noteSQL.newNote(date,noteName,userId);
    }
    public String getNoteText()
    {
        return noteText;
    } //basic functions
    public String getNoteName()
    {
        return noteName;
    }
    public void setText(String text)
    {
        this.noteText=text;
    }


    public Layout addLayout() //the lauout and the buttons that will appear while making a new note
    {
        formLayout = new HorizontalLayout();
        formLayout.setWidth("60%");
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label nameLabel = new Label();
        nameLabel.setValue(getNoteName());
        nameLabel.addStyleName(ValoTheme.LABEL_H3);
        nameLabel.addStyleName(ValoTheme.LABEL_BOLD);

        Button editButton = new Button("Edit");
        editButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        editButton.setIcon(VaadinIcons.EDIT);
        editButton.addClickListener(clickEvent -> editButtonFunction());

        Button deleteButton = new Button("Delete");
        deleteButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        deleteButton.setIcon(VaadinIcons.TRASH);
        deleteButton.addClickListener(clickEvent -> deleteButtonFunction());

        formLayout.addComponentsAndExpand(nameLabel);
        formLayout.addComponents(editButton);
        formLayout.addComponents(deleteButton);

        subTextField=new TextArea();

        return formLayout;
    }

    public void editButtonFunction() //edit note
    {

        subWindow = new Window("Edit Note : " + getNoteName() + "\t" + date);

        if(getNoteText()!=null)
        subTextField.setValue(getNoteText());

        subContent = new VerticalLayout();
        subButtons = new HorizontalLayout();
        subImages = new HorizontalLayout();

        Button okButton= new Button("Finish");
        okButton.addClickListener(clickEvent -> {
            noteSQL.editText(subTextField.getValue(),noteId);
           setText(subTextField.getValue());
           subWindow.close();
           Page.getCurrent().reload();
        });
        Button addImageButton = new Button("Images",VaadinIcons.CAMERA);
        addImageButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        Button addSoundClip = new Button("Sound Clip",VaadinIcons.FILE_SOUND);
        addSoundClip.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        Button calcButton = new Button("Calculator",VaadinIcons.CALC);
        calcButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        Button exportButton = new Button("Export Note",VaadinIcons.OUTBOX);
        exportButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        Button printButton = new Button("Print Note",VaadinIcons.PRINT);
        printButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);


        addImageButton.addClickListener(clickEvent ->{
            addImageUrlWindow();
        } );

        addSoundClip.addClickListener(clickEvent -> {
           addSoundUrlWindow();
        });

        calcButton.addClickListener(clickEvent -> {
            calcWindow();
        });

        exportButton.addClickListener(clickEvent -> {
                NoteExport noteExport = new NoteExport();
                noteExport.exportNote(noteId,date,noteName,noteText,audios);
        });
        printButton.addClickListener(clickEvent -> {
            JavaScript.getCurrent().execute("print();");
        });

        subWindow.setContent(subContent);
        subContent.addComponentsAndExpand(subTextField);
        subButtons.addComponents(okButton,addImageButton,addSoundClip,calcButton,exportButton,printButton);

        subContent.addComponent(subButtons);
        subWindow.center();
        subWindow.setHeight("650");
        subWindow.setWidth("900px");
        subTextField.setSizeFull();

        addImageFunction();

        UI.getCurrent().addWindow(subWindow);
    }

    public void deleteButtonFunction() //delete a note
    {
        noteSQL.deleteText(noteId);
        Page.getCurrent().reload();
    }

    public void addImageUrlWindow() //new window to add an image url
    {
        TextArea url = new TextArea();
        Button okButton = new Button("OK");
        Button deleteButton = new Button("Delete Photos");
        Window suWin = new Window("ADD IMAGE URL");
        VerticalLayout subContent = new VerticalLayout();
        HorizontalLayout buttons= new HorizontalLayout();

        subContent.addComponentsAndExpand(url);
        buttons.addComponents(okButton,deleteButton);
        subContent.addComponent(buttons);
        suWin.setContent(subContent);
        suWin.center();
        suWin.setHeight("150");
        suWin.setWidth("500px");
        url.setSizeFull();
        UI.getCurrent().addWindow(suWin);

        okButton.addClickListener(clickEvent -> {
            noteSQL.addImageURL(noteId,url.getValue(),noteName);
            addPhoto(url.getValue());
            suWin.close();


        });
        deleteButton.addClickListener(clickEvent -> {
            noteSQL.deletePhoto(noteId);
            subWindow.close();
            suWin.close();
        });
    }

    public void addImageFunction() //image insertion process
    {
        ResultSet rs = null;
        rs=noteSQL.showPhotos(noteId);
        try {
        while (rs.next()) {
            addPhoto(rs.getString("photo"));
        } } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void addPhoto(String url) //Image options
    {
        if(!url.isEmpty()) {
            image = new Image();
            image.setSource(new ExternalResource(url));
            image.setWidth("200px");
            image.setHeight("150px");
            subContent.addComponent(panel);
            PhotoContent.setSizeUndefined();
            PhotoContent.addComponent(image);
            panel.setContent(PhotoContent);
            panel.getScrollTop();

        }
    }

    public void addSoundUrlWindow() //new window to add an audio url
    {
        TextArea SoundUrl = new TextArea();
        Button okButton = new Button("OK");
        Button listenButton = new Button("Play");
        listenButton.setIcon(VaadinIcons.PLAY);
        listenButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        String audioString="null";
        Window subWindow = new Window("ADD Sound URL");
        VerticalLayout subContent = new VerticalLayout();
        HorizontalLayout subButtons = new HorizontalLayout();
        subWindow.setContent(subContent);
        subButtons.addComponentsAndExpand(okButton,listenButton);
        subContent.addComponentsAndExpand(SoundUrl);
        subContent.addComponents(subButtons);
        subWindow.center();
        subWindow.setHeight("400");
        subWindow.setWidth("500px");
        SoundUrl.setSizeFull();
        UI.getCurrent().addWindow(subWindow);


        ResultSet rs=noteSQL.showAudios(noteId);
        try {
            while (rs.next()) {
                audioString = rs.getString("audios");
            }
            SoundUrl.setValue(audioString);
        }catch (Exception e){
            System.out.println("Sound Null Exception");
        }

//OK BUTTON
        okButton.addClickListener(clickEvent -> {
            noteSQL.addAudioURL(SoundUrl.getValue(),noteId);
            subWindow.close();
        });

        listenButton.addClickListener(clickEvent ->{
            Audio audioClip = new Audio();
            //audioClip.setSource(new ExternalResource(SoundUrl.getValue()));
            audioClip.setSource(new ExternalResource(SoundUrl.getValue()));
            audioClip.play();
            subContent.addComponent(audioClip);
        } );
    }

    public void calcWindow() //calculator
    {
        subWindowCalc = new Window("Calculator");
        subWindowCalc.center();
        subWindowCalc.setHeight("600");
        subWindowCalc.setWidth("350px");

        UI.getCurrent().addWindow(subWindowCalc);
        VerticalLayout layout = new VerticalLayout();
        NoteCalculator calc = new NoteCalculator();
        layout = calc.getLayout();
        Button submit = new Button("Submit", VaadinIcons.ENTER);
        layout.addComponent(submit);
        submit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        submit.addClickListener(clickEvent -> {
            subTextField.setValue(subTextField.getValue()+" "+calc.getResult());
            subWindowCalc.close();
        });
        subWindowCalc.setContent(layout);
    }

}
