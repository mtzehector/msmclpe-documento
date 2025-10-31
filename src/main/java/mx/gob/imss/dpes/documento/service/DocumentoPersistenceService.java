package mx.gob.imss.dpes.documento.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.documento.entity.McltDocumento;
import mx.gob.imss.dpes.documento.model.DocumentoTiposRequest;
import mx.gob.imss.dpes.documento.repository.DocumentoBySolicitudAndTipoSpecification;
import mx.gob.imss.dpes.documento.repository.DocumentoRepository;
import mx.gob.imss.dpes.documento.repository.DocumentosBySolAndListaTiposSpecification;
import mx.gob.imss.dpes.interfaces.documento.model.Documento;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class DocumentoPersistenceService extends BaseCRUDService<McltDocumento, McltDocumento, Long, Long> {

    @Autowired
    private DocumentoRepository repository;

    @Override
    public JpaSpecificationExecutor<McltDocumento> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<McltDocumento, Long> getJpaRepository() {
        return repository;
    }

    public List<McltDocumento> findBySolicitudAndTipo(Documento documento) {
        Collection<BaseSpecification> constraints = new ArrayList<>();
        constraints.add(new DocumentoBySolicitudAndTipoSpecification(
                documento.getCveSolicitud(),
                documento.getTipoDocumento()));

        return load(constraints);

    }

    public List<McltDocumento> findBySolicitudAndListaTipos(DocumentoTiposRequest documento) {
        Collection<BaseSpecification> constraints = new ArrayList<>();
        constraints.add(new DocumentosBySolAndListaTiposSpecification(
                documento.getCveSolicitud(),
                documento.getTiposDocumento()));

        return load(constraints);

    }

    public List<McltDocumento> findByPersona(Long cvePersona) {
        List<McltDocumento> documentoList = new LinkedList<>();
        documentoList = this.repository.findByCvePersona(cvePersona);
        if (documentoList == null) {
            documentoList = new LinkedList<>();
        }
        return documentoList;
   }
    
    public List<McltDocumento> findByEntidadFinanciera(Long cvePersona) {
        List<McltDocumento> documentoList = new LinkedList<>();
        documentoList = this.repository.findByCveEntidadFinanciera(cvePersona);
        if (documentoList == null) {
            documentoList = new LinkedList<>();
        }
        return documentoList;
   }
    
    public McltDocumento findByCvePersonaAndTipoDocumento(Long cvePersona, Long tipoDocumento) {
        McltDocumento documento = this.repository.findByPersonaAndTipo(cvePersona,tipoDocumento);
        return documento;
   }
    
    public List<McltDocumento> findByCveSolicitudAndTipoDocumento(Long cveSolicitud, Long tipoDocumento) {
        List<McltDocumento> documento = this.repository.findByCveSolicitudAndTipoDocumento(cveSolicitud,tipoDocumento);
        return documento;
   }

   public McltDocumento findByCveSolicitudAndRefBoveda(Long cveSolicitud){
        return this.repository.findByCveSolicitudAndRefDocBovedaNotNull(cveSolicitud);
   }

}
