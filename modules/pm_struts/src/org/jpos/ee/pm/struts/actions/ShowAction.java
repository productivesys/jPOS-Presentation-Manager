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
package org.jpos.ee.pm.struts.actions;

/**Action for show operation. */
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.struts.PMStrutsContext;

public class ShowAction extends RowActionSupport {

    /**Makes the operation generate an audithory entry*/
    protected boolean isAudited() {    return false; }
    
    protected boolean prepare(PMStrutsContext ctx) throws PMException {
        super.prepare(ctx);
        if(ctx.getRequest().getParameter(FINISH)==null){
            /*This point limite anidation of weak entities.*/
            if(!ctx.isWeak()){
                clearModifiedOwnerCollection(ctx);
            }
        }
        if(ctx.getSelected() == null){
            throw new PMException("pm.instance.not.found");
        }
        return true;
    }
    
    protected void doExecute(PMStrutsContext ctx) throws PMException {}
}