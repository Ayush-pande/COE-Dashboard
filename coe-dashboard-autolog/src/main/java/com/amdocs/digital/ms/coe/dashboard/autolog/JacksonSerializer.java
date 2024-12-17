package com.amdocs.digital.ms.coe.dashboard.autolog;

import com.amdocs.msbase.privatedata.annotations.NPI;
import com.amdocs.msbase.privatedata.annotations.PCI;
import com.amdocs.msbase.privatedata.annotations.SPI;
import com.amdocs.msbase.privatedata.annotations.PII;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.lang.reflect.Field;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class JacksonSerializer {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static boolean isInit = false;

	public static class JsonIgnoreIntrospector extends JacksonAnnotationIntrospector {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean hasIgnoreMarker(final AnnotatedMember m) {
			return m.getRawType() == HttpServletRequest.class || m.hasAnnotation(JsonIgnore.class) || isPrivate(m);
		}

        private static boolean isPrivate(AnnotatedMember m) {
            if (m instanceof AnnotatedField) {
                return m.hasAnnotation(NPI.class) || m.hasAnnotation(PCI.class) || m.hasAnnotation(PII.class)
                        || m.hasAnnotation(SPI.class);
            } else if (m instanceof AnnotatedMethod method) {
                String fieldName = getFieldName(method);
                if (fieldName != null) {
                    try {
                        Field f = method.getDeclaringClass().getDeclaredField(fieldName);
                        return f.isAnnotationPresent(NPI.class) || f.isAnnotationPresent(PCI.class)
                                || f.isAnnotationPresent(PII.class) || f.isAnnotationPresent(SPI.class);
                    } catch (Exception e) { // NOSONAR
                        // ignore and return false below
                    }
                }
            }
            return false;
        }

        private static String getFieldName(AnnotatedMethod method) {
            String name = method.getName();
            if (name.startsWith("get")) {
                name = name.substring(3);
            } else if (name.startsWith("is")) {
                name = name.substring(2);
            } else {
                return null;
            }
            return StringUtils.uncapitalize(name);
        }

	}

    private JacksonSerializer() {}

    @SuppressWarnings("squid:CallToDeprecatedMethod")
	private static void init() {
		if (!isInit) {
			objectMapper.setDefaultPropertyInclusion(Include.NON_EMPTY);
			objectMapper.disable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
			objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

			objectMapper.registerModule(new JavaTimeModule());
	        objectMapper.setDateFormat(new ISO8601DateFormat());
			objectMapper.setAnnotationIntrospector(new JsonIgnoreIntrospector());

			isInit = true;
		}
	}

	public static ObjectMapper getSerializer() {
		init();
		return objectMapper;
	}

}