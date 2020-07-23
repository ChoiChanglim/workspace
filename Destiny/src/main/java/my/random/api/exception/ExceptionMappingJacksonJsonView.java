package my.random.api.exception;

import java.util.Map;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class ExceptionMappingJacksonJsonView extends MappingJackson2JsonView{
    @Override
    protected Object filterModel(Map<String, Object> model){
        Object result = super.filterModel(model);
        if(!(result instanceof Map)){
            return result;
        }

        Map map = (Map) result;
        if(map.containsKey("info")){
            map.remove("info");
        }
        if(map.size() == 1){
            return map.values().toArray()[0];
        }
        return map;
    }
}