package com.htf.bigdata.invest.indicatormanage.component.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htf.bigdata.invest.indicatormanage.component.exception.CustomException;
import com.htf.bigdata.invest.indicatormanage.component.exception.ServiceException;
import com.htf.bigdata.invest.indicatormanage.component.exception.ValidatorException;
import com.htf.bigdata.invest.indicatormanage.config.code.SystemEnumCodeConfig;
import com.htf.bigdata.invest.indicatormanage.model.response.ErrorResponse;
import com.htf.bigdata.invest.indicatormanage.model.response.Response;

@RestController
@ControllerAdvice
class ControllerInterceptor implements ErrorController {

    private static final Logger logger = LogManager.getLogger(ControllerInterceptor.class);

    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public String getErrorPath(){
        return "";
    }

    /**
     * 404
     * @return
     */
    @RequestMapping("/error")
    public Response errorRequestUrlExceptionHanlder() {
        return new ErrorResponse(SystemEnumCodeConfig.ERROR_REQUEST_URL.getCode(), SystemEnumCodeConfig.ERROR_REQUEST_URL.getMessage());
    }

    /**
     * 默认500
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public Response defaultExceptionHandler(HttpServletRequest request, Exception e) {
        logger.error(request.getRequestURL() + ": " + e.getMessage(),e);
        e.printStackTrace();
        return new ErrorResponse(SystemEnumCodeConfig.ERROR_SERVER.getCode(), e.getMessage());
    }

    /**
     * 错误http method
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response methodNotSupportedExceptionHandler() {
        return new ErrorResponse(SystemEnumCodeConfig.ERROR_METHOD_NOT_SUPPORTED.getCode(), SystemEnumCodeConfig.ERROR_METHOD_NOT_SUPPORTED.getMessage());
    }

    /**
     * 参数校验异常拦截
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {ValidatorException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public Response validatorExceptionHandler(Exception exception) {
        logger.error(exception);
        exception.printStackTrace();

        List<String> errors = new ArrayList<String>();

        if (exception instanceof ValidatorException) {
            errors.add(exception.getMessage());

        } else if (exception instanceof ConstraintViolationException) {
            //参数校验get
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                errors.add(item.getMessage());
            }

        } else if (exception instanceof MethodArgumentNotValidException){
            //参数校验post
            MethodArgumentNotValidException exs = (MethodArgumentNotValidException) exception;
            BindingResult bindingResult = exs.getBindingResult();
            for(FieldError fieldError :bindingResult.getFieldErrors()){
                errors.add(fieldError.getDefaultMessage());
            }
        }
        return new ErrorResponse(SystemEnumCodeConfig.ERROR_VALIDATOR_PARAMETER.getCode(), ArrayUtils.toString(errors));
    }

    /**
     * service异常拦截
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public Response serviceExceptionHandler(ValidatorException e) {
        logger.error(e);
        e.printStackTrace();
        return new ErrorResponse(e.getCode(), e.getMessage());
    }

    /**
     * 自定义异常拦截
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public Response serviceExceptionHandler(CustomException e) {
        logger.error(e);
        e.printStackTrace();
        return new ErrorResponse(e.getCode(), e.getMessage());
    }
}