/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2010 Alejandro P. Revilla
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
package org.jpos.ee.pm.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class DBEntityFilter extends EntityFilter {

    private List<Criterion> filters;

    public DBEntityFilter() {
        super();
        this.setFilters(new ArrayList<Criterion>());
    }

    @Override
    public void clear() {
        getFilters().clear();
    }

    @Override
    public void process(Entity entity) {
        debug("Processing filter");
        for (Entry<String, List<Object>> entry : getFilterValues().entrySet()) {
            final Field field = entity.getFieldById(entry.getKey());
            final List<Object> values = entry.getValue();
            if (values.get(0) != null) {
                Criterion c = getCompareCriterion(field.getProperty(), values);
                if (c != null) {
                    getFilters().add(c);
                }
            }
        }
        debug("Resulting filters: " + getFilters());
    }

    protected Criterion getCompareCriterion(String fid, List<Object> values) {
        Object value_0 = values.get(0);
        switch (getFilterOperation(fid)) {
            case LIKE:
                if (value_0 instanceof String) {
                    return Restrictions.ilike(fid, "%" + value_0 + "%");
                } else {
                    return Restrictions.eq(fid, value_0);
                }
            case GE:
                return Restrictions.ge(fid, value_0);
            case GT:
                return Restrictions.gt(fid, value_0);
            case LE:
                return Restrictions.le(fid, value_0);
            case LT:
                return Restrictions.lt(fid, value_0);
            case NE:
                return Restrictions.not(Restrictions.eq(fid, value_0));
            default:
                return Restrictions.eq(fid, value_0);
        }
    }

    public final void setFilters(List<Criterion> filters) {
        this.filters = filters;
    }

    public List<Criterion> getFilters() {
        return filters;
    }
}
