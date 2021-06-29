/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.ingenio.administracion.util;

import java.util.Collections;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author hacker
 */
@FacesConverter("selectItemsConverter")
public class SelectItemsConverter implements Converter{
    //private static final Logger logger = LoggerFactory.getLogger(SelectItemsConverter.class);
    @Override

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        //System.out.println(" value :::::::" +value);
        if (value.equals("")) {
            return null;  // si el valor viene vacio retorna null  para que el html pida ingreso de valor
        }
        final int index = Integer.parseInt(value);

        if (index == -1) {
            return null;
        }
        

        final List<?> objects = getObjectsFromUISelectItemsComponent(component);
        //logger.trace("String as Object: {}", objects.get(index));
        return objects.get(index);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        final List<?> objects = getObjectsFromUISelectItemsComponent(component);
        //logger.trace("Object value: {}", value);
        //logger.trace("Object as string: {}", objects.indexOf(value));
        return String.valueOf(objects.indexOf(value));
    }
    /**
     * Metodo para
     *
     */
    private List<?> getObjectsFromUISelectItemsComponent(UIComponent component) {
        List<?> objects = Collections.emptyList();
        for (UIComponent child : component.getChildren()) {
            if (child.getClass() == UISelectItems.class) {
                objects = (List<?>) ((UISelectItems) child).getValue();
            }
        }
        //logger.trace("Objects: {}", objects);
        return objects;
    }

}
