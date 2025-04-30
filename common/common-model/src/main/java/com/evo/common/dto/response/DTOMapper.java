package com.evo.common.dto.response;

import java.util.List;

public interface DTOMapper<D, M, E> {
    D domainModelToDTO(M model);

    D entityToDTO(E entity);

    List<D> entitiesToDTOs(List<E> entities);

    M dtoToDomainModel(D dto);

    List<D> domainModelsToDTOs(List<M> models);
}
