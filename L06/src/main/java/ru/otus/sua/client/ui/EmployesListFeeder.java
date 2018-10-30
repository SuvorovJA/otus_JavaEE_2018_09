package ru.otus.sua.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import java.util.ArrayList;

public class EmployesListFeeder {

    private final MainResources res;
    private final VerticalPanel panel;
    private final DataGrid<EmployeEntry> grid;
    private ArrayList<EmployeEntry> data;
    private EmployeEntry selectedEmploye;
    private boolean isSelected;

    public EmployesListFeeder(VerticalPanel panel, DataGrid<EmployeEntry> grid, MainResources res) {
        this.res = res;
        this.panel = panel;
        this.grid = grid;
        this.data = new ArrayList<>();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public EmployeEntry getSelectedEmploye() {
        return selectedEmploye;
    }

    public void FillEmployeePanel() {
        String url = GWT.getHostPageBaseURL() + "allemployes";
        JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
        AsyncCallback<Feed> asyncCallback = new AsyncCallback<Feed>() {
            public void onFailure(Throwable throwable) {
                panel.add(new Label("ошибка"));
            }

            public void onSuccess(Feed feed) {
                JsArray<EmployeEntry> entries = feed.getEntries();
                for (int i = 0; i < entries.length(); i++) {
                    EmployeEntry entry = entries.get(i);
                    data.add(entry);
                }
                deferredInitDataGrid();
            }
        };
        jsonp.requestObject(url, asyncCallback);
    }


    private void deferredInitDataGrid() {
        grid.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<EmployeEntry> fullNameColumn = new TextColumn<EmployeEntry>() {
            @Override
            public String getValue(EmployeEntry entry) {
                return entry.getFullName();
            }
        };
        grid.addColumn(fullNameColumn, "Полное Имя");
        TextColumn<EmployeEntry> departmentColumn = new TextColumn<EmployeEntry>() {
            @Override
            public String getValue(EmployeEntry entry) {
                return entry.getDepartment();
            }
        };
        grid.addColumn(departmentColumn, "Отдел");
        TextColumn<EmployeEntry> appointmentColumn = new TextColumn<EmployeEntry>() {
            @Override
            public String getValue(EmployeEntry entry) {
                return entry.getAppointment();
            }
        };
        grid.addColumn(appointmentColumn, "Должность");
        TextColumn<EmployeEntry> salaryColumn = new TextColumn<EmployeEntry>() {
            @Override
            public String getValue(EmployeEntry entry) {
                return entry.getSalary();
            }
        };
        grid.addColumn(salaryColumn, "Зарплата");
        TextColumn<EmployeEntry> cityColumn = new TextColumn<EmployeEntry>() {
            @Override
            public String getValue(EmployeEntry entry) {
                return entry.getCity();
            }
        };
        grid.addColumn(cityColumn, "Город");
        TextColumn<EmployeEntry> loginColumn = new TextColumn<EmployeEntry>() {
            @Override
            public String getValue(EmployeEntry entry) {
                return entry.getLogin();
            }
        };
        grid.addColumn(loginColumn, "Логин");

        final SingleSelectionModel<EmployeEntry> selectionModel = new SingleSelectionModel<>();
        grid.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                EmployeEntry selected = selectionModel.getSelectedObject();
                if (selected != null) {
                    isSelected = true;
                    selectedEmploye = selected;
                } else {
                    isSelected = false;
                }
            }
        });

        grid.setRowCount(data.size(), true);
        grid.setRowData(0, data);
        grid.setWidth("100%");
        grid.setHeight("400px");
        grid.onResize();
    }


    static class Feed extends JavaScriptObject {
        protected Feed() {
        }

        public final native JsArray<EmployeEntry> getEntries() /*-{
            return this;
        }-*/;
    }

    static class EmployeEntry extends JavaScriptObject {

        protected EmployeEntry() {
        }

        public final native String getFullName() /*-{
            return this.fullName;
        }-*/;

        public final native String getSalary() /*-{
            return this.salary.toString();
        }-*/;

        public final native String getCity() /*-{
            return this.city;
        }-*/;

        public final native String getDepartment() /*-{
            return this.department;
        }-*/;

        public final native String getAppointment() /*-{
            return this.appointment;
        }-*/;

        public final native String getLogin() /*-{
            return this.login;
        }-*/;

        public final native String getPasshash() /*-{
            return this.passhash;
        }-*/;

        public final native String getDepartmentId() /*-{
            return this.departmentId;
        }-*/;

        public final native String getAppointmentId() /*-{
            return this.appointmentId;
        }-*/;

        public final native String getId() /*-{
            return this.id;
        }-*/;

        public final native String getCredentialsId() /*-{
            return this.credentialsId;
        }-*/;
    }
}
