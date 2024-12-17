package com.amdocs.digital.ms.coe.dashboard.tests.autolog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.amdocs.digital.ms.coe.dashboard.autolog.JacksonSerializer;
import com.amdocs.msbase.privatedata.annotations.NPI;
import com.amdocs.msbase.privatedata.annotations.PCI;
import com.amdocs.msbase.privatedata.annotations.PII;
import com.amdocs.msbase.privatedata.annotations.SPI;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class JacksonSerializerTest {

    @Test
    public void jacksonSerializerTest() throws JsonProcessingException {
        Person person = new Person();
        person.setId("1");
        person.setFirstName("John");
        person.setFirstName("Smith");
        person.setPhoneNumber("505-555609");
        person.setManagerId("1");
        person.setManager(person);
        ObjectMapper serializer = JacksonSerializer.getSerializer();
        String json = serializer.writeValueAsString(person);
        assertEquals("{\"id\":\"1\",\"managerId\":\"1\"}", json);
    }

    public static class Person {
        private String id;
        @NPI
        private String firstName;
        @PCI
        private String lastName;
        @PII
        private String phoneNumber;
        @SPI
        private String email;
        private String managerId;
        @JsonIgnore
        private Person manager;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getManagerId() {
            return managerId;
        }

        public void setManagerId(String managerId) {
            this.managerId = managerId;
        }

        public Person getManager() {
            return manager;
        }

        public void setManager(Person manager) {
            this.manager = manager;
        }

    }
}
