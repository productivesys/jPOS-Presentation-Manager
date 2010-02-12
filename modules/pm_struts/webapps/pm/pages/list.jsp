<!--/*
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
 */-->
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/fn.tld" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<%@page import="org.jpos.ee.pm.converter.Converter"%>
<%@page import="org.jpos.ee.pm.core.Entity"%>
<bean:define id="es" 	 			name="es" type="org.jpos.ee.pm.struts.PMEntitySupport" />
<%  
	Entity e = es.putEntityInRequest(request);
	request.setAttribute("operation", e.getOperations().getOperation("list"));
	es.putListInRequest(request);
%>
<bean:define id="fields" 	  name="entity" property="listableFields" type="java.util.List" toScope="request"/>
<bean:define id="operations"  name="PMLIST" property="operations" type="org.jpos.ee.pm.core.Operations" toScope="request"/>
<bean:define id="contents" 	  name="PMLIST" property="contents" type="java.util.List<Object>" toScope="request"/>
<bean:define id="operation"   name="operation" type="org.jpos.ee.pm.core.Operation" toScope="request"/>
<bean:define id="entity"	  name="entity" type="org.jpos.ee.pm.core.Entity" toScope="request"/>
<pm:page title="list">
<script type="text/javascript" src="${es.context_path}/js/jquery.modal.js"></script>
<script type="text/javascript" src="${es.context_path}/js/jquery.center.js"></script>
<div class="boxed">
<html:form method="post" action="/list">
	<input type="hidden" name="finish" value="1" />
	<pm:pmtitle entity="${entity}" operation="${operation}"/>
	<html:errors property="entity" /> 
	<pm:operations labels="true" />
	<script type="text/javascript" charset="utf-8">
		var pmid = "${pmid}";
		var searchable = "${searchable}" == "true";
		var sortable = false;
		var paginable = false;
		var texts = new Array(
				"<bean:message key='list.search.all' />" ,
				"<bean:message key='list.first' />" ,
				"<bean:message key='list.last' />" ,
				"<bean:message key='list.next' />" ,
				"<bean:message key='list.previous' />" ,
				"<bean:message key='list.info' />" ,
				"<bean:message key='list.info.empty' />" ,
				"<bean:message key='list.info.filtered' />" ,
				"<bean:message key='list.length.menu' />" ,
				"<bean:message key='list.processing' />" ,
				"<bean:message key='list.zero.records' />" 
					);
	</script>	
	<script type="text/javascript" charset="utf-8" src="${es.context_path}/js/list.js"></script>

	<div id="list-container">
		<div id="example_wrapper" class="dataTables_wrapper">
			<jsp:include page="list-table.jsp" />
			<jsp:include page="list-pagination.jsp" />
			<jsp:include page="list-sort.jsp" />
		</div>
	</div>

	<logic:present name="entity" property="highlights">
	<style>
	<logic:iterate id="highlight" name="entity" property="highlights.highlights">
		<logic:equal value="instance" name="highlight" property="scope">
		tr.${highlight.field}_${highlight.value} { background-color: ${highlight.color}; }
		</logic:equal>
		<logic:notEqual value="instance" name="highlight" property="scope">
		td.${highlight.field}_${highlight.value} { background-color: ${highlight.color}; }
		</logic:notEqual>
	</logic:iterate>
	</style>	
	</logic:present>
	
	<script type="text/javascript">
	$(function(){
		var myOpen=function(hash){
			$('#sort_page').center();
			hash.w.css('opacity',0.88).show(); 
		}; 

		$("#page").keydown(function(event){
		    if(event.keyCode == 13)
		       this.form.submit();
		});
		$('#operationsort').addClass('jqModal');
		$('#sort_page').jqm({onShow:myOpen});		  
	});
	</script>
	<script type="text/javascript" src="${es.context_path}/js/highlight.js"></script>
</html:form>
</div>
</pm:page>