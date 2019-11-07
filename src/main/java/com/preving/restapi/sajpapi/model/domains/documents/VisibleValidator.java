package com.preving.restapi.sajpapi.model.domains.documents;





import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class VisibleValidator implements ConstraintValidator<VisibleCondition, Document > {

    private String required;
    private String message;
    @Override
    public void initialize(VisibleCondition requiredIfChecked) {
        required = requiredIfChecked.required();
        message=requiredIfChecked.message();
    }

    @Override
    public boolean isValid(Document documento, ConstraintValidatorContext context) {


        if (documento.isVisibleExtranet()) {
            if(documento.getDescription()==null || documento.getDescription().equals("")){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addNode(required).addConstraintViolation();
                return false;
            }else{
                return true;
            }


        }

        return true;
    }




}
