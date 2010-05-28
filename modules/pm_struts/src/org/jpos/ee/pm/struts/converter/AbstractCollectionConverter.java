package org.jpos.ee.pm.struts.converter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.EntitySupport;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.ListFilter;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMLogger;
import org.jpos.ee.pm.struts.PMEntitySupport;
import org.jpos.ee.pm.struts.PMStrutsContext;

/**
 *
 * @author jpaoletti
 */
public abstract class AbstractCollectionConverter extends StrutsEditConverter{

    public void saveList(PMStrutsContext ctx, String eid){
        try {
            ctx.getSession().setAttribute(getTmpName(ctx, eid), getList(ctx));
        } catch (PMException ex) {
            PMLogger.error(ex);
        }
    }

    public static List<?> recoverList(PMStrutsContext ctx, String eid, boolean remove){
        try {
            final List<?> r = (List<?>) ctx.getSession().getAttribute(getTmpName(ctx, eid));
            if(remove) ctx.getSession().removeAttribute(getTmpName(ctx, eid));
            return r;
        } catch (PMException ex) {
            PMLogger.error(ex);
            return null;
        }
    }

    public List<?> getList(PMStrutsContext ctx) throws PMException {
        final String filter = getConfig("filter");
        final String eid = getConfig("entity");

        ListFilter lfilter = null;
        if( filter != null && filter.compareTo("null") != 0 && filter.compareTo("") != 0) {
            lfilter = (ListFilter) EntitySupport.newObjectOf(filter);
        }
        PMEntitySupport es = PMEntitySupport.getInstance();
        Entity e = es.getPmservice().getEntity(eid);
        List<?> list = null;
        if(e==null) throw new ConverterException("Cannot find entity "+eid);
        synchronized (e) {
            ListFilter tmp = e.getListfilter();
            e.setListfilter(lfilter);
            list = e.getList(ctx,null,null,null);
            e.setListfilter(tmp);
        }
        return list;
    }

    public static String getTmpName(PMStrutsContext ctx,  String eid) throws PMException{
        Field field = (Field) ctx.get(PM_FIELD);
        return "tmp_"+ctx.getEntity().getId()+"_"+field.getId();
    }

}