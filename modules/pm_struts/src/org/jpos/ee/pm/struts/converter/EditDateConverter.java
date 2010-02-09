/*
 * jPOS Presentation Manager [http://jpospm.blogspot.com]
 * Copyright (C) 2010 Jeronimo Paoletti [jeronimo.paoletti@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpos.ee.pm.struts.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntityInstanceWrapper;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.Operation;

/**Converter for date.<br>
 * <pre>
 * {@code
 * <converter class="org.jpos.ee.pm.converter.EditDateConverter">
 *     <operationId>edit (or add)</operationId>
 *     <properties>
 *         <property name="format" value="dd/MM/yyyy" />
 *     <properties>
 * </converter>
 * }
 * </pre>
 * @author J.Paoletti jeronimo.paoletti@gmail.com
 * */
public class EditDateConverter extends StrutsEditConverter {

    public Object build(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, Object value) throws ConverterException {
    	try {
            if (value != null)
                return getDateFormat().parse ((String)value);
        } catch (ParseException e) {
            //PMLogger.error(e);
        }
        return null;
	}
	
    public String visualize(Entity entity, Field field, Operation operation,
			EntityInstanceWrapper einstance, String extra) throws ConverterException {
    	Date o = (Date) getValue(einstance.getInstance(), field);
    	try{
    		return super.visualize("date_converter.jsp?value="+getDateFormat().format(o));
    	}catch (Exception e) {
    		return super.visualize("date_converter.jsp?value=");
		}
	}
    
    public DateFormat getDateFormat() {
        DateFormat df = new SimpleDateFormat (getConfig("format", "MM/dd/yyyy"));
        return df;
    }
}