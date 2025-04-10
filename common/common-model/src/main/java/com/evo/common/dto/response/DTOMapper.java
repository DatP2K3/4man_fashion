package com.evo.common.dto.response;

import java.util.List;

public interface DTOMapper<D, M> {
    D domainModelToDTO(M model);

    M dtoToDomainModel(D dto);

    List<D> domainModelsToDTOs(List<M> models);
}
