package com.amdocs.digital.ms.coe.dashboard.tests.autolog;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.amdocs.digital.ms.coe.dashboard.autolog.AutoLogAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class AutoLogAspectTest {

	private static final String NAME = "Bob";
	private static final int AGE = 21;    
	private static final String SIGNATURE_TYPE_NAME = "SIGNATURE_TYPE_NAME";
    
	private AutoLogAspect autoLogAspect;

    @Mock
    private org.slf4j.Logger logger;
    @Mock
    private JoinPoint.StaticPart staticPart;
    @Mock
    private MethodSignature signature;
    @Mock
    private JoinPoint joinPoint;
    
    private ArgumentCaptor<String> captor;

    private void createMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    public static class Person
    {
    	private String name;
    	private int age;
		
    	public String getName() {
			return name;
		}
		
    	public void setName(String name) {
			this.name = name;
		}
		
    	public int getAge() {
			return age;
		}
		
    	public void setAge(int age) {
			this.age = age;
		}
    	    	
    }

    public Person createPerson(String name, int age) {
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        return person;
    }

    @Before
    public void setUp() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException {

        createMocks();
        autoLogAspect = new AutoLogAspect();
        when(signature.getDeclaringTypeName()).thenReturn(SIGNATURE_TYPE_NAME);
        when(staticPart.getSignature()).thenReturn(signature);
        autoLogAspect.init(staticPart);
        Method method = this.getClass().getDeclaredMethod("createPerson", String.class, int.class);
        Field field = autoLogAspect.getClass().getDeclaredField("logger");
        field.setAccessible(true);
        field.set(autoLogAspect, logger);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getArgs()).thenReturn(new Object[] {NAME, AGE});
        when(signature.getMethod()).thenReturn(method);
        when(signature.getDeclaringType()).thenReturn(signature.getClass());
        when(logger.isTraceEnabled()).thenReturn(true);
        
        captor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    public void controllerBefore() {
        autoLogAspect.controllerBefore(joinPoint);
        verify(logger, times(2)).trace(captor.capture());
        List<String> msgs = captor.getAllValues();
        assertTrue(msgs.get(0).startsWith("AutoLog enter: AutoLogAspect.writeValueAsString execution time:"));
        assertEquals(" ([\"Bob\",21])", msgs.get(1));
    }

    @Test
    public void selectAllBefore() {
        autoLogAspect.selectAllBefore(joinPoint);
        verify(logger).debug(captor.capture());
        String msg = captor.getValue();
        System.out.println(msg);
        assertTrue(msg.startsWith("AutoLog return:"));
        assertTrue(msg.endsWith("createPerson"));
    }

    @Test
    public void logControllerAfterReturn() {
        autoLogAspect.logControllerAfterReturn(joinPoint, createPerson(NAME, AGE));
        verify(logger, times(2)).trace(captor.capture());
        List<String> msgs = captor.getAllValues();
        assertEquals(" ({\"name\":\"Bob\",\"age\":21})", msgs.get(1));
    }

    @Test
    public void logSelectAllAfterReturn() {
        autoLogAspect.logSelectAllAfterReturn(joinPoint, createPerson(NAME, AGE));
        verify(logger).debug(captor.capture());
        String msg = captor.getValue();
        assertTrue(msg.startsWith("AutoLog return:"));
        assertTrue(msg.endsWith("createPerson"));
    }
}
