package ru.otus.sua.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class EditForm extends DialogBox {

    private static EditFormUiBinder uiBinder = GWT.create(EditFormUiBinder.class);
    @UiField(provided = true)
    final EditFormResources res;
    @UiField
    DialogBox editFormDialogBox;
    @UiField
    Label employeIdLabel;
    @UiField
    Label departmentIdLabel;
    @UiField
    Label appointmentIdLabel;
    @UiField
    Label credentialsIdLabel;
    @UiField
    Label errorLabel;
    @UiField
    TextBox fullNameTextBox;
    @UiField
    ListBox cityListBox;
    @UiField
    ListBox departmentListBox;
    @UiField
    ListBox appointmentListBox;
    @UiField
    TextBox loginTextBox;
    @UiField
    PasswordTextBox passwordTextBox;
    @UiField
    Button clearButton;
    @UiField
    Button cancelButton;
    @UiField
    Button okButton;
    @UiField
    TextBox salaryTextBox;
    private HorizontalPanel threeButtonPanel = new HorizontalPanel();
    private DataGrid<EmployesListFeeder.EmployeEntry> dataGrid;
    private Button newButton = new Button();
    private Button deleteButton = new Button();
    private Button editButton = new Button();
    private EmployesListFeeder employesListFeeder;

    public EditForm(DataGrid<EmployesListFeeder.EmployeEntry> dataGrid,
                    EmployesListFeeder employesListFeeder) {
        this.res = GWT.create(EditFormResources.class);
        res.style().ensureInjected();
        setWidget(uiBinder.createAndBindUi(this));

        this.employesListFeeder = employesListFeeder;
        this.dataGrid = dataGrid;
        addThreeButtons();
    }

    @UiHandler("clearButton")
    public void clearButtonClick(ClickEvent event) {
        clearEditForm();
    }

    @UiHandler("cancelButton")
    public void cancelButtonClick(ClickEvent event) {
        hideEditForm();
    }

    @UiHandler("okButton")
    public void okButtonClick(ClickEvent event) {
        hideEditForm();
        saveEmployee();
    }

    private void newButtonClick(ClickEvent event) {
        clearEditForm();
        showEditForm("Новый работник");
    }

    private void editButtonClick(ClickEvent event) {
        if (employesListFeeder.isSelected()) {
            showEditForm("Изменить работника", employesListFeeder.getSelectedEmploye());
        }
    }

    private void deleteButtonClick(ClickEvent event) {
        Window.alert("del");
    }

    private void showEditForm(String s) {
        editFormDialogBox.setText(s);
        editFormDialogBox.setVisible(true);
        editFormDialogBox.center();
        editFormDialogBox.show();
    }

    private void showEditForm(String s, EmployesListFeeder.EmployeEntry selected) {
        employeIdLabel.setText(selected.getId());
        departmentIdLabel.setText(selected.getDepartmentId());
        appointmentIdLabel.setText(selected.getAppointmentId());
        credentialsIdLabel.setText(selected.getCredentialsId());
        errorLabel.setText("---");
        fullNameTextBox.setText(selected.getFullName());
        cityListBox.setItemText(0, selected.getCity());
        departmentListBox.setItemText(0, selected.getDepartment());
        appointmentListBox.setItemText(0, selected.getAppointment());
        salaryTextBox.setText(selected.getSalary());
        loginTextBox.setText(selected.getLogin());
        passwordTextBox.setText(selected.getPasshash());
        clearButton.setEnabled(false);
        showEditForm(s);
    }

    private void hideEditForm() {
        clearButton.setEnabled(true);
        editFormDialogBox.setText("");
        editFormDialogBox.hide();
        editFormDialogBox.setVisible(false);
    }

    private void clearEditForm() {
        employeIdLabel.setText("---");
        appointmentIdLabel.setText("---");
        departmentIdLabel.setText("---");
        errorLabel.setText("---");
        credentialsIdLabel.setText("---");
        loginTextBox.setText("");
        passwordTextBox.setText("");
        fullNameTextBox.setText("");
        salaryTextBox.setText("");
        cityListBox.setSelectedIndex(0);
        cityListBox.setItemText(0, "");
        departmentListBox.setSelectedIndex(0);
        departmentListBox.setItemText(0, "");
        appointmentListBox.setSelectedIndex(0);
        appointmentListBox.setItemText(0, "");
    }

    private void addThreeButtons() {
        newButton.setText("Создать");
        editButton.setText("Изменить");
        deleteButton.setText("Удалить");
        newButton.setStyleName(res.style().buttonLinkReleased());
        editButton.setStyleName(res.style().buttonLinkReleased());
        deleteButton.setStyleName(res.style().buttonLinkReleased());
        newButton.addClickHandler(this::newButtonClick);
        deleteButton.addClickHandler(this::deleteButtonClick);
        editButton.addClickHandler(this::editButtonClick);
        threeButtonPanel.add(deleteButton);
        threeButtonPanel.add(editButton);
        threeButtonPanel.add(newButton);
    }

    private void saveEmployee() {
    }

    public IsWidget getThreePanel() {
        return this.threeButtonPanel;
    }

    @UiTemplate("EditForm.ui.xml")
    interface EditFormUiBinder extends UiBinder<Widget, EditForm> {
    }
}
