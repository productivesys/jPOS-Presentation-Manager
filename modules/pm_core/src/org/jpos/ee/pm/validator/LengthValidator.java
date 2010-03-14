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
package org.jpos.ee.pm.validator;

import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMMessage;

/**Validate the length of the string.
 * max-length: maximum length of the string
 * max-length-msg: message to show if the name is too long
 * min-length: minimum length of the string
 * min-length-msg:  message to show if the name is too short
 * 
 * @author jpaoletti jeronimo.paoletti@gmail.com */
public class LengthValidator extends ValidatorSupport {
	
	/**The validate method*/
	public ValidationResult validate(PMContext ctx) {
		ValidationResult res = new ValidationResult();
		Field field = (Field)ctx.get(PM_FIELD);
        String fieldvalue = (String) ctx.get(PM_FIELD_VALUE);
        
        res.setSuccessful(true);
        
		Integer len = fieldvalue.length();
        Integer maxl = getInt ("max-length");
		if (len > maxl){
        	res.setSuccessful(false);
        	res.getMessages().add(new PMMessage(field.getId(), get("max-length-msg", ""), len.toString(), maxl.toString()));
        }
        Integer minl = getInt ("min-length");
		if (len < minl){
        	res.setSuccessful(false);
        	res.getMessages().add(new PMMessage(field.getId(), get("min-length-msg", ""), len.toString(), minl.toString()));
        }
        return res;
	}
}