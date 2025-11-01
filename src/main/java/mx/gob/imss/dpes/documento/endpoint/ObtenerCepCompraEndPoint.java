/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.documento.endpoint;

import java.util.logging.Level;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.PageRequestModel;
import mx.gob.imss.dpes.documento.entity.McltDocumento;
import mx.gob.imss.dpes.documento.model.CepCompraReq;
import mx.gob.imss.dpes.documento.service.ObtenerCepCompraService;
import org.springframework.data.domain.Page;

/**
 *
 * @author juanf.barragan
 */
@ApplicationScoped
@Path("/obtenerCepCompra")
public class ObtenerCepCompraEndPoint extends BaseGUIEndPoint<McltDocumento, McltDocumento, McltDocumento>{
    
    @Inject
    private ObtenerCepCompraService service;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocumentos(PageRequestModel<CepCompraReq> request) throws BusinessException{
        
        log.log(Level.INFO, ">>> documentoBack|ObtenerCepCompraEndPoint {0} ", request);
        
        Page<McltDocumento> page;
        
        page = service.obtenerCepCompras(request);
        
        return Response.ok(page).build();
    }
    
}
