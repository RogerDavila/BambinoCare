package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.NannyEntity;

public interface NannyService {
	List<NannyEntity> findAllNannies();
	NannyEntity findNannyByIdNanny(Integer idNanny);
	NannyEntity createNanny(NannyEntity nanny);
	NannyEntity editNanny(NannyEntity nanny);
	void removeNanny(Integer idNanny);
}
