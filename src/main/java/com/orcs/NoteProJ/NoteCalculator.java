package com.orcs.NoteProJ;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class NoteCalculator extends VerticalLayout
{
    private VerticalLayout layout;
    private int a,b;
    private boolean plus,minus,mul,div;
    private Label result;

    public NoteCalculator()
    {
        setupLayout();
        addHeader();
        addForm();
    }
    private void setupLayout() {
        layout = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

       // setContent(layout);

    }

    private void addHeader() {
        Label header = new Label("CALCULATOR");
        header.addStyleName(ValoTheme.LABEL_H1);
        layout.addComponent(header);

    }

    private void addForm() {
        HorizontalLayout formLayout = new HorizontalLayout();
        HorizontalLayout formLayout1 = new HorizontalLayout();
        HorizontalLayout formLayout2 = new HorizontalLayout();
        HorizontalLayout formLayout3 = new HorizontalLayout();
        HorizontalLayout formLayout4 = new HorizontalLayout();
        HorizontalLayout text = new HorizontalLayout();
        HorizontalLayout bottom = new HorizontalLayout();

        result = new Label();
        //taskField.focus();
        Button addButton = new Button("+"); //ADD BUTTON
        Button minusButton = new Button("-"); // MINUS BUTTON
        Button equalButton = new Button("=");
        equalButton.addStyleName(ValoTheme.LABEL_COLORED);
        Button mulButton = new Button("*");
        Button divButton = new Button("/");
        Button button1 = new Button("1"); //1 button
        Button button2 = new Button("2"); //2
        Button button3 = new Button("3"); //3
        Button button4 = new Button("4"); //4
        Button button5 = new Button("5"); //5
        Button button6 = new Button("6"); //6
        Button button7 = new Button("7");// 7
        Button button8 = new Button("8");
        Button button9 = new Button("9");
        Button button0 = new Button("0");
        Button clear = new Button("CLEAR");


        //result.setValue("0");

        layout.addComponent(text);
        layout.addComponent(formLayout1);
        layout.addComponent(formLayout2);
        layout.addComponent(formLayout3);
        layout.addComponent(formLayout);
        layout.addComponent(formLayout4);
        layout.addComponent(bottom);

        text.addComponents(result);

        formLayout.addComponent(addButton);
        formLayout.addComponents(button0);
        formLayout.addComponent(minusButton);

        formLayout1.addComponents(button1);
        formLayout1.addComponents(button2);
        formLayout1.addComponents(button3);
        formLayout2.addComponents(button4);
        formLayout2.addComponents(button5);
        formLayout2.addComponents(button6);
        formLayout3.addComponents(button7);
        formLayout3.addComponents(button8);
        formLayout3.addComponents(button9);
        formLayout4.addComponents(divButton);
        formLayout4.addComponents(equalButton);
        formLayout4.addComponents(mulButton);
        bottom.addComponents(clear);

        clear.addStyleName(ValoTheme.BUTTON_PRIMARY);



        button0.addClickListener(clickEvent ->
        {
            result.setValue(result.getValue() + "0");
        });
        button1.addClickListener(clickEvent ->
        {
            result.setValue(result.getValue() + "1");
        });
        button2.addClickListener(clickEvent ->
        {
            result.setValue(result.getValue() + "2");
        });
        button3.addClickListener(clickEvent ->
        {
            result.setValue(result.getValue() + "3");
        });
        button4.addClickListener(clickEvent ->
        {
            result.setValue(result.getValue() + "4");
        });
        button5.addClickListener(clickEvent ->
        {
            result.setValue(result.getValue() + "5");
        });
        button6.addClickListener(clickEvent ->
        {
            result.setValue(result.getValue() + "6");
        });
        button7.addClickListener(clickEvent ->
        {
            result.setValue(result.getValue() + "7");
        });
        button8.addClickListener(clickEvent ->
        {
            result.setValue(result.getValue() + "8");
        });
        button9.addClickListener(clickEvent ->
        {
            result.setValue(result.getValue() + "9");
        });

        addButton.addClickListener(click -> {
            plus = true;
            a = Integer.parseInt(result.getValue());
            result.setValue(null);
        });

        minusButton.addClickListener(click -> {
            minus = true;
            a = Integer.parseInt(result.getValue());
            result.setValue(null);
        });
        divButton.addClickListener(clickEvent -> {
            div = true;
            a = Integer.parseInt(result.getValue());
            result.setValue(null);
        });
        mulButton.addClickListener(clickEvent -> {
            mul = true;
            a = Integer.parseInt(result.getValue());
            result.setValue(null);
        });
        equalButton.addClickListener(clickEvent -> {
            b = Integer.parseInt(result.getValue());

            if (minus == true) {
                result.setValue(Integer.toString(a - b));
                minus = false;
            }
            if (plus == true) {
                result.setValue(Integer.toString(a + b));
                plus = false;
            }
            if (mul == true) {
                result.setValue(Integer.toString(a * b));
                mul = false;
            }
            if (div == true) {
                result.setValue(Integer.toString(a / b));
                div = false;
            }
        });
        clear.addClickListener(clickEvent -> {
            a = 0;
            b = 0;
            result.setValue(null);
        });
    }
    public VerticalLayout getLayout()
    {
        return layout;
    }
    public String getResult()
    {
        String res = result.getValue();
        return  res;
    }
}