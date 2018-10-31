package ru.otus.sua.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.sua.client.CrudService;
import ru.otus.sua.entityes.EmployeEntity;

import static ru.otus.sua.helpers.EntytiesHelper6.cloneEmployeEntity;
import static ru.otus.sua.helpers.EntytiesHelper6.createEmployeEntityFromJsonWithIds;
import static ru.otus.sua.helpers.JpaHelper6.*;

public class CrudServiceImpl extends RemoteServiceServlet implements CrudService {

    private static Logger log = LoggerFactory.getLogger(CrudServiceImpl.class);

    @Override
    public String deleteEmploye(String json) throws IllegalArgumentException {
        log.info("Delete obj: {}", json);
        // TODO Verifing
        return deleteEmployeEntity(createEmployeEntityFromJsonWithIds(json)) ? "OK" : "FAIL";
    }

    @Override
    public String createEmploye(String json) throws IllegalArgumentException {
        log.info("Create obj: {}", json);
        return saveEmployeEntity(createEmployeEntityFromJsonWithIds(json)) ? "OK" : "FAIL";
    }

    @Override
    public String editEmploye(String json) throws IllegalArgumentException {
        log.info("Edit obj: {}", json);
        EmployeEntity entityExternal = createEmployeEntityFromJsonWithIds(json);
        EmployeEntity entityPersisted = readEmployeById(entityExternal.getId());
        if (entityPersisted == null) {
            return saveEmployeEntity(entityExternal) ? "OK" : "FAIL";
        } else {
            cloneEmployeEntity(entityExternal, entityPersisted);
            return updateEmployeEntity(entityPersisted) ? "OK" : "FAIL";
        }
    }

    @Override
    public String readEmploye(String json) throws IllegalArgumentException {
        String jsonEmploye = "";
        log.info("Read obj: {}", json);
        // TODO nothing now
        return jsonEmploye;
    }


}
