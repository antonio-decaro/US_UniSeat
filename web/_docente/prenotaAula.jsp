<%--
  Created by IntelliJ IDEA.
  User: spide
  Date: 09/01/2020
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="prenotazione">
    <form id="prenotazione_form" name="prenota_aula">
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
                        <form action="" method="post" role="form" class="contactForm">
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
                                                <i id="servizi_prese" class="fa fa-bolt" data-toggle="tooltip" title="L'aula è dotata di prese sul banco"></i>
                                            </span>
                                    <span class="icon col-1" style="color: black; font-size: medium">
                                                <i id="servizi_computer" class="fa fa-laptop" data-toggle="tooltip" title="L'aula è dotata di postazioni pc"></i>
                                            </span>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="form-check">
                                    <input name="data" type="radio" id="radioLunedi" value="LUNEDI" checked>
                                    <label for="radioLunedi">Luned&igrave;</label>
                                </div>
                                <div class="form-check">
                                    <input name="data" type="radio" id="radioMartedi" value="MARTEDI">
                                    <label for="radioMartedi">Marted&igrave;</label>
                                </div>
                                <div class="form-check">
                                    <input name="data" type="radio" id="radioMercoledi" value="MERCOLEDI">
                                    <label for="radioMercoledi">Mercoled&igrave;</label>
                                </div>
                                <div class="form-check">
                                    <input name="data" type="radio" id="radioGiovedi" value="GIOVEDI">
                                    <label for="radioGiovedi">Gioved&igrave;</label>
                                </div>
                                <div class="form-check">
                                    <input name="data" type="radio" id="radioVenerdi" value="VENERDI">
                                    <label for="radioVenerdi">Venerd&igrave;</label>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-5 col-form-label" for="oraInizio_prenotazione">Ora Inizio</label>
                                <div class="col-4">
                                    <input name="oraInizio" id="oraInizio_prenotazione" class="form-control" type="time"/>
                                </div>
                                <label class="col-3 col-form-label">ore</label>
                            </div>
                            <div class="form-group row">
                                <label class="col-5 col-form-label" for="durata_prenotazione">Durata prenotazione</label>
                                <div class="col-4">
                                    <input name="durata" id="durata_prenotazione" class="form-control" type="number" min="1" max="6" step="1"/>
                                </div>
                                <label class="col-3 col-form-label">ore</label>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- info footer -->
                <div class="modal-footer">
                    <% if (u != null && !u.getTipoUtente().equals(TipoUtente.ADMIN)) { %>
                    <button id="submit_button" type="button" class="btn btn-primary" data-dismiss="modal">Prenota</button>
                    <% } %>
                    <button type="button" class="btn btn-outline-primary" data-dismiss="modal">Chiudi</button>
                </div>
            </div>
        </div>
    </form>
</div>