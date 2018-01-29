package com.bambinocare.model.service;

import java.util.Date;
import java.util.List;
import com.bambinocare.model.entity.NannyEntity;

public interface NannyService {
	List<NannyEntity> findAllNannies();
	NannyEntity findByNannyId(Integer nannyId);
	NannyEntity createNanny(NannyEntity nanny);
	NannyEntity editNanny(NannyEntity nanny);
	void removeNanny(Integer nannyId);
	NannyEntity findByUserEmail(String email);
	NannyEntity saveNanny(NannyEntity nanny);
	boolean isNannyAvailable(Date startDate, Date finalDate, NannyEntity nanny);
}
