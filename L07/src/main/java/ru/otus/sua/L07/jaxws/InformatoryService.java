package ru.otus.sua.L07.jaxws;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.sua.L07.entities.helpers.EmployeEntityDAO;

import javax.jws.WebMethod;
import javax.jws.WebService;

@NoArgsConstructor
@Slf4j
@WebService(endpointInterface = "ru.otus.sua.L07.jaxws.Informatory")
public class InformatoryService implements Informatory {

    @Override
    public double getAvgSalary() {
        log.info("arrived jax-ws request for avgSalary");
        return EmployeEntityDAO.getAvgSalary();
    }
}
