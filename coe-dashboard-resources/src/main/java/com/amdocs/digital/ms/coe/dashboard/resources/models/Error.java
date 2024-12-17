/**
* Default Error created when there's no Error or ErrorResponse
* definition in the swagger api.
*/

package com.amdocs.digital.ms.coe.dashboard.resources.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;

@ApiModel(
    description = "Error response. This object is used when an API throws an error, typically with a HTTP error response codes 3xx, 4xx, and 5xx"
)
@JsonTypeInfo(
    use = Id.NAME,
    include = As.PROPERTY,
    property = "@type",
    defaultImpl = Error.class,
    visible = true
)

public class Error {
    @JsonProperty("code")
    private String code = null;
    @JsonProperty("message")
    private String message = null;
    @JsonProperty("traceId")
    private String traceId = null;

    public Error code(String code) {
        this.code = code;
        return this;
    }

    @ApiModelProperty(
        required = true,
        value = "Error code relevant to an application, defined in the API or in a common list"
    )
    
    @NotNull
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    @ApiModelProperty("Additional information about the error  and corrective actions related to the error that can be displayed to a user")
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @ApiModelProperty("The unique tracker ID used to facilitate troubleshooting")
    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

}