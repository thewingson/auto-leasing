package kz.almat.service;

import kz.almat.model.Agreement;
import kz.almat.model.Car;

public interface AgreementService extends CommonService<Agreement> {

    Agreement getByCar(Car car);

}
