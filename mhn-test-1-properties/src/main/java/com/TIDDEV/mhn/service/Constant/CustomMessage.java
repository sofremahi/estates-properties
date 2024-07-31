package com.TIDDEV.mhn.service.Constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum CustomMessage {
    SERVICE_ERROR("Service.Unavailable") ,
    SUCCESSFULLY_OPERATED("OPERATION.SUCCESS") ,
    INVALID_FIELDS("INVALID.INPUT"),
    SAVED_SUCCESSFUL("DATA.SAVED"),
    ERROR_SYSTEM("SYSTEM.ERROR") ,
    NO_CONTENT("NO.DATA.FOUND");
    private final String title ;

}
