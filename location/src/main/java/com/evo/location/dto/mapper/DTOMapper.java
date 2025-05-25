package com.evo.location.dto.mapper;

import java.util.List;

public interface DTOMapper<D, E> {
    D entityToDTO(E entity);

    List<D> entityListToDTOList(List<E> entityList);
}
