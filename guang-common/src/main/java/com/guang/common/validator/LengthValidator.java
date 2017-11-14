package com.guang.common.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * @author huxianguang
 * @create 2017-11-13-下午9:23
 **/
public class LengthValidator extends ValidatorHandler<String> implements Validator<String>{


    private int min;
    private int max;
    private String fieldName;

    public LengthValidator(int min, int max, String fieldName) {
        this.min = min;
        this.max = max;
        this.fieldName = fieldName;
    }

    /**
     * 执行验证
     * <p/>
     * 如果发生错误内部需要调用{@link ValidatorContext#addErrorMsg(String)}方法，也即<code>context.addErrorMsg(String)
     * </code>来添加错误，该错误会被添加到结果存根{@link Result}的错误消息列表中。
     *
     * @param context 验证上下文
     * @param t 待验证对象
     *
     * @return 是否验证通过
     */
    @Override
    public boolean validate(ValidatorContext context, String s) {
        if (null == s || s.length() > max || s.length() < min) {
            /**
             * 需要详细的信息，包括错误消息，错误属性/字段，错误值，
             * 错误码，都可以自己定义，放入错误的方法如下，
             * create()方法传入消息（必填），
             * setErrorCode()方法设置错误码（选填），
             * setField()设置错误字段（选填），
             * setInvalidValue()设置错误值（选填）。
             * 当然这些信息需要result(toComplex())才可以获取到
             */
            context.addError(ValidationError.create(String.format("%s长度必须介于%s~%s之间！",fieldName,min,max))
            .setErrorCode(-1)
            .setField(fieldName)
            .setInvalidValue(s));
            return false;
        }

        return true;
    }
}
