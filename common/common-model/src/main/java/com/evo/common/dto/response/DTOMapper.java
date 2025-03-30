package com.evo.common.dto.response;

public interface DTOMapper<D, M> {
    D domainModelToDTO(M model);

    M dtoToDomainModel(D dto);
}
