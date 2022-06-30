package com.alvin.java.core.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@AnnoTest("alvinAnno")
@Inherited
@Retention( value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE_USE,ElementType.PACKAGE,ElementType.FIELD,
        ElementType.TYPE_PARAMETER,ElementType.CONSTRUCTOR,ElementType.LOCAL_VARIABLE})
@Documented
public @interface FullAnnoTest {

    String value() default  "FullAnnoTest";

}
