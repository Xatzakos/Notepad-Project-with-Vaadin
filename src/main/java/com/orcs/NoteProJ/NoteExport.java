package com.orcs.NoteProJ;

import com.vaadin.ui.*;

import java.sql.ResultSet;
import java.util.Formatter;

public class NoteExport {
    private Formatter x;
    public void exportNote(int noteId, String date, String noteName, String noteText, String audios)
    {
        NoteSQL noteSQL = new NoteSQL();
        ResultSet rs = noteSQL.showPhotos(noteId);
        String photosURL = "";
        try {
            while (rs.next()) {
                photosURL+= rs.getString("photo") + "\n";
            } } catch (Exception e) {
            System.out.println(e);
        }
        String exportText="Date : "+date + "\n"+"Note Name : "+noteName + "\n" + "Note Text : "+noteText + "\n" + "Audio Note : " + audios +"\n" + "Photos : "+"\n" + photosURL;
        System.out.println(exportText);
        openFile(exportText,noteName);
    }
    public void openFile(String exportText,String noteName) {
        TextArea url = new TextArea();
        Button okButton = new Button("OK");
        Window suWin = new Window("ADD EXPORT PATH");
        VerticalLayout subContent = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();

        subContent.addComponentsAndExpand(url);
        buttons.addComponents(okButton);
        subContent.addComponent(buttons);
        suWin.setContent(subContent);
        suWin.center();
        suWin.setHeight("150");
        suWin.setWidth("500px");
        url.setSizeFull();
        UI.getCurrent().addWindow(suWin);

        okButton.addClickListener(clickEvent -> {
            try {
                x = new Formatter(url.getValue() + "/" + noteName + "EXPORT.txt");

            } catch (Exception e) {
                System.out.println(e);
            }
            x.format("%s", exportText);
            x.close();
            suWin.close();
        });
    }
}
