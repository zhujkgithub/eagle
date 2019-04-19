package com.soaring.eagle.admin.freemarker;

import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-25
 * Time: 21:46
 * Description:
 */
@Component("sys_dict_list")
public class SysDictListController implements TemplateDirectiveModel {

    private static final String DATA_TYPE = "type";
    private static final String DATA_VALUE = "value";
    //是否默认无选项，在编辑页面 必填项中有些需要设置为无
    private static final String DEFAULT = "default";

//    @Resource
//    private ISysDictService sysDictService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                        TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        Writer out = environment.getOut();
        TemplateModel typeModel = (TemplateModel) map.get(DATA_TYPE);
        TemplateModel valueModel = (TemplateModel) map.get(DATA_VALUE);
        TemplateModel defaultModel = (TemplateModel) map.get(DEFAULT);
        String defaultMessage = "<option value=\"\">请选择</option>";
        if(defaultModel != null){
            Boolean defaultValue = ((TemplateBooleanModel) defaultModel).getAsBoolean();
            if(!defaultValue){
                defaultMessage = "";
            }
        }

        if (null != typeModel && null != valueModel) {
            // 获取参数中的字典ID
            String type = ((TemplateScalarModel) typeModel).getAsString();
            String value = ((TemplateScalarModel) valueModel).getAsString();

//            SysDict dict = new SysDict();
//            dict.setType(type);
//            dict.setDeleted(0);
            StringBuilder result = new StringBuilder(defaultMessage);
//            ResponseModel resultModel = sysDictService.listByType(dict);
//            if (null != resultModel && "0".equals(resultModel.getSuccess())) {
//                List<SysDict> sysDictList = FastJsonHelper.beanlistToeanList(resultModel.getData(), SysDict.class);
//                for (SysDict aSysDictList : sysDictList) {
//                    result.append("<option ");
//                    if(StringUtils.isNotBlank(value) && Integer.parseInt(value) == aSysDictList.getValue()){
//                        result.append("selected");
//                    }
//                    result.append(" value=").append(aSysDictList.getValue()).append(" >").append(aSysDictList.getLabel()).append("</option>");
//                }
//
//            }
            out.write(result.toString());

            if (templateDirectiveBody != null) {
                templateDirectiveBody.render(environment.getOut());
            } else {
                throw new RuntimeException("标签内部至少要加一个空格");
            }
        }
    }

}
