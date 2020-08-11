package com.jolan.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author jolan80
 * @date 2020-08-09 10:30
 *
 */
public class MyImportSelector implements ImportSelector {
    //自定义逻辑，返回需要的组件,返回值就是要导入容器中的组件全类名
    //AnnotationMetadata:当前标注@Import注解的类的所有注解信息
    //该方法不能返回null，可以返回空数组
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.jolan.bean.Blue", "com.jolan.bean.Yellow"};
    }
}
