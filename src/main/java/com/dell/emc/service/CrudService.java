package com.dell.emc.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<E> {

	E save(E entity);

	Optional<E> getById(Long id); // load one, lazy loading

	List<E> getAll();

	void delete(Long id);
}
