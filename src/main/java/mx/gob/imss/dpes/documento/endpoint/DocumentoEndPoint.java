package mx.gob.imss.dpes.documento.endpoint;

import java.util.List;
import java.util.logging.Level;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.exception.DataNotFoundException;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.documento.service.DocumentoPersistenceService;
import mx.gob.imss.dpes.documento.entity.McltDocumento;
import mx.gob.imss.dpes.documento.model.DocumentoTiposRequest;
import mx.gob.imss.dpes.interfaces.documento.model.Documento;

@ApplicationScoped
@Path("/documento")
public class DocumentoEndPoint extends BaseGUIEndPoint<McltDocumento, McltDocumento, McltDocumento> {

    @Inject
    private DocumentoPersistenceService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response create(McltDocumento documento) throws BusinessException {
        log.log(Level.INFO, ">>> documentoBack DocumentoEndPoint Inicia DocumentoBack");
        
        documento.setIndDocumentoHistorico(Boolean.FALSE);
        
        log.log(Level.INFO, ">>> documentoBack DocumentoEndPoint documento IN:{0}", documento);

        return Response.ok(service.save(documento)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/refDocBoveda")
    @Override
    public Response update(McltDocumento documento) throws BusinessException {
        McltDocumento persisted = service.findOne(documento.getId());
        persisted.setRefDocBoveda(documento.getRefDocBoveda());
        return Response.ok(service.save(persisted)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/persona")
    public Response updatePersona(Documento documento) throws BusinessException {
        McltDocumento persisted = service.findOne(documento.getId());
        persisted.setCvePersona(documento.getCvePersona());
        return Response.ok(service.save(persisted)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/persona-clean")
    public Response updatePersonaClean(Documento documento) throws BusinessException {
        List<McltDocumento> persistedList = service.findByPersona(documento.getCvePersona());
        for (McltDocumento persisted : persistedList) {
            persisted.setCvePersona(null);
            service.save(persisted);
        }
        return toResponse(new Message());

    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/entidadfinanciera")
    public Response updateEntidadFinanciera(Documento documento) throws BusinessException {
        McltDocumento persisted = service.findOne(documento.getId());
        persisted.setCveEntidadFinanciera(documento.getCveEntidadFinanciera());
        return Response.ok(service.save(persisted)).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/solicitud")
    public Response updateSolicitud(Documento documento) throws BusinessException {
        McltDocumento persisted = service.findOne(documento.getId());
        persisted.setCveSolicitud(documento.getCveSolicitud());
        persisted.setCvePrestamoRecuperacion(documento.getCvePrestamoRecuperacion());
        return Response.ok(service.save(persisted)).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/solicitud-cleanupdate")
    public Response cleanAndUpdateSolicitud(Documento documento) throws BusinessException {
        //ACTUALIZA CVE_SOLICITUD 1l EN TABLA MCLT_DOCUMENTO
        this.updateSolicitudCleanByTipo(documento);
        McltDocumento persisted = service.findOne(documento.getId());
        persisted.setCveSolicitud(documento.getCveSolicitud());
        persisted.setCvePrestamoRecuperacion(documento.getCvePrestamoRecuperacion());
        //LUEGO VUELVE A ACTUALIZAR CVE_SOLICITUD COMO ESTABA EN TABLA MCLT_DOCUMENTO
        return Response.ok(service.save(persisted)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/entidadfinanciera-clean")
    public Response updateEntidadFinancieraClean(Long cveEntidadFinanciera) throws BusinessException {
        log.log(Level.INFO, ">>> documentoBack DocumentoEndPoint updateEntidadFinancieraClean cveEntidadFinanciera={0}", cveEntidadFinanciera);
        List<McltDocumento> persistedList = service.findByEntidadFinanciera(cveEntidadFinanciera);
        int i = 0;
        for (McltDocumento persisted : persistedList) {
            log.log(Level.INFO, "           >>><<<["+(i++)+"] documentoBack DocumentoEndPoint updateEntidadFinancieraClean persisted.getId={0}", persisted.getId());
            persisted.setCveEntidadFinanciera(null);
            service.save(persisted);
        }
        return toResponse(new Message());

    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/solicitud-cleanbytipo")
    public Response updateSolicitudCleanByTipo(Documento documento) throws BusinessException {
        log.log(Level.INFO, ">>> documentoBack DocumentoEndPoint updateSolicitudCleanByTipo cveSolicitud="+documento.getCveSolicitud()+" tipoDocumento="+documento.getTipoDocumento());
        List<McltDocumento> persistedList = service.findByCveSolicitudAndTipoDocumento(documento.getCveSolicitud(),documento.getTipoDocumento().getTipo());
        int i = 0;
        for (McltDocumento persisted : persistedList) {
            log.log(Level.INFO, "           >>><<<["+(i++)+"] documentoBack DocumentoEndPoint.updateSolicitudCleanByTipo persisted.getId={0}", persisted.getId());
            persisted.setCveSolicitud(1L);
            service.save(persisted);
        }
        return toResponse(new Message());

    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/bajaByPersona")
    public Response bajaByPersona(Long id){
        try {
            List<McltDocumento> persisted = service.findByPersona(id);
            for(McltDocumento documento : persisted){
                bajaLogica(documento.getId());
            }
            return Response.ok(persisted).build();
        } catch (Exception nfe) {
            log.log(Level.SEVERE, null, nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response load(@PathParam("id") Long id) throws BusinessException {
        try {
            McltDocumento persisted = service.findOne(id);
            return Response.ok(persisted).build();
        } catch (Exception nfe) {
            log.log(Level.SEVERE, null, nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/bySolicitudAndTipo")
    public Response load(Documento documento) throws BusinessException {
        try {
            return Response.ok(service.findBySolicitudAndTipo(documento)).build();
        } catch (Exception nfe) {
            log.log(Level.SEVERE, null, nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/byPersonaAndTipo")
    public Response loadByPersonaAndTipo(Documento documento) throws BusinessException {
        try {
            log.log(Level.INFO, ">>>>>>>> DocumentoEndPoint  loadByPersonaAndTipo documento={0}", documento);
            log.log(Level.INFO, ">>>>>>>> DocumentoEndPoint  loadByPersonaAndTipo documento.getTipoDocumento().getTipo()={0}", documento.getTipoDocumento().getTipo());
            McltDocumento documentoEntidad = service.findByCvePersonaAndTipoDocumento(documento.getCvePersona(), documento.getTipoDocumento().getTipo());
            if (documentoEntidad != null) {
                return Response.ok(documentoEntidad).build();
            } else {
                return toResponse(new Message());
            }
        } catch (Exception nfe) {
            nfe.printStackTrace();
            log.log(Level.SEVERE, ">>>>>>>> ERROR DocumentoEndPoint  loadByPersonaAndTipo message={0}", nfe.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/refDocumento/{id}")
    public Response loadRefDocumento(@PathParam("id") Long id) throws BusinessException {
        try {
            McltDocumento persisted = service.findOne(id);
            return Response.ok(persisted.getRefDocumento()).build();
        } catch (Exception nfe) {
            log.log(Level.SEVERE, null, nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/refDocumento/bySolicitudAndTipo")
    public Response loadRefDocumento(Documento documento) throws BusinessException {
        try {
            log.log(Level.INFO, ">>>>>>>> DocumentoEndPoint  loadRefDocumento documento={0}", documento);
            List<McltDocumento> list = service.findBySolicitudAndTipo(documento);
            log.log(Level.INFO, ">>>>>>>> DocumentoEndPoint list docs {0}=", list.size());
            if (list.size() == 1) {
                return Response.ok(list.get(0).getRefDocumento()).build();
            }
            //return Response.status(Response.Status.NOT_FOUND).build();
            throw new DataNotFoundException();

        } catch (Exception nfe) {
            nfe.printStackTrace();
            log.log(Level.SEVERE, nfe.getMessage(), nfe);
            //return Response.status(Response.Status.NOT_FOUND).build();
            throw new UnknowException();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/refDocBoveda/bySolicitudAndTipo")
    public Response loadRefDocBoveda(Documento documento) throws BusinessException {
        try {

            List<McltDocumento> list
                    = service.findBySolicitudAndTipo(documento);
            if (list.size() == 1) {
                return Response.ok(list.get(0).getRefDocBoveda()).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();

        } catch (Exception nfe) {
            log.log(Level.SEVERE, null, nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     *
     * @param documento
     * @return documento
     * @throws BusinessException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/entidad/bySolicitudAndTipo")
    public Response loadDocumento(Documento documento) throws BusinessException {
        try {
            log.log(Level.INFO, "Request de busqueda:{0}", documento);
            List<McltDocumento> list
                    = service.findBySolicitudAndTipo(documento);
            if (list.size() == 1) {

                log.log(Level.INFO, "Lista: {0}", list.get(0));
                return Response.ok(list.get(0)).build();

            }
            log.log(Level.INFO, "Lista del error:");
            return Response.status(Response.Status.NO_CONTENT).build();

        } catch (Exception nfe) {
            log.log(Level.SEVERE, null, nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     *
     * @param id
     * @return void bajalogica
     * @throws BusinessException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/bajalogica")
    public Response bajaLogica(Long id) throws BusinessException {
        log.log(Level.INFO, "Inicia DocumentoBack");
        log.log(Level.INFO, "documento IN:{0}", id);
        service.delete(id);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/bySolicitudAndListaTipos")
    public Response load(DocumentoTiposRequest documento) throws BusinessException {
        try {
            log.log(Level.INFO, "Inicia DocumentoBack bySolicitudAndListaTipos");
            log.log(Level.INFO, "documento IN:{0}", documento.getTiposDocumento());
            return Response.ok(service.findBySolicitudAndListaTipos(documento)).build();
        } catch (Exception nfe) {
            log.log(Level.SEVERE, "Error al  obtener el archivo ", nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/reinstalacion/{idSolicitud}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPorSolicitudYRefBoveda(@PathParam("idSolicitud") Long idSolicitud) throws BusinessException{
        try {
            McltDocumento persisted = service.findByCveSolicitudAndRefBoveda(idSolicitud);
            return Response.ok(persisted).build();
        } catch (Exception nfe) {
            log.log(Level.SEVERE, null, nfe);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
