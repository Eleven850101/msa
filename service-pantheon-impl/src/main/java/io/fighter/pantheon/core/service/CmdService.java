package io.fighter.pantheon.core.service;

import io.fighter.pantheon.CmdDescription;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

/**
 * @author xiasx
 * @create 2019-03-20 17:30
 **/

@Service
public class CmdService {

    private String scanPackage = "io.fighter.pantheon.controller";


    public void initCmd() {
        Reflections reflections = new Reflections(scanPackage);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(RequestMapping.class);

        if(!CollectionUtils.isEmpty(classes)) {
            for(Class<?> clazz : classes) {
                RequestMapping clazzRequestMapping = clazz.getAnnotation(RequestMapping.class);
                String controllerMapingValue = (Objects.nonNull(clazzRequestMapping) && clazzRequestMapping.value().length>0)? clazzRequestMapping.value()[0]: "";
                System.out.println("The Controller Mapping Value is:" + controllerMapingValue);

                Method[] methods = clazz.getMethods();
                for(Method method : methods) {
                    String mappingValue = this.getMappingValue(method);
                    CmdDescription cmd = method.getAnnotation(CmdDescription.class);
                    if(Objects.nonNull(cmd)) {
                        String cmdValue = cmd.value();
                        String cmdLevel = cmd.level();
                        System.out.println("The Method Mapping Value is:" + mappingValue);
                        System.out.println("The Value is:" + cmdValue + ", and The Level is:" + cmdLevel);


                    }
                }
            }
        }
    }


    private String getMappingValue(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            return requestMapping.value().length > 0 ? requestMapping.value()[0] : "";
        }
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (getMapping != null) {
            return getMapping.value().length > 0 ? getMapping.value()[0] : "";
        }
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (postMapping != null) {
            return postMapping.value().length > 0 ? postMapping.value()[0] : "";
        }
        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        if (putMapping != null) {
            return putMapping.value().length > 0 ? putMapping.value()[0] : "";
        }
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        if (deleteMapping != null) {
            return deleteMapping.value().length > 0 ? deleteMapping.value()[0] : "";
        }
        return null;
    }
}
