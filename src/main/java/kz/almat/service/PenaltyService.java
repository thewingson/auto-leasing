package kz.almat.service;

import kz.almat.model.Penalty;

import java.util.List;

public interface PenaltyService extends CommonService<Penalty> {
    List<Penalty> getByDebtor(Long userId);

    void pay(Long id, Long userId);
}
