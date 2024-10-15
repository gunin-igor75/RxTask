package com.github.gunin_igor75.rxtask.presentation.mappers

import com.github.gunin_igor75.rxtask.data.dto.DataCasesDto
import com.github.gunin_igor75.rxtask.presentation.model.UiCase


fun DataCasesDto.dataCaseDtoToCase(): UiCase {
    return UiCase(
        id = id,
        title = title,
    )
}

fun List<DataCasesDto>.dataDtoCasesToCases() =
    map { it.dataCaseDtoToCase() }


