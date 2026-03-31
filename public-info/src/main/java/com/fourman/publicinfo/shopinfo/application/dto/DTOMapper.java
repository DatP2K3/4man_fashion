package com.fourman.publicinfo.shopinfo.application.dto;

public interface DTOMapper<D, M> {
    D domainModelToDTO(M model);

    M dtoToDomainModel(D dto);
}
