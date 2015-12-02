package org.pachnanda.springboot.microservice;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Created by gautamp on 13/10/2015.
 */
public class PropertiesValidator implements Validator {

    final Pattern pattern = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");

   // @Override
    public boolean supports(Class<?> type) {
        return type == AppProperties.class;
    }

   // @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "host", "host.empty");
        ValidationUtils.rejectIfEmpty(errors, "port", "port.empty");
        AppProperties properties = (AppProperties) o;
        if (properties.getHost() != null
                && !this.pattern.matcher(properties.getHost()).matches()) {
            errors.rejectValue("host", "Invalid host");
        }
    }

}