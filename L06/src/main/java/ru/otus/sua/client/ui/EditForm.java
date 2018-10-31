package ru.otus.sua.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import ru.otus.sua.client.CrudService;
import ru.otus.sua.client.CrudServiceAsync;

public class EditForm extends DialogBox {

    private static EditFormUiBinder uiBinder = GWT.create(EditFormUiBinder.class);
    @UiField(provided = true)
    final EditFormResources res;
    private final CrudServiceAsync crudService = GWT.create(CrudService.class);
    @UiField
    DialogBox editFormDialogBox;
    @UiField
    TextBox employeIdLabel;
    @UiField
    TextBox departmentIdLabel;
    @UiField
    TextBox appointmentIdLabel;
    @UiField
    TextBox credentialsIdLabel;
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
    private boolean isCreation = false;

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
        isCreation = true;
        clearEditForm();
        showEditForm("Новый работник");
    }

    private void editButtonClick(ClickEvent event) {
        isCreation = false;
        clearEditForm();
        if (employesListFeeder.isSelected()) {
            showEditForm("Изменить работника", employesListFeeder.getSelectedEmploye());
        } else {
            Window.alert("Ничего не выбрано.");
        }
    }

    private void deleteButtonClick(ClickEvent event) {
        if (employesListFeeder.isSelected()) {
            //errorLabel.setText("обработка"); // TODO on delete EditForm is not showing
            if (employesListFeeder.getSelectedEmploye().getFullName().equals("ADMIN")) {
                Window.alert("Администратора удалять не стоит..");
            } else {
                crudService.deleteEmploye(convertSelectedEntryToJson(), new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //errorLabel.setText(caught.getLocalizedMessage());
                        Window.alert("Удаление неудачно: " + caught.getLocalizedMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        //errorLabel.setText("Удаление завершено:" + result);
                        Window.alert("Удаление завершено: " + result);
                        employesListFeeder.FillEmployeePanel();
                    }
                });
            }
        } else {
            Window.alert("Ничего не выбрано.");
        }
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
        employeIdLabel.setText("");
        appointmentIdLabel.setText("");
        departmentIdLabel.setText("");
        errorLabel.setText("");
        credentialsIdLabel.setText("");
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

    private String convertEditFormToJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("employeId", new JSONString(employeIdLabel.getText()));
        jsonObject.put("departmentId", new JSONString(departmentIdLabel.getText()));
        jsonObject.put("appointmentId", new JSONString(appointmentIdLabel.getText()));
        jsonObject.put("credentialsId", new JSONString(credentialsIdLabel.getText()));
        jsonObject.put("fullName", new JSONString(fullNameTextBox.getText()));
        jsonObject.put("city", new JSONString(cityListBox.getSelectedItemText()));
        jsonObject.put("department", new JSONString(departmentListBox.getSelectedItemText()));
        jsonObject.put("appointment", new JSONString(appointmentListBox.getSelectedItemText()));
        jsonObject.put("salary", new JSONString(salaryTextBox.getText()));
        jsonObject.put("login", new JSONString(loginTextBox.getText()));
        jsonObject.put("passhash", new JSONString(passwordTextBox.getText()));
        return jsonObject.toString();
    }

    private String convertSelectedEntryToJson() {
        if (employesListFeeder.isSelected()) {
            EmployesListFeeder.EmployeEntry entry = employesListFeeder.getSelectedEmploye();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("employeId", new JSONString("" + entry.getId()));
            jsonObject.put("departmentId", new JSONString("" + entry.getDepartmentId()));
            jsonObject.put("appointmentId", new JSONString("" + entry.getAppointmentId()));
            jsonObject.put("credentialsId", new JSONString("" + entry.getCredentialsId()));
            jsonObject.put("fullName", new JSONString(entry.getFullName()));
            jsonObject.put("city", new JSONString(entry.getCity()));
            jsonObject.put("department", new JSONString(entry.getDepartment()));
            jsonObject.put("appointment", new JSONString(entry.getAppointment()));
            jsonObject.put("salary", new JSONString(entry.getSalary()));
            jsonObject.put("login", new JSONString(entry.getLogin()));
            jsonObject.put("passhash", new JSONString(entry.getPasshash()));
            return jsonObject.toString();
        } else {
            return "";
        }
    }

    private void saveEmployee() {
        if (isCreation) {
            crudService.createEmploye(convertEditFormToJson(), new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    //errorLabel.setText(caught.getLocalizedMessage());
                    Window.alert("Создание неудачно: " + caught.getLocalizedMessage());
                }

                @Override
                public void onSuccess(String result) {
                    //errorLabel.setText("Удаление завершено:" + result);
                    Window.alert("Создание завершено: " + result);
                    employesListFeeder.FillEmployeePanel();
                }
            });
        } else {
            crudService.editEmploye(convertEditFormToJson(), new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    //errorLabel.setText(caught.getLocalizedMessage());
                    Window.alert("Изменение неудачно: " + caught.getLocalizedMessage());
                }

                @Override
                public void onSuccess(String result) {
                    //errorLabel.setText("Удаление завершено:" + result);
                    Window.alert("Изменение завершено: " + result);
                    employesListFeeder.FillEmployeePanel();
                }
            });
        }
    }

    public IsWidget getThreePanel() {
        return this.threeButtonPanel;
    }

    @UiTemplate("EditForm.ui.xml")
    interface EditFormUiBinder extends UiBinder<Widget, EditForm> {
    }
}
