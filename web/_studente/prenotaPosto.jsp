<%--
  Created by IntelliJ IDEA.
  User: spide
  Date: 09/01/2020
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal fade" id="prenotazione">
    <form id="prenotazione_form" name="prenota_posto">
        <input name="aula" id="id_aula" type="hidden"/>
        <input name="edificio" id="id_edificio" type="hidden" value="<%=strEdificio%>"/>
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <!-- info Header -->
                <div class="modal-header">
                    <h4 id="nome_aula" class="modal-title">#Name#</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <!-- info body -->
                <div class="modal-body">
                    <div class="form">
                        <div id="exercitationrormessage"></div>
                            <div class="form-group">
                                <b>Posti Totali:</b> <label id="posti_aula"></label>
                                <span class="input"></span>
                            </div>
                            <div class="form-group">
                                <b>Posti Disponibili:</b> <label id="posti_disponibili"></label>
                                <span class="input"></span>
                            </div>
                            <div class="form-group row">
                                <label class="col-2"><b>Servizi: </b></label>
                                <div class="col-3">
                                    <span class="icon col-1" style="color: black; font-size: medium">
                                        <i id="servizi_prese" class="fa fa-bolt" data-toggle="tooltip"
                                           title="L'aula è dotata di prese sul banco"></i>
                                    </span>
                                    <span class="icon col-1" style="color: black; font-size: medium">
                                        <i id="servizi_computer" class="fa fa-laptop" data-toggle="tooltip"
                                           title="L'aula è dotata di postazioni pc"></i>
                                    </span>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-5 col-form-label" for="durata_prenotazione">Durata
                                    prenotazione</label>
                                <div class="col-4">
                                    <input name="durata" id="durata_prenotazione" class="form-control" type="number"
                                           min="30" max="300" step="30"/>
                                </div>
                                <label class="col-3 col-form-label">minuti</label>
                            </div>
                    </div>
                </div>
                <!-- info footer -->
                <div class="modal-footer">
                    <button id="submit_button" type="button" class="btn btn-primary" data-dismiss="modal">Prenota</button>
                    <button type="button" class="btn btn-outline-primary" data-dismiss="modal">Chiudi</button>
                </div>
            </div>
        </div>
    </form>
</div>