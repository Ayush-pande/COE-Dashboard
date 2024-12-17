package com.amdocs.digital.ms.coe.dashboard.autolog;

import java.lang.reflect.Method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Development notes:  Use IntelliJ's decompiler to look at .class files to see what aspectj added.  When you are editing
// aspect annotations, it give faster turn-around to do this than to run an augmented program.
// Safari has AspectJ in Action.  Which is useful.
// I think if you try to break the lines up, it will confuse aspectj.  Note that Shift+Alt+Y turns on linewrap in Eclipse
// Eclipse warns that advice is not applied.  I guess it just doesn't see the uses in other projects.

// This says for each type (aka class or interface) that matches the package pattern (btw ".." means is a wild card so
// that this annotation could be copied into different microservices without having to edit) and that doesn't have the
// NotLoggable annotation make an instance of AutoLogAspect.  We want an instance per type so we can have a Logger
// per type.
@Aspect("pertypewithin((!@NotLoggable com.amdocs.digital.ms..couchbase.*) || (!@NotLoggable com.amdocs.digital.ms..resources.delegates.*) || (!@NotLoggable com.amdocs.digital.ms..oracle.*) || (!@NotLoggable com.amdocs.digital.ms..business.services.implementation.*) || (!@NotLoggable com.amdocs.digital.ms..business.domain..implementation.*) || (!@NotLoggable com.amdocs.digital.ms..gateways.implementation.*))")
public class AutoLogAspect {
    // We have an AutoLogAspect per type, so this is like a static field in each matching type.
    @SuppressWarnings("squid:S1312")
    private Logger logger;
    private ObjectMapper objectMapper;

    // This just give something so that the following @After can refer to it.
    @Pointcut("staticinitialization(*)")
    public void staticIniter() {
        // Comment added to avoid sonar warning
    }


    // Initialize logger when target type is loaded
    @After("staticIniter()")
    public void init(JoinPoint.StaticPart joinPointStatic) {
        // I believe by SLF4J rules auto logger will be a child of the class's logger.
        // So enabling debug logging for the class will enable debug autologging
        logger = LoggerFactory.getLogger(joinPointStatic.getSignature().getDeclaringTypeName() + ".autolog");
        objectMapper = JacksonSerializer.getSerializer();
    }

    @Pointcut("execution( !@NotLoggable * com.amdocs.digital.ms..resources.delegates.*.execute(..))")
    private void controller(){
        // Comment added to avoid sonar warning
    }

    @Pointcut( "!execution(* *.lambda*(..)) && !execution(* *.hashCode()) && !execution(* *.equals(Object)) && !execution(* *.toString()) && !execution(* *.get*()) && (execution( !@NotLoggable * com.amdocs.digital.ms..couchbase.*.*(..)) || execution( !@NotLoggable * com.amdocs.digital.ms..oracle.*.*(..)) || execution( !@NotLoggable * com.amdocs.digital.ms..business.services.implementation.*.*(..)) || execution( !@NotLoggable * com.amdocs.digital.ms..business.domain..implementation.*.*(..)) || execution( !@NotLoggable * com.amdocs.digital.ms..gateways.implementation.*.*(..)))")
    @SuppressWarnings("squid:S2325")
    private void selectAll() {
        // Comment added to avoid sonar warning
    }

    @Before("controller()")
    public void controllerBefore(JoinPoint jp) {
        logBefore(jp, true);
    }

    @Before("selectAll()")
    public void selectAllBefore(JoinPoint jp) {
        logBefore(jp, false);
    }

    private void logBefore(JoinPoint jp, boolean isController) {
        if (!amLogging(isController)) {
            return;
        }

        logMessage(jp, isController);
        if (logger.isTraceEnabled() && isController) {
            StringBuilder buf = new StringBuilder(200);
            buf.append(" (").append(writeValueAsString(jp.getArgs())).append(")");
            logger.trace(buf.toString());
        }
    }

    @AfterReturning(pointcut = "controller()", returning = "retVal")
    public void logControllerAfterReturn(JoinPoint jp, Object retVal) {
        logAfterReturn(jp, retVal, true);
    }

    @AfterReturning(pointcut = "selectAll()", returning = "retVal")
    public void logSelectAllAfterReturn(JoinPoint jp, Object retVal) {
        logAfterReturn(jp, retVal, false);
    }

    private void logAfterReturn(JoinPoint jp, Object retVal, boolean isController) {
        if (!amLogging(isController)) {
            return;
        }

        logMessage(jp, isController);
        MethodSignature sig = (MethodSignature) jp.getSignature();
        Method method = sig.getMethod();
        Class<?> retType = method.getReturnType();
        if (!retType.equals(Void.TYPE) && logger.isTraceEnabled() && isController) {
            StringBuilder buf = new StringBuilder(200);
            buf.append(" (").append(writeValueAsString(retVal)).append(")");
            logger.trace(buf.toString());
        }

    }

    private void logMessage(JoinPoint jp, boolean isController) {
        String classAndMethodName = getClassAndMethodName(jp);
        StringBuilder buf = new StringBuilder(60);
        buf.append("AutoLog return: ").append(classAndMethodName);
        String logMessage = buf.toString();
        if (isController) {
            logger.info(logMessage);
        } else {
            logger.debug(logMessage);
        }
    }

    private String writeValueAsString(Object obj) {
        try {
            long start = System.nanoTime();
            String str = objectMapper.writeValueAsString(obj);
            if (logger.isTraceEnabled()) {
                long elapsedTime = System.nanoTime() - start;
                printElapsedTime(elapsedTime, "AutoLogAspect.writeValueAsString");
            }
            return str;
        } catch (Exception e) { // NOSONAR
            // ignore and return "" below
        }
        return "";
    }

    private void printElapsedTime(long elapsedTime, String classAndMethodName) {
        if (logger.isTraceEnabled()) {
            StringBuilder buf = new StringBuilder(60);
            buf.append("AutoLog enter: ").append(classAndMethodName).append(" execution time: ").append(elapsedTime).append(" nanoseconds.");
            logger.trace(buf.toString());
        }
    }

    private String getClassAndMethodName(JoinPoint jp) {
        MethodSignature sig = (MethodSignature) jp.getSignature();
        Method method = sig.getMethod();
        return new StringBuilder(sig.getDeclaringType().getSimpleName()).append(".").append(method.getName()).toString();

    }

    private boolean amLogging(boolean isController) {
        return logger.isTraceEnabled() || logger.isDebugEnabled() || (logger.isInfoEnabled() && isController);
    }
}