package laojy.dynamicDS;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect  
@Order(1)   
@Component  
public class DataSourceAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());  
    
    //切点  
    @Pointcut("execution(* laojy.mybatis..*.*(..)))")  
    public void aspect() { }  
      
    @Before("aspect()")  
    private void before(JoinPoint point) {  
        Object target = point.getTarget();    
        String method = point.getSignature().getName();  
        Class<?> classz = target.getClass();
        Class<?>[] parameterTypes = ((MethodSignature)point.getSignature()).getParameterTypes();
        try {    
            Method m = classz.getMethod(method, parameterTypes);  
            if (m != null && m.isAnnotationPresent(DS.class)) {    
                DS data = m.getAnnotation(DS.class);    
                DataSourceHolder.set(data.value());   
                logger.info("===============上下文赋值完成:{}",data.value());  
            }    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
          
    }  
    
}
