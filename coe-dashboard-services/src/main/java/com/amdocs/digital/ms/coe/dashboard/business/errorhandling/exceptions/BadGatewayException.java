package com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;

@SuppressWarnings("serial")
public class BadGatewayException extends AbstractApplicationException {

    public static final String SERVICE_NAME = "SERVICE_NAME";
    public static final String GATEWAY_CODE = "GATEWAY_CODE";
    public static final String GATEWAY_STATUS = "GATEWAY_STATUS";
    public static final String GATEWAY_ERR_MSG = "GATEWAY_ERR_MSG";

    public BadGatewayException(final String serviceName, final String gatewayCode, final String gatewayErrMsg,
            final HttpStatus gatewayStatus, final Throwable cause, final IMessages messages) {

        super(cause, messages.getMessage(ErrorCodes.BAD_GATEWAY_EXCEPTION_MSG, serviceName, gatewayErrMsg),
                ErrorCodes.BAD_GATEWAY_EXCEPTION_MSG,
                initMap(serviceName, gatewayCode, gatewayErrMsg, gatewayStatus));
    }

    private static Map<String, Object> initMap(final String serviceName, final String gatewayCode, final String gatewayErrMsg,
            final HttpStatus gatewayStatus) {
		final Map<String, Object> ret = new HashMap<>();
		ret.put(SERVICE_NAME, serviceName);
		ret.put(GATEWAY_CODE, gatewayCode);
		ret.put(GATEWAY_ERR_MSG, gatewayErrMsg);
		ret.put(GATEWAY_STATUS, gatewayStatus);
		return ret;
	}

    @Override
    public String getErrorCode() {
        String errorCode = (String) getParameters().get(GATEWAY_CODE);
        return errorCode != null ? errorCode : ErrorCodes.BAD_GATEWAY_EXCEPTION_MSG;
    }

}
